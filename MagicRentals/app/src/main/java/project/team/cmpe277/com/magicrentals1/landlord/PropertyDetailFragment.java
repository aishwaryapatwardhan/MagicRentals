package project.team.cmpe277.com.magicrentals1.landlord;

import android.annotation.TargetApi;
//import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.R;

/**
 * Created by savani on 5/1/16.
 */
public class PropertyDetailFragment extends Fragment {
    private ArrayList<PropertyModel> mPropertyList ;
    PropertyModel mProperty;
    String propertyKey;
    PropertiesResultLab mpropertiesResultLab;
    private ImageView propertyImage;
    private TextView viewcountV;
    private TextView addressV;
    private TextView streetV;
    private TextView cityV;
    private TextView stateV;
    private TextView rentV;
    private TextView areaV;
    private TextView roomsV;
    private TextView bathsV;
    private TextView descV;
    private TextView statusV;
    private static final String TAG = "PropertyDetailFragment";

    public static final String PROPERTY_KEY = "Property_key";
    public PropertyDetailFragment() {

    }

//

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);
        propertyKey = getArguments().getString(PROPERTY_KEY);
        mpropertiesResultLab = PropertiesResultLab.getPropertiesResultLab(getContext());
        mPropertyList = mpropertiesResultLab.getPropertyList();

        for(PropertyModel property : mPropertyList){
            if(property.getKey().equals(propertyKey)){
                System.out.println(property.getKey());
                mProperty = property;
                break;
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Bundle args = new Bundle();
       // args.
        View view = inflater.inflate(R.layout.landlord_property_detail_fragment, container, false);
      //  String userid = getArguments().getString("USERID");
      //  System.out.println("userid  ... " +userid);
      //  propertyImage = (ImageView)view.findViewById(R.id.house_image);
        viewcountV = (TextView)view.findViewById(R.id.view_count_detail);
        addressV = (TextView)view.findViewById(R.id.address_detail);
        streetV = (TextView)view.findViewById(R.id.street_detail);
        cityV = (TextView)view.findViewById(R.id.city_detail);
        stateV = (TextView)view.findViewById(R.id.street_detail);
        rentV = (TextView)view.findViewById(R.id.rent_detail);
        areaV = (TextView)view.findViewById(R.id.area_detail);
        roomsV = (TextView)view.findViewById(R.id.bedroom_detail);
        bathsV = (TextView)view.findViewById(R.id.baths_detail);
        descV = (TextView)view.findViewById(R.id.desc_detail);
        statusV = (TextView)view.findViewById(R.id.status_det);

        if(mProperty != null){
            //set image Sai....
            System.out.println("view count ......  "+mProperty.getView_count());
            viewcountV.setText(mProperty.getView_count());
          //  addressV.setText(mProperty.); CHECK
            streetV.setText(mProperty.getStreet());
            cityV.setText(mProperty.getCity());
            stateV.setText(mProperty.getState());
            rentV.setText(mProperty.getRent());
            areaV.setText(mProperty.getArea());
            roomsV.setText(mProperty.getRoom());
            bathsV.setText(mProperty.getBath());
            descV.setText(mProperty.getDescription());
            Log.i(TAG, mProperty.getStatus());
            statusV.setText(mProperty.getStatus());
        }

        return view;

    }
    public static PropertyDetailFragment newInstance(String propKey){
        Bundle args = new Bundle();
        args.putString(PROPERTY_KEY, propKey);

        PropertyDetailFragment propertyDetailFragment = new PropertyDetailFragment();
        propertyDetailFragment.setArguments(args);

        return propertyDetailFragment;
    }

}