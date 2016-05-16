package project.team.cmpe277.com.magicrentals1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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

import project.team.cmpe277.com.magicrentals1.utility.StringManipul;
import project.team.cmpe277.com.magicrentals1.utility.ThumbnailDownloader;

public class TenantsFavListFragment extends android.support.v4.app.Fragment {

    private FavGridViewAdapter gridViewAdapter;

    private GridView gridView;
    private String userid;
    private static int rate;


    static ThumbnailDownloader<ImageView> mThumbnailThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = preferences.getString(LoginActivity.USERID,null);

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View searchlistview = inflater.inflate(R.layout.searchlistfragment_tenant, container, false);
        gridView = (GridView)searchlistview.findViewById(R.id.gridview);
        new FavApi().execute(container);
        /*final ArrayList<GridImageDetailItem> gridImageItems = FavPropSingleton.get(this.getActivity()).getGridImageDetailItems();
        gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.property_grid,gridImageItems);
        gridView.setAdapter(gridViewAdapter);*/

       /* gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //GridImageDetailItem item = (GridImageDetailItem) parent.getItemAtPosition(position);
                Intent i = new Intent(getActivity(), TenantsFavDetailActivity.class);
                i.putExtra("USERID", TenantSearchListActivity.userid);
                i.putExtra("POSITION", position);
                startActivity(i);
            }
        });*/

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
        inflater.inflate(R.menu.menu_fav, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id){
            case R.id.search_post:
                Intent j = new Intent(getActivity().getApplicationContext(), TenantSearchActivity.class);
                j.putExtra("USERID", userid);
                startActivity(j);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class FavApi extends AsyncTask<Object, Void, JSONObject> {
        ViewGroup v;
        @Override
        protected JSONObject doInBackground(Object... parameters){


            HttpURLConnection httpConn;
            v = (ViewGroup)parameters[0];
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

                final ArrayList<GridImageDetailItem> gridImageItems = FavPropSingleton.get(getActivity()).getGridImageDetailItems();

                if(gridImageItems!=null && gridImageItems.size() == 0){
                    TextView editText = new TextView(getActivity());
                    editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setText("No favourites added");
                    try{
                        v.addView(editText);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else {

                    gridViewAdapter = new FavGridViewAdapter(getActivity(), R.layout.property_grid, gridImageItems);
                    gridView.setAdapter(gridViewAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //GridImageDetailItem item = (GridImageDetailItem) parent.getItemAtPosition(position);
                            Intent i = new Intent(getActivity(), TenantsFavDetailActivity.class);
                            i.putExtra("USERID", TenantSearchListActivity.userid);
                            i.putExtra("POSITION", position);
                            startActivity(i);
                        }
                    });
                }

            }
            catch (Exception ex){
                System.out.print("Hi");
            }
        }

    }


}
