package project.team.cmpe277.com.magicrentals1;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * Created by Rekha on 5/11/2016.
 */
public class TenantDetailPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private static final String TAG = "TDPA";
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        Log.i("CLASSES","TenantDetailsPager");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setTitle("Magic Rentals");

        final ArrayList<GridImageDetailItem> gridImageItems  = PropSingleton.get(this).getGridImageDetailItems();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return gridImageItems.size();
            }

            @Override
            public Fragment getItem(int pos) {
                GridImageDetailItem gridImageDetailItem = gridImageItems.get(pos);
                return TenantSearchDetailFragment.newInstance(gridImageDetailItem.getId());
            }
        });

        String id = (String)getIntent()
                .getSerializableExtra(TenantSearchDetailFragment.IDS);
        for (int i = 0; i < gridImageItems.size(); i++) {
            if (gridImageItems.get(i).getId().equals(id)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }


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
