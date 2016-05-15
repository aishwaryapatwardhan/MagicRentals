package project.team.cmpe277.com.magicrentals1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
import project.team.cmpe277.com.magicrentals1.utility.ThumbnailDownloader;

/**
 * Created by Rekha on 4/27/2016.
 */
public class TenantSearchListFragment extends Fragment {

    private GridViewAdapter gridViewAdapter;

    private GridView gridView;
    private String userid;
    private static int rate;
    AlertDialog actions;


    static ThumbnailDownloader<ImageView> mThumbnailThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString("USERID",null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select notification frequency");
        String[] options = { "Real-time", "Daily", "Weekly" };
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();

        mThumbnailThread = new ThumbnailDownloader<ImageView>(new Handler());
        mThumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                if (isVisible()) {
                    imageView.setImageBitmap(thumbnail);
                }
            }
        });
        mThumbnailThread.start();
        mThumbnailThread.getLooper();
    }

    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0: rate = 1;
                    new SaveSearch().execute();
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent setup with real-time notification", Toast.LENGTH_LONG).show();
                    break;
                case 1: rate = 2;
                    new SaveSearch().execute();
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent setup with daily notification", Toast.LENGTH_LONG).show();
                    break;
                case 2: rate = 3;
                    new SaveSearch().execute();
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

        View searchlistview = inflater.inflate(R.layout.searchlistfragment_tenant, container, false);
        gridView = (GridView)searchlistview.findViewById(R.id.gridview);

        final ArrayList<GridImageDetailItem> gridImageItems = PropSingleton.get(this.getActivity()).getGridImageDetailItems();

        if(gridImageItems!=null && gridImageItems.size() == 0){


        }else {
            gridViewAdapter = new GridViewAdapter(getActivity(), R.layout.property_grid, gridImageItems);
            gridView.setAdapter(gridViewAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //GridImageDetailItem item = (GridImageDetailItem) parent.getItemAtPosition(position);
                    Intent i = new Intent(getActivity(), TenantDetailPagerActivity.class);
                    i.putExtra("USERID", TenantSearchListActivity.userid);
                    i.putExtra("POSITION", position);
                    startActivity(i);
                }
            });
        }

        return searchlistview;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailThread.quit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailThread.clearQueue();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
    }

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
            case R.id.favorites:
                //favourites activity
                Intent i = new Intent(getActivity().getApplicationContext(), TenantsFavActivity.class);
                startActivity(i);
                return true;
            case R.id.save_search:
                actions.show();
                //api call
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SaveSearch extends AsyncTask<Object, Void, JSONObject> {


        @Override
        protected JSONObject doInBackground(Object... parameters){

            SearchParameters sp = SearchParameters.getSearchParameters();
            HttpURLConnection httpConn;
            // String str = "http://54.153.2.150:3000/getPostsByUser?userid="+"savani";
            String str = LoginActivity.urlip+"/saveSearch?user_id="+userid+"&saveSearch=true&description="+sp.getKeywords()+"&City="+sp.getCity()+"&Zip="+sp.getZipcode()+"&property_type="+sp.getPropertytype()+"&min_rent="+sp.getMinPrice()+"&max_rent="+sp.getMaxPrice()+"&street="+sp.getStreet()+"&rate="+rate;
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
