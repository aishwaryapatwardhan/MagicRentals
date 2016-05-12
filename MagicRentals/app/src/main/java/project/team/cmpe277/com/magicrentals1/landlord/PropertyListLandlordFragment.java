package project.team.cmpe277.com.magicrentals1.landlord;

import android.annotation.TargetApi;
//import android.app.ListFragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.LoginActivity;
import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;
import project.team.cmpe277.com.magicrentals1.utility.ThumbnailDownloader;

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
        progress = (ProgressBar) v.findViewById(R.id.progressbar);

        return v;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(getContext());
        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences
                                                        (TAG, Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID,null);
        mPropertyResultLab.getPropertyList().clear();
        System.out.println("USERID......... "+userid);

        //temp changes to pass UID
//        if(userid.isEmpty()){
            userid = "Rekha";
  //      }
//


//        HashMap<String, String> hm= new HashMap<>();
//        hm.put("user_id","savani");
////
//        String url = "http://192.168.1.173:3000/getPostsByUser";
//        //    new MultipartUtilityAsyncTask(hm, null).execute(url);
//        new MultipartUtilityAsyncTask(this.getContext(), hm, null).execute(url);
        listView = getListView();
        new PropertiesListAsyncTask("savani",this, listView, progress).execute(userid);
        mPropertyList = mPropertyResultLab.getPropertyList();
        madapter = (PropertyListAdapter) getListAdapter();

        if(madapter == null){
            Log.i(TAG, "null adapter");
        }
        madapter = (PropertyListAdapter) this.getListAdapter();
        if(madapter == null){
            Log.i(TAG, "null adapter");
        }
      //test
//         madapter = new PropertyListAdapter
//                (getContext(), R.layout.landlord_property_row, mPropertyList);
//          mPropertyListAdapter.notifyDataSetChanged();
//        listView.setAdapter(mPropertyListAdapter);



       listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
       // listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                System.out.println("Selected item .... " + position+"id  .. "+id);
                if(checked){
                  selected_line_al.add((int)id);
                }else{
                    selected_line_al.remove((int)id);
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
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {

               // PropertyListAdapter pla = (PropertyListAdapter) getListAdapter();;
               // PropertyListAdapter pla = ListView

                PropertiesResultLab resultsLab = PropertiesResultLab.
                        getPropertiesResultLab(getActivity());
                System.out.println("data.........   " + resultsLab );

//                for (int i = pla.getCount() - 1; i >= 0; i--) {
//                    System.out.print("In chk..");
//                    if (getListView().isItemChecked(i)) {
//                        selected_line_al.add(i);
//
//                        System.out.println("selected line-------- " + resultsLab.getPropertyList().get(i).getNickname());
//
//
//                    }
//                }
                switch (item.getItemId()) {
                    case R.id.rentedM:
                        System.out.println("inside rented.......");
                        System.out.println("selected line before OK..  "+selected_line_al.size());

                        AlertDialog.Builder builder = new AlertDialog.Builder(listView.getContext());

                        builder.setMessage(R.string.alertrented)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()  {
                                    public void onClick(DialogInterface dialog, int id) {
                                       //
                                        Log.i(TAG, "Inisde OK");
//                                        PropertyModel pm =  new PropertyModel();
//                                        pm =
                                        rentedOk = true;
                                        mPropertyList = mCallbacks.onRentedClicked(selected_line_al, madapter);
//                                        madapter = new PropertyListAdapter
//                                                (getContext(), R.layout.landlord_property_row, mPropertyList);
//                                        madapter.notifyDataSetChanged();
//                                        listView.setAdapter(madapter);
                                       // Boolean b = resultsLab.rented(selected_line_al, listView.getContext());
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Log.i(TAG, "Inisde Cancel");
                                     //
                                       // finish();
                                       // rentedOk = false;
                                    }
                                });
                        builder.setCancelable(true);
                        builder.create();
                        builder.show();
                        Log.i(TAG, "after button alert.. ");
                        System.out.println(rentedOk);
//                        if (rentedOk) {
//                            System.out.println("selcted line   "+selected_line_al.size());
//                            Boolean b = resultsLab.rented(selected_line_al, listView.getContext());
//                        }

                        //   mAdapter.notifyDatasetChanged();
                        break;
                    case R.id.editM:
                        Bundle bundle = new Bundle();
                        bundle.putInt("selectedLine", selected_line_al.get(0));
                        bundle.putString("USERID",userid);
                        SharedPreferences sharedPreferences = getContext().
                                getSharedPreferences(TAG,Context.MODE_PRIVATE);
                        sharedPreferences.edit().putInt("selectedLine", selected_line_al.get(0)).apply();

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
                        //resultsLab.cancel(selected_line_al);
                        AlertDialog.Builder buildercan = new AlertDialog.Builder(listView.getContext());
                        buildercan.setMessage(R.string.alertcancel)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //
                                        mCallbacks.onCancelClicked(selected_line_al,madapter);
                                        cancelOk = true;
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // finish();
                                        cancelOk = false;
                                    }
                                });
                        buildercan.setCancelable(true);
                        buildercan.create();
                        buildercan.show();
                        if (cancelOk) {
                            Boolean b = resultsLab.cancel(selected_line_al, listView.getContext());
                        }
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
        ArrayList<PropertyModel> onRentedClicked(ArrayList<Integer> selected_lines,
                                                 PropertyListAdapter adapter);
        void onCancelClicked(ArrayList<Integer> selected_lines, PropertyListAdapter adapter);
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

