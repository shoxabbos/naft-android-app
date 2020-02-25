package uz.itmaker.naft.Activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.EmployerOfferProjects.Project;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOffer extends BaseActivity {

    private Spinner offer_spinner;
    private EditText offer_detail;
    private Button send_offer;
    SweetAlertDialog pDialog;
    String user_id;
    int freelancer_id;
    String desc;
    ProgressBar dd_loading;
    List<Project> arrayList;
    String pid;
    int keyNameStr;
    int job_id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_detail_app_bar_send_offer);
        setSupportActionBar(toolbar);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
        if (bundle != null){
            freelancer_id = bundle.getInt("id");
        }
        offer_detail = findViewById(R.id.ui_desc_detail);
        offer_spinner = findViewById(R.id.ui_spinner_send);
        dd_loading = findViewById(R.id.ui_bookloading);
        send_offer = findViewById(R.id.ui_send_offer);

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(SendOffer.this, Constants.ISUSERLOGGEDIN);

        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();
        if (isUserLoggedIn) {
            if (user.getProfile().getPmeta().getUserType().equals("employer")){
                offer_spinner.setVisibility(View.VISIBLE);
                dd_loading.setVisibility(View.VISIBLE);
                Call<List<Project>> call_rates = RetrofitClient.getInstance().getApi().getoffer_project(pid);
                call_rates.enqueue(new Callback<List<Project>>(){
                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                        dd_loading.setVisibility(View.GONE);
                        arrayList =response.body();
                        for (Project C:arrayList){
                            if (C.getTitle() != null){
                                Log.d("Rates" , C.getTitle());
                                final String[] languageName = new String[arrayList.size()];
                                for (int i=0 ; i<arrayList.size() ; i++){
                                    languageName[i]= arrayList.get(i).getTitle();
                                }
                                offer_spinner.setAdapter(new ArrayAdapter<String>(
                                        getApplicationContext(),
                                        R.layout.items_view,
                                        languageName
                                ));
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Project>> call, Throwable t) {
                        dd_loading.setVisibility(View.GONE);
                        Toast.makeText(SendOffer.this , "Нет доступных проектов" , Toast.LENGTH_SHORT).show();
                    }
                });


                offer_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                        if (arrayList != null && arrayList.size() != 0){
                            keyNameStr = arrayList.get(position).getId();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent){
                    }
                });
                send_offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendemployerOffer();
                    }
                });
            }
            if (user.getProfile().getPmeta().getUserType().equals("freelancer")){
                offer_spinner.setVisibility(View.GONE);
                send_offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pDialog =  new SweetAlertDialog(SendOffer.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Вы ограничены в выполнении этого действия");
                    pDialog.show();
                    }
                });
            }
        }

    }


    public  void sendemployerOffer(){
        dd_loading.setVisibility(View.VISIBLE);
        user_id = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();
        desc= offer_detail.getText().toString().trim();
        job_id = keyNameStr;
        Call<ResponseBody> call_freelancer = RetrofitClient.getInstance().getApi().Send_Employer_offer(user_id ,freelancer_id ,job_id  , desc );
        call_freelancer.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200){
                    dd_loading.setVisibility(View.GONE);
                    pDialog =  new SweetAlertDialog(SendOffer.this, SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("Молодец");
                    pDialog.setContentText("Предложение успешно отправлено");
                    pDialog.show();

                }else {
                    dd_loading.setVisibility(View.GONE);
                    pDialog =  new SweetAlertDialog(SendOffer.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Ошибка");
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dd_loading.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }
}
