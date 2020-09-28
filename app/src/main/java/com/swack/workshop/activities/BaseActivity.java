package com.swack.workshop.activities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.swack.workshop.R;
import com.swack.workshop.data.APIService;
import com.swack.workshop.data.APIUrl;
import com.swack.workshop.data.SharedPreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;


public class BaseActivity extends AppCompatActivity {
	
	public static SharedPreferenceManager prefManager;
	public DisplayMetrics metrices;
	public APIService apiService;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		prefManager = new SharedPreferenceManager(this);
		metrices = getResources().getDisplayMetrics();
		apiService = APIUrl.getClient().create(APIService.class);
	}
	public void errorOut(Throwable t){
		if(!isNetworkAvailable()){
			Toasty.error(getApplicationContext(), getResources().getString(R.string.error_msg_no_internet), Toasty.LENGTH_LONG).show();
		}else {
			Toasty.error(getApplicationContext(), getResources().getString(R.string.serverdown) + t, Toasty.LENGTH_LONG).show();
		}
	}
	public  boolean isNetworkAvailable()
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static String getDateText(String timestamp)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		String date = format.format(new Date(Long.parseLong(timestamp)));
		String str = date;
		return str;
	}

	public String parseDate(String time) {
		String inputPattern = "dd-MM-yyyy HH:mm:ss";
		String outputPattern = "MMM dd,yyyy";
		SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
		SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

		Date date = null;
		String str = null;

		try {
			date = inputFormat.parse(time);
			str = outputFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

}
