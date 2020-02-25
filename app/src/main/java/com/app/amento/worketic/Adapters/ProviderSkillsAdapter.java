package uz.itmaker.naft.Adapters;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Provider.Review;
import uz.itmaker.naft.Model.Provider.Skill;
import uz.itmaker.naft.R;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;

import java.nio.channels.SelectionKey;
import java.util.List;

public class ProviderSkillsAdapter extends RecyclerView.Adapter<ProviderSkillsAdapter.skills_ViewHolder> {

    private onListInteractionListnerWorketo mListener;
    List<Skill> SkillItems;

    public ProviderSkillsAdapter (List<Skill> _topcategoryItems  , onListInteractionListnerWorketo _context){
        SkillItems =  _topcategoryItems;
        mListener = _context;

    }

    @NonNull
    @Override
    public skills_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_freelancer_skill, viewGroup , false);
        ProviderSkillsAdapter.skills_ViewHolder freelancerViewHolder = new ProviderSkillsAdapter.skills_ViewHolder(viewItem);
        return freelancerViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull skills_ViewHolder holder, int i) {
        holder.mItem = SkillItems.get(i);
        holder.skill_name.setText(Html.fromHtml(SkillItems.get(i).getSkillName()).toString());
        holder.skill_value.setText(Html.fromHtml(SkillItems.get(i).getSkillVal()).toString());
        holder.stripedAnimation.setProgress(Integer.parseInt(SkillItems.get(i).getSkillVal()));


    }

    @Override
    public int getItemCount() {
        return SkillItems.size();
    }

    public static class skills_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public TextView skill_name , skill_value;
        public Skill mItem;
        public BootstrapProgressBar stripedAnimation;

        public skills_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            skill_name = mView.findViewById(R.id.ui_skill_name);
            skill_value = mView.findViewById(R.id.ui_skill_value);
            stripedAnimation = mView.findViewById(R.id.progress_striped_animated);
            stripedAnimation.setBackgroundColor(Color.parseColor("#fafafa"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                stripedAnimation.setOutlineAmbientShadowColor(Color.parseColor("#00cc8d"));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                stripedAnimation.setOutlineSpotShadowColor(Color.parseColor("#186F54"));
            }


        }
    }
}
