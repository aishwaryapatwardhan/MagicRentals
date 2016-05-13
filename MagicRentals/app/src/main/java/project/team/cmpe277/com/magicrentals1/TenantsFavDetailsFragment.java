package project.team.cmpe277.com.magicrentals1;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.StringManipul;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

/**
 * Created by Rekha on 5/1/2016.
 */
public class TenantsFavDetailsFragment extends android.app.Fragment {


    //Raghu's Variable
    private String url;


    private TextView streetValue;
    private TextView cityValue;
    private TextView zipcodeValue;
    private TextView stateValue;
    private TextView proptypeValue;
    private TextView numRoomsValue;
    private TextView numBathsValue;
    private TextView sqFeetValue;
    private TextView montlyRentValue;
    private TextView descriptionValue;
    private TextView othersValue;
    private TextView contactNumberValue;
    private TextView contactEmailValue;
    private ImageView heartsImage;
    private ImageView iconImage;
    private Boolean heartflag;
    private static int rate;
    AlertDialog actions;
    private int position;
    public static final String IDS = "project.team.cmpe277.com.magicrentals1.ID";
    String refid;
    String userid;
    FavPropertieDetails favPropertieDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //refid = getArguments().getSerializable("RID").toString();
        refid = TenantsFavDetailActivity.rid;
        ArrayList<FavPropertieDetails> favPropCollections = FavPropCollections.getFavList();
        favPropertieDetails = FavPropCollections.getFavPropertieDetails(refid);

        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString("USERID", null);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View detailview = inflater.inflate(R.layout.searchdetailfragment_tenant, container, false);
        setHasOptionsMenu(true);


        streetValue = (TextView)detailview.findViewById(R.id.streetValue);
        cityValue = (TextView)detailview.findViewById(R.id.cityValue);
        zipcodeValue = (TextView)detailview.findViewById(R.id.zipcodeValue);
        stateValue = (TextView)detailview.findViewById(R.id.stateValue);
        proptypeValue = (TextView)detailview.findViewById(R.id.proptypeValue);
        numRoomsValue = (TextView)detailview.findViewById(R.id.numRoomsValue);
        numBathsValue = (TextView)detailview.findViewById(R.id.numBathsValue);
        sqFeetValue = (TextView)detailview.findViewById(R.id.sqFeetValue);
        montlyRentValue = (TextView)detailview.findViewById(R.id.monthlyRentValue);
        descriptionValue = (TextView)detailview.findViewById(R.id.descriptionValue);
        othersValue = (TextView)detailview.findViewById(R.id.othersValue);
        contactNumberValue = (TextView)detailview.findViewById(R.id.contactNumberValue);
        contactEmailValue = (TextView)detailview.findViewById(R.id.contactEmailValue);
        heartsImage = (ImageView)detailview.findViewById(R.id.heartsImage);
        iconImage = (ImageView)detailview.findViewById(R.id.iconImage);

        streetValue.setText(favPropertieDetails.getStreet());
        cityValue.setText(favPropertieDetails.getCity());
        zipcodeValue.setText(favPropertieDetails.getZip());
        stateValue.setText(favPropertieDetails.getState());
        proptypeValue.setText(favPropertieDetails.getProperty_type());
        numRoomsValue.setText(favPropertieDetails.getRoom());
        numBathsValue.setText(favPropertieDetails.getBath());
        sqFeetValue.setText(favPropertieDetails.getArea());
        montlyRentValue.setText(favPropertieDetails.getRent());
        descriptionValue.setText(favPropertieDetails.getDescription());
        othersValue.setText(favPropertieDetails.getOther_details());
        contactNumberValue.setText(favPropertieDetails.getMobile());
        contactEmailValue.setText(favPropertieDetails.getEmail());


        new UrlActivity().execute(favPropertieDetails.getImages());

        int drawableId = getResources().getIdentifier("solidheart", "drawable", "project.team.cmpe277.com.magicrentals");
        heartsImage.setBackgroundResource(drawableId);

        heartsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RemFav().execute(refid);
                FavPropCollections.getFavList().remove(FavPropCollections.getFavPropertieDetails(refid));
                Toast.makeText(getActivity(),"Item is removed",Toast.LENGTH_LONG).show();
               /* HashMap<String, String> hmap = new HashMap<>();
                hmap.put("uid","Rekha");
                hmap.put("uid","ids");
                new MultipartUtilityAsyncTask(getActivity(),hmap,null).execute(url);*/
                Intent i = new Intent(getActivity().getApplicationContext(), TenantsFavActivity.class);
                startActivity(i);
            }
        });



        return detailview;
    }


/*
    public static android.support.v4.app.Fragment newInstance(String refid) {
        Bundle args = new Bundle();
        args.putSerializable(IDS, refid);
        TenantSearchDetailFragment fragment = new TenantSearchDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

/*
    @Override
    public void onTaskCompleted(JSONObject result) {

        if (heartflag == false) {
            Toast.makeText(getActivity().getApplicationContext(), "Added to favourites", Toast.LENGTH_LONG).show();
            int drawableId = getResources().getIdentifier("solidheart", "drawable", "project.team.cmpe277.com.magicrentals");
            heartsImage.setBackgroundResource(drawableId);
            heartflag = true;
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Removed from favourites", Toast.LENGTH_LONG).show();
            int drawableId = getResources().getIdentifier("shallowheart", "drawable", "project.team.cmpe277.com.magicrentals");
            heartsImage.setBackgroundResource(drawableId);
            heartflag = false;
        }
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id){
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.favorites:
                //favourites activity
                Intent i = new Intent(getActivity().getApplicationContext(), TenantsFavActivity.class);
                startActivity(i);
                return true;
            case R.id.save_search:
                actions.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
    }


    public class UrlActivity extends AsyncTask<String,Void,Bitmap> {

        private Exception exception;
        public Bitmap doInBackground(String... urls){
            Bitmap mybitmap;

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                mybitmap = BitmapFactory.decodeStream(input);

                return mybitmap;
            } catch (IOException e) {
                // Log exception
                this.exception = e;
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap mybitmap) {
            iconImage.setImageBitmap(mybitmap);
        }
    }



    public class RemFav extends AsyncTask<Object, Void, JSONObject> {


        @Override
        protected JSONObject doInBackground(Object... parameters){

            String ids = parameters[0].toString();
            HttpURLConnection httpConn;
            // String str = "http://54.153.2.150:3000/getPostsByUser?userid="+"savani";
            String str = LoginActivity.urlip+"/removeFav?uid="+userid+"&ids="+ids;
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
            int drawableId = getResources().getIdentifier("shallowheart", "drawable", "project.team.cmpe277.com.magicrentals");
            heartsImage.setBackgroundResource(drawableId);
            heartflag = false;
        }

    }



}
