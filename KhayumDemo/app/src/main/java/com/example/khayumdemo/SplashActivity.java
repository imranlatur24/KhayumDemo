package com.example.khayumdemo;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private ImageView splash_image;
    private boolean isLogin;
    private Intent intent;
   // private APIService apiService;
    static String KEY = "key";
    static String version_code = "1.2";
    private static int TIMEOUT_MILLIS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        init();

      /*  if(isNetworkAvailable()) {
            card_VersioncodeM();
        }else {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(4000);
                        if(isLogin){
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else {
                            intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        finish();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }*/
    }

    private void init()
    {
       // apiService = APIUrl.getClient().create(APIService.class);

        splash_image = findViewById(R.id.image);
      anim();


    }

    private void anim()
    {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down);
        splash_image.startAnimation(animation);
    }
}
