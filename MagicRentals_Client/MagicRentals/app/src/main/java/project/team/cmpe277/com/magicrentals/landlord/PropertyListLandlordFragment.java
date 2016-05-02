package project.team.cmpe277.com.magicrentals.landlord;

import android.annotation.TargetApi;
//import android.app.ListFragment;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals.R;

/**
 * Created by savani on 4/26/16.
 */
public class PropertyListLandlordFragment extends ListFragment {
    private static final String TAG = "PropertyListLandlordFragment";
   // PropertyListAdapter adapter ;
    SimpleCursorAdapter mAdapter;
    ProgressBar progress;
    ListView listView;
    ArrayList<PropertyModel> mPropertyList ;
    PropertiesResultLab mPropertyResultLab;
    String userid;
    Activity activity;

    public PropertyListLandlordFragment() {

    }
    public static PropertyListLandlordFragment getFragment(String userid) {
        Bundle args = new Bundle();
        PropertyListLandlordFragment fragment = new PropertyListLandlordFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        System.out.println("onAttach called");
        this.activity = activity;
    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.landlord_properties_fragment, container, false);
//
//        progress = (ProgressBar) listView1.findViewById(R.id.progressBar);
//
//       mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(getContext());
////
//        mPropertyList = mPropertyResultLab.getPropertyList();

//        PropertyListAdapter mPropertyListAdapter = new PropertyListAdapter
//                (getActivity(),R.layout.landlord_property_row, mPropertyList );
//        if (activity != null) {
////            PropertyListAdapter mPropertyListAdapter = new PropertyListAdapter
////                    (activity.getApplicationContext(), R.layout.landlord_property_row, mPropertyList );
////            mPropertyListAdapter.notifyDataSetChanged();
////            listView.setAdapter(mPropertyListAdapter);
//
//        }
//


        // return searchView;


        // Create a progress bar to display while the list loads



       // progress = (ProgressBar) listView.findViewById(R.id.progressBar);
//                progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//                        Toolbar.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
//                progressBar.setIndeterminate(true);
//                getListView().setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
      //  ViewGroup root = (ViewGroup) listView1.findViewById(android.R.id.content);
      //  root.addView(progress);

     //   return listView1;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
       mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(getContext());
//
        mPropertyList = mPropertyResultLab.getPropertyList();



        PropertyListAdapter mPropertyListAdapter = new PropertyListAdapter
                (activity, R.layout.landlord_property_row, mPropertyList );
        mPropertyListAdapter.notifyDataSetChanged();
        setListAdapter(mPropertyListAdapter);

    }





}
