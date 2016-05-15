package project.team.cmpe277.com.magicrentals1;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.landlord.PropertyListAdapter;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyListLandlordFragment;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyModel;

public class TenantAndLandlordNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PropertyListLandlordFragment.Callbacks {

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

            FragmentManager fm = getFragmentManager();
            getSupportFragmentManager().beginTransaction().replace(R.id.detailFragmentContainer, new PropertyListLandlordFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPropertyClicked(PropertyModel property) {

    }

    @Override
    public ArrayList<PropertyModel> onRentedClicked(int selected_line, PropertyListAdapter adapter) {
        return null;
    }

    @Override
    public void onCancelClicked(int selected_line, PropertyListAdapter adapter) {

    }
}
