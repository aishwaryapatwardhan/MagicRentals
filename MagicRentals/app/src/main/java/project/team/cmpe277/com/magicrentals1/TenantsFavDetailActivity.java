package project.team.cmpe277.com.magicrentals1;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class TenantsFavDetailActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");

        final ArrayList<GridImageDetailItem> gridImageItems  = FavPropSingleton.get(this).getGridImageDetailItems();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return gridImageItems.size();
            }

            @Override
            public Fragment getItem(int pos) {
                GridImageDetailItem gridImageDetailItem = gridImageItems.get(pos);
                return TenantsFavDetailsFragment.newInstance(gridImageDetailItem.getId());
            }
        });

        String id = (String)getIntent()
                .getSerializableExtra(TenantsFavDetailsFragment.IDS);
        for (int i = 0; i < gridImageItems.size(); i++) {
            if (gridImageItems.get(i).getId().equals(id)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }


    }

}
