package project.team.cmpe277.com.magicrentals1;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rekha on 4/27/2016.
 */
public class TenantSearchActivity extends AppCompatActivity {

    public static String userid;
    private static final String TAG = "TenantSearchActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchactivity_tenant);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID, null);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.search_container, new TenantSearchFragment()).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}
