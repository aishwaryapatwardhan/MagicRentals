package project.team.cmpe277.com.magicrentals1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.utility.StringManipul;

/**
 * Created by Rekha on 5/1/2016.
 */
public class TenantsFavDetailsFragment extends android.support.v4.app.Fragment{


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
    GridImageDetailItem gridImageDetailItem;
    HashMap<String,String> myref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refid = getArguments().getSerializable(IDS).toString();
        gridImageDetailItem = FavPropSingleton.get(getActivity()).getGridImageDetailItem(refid);
        myref = FavPropSingleton.get(getActivity()).createHaspMap();
        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID, null);

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

        streetValue.setText(gridImageDetailItem.getStreetAddr());
        cityValue.setText(gridImageDetailItem.getCityAddr());
        zipcodeValue.setText(gridImageDetailItem.getZipCode());
        stateValue.setText(gridImageDetailItem.getStateAddr());
        proptypeValue.setText(gridImageDetailItem.getPropertyType());
        numRoomsValue.setText(gridImageDetailItem.getNoOfRooms());
        numBathsValue.setText(gridImageDetailItem.getNoOfBaths());
        sqFeetValue.setText(gridImageDetailItem.getSqFoot());
        montlyRentValue.setText(gridImageDetailItem.getRent());
        descriptionValue.setText(gridImageDetailItem.getDescription());
        othersValue.setText(gridImageDetailItem.getOthers());
        contactNumberValue.setText(gridImageDetailItem.getContact());
        contactEmailValue.setText(gridImageDetailItem.getEmail());


        int drawableId = getResources().getIdentifier("solidheart", "drawable", "project.team.cmpe277.com.magicrentals");
        heartsImage.setBackgroundResource(drawableId);


        new UrlActivity().execute(gridImageDetailItem.getImageIcon());

        heartsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RemFav().execute(refid);
                Intent i = new Intent(getActivity().getApplicationContext(), TenantsFavActivity.class);
                startActivity(i);

            }
        });


        return detailview;
    }



    public static android.support.v4.app.Fragment newInstance(String refid) {
        Bundle args = new Bundle();
        args.putSerializable(IDS, refid);
        TenantsFavDetailsFragment fragment = new TenantsFavDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fav, menu);
    }


    public class UrlActivity extends AsyncTask<String,Void,Bitmap> {

        private Exception exception;

        public Bitmap doInBackground(String... urls) {
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

        }

    }

}
