package project.team.cmpe277.com.magicrentals1;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by Rekha on 5/11/2016.
 */
public class TenantDetailPagerActivity extends FragmentActivity {

    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

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

}