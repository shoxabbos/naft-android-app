package uz.itmaker.naft.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import uz.itmaker.naft.Activites.DetailFreelancerActivity;
import uz.itmaker.naft.Adapters.HomeFreelancerAdapter;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.victor.loading.rotate.RotateLoading;
import java.io.IOException;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static uz.itmaker.naft.Fragments.HomeFragment.REQUEST_UPDATE_FAVORITE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreelancerDetailFragment extends BaseFragment {
    List<ProviderModel> freelancerCategories;
    private RecyclerView freelancer_view;
    ProgressBar dd_loading;
    private View view;
    private ProviderModel provider;
    private boolean isLoading  = true;
    private int LP_pastVisibleItems , LP_visibleItemCount , LP_totalItemCount , LP_previous_total= 0;
    private int LP_view_threshold = 10;
    private int Response_key = 200;
    private int page_number=1;
    SweetAlertDialog pDialog;
    String pid;
    private GridLayoutManager layoutManager;
    private HomeFreelancerAdapter homeFreelancerAdapter;
    public FreelancerDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_freelancer_detail, container, false);

        initView();
        setData();

        return  view;
    }

    private void initView(){

        dd_loading = view.findViewById(R.id.ui_bookloading);
        layoutManager = new GridLayoutManager(getActivity() , 1);
        freelancer_view = view.findViewById(R.id.ui_freelancer_complete_listing);
//        freelancer_view.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayout.VERTICAL , false));

        freelancer_view.setHasFixedSize(true);
        freelancer_view.setLayoutManager(layoutManager);
    }

    private void setData(){
        dd_loading.setVisibility(View.VISIBLE);

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(getActivity(), Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){

            pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getProfileId().toString();

        }else {
            pid = "";
        }

        final Call<List<ProviderModel>> freelancer_category = RetrofitClient.getInstance().getApi().getCompleteFreelancer( pid , "search" , 10 ,page_number );
        freelancer_category.enqueue(new Callback<List<ProviderModel>>(){
            @Override
            public void onResponse(Call<List<ProviderModel>> call, Response<List<ProviderModel>> response) {
                if (response.code() == 200){
                    freelancerCategories = response.body();
                    homeFreelancerAdapter = new HomeFreelancerAdapter(freelancerCategories , FreelancerDetailFragment.this , getActivity());
                    freelancer_view.setAdapter(homeFreelancerAdapter);
                    dd_loading.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getActivity() , "Error" , Toast.LENGTH_SHORT).show();
                    dd_loading.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<ProviderModel>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity() , "Some Error" , Toast.LENGTH_SHORT).show();
                dd_loading.setVisibility(View.GONE);

            }
        });

        freelancer_view.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LP_visibleItemCount = layoutManager.getChildCount();
                LP_totalItemCount = layoutManager.getItemCount();
                LP_pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (dy>0)
                {
                    if (isLoading){

                        if (LP_totalItemCount>LP_previous_total){

                            isLoading = false;
                            LP_previous_total = LP_totalItemCount;
                        }
                    }
                    if (!isLoading &&(LP_totalItemCount-LP_visibleItemCount)<= (LP_pastVisibleItems+LP_view_threshold) ){

                        page_number++;
                        PerformPagination();
                        isLoading= true;
                    }

                }
            }
        });
    }

    private void PerformPagination(){

        dd_loading.setVisibility(View.VISIBLE);
        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(getActivity(), Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){

            pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getProfileId().toString();

        }else {
            pid = "";
        }
        Call<List<ProviderModel>> Latest_ProviderCall = RetrofitClient.getInstance().getApi().getCompleteFreelancer( pid , "search" , 10 , page_number);
        Latest_ProviderCall.enqueue(new Callback<List<ProviderModel>>() {
            @Override
            public void onResponse(Call<List<ProviderModel>> call, Response<List<ProviderModel>> response) {

                if (response.code() == 200){

                    // here i am getting response
                    List<ProviderModel> LatestProviderVerticalItem = response.body();
                    homeFreelancerAdapter.addLatestProviderPagination(LatestProviderVerticalItem);

                }else if(response.code() == 400){
                    Toast.makeText(getActivity(), "No more data available..." , Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "No more data available..." , Toast.LENGTH_SHORT).show();
                }
                dd_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<List<ProviderModel>> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof IOException){
                    pDialog =  new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("This is actual network failure!");
                    pDialog.show();
                    // logging probably not necessary
                    dd_loading.setVisibility(View.GONE);

                }
                else {
                    pDialog =  new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Нет больше данных!");
                    pDialog.show();
                    // todo log to some central bug tracking service
                    dd_loading.setVisibility(View.GONE);

                }
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


}
