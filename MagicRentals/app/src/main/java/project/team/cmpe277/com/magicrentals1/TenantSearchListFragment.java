package project.team.cmpe277.com.magicrentals1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class TenantSearchListFragment extends Fragment {

    private GridViewAdapter gridViewAdapter;

    private GridView gridView;


    static ThumbnailDownloader<ImageView> mThumbnailThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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
        final ArrayList<GridImageDetailItem> gridImageItems = PropSingleton.get(this.getActivity()).getGridImageDetailItems();
        gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.property_grid,gridImageItems);
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
            case R.id.favorites:
                //favourites activity
                return true;
            case R.id.save_search:
                //api call
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
