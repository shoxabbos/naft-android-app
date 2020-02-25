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
import uz.itmaker.naft.Model.Provider.Review;
import uz.itmaker.naft.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ProviderReviewAdapter extends RecyclerView.Adapter<ProviderReviewAdapter.review_ViewHolder> {

    private onListInteractionListnerWorketo mListener;
    List<Review> ReviewItems;

    public ProviderReviewAdapter (List<Review> _topcategoryItems  , onListInteractionListnerWorketo _context){
        ReviewItems =  _topcategoryItems;
        mListener = _context;

    }

    @NonNull
    @Override
    public review_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_freelancer_review, viewGroup , false);
        ProviderReviewAdapter.review_ViewHolder freelancerViewHolder = new ProviderReviewAdapter.review_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull review_ViewHolder holder, int i) {

        holder.mItem = ReviewItems.get(i);
        holder.wt_review_name.setText(Html.fromHtml(ReviewItems.get(i).getEmployerName()).toString());
        holder.wt_review_title.setText(Html.fromHtml(ReviewItems.get(i).getProjectTitle()).toString());
        holder.wt_review_location.setText(Html.fromHtml(ReviewItems.get(i).getProjectLocation()).toString());
        holder.wt_review_rating.setText(ReviewItems.get(i).getProjectRating() + "/5");
        holder.wt_review_level_title.setText(Html.fromHtml(ReviewItems.get(i).getLevelTitle()).toString());
        holder.wt_review_post_date.setText(Html.fromHtml(ReviewItems.get(i).getPostDate()).toString());
        holder.wt_review_content.setText(Html.fromHtml(ReviewItems.get(i).getReviewContent()).toString());


        if (!ReviewItems.get(i).getEmployerImage().equals("") ) {
            Picasso.get().load(ReviewItems.get(i).getEmployerImage()).into(holder.wt_review_imageview);
        }





    }

    @Override
    public int getItemCount() {
        return ReviewItems.size();
    }

    public static class review_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public TextView wt_review_name , wt_review_title , wt_review_content ,  wt_review_location , wt_review_rating , wt_review_post_date , wt_review_level_title;
        public ImageView wt_review_imageview;
        public Review mItem;

        public review_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            wt_review_content = mView.findViewById(R.id.ui_review_content);
            wt_review_name = mView.findViewById(R.id.ui_review_name);
            wt_review_title = mView.findViewById(R.id.ui_review_title);
            wt_review_level_title = mView.findViewById(R.id.ui_review_level);
            wt_review_location = mView.findViewById(R.id.ui_review_location);
            wt_review_post_date = mView.findViewById(R.id.ui_review_date);
            wt_review_rating = mView.findViewById(R.id.ui_review_rating);
            wt_review_imageview = mView.findViewById(R.id.ui_review_image);


        }
    }

}
