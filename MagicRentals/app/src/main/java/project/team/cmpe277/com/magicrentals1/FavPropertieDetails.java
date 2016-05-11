package project.team.cmpe277.com.magicrentals1;

/**
 * Created by Raghu on 5/10/2016.
 */
public class FavPropertieDetails {
    String user_id;
    String Street;
    String City;
    String State;
    String Zip;
    String property_type;
    String bath;
    String room;
    String area;
    String rent;
    String email;
    String Mobile;
    String description;
    String images;
    String other_details;
    String Status;
    //String nickname;

    public FavPropertieDetails(String user_id, String street, String city, String state, String zip, String property_type, String bath, String room, String area, String rent, String email, String mobile, String description, String other_details, String status, String img) {
        this.user_id = user_id;
        Street = street;
        City = city;
        State = state;
        Zip = zip;
        images = img;
        this.property_type = property_type;
        this.bath = bath;
        this.room = room;
        this.area = area;
        this.rent = rent;
        this.email = email;
        Mobile = mobile;
        this.description = description;
        this.other_details = other_details;
        Status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOther_details() {
        return other_details;
    }

    public void setOther_details(String other_details) {
        this.other_details = other_details;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
