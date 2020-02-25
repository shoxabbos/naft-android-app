package uz.itmaker.naft.Fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.view.View;


import uz.itmaker.naft.Interface.DialogInteractionListener;
import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Category;
import uz.itmaker.naft.Model.Company.Employer;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.Model.profileData;

public class BaseFragment extends Fragment implements  View.OnClickListener, onListInteractionListnerWorketo, DialogInteractionListener {
    private ProgressDialog progressDialog;
    private ProviderModel provider;


    @Override
    public void onPositiveClick(String msg) {
    }
    @Override
    public void onCategoryListInteraction(Category item) {
    }
    @Override
    public void onAllProviderListInteraction(ProviderModel item) {
    }
    @Override
    public void onAlljobListInteraction(LatestJobModel item) {
    }
    @Override
    public void onAllCompanyListInteraction(Employer item) {
    }
    @Override
    public void onProfileLoaded(profileData items) {
    }
    @Override
    public void onClick(View v) {
    }
}
