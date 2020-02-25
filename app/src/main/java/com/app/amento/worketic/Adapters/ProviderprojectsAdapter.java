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
import uz.itmaker.naft.Model.Provider.Project;
import uz.itmaker.naft.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class  ProviderprojectsAdapter extends RecyclerView.Adapter<ProviderprojectsAdapter.projects_ViewHolder> {

    private onListInteractionListnerWorketo mListener;
    List<Project> ProjectItems;

    public ProviderprojectsAdapter (List<Project> _topcategoryItems  , onListInteractionListnerWorketo _context){
        ProjectItems =  _topcategoryItems;
        mListener = _context;

    }

    @NonNull
    @Override
    public projects_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_freelancer_project, viewGroup , false);
        ProviderprojectsAdapter.projects_ViewHolder freelancerViewHolder = new ProviderprojectsAdapter.projects_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull projects_ViewHolder holder, int i) {
        holder.mItem = ProjectItems.get(i);
        holder.project_link.setText(Html.fromHtml(ProjectItems.get(i).getLink()).toString());
        holder.project_title.setText(Html.fromHtml(ProjectItems.get(i).getTitle()).toString());

        if (!ProjectItems.get(i).getImage().getUrl().equals("") ) {
            Picasso.get().load(ProjectItems.get(i).getImage().getUrl()).into(holder.project_image);
        }
        }

    @Override
    public int getItemCount() {
        return ProjectItems.size();
    }

    public static class projects_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public TextView project_title , project_link;
        public ImageView project_image;
        public Project mItem;

        public projects_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            project_title = mView.findViewById(R.id.ui_project_title);
            project_link = mView.findViewById(R.id.ui_project_link);
            project_image = mView.findViewById(R.id.ui_project_image);



        }
    }
}
