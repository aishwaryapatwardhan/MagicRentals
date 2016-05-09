package project.team.cmpe277.com.magicrentals1;

import java.util.ArrayList;

/**
 * Created by Rekha on 5/1/2016.
 */
public class GridImageDetailItem {

    private String price;
    private String id;
    private String streetAddr;
    private String cityAddr;
    private String stateAddr;
    private String zipCode;
    private String propertyType;
    private String noOfRooms;
    private String noOfBaths;
    private String sqFoot;
    private String rent;
    private String description;
    private String deposit;
    private String leaseType;
    private String imageIcon;
    private ArrayList<String> imagesPosted;
    private String contact;
    private String email;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GridImageDetailItem(String price, String id, String streetAddr, String cityAddr, String stateAddr, String zipCode, String propertyType, String noOfRooms, String noOfBaths, String sqFoot, String rent, String description, String deposit, String leaseType, String imageIcon,String contact,String email) {

        this.price = price;
        this.id = id;
        this.streetAddr = streetAddr;
        this.cityAddr = cityAddr;
        this.stateAddr = stateAddr;
        this.zipCode = zipCode;
        this.propertyType = propertyType;
        this.noOfRooms = noOfRooms;
        this.noOfBaths = noOfBaths;
        this.sqFoot = sqFoot;
        this.rent = rent;
        this.description = description;
        this.deposit = deposit;
        this.leaseType = leaseType;
        this.imageIcon = imageIcon;
        this.contact = contact;
        this.email = email;
    }
}
