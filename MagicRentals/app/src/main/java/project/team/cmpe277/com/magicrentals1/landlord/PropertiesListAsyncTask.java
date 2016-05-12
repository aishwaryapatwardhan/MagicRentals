package project.team.cmpe277.com.magicrentals1.landlord;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;

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
    String newurl ;
    public PropertiesListAsyncTask(String userid, PropertyListLandlordFragment listFragment,
                                   ListView listView, ProgressBar progressBar){
       this.userid = userid;
        this.mPropertyFragment = listFragment;
        this.mListView = listView;
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(listFragment.getContext());
        this.mProgressBar = progressBar;
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
        String str = newurl+"/getPostsByUser?user_id="+"savani";

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
                    System.out.println("String ..... "+sb.toString() + "ja... "+ja+"code.... "+code);
                    for(int i = 0; i < ja.length(); i++ ){
                        PropertyModel pm = new PropertyModel();
                        JSONObject result =  ja.getJSONObject(i);
                        if(result.has("_id"))
                            pm.setKey(result.getString("_id"));
                        if(result.has("user_id"))
                            pm.setUser_id(result.getString("user_id"));
                        if(result.has("address")){
                            JSONObject address = result.getJSONObject("address");
                            if(address.has("Street")){
                                pm.setStreet(changeNull(address.getString("Street"), false));}
                            if(address.has("City"))
                                pm.setCity(changeNull(address.getString("City"), false));
                            if(address.has("State"))
                                pm.setState(changeNull(address.getString("State"), false));
                            if(address.has("Zip"))
                                pm.setZip(changeNull(address.getString("Zip"), false));

                        }
                        if(result.has("property_type"))
                            pm.setProperty_type(result.getString("property_type"));
                        if(result.has("units")){
                            JSONObject units = result.getJSONObject("units");
                            if(units.has("bath") &&
                                            units.getString("bath").matches(".*\\d+.*"))
                                pm.setBath(changeNull(units.getString("bath"), true));
                            if(units.has("room")&&
                                    units.getString("room").matches(".*\\d+.*"))
                                pm.setRoom(changeNull(units.getString("room"), true));
                            if(units.has("area"))
                                pm.setArea(changeNull(units.getString("area"), true));
                        }
                        if(result.has("rent"))
                            pm.setRent(changeNull(result.getString("rent"), true));
                        if(result.has("Contact_info")){
                            JSONObject contact = result.getJSONObject("Contact_info");
                            if(contact.has("email"))
                                pm.setEmail(changeNull(contact.getString("email"), false));
                            if(contact.has("Mobile"))
                                pm.setMobile(changeNull(contact.getString("Mobile"), true));
                        }
                        if(result.has("description"))
                            pm.setDescription(changeNull(result.getString("description"), false));
                        if(result.has("Status"))
                            pm.setStatus(changeNull(result.getString("Status"), false));
                        if(result.has("view_count") )
                            pm.setView_count(changeNull(result.getString("view_count"), true));
                        if(result.has("nickname"))
                            pm.setNickname(changeNull(result.getString("nickname"), false));
                        if(result.has("other_details"))
                            pm.setOther_details(changeNull(result.getString("other_details"), false));
//                        {      "_id": "savaniffwffyyfggq12345",
//                                "user_id": "savani",      "address": {
//                            "Street": "ffw",        "City": "ffyy",
//                                    "State": "fggq",        "Zip": "12345"      },
//                            "property_type": "Apartment",      "units": {
//                            "bath": null,        "room": null,        "area": 4666      },
//                            "rent": 4666,      "Contact_info": {        "email": null,
//                                "Mobile": null      },      "description": null,      "Images": null,
//                                "other_details": null,      "Status": null,      "view_count": null    }
                        mPropertyList.add(pm);
//
                   }


                }

                ResponseModel responseModel = new ResponseModel();
              //  responseModel = gson.fromJson(str1, ResponseModel.class);
                System.out.println("Response ..... "+responseModel.getData());
                if ( responseModel.getCode() == 200 ){
                    System.out.println("Status..... "+responseModel.getStatus());
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
        p.setOther_details("new new");
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
        p1.setOther_details("new n333ew");
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
        p3.setOther_details("neeeeeew new");

        // tempPropertyList = new ArrayList<>();
        mPropertyList.add(p3);
        mProgressBar.setVisibility(View.GONE);
        if(  mPropertyFragment.isVisible()){
            PropertyListAdapter mPropertyListAdapter = new PropertyListAdapter
                    (mPropertyFragment.getContext(), R.layout.landlord_property_row, mPropertyList);
          //  mPropertyListAdapter.notifyDataSetChanged();
            mListView.setAdapter(mPropertyListAdapter);

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
