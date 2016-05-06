package project.team.cmpe277.com.magicrentals1.landlord;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.R;

/**
 * Created by savani on 5/4/16.
 */
public class PropertyDetailActivity extends AppCompatActivity{

    public static String userid;
    ViewPager mViewPager;
    private ArrayList<PropertyModel> mPropertyResults ;
    android.support.v7.app.ActionBar mActionBar;
    FragmentStatePagerAdapter fragmentStatePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.detailPropFragmentContainer);
        setContentView(mViewPager);
        mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mPropertyResults = PropertiesResultLab.getPropertiesResultLab(
                getApplicationContext()).getPropertyList();


        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentStatePagerAdapter = new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
               PropertyModel  property= mPropertyResults.get(position);
                return PropertyDetailFragment.newInstance(property.getKey());
            }

            @Override
            public int getCount() {
                return mPropertyResults.size();
            }
        };
        mViewPager.setAdapter(fragmentStatePagerAdapter);
        fragmentStatePagerAdapter.notifyDataSetChanged();
        final String propertyKey = getIntent().getStringExtra(PropertyDetailFragment.PROPERTY_KEY); //Property_key  set in fragment
        for (int i = 0; i < mPropertyResults.size(); i++) {
            if (mPropertyResults.get(i).getKey().equals(propertyKey)) {
                mViewPager.setCurrentItem(i);
                mActionBar.setTitle(mPropertyResults.get(i).getNickname());
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PropertyModel property = mPropertyResults.get(position);
                if (property != null) {
                    //   mActionBar.setDisplayShowTitleEnabled(true);
                    mActionBar.setTitle(property.getNickname());
                  //  mActionBar.setLogo(R.mipmap.ic_launcher);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




//        FragmentManager fm = getFragmentManager();
//        fm.beginTransaction().replace(R.id.detailPropFragmentContainer, new PropertyDetailFragment()).commit();
}

    @Override
    protected void onPause() {
        super.onPause();
        mPropertyResults = PropertiesResultLab.getPropertiesResultLab(getApplicationContext()).getPropertyList();
        fragmentStatePagerAdapter.notifyDataSetChanged();
    }
}
