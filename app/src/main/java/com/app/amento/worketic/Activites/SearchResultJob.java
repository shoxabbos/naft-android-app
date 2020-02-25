package uz.itmaker.naft.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import uz.itmaker.naft.Adapters.HomeFreelancerAdapter;
import uz.itmaker.naft.Adapters.LatestJobCompleteAdapter;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static uz.itmaker.naft.Fragments.HomeFragment.REQUEST_UPDATE_FAVORITE;

public class SearchResultJob extends BaseActivity {

    private String keyword = Constants.EMPTY_STRING;
    private String[] language;
    private String[] category;
    private String[] location;
    private String[] skills;
    private String[] duration;
    private String[] type;
    private String[] project_type;
    private String user_id;
    private ProviderModel provider;
    public RecyclerView recyclerView;
    String pid;
    ProgressBar dd_loading;
    private ImageView noData;
    private LatestJobModel jobModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_job);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_app_bar_search_job);
        setSupportActionBar(toolbar);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        noData =   findViewById(R.id.list_no_data);
        dd_loading = findViewById(R.id.ui_bookloading);
        recyclerView = findViewById(R.id.ui_search_result_job);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultJob.this , LinearLayout.VERTICAL , false));

        loadData();
    }

    private void loadData(){

        dd_loading.setVisibility(View.VISIBLE);
        if (getIntent() !=null){

            Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
            if (bundle != null){

                keyword = bundle.getString("name");
                category = bundle.getStringArray("category");
                skills = bundle.getStringArray("skill");
                location = bundle.getStringArray("location");
                type = bundle.getStringArray("freelancerlevel");
                duration = bundle.getStringArray("durationArray");
                project_type = bundle.getStringArray("projectType");
                language= bundle.getStringArray("language");

            }

        }

        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        if(SharedPreferenceUtil.getBoolen(this, Constants.ISUSERLOGGEDIN)
                && user != null) {
            user_id = user.getProfile().getUmeta().getId();
        }

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(SearchResultJob.this, Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){

            pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getProfileId().toString();

        }else {
            pid = "";
        }

        RetrofitClient.getInstance().getApi().SearchJob(
                pid,
                "search",
                10,
                keyword,
                category,
                skills,
                location,
                type,
                duration,
                project_type,
                language

        ).enqueue(dataCallBack);
    }

    Callback<List<LatestJobModel>> dataCallBack = new Callback<List<LatestJobModel>>(){
        @Override
        public void onResponse(Call<List<LatestJobModel>> call, Response<List<LatestJobModel>> response) {
            if (response.code() == 200) {
                List<LatestJobModel> data = new ArrayList<>();

                if (response.body() != null && !response.body().isEmpty()) {
                    data.addAll(response.body());
                    recyclerView.setAdapter(new LatestJobCompleteAdapter(data, SearchResultJob.this , getApplicationContext()));
                    dd_loading.setVisibility(View.GONE);
                } else {
                    showNoData();
                    dd_loading.setVisibility(View.GONE);
                }
            } else {
                showNoData();
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                dd_loading.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(Call<List<LatestJobModel>> call, Throwable t) {
            t.printStackTrace();
            showNoData();
            dd_loading.setVisibility(View.GONE);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }

    @Override
    public void onAlljobListInteraction(LatestJobModel item) {
        Intent detailActiivtyIntent = new Intent(SearchResultJob.this, DetailJobActivity.class);
        detailActiivtyIntent.putExtra(Constants.DATA , item);
        jobModel = item;
        startActivityForResult(detailActiivtyIntent,REQUEST_UPDATE_FAVORITE);
    }

    protected void showNoData(){
        recyclerView.setAdapter(null);
        noData.setVisibility(View.VISIBLE);
    }
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }
}
