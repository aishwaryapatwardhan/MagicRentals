package project.team.cmpe277.com.magicrentals1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rekha on 4/29/2016.
 */
public class GridViewAdapter extends ArrayAdapter{

    private Context context;
    private int layoutResourceId;
    private ArrayList<GridImageItem> data;

    public GridViewAdapter(Context context,int layoutResourceId,ArrayList<GridImageItem> data){
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View row = convertView;
        ViewHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);
            holder = new ViewHolder();
            holder.address = (TextView)row.findViewById(R.id.myImageViewAddress);
            holder.image = (ImageView)row.findViewById(R.id.myImageView);
            holder.price = (TextView)row.findViewById(R.id.myImageViewPrice);
            row.setTag(holder);
        }else {
            holder = (ViewHolder)row.getTag();
        }
        GridImageItem item = data.get(position);

        TenantSearchListFragment.mThumbnailThread.queueThumbnail(holder.image,item.getImage());
        //holder.image.setImageBitmap(item.getImage());
        holder.price.setText(item.getPrice());
        holder.address.setText(item.getAddress());
        return row;
    }

    static class ViewHolder{
        TextView address;
        ImageView image;
        TextView price;
    }


}
