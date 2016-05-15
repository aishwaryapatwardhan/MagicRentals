package project.team.cmpe277.com.magicrentals1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView.OnItemSelectedListener;
//import android.support.v4.app.ActivityCompat;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.landlord.LandlordUtils;
import project.team.cmpe277.com.magicrentals1.landlord.PropertiesResultLab;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyListAdapter;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyListLandlordFragment;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyModel;
import project.team.cmpe277.com.magicrentals1.landlord.ResponseModel;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.StringManipul;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

/**
 * Created by Rekha on 4/26/2016.
 */
public class TenantSearchFragment extends android.app.Fragment implements AdapterView.OnItemSelectedListener,GoogleApiClient.OnConnectionFailedListener{

    private EditText locationvalue;
    private EditText keywordvalue;
    private Spinner propertyvalue;
    private Spinner pricerangevalue;
    private Button mSearch;
    private EditText zipcodeval;
    AlertDialog actions;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String TAG = "TenSrchF";
    public static HashMap<String,String> searchFilters = new HashMap<String,String>();
    String userid;

    public static SearchParameters sp = SearchParameters.getSearchParameters();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setRetainInstance(true);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString("USERID",null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select notification frequency");
        String[] options = { "Real-time", "Daily", "Weekly" };
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
    }

    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0: // api call with real time
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent setup with real-time notification", Toast.LENGTH_LONG).show();
                    break;
                case 1: // api call with daily
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent setup with daily notification", Toast.LENGTH_LONG).show();
                    break;
                case 2: // weekly
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent setup with weekly notification", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent not setup", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View searchview = inflater.inflate(R.layout.searchfragment_tenant, container, false);


