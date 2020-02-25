package uz.itmaker.naft.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.FileUtils;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Uri> arrayList;

    public MyAdapter(Context context, ArrayList<Uri> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position){
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (mInflater != null) {
            convertView = mInflater.inflate(R.layout.list_items, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView imagePath = convertView.findViewById(R.id.imagePath);
        ImageView deleteBtn = convertView.findViewById(R.id.ui_del);
        String path =FileUtils.getPath(context, arrayList.get(position));
        assert path != null;
        imagePath.setText(path.substring(path.lastIndexOf("/")+1));

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
