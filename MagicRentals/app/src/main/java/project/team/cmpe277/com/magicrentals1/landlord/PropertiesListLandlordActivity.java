package project.team.cmpe277.com.magicrentals1.landlord;

import android.annotation.TargetApi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;


import java.util.ArrayList;
import java.util.logging.Logger;
/**
 * Created by savani on 4/26/16.
 */

import project.team.cmpe277.com.magicrentals1.R;

public class PropertiesListLandlordActivity   extends AppCompatActivity implements PropertyListLandlordFragment.Callbacks
       {



    static String userid;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_properties_landlord);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

  /*      userid = getIntent()
                .getSerializableExtra("USERID").toString();*/
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

/*        userid = getIntent()
                .getSerializableExtra("USERID").toString();*/
        Intent i = new Intent(getApplicationContext(), UploadPropertyDataActivity.class);
        i.putExtra("USERID", userid);
        startActivity(i);
//        bundle.putString("USERID", userid);
//        UploadDataFragment fragment = new UploadDataFragment();
//        fragment.setArguments(bundle);
//        // fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPropertyClicked(PropertyModel property) {
      //  Logger.getAnonymousLogger("sklslsl  ");

        System.out.println("Inside clicke event   ");
        if(findViewById(R.id.detailPropFragmentContainer) == null){
            Intent i = new Intent(this,PropertyDetailActivity.class);
            i.putExtra(PropertyDetailFragment.PROPERTY_KEY, property.getKey());
            startActivity(i);
        }else{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment oldDetail = fm.findFragmentById(R.id.detailPropFragmentContainer);
            Fragment newDetail = PropertyDetailFragment.newInstance(property.getKey());
            if (oldDetail != null) {
                ft.remove(oldDetail);
            }
            ft.add(R.id.detailPropFragmentContainer, newDetail);
            ft.commit();
        }
    }


       }
