package project.team.cmpe277.com.magicrentals1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
    private ArrayList<GridImageDetailItem> data;

    public GridViewAdapter(Context context,int layoutResourceId,ArrayList<GridImageDetailItem> data){
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
        GridImageDetailItem item = data.get(position);
        Bitmap cacheHit = TenantSearchListFragment.mThumbnailThread.checkCache(item.getImageIcon());
        if(cacheHit != null){
            holder.image.setImageBitmap(cacheHit);
        }else{
            TenantSearchListFragment.mThumbnailThread.queueThumbnail(holder.image,item.getImageIcon());
        }

        for( int i = Math.max(0,position -10); i < Math.min(data.size()-1, position+10); i++){
            TenantSearchListFragment.mThumbnailThread.queuePreload(item.getImageIcon());
        }
        holder.price.setText(item.getPrice());
        holder.address.setText(item.getStreetAddr());
        return row;
    }

    static class ViewHolder{
        TextView address;
        ImageView image;
        TextView price;
    }


}
