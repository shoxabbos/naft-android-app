package uz.itmaker.naft.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Latestjob.Skill;
import uz.itmaker.naft.R;

import java.util.List;

public class LatestJonSkillsAdapter extends RecyclerView.Adapter<LatestJonSkillsAdapter.latest_skills_ViewHolder> {


    private onListInteractionListnerWorketo mListener;
    List<Skill> SkillItems;

    public LatestJonSkillsAdapter (List<Skill> _topcategoryItems  , onListInteractionListnerWorketo _context){
        SkillItems =  _topcategoryItems;
        mListener = _context;

    }

    @NonNull
    @Override
    public latest_skills_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_latest_job_skills, viewGroup , false);
        LatestJonSkillsAdapter.latest_skills_ViewHolder freelancerViewHolder = new LatestJonSkillsAdapter.latest_skills_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull latest_skills_ViewHolder holder, int i) {
        holder.mItem = SkillItems.get(i);
        holder.skill_name.setText(SkillItems.get(i).getSkillName());
    }

    @Override
    public int getItemCount() {
        return SkillItems.size();
    }

    public static class latest_skills_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public TextView skill_name ;
        public Skill mItem;

        public latest_skills_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            skill_name = mView.findViewById(R.id.ui_skill_name_latest);




        }
    }
}
