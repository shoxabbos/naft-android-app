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
import android.widget.TextView;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.zip.GZIPInputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFreelancerAdapter extends RecyclerView.Adapter<HomeFreelancerAdapter.home_freelancer_ViewHolder>  {

    private onListInteractionListnerWorketo mListener;
    List<ProviderModel> freelancerItems;
    Context context;

    public HomeFreelancerAdapter (List<ProviderModel> _topcategoryItems  , onListInteractionListnerWorketo _context , Context context){
        freelancerItems =  _topcategoryItems;
        mListener = _context;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeFreelancerAdapter.home_freelancer_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_home_featured_freelancer, viewGroup , false);
        HomeFreelancerAdapter.home_freelancer_ViewHolder freelancerViewHolder = new HomeFreelancerAdapter.home_freelancer_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeFreelancerAdapter.home_freelancer_ViewHolder holder, final int position){
        holder.mItem = freelancerItems.get(position);
        holder.freelancer_name.setText(Html.fromHtml(freelancerItems.get(position).getName()).toString());
        if (freelancerItems.get(position).getTagLine().equals("")){
            holder.freelancer_tagline.setVisibility(View.GONE);
        }else {
            holder.freelancer_tagline.setText(Html.fromHtml(freelancerItems.get(position).getTagLine()).toString());
        }

        holder.freelancer_price.setText(Html.fromHtml(freelancerItems.get(position).getPerhourRate() + "/hr").toString());
        holder.freelancer_country_name.setText(Html.fromHtml(freelancerItems.get(position).getLocation().getCountry()).toString());

        Picasso.get().load(freelancerItems.get(position).getProfileImg()).into(holder.freelancer_image);
        if(!freelancerItems.get(position).getLocation().getFlag().equals("")){
            Picasso.get().load(freelancerItems.get(position).getLocation().getFlag()).into(holder.freelancer_flag);
        }

        if (freelancerItems.get(position).getBadge().getBadgetColor().equals("")){
            holder.free_feature_image.setVisibility(View.GONE);
            holder.free_featured_badge.setVisibility(View.GONE);
            holder.home_freelancer_card.setCardBackgroundColor(Color.parseColor("#ffffff"));
        }else  {
            holder.free_feature_image.setVisibility(View.VISIBLE);
            holder.free_featured_badge.setVisibility(View.VISIBLE);
            holder.home_freelancer_card.setCardBackgroundColor(Color.parseColor("#fffdf3"));
        }


        if (freelancerItems.get(position).getIsVerified().equals("yes")){

            holder.freelancer_verified.setVisibility(View.VISIBLE);

        }else {
            holder.freelancer_verified.setVisibility(View.GONE);
        }

        holder.freelancer_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (null != mListener) {
                    mListener.onAllProviderListInteraction(holder.mItem);
                }
            }
        });

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(context, Constants.ISUSERLOGGEDIN);

        if (freelancerItems.get(position).getFavorit().equals("yes")){
            holder.freelancer_unfav.setVisibility(View.GONE);
            holder.freelancer_fav.setVisibility(View.VISIBLE);
            holder.freelancer_fav.setClickable(false);

        }else{
            holder.freelancer_fav.setVisibility(View.GONE);
            holder.freelancer_unfav.setVisibility(View.VISIBLE);
            if (isUserLoggedIn){
            holder.freelancer_unfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        holder.dd_loading.setVisibility(View.VISIBLE);
                        String id = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();

                        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().UpdateFavourite(id , freelancerItems.get(position).getUserId() , "saved_freelancer");
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code() == 200){

                                    holder.freelancer_unfav.setVisibility(View.GONE);
                                    holder.freelancer_fav.setVisibility(View.VISIBLE);
                                    holder.dd_loading.setVisibility(View.GONE);

                                }else if(response.code() == 203) {
                                    holder.dd_loading.setVisibility(View.GONE);
                                    holder.freelancer_unfav.setVisibility(View.VISIBLE);
                                    holder.freelancer_fav.setVisibility(View.GONE);
                                    holder.pDialog =  new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                                    holder.pDialog.setTitleText("Ой...");
                                    holder.pDialog.setContentText("Sorry, \n You are not authorized to perform this action.");
                                    holder.pDialog.show();

                                }else {
                                    holder.freelancer_unfav.setVisibility(View.VISIBLE);
                                    holder.freelancer_fav.setVisibility(View.GONE);
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

                holder.freelancer_unfav.setOnClickListener(new View.OnClickListener() {
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
        return freelancerItems.size();
    }

    public static class home_freelancer_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public TextView freelancer_name , freelancer_tagline , freelancer_price , freelancer_country_name;
        public ImageView freelancer_image , freelancer_flag , freelancer_verified , freelancer_fav , freelancer_unfav ,
                free_feature_image , free_featured_badge;
        public LinearLayout freelancer_layout;
        public ProviderModel mItem;
        public ProgressBar dd_loading;
        public CardView home_freelancer_card;
        SweetAlertDialog pDialog;

        public home_freelancer_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            freelancer_layout = mView.findViewById(R.id.ui_layout_freelancer);
            freelancer_name = mView.findViewById(R.id.ui_freelancer_main_name);
            freelancer_tagline = mView.findViewById(R.id.ui_freelancer_main_tagline);
            freelancer_price = mView.findViewById(R.id.ui_freelancer_main_price);
            freelancer_country_name = mView.findViewById(R.id.ui_freelancer_main_countery_name);
            freelancer_image = mView.findViewById(R.id.ui_freelancer_main_profile_image);
            freelancer_flag = mView.findViewById(R.id.ui_freelancer_main_profile_flag);
            freelancer_verified = mView.findViewById(R.id.ui_freelancer_main_verified_image);
            home_freelancer_card = mView.findViewById(R.id.ui_freelancer_card);
            freelancer_fav = mView.findViewById(R.id.ui_fav_image);
            freelancer_unfav = mView.findViewById(R.id.ui_unfav_image);
            dd_loading = mView.findViewById(R.id.ui_bookloading);
            free_feature_image = mView.findViewById(R.id.featured_image);
            free_featured_badge = mView.findViewById(R.id.featured_badge);
        }
    }

    public void addLatestProviderPagination(List<ProviderModel> freelancer){


        for (ProviderModel lp : freelancer){
            freelancerItems.add(lp);
        }
        notifyDataSetChanged();

    }

}
