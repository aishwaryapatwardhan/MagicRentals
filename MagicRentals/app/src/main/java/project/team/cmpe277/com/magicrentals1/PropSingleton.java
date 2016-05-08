package project.team.cmpe277.com.magicrentals1;

import android.content.Context;

import java.util.ArrayList;


/**
 * Created by Rekha on 5/8/2016.
 */
public class PropSingleton {


        private static PropSingleton propSingleton;
        private Context mAppContext;
        private ArrayList<GridImageDetailItem> mgridImageDetailItem;

        private PropSingleton(Context appContext) {
            mAppContext = appContext;
            mgridImageDetailItem = new ArrayList<GridImageDetailItem>();
        }

        public static PropSingleton get(Context c) {
            if (propSingleton == null) {
                propSingleton = new PropSingleton(c.getApplicationContext());
            }
            return propSingleton;
        }

        public ArrayList<GridImageDetailItem> getGridImageDetailItems(){
            return mgridImageDetailItem;
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

        /*public void addFav(RestaurantAttr res){
            restaurantAttrs.add(res);
        }*/


}
