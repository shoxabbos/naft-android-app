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

import uz.itmaker.naft.Adapters.CompanyListAdapter;
import uz.itmaker.naft.Adapters.LatestJobCompleteAdapter;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.Company.Employer;
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

public class SearchResultCompany extends BaseActivity {

    private String keyword = Constants.EMPTY_STRING;
    private String[] location;
    private String[] employees;
    private String user_id;
    private ProviderModel provider;
    public RecyclerView recyclerView;
    private ImageView noData;
    ProgressBar dd_loading;
    Employer CompanyModelModel;
    String pid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_company);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_app_bar_search_company);
        setSupportActionBar(toolbar);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        noData =   findViewById(R.id.list_no_data);
        dd_loading = findViewById(R.id.ui_bookloading);
        recyclerView = findViewById(R.id.ui_search_result_company);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultCompany.this , LinearLayout.VERTICAL , false));
        loadData();

    }

    private void loadData(){

        dd_loading.setVisibility(View.VISIBLE);
        if (getIntent() !=null){

            Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
            if (bundle != null){

                keyword = bundle.getString("name");
                location = bundle.getStringArray("location");
                employees = bundle.getStringArray("duration");

            }

        }

        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        if(SharedPreferenceUtil.getBoolen(this, Constants.ISUSERLOGGEDIN)
                && user != null) {
            user_id = user.getProfile().getUmeta().getId();
        }
        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(SearchResultCompany.this, Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){

            pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getProfileId().toString();

        }else {
            pid = "";
        }
        RetrofitClient.getInstance().getApi().SearchComapny(
                pid,
                "search",
                10,
                keyword,
                location,
                employees

        ).enqueue(dataCallBack);

    }

    Callback<List<Employer>> dataCallBack = new Callback<List<Employer>>() {
        @Override
        public void onResponse(Call<List<Employer>> call, Response<List<Employer>> response) {
            if (response.code() == 200) {
                List<Employer> data = new ArrayList<>();

                if (response.body() != null && !response.body().isEmpty()) {
                    data.addAll(response.body());
                    recyclerView.setAdapter(new CompanyListAdapter(data, SearchResultCompany.this  , getApplicationContext()));
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
        public void onFailure(Call<List<Employer>> call, Throwable t) {
            t.printStackTrace();
            showNoData();
            dd_loading.setVisibility(View.GONE);
        }
    };

    @Override
    public void onAllCompanyListInteraction(Employer item) {

        Intent detailActiivtyIntent = new Intent(SearchResultCompany.this, DetailCompanyActivity.class);
        detailActiivtyIntent.putExtra(Constants.DATA , item);
        CompanyModelModel = item;
        startActivityForResult(detailActiivtyIntent,REQUEST_UPDATE_FAVORITE);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
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
