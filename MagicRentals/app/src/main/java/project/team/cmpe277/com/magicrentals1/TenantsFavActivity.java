package project.team.cmpe277.com.magicrentals1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;


public class TenantsFavActivity extends AppCompatActivity implements TaskCompletedStatus {

    private boolean bFreshLoad = true;
    SharedPreferences preferences;// = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
    String userid; //= preferences.getString(LoginActivity.USERID, null);
    private static final String TAG = "TenantsFavActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants_fav);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString("USERID",null);
        if(bFreshLoad){
            String url = getString(R.string.url)+"/getAllFav";
            //String url =  "http://10.0.0.44:3000/getAllFav";
            HashMap<String, String> hmap = new HashMap<>();
            hmap.put("uid",userid);
            new MultipartUtilityAsyncTask(this,hmap,null).execute(url);
        }else{
            TenantsFavListFragment fragment = new TenantsFavListFragment();
            //TenantsFavListFragment fragment = TenantsFavListFragment.newInstance("for_FavList");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            //fm.beginTransaction().replace(R.id.search_container, new TenantSearchFragment()).commit();
            transaction.add(R.id.favGrid_Container,fragment,"Favlist");
            transaction.commit();
        }
    }

    @Override
    public void onTaskCompleted(JSONObject result) {
       // System.out.print("test");
        JSONObject addr, units, contact;

        try{
            JSONArray jsonarray = result.getJSONArray("data");

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                addr = jsonobject.getJSONObject("address");
                units = jsonobject.getJSONObject("units");
                contact = jsonobject.getJSONObject("Contact_info");

                FavPropertieDetails favPropertieDetails = new FavPropertieDetails(jsonobject.getString("user_id"),addr.getString("Street"),
                        addr.getString("City"),addr.getString("State"),addr.getString("Zip"),jsonobject.getString("property_type"),
                        units.getString("bath"),units.getString("room"),units.getString("area"),jsonobject.getString("rent"),
                        contact.getString("email"),contact.getString("Mobile"),jsonobject.getString("description"),jsonobject.getString("Images"),
                        jsonobject.getString("other_details"),jsonobject.getString("Status"),jsonobject.getString("_id"));
                FavPropCollections.favoriteAList.add(favPropertieDetails);
            }

            TenantsFavListFragment fragment = new  TenantsFavListFragment();
            //TenantsFavListFragment fragment = TenantsFavListFragment.newInstance("for_FavList");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            //fm.beginTransaction().replace(R.id.search_container, new TenantSearchFragment()).commit();
            transaction.add(R.id.favGrid_Container,fragment,"Favlist");
            transaction.commit();
        }
        catch (Exception ex){
            //exception
        }

    }
}
