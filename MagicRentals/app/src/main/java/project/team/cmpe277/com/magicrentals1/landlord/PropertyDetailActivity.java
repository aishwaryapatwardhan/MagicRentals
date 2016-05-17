package project.team.cmpe277.com.magicrentals1.landlord;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

/**
 * Created by savani on 5/4/16.
 */
public class PropertyDetailActivity extends AppCompatActivity
        implements PropertyDetailFragment.DetailCallbacks, TaskCompletedStatus {

    public static String userid;
    ViewPager mViewPager;
    private ArrayList<PropertyModel> mPropertyResults ;
    android.support.v7.app.ActionBar mActionBar;
    FragmentStatePagerAdapter fragmentStatePagerAdapter;
    private static final String TAG = "PropertyDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.detailPropFragmentContainer);
        setContentView(mViewPager);
        mActionBar = getSupportActionBar();
        assert mActionBar != null;
        //mActionBar.setDisplayHomeAsUpEnabled(true);

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



    @Override
    public void onRentedClicked(PropertyModel currModel) {
        PropertiesResultLab mPropertyResultLab;
        System.out.println("Helllo.... "+currModel);
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(this);

        HashMap<String, String> hm= new HashMap<>();
        hm.put("id", currModel.getKey());
        //Log.i(TAG, hm.get("id")+"USersssss.......");
        hm.put("Status","Rented");
        hm.put("email",currModel.getEmail());
        // String url = "http://54.153.2.150:3000/updateStatus";
        String url = getString(R.string.url)+"/updateStatus";
        currModel.setStatus("Rented");
        new MultipartUtilityAsyncTask(PropertyDetailActivity.this, hm, null).execute(url);
        finish();
        //return mPropertyList;
    }

    @Override
    public void onCancelClicked(PropertyModel currModel) {
        PropertiesResultLab mPropertyResultLab;
        System.out.println("Helllo.... "+currModel);
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(this);

        HashMap<String, String> hm= new HashMap<>();
        hm.put("id", currModel.getKey());
        //Log.i(TAG, hm.get("id")+"USersssss.......");
        hm.put("Status","Cancelled");
        hm.put("email",currModel.getEmail());
        // String url = "http://54.153.2.150:3000/updateStatus";
        String url = getString(R.string.url)+"/updateStatus";
        currModel.setStatus("Cancelled");
        new MultipartUtilityAsyncTask(PropertyDetailActivity.this, hm, null).execute(url);
        finish();
        //return mPropertyList;
    }


    @Override
    public void onTaskCompleted(JSONObject result) {
        Log.i(TAG, "Response..."+result);

    }



}
