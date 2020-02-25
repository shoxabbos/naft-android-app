package uz.itmaker.naft.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassowrdActivity extends AppCompatActivity {


    private EditText wt_email;
    private TextView wt_login;
    private ProgressDialog mRegProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passowrd);


        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.forget_page_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        wt_login = findViewById(R.id.ui_move_login);
        wt_email = findViewById(R.id.ui_forgot_email);
        mRegProgress = new ProgressDialog(this);

        findViewById(R.id.ui_submit_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResetpasswordemail();
            }
        });
        wt_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent move = new Intent(ForgetPassowrdActivity.this , LoginActivity.class);
                startActivity(move);
            }
        });
    }

    public void sendResetpasswordemail(){

        String email = wt_email.getText().toString().trim();

        if (email.isEmpty()){
            wt_email.setError("email is Required");
            wt_email.requestFocus();
            return;
        }
        if (!TextUtils.isEmpty(email)){
            mRegProgress.setTitle("Sending email");
            mRegProgress.setMessage("Пожалуйста подождите");
            mRegProgress.setCanceledOnTouchOutside(false);
            mRegProgress.show();

            retrofit2.Call<ResponseBody> sendResetEmail = RetrofitClient.getInstance().getApi().recoverPassword(email);
            sendResetEmail.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == 200){
                        mRegProgress.dismiss();
                        String s  = response.body().toString();
                        Toast.makeText(ForgetPassowrdActivity.this , "Проверьте свою электронную почту, чтобы сбросить пароль" , Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(ForgetPassowrdActivity.this , "Электронная почта не существует" , Toast.LENGTH_LONG).show();

                    }

                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(ForgetPassowrdActivity.this ,  "При отправке вам контактной информации произошла ошибка..." , Toast.LENGTH_SHORT).show();
                }
            });
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
