package project.team.cmpe277.com.magicrentals1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
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

/**
 * Created by Rekha on 4/26/2016.
 */
public class TenantSearchFragment extends android.app.Fragment implements AdapterView.OnItemSelectedListener,GoogleApiClient.OnConnectionFailedListener{

    private EditText locationvalue;
    private EditText keywordvalue;
    private Spinner propertyvalue;
    private Spinner pricerangevalue;
    private Button mSearch;
    AlertDialog actions;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String TAG = "TenSrchF";

    public static SearchParameters sp = new SearchParameters();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage((FragmentActivity) this.getActivity(), GOOGLE_API_CLIENT_ID, this)
                .build();

        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
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
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this.getActivity(), R.array.proptypelist, R.layout.spinner_list);

        propertyvalue.setAdapter(adapter1);

        pricerangevalue = (Spinner) searchview.findViewById(R.id.pricerangevalue);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this.getActivity(), R.array.pricerangelist, R.layout.spinner_list);

        pricerangevalue.setAdapter(adapter2);

        keywordvalue = (EditText) searchview.findViewById(R.id.keywordvalue);
        locationvalue = (EditText) searchview.findViewById(R.id.locationvalue);
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
                    sp.setLocation("");
                } else {

                    //This should be set to current location when no location is mentioned
                    sp.setLocation(s.toString());
                }
            }
        });

        //When Search is initiated

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make an API call to display the search results - get results into PropSingleton
                if(sp.getLocation().toString().equals("")) {
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
                }



                /*Intent i = new Intent(getActivity(), TenantSearchListActivity.class);
                //i.putExtra("USERID", TenantSearchActivity.userid);
                startActivity(i);*/
            }
        });

        return searchview;
    }

    //Get the values from Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.propertyvalue) {
            TextView mytext = (TextView) view;
            sp.setPropertytype(mytext.getText().toString());

        } else {
            if (parent.getId() == R.id.pricerangevalue) {
                TextView mytext = (TextView) view;
                String val[] = mytext.getText().toString().split("-", 2);
                if (val.length > 1) {
                    sp.setMinPrice(Integer.parseInt(val[0]));
                    sp.setMaxPrice(Integer.parseInt(val[1]));
                } else {
                    sp.setMinPrice(Integer.parseInt(val[0]));
                    sp.setMaxPrice(Integer.MAX_VALUE);
                }


            }
        }
    }

    //When nothing is selected  in Spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        sp.setPropertytype("");
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
                return true;
            case R.id.createpost:
                //savani your activity - to create a post
                return true;
            case R.id.mypostings:
                //savani your activity to list the owner's previous posts if exists
                return true;
        }

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.save_search) {
            //make api call to save the search agent - only one search agent - update existing search agent
            actions.show();
            return true;
        }*/

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

                sp.setLocation(location);
            }
        });
    }

}
