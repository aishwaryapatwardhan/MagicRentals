package project.team.cmpe277.com.magicrentals1.landlord;

/**
 * Created by savani on 4/26/16.
 */
public class PropertyModel {

    String user_id;
    String key;
    String Street;
    String City;
    String State;
    String Zip;
    String property_type;
    String bath = "0";
    String room = "0";
    String area;
    String rent;
    String email;
    String Mobile;
    String description;
    String images;
    String other_details;
    String Status;
    String view_count = "0";
    String nickname;
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getKey(){
        return key;
    }
    public  void setKey(String key){
        this.key = key;
    }

    public void setNickname(String nickname){ this.nickname = nickname; }

    public String getNickname(){ return nickname; }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }





    //user_id , Street , City, State  , Zip    , property_type ,
    // bath , room , area , rent  , email , Mobile , description ,
    // Images (need to decide this yet..), other_details ,  Status , view_count



}
