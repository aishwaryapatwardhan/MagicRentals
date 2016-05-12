package project.team.cmpe277.com.magicrentals1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TenantsFavDetailActivity extends AppCompatActivity {

    public static String userid;
    private static final String TAG = "TenantsFavDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants_fav_detail);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID,null);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");

        String rid = getIntent()
                .getSerializableExtra("RID").toString();

        TenantsFavDetailsFragment fragment = new  TenantsFavDetailsFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.favDetail_container, fragment, "Favlist");
        transaction.commit();

    }
}