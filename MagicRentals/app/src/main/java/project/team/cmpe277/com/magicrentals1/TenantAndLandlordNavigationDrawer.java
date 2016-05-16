package project.team.cmpe277.com.magicrentals1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.landlord.PropertyDetailActivity;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyDetailFragment;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyListAdapter;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyListLandlordFragment;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyModel;
import project.team.cmpe277.com.magicrentals1.landlord.UploadPropertyDataFragment;
import project.team.cmpe277.com.magicrentals1.utility.PopUpTenantLandlord;

public class TenantAndLandlordNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PropertyListLandlordFragment.Callbacks {

    private static final String TAG = "TenantAndLandlordND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_and_landlord_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");

        Intent i = new Intent(this, PopUpTenantLandlord.class);
        startActivityForResult(i, PopUpTenantLandlord.USER_OR_LANDLORD);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tenant_and_landlord_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {


            getSupportFragmentManager().beginTransaction().replace(R.id.detailFragmentContainer, new TenantSearchFragment()).commit();

            // Handle the camera action
        } else if (id == R.id.nav_favs) {


            getSupportFragmentManager().beginTransaction().replace(R.id.detailFragmentContainer, new TenantsFavListFragment()).commit();

        } /*else if (id == R.id.nav_create_posts) {

            FragmentManager fm = getFragmentManager();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PropertyListLandlordFragment()).commit();


        }*/ else if (id == R.id.nav_view_posts) {
            getSupportFragmentManager().beginTransaction().replace(R.id.detailFragmentContainer, new PropertyListLandlordFragment()).commit();

        } else if (id == R.id.nav_logout) {
            getSharedPreferences("USER", Context.MODE_PRIVATE).edit().remove(LoginActivity.USERID).commit();
            finish();

        }
        else if(id == R.id.nav_create_posts){
            getSupportFragmentManager().beginTransaction().replace(R.id.detailFragmentContainer, new UploadPropertyDataFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPropertyClicked(PropertyModel property) {

        System.out.println("Inside click event   ");
        if (findViewById(R.id.detailPropFragmentContainer) == null) {
            Intent i = new Intent(this, PropertyDetailActivity.class);
            i.putExtra(PropertyDetailFragment.PROPERTY_KEY, property.getKey());
            startActivity(i);
        } else {
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

    @Override
    public ArrayList<PropertyModel> onRentedClicked(int selected_line, PropertyListAdapter adapter) {
        return null;
    }

    @Override
    public void onCancelClicked(int selected_line, PropertyListAdapter adapter) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PopUpTenantLandlord.USER_OR_LANDLORD && data != null) {
            String userOption = data.getStringExtra(PopUpTenantLandlord.USER_OPTION);

            Log.i(TAG, userOption + " this is the user option");
            Log.i(TAG, PopUpTenantLandlord.TENANT_OPTION + " this is the upload photo option");
            Log.i(TAG, PopUpTenantLandlord.LANDLORD_OPTION + " this is the take photo option");

            if (userOption.equals(PopUpTenantLandlord.TENANT_OPTION)) {
                Log.i(TAG, "You've clicked upload photo");
                getSupportFragmentManager().beginTransaction().replace(R.id.detailFragmentContainer, new TenantSearchFragment()).commit();
            } else if (userOption.equals(PopUpTenantLandlord.LANDLORD_OPTION)) {
                Log.i(TAG, "You've clicked take photo");
                getSupportFragmentManager().beginTransaction().replace(R.id.detailFragmentContainer, new PropertyListLandlordFragment()).commit();
            }
        }

    }
}
