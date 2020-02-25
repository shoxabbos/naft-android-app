package uz.itmaker.naft.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private EditText wt_login_email , wt_login_password;
    private Button wt_forget_password , wt_signin;
    private ProgressDialog mLoginProgress;
    private LinearLayout signup;
    SweetAlertDialog pDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_page_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mLoginProgress = new ProgressDialog(this);

        initViews();
        setListner();

    }

    public  void initViews(){

        wt_login_email = findViewById(R.id.ui_login_email);
        wt_login_password = findViewById(R.id.ui_login_password);
        wt_forget_password =  findViewById(R.id.ui_btn_forgot);
        wt_signin = findViewById(R.id.ui_signin);
        signup = findViewById(R.id.ui_login_linearlayout2);

    }

    public void setListner(){
        wt_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(LoginActivity.this , ForgetPassowrdActivity.class);
                startActivity(move);
            }
        });

        wt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(LoginActivity.this , SignupActivity.class);
                startActivity(move);
            }
        });

    }


    public void userLogin(){

        final String email = wt_login_email.getText().toString().trim();
        final String password = wt_login_password.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            wt_login_email.setError("Email не правильно отформатирован");
            wt_login_email.requestFocus();
            return;
        }
        if (password.isEmpty()){
            wt_login_password.setError("Необходим пароль");
            wt_login_password.requestFocus();
            return;
        }
        if (password.length() < 6){
            wt_login_password.setError("Пароль должен быть не менее 6 символов");
            wt_login_password.requestFocus();
            return;
        }


        if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

            mLoginProgress.setTitle("Происходит вход в систему");
            mLoginProgress.setMessage("Пожалуйста, подождите, пока мы проверяем ваши учетные данные.");
            mLoginProgress.setCanceledOnTouchOutside(false);
            mLoginProgress.show();


            SharedPreferenceUtil.storeStringValue(LoginActivity.this, Constants.USERNAME,email);
            SharedPreferenceUtil.storeStringValue(LoginActivity.this,Constants.PASSWORD,password);


            Call<RetrofitClientLogin> Latest_ProviderCall = RetrofitClient.getInstance().getApi().userLogin(email , password);
            Latest_ProviderCall.enqueue(new Callback<RetrofitClientLogin>() {
                @Override
                public void onResponse(Call<RetrofitClientLogin> call, Response<RetrofitClientLogin> response) {
                    if (response.code() == 200) {
                        RetrofitClientLogin data = response.body();
                        onLoginUser(data);
                        DatabaseUtil.getInstance().storeUser(response.body());
                        mLoginProgress.hide();
                        wt_login_email.getText().clear();
                        wt_login_password.getText().clear();

                    } else {
                        mLoginProgress.hide();
                        pDialog =  new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Ой...");
                        pDialog.setContentText("Неверный адрес электронной почты или пароль");
                        pDialog.show();
                    }
                }
                @Override
                public void onFailure(Call<RetrofitClientLogin> call, Throwable t) {
                    t.printStackTrace();
                    mLoginProgress.hide();
                    pDialog =  new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Сбой в работе сети");
                    pDialog.show();

                }
            });
        }
    }
    @Override
    public void onLoginUser(RetrofitClientLogin data) {
        if(data.getType().equals(Constants.SUCCESS)) {
            SharedPreferenceUtil.storeBooleanValue(this, Constants.ISUSERLOGGEDIN, true);
            if (getIntent() != null && getIntent().getBooleanExtra(Constants.IS_RESULT_ACTIVITY, false)) {
                setResult(RESULT_OK);
            } else {
                openAcitivty(HomeActivity.class);
                hideProgressDialog();
            }
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
