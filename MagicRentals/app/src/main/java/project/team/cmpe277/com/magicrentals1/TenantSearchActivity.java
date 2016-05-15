package project.team.cmpe277.com.magicrentals1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

/**
 * Created by Rekha on 4/27/2016.
 */
public class TenantSearchActivity extends AppCompatActivity implements TaskCompletedStatus{

    public static String userid;
    private static final String TAG = "TenantSearchActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchactivity_tenant);
        Log.i("CLASSES","TenantSearchActivity");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID, null);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/



        getSupportFragmentManager().beginTransaction().replace(R.id.search_container, new TenantSearchFragment()).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onTaskCompleted(JSONObject result) {

    }
}
