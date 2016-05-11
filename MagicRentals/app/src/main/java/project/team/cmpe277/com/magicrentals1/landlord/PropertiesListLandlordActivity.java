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


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
/**
 * Created by savani on 4/26/16.
 */

import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

public class PropertiesListLandlordActivity   extends AppCompatActivity
        implements PropertyListLandlordFragment.Callbacks, TaskCompletedStatus
       {



    static String userid;
           ArrayList<PropertyModel> mPropertyList;
           PropertiesResultLab mPropertyResultLab;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_properties_landlord);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

  /*      userid = getIntent()
                .getSerializableExtra("USERID").toString();*/

//        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(PropertiesListLandlordActivity.this);
////
//        mPropertyList = mPropertyResultLab.getPropertyList();
//
//        HashMap<String, String> hm= new HashMap<>();
//        hm.put("user_id","savani");
////
//        String url = "http://192.168.1.173:3000/getPostsByUser";
//        //    new MultipartUtilityAsyncTask(hm, null).execute(url);
//        new MultipartUtilityAsyncTask(PropertiesListLandlordActivity.this, hm, null).execute(url);

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

           public void onTaskCompleted(JSONObject jsonObject){
               //
               //Log.i(TAG, "Response---- "+jsonObject );
               System.out.println("Response......"+jsonObject);
               ArrayList<PropertyModel> tempPropertyList;
               PropertyModel p = new PropertyModel();
               p.setKey("001");
               p.setNickname("First House");
               p.setStreet("2u28 duu");
               p.setCity("San Jose");
               p.setState("CA");
               p.setZip("83993");
               p.setView_count("10");
               p.setProperty_type("Villa");
               p.setBath("2");
               p.setView_count("123");
               p.setRent("12333");
               p.setArea("2222");
               p.setBath("2");
               p.setRoom("3");
               p.setDescription("sjsjsjs ejej");
               tempPropertyList = new ArrayList<>();
               mPropertyList.add(p);
               PropertyModel p1 = new PropertyModel();
               p1.setKey("002");
               p1.setNickname("Sec House");
               p1.setStreet("2u2jj8 duu");
               p1.setCity("San Joskke");
               p1.setState("CAjj");
               p1.setZip("8j993");
               p1.setView_count("39");
               p1.setView_count("100");
               p1.setRent("12333");
               p1.setArea("2222");
               p1.setBath("2");
               p1.setRoom("3");
               p1.setDescription("hi jjfjf lfo");
               // tempPropertyList = new ArrayList<>();
               mPropertyList.add(p1);

               PropertyModel p3 = new PropertyModel();
               p3.setKey("003");
               p3.setNickname("Third House");
               p3.setStreet("2u2jejjjjj8 duu");
               p3.setCity("Seeean Joskke");
               p3.setState("C ejAjj");
               p3.setZip("82kkj993");
               p3.setView_count("334");
               p3.setView_count("144");
               p3.setRent("12333");
               p3.setArea("2222");
               p3.setBath("2");
               p3.setRoom("3");
               p3.setDescription("sjkkks dfj heii oo");
               // tempPropertyList = new ArrayList<>();
               mPropertyList.add(p3);
             //  PropertyListLandlordFragment.get(mPropertyList);

               //  mPropertyList = tempPropertyList;
               //return tempPropertyList;

           }
       }
