package uz.itmaker.naft.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;

public class SignupActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    WebView signup_view;
    private TextView ui_login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_app_bar_signup);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        signup_view = findViewById(R.id.ui_signup_webview);
        signup_view.setWebViewClient(new MyBrowser());
        signup_view.getSettings().setJavaScriptEnabled(true);
        signup_view.setWebChromeClient(new WebChromeClient());
        signup_view.loadUrl(Constants.SIGNUP);
        ui_login = findViewById(R.id.ui_crateaccount_text);
        ui_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(SignupActivity.this , LoginActivity.class);
                startActivity(move);
            }
        });
    }



    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            showProgressDialog("Loading ...");
        }
        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
            hideProgressDialog();
        }
    }
    protected void showProgressDialog(String msg) {
        try {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage(msg);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }

    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }

}
