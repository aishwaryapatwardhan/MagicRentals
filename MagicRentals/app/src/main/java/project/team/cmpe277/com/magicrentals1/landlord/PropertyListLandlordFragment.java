package project.team.cmpe277.com.magicrentals1.landlord;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.LoginActivity;
import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.utility.ThumbnailDownloader;

//import android.app.ListFragment;

/**
 * Created by savani on 4/26/16.
 */
public class PropertyListLandlordFragment extends ListFragment  {
    private static final String TAG = "ListLandlordFragment";
    PropertyListAdapter madapter ;
    ProgressBar progress;
    ListView listView;
    ArrayList<PropertyModel> mPropertyList;
    PropertiesResultLab mPropertyResultLab;
    PropertyListAdapter mAdapter;
    String userid;
    Activity activity;
    public Callbacks mCallbacks;
    private Boolean rentedOk = false;
    private Boolean cancelOk = false;
    ArrayList<Integer> selected_line_al = new ArrayList<>();
    int selectedLine = -1;

    //  private static final String TAG = "ListFragmentL";
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
        View v = inflater.inflate(R.layout.landlord_properties_fragment, container, false);
        setHasOptionsMenu(true);
        progress = (ProgressBar) v.findViewById(R.id.progressbar);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_landlord,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER",Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString(LoginActivity.USERID,null);
        mPropertyList = mPropertyResultLab.getPropertyList();
        mPropertyResultLab.getPropertyList().clear();
        listView = getListView();
        // mPropertyResultLab.getPropertyList().clear();
        madapter = new PropertyListAdapter
                (getContext(), R.layout.landlord_property_row, mPropertyList);
        // mPropertyListAdapter.notifyDataSetChanged();
        //   listView.setAdapter(madapter);
        System.out.println("adapter ========"+madapter);
        System.out.println("USERID......... "+userid);

        new PropertiesListAsyncTask(userid,this, listView, progress, madapter).execute(userid);
        mPropertyResultLab.getPropertyList().clear();
        //   mPropertyList = mPropertyResultLab.getPropertyList();
        if(madapter == null){
            Log.i(TAG, "null adapter");
        }
       // listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
       // listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        // listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                System.out.println("Selected item .... " + position+"id  .. "+id);
                if(checked){
                    if(selectedLine == -1) selectedLine = (int) id;
                    else {
                          int temp = selectedLine;
                          selectedLine = (int) id;
                        listView.setItemChecked(temp, false);

                    }

                }else{
                    if(selectedLine != -1)  selectedLine = -1;
                }
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
                return true;
            }



            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("mListView  called........ "+position+"id === "+id+"view... "+view
                        +"size === "+mPropertyList.size());
                ;
                // PropertyModel prop = (PropertyModel) parent.getItemAtPosition(position);
                PropertyModel prop = new PropertyModel();

                prop = mPropertyList.get((int)id);

                // System.out.println("KEy" +prop.getKey();


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


    }

    public interface Callbacks{
        void onPropertyClicked(PropertyModel property);
        ArrayList<PropertyModel> onRentedClicked(int selected_line,
                                                 PropertyListAdapter adapter);
        void onCancelClicked(int selected_line, PropertyListAdapter adapter);
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
    public void get( ArrayList<PropertyModel>  props){
        mPropertyList = props;
    }

    @Override
    public void onResume() {
        super.onResume();
        madapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case  R.id.add_property:

                Log.i("ADDPROPERty","calling activity");
                Intent g = new Intent(getActivity().getApplicationContext(), UploadPropertyDataActivity.class);
                startActivity(g);
        }

        return super.onOptionsItemSelected(item);
    }
}








