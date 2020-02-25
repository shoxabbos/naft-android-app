package uz.itmaker.naft.Adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import uz.itmaker.naft.Interface.onListInteractionListnerWorketo;
import uz.itmaker.naft.Model.Latestjob.Attanchent;
import uz.itmaker.naft.R;
import java.io.File;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


public class LatestJobAttachmentAdapter extends  RecyclerView.Adapter<LatestJobAttachmentAdapter.attachment_ViewHolder> {
    private onListInteractionListnerWorketo mListener;
    List<Attanchent> AttachmentItems;
    private Context context;

    public LatestJobAttachmentAdapter (List<Attanchent> _topcategoryItems  , onListInteractionListnerWorketo _context , Context context){
        AttachmentItems =  _topcategoryItems;
        mListener = _context;
        this.context = context;

    }

    @NonNull
    @Override
    public attachment_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_latest_job_attachments, viewGroup , false);
        LatestJobAttachmentAdapter.attachment_ViewHolder freelancerViewHolder = new LatestJobAttachmentAdapter.attachment_ViewHolder(viewItem);
        return freelancerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final attachment_ViewHolder holder, final int i) {

        holder.mItem = AttachmentItems.get(i);
        holder.attatchment_title.setText(Html.fromHtml(AttachmentItems.get(i).getDocumentName()).toString());
//        holder.attatchment_value.setText(Integer.toString(AttachmentItems.get(i).getFileSize()));

        holder.attachment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(AttachmentItems.get(i).getUrl());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(context, DIRECTORY_DOWNLOADS,
                        File.separator + "Worktern Files" + File.separator + AttachmentItems.get(i).getDocumentName());
                Long reference = downloadManager.enqueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return AttachmentItems.size();
    }

    public static class attachment_ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public TextView attatchment_title , attatchment_value;
        public LinearLayout attachment_view;
        public Attanchent mItem;

        public attachment_ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            attatchment_title = mView.findViewById(R.id.ui_attachment_name);
            attatchment_value = mView.findViewById(R.id.ui_attachment_size);
            attachment_view = mView.findViewById(R.id.ui_complete_attachment);



        }
    }
}
