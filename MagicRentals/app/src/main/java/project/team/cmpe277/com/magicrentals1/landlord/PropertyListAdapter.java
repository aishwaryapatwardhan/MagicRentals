package project.team.cmpe277.com.magicrentals1.landlord;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import project.team.cmpe277.com.magicrentals1.R;

/**
 * Created by savani on 4/26/16.
 */
public class PropertyListAdapter extends ArrayAdapter<PropertyModel> {
    ArrayList<PropertyModel> mPropertyList;
    HashMap<PropertyModel, Boolean> mPropertyModel;
    int resource;
    Context context;
    LayoutInflater inflater;

    ArrayList<Integer> selected;
    LayoutInflater view;
    private static final String TAG = "ADAPTER";

    public PropertyListAdapter(Context context, int resource, ArrayList<PropertyModel> propertyList) {
       super(context, resource, propertyList);
        this.context = context;
        //this.context = context.getApplicationContext();
        this.resource = resource;
        selected = new ArrayList<>();
        mPropertyList = propertyList;
        if(mPropertyList!=null){
            view = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);}
        else Toast.makeText(context, "No Results to Show", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
            vh = new ViewHolder();
            vh.nicknameV = (TextView) convertView.findViewById(R.id.nick_name);
            vh.countV = (TextView) convertView.findViewById(R.id.view_count);
            vh.streetV = (TextView) convertView.findViewById(R.id.house_street);
        //    vh.cityV = (TextView) convertView.findViewById(R.id.house_city);
         //   vh.stateV = (TextView) convertView.findViewById(R.id.house_state);
        //    vh.zipV = (TextView) convertView.findViewById(R.id.house_zip);
            vh.addressFullV = (TextView) convertView.findViewById(R.id.address_line);
            vh.statusV= (TextView) convertView.findViewById(R.id.statuscurr);
            convertView.setTag(vh);

        }else{
            vh  = (ViewHolder) convertView.getTag();

        }

        PropertyModel property = getItem(position);
        System.out.println("ID "+ property.getKey());
        String temp_view = property.getView_count();
        if (temp_view == null || temp_view.equals("null")) temp_view = "0";
        vh.countV.setText(temp_view);
        vh.streetV.setText(property.getStreet());
//        vh.stateV.setText(property.getState());
//        vh.cityV.setText(property.getCity());
//        vh.zipV.setText(property.getZip());
        vh.addressFullV.setText(property.getState()+" "+property.getCity()+" "+property.getZip());
        vh.statusV.setText(property.getStatus());

//            //convertView =
//            convertView = activity.getLayoutInflater()
//                    .inflate( R.layout.landlord_property_row, null);

            //        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//
//    /* adapt the image to the size of the display */
//            Display display = convertView.getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
//                    convertView.getResources(),R.mipmap.house),size.x,size.y,true);

//    /* fill the background ImageView with the resized image */
//        ImageView iv_background = (ImageView) findViewById(R.id.houselistimg);
//        if(bmp == null) Log.i(TAG, "bmp is not null");
//        else
//        {   Log.i(TAG, "bmp---- "+bmp);
//            iv_background.setImageBitmap(bmp); }
            ////******////



       //     convertView = inflater.inflate(resource, parent, false);
            LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.linearlayoutrow);

//            ViewTreeObserver viewTreeObserver = convertView.getViewTreeObserver();
//            if (viewTreeObserver.isAlive()) {
//                final View finalConvertView = convertView;
//                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        finalConvertView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        int viewWidth = finalConvertView.getWidth();
//                        int viewHeight = finalConvertView.getHeight();
//                        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.linearlayoutrow);
//                        Drawable background = linearLayout.getBackground();
//                        background.
//                    }
//                });
//            }
            //Bitmap bmImg = BitmapFactory.decodeResource(convertView.getResources(), R.mipmap.ic_imagehouse);
            //BitmapDrawable background = new BitmapDrawable(bmImg);
           // Drawable background = convertView.getResources().getDrawable(R.mipmap.ic_imagehouse);
         //   background.setAlpha(110);
           // linearLayout.setBackgroundDrawable(background);



        if(selected.equals(position)){
            convertView.setBackgroundColor(Color.LTGRAY);
        }





       // ImageView propertyImage = (ImageView)convertView.findViewById(R.id.house_image);


        //following is the code to do preloading and caching of images
        /*Bitmap cacheHit = PropertyListLandlordFragment.mThumbnailThread.checkCache(property.getImages());
        if(cacheHit != null){
            propertyImage.setImageBitmap(cacheHit);
        }else{
            PropertyListLandlordFragment.mThumbnailThread.queueThumbnail(propertyImage,property.getImages());
        }

        for( int i = Math.max(0,position -10); i < Math.min(mPropertyList.size()-1, position+10); i++){
            PropertyListLandlordFragment.mThumbnailThread.queuePreload(property.getImages());
        }*/


        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        System.out.println("Notified....");
        super.notifyDataSetChanged();
    }


    void setSelected(int position){
        selected.add(position);
    }
    void resetSelected(int position){
        selected.remove(position);
    }

    class ViewHolder {
        TextView nicknameV;
        TextView countV;
        TextView streetV;
        TextView cityV;
        TextView stateV;
        TextView zipV;
        TextView statusV;
        TextView addressFullV;

}}


