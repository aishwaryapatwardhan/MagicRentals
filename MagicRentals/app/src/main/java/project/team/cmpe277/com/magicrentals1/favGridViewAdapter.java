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
 * Created by Raghu on 5/10/2016.
 */
public class favGridViewAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private ArrayList<FavPropertieDetails> data;

    public favGridViewAdapter(Context context,int layoutResourceId){
        super(context,layoutResourceId,FavPropCollections.getFavList());
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = FavPropCollections.getFavList();
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
        FavPropertieDetails item = data.get(position);
        Bitmap cacheHit = TenantsFavListFragment.mThumbnailThread.checkCache(item.getImages());
        if(cacheHit != null){
            holder.image.setImageBitmap(cacheHit);
        }else{
            TenantsFavListFragment.mThumbnailThread.queueThumbnail(holder.image,item.getImages());
        }

        for( int i = Math.max(0,position -10); i < Math.min(data.size()-1, position+10); i++){
            TenantsFavListFragment.mThumbnailThread.queuePreload(item.getImages());
        }
//        holder.image = (ImageView)row.findViewById(R.id.myImageView);

        holder.price.setText(item.getRent().toString());
        holder.address.setText(item.getStreet()+" "+item.getCity()+" "+item.getZip() );
        return row;
    }

    static class ViewHolder{
        TextView address;
        ImageView image;
        TextView price;
    }
}

