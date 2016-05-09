package project.team.cmpe277.com.magicrentals1;

/**
 * Created by Rekha on 4/26/2016.
 */
public class SearchParameters {

    private int minPrice;
    private int maxPrice;
    private String propertytype;
    private String keywords;
    private String location;

    public SearchParameters() {
        this.location = "";
        this.keywords = "";
        this.propertytype = "";
        this.minPrice = 0;
        this.maxPrice = 0;
    }

    public void setPropertytype(String propertytype) {
        this.propertytype = propertytype;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPropertytype() {
        return propertytype;
    }

    public String getLocation() {
        return location;
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
