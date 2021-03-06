package project.team.cmpe277.com.magicrentals1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Rekha on 4/27/2016.
 */
public class TenantSearchListActivity extends AppCompatActivity {

    public static String userid;
    private static final String TAG = "TenSrchLA";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CLASSES","TenantSearchListActivity");
        setContentView(R.layout.searchlistactivity_tenant);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID,null);
        //Log.i(TAG, userid);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");


        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.searchlist_container, new TenantSearchListFragment()).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        *//*if (id == R.id.action_settings) {
            return true;
        }*//*

        return super.onOptionsItemSelected(item);
    }*/

}
