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
import uz.itmaker.naft.Model.Provider.Award;
import uz.itmaker.naft.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class providerAwardceAdapter extends RecyclerView.Adapter<providerAwardceAdapter.awards_ViewHolder>  {

    private onListInteractionListnerWorketo mListener;
    List<Award> AwardItems;

    public providerAwardceAdapter (List<Award> _topcategoryItems  , onListInteractionListnerWorketo _context){
        AwardItems =  _topcategoryItems;
        mListener = _context;

    }

    @NonNull
    @Override
    public awards_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_freelancer_detail_award, viewGroup , false);
        providerAwardceAdapter.awards_ViewHolder freelancerViewHolder = new providerAwardceAdapter.awards_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull awards_ViewHolder holder, int i) {
        holder.mItem = AwardItems.get(i);
        holder.award_date.setText(Html.fromHtml(AwardItems.get(i).getDate()).toString());
        holder.award_date.setText(Html.fromHtml(AwardItems.get(i).getTitle()).toString());

        if (!AwardItems.get(i).getImage().getUrl().equals("")){
            Picasso.get().load(AwardItems.get(i).getImage().getUrl()).into(holder.award_image);
        }
    }

    @Override
    public int getItemCount() {
        return AwardItems.size();
    }

    public static class awards_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public TextView award_title , award_date;
        public ImageView award_image;
        public Award mItem;

        public awards_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            award_title = mView.findViewById(R.id.ui_award_title);
            award_date = mView.findViewById(R.id.ui_award_date);
            award_image = mView.findViewById(R.id.ui_award_image);



        }
    }
}
