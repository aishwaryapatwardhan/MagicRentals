package project.team.cmpe277.com.magicrentals1.landlord;

import android.annotation.TargetApi;
//import android.app.Fragment;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private ArrayList<PropertyModel> mPropertyList;
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
    private TextView extradetV;
    private static final String TAG = "PropertyDetailFragment";

    public static final String PROPERTY_KEY = "Property_key";
    public DetailCallbacks mCallbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (DetailCallbacks) context;
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

        for (PropertyModel property : mPropertyList) {
            if (property.getKey().equals(propertyKey)) {
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
        viewcountV = (TextView) view.findViewById(R.id.view_count_detail);
        addressV = (TextView) view.findViewById(R.id.address_detail);
        streetV = (TextView) view.findViewById(R.id.street_detail);
        cityV = (TextView) view.findViewById(R.id.city_detail);
        stateV = (TextView) view.findViewById(R.id.street_detail);
        rentV = (TextView) view.findViewById(R.id.rent_detail);
        areaV = (TextView) view.findViewById(R.id.area_detail);
        roomsV = (TextView) view.findViewById(R.id.bedroom_detail);
        bathsV = (TextView) view.findViewById(R.id.baths_detail);
        descV = (TextView) view.findViewById(R.id.desc_detail);
        statusV = (TextView) view.findViewById(R.id.status_det);
        extradetV = (TextView) view.findViewById(R.id.extra_det);

        if (mProperty != null) {
            //set image Sai....
            System.out.println("view count ......  " + mProperty.getView_count());
            String tempCount = mProperty.getView_count().toString();
            if (tempCount.equals("0")) tempCount = "No";
            viewcountV.setText(tempCount + " Views");
            //  addressV.setText(mProperty.); CHECK
            streetV.setText(mProperty.getStreet());
            cityV.setText(mProperty.getCity());
            stateV.setText(mProperty.getState());
            rentV.setText(mProperty.getRent() + "  USD");
            areaV.setText(mProperty.getArea() + "  Sq Ft");
            roomsV.setText(mProperty.getRoom() + " Rooms");
            bathsV.setText(mProperty.getBath() + "  Baths");
            descV.setText(mProperty.getDescription());
            Log.i(TAG, mProperty.getStatus());
            statusV.setText(mProperty.getStatus());
            statusV.setText(mProperty.getOther_details());

        }
        return view;

    }

    public static PropertyDetailFragment newInstance(String propKey) {
        Bundle args = new Bundle();
        args.putString(PROPERTY_KEY, propKey);

        PropertyDetailFragment propertyDetailFragment = new PropertyDetailFragment();
        propertyDetailFragment.setArguments(args);

        return propertyDetailFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_landlord_context, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.add_property:

                Log.i("ADDPROPERty", "calling activity");
                Intent g = new Intent(getActivity().getApplicationContext(), UploadPropertyDataActivity.class);
                startActivity(g);
                break;

            case R.id.rentedM:

                //mProperty
                if (mProperty.getStatus().equals("Created")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(R.string.alertrented)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //
                                    Log.i(TAG, "Inisde OK  "+mProperty.getZip());
                                     mCallbacks.onRentedClicked(mProperty);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.i(TAG, "Inisde Cancel");

                                }
                            });
                    builder.setCancelable(true);
                    builder.create();
                    builder.show();
                    Log.i(TAG, "after button alert.. ");
                } else if (mProperty.getStatus().equals("Rented")) {
                    android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder
                            (getContext()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Property is already rented.");
                    alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }


                //   mAdapter.notifyDatasetChanged();
                break;
            case R.id.editM:
                Bundle bundle = new Bundle();
                bundle.putString("key", mProperty.getKey() );
                Intent i = new Intent(getActivity(), EditPropertiesActivity.class);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.cancelM:
                //resultsLab.cancel(selected_line_al);
                AlertDialog.Builder buildercan = new AlertDialog.Builder(this.getContext());
                buildercan.setMessage(R.string.alertcancel)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("CANCEL","has been called");
                                mCallbacks.onCancelClicked(mProperty);

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish();
                            }
                        });
                buildercan.setCancelable(true);
                buildercan.create();
                buildercan.show();

                break;
        }
        return super.onOptionsItemSelected(item);

    }
  public  interface DetailCallbacks{

        void onRentedClicked(PropertyModel currModel);
        void onCancelClicked(PropertyModel currModel);
    }

}
