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

import uz.itmaker.naft.Adapters.LatestJobCompleteAdapter;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.Category;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import com.victor.loading.rotate.RotateLoading;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static uz.itmaker.naft.Fragments.HomeFragment.REQUEST_UPDATE_FAVORITE;

public class SearchResultActivity extends BaseActivity {

    private Category job_cat;
    String slug;
    private RecyclerView job_by_category;
    List<LatestJobModel> jobbycategories;
    private LatestJobModel jobModel;
    private ImageView noData;
    ProgressBar dd_loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_detail_app_bar_search_job_by_category);
        setSupportActionBar(toolbar);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        job_cat = (Category) getIntent().getSerializableExtra(Constants.DATA);
        noData =   findViewById(R.id.list_no_data);
        dd_loading = findViewById(R.id.ui_bookloading);
        job_by_category = findViewById(R.id.ui_job_by_category_listing);
        job_by_category.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this , LinearLayout.VERTICAL , false));

        getjobbyCategories();
    }


    public void getjobbyCategories(){
        dd_loading.setVisibility(View.VISIBLE);


        slug =  getIntent().getBundleExtra(Constants.DATA).getString(Constants.DATA);

        final Call<List<LatestJobModel>> top_categoryCall = RetrofitClient.getInstance().getApi().getjobbycategories("search" , slug);
        top_categoryCall.enqueue(new Callback<List<LatestJobModel>>() {
            @Override
            public void onResponse(Call<List<LatestJobModel>> call, Response<List<LatestJobModel>> response) {
                if (response.code() == 200){
                    jobbycategories = response.body();
                    LatestJobCompleteAdapter homeLatestJobAdapter = new LatestJobCompleteAdapter(jobbycategories ,   SearchResultActivity.this , getApplicationContext());
                    job_by_category.setAdapter(homeLatestJobAdapter);
                    dd_loading.setVisibility(View.GONE);

                }
                else {
                    showNoData();
                    dd_loading.setVisibility(View.GONE);

                }
            }
            @Override
            public void onFailure(Call<List<LatestJobModel>> call, Throwable t) {
                t.printStackTrace();
                showNoData();
                dd_loading.setVisibility(View.GONE);

            }
        });

    }
    @Override
    public void onAlljobListInteraction(LatestJobModel item) {
        Intent detailActiivtyIntent = new Intent(SearchResultActivity.this, DetailJobActivity.class);
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
    protected void showNoData(){
        job_by_category.setAdapter(null);
        noData.setVisibility(View.VISIBLE);
    }
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }
}
