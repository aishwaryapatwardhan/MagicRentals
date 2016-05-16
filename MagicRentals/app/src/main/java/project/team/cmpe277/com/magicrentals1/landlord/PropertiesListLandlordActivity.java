package project.team.cmpe277.com.magicrentals1.landlord;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.LoginActivity;
import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

/**
 * Created by savani on 4/26/16.
 */

public class PropertiesListLandlordActivity   extends AppCompatActivity
        implements PropertyListLandlordFragment.Callbacks, TaskCompletedStatus
       {

           private static final String TAG = "LandlordListActivity";
           String userid;
           ArrayList<PropertyModel> mPropertyList;
           PropertiesResultLab mPropertyResultLab;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_properties_landlord);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);


        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID, null);
        Fragment fragment = PropertyListLandlordFragment.getFragment(userid);
        // fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landlord, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Bundle bundle = new Bundle();


        Intent i = new Intent(getApplicationContext(), UploadPropertyDataActivity.class);
        i.putExtra("USERID", userid);
        startActivity(i);
//        bundle.putString("USERID", userid);
//        UploadDataFragment fragment = new UploadDataFragment();
//        fragment.setArguments(bundle);
//        // fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPropertyClicked(PropertyModel property) {
      //  Logger.getAnonymousLogger("sklslsl  ");

        System.out.println("Inside click event   ");
        if(findViewById(R.id.detailPropFragmentContainer) == null){
            Intent i = new Intent(this,PropertyDetailActivity.class);
            i.putExtra(PropertyDetailFragment.PROPERTY_KEY, property.getKey());
            startActivity(i);
        }else{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment oldDetail = fm.findFragmentById(R.id.detailPropFragmentContainer);
            Fragment newDetail = PropertyDetailFragment.newInstance(property.getKey());
            if (oldDetail != null) {
                ft.remove(oldDetail);
            }
            ft.add(R.id.detailPropFragmentContainer, newDetail);
            ft.commit();
        }
    }
           @Override
           public  ArrayList<PropertyModel> onRentedClicked(int selected_line, PropertyListAdapter adapter){
               PropertiesResultLab mPropertyResultLab;
               mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(this);
               mPropertyList = mPropertyResultLab.getPropertyList();
              // System.out.println("Inside callback"+ mPropertyList.size()+"first--- "+mPropertyList.get(0));
               HashMap<String, String> hm= new HashMap<>();
               // hm.put("user_id", mPropertyList.get(al.get(0)).getUser_id());
               // hm.put("user_id", "savaniffwffyyfggq12345");
               // hm.put("id",sPropertiesResultLab.mPropertyList.get(al.get(0)).getKey());

               hm.put("id", mPropertyList.get(selected_line).getKey());
               Log.i(TAG, hm.get("id")+"USersssss.......");
               hm.put("Status","Rented");
               hm.put("email",mPropertyList.get(selected_line).getEmail());
               // String url = "http://54.153.2.150:3000/updateStatus";
               String url = getString(R.string.url)+"/updateStatus";
               PropertyModel pm =  mPropertyList.get(selected_line);
               pm.setStatus("Rented");


               new MultipartUtilityAsyncTask(PropertiesListLandlordActivity.this, hm, null).execute(url);
               return mPropertyList;
               //adapter.notifyDataSetChanged();
//               PropertyListAdapter mAdapter = new PropertyListAdapter()
//                       (this,R.layout.search_result_row, mPropertyList );
//
//               listView.setAdapter(mAdapter);
           }

           @Override
           public void onCancelClicked(int selected_line, PropertyListAdapter adapter) {
               PropertiesResultLab mPropertyResultLab;
               mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(this);
               mPropertyList = mPropertyResultLab.getPropertyList();
               HashMap<String, String> hm= new HashMap<>();
               hm.put("id", mPropertyList.get(selected_line).getKey());
               Log.i(TAG, hm.get("id")+"USersssss.......");
               hm.put("Status","Cancelled");
               hm.put("email",mPropertyList.get(selected_line).getEmail());
               // String url = "http://54.153.2.150:3000/updateStatus";
               String url = getString(R.string.url)+"/updateStatus";
               //PropertyModel pm =  mPropertyList.get();
               mPropertyList.remove(selected_line);

               new MultipartUtilityAsyncTask(PropertiesListLandlordActivity.this, hm, null).execute(url);
             //  return mPropertyList;


           }


           @Override
           public void onTaskCompleted(JSONObject result) {
               Log.i(TAG, "Response..."+result);

           }
       }
