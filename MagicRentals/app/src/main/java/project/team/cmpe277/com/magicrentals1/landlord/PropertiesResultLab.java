package project.team.cmpe277.com.magicrentals1.landlord;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.HashMap;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

/**
 * Created by savani on 4/26/16.
 */
public class PropertiesResultLab implements TaskCompletedStatus{
    private ArrayList<PropertyModel> mPropertyList;
    private static PropertiesResultLab sPropertiesResultLab;
    private Context mAppContext;
    private static final String TAG = "LandlordPropertLab";

    private PropertiesResultLab(Context appContext){
        mAppContext = appContext;
        mPropertyList = new ArrayList<>();

       // mPropertyList = retrieveProperties();

    }

    public static PropertiesResultLab getPropertiesResultLab(Context context){
        if(sPropertiesResultLab == null){
            sPropertiesResultLab = new PropertiesResultLab(context.getApplicationContext());
        }
        return sPropertiesResultLab;
    }

    public static PropertiesResultLab getPropertiesResultLabNew(Context context){
        sPropertiesResultLab = new PropertiesResultLab(context.getApplicationContext());
        return  sPropertiesResultLab;
    }

    public  boolean rented(ArrayList<Integer> al, Context context){
        //call service..

        System.out.println("Rented......   "+sPropertiesResultLab.mPropertyList.get(1).nickname);
        HashMap<String, String> hm= new HashMap<>();
       // hm.put("user_id", mPropertyList.get(al.get(0)).getUser_id());
        hm.put("user_id", "savaniffwffyyfggq12345");
       // hm.put("id",sPropertiesResultLab.mPropertyList.get(al.get(0)).getKey());
        hm.put("Status","Rented");
       // String url = "http://54.153.2.150:3000/updateStatus";
        String url = "http://192.168.1.173:3000/updateStatus";
        new MultipartUtilityAsyncTask(context, hm, null).execute(url);
        Boolean b = true;
        return b;

    }

    public boolean cancel(ArrayList<Integer> al, Context context){
        HashMap<String, String> hm= new HashMap<>();
        hm.put("user_id", sPropertiesResultLab.mPropertyList.get(al.get(0)).getUser_id());
        hm.put("id",sPropertiesResultLab.mPropertyList.get(al.get(0)).getKey());
        hm.put("Status","Cancelled");
       // String url = "http://54.153.2.150:3000/updateStatus";
        String url = "http://192.168.1.173:3000/updateStatus";
       // new MultipartUtilityAsyncTask(hm, null).execute(url);
        new MultipartUtilityAsyncTask(context, hm, null).execute(url);
        Boolean b = true;
        int a = al.get(0);
        System.out.println("array list length... "+al.size()+ "arrray value ...  "+al.get(0));
        Log.i(TAG, "value of index --- "+a);
        PropertyModel pm = sPropertiesResultLab.mPropertyList.remove(a);
        Log.i(TAG, "value of deleted .... "+pm.getNickname());

        return b;

    }

    public ArrayList<PropertyModel> getPropertyList(){
        return mPropertyList;
    }

    private ArrayList<PropertyModel> retrieveProperties(){

     //   new PropertiesListAsyncTask("savani").execute("savani");


//        try {
//            URL url = new URL(str);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            //conn.getC
//            int response  = conn.getResponseCode();
//            Log.i(TAG, "connection to api");
//            System.out.println("connected  "+response);
//          //  conn.setRequestProperty("User-Agent", USER_AGENT));
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //String requestUrl = ;
        HashMap<String, String> hm= new HashMap<>();
        hm.put("user_id","savani");
//
        String url = "http://192.168.1.173:3000/updateStatus";
     //    new MultipartUtilityAsyncTask(hm, null).execute(url);
        new MultipartUtilityAsyncTask(mAppContext, hm, null).execute(url);

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
        tempPropertyList.add(p);
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
        tempPropertyList.add(p1);

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
        tempPropertyList.add(p3);


        return tempPropertyList;


    }
    public void onTaskCompleted(JSONObject jsonObject){
        //
        Log.i(TAG, "Response---- "+jsonObject );
    }

}
