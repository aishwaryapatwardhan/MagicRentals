package project.team.cmpe277.com.magicrentals1;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Rekha on 5/1/2016.
 */
public class TenantSearchDetailActivity extends AppCompatActivity {
    public static String userid;
    private static final String TAG = "TSDActivity";
    String uuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchdetailactivity_tenant);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID, null);

        Log.i("CLASSES", "Is called");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");

        uuid = getIntent()
                .getSerializableExtra("UUID").toString();

        //FragmentManager fm = getFragmentManager();
        //fm.beginTransaction().replace(R.id.searchdetail_container, new TenantSearchDetailFragment()).commit();
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
