package uz.itmaker.naft.Fragments;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import uz.itmaker.naft.Activites.DetailFreelancerActivity;
import uz.itmaker.naft.Activites.DetailJobActivity;
import uz.itmaker.naft.Activites.SearchResultActivity;
import uz.itmaker.naft.Adapters.HomeFreelancerAdapter;
import uz.itmaker.naft.Adapters.HomeImageAdapter;
import uz.itmaker.naft.Adapters.HomeLatestJobAdapter;
import uz.itmaker.naft.Adapters.TopCatAdpater;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.Category;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Transfoamtion.FadePageTransformer;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    public static final int REQUEST_UPDATE_FAVORITE = 105;
    ViewPager viewPager;
    TextView featuredText;
    List<Category> topCategories;
    List<ProviderModel> freelancerCategories;
    List<LatestJobModel> latestjobcategories;
    private ProviderModel provider;
    private LatestJobModel jobModel;
    int show = 5;
    String pid;
    private ShimmerRecyclerView mtopcat , mfeatured , mlatest;

    private View mMainView;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_home, container, false);

        featuredText = (TextView) mMainView.findViewById(R.id.ui_featured_text);
        featuredText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });

        viewPager = mMainView.findViewById(R.id.view_pager_home);
        HomeImageAdapter adapter = new HomeImageAdapter(getActivity());
        viewPager.setPageTransformer(false, new FadePageTransformer());
        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyImageTimer() , 4000 , 4000);

        initView();
        getTopCategories();
        getFeaturedFreelancers();
        getlatestpostedjob();

        return mMainView;
    }



    public class MyImageTimer extends TimerTask {

        @Override
        public void run() {
            if (getActivity()!= null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(viewPager.getCurrentItem() == 0){
                            viewPager.setCurrentItem(1);
                        }else if(viewPager.getCurrentItem() == 1){
                            viewPager.setCurrentItem(2);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }

    public void initView(){

        mtopcat = mMainView.findViewById(R.id.shimmer_recycler_view_cat);
        mtopcat.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayout.HORIZONTAL , false));

        mfeatured = mMainView.findViewById(R.id.shimmer_recycler_view_featured);
        mfeatured.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayout.VERTICAL , false));

        mlatest = mMainView.findViewById(R.id.shimmer_recycler_view_latest_job);
        mlatest.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayout.HORIZONTAL , false));

    }

    public void getTopCategories(){
        final Call<List<Category>> top_categoryCall = RetrofitClient.getInstance().getApi().getTopCategories();
        top_categoryCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.code() == 200){
                    topCategories = response.body();
                    TopCatAdpater topCategoryAdapter = new TopCatAdpater(topCategories , HomeFragment.this);
                    mtopcat.setAdapter(topCategoryAdapter);
                } else {
                    Toast.makeText(getActivity() , response.message() , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        });
    }

    public void getFeaturedFreelancers(){

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(getActivity(), Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){

            pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getProfileId().toString();

        }else {
            pid = "";
        }

        final Call<List<ProviderModel>> freelancer_category = RetrofitClient.getInstance().getApi().getHomeFreelancer(pid , "featured" , 5);
        freelancer_category.enqueue(new Callback<List<ProviderModel>>() {
            @Override
            public void onResponse(Call<List<ProviderModel>> call, Response<List<ProviderModel>> response) {
                if (response.code() == 200){
                    freelancerCategories = response.body();
                    HomeFreelancerAdapter homeFreelancerAdapter = new HomeFreelancerAdapter(freelancerCategories , HomeFragment.this , getActivity());
                    mfeatured.setAdapter(homeFreelancerAdapter);
                }
                else {
                    Toast.makeText(getActivity() , "Some Error" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProviderModel>> call, Throwable t) {
                t.printStackTrace();
                mfeatured.setVisibility(View.GONE);
            }
        });
    }

    public  void getlatestpostedjob(){
        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(getActivity(), Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){

            pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getProfileId().toString();

        }else {
            pid = "";
        }

        final Call<List<LatestJobModel>> latestjob = RetrofitClient.getInstance().getApi().getLatestjob(pid , "latest" , 5);
        latestjob.enqueue(new Callback<List<LatestJobModel>>() {
            @Override
            public void onResponse(Call<List<LatestJobModel>> call, Response<List<LatestJobModel>> response) {

                if (response.code() == 200){
                    latestjobcategories = response.body();
                    HomeLatestJobAdapter homeLatestJobAdapter = new HomeLatestJobAdapter(latestjobcategories , HomeFragment.this , getActivity());
                    mlatest.setAdapter(homeLatestJobAdapter);
                }
                else if (response.code() == 203){
                    Toast.makeText(getActivity() , response.message() , Toast.LENGTH_SHORT).show();
                    mlatest.setVisibility(View.GONE);
                }else if(response.code() == 500){
                    mlatest.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<LatestJobModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onAllProviderListInteraction(ProviderModel item) {
        Intent detailActiivtyIntent = new Intent(getActivity(), DetailFreelancerActivity.class);
        detailActiivtyIntent.putExtra(Constants.DATA , item);
        provider = item;
        startActivityForResult(detailActiivtyIntent,REQUEST_UPDATE_FAVORITE);
    }

    @Override
    public void onAlljobListInteraction(LatestJobModel item) {
        Intent detailActiivtyIntent = new Intent(getActivity(), DetailJobActivity.class);
        detailActiivtyIntent.putExtra(Constants.DATA , item);
        jobModel = item;
        startActivityForResult(detailActiivtyIntent,REQUEST_UPDATE_FAVORITE);
    }

    @Override
    public void onCategoryListInteraction(Category item) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DATA, item.getSlug());
        bundle.putString(Constants.TITLE, "Top Category");
        openAcitivty(bundle, SearchResultActivity.class);
    }
    protected void openAcitivty(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtra(Constants.DATA, bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
