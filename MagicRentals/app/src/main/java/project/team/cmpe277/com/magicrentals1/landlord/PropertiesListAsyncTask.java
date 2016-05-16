package project.team.cmpe277.com.magicrentals1.landlord;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.R;

/**
 * Created by savani on 5/10/16.
 */
public class PropertiesListAsyncTask extends AsyncTask<String, Void, Boolean>{

    private String userid;
    ProgressBar mProgressBar;
    private PropertyListLandlordFragment mPropertyFragment;
    private ListView mListView;
    ArrayList<PropertyModel> mPropertyList;
    PropertiesResultLab mPropertyResultLab;
    PropertyListAdapter mPropertyListAdapter;
    String newurl ;
    public PropertiesListAsyncTask(String userid, PropertyListLandlordFragment listFragment,
                                   ListView listView, ProgressBar progressBar, PropertyListAdapter adapter){
        this.userid = userid;
        this.mPropertyFragment = listFragment;
        this.mListView = listView;
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(listFragment.getContext());
        this.mProgressBar = progressBar;
        this.mPropertyListAdapter = adapter;
        System.out.println("constructor .. "+ mPropertyListAdapter);
        //Log.i("inside adapter", mPropertyListAdapter);
//
        newurl = mPropertyFragment.getString(R.string.url);
        mPropertyList = mPropertyResultLab.getPropertyList();
    }
    @Override
    protected void onPreExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
    @Override
    protected Boolean doInBackground(String... userid){
        HttpURLConnection httpConn;
        // String str = "http://54.153.2.150:3000/getPostsByUser?userid="+"savani";
        //  String str = newurl+"/getPostsByUser?user_id="+"";
        String str = newurl+"/getPostsByUser?user_id="+userid[0];

        System.out.println(str);

        URL url = null;
        try {
            url = new URL(str);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br
                        = new BufferedReader(new InputStreamReader(in));
                //InputStreamReader isr = new InputStreamReader(in);
                String temp = null;
                String status = null;
                JSONObject json = null;
                StringBuilder sb = new StringBuilder();

                while ((temp = br.readLine()) != null){
                    System.out.println("readd input stream...."+temp);
                    sb.append(temp);
                }
                json = new JSONObject(sb.toString());
                int code = json.getInt("code");
                if(code == 200){
                    JSONArray ja = json.getJSONArray("data");

                    for(int i = 0; i < ja.length(); i++ ) {
                        PropertyModel pm = new PropertyModel();
                        JSONObject result = ja.getJSONObject(i);
                        if (result.has("Status"))
                            status = changeNull(result.getString("Status"), false);
                        if (!status.equals("Cancelled")) {
                            if (result.has("_id"))
                                pm.setKey(result.getString("_id"));
                            if (result.has("user_id"))
                                pm.setUser_id(result.getString("user_id"));
                            if (result.has("address")) {
                                JSONObject address = result.getJSONObject("address");
                                if (address.has("Street")) {
                                    pm.setStreet(changeNull(address.getString("Street"), false));
                                }
                                if (address.has("City"))
                                    pm.setCity(changeNull(address.getString("City"), false));
                                if (address.has("State"))
                                    pm.setState(changeNull(address.getString("State"), false));
                                if (address.has("Zip"))
                                    pm.setZip(changeNull(address.getString("Zip"), false));
                            }
                            if (result.has("property_type"))
                                pm.setProperty_type(result.getString("property_type"));
                            if (result.has("units")) {
                                JSONObject units = result.getJSONObject("units");
                                if (units.has("bath") &&
                                        units.getString("bath").matches(".*\\d+.*"))
                                    pm.setBath(changeNull(units.getString("bath"), true));
                                if (units.has("room") &&
                                        units.getString("room").matches(".*\\d+.*"))
                                    pm.setRoom(changeNull(units.getString("room"), true));
                                if (units.has("area"))
                                    pm.setArea(changeNull(units.getString("area"), true));
                            }
                            if (result.has("rent"))
                                pm.setRent(changeNull(result.getString("rent"), true));
                            if (result.has("Contact_info")) {
                                JSONObject contact = result.getJSONObject("Contact_info");
                                if (contact.has("email")) {
                                    pm.setEmail(changeNull(contact.getString("email"), false));
                                    System.out.println("email.... " + changeNull(contact.getString("email"), false));
                                }
                                if (contact.has("Mobile"))
                                    pm.setMobile(changeNull(contact.getString("Mobile"), true));
                            }
                            if (result.has("description"))
                                pm.setDescription(changeNull(result.getString("description"), false));
                            if (result.has("Status"))
                                pm.setStatus(changeNull(result.getString("Status"), false));
                            if (result.has("view_count"))
                                pm.setView_count(changeNull(result.getString("view_count"), true));
                            if (result.has("nickName"))
                                pm.setNickname(changeNull(result.getString("nickName"), false));
                            if (result.has("other_details"))
                                pm.setOther_details(changeNull(result.getString("other_details"), false));
                            if(result.has("Images"))
                                pm.setImages(changeNull(result.getString("Images"),false));
                            mPropertyList.add(pm);
                        }
                    }

//                ResponseModel responseModel = new ResponseModel();
//              //  responseModel = gson.fromJson(str1, ResponseModel.class);
//                System.out.println("Response ..... "+responseModel.getData());
//                if ( responseModel.getCode() == 200 ){
//                    System.out.println("Status..... "+responseModel.getStatus());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }


        }catch (MalformedURLException e) {
            e.printStackTrace(); } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
//       ArrayList<PropertyModel> tempPropertyList;
//        PropertyModel p = new PropertyModel();
//        p.setKey("001");
//        p.setNickname("First House");
//        p.setStreet("Meridian #344");
//        p.setCity("San Jose");
//        p.setState("CA");
//        p.setZip("95126");
//        p.setView_count("10");
//        p.setProperty_type("Villa");
//        p.setBath("2");
//        p.setView_count("123");
//        p.setRent("4333");
//        p.setArea("2222");
//        p.setBath("2");
//        p.setRoom("3");
//        p.setStatus("Created");
//        p.setDescription("Very nice view");
//        p.setOther_details("Lease - 1yr");
//        tempPropertyList = new ArrayList<>();
//        mPropertyList.add(p);
//        PropertyModel p1 = new PropertyModel();
//        p1.setKey("002");
//        p1.setNickname("Sec House");
//        p1.setStreet("San Carlos #12");
//        p1.setCity("San Jose");
//        p1.setState("CA");
//        p1.setZip("89993");
//        p1.setView_count("39");
//        p1.setView_count("100");
//        p1.setRent("1343");
//        p1.setArea("1000");
//        p1.setBath("2");
//        p1.setRoom("3");
//        p1.setProperty_type("Apartment");
//        p1.setDescription("Luxurious Apartment");
//        p1.setStatus("Created");
//        // tempPropertyList = new ArrayList<>();
//        p1.setOther_details("Lease - 6months");
//        mPropertyList.add(p1);
//
//        PropertyModel p3 = new PropertyModel();
//        p3.setKey("003");
//        p3.setNickname("Property");
//        p3.setStreet("Race Street #45");
//        p3.setCity("San Jose");
//        p3.setState("CA");
//        p3.setZip("95126");
//        p3.setView_count("334");
//        p3.setView_count("144");
//        p3.setRent("4200");
//        p3.setArea("1200");
//        p3.setBath("2"); if(  mPropertyFragment.isVisible()){
//        PropertyListAdapter mPropertyListAdapter = new PropertyListAdapter
//                (mPropertyFragment.getContext(), R.layout.landlord_property_row, mPropertyList);
        //  mPropertyListAdapter.notifyDataSetChanged();
        //    mListView.setAdapter(mPropertyListAdapter);
//        p3.setRoom("3");
//        p3.setDescription("Close to bus stop");
//        p3.setOther_details("Lease - 1 year");
//        p3.setStatus("Created");
//
//        // tempPropertyList = new ArrayList<>();
//        mPropertyList.add(p3);
        mProgressBar.setVisibility(View.GONE);
        if(  mPropertyFragment.isVisible()){
            System.out.println("Current Adapter...... "+mPropertyListAdapter);

            mPropertyListAdapter.notifyDataSetChanged();
            mListView.setAdapter(mPropertyListAdapter);


//            if(  mPropertyFragment.isVisible()){
//                PropertyListAdapter mPropertyListAdapter1 = new PropertyListAdapter
//                        (mPropertyFragment.getContext(), R.layout.landlord_property_row, mPropertyList);
//                //  mPropertyListAdapter.notifyDataSetChanged();
//                mListView.setAdapter(mPropertyListAdapter1);


        }



    }
    String changeNull(String str, boolean num){
        if(str.equals("null") && !num ){
            String a = "";
            return a;
        }else  if(str.equals("null") && num ){
            String a = "0";
            return a;
        }
        return str;
    }


}
