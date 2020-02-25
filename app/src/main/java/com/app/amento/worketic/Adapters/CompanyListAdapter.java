package uz.itmaker.naft.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Company.Employer;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.company_ViewHolder> {

    private onListInteractionListnerWorketo mListener;
    List<Employer> companyItem;
    Context context;

    public CompanyListAdapter (List<Employer> _topcategoryItems  , onListInteractionListnerWorketo _context , Context context){
        companyItem =  _topcategoryItems;
        mListener = _context;
        this.context = context;
    }

    @NonNull
    @Override
    public company_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_search_result_company, viewGroup , false);
        CompanyListAdapter.company_ViewHolder freelancerViewHolder = new CompanyListAdapter.company_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final company_ViewHolder holder, int i) {
        holder.mItem = companyItem.get(i);
        holder.ui_name.setText(Html.fromHtml(companyItem.get(i).getName()));
        holder.ui_title.setText(Html.fromHtml(companyItem.get(i).getTagLine()));
        if (companyItem.get(i).getProfileImg().equals("")){
        }else {
            Picasso.get().load(companyItem.get(i).getProfileImg()).into(holder.ui_profile);
        }

        if (companyItem.get(i).getBannerImg().equals("")){
        }else {
            Picasso.get().load(companyItem.get(i).getBannerImg()).into(holder.ui_banner);
        }

        holder.ui_company_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onAllCompanyListInteraction(holder.mItem);
                }
            }
        });

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(context, Constants.ISUSERLOGGEDIN);

        if (companyItem.get(i).getFavorit().equals("yes")){
            holder.unfav.setVisibility(View.GONE);
            holder.fav.setVisibility(View.VISIBLE);
            holder.fav.setClickable(false);

        }else{
            holder.fav.setVisibility(View.GONE);
            holder.unfav.setVisibility(View.VISIBLE);}
    }

    @Override
    public int getItemCount() {
        return companyItem.size();
    }

    public static class company_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public TextView ui_name , ui_title;
        public ImageView ui_profile , ui_banner , fav  , unfav;
        public CardView ui_company_layout;
        public Employer mItem;

        public company_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ui_name = mView.findViewById(R.id.ui_company_name);
            ui_title = mView.findViewById(R.id.ui_company_title);
            ui_profile = mView.findViewById(R.id.ui_company_profile_image);
            ui_banner = mView.findViewById(R.id.ui_company_banner_image);
            ui_company_layout = mView.findViewById(R.id.ui_company_list_layout);
            fav = mView.findViewById(R.id.company_fav);
            unfav = mView.findViewById(R.id.company_unfav);

        }
    }

    public void addcompnayPagination(List<Employer> company){

        for (Employer lp : company){
            companyItem.add(lp);
        }
        notifyDataSetChanged();
    }
}
