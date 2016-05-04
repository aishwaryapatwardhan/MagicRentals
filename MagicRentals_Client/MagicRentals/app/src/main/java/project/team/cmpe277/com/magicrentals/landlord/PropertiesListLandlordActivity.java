package project.team.cmpe277.com.magicrentals.landlord;

import android.annotation.TargetApi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;


import java.util.ArrayList;
/**
 * Created by savani on 4/26/16.
 */

import project.team.cmpe277.com.magicrentals.R;

public class PropertiesListLandlordActivity   extends AppCompatActivity {



    static String userid;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_properties_landlord);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        userid = getIntent()
                .getSerializableExtra("USERID").toString();
        Fragment fragment = PropertyListLandlordFragment.getFragment(userid);
       // fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landlord, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();

        userid = getIntent()
                .getSerializableExtra("USERID").toString();
        bundle.putString("USERID", userid);
        UploadDataFragment fragment = new UploadDataFragment();
        fragment.setArguments(bundle);
        // fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();

        return super.onOptionsItemSelected(item);
    }
}
