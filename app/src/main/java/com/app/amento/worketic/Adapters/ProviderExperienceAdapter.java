package uz.itmaker.naft.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Provider.Experience;
import uz.itmaker.naft.Model.Provider.Review;
import uz.itmaker.naft.R;

import java.util.List;

public class ProviderExperienceAdapter extends RecyclerView.Adapter<ProviderExperienceAdapter.experience_ViewHolder> {

    private onListInteractionListnerWorketo mListener;
    List<Experience> ExperienceItems;

    public ProviderExperienceAdapter (List<Experience> _topcategoryItems  , onListInteractionListnerWorketo _context){
        ExperienceItems =  _topcategoryItems;
        mListener = _context;

    }

    @NonNull
    @Override
    public experience_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_freelancer_experience, viewGroup , false);
        ProviderExperienceAdapter.experience_ViewHolder freelancerViewHolder = new ProviderExperienceAdapter.experience_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull experience_ViewHolder holder, int i) {
        holder.mItem = ExperienceItems.get(i);
        holder.title.setText(Html.fromHtml(ExperienceItems.get(i).getTitle()).toString());
        holder.company.setText(Html.fromHtml(ExperienceItems.get(i).getCompany()).toString());
        holder.date.setText(Html.fromHtml(ExperienceItems.get(i).getStartdate() + " - " + ExperienceItems.get(i).getEnddate()).toString());
        holder.title.setText(Html.fromHtml(ExperienceItems.get(i).getTitle()).toString());
    }

    @Override
    public int getItemCount() {
        return ExperienceItems.size();
    }

    public static class experience_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        private TextView title , company , date , content;
        public Experience mItem;

        public experience_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            title = mView.findViewById(R.id.ui_experience_title);
            company = mView.findViewById(R.id.ui_experience_company);
            date = mView.findViewById(R.id.ui_experience_date);
            content = mView.findViewById(R.id.ui_experience_content);


        }
    }


}
