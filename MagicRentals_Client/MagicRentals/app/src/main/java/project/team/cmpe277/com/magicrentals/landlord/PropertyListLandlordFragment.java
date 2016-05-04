package project.team.cmpe277.com.magicrentals.landlord;

import android.annotation.TargetApi;
//import android.app.ListFragment;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals.R;

/**
 * Created by savani on 4/26/16.
 */
public class PropertyListLandlordFragment extends ListFragment
{
    private static final String TAG = "PropertyListLandlordFragment";
    // PropertyListAdapter adapter ;
    SimpleCursorAdapter mAdapter;
    ProgressBar progress;
    ListView listView;
    ArrayList<PropertyModel> mPropertyList;
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("onAttach called");
        this.activity = activity;
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // View v = super.onCreateView(inflater, container, savedInstanceState);

        System.out.print("In createViews..");
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

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(getContext());
//
        mPropertyList = mPropertyResultLab.getPropertyList();


        PropertyListAdapter mPropertyListAdapter = new PropertyListAdapter
                (activity, R.layout.landlord_property_row, mPropertyList);
        mPropertyListAdapter.notifyDataSetChanged();
        setListAdapter(mPropertyListAdapter);
      //  listView = (findViewById(R.id.la);
        listView = getListView();

       listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
       // listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                System.out.println("Selected item .... "+position);



            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                System.out.println("inn  menu ........");
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_landlord_context, menu);
                return true;

            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                ArrayList<Integer> selected_line_al = new ArrayList<>();
                PropertyListAdapter pla = (PropertyListAdapter)getListAdapter();
                PropertiesResultLab resultsLab = PropertiesResultLab.
                        getPropertiesResultLab(getActivity());
                System.out.println("data.........   "+resultsLab);
                for(int i = pla.getCount()-1; i > 0 ; i-- ){
                    System.out.print("In chk..");
                    if (getListView().isItemChecked(i)){
                        selected_line_al.add(i);

                        System.out.println("selected line-------- "+resultsLab.getPropertyList().get(i).getNickname());


                    }
                }
                switch (item.getItemId()){
                    case R.id.deleteM:
                        resultsLab.delete(selected_line_al);
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });


    }




}



