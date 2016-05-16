package project.team.cmpe277.com.magicrentals1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class TenantsFavActivity extends AppCompatActivity{

public static String userid;
private static final String TAG = "TenFavLA";



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.searchlistactivity_tenant);
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
            userid = preferences.getString(LoginActivity.USERID,null);
            //Log.i(TAG, userid);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.drawable.ic_launcher);

            actionBar.setTitle("Magic Rentals");



            getSupportFragmentManager().beginTransaction().replace(R.id.searchlist_container, new TenantsFavListFragment()).commit();
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

}
