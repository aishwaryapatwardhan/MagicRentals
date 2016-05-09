package project.team.cmpe277.com.magicrentals1.landlord;

import android.annotation.TargetApi;
//import android.app.ListFragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.utility.ThumbnailDownloader;

/**
 * Created by savani on 4/26/16.
 */
public class PropertyListLandlordFragment extends ListFragment {
    private static final String TAG = "PropertyListLandlordFragment";
    // PropertyListAdapter adapter ;
    SimpleCursorAdapter mAdapter;
    ProgressBar progress;
    ListView listView;
    ArrayList<PropertyModel> mPropertyList;
    PropertiesResultLab mPropertyResultLab;
    String userid;
    Activity activity;
    public Callbacks mCallbacks;

    static ThumbnailDownloader<ImageView> mThumbnailThread;






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
        mThumbnailThread = new ThumbnailDownloader<>(new Handler());
        mThumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                if (isVisible()) {
                    imageView.setImageBitmap(thumbnail);
                }
            }
        });
        mThumbnailThread.start();
        mThumbnailThread.getLooper();


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


//        userid = getActivity().getIntent().getExtras().getString("USERID");
 //       System.out.print("In createViews.." +userid);
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
        setListAdapter(mPropertyListAdapter);
        //  listView = (findViewById(R.id.la);
        listView = getListView();
       // listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

       listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
       // listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                System.out.println("Selected item .... " + position);


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
                PropertyListAdapter pla = (PropertyListAdapter) getListAdapter();
                PropertiesResultLab resultsLab = PropertiesResultLab.
                        getPropertiesResultLab(getActivity());
                System.out.println("data.........   " + resultsLab);
                for (int i = pla.getCount() - 1; i >= 0; i--) {
                    System.out.print("In chk..");
                    if (getListView().isItemChecked(i)) {
                        selected_line_al.add(i);

                        System.out.println("selected line-------- " + resultsLab.getPropertyList().get(i).getNickname());


                    }
                }
                switch (item.getItemId()) {
                    case R.id.rentedM:
                        System.out.println("inside rented.......");
                        resultsLab.rented(selected_line_al);

                     //   mAdapter.notifyDatasetChanged();
                        break;
                    case R.id.editM:
                        Bundle bundle = new Bundle();
                        bundle.putInt("selectedLine", selected_line_al.get(0));
                        bundle.putString("USERID",userid);

                        Intent i = new Intent(getActivity(), EditPropertiesActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                        break;
//                        Intent i = new Intent(listView.getApplicationContext(), UploadPropertyDataActivity.class);
//                        i.putExtra("USERID", userid);
//                        startActivity(i);

                        //bundle.putString("USERID", userid);
//                        EditPropertiesFragment fragment = new EditPropertiesFragment();
//                        fragment.setArguments(bundle);
//                        // fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
//                        getActivity().getSupportFragmentManager().beginTransaction().
//                                replace(R.id.fragmentContainer, fragment).commit();
                    case R.id.cancelM:
                        resultsLab.cancel(selected_line_al);
                        break;

                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("mListView  called........ ");
                PropertyModel prop = (PropertyModel) parent.getItemAtPosition(position);
                 mCallbacks.onPropertyClicked(prop);
            }

        });
//        listView.setOnClickListener(new View.OnClickListener(){
////            @Override
////            public void onClick(View v) {
////                MenuInflater inflater = v.getParent();
////                inflater.inflate(R.menu.menu_landlord_context, menu);
////
////            }
//            public boolean onCreateActionMode
//        });


//
//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("inside click detail.......");
//                String selectedValue = (String) getListAdapter().getItem();

//            public void onPropertyClicked(PropertyModel property) {
//                if (findViewById(R.id.detailPropFragmentContainer) == null) {
//                    Intent i = new Intent(this, PropertyDetailActivity.class);
//                    i.putExtra(PropertyDetailFragment.PROPERTY_KEY, property.getKey());
//                    startActivity(i);
//                } else {
//                    FragmentManager fm = getSupportFragmentManager();
//                    FragmentTransaction ft = fm.beginTransaction();
//                    Fragment oldDetail = fm.findFragmentById(R.id.detailPropFragmentContainer);
//                    Fragment newDetail = PropertyDetailFragment.newInstance(property.getKey());
//                    if (oldDetail != null) {
//                        ft.remove(oldDetail);
//                    }
//                    ft.add(R.id.detailPropFragmentContainer, newDetail);
//                    ft.commit();
//                }
//            }
//        }
//        });

    }

    public interface Callbacks{
        void onPropertyClicked(PropertyModel property);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

}








