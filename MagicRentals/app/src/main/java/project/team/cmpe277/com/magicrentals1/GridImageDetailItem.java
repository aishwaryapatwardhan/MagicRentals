package project.team.cmpe277.com.magicrentals1;

import java.util.ArrayList;

/**
 * Created by Rekha on 5/1/2016.
 */
public class GridImageDetailItem {
    String streetAddr;
    String cityAddr;
    String stateAddr;
    String zipCode;
    String propertyType;
    String noOfRooms;
    String noOfBaths;
    String sqFoot;
    String rent;
    String description;
    String deposit;
    String leaseType;
    String imageIcon;
    ArrayList<String> imagesPosted;


    public String getStreetAddr() {
        return streetAddr;
    }

    public void setStreetAddr(String streetAddr) {
        this.streetAddr = streetAddr;
    }

    public String getCityAddr() {
        return cityAddr;
    }

    public void setCityAddr(String cityAddr) {
        this.cityAddr = cityAddr;
    }

    public String getStateAddr() {
        return stateAddr;
    }

    public void setStateAddr(String stateAddr) {
        this.stateAddr = stateAddr;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(String noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public String getNoOfBaths() {
        return noOfBaths;
    }

    public void setNoOfBaths(String noOfBaths) {
        this.noOfBaths = noOfBaths;
    }

    public String getSqFoot() {
        return sqFoot;
    }

    public void setSqFoot(String sqFoot) {
        this.sqFoot = sqFoot;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(String leaseType) {
        this.leaseType = leaseType;
    }

    public String getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(String imageIcon) {
        this.imageIcon = imageIcon;
    }

    public ArrayList<String> getImagesPosted() {
        return imagesPosted;
    }

    public void setImagesPosted(ArrayList<String> imagesPosted) {
        this.imagesPosted = imagesPosted;
    }


}
