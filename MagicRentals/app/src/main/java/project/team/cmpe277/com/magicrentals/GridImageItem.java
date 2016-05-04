package project.team.cmpe277.com.magicrentals;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by Rekha on 4/29/2016.
 */
public class GridImageItem {

    private String image;
    private String address;
    private String price;
    private String id;

    public GridImageItem(String image,String address,String price){
        this.image = image;
        this.address = address;
        this.price = price;
        this.id = "01";
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
