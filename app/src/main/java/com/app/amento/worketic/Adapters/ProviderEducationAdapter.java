package uz.itmaker.naft.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Provider.Education;
import uz.itmaker.naft.Model.Provider.Experience;
import uz.itmaker.naft.R;

import java.util.List;

public class ProviderEducationAdapter extends RecyclerView.Adapter<ProviderEducationAdapter.education_ViewHolder> {

    private onListInteractionListnerWorketo mListener;
    List<Education> EducationItems;

    public ProviderEducationAdapter (List<Education> _topcategoryItems  , onListInteractionListnerWorketo _context){
        EducationItems =  _topcategoryItems;
        mListener = _context;

    }

    @NonNull
    @Override
    public education_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_freelancer_experience, viewGroup , false);
        ProviderEducationAdapter.education_ViewHolder freelancerViewHolder = new ProviderEducationAdapter.education_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull education_ViewHolder holder, int i) {
        holder.mItem = EducationItems.get(i);
        holder.title.setText(Html.fromHtml(EducationItems.get(i).getTitle()).toString());
        holder.company.setText(Html.fromHtml(EducationItems.get(i).getInstitute()).toString());
        holder.date.setText(Html.fromHtml(EducationItems.get(i).getStartdate() + "-" + EducationItems.get(i).getEnddate()).toString());
        holder.title.setText(Html.fromHtml(EducationItems.get(i).getTitle()).toString());

    }

    @Override
    public int getItemCount() {
        return EducationItems.size();
    }

    public static class education_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        private TextView title , company , date , content;
        public Education mItem;

        public education_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            title = mView.findViewById(R.id.ui_experience_title);
            company = mView.findViewById(R.id.ui_experience_company);
            date = mView.findViewById(R.id.ui_experience_date);
            content = mView.findViewById(R.id.ui_experience_content);


        }
    }

}
