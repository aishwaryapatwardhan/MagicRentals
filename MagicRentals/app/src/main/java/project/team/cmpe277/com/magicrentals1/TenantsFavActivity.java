package project.team.cmpe277.com.magicrentals1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TenantsFavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants_fav);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");

        TenantsFavListFragment fragment = new  TenantsFavListFragment();
        //TenantsFavListFragment fragment = TenantsFavListFragment.newInstance("for_FavList");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //fm.beginTransaction().replace(R.id.search_container, new TenantSearchFragment()).commit();
        transaction.add(R.id.favGrid_Container,fragment,"Favlist");
        transaction.commit();
    }
}
