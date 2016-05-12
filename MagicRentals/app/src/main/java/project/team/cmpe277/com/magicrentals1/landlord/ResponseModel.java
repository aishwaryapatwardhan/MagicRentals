package project.team.cmpe277.com.magicrentals1.landlord;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by savani on 5/10/16.
 */
public class ResponseModel {
    private String status;
    private JSONArray data;
    private int code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
