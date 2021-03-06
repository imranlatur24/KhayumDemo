package com.swack.workshop.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.swack.workshop.R;
import com.swack.workshop.data.APIService;
import com.swack.workshop.data.APIUrl;
import com.swack.workshop.model.ResponseResult;

import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity" ;
    private Dialog dialog;
    private Button btnLoginLogin,btn_Signup, btnOTP, btnSubmit;
    private TextView txt_forgot_password, resendOTP, dialogTitle;
    private EditText edtMobilelogin,edtPasswordlogin,edtOTP, edtMobile, edtPassword, edtRePassword;
    private String mobile,password,fcmcode, id;
    private APIService apiService;
    //private TextToSpeech mTTS;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    private void init() {

        prefManager.connectDB();
        fcmcode = prefManager.getString("FCM_TOKEN");
        prefManager.closeDB();

        System.out.println("Refreshed "+fcmcode);

        edtMobilelogin = findViewById(R.id.edtMobilelogin);
        edtPasswordlogin = findViewById(R.id.edtPasswordlogin);
        btnLoginLogin = findViewById(R.id.btnLoginLogin);
        btn_Signup = findViewById(R.id.btnRegisterLogin);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);
        txt_forgot_password.setOnClickListener(this);
        btnLoginLogin.setOnClickListener(this);
        btn_Signup.setOnClickListener(this);

        apiService = APIUrl.getClient().create(APIService.class);

      /*  mTTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mTTS.setLanguage(new Locale("en","IN"));

            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void otpDialog() {
        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.otp_verification);
        dialog.setCancelable(true);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialogTitle = dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText("Change Password");
        edtOTP = dialog.findViewById(R.id.edtOTP);
        edtPassword = dialog.findViewById(R.id.edtPassword);
        edtRePassword = dialog.findViewById(R.id.edtRePassword);
        resendOTP = dialog.findViewById(R.id.resendOTP);
        btnOTP = dialog.findViewById(R.id.btnOTP);

        edtPassword.setVisibility(View.VISIBLE);
        edtRePassword.setVisibility(View.VISIBLE);
        resendOTP.setOnClickListener(this);
        btnOTP.setOnClickListener(this);

        dialog.show();
    }

    private void passwordDialog() {
        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.forgot_password);
        dialog.setCancelable(true);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        edtMobile = dialog.findViewById(R.id.edtMobile);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);

        dialog.show();
    }

    private void otpVerification(final String mobile, final String password,final  String otp) {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("OTP verifying...");
        progressDialog.show();

        //calling the api
        apiService.otpVerificationPassword(APIUrl.KEY,
                id,
                mobile,
                password,
                otp
        ).enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                try{
                    System.out.println(TAG+ " Response "+response.body().getResponse());
                    if(Integer.parseInt(response.body().getResponse()) == 101) {
                        dialog.dismiss();
                        Toasty.success(getApplicationContext(), "Password changed successfully", Toast.LENGTH_LONG).show();
                    }else {
                        progressDialog.dismiss();
                        System.out.println(TAG + " Else Close");
                        Toasty.error(getApplicationContext(), getResources().getString(R.string.serverdown), Toast.LENGTH_LONG).show();
                    }
                }catch (NullPointerException e){
                    Toasty.error(getApplicationContext(), "Server break down, try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void forgotPassword(String mobile) {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.show();

        //calling the api
        apiService.forgotPassword(APIUrl.KEY,
                mobile
        ).enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                try {
                    if (Integer.parseInt(response.body().getResponse()) == 101) {
                        dialog.dismiss();
                        id =response.body().getLogin().get(0).getGar_id();
                        otpDialog();
                        Toasty.success(getApplicationContext(), "You will receive OTP shortly for change password", Toast.LENGTH_LONG).show();
                    } else if (Integer.parseInt(response.body().getResponse()) == 103) {
                        System.out.println(TAG + " Required Parameter Missing");
                        Toasty.error(getApplicationContext(), "Error, try again", Toast.LENGTH_LONG).show();
                    } else if (Integer.parseInt(response.body().getResponse()) == 104) {
                        System.out.println(TAG + " Invalid Key");
                        Toasty.error(getApplicationContext(), "Error, try again", Toast.LENGTH_LONG).show();
                    } else if (Integer.parseInt(response.body().getResponse()) == 105) {
                        System.out.println(TAG + " Invalid Mobile no");
                        Toasty.error(getApplicationContext(), "Invalid Mobile no, try again", Toast.LENGTH_LONG).show();
                    } else if (Integer.parseInt(response.body().getResponse()) == 106) {
                        System.out.println(TAG + " Page Not Found");
                        Toasty.error(getApplicationContext(), "Error, try again", Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println(TAG + " Else Close");
                        Toasty.error(getApplicationContext(), "Error, try again", Toast.LENGTH_LONG).show();
                    }
                }catch (NullPointerException e){
                    Toasty.error(getApplicationContext(), "Server break down, try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLoginLogin:
                mobile = edtMobilelogin.getText().toString().trim();
                password = edtPasswordlogin.getText().toString().trim();

                if (!isNetworkAvailable()) {
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.error_msg_no_internet), Toasty.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    // registerMobile.setError("Enter Mobile no.");
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.entermobile), Toasty.LENGTH_SHORT).show();
                    return;
                }
                if (mobile.length() < 10) {
                    //  registerMobile.setError("Enter Valid Mobile no.");
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.entervalidmobileno), Toasty.LENGTH_SHORT).show();
                    return;
                }
                if (String.valueOf(mobile.charAt(0)).equals("0") ||
                        String.valueOf(mobile.charAt(0)).equals("1") ||
                        String.valueOf(mobile.charAt(0)).equals("2") ||
                        String.valueOf(mobile.charAt(0)).equals("3") ||
                        String.valueOf(mobile.charAt(0)).equals("4") ||
                        String.valueOf(mobile.charAt(0)).equals("5") ||
                        String.valueOf(mobile.charAt(0)).equals("6")) {
                    // registerMobile.setError("Enter Valid Mobile .");
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.entervalidmobileno), Toasty.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    //registerCPassword.setError("Enter Confirm Password");
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.enterpassword), Toasty.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    // registerPassword.setError("Enter Password should not be less than 6 character");
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.enterpasswordvalidation), Toasty.LENGTH_SHORT).show();
                    return;
                }
                login(mobile,password);
                break;

            case R.id.btnRegisterLogin:
                startActivity(new Intent(LoginActivity.this,Registration.class));
                break;
            case R.id.txt_forgot_password:
                passwordDialog();
                break;
            case R.id.btnSubmit:
                mobile  = edtMobile.getText().toString().trim();
                if(!isNetworkAvailable()){
                    Toasty.error(this, "No internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    edtMobile.setError("Enter mobile number");
                    return;
                }
                if(mobile.length() < 10){
                    edtMobile.setError("Enter valid mobile number");
                    return;
                }
                forgotPassword(mobile);
                break;
            case R.id.resendOTP:
                mobile  = edtMobile.getText().toString().trim();
                if(!isNetworkAvailable()){
                    Toasty.error(this, "No internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    edtMobile.setError("Enter mobile number");
                    return;
                }
                if(mobile.length() < 10){
                    edtMobile.setError("Enter valid mobile number");
                    return;
                }
                forgotPassword(mobile);
                break;

            case R.id.btnOTP:
                password = edtPassword.getText().toString().trim();
                String rePassword = edtRePassword.getText().toString().trim();
                String otp = edtOTP.getText().toString();
                if(!isNetworkAvailable()){
                    Toasty.error(this, "No internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    edtPassword.setError("Enter password");
                    return;
                }
                if(password.length() < 6){
                    edtPassword.setError("Password should be greater than 6 letter");
                    return;
                }
                if(TextUtils.isEmpty(rePassword)){
                    edtRePassword.setError("Enter confirm password");
                    return;
                }
                if(TextUtils.isEmpty(otp)){
                    edtOTP.setError("Enter OTP");
                    return;
                }
                if(!password.equals(rePassword)){
                    Toasty.error(this, "Password don't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                otpVerification(mobile, password ,otp);
                break;
        }
    }

      private void login(String mobile, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        callLogin(mobile,password).enqueue(new Callback<ResponseResult>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                progressDialog.dismiss();
                Log.d(TAG, "response login : " + response.body().getResponse());
                if (Integer.parseInt(response.body().getResponse()) == 101) {

                    try{
                        Toasty.success(getApplicationContext(), "Welcome " + response.body().getLogin().get(0).getGar_name(), Toast.LENGTH_SHORT).show();
                    }catch (NullPointerException e)
                    {
                        e.printStackTrace();
                        Toasty.error(getApplicationContext(),"data not available",Toasty.LENGTH_SHORT).show();
                    }
                   /* mTTS.speak( "Welcome to swack workshop"+response.body().getGarageProfileList().get(0).getGar_name(), TextToSpeech.QUEUE_ADD, null, null);
                    mTTS.setPitch((float)0.8);
                    mTTS.setSpeechRate((float)0.2);*/
                    prefManager.connectDB();
                    prefManager.setBoolean("isLogin", true);
                    prefManager.setString("gar_id", response.body().getGarageProfileList().get(0).getGar_id());
                    prefManager.setString("gar_name", response.body().getGarageProfileList().get(0).getGar_name());
                    prefManager.setString("gar_address", response.body().getGarageProfileList().get(0).getGar_address());
                    prefManager.setString("gar_email", response.body().getGarageProfileList().get(0).getGar_email());
                    prefManager.setString("gar_mob", response.body().getGarageProfileList().get(0).getGar_mobi());
                    prefManager.setString("gar_pic", response.body().getGarageProfileList().get(0).getGarprofilepic());
                    prefManager.setString("gar_aadhar", response.body().getGarageProfileList().get(0).getGardoc_aadhar());
                    prefManager.setString("gar_pan", response.body().getGarageProfileList().get(0).getGardoc_pan());
                    prefManager.setString("gar_log", response.body().getGarageProfileList().get(0).getGarshopact());
                    prefManager.closeDB();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else if (Integer.parseInt(response.body().getResponse()) == 102) {
                    progressDialog.dismiss();
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.password_wrong), Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(response.body().getResponse()) == 103) {
                    progressDialog.dismiss();
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.user_does_not_exist), Toast.LENGTH_LONG).show();
                }else if (Integer.parseInt(response.body().getResponse()) == 6188) {
                    progressDialog.dismiss();
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.serverdown), Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    System.out.println(TAG + " Else Close");
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.serverdown), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                progressDialog.dismiss();
                errorOut(t);
            }
        });
    }

    private Call<ResponseResult>
    callLogin(final String mobile, final String password) {
        System.out.println("LoginUsername : "+mobile+" Password "+password+" fcmcode "+fcmcode);
        return apiService.callLoginApi(
                APIUrl.KEY,
                fcmcode,
                mobile,
                password
        );
    }


}
