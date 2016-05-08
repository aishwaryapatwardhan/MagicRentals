package project.team.cmpe277.com.magicrentals1.landlord;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    Activity activity;
    ArrayList<Integer> selected;

    public PropertyListAdapter(Context context, int resource, ArrayList<PropertyModel> propertyList) {

        super(context, resource, propertyList);
        selected = new ArrayList<>();
        activity = (Activity) context;
        mPropertyList = propertyList;


    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater;
            System.out.println("Savani   .......   " + Context.LAYOUT_INFLATER_SERVICE);
            //convertView =
            convertView = activity.getLayoutInflater()
                    .inflate(R.layout.landlord_property_row, null);
        }
        PropertyModel property = getItem(position);
        if(selected.equals(position)){
            convertView.setBackgroundColor(Color.LTGRAY);
        }
        TextView nicknameV = (TextView) convertView.findViewById(R.id.nick_name);
        nicknameV.setText(property.getNickname());

        TextView countV = (TextView) convertView.findViewById(R.id.view_count);
        countV.setText(property.getView_count());

        TextView streetV = (TextView) convertView.findViewById(R.id.house_street);
        streetV.setText(property.getStreet());

        TextView cityV = (TextView) convertView.findViewById(R.id.house_city);
        cityV.setText(property.getCity());

        TextView stateV = (TextView) convertView.findViewById(R.id.house_state);
        stateV.setText(property.getState());

        TextView zipV = (TextView) convertView.findViewById(R.id.house_zip);
        zipV.setText(property.getZip());

        ImageView propertyImage = (ImageView)convertView.findViewById(R.id.house_image);

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

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("inside click detail.......");
//          public void onPropertyClicked(PropertyModel property) {
//              if(findViewById(R.id.detailPropFragmentContainer) == null){
//                  Intent i = new Intent(this,PropertyDetailActivity.class);
//                  i.putExtra(PropertyDetailFragment.PROPERTY_KEY, property.getKey());
//                  startActivity(i);
//              }else{
//                  FragmentManager fm = getSupportFragmentManager();
//                  FragmentTransaction ft = fm.beginTransaction();
//                  Fragment oldDetail = fm.findFragmentById(R.id.detailPropFragmentContainer);
//                  Fragment newDetail = PropertyDetailFragment.newInstance(property.getKey());
//                  if (oldDetail != null) {
//                      ft.remove(oldDetail);
//                  }
//                  ft.add(R.id.detailPropFragmentContainer, newDetail);
//                  ft.commit();
//              }
//          }
//            }
//        });

        return convertView;
    }
    void setSelected(int position){
        selected.add(position);
    }
    void resetSelected(int position){
        selected.remove(position);
    }
//    @Override
//    void notifyDataSetChanged(){
//
//    }




}


