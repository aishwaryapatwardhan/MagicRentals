package project.team.cmpe277.com.magicrentals1;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Rekha on 5/1/2016.
 */
public class TenantSearchDetailActivity extends AppCompatActivity {
    public static String userid;
    private static final String TAG = "TenantSearchDetailActivity";
    String uuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchdetailactivity_tenant);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID, null);


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

}
