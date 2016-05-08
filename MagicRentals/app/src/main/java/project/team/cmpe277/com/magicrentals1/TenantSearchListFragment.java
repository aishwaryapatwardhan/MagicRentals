package project.team.cmpe277.com.magicrentals1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.utility.ThumbnailDownloader;

/**
 * Created by Rekha on 4/27/2016.
 */
public class TenantSearchListFragment extends android.app.Fragment {

    private GridViewAdapter gridViewAdapter;
    private GridView gridView;
    final ArrayList<GridImageItem> gridImageItems = new ArrayList<>();

    static ThumbnailDownloader<ImageView> mThumbnailThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        getData();
        gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.property_grid,getData());
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridImageItem item = (GridImageItem)parent.getItemAtPosition(position);
                Intent i = new Intent(getActivity(),TenantSearchDetailActivity.class);
                i.putExtra("USERID",TenantSearchListActivity.userid);
                i.putExtra("UUID",item.getId());
                startActivity(i);
            }
        });

        return searchlistview;
    }

    private ArrayList<GridImageItem> getData(){

        for(int i = 0; i < 4; i++){
            //new UrlActivity().execute("$100","190 Ryland Street","http://s3-media3.fl.yelpcdn.com/bphoto/nQK-6_vZMt5n88zsAS94ew/ms.jpg");
            gridImageItems.add(new GridImageItem("http://s3-media3.fl.yelpcdn.com/bphoto/nQK-6_vZMt5n88zsAS94ew/ms.jpg","190 Ryland Street","$101"));
            gridImageItems.add(new GridImageItem("http://s3-media3.fl.yelpcdn.com/bphoto/nQK-6_vZMt5n88zsAS94ew/ms.jpg","190 Ryland Street","$101"));
            gridImageItems.add(new GridImageItem("http://s3-media3.fl.yelpcdn.com/bphoto/nQK-6_vZMt5n88zsAS94ew/ms.jpg","190 Ryland Street","$101"));
            gridImageItems.add(new GridImageItem("http://s3-media3.fl.yelpcdn.com/bphoto/nQK-6_vZMt5n88zsAS94ew/ms.jpg","190 Ryland Street","$101"));
        }
        return gridImageItems;
    }

    /*public class UrlActivity extends AsyncTask<Object, Void, Bitmap> {
        private Exception exception;
        Bitmap mybitmap;
        String address;
        String price;


        public Bitmap doInBackground(Object... parameters) {

            try {
                    address = parameters[1].toString();
                    price = parameters[0].toString();
                    String urlStrng = (String) parameters[2];
                    URL url = new URL(urlStrng);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    mybitmap = BitmapFactory.decodeStream(input);
                    gridImageItems.add(new GridImageItem(mybitmap,address,price));
                    input.reset();
                return mybitmap;
            } catch (IOException e) {
                // Log exception
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmaps) {
            gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.property_grid,gridImageItems);
            gridView.setAdapter(gridViewAdapter);
        }
    }*/

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
}
