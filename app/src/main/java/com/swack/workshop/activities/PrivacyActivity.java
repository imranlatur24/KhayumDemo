package com.swack.workshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.swack.workshop.R;

import es.dmoral.toasty.Toasty;

public class PrivacyActivity extends AppCompatActivity {

    private WebView pdfWebView;
    private String pdf;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        getSupportActionBar().setTitle("Privacy Policy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        progressBar = ProgressDialog.show(PrivacyActivity.this, "PDF File", "please wait");
        pdf = "http://swack.in/swack/GarageApi/GarPrivacyPolice.pdf";

        pdfWebView = (WebView) findViewById(R.id.pdfWebView);
        pdfWebView.getSettings().setJavaScriptEnabled(true);
        pdfWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        pdfWebView.getSettings().setAllowFileAccess(true);
        pdfWebView.setScrollbarFadingEnabled(false);
        pdfWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toasty.error(PrivacyActivity.this, "Oh no! " + description, Toasty.LENGTH_SHORT).show();

            }
        });
        pdfWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ImageView locButton = (ImageView) menu.findItem(R.id.action_refresh).getActionView();
        if (locButton != null) {
            locButton.setImageResource(R.drawable.ic_refresh);
            locButton.setPadding(8,8,8,8);
            // need some resize
            locButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation rotation = AnimationUtils.loadAnimation(PrivacyActivity.this, R.anim.rotate);
                    view.startAnimation(rotation);
                    // create and use new data set
                    pdfWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);
                }
            });
        }
        return true;
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

}