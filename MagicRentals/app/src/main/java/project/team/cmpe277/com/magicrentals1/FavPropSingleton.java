package project.team.cmpe277.com.magicrentals1;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Raghu on 5/8/2016.
 */
public class FavPropSingleton {


    private static FavPropSingleton favPropSingleton;
    private Context mAppContext;
    private ArrayList<GridImageDetailItem> mgridImageDetailItem;
    private HashMap<String,String> myref;

    private FavPropSingleton(Context appContext) {
        mAppContext = appContext;
        mgridImageDetailItem = new ArrayList<GridImageDetailItem>();

    }

    public static FavPropSingleton get(Context c) {
        if (favPropSingleton == null) {
            favPropSingleton = new FavPropSingleton(c.getApplicationContext());
        }
        return favPropSingleton;
    }

    public ArrayList<GridImageDetailItem> getGridImageDetailItems(){
        return mgridImageDetailItem;
    }

    public void setGridImageDetailItems(ArrayList<GridImageDetailItem> list){
        mgridImageDetailItem = list;
    }

    public GridImageDetailItem getGridImageDetailItem(String refid){
        for(GridImageDetailItem rs: mgridImageDetailItem){
            if (rs.getId().equals(refid)){
                return rs;
            }
        }
        return null;
    }

    public void clearList(){
        mgridImageDetailItem.clear();
    }

    public int getSizeofArray(){
        return mgridImageDetailItem.size();
    }

    public HashMap<String,String> createHaspMap() {
        if (mgridImageDetailItem != null) {
            if (mgridImageDetailItem.size() > 0) {
                for (GridImageDetailItem rs : mgridImageDetailItem) {
                    myref.put(rs.getId(), rs.getId());
                }
            }

        }
        return myref;
    }


}
