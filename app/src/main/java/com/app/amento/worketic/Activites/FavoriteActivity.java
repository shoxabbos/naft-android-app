package uz.itmaker.naft.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import uz.itmaker.naft.Adapters.CompanyListAdapter;
import uz.itmaker.naft.Adapters.HomeFreelancerAdapter;
import uz.itmaker.naft.Adapters.LatestJobCompleteAdapter;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.Company.Employer;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static uz.itmaker.naft.Fragments.HomeFragment.REQUEST_UPDATE_FAVORITE;

public class FavoriteActivity extends BaseActivity {

    private RadioGroup radiogroup;
    private RadioButton freelancer_btn , job_btn , company_btn;
    private RecyclerView freelacer_fav_view , job_fav_view , company_fav_view;
    ProgressBar dd_loading;
    private ProviderModel provider;
    private LatestJobModel jobModel;
    private SweetAlertDialog pDialog;
    private ImageView noData;
    String pid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_favourite_toolbar);
        setSupportActionBar(toolbar);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initData();
        setFavData();
    }

    private void initData(){

        noData =   findViewById(R.id.list_no_data);
        dd_loading = findViewById(R.id.ui_bookloading);
        radiogroup =  findViewById(R.id.ui_radiogroup_signup_fav);
        freelancer_btn =  findViewById(R.id.ui_freelancer_radiobtn_fav);
        job_btn =  findViewById(R.id.ui_job_radiobtn_fav);
        company_btn = findViewById(R.id.ui_employer_radiobtn_fav);
        freelacer_fav_view = findViewById(R.id.ui_freelancer_recycler_view);
        freelacer_fav_view.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this , LinearLayout.VERTICAL , false));
        job_fav_view = findViewById(R.id.ui_job_recycler_view);
        job_fav_view.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this , LinearLayout.VERTICAL , false));
        company_fav_view = findViewById(R.id.ui_employeer_recycler_view);
        company_fav_view.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this , LinearLayout.VERTICAL , false));

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int option = radiogroup.getCheckedRadioButtonId();

                switch (option)
                {
                    case R.id.ui_freelancer_radiobtn_fav:
                        if (freelancer_btn.isChecked()){
                            freelacer_fav_view.setVisibility(View.VISIBLE);
                            job_fav_view.setVisibility(View.GONE);
                            company_fav_view.setVisibility(View.GONE);
                            setFavData();
                        }
                    case R.id.ui_job_radiobtn_fav:
                        if (job_btn.isChecked()){

                            freelacer_fav_view.setVisibility(View.GONE);
                            job_fav_view.setVisibility(View.VISIBLE);
                            company_fav_view.setVisibility(View.GONE);
                            setjobData();
                        }
                    case R.id.ui_employer_radiobtn_fav:
                        if (company_btn.isChecked()){
                            freelacer_fav_view.setVisibility(View.GONE);
                            job_fav_view.setVisibility(View.GONE);
                            company_fav_view.setVisibility(View.VISIBLE);
                            setcompanyData();
                        }
                }
            }
        });

    }
    private void setFavData(){
        noData.setVisibility(View.GONE);
        dd_loading.setVisibility(View.VISIBLE);
        pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();
        Call<List<ProviderModel>> fav_freelancer = RetrofitClient.getInstance().getApi().getFav_Freelancer(pid , "favorite");
        fav_freelancer.enqueue(new Callback<List<ProviderModel>>() {
            @Override
            public void onResponse(Call<List<ProviderModel>> call, Response<List<ProviderModel>> response) {
                if (response.code() == 200){

                    List<ProviderModel> data = new ArrayList<>();

                    if (response.body() != null && !response.body().isEmpty()) {
                        noData.setVisibility(View.GONE);
                        data.addAll(response.body());
                        freelacer_fav_view.setAdapter(new HomeFreelancerAdapter(data, FavoriteActivity.this , getApplicationContext()));
                        dd_loading.setVisibility(View.GONE);
                    }else {
                        showNoData_freelancer();
                        dd_loading.setVisibility(View.GONE);
                    }
                }
                else if(response.code() == 203) {
                    showNoData_freelancer();
                    pDialog = new SweetAlertDialog(FavoriteActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("No Data in your Favourite list!");
                    pDialog.show();
                    dd_loading.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<ProviderModel>> call, Throwable t) {
                t.printStackTrace();
                showNoData_freelancer();
                dd_loading.setVisibility(View.GONE);
            }
        });

    }

    private void setjobData(){
        noData.setVisibility(View.GONE);
        dd_loading.setVisibility(View.VISIBLE);
        pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();
        Call<List<LatestJobModel>> fav_job = RetrofitClient.getInstance().getApi().getFav_job(pid , "favorite");
        fav_job.enqueue(new Callback<List<LatestJobModel>>() {
            @Override
            public void onResponse(Call<List<LatestJobModel>> call, Response<List<LatestJobModel>> response) {
                if (response.code() == 200){

                    List<LatestJobModel> data = new ArrayList<>();

                    if (response.body() != null && !response.body().isEmpty()) {
                        noData.setVisibility(View.GONE);
                        data.addAll(response.body());
                        job_fav_view.setAdapter(new LatestJobCompleteAdapter(data, FavoriteActivity.this , getApplicationContext()));
                        dd_loading.setVisibility(View.GONE);
                    }else {
                        showNoData_job();
                        dd_loading.setVisibility(View.GONE);
                    }
                }
                else if(response.code() == 203) {
                    showNoData_job();
                    dd_loading.setVisibility(View.GONE);
                    pDialog = new SweetAlertDialog(FavoriteActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("No Data in your Favourite list!");
                    pDialog.show();
                    dd_loading.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<LatestJobModel>> call, Throwable t) {
                t.printStackTrace();
                showNoData_job();
                dd_loading.setVisibility(View.GONE);
            }
        });
    }
    private void setcompanyData(){

        noData.setVisibility(View.GONE);
        dd_loading.setVisibility(View.VISIBLE);
        pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();
        Call<List<Employer>> fav_company = RetrofitClient.getInstance().getApi().getFav_company(pid , "favorite");
        fav_company.enqueue(new Callback<List<Employer>>() {
            @Override
            public void onResponse(Call<List<Employer>> call, Response<List<Employer>> response) {
                if (response.code() == 200){

                    List<Employer> data = new ArrayList<>();

                    if (response.body() != null && !response.body().isEmpty()) {
                        noData.setVisibility(View.GONE);
                        data.addAll(response.body());
                        company_fav_view.setAdapter(new CompanyListAdapter(data, FavoriteActivity.this , getApplicationContext()));
                        dd_loading.setVisibility(View.GONE);
                    }else {
                        showNoData_company();
                        dd_loading.setVisibility(View.GONE);
                    }

                }
                else if(response.code() == 203) {
                    showNoData_company();
                    dd_loading.setVisibility(View.GONE);
                    pDialog = new SweetAlertDialog(FavoriteActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Нет данных в вашем списке избранного!");
                    pDialog.show();
                    dd_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Employer>> call, Throwable t) {
                t.printStackTrace();
                showNoData_company();
                dd_loading.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onAllProviderListInteraction(ProviderModel item) {
        Intent detailActiivtyIntent = new Intent(FavoriteActivity.this, DetailFreelancerActivity.class);
        detailActiivtyIntent.putExtra(Constants.DATA , item);
        provider = item;
        startActivityForResult(detailActiivtyIntent,REQUEST_UPDATE_FAVORITE);
    }

    @Override
    public void onAlljobListInteraction(LatestJobModel item) {
        Intent detailActiivtyIntent = new Intent(FavoriteActivity.this, DetailJobActivity.class);
        detailActiivtyIntent.putExtra(Constants.DATA , item);
        jobModel = item;
        startActivityForResult(detailActiivtyIntent,REQUEST_UPDATE_FAVORITE);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }
    protected void showNoData_freelancer(){
        freelacer_fav_view.setAdapter(null);
        noData.setVisibility(View.VISIBLE);
    }
    protected void showNoData_job(){
        job_fav_view.setAdapter(null);
        noData.setVisibility(View.VISIBLE);
    }
    protected void showNoData_company(){
        company_fav_view.setAdapter(null);
        noData.setVisibility(View.VISIBLE);
    }
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }
}