//    public void onTaskCompleted(JSONObject jsonObject){
//        //
//        //Log.i(TAG, "Response---- "+jsonObject );
//        System.out.println("Response......"+jsonObject);
//        ArrayList<PropertyModel> tempPropertyList;
//        PropertyModel p = new PropertyModel();
//        p.setKey("001");
//        p.setNickname("First House");
//        p.setStreet("2u28 duu");
//        p.setCity("San Jose");
//        p.setState("CA");
//        p.setZip("83993");
//        p.setView_count("10");
//        p.setProperty_type("Villa");
//        p.setBath("2");
//        p.setView_count("123");
//        p.setRent("12333");
//        p.setArea("2222");
//        p.setBath("2");
//        p.setRoom("3");
//        p.setDescription("sjsjsjs ejej");
//        tempPropertyList = new ArrayList<>();
//        mPropertyList.add(p);
//        PropertyModel p1 = new PropertyModel();
//        p1.setKey("002");
//        p1.setNickname("Sec House");
//        p1.setStreet("2u2jj8 duu");
//        p1.setCity("San Joskke");
//        p1.setState("CAjj");
//        p1.setZip("8j993");
//        p1.setView_count("39");
//        p1.setView_count("100");
//        p1.setRent("12333");
//        p1.setArea("2222");
//        p1.setBath("2");
//        p1.setRoom("3");
//        p1.setDescription("hi jjfjf lfo");
//        // tempPropertyList = new ArrayList<>();
//        mPropertyList.add(p1);
//
//        PropertyModel p3 = new PropertyModel();
//        p3.setKey("003");
//        p3.setNickname("Third House");
//        p3.setStreet("2u2jejjjjj8 duu");
//        p3.setCity("Seeean Joskke");
//        p3.setState("C ejAjj");
//        p3.setZip("82kkj993");
//        p3.setView_count("334");
//        p3.setView_count("144");
//        p3.setRent("12333");
//        p3.setArea("2222");
//        p3.setBath("2");
//        p3.setRoom("3");
//        p3.setDescription("sjkkks dfj heii oo");
//        // tempPropertyList = new ArrayList<>();
//        mPropertyList.add(p3);
//
//      //  mPropertyList = tempPropertyList;
//        //return tempPropertyList;
//
//    }


    @Override
    public void onResume() {
        super.onResume();
     //   madapter.notifyDataSetChanged();
    }
}








