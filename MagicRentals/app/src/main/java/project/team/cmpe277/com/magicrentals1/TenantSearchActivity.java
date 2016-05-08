package project.team.cmpe277.com.magicrentals1;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rekha on 4/27/2016.
 */
public class TenantSearchActivity extends AppCompatActivity {

    public static String userid;
    private static final String TAG = "TenantSearchActivity";
    SharedPreferences preferences = this.getSharedPreferences(TAG, Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchactivity_tenant);

        userid = preferences.getString(LoginActivity.USERID,null);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.search_container, new TenantSearchFragment()).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /*@Override
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
        if (id == R.id.save_search) {
            //make api call to save the search agent - only one search agent - update existing search agent
            return true;
        }
        else if (id == R.id.favorites) {
            //migrate to new favourites activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}
