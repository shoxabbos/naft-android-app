package uz.itmaker.naft.Interface;

import uz.itmaker.naft.Model.Category;
import uz.itmaker.naft.Model.Company.Employer;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.Model.profileData;

public interface onListInteractionListnerWorketo {
    void onCategoryListInteraction(Category item);
    void onAllProviderListInteraction(ProviderModel item);
    void onAlljobListInteraction(LatestJobModel item);
    void onAllCompanyListInteraction(Employer item);
    void onProfileLoaded(profileData items);

}
