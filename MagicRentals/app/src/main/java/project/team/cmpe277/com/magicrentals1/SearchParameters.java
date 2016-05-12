package project.team.cmpe277.com.magicrentals1;

/**
 * Created by Rekha on 4/26/2016.
 */
public class SearchParameters {

    private int minPrice;
    private int maxPrice;
    private String propertytype;
    private String keywords;
    private String city;
    private String zipcode;
    private String street;

    public SearchParameters() {
        this.street = "";
        this.keywords = "";
        this.propertytype = "";
        this.minPrice = 0;
        this.maxPrice = Integer.MAX_VALUE;
    }

    public void setPropertytype(String propertytype) {
        this.propertytype = propertytype;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }


    public String getPropertytype() {
        return propertytype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getKeywords() {
        return keywords;
    }
    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

}
