package uz.itmaker.naft.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import uz.itmaker.naft.Activites.DetailCompanyActivity;
import uz.itmaker.naft.Adapters.CompanyListAdapter;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.Company.Employer;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;

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
public class CompanyListFragment extends BaseFragment {

    ProgressBar dd_loading;
    List<Employer> CompanyListng;
    Employer CompanyModelModel;
    private RecyclerView Companyview;
    public View mMainView;
    private boolean isLoading  = true;
    private int LP_pastVisibleItems , LP_visibleItemCount , LP_totalItemCount , LP_previous_total= 0;
    private int LP_view_threshold = 10;
    private int Response_key = 200;
    private int page_number=1;
    SweetAlertDialog pDialog;
    String pid;
    private GridLayoutManager layoutManager;
    private CompanyListAdapter companyListAdapter;
    public CompanyListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_company_list, container, false);

        initViews();
        setData();

        return mMainView;
    }


    private void initViews(){
        dd_loading = mMainView.findViewById(R.id.ui_bookloading);
        layoutManager = new GridLayoutManager(getActivity() , 1);
        Companyview = mMainView.findViewById(R.id.ui_company_listing);
//      Companyview.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayout.VERTICAL , false));
        Companyview.setHasFixedSize(true);
        Companyview.setLayoutManager(layoutManager);
    }

    private void setData(){
        dd_loading.setVisibility(View.VISIBLE);

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(getActivity(), Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){

            pid = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getProfileId().toString();

        }else {
            pid = "";
        }

        final Call<List<Employer>> latestjob = RetrofitClient.getInstance().getApi().getCompanyListing(pid , "search" , 10 , page_number);
        latestjob.enqueue(new Callback<List<Employer>>() {
            @Override
            public void onResponse(Call<List<Employer>> call, Response<List<Employer>> response) {
                if (response.code() == 200){

                    CompanyListng = response.body();
                    companyListAdapter = new CompanyListAdapter(CompanyListng , CompanyListFragment.this , getActivity());
                    Companyview.setAdapter(companyListAdapter);
                    dd_loading.setVisibility(View.GONE);

                }
                else {

                    Toast.makeText(getActivity() , response.message() , Toast.LENGTH_SHORT).show();
                    dd_loading.setVisibility(View.GONE);

                }
            }
            @Override
            public void onFailure(Call<List<Employer>> call , Throwable t) {

                t.printStackTrace();
                dd_loading.setVisibility(View.GONE);

            }
        });

        Companyview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LP_visibleItemCount = layoutManager.getChildCount();
                LP_totalItemCount   = layoutManager.getItemCount();
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
        Call<List<Employer>> Latest_ProviderCall = RetrofitClient.getInstance().getApi().getCompanyListing(pid , "search" , 10 , page_number);
        Latest_ProviderCall.enqueue(new Callback<List<Employer>>() {
            @Override
            public void onResponse(Call<List<Employer>> call, Response<List<Employer>> response) {

                if (response.code() == 200){

                    // here i am getting response
                    List<Employer> LatestProviderVerticalItem = response.body();
                    companyListAdapter.addcompnayPagination(LatestProviderVerticalItem);

                }else if(response.code() == 400){
                    pDialog =  new SweetAlertDialog(getActivity() , SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Нет больше данных!");
                    pDialog.show();
                    // todo log to some central bug tracking service
                    dd_loading.setVisibility(View.GONE);

                }else {
                    pDialog =  new SweetAlertDialog(getActivity() , SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Нет больше данных!");
                    pDialog.show();
                    // todo log to some central bug tracking service
                    dd_loading.setVisibility(View.GONE);

                }
                dd_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<List<Employer>> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof IOException) {
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
    public void onAllCompanyListInteraction(Employer item) {

        Intent detailActiivtyIntent = new Intent(getActivity(), DetailCompanyActivity.class);
        detailActiivtyIntent.putExtra(Constants.DATA , item);
        CompanyModelModel = item;
        startActivityForResult(detailActiivtyIntent,REQUEST_UPDATE_FAVORITE);
    }



}