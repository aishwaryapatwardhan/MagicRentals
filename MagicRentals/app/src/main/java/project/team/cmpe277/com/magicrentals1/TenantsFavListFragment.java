package project.team.cmpe277.com.magicrentals1;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.utility.ThumbnailDownloader;

public class TenantsFavListFragment extends android.app.Fragment {

    private favGridViewAdapter favGridViewAdapter;
    private GridView favGridView;
    private ArrayList<FavPropertieDetails> favPropCollections; //= PropSingleton.get(getActivity()).getGridImageDetailItems();

    static ThumbnailDownloader<ImageView> mThumbnailThread;

    public TenantsFavListFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View favListView = inflater.inflate(R.layout.fragment_tenants_fav_list, container, false);

        favPropCollections = FavPropCollections.getFavList();

        favGridView = (GridView) favListView.findViewById(R.id.favGrid);
        favGridViewAdapter =  new favGridViewAdapter(getActivity(),R.layout.property_grid);
        favGridView.setAdapter(favGridViewAdapter);

        favGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.print("Item cliecked");
            }
        });
        return favListView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailThread.quit();
    }
}
