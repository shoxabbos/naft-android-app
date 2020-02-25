package uz.itmaker.naft.Adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Category;
import uz.itmaker.naft.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TopCatAdpater  extends RecyclerView.Adapter<TopCatAdpater.top_category_ViewHolder> {


    private onListInteractionListnerWorketo mListener;

    List<Category> TopCategoryItems;

    public TopCatAdpater (List<Category> _topcategoryItems  , onListInteractionListnerWorketo _context){
        TopCategoryItems =  _topcategoryItems;
        mListener = _context;

    }

    @Override
    public top_category_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home_categories, parent , false);
        top_category_ViewHolder topCategoryViewHolder = new top_category_ViewHolder(viewItem);
        return topCategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(final top_category_ViewHolder holder, int position) {
        holder.mItem = TopCategoryItems.get(position);
       holder.top_categoty_title.setText(Html.fromHtml(TopCategoryItems.get(position).getName()).toString());
        Picasso.get().load(TopCategoryItems.get(position).getImage()).into(holder.top_category_image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener){

                    mListener.onCategoryListInteraction(holder.mItem);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return TopCategoryItems.size();
    }
    public static class top_category_ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
//
//        @BindView(R.id.ui_cat_card)
//        CardView top_category_card_view;

//        @BindView(R.id.ui_cat_image)
//        ImageView top_category_image;
//
//        @BindView(R.id.ui_cat_text)
//        TextView top_categoty_title;

        public final TextView top_categoty_title;
        public final ImageView top_category_image;
        public CardView card;

        public Category mItem;


        public top_category_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
//            ButterKnife.bind(this , itemView);
            top_categoty_title = (TextView) itemView.findViewById(R.id.ui_cat_text);
            top_category_image =  itemView.findViewById(R.id.ui_cat_image);
            card = itemView.findViewById(R.id.ui_cat_card);
        }
    }
}
