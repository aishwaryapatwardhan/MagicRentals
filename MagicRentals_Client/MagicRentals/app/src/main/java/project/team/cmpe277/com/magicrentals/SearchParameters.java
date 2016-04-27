package project.team.cmpe277.com.magicrentals;

/**
 * Created by Rekha on 4/26/2016.
 */
public class SearchParameters {

    private String pricerange;
    private String propertytype;
    private String keywords;
    private String location;

    public void setPricerange(String pricerange) {
        this.pricerange = pricerange;
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

    public String getPricerange() {
        return pricerange;
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

}
