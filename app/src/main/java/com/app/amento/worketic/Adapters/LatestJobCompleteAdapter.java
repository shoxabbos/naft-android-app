package uz.itmaker.naft.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestJobCompleteAdapter  extends RecyclerView.Adapter<LatestJobCompleteAdapter.home_latestjob_ViewHolder>  {


    private onListInteractionListnerWorketo mListener;
    List<LatestJobModel> latestJobItems;
    Context context;

    public LatestJobCompleteAdapter (List<LatestJobModel> _topcategoryItems  , onListInteractionListnerWorketo _context , Context context){
        latestJobItems =  _topcategoryItems;
        mListener = _context;
        this.context = context;
    }

    @NonNull
    @Override
    public LatestJobCompleteAdapter.home_latestjob_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_complete_job_listing, viewGroup , false);
        LatestJobCompleteAdapter.home_latestjob_ViewHolder freelancerViewHolder = new LatestJobCompleteAdapter.home_latestjob_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LatestJobCompleteAdapter.home_latestjob_ViewHolder holder, final int position) {

        holder.mItem = latestJobItems.get(position);
        holder.latestjob_name.setText(Html.fromHtml(latestJobItems.get(position).getEmployerName()).toString());
        holder.latestjob_title.setText(Html.fromHtml(latestJobItems.get(position).getProjectTitle()).toString());
        holder.latestjob_type.setText(Html.fromHtml(latestJobItems.get(position).getProjectLevel().getLevelTitle()).toString());
        if (latestJobItems.get(position).getLocation().getCountry().equals("")){
            holder.latestjob_layout.setVisibility(View.GONE);
        }else {
            holder.latestjob_country_name.setText(Html.fromHtml(latestJobItems.get(position).getLocation().getCountry()).toString());
        }
        holder.latestjob_schedule.setText(Html.fromHtml(latestJobItems.get(position).getProjectType()).toString());
        holder.latestjob_duration.setText(Html.fromHtml(latestJobItems.get(position).getProjectDuration()).toString());

        if(!latestJobItems.get(position).getLocation().getFlag().equals("")){
            Picasso.get().load(latestJobItems.get(position).getLocation().getFlag()).into(holder.latestjob_flag);
        }

        if (latestJobItems.get(position).getIsVerified().equals("yes")){
            holder.latestjob_verified.setVisibility(View.VISIBLE);
//            holder.complete_job.setCardBackgroundColor( Color.parseColor("#fffdf3") );

        }else {

            holder.latestjob_verified.setVisibility(View.GONE);
//            holder.complete_job.setCardBackgroundColor( Color.parseColor("#ffffff") );

        }

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onAlljobListInteraction(holder.mItem);
                }
            }
        });
        if(latestJobItems.get(position).getFeaturedColor().equals("")){
            holder.badge_color.setVisibility(View.GONE);
            holder.badge_icon.setVisibility(View.GONE);
        }else {
            holder.badge_icon.setVisibility(View.VISIBLE);
            Picasso.get().load(latestJobItems.get(position).getFeaturedUrl()).into(holder.badge_icon);
            holder.badge_color.setVisibility(View.VISIBLE);

        }

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(context, Constants.ISUSERLOGGEDIN);


        if (latestJobItems.get(position).getFavorit().equals("yes")){
            holder.unfav_image.setVisibility(View.GONE);
            holder.fav_image.setVisibility(View.VISIBLE);
            holder.fav_image.setClickable(false);

        }else{
            holder.fav_image.setVisibility(View.GONE);
            holder.unfav_image.setVisibility(View.VISIBLE);

            if (isUserLoggedIn){
                holder.unfav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        holder.dd_loading.setVisibility(View.VISIBLE);
                        String id = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();

                        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().UpdateFavourite(id , latestJobItems.get(position).getJobId() , "saved_jobs");
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code() == 200){

                                    holder.unfav_image.setVisibility(View.GONE);
                                    holder.fav_image.setVisibility(View.VISIBLE);
                                    holder.dd_loading.setVisibility(View.GONE);

                                }else if(response.code() == 203) {
                                    holder.dd_loading.setVisibility(View.GONE);
                                    holder.unfav_image.setVisibility(View.VISIBLE);
                                    holder.fav_image.setVisibility(View.GONE);
                                    holder.pDialog =  new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                                    holder.pDialog.setTitleText("Ой...");
                                    holder.pDialog.setContentText("Sorry, \n You are not authorized to perform this action.");
                                    holder.pDialog.show();

                                }else {
                                    holder.unfav_image.setVisibility(View.VISIBLE);
                                    holder.fav_image.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                });


            }else{

                holder.unfav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.pDialog =  new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                        holder.pDialog.setTitleText("Ой...");
                        holder.pDialog.setContentText("Пожалуйста авторизуйтесь сначало");
                        holder.pDialog.show();
                    }
                });


            }

        }

    }

    @Override
    public int getItemCount() {
        return latestJobItems.size();
    }


    public static class home_latestjob_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public TextView latestjob_name , latestjob_title , latestjob_type, latestjob_country_name , latestjob_schedule , latestjob_duration;
        public ImageView latestjob_flag  , latestjob_verified , fav_image , unfav_image ,  badge_color , badge_icon;
        public LinearLayout latestjob_layout;
        public RelativeLayout main_layout;
        public LatestJobModel mItem;
        public CardView complete_job;

        public ProgressBar dd_loading;
        SweetAlertDialog pDialog;

        public home_latestjob_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            main_layout = mView.findViewById(R.id.ui_latest_job_layout);
            latestjob_country_name = mView.findViewById(R.id.ui_latest_job_countryname);
            latestjob_name = mView.findViewById(R.id.ui_latest_job_name);
            latestjob_title = mView.findViewById(R.id.ui_latest_job_title);
            latestjob_type = mView.findViewById(R.id.ui_latest_job_level);
            latestjob_schedule = mView.findViewById(R.id.ui_latest_job_timing);
            latestjob_duration = mView.findViewById(R.id.ui_latest_job_duration);
            latestjob_flag = mView.findViewById(R.id.ui_latest_job_flag);
            latestjob_verified = mView.findViewById(R.id.ui_latest_verified);
            complete_job = mView.findViewById(R.id.ui_complete_job_card);
            fav_image = mView.findViewById(R.id.ui_fav_latest_image);
            unfav_image = mView.findViewById(R.id.ui_unfav_lateat_image);
            dd_loading = mView.findViewById(R.id.ui_loading);
            latestjob_layout = mView.findViewById(R.id.ui_main_text_4);
            badge_color = mView.findViewById(R.id.ui_job_badge_color);
            badge_icon = mView.findViewById(R.id.ui_job_badge_icon);

        }
    }


    public void addLatestJObPagination(List<LatestJobModel> job){


        for (LatestJobModel lp : job){

            latestJobItems.add(lp);

        }
        notifyDataSetChanged();

    }
}
