package uz.itmaker.naft.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import uz.itmaker.naft.Adapters.CompanyListAdapter;
import uz.itmaker.naft.Adapters.LatestJobCompleteAdapter;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Company.Employer;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static uz.itmaker.naft.Fragments.HomeFragment.REQUEST_UPDATE_FAVORITE;

public class DetailCompanyActivity extends BaseActivity implements onListInteractionListnerWorketo {

    private ImageView ui_company_profile , ui_company_banner, ui_share_employee;
    private TextView ui_compnay_name , ui_company_tagline , ui_company_about , ui_company_id;
    private Employer company;
    List<Employer> CompanyListng;
    CompanyListAdapter companyListAdapter;
    List<LatestJobModel> latestjobcategories;
    private ShimmerRecyclerView recyclerView;
    private LinearLayout job_opening_layout;
    private Button follow_company;
    SweetAlertDialog pDialog;
    String pid;
    private LatestJobModel jobModel;
    String company_id;
    Toolbar toolbar;
    private ImageView noData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_company);
        toolbar = (Toolbar) findViewById(R.id.ui_detail_app_bar_company);
        setSupportActionBar(toolbar);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        company = (Employer) getIntent().getSerializableExtra(Constants.DATA);
        company_id = company.getEmployId();
        initData();
        setData();
        getData();

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(DetailCompanyActivity.this, Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){
            if (company.getFavorit().equals("yes")){

                follow_company.setText("Following");
                follow_company.setBackgroundResource(R.drawable.button_background);
                follow_company.setClickable(false);

            }else {

                follow_company.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setlistner();
                    }
                });
            }
        }else {

            follow_company.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pDialog =  new SweetAlertDialog(DetailCompanyActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Пожалуйста авторизуйтесь сначало");
                    pDialog.show();
                }
            });
        }
    }

    private void initData(){
        noData =   findViewById(R.id.list_no_data);
        ui_company_profile = findViewById(R.id.ui_company_profile_image);
        ui_company_banner = findViewById(R.id.ui_company_banner_image);
        ui_compnay_name = findViewById(R.id.ui_company_name_text);
        ui_company_tagline = findViewById(R.id.ui_company_tagline_text);
        ui_company_id = findViewById(R.id.ui_company_id_text);
        ui_company_about = findViewById(R.id.ui_company_about_text);
        job_opening_layout = findViewById(R.id.ui_layout_job_opening);
        follow_company = findViewById(R.id.ui_follow_company);
        ui_share_employee = findViewById(R.id.ui_share_company);
        recyclerView = (ShimmerRecyclerView) findViewById(R.id.shimmer_recycler_view_company_detail_job);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        ui_share_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCompany();
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(DetailCompanyActivity.this , SearchActivity.class);
                startActivity(move);
            }
        });

    }

    private void setData(){

        ui_compnay_name.setText(Html.fromHtml(company.getName()).toString());
        ui_company_tagline.setText(Html.fromHtml(company.getTagLine()).toString());
        ui_company_id.setText(company.getEmployId());
        ui_company_about.setText(Html.fromHtml(company.getEmployerDes()).toString());
        Picasso.get().load(company.getProfileImg()).into(ui_company_profile);
        Picasso.get().load(company.getBannerImg()).into(ui_company_banner);

    }

    private void setlistner(){

        String id = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().UpdateFavourite(id , Integer.parseInt(company.getUserId())  , "saved_employers");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200){

                   follow_company.setText("Following");
                   follow_company.setBackgroundResource(R.drawable.button_background);
                   follow_company.setClickable(false);

                }else {
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void getData(){
        noData.setVisibility(View.GONE);
        Call<List<LatestJobModel>> jobs = RetrofitClient.getInstance().getApi().getCompanyJobs("company" , company_id);
        jobs.enqueue(new Callback<List<LatestJobModel>>() {
            @Override
            public void onResponse(Call<List<LatestJobModel>> call, Response<List<LatestJobModel>> response) {
                if (response.code()==200){

                    latestjobcategories = response.body();
                    LatestJobCompleteAdapter homeLatestJobAdapter = new LatestJobCompleteAdapter(latestjobcategories , DetailCompanyActivity.this , DetailCompanyActivity.this);
                    recyclerView.setAdapter(homeLatestJobAdapter);
                }else {
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<LatestJobModel>> call, Throwable t) {
                noData.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });

    }
    protected void shareCompany() {
        String message = company.getCompanyLink();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Choose" ));
    }

    @Override
    public void onAlljobListInteraction(LatestJobModel item) {
        Intent detailActiivtyIntent = new Intent(DetailCompanyActivity.this, DetailJobActivity.class);
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
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }
}