        propertyvalue = (Spinner) searchview.findViewById(R.id.propertyvalue);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this.getActivity(), R.array.property_type1, R.layout.spinner_list);
        propertyvalue.setOnItemSelectedListener(this);
        propertyvalue.setAdapter(adapter1);

        pricerangevalue = (Spinner) searchview.findViewById(R.id.pricerangevalue);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this.getActivity(), R.array.pricerangelist, R.layout.spinner_list);

        pricerangevalue.setAdapter(adapter2);
        pricerangevalue.setOnItemSelectedListener(this);

        keywordvalue = (EditText) searchview.findViewById(R.id.keywordvalue);
        locationvalue = (EditText) searchview.findViewById(R.id.locationvalue);
        zipcodeval = (EditText) searchview.findViewById(R.id.zipcodeval);
        mSearch = (Button) searchview.findViewById(R.id.search);


        keywordvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(null)) {
                    sp.setKeywords("");
                } else {
                    sp.setKeywords(s.toString());
                }
            }
        });


        locationvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(null)) {
                    sp.setCity("");
                } else {

                    //This should be set to current location when no location is mentioned
                    sp.setCity(s.toString());
                }
            }
        });

        zipcodeval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(null)) {
                    sp.setZipcode("");
                } else {

                    //This should be set to current location when no location is mentioned
                    sp.setZipcode(s.toString());
                }
            }
        });

        //When Search is initiated

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(sp.getCity() == null & sp.getZipcode() == null) {
                    if (mGoogleApiClient.isConnected()) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                android.Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    PERMISSION_REQUEST_CODE);
                        } else {
                            callPlaceDetectionApi();
                        }
                    }
                } else{
                    new SearchApi().execute(sp);
                }

            }
        });

        return searchview;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.propertyvalue) {
            TextView mytext = (TextView) view;
            if(mytext.getText().toString().equals("Select type")){
                sp.setPropertytype("");
            }else {
                sp.setPropertytype(mytext.getText().toString());
            }

        } else {
            if (parent.getId() == R.id.pricerangevalue) {
                TextView mytext = (TextView) view;
                if(mytext.getText().toString().equals("Select range")){
                    sp.setMinPrice(0);
                    sp.setMaxPrice(Integer.MAX_VALUE);
                }else {
                    String val[] = mytext.getText().toString().split("-", 2);
                    if (val.length > 1) {
                        sp.setMinPrice(Integer.parseInt(val[0].substring(1)));
                        sp.setMaxPrice(Integer.parseInt(val[1].substring(1)));
                    } else {
                        if(mytext.getText().toString().equals("above $90000")) {
                            sp.setMinPrice(90000);
                            sp.setMaxPrice(Integer.MAX_VALUE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_login, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id){
            case R.id.favorites:
                //favourites activity

                Intent i = new Intent(getActivity().getApplicationContext(), TenantsFavActivity.class);
                startActivity(i);

                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public class GetPostsAPI extends AsyncTask<Object, Void, Boolean> {
        private Exception exception;



        public Boolean doInBackground(Object... parameters) {

            try {

                return true;
            } catch (Exception e) {
                // Log exception
                this.exception = e;
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(getActivity(),
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPlaceDetectionApi();
                }
                break;
        }
    }

    private void callPlaceDetectionApi() throws SecurityException {
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                double likelihoodvalue = 0.0;
                String location = "";
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    Log.i(TAG, String.format("Place '%s' with " +
                                    "likelihood: %g",
                            placeLikelihood.getPlace().getName(),
                            placeLikelihood.getLikelihood()));
                    if(likelihoodvalue > placeLikelihood.getLikelihood()){
                        likelihoodvalue = placeLikelihood.getLikelihood();
                        location = placeLikelihood.getPlace().getName().toString();
                    }
                }
                likelyPlaces.release();
                if(location == ""){
                    location = "San Jose";
                }
                sp.setStreet(location);
                new SearchApi().execute(sp);
            }
        });
    }



    public class SearchApi extends AsyncTask<Object, Void, JSONObject>{

        SearchParameters sP;

        @Override
        protected JSONObject doInBackground(Object... parameters){

            sp = SearchParameters.getSearchParameters();
            HttpURLConnection httpConn;
            // String str = "http://54.153.2.150:3000/getPostsByUser?userid="+"savani";
            String str = LoginActivity.urlip+"/searchPosts?user_id="+userid+"&saveSearch=false&description="+sp.getKeywords()+"&City="+sp.getCity()+"&Zip="+sp.getZipcode()+"&property_type="+sp.getPropertytype()+"&min_rent="+sp.getMinPrice()+"&max_rent="+sp.getMaxPrice()+"&street="+sp.getStreet();
            System.out.println(str);
            str = StringManipul.replace(str);
            System.out.println(str);
            URL url = null;
            JSONObject json = null;
            try {
                url = new URL(str);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader br
                            = new BufferedReader(new InputStreamReader(in));

                    String temp = null;

                    StringBuilder sb = new StringBuilder();

                    while ((temp = br.readLine()) != null){
                        System.out.println("read input stream...."+temp);
                        sb.append(temp);
                    }
                    json = new JSONObject(sb.toString());



                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

            }catch (MalformedURLException e) {
                e.printStackTrace(); } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            JSONObject addr, units, contact;

            try{
                JSONArray jsonarray = result.getJSONArray("data");
                ArrayList<GridImageDetailItem> gdl = new ArrayList<GridImageDetailItem>();

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    addr = jsonobject.getJSONObject("address");
                    units = jsonobject.getJSONObject("units");
                    contact = jsonobject.getJSONObject("Contact_info");

                    GridImageDetailItem gridImageDetailItem = new GridImageDetailItem(jsonobject.getString("_id"), addr.getString("Street"), addr.getString("City"),
                            addr.getString("State"),
                            addr.getString("Zip"), jsonobject.getString("property_type"), units.getString("room"),
                            units.getString("bath"), units.getString("area"), jsonobject.getString("rent"), jsonobject.getString("description"),
                            jsonobject.getString("other_details"), jsonobject.getString("Images"),contact.getString("Mobile"),contact.getString("email"));
                    gridImageDetailItem.setCount(Integer.parseInt(jsonobject.getString("view_count")));
                    gdl.add(gridImageDetailItem);
                }
                PropSingleton.get(getActivity()).clearList();
                PropSingleton.get(getActivity()).setGridImageDetailItems(gdl);

                Intent i = new Intent(getActivity(), TenantSearchListActivity.class);
                startActivity(i);

            }
            catch (Exception ex){
                System.out.print("Hi");
            }
        }

    }


    public class FavApi extends AsyncTask<Object, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(Object... parameters){

            sp = SearchParameters.getSearchParameters();
            HttpURLConnection httpConn;

            String str = LoginActivity.urlip+"/getAllFav?uid="+userid;
            System.out.println(str);
            str = StringManipul.replace(str);
            System.out.println(str);
            URL url = null;
            JSONObject json = null;
            try {
                url = new URL(str);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader br
                            = new BufferedReader(new InputStreamReader(in));

                    String temp = null;

                    StringBuilder sb = new StringBuilder();

                    while ((temp = br.readLine()) != null){
                        System.out.println("read input stream...."+temp);
                        sb.append(temp);
                    }
                    json = new JSONObject(sb.toString());



                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

            }catch (MalformedURLException e) {
                e.printStackTrace(); } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            JSONObject addr, units, contact;

            try{
                JSONArray jsonarray = result.getJSONArray("data");
                ArrayList<GridImageDetailItem> gdl = new ArrayList<GridImageDetailItem>();

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    addr = jsonobject.getJSONObject("address");
                    units = jsonobject.getJSONObject("units");
                    contact = jsonobject.getJSONObject("Contact_info");

                    GridImageDetailItem gridImageDetailItem = new GridImageDetailItem(jsonobject.getString("_id"), addr.getString("Street"), addr.getString("City"),
                            addr.getString("State"),
                            addr.getString("Zip"), jsonobject.getString("property_type"), units.getString("room"),
                            units.getString("bath"), units.getString("area"), jsonobject.getString("rent"), jsonobject.getString("description"),
                            jsonobject.getString("other_details"), jsonobject.getString("Images"),contact.getString("Mobile"),contact.getString("email"));
                    gridImageDetailItem.setCount(Integer.parseInt(jsonobject.getString("view_count")));
                    gdl.add(gridImageDetailItem);
                }
                FavPropSingleton.get(getActivity()).clearList();
                FavPropSingleton.get(getActivity()).setGridImageDetailItems(gdl);

            }
            catch (Exception ex){
                System.out.print("Hi");
            }
        }

    }

    /*@Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }*/
}
