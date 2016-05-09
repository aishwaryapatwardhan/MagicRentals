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
            mgridImageDetailItem.add(new GridImageDetailItem("$100","1","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","2","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","3","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","4","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","5","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","6","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","7","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","8","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","9","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","10","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","11","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","12","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","13","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","14","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","15","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
            mgridImageDetailItem.add(new GridImageDetailItem("$100","16","190 RYland Street","San JOse","isdhfis","13913803","Condo","10","2","100", "2000","gesghekslgjhewgjldsjvdslbndklnbldkbhreiohgeriogewropgjds;lvsdmvlsrjboeriheiroghsogjvdsop", "2913","hkdsjhdigh","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg","jfjfwo","vjoesigjieosjgs"));
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
