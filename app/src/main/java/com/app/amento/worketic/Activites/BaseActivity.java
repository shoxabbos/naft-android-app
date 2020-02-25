package uz.itmaker.naft.Activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;

import uz.itmaker.naft.Interface.OnSignupLoginListener;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Category;
import uz.itmaker.naft.Model.Company.Employer;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.Model.profileData;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;

import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity implements onListInteractionListnerWorketo , OnSignupLoginListener {

    private static Locale myLocale;
    private ProgressDialog progressDialog;
    private String uid;
    private static String appUrl = "https://play.google.com/store/apps/details?id=" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        updateLocale(SharedPreferenceUtil.getStringValue(this,"locale",getString(R.string.default_locale)));
        progressDialog = new ProgressDialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setActionBar();
    }

    protected void openAcitivty(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }
    protected void setActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    protected void updateLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    public static void showRateDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Оценить заявку")
                .setMessage("Пожалуйста, оцените приложение в магазине")
                .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (context != null) {
                            String link = "market://details?id=";
                            try {
                                context.getPackageManager()
                                        .getPackageInfo("com.android.vending", 0);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                                link = appUrl;
                            }
                            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(link + context.getPackageName())));
                        }
                    }
                })
                .setNegativeButton("Отменить", null);
        builder.show();
    }

    protected void confirmLogoutApp(){


        new SweetAlertDialog(BaseActivity.this , SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Вы уверены?")
                .setContentText("Вы хотите выйти!")
                .setConfirmText("Да")
                .setCancelText("Нет")

                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        uid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();

                        Call<ResponseBody> Contact_Call = RetrofitClient.getInstance().getApi().logout(uid);
                        Contact_Call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                String s  = response.body().toString();
                                SharedPreferenceUtil.storeBooleanValue(BaseActivity.this, Constants.ISUSERLOGGEDIN, false);
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                realm.deleteAll();
                                realm.commitTransaction();
                                openAcitivty(LoginActivity.class);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                SweetAlertDialog  pDialog =  new SweetAlertDialog(BaseActivity.this, SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitleText("Ой...");
                                pDialog.setContentText("Something went wrong!");
                                pDialog.show();
                            }
                        });
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void onCategoryListInteraction(Category item) {
    }
    @Override
    public void onAllProviderListInteraction(ProviderModel item) {
    }
    @Override
    public void onAlljobListInteraction(LatestJobModel item) {
    }
    @Override
    public void onAllCompanyListInteraction(Employer item) {
    }
    @Override
    public void onProfileLoaded(profileData items) {
    }
    @Override
    public void onLoginUser(RetrofitClientLogin data) {
    }
}
