package project.team.cmpe277.com.magicrentals1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;


public class TenantsFavActivity extends AppCompatActivity{

public static String userid;
private static final String TAG = "TenFavLA";



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.searchlistactivity_tenant);
            SharedPreferences preferences = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
            userid = preferences.getString(LoginActivity.USERID,null);
            //Log.i(TAG, userid);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.drawable.ic_launcher);

            actionBar.setTitle("Magic Rentals");


            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.searchlist_container, new TenantsFavListFragment()).commit();
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        }

}
