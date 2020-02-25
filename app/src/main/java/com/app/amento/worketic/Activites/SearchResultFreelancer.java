package uz.itmaker.naft.Activites;

import android.content.Intent;
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
import uz.itmaker.naft.Interface.RetrofitClient;
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

public class SearchResultFreelancer extends BaseActivity {

    private String keyword = Constants.EMPTY_STRING;
    private String hourly_rate = Constants.EMPTY_STRING;
    private String[] language;
    private String[] location;
    private String[] skills;
    private String[] english_level;
    private String[] type;
    private String user_id;
    private ProviderModel provider;
    public RecyclerView recyclerView;
    private ImageView noData;
    ProgressBar dd_loading;
    String pid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_freelancer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_app_bar_search_freelancer);
        setSupportActionBar(toolbar);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        noData =   findViewById(R.id.list_no_data);
        dd_loading = findViewById(R.id.ui_bookloading);
        recyclerView = findViewById(R.id.ui_search_result_freelancer);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultFreelancer.this , LinearLayout.VERTICAL , false));

        loadData();

    }

    private void loadData(){

        dd_loading.setVisibility(View.VISIBLE);
        if (getIntent() !=null){

            Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
            if (bundle != null){

                keyword = bundle.getString("name");
                skills = bundle.getStringArray("skill");
                location = bundle.getStringArray("location");
//              hourly_rate = bundle.getString("rate");
                type = bundle.getStringArray("freelancerlevel");
                english_level = bundle.getStringArray("englishlevel");
                language= bundle.getStringArray("language");

            }

        }

        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        if(SharedPreferenceUtil.getBoolen(this, Constants.ISUSERLOGGEDIN)
                && user != null) {
            user_id = user.getProfile().getUmeta().getId();

        }
        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(SearchResultFreelancer.this, Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){

            pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getProfileId().toString();

        }else {
            pid = "";
        }
        RetrofitClient.getInstance().getApi().SearchFreelancer(
                pid,
                "search",
                10,
                keyword,
                skills,
                location,
//              hourly_rate,
                type,
                english_level,
                language

        ).enqueue(dataCallBack);

    }


    Callback<List<ProviderModel>> dataCallBack = new Callback<List<ProviderModel>>() {
        @Override
        public void onResponse(Call<List<ProviderModel>> call, Response<List<ProviderModel>> response) {
            if (response.code() == 200) {
                List<ProviderModel> data = new ArrayList<>();

                if (response.body() != null && !response.body().isEmpty()) {
                    data.addAll(response.body());
                    recyclerView.setAdapter(new HomeFreelancerAdapter(data, SearchResultFreelancer.this , getApplicationContext()));
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
        public void onFailure(Call<List<ProviderModel>> call, Throwable t) {
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
    public void onAllProviderListInteraction(ProviderModel item) {
        Intent detailActiivtyIntent = new Intent(SearchResultFreelancer.this, DetailFreelancerActivity.class);
        detailActiivtyIntent.putExtra(Constants.DATA , item);
        provider = item;
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
