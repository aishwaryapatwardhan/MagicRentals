package project.team.cmpe277.com.magicrentals1.landlord;

import android.widget.EditText;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by savani on 5/5/16.
 */
public  class LandlordUtils {
    private static final String ZIP_REGEX = "\\d{5}";
  //  public static final String url = "http://54.153.2.150:3000";
    public static final String url = "http://10.0.2.2:3000";


        public static boolean isValidEmail(String email) {
            String EMAIL_PATTERN = ".+\\@.+\\..+";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }


    public  static boolean isValidZip(String zip){
        return Pattern.matches(ZIP_REGEX, zip);
    }

    public static HashMap<String, String> serialize(PropertyModel pm){
        HashMap<String, String> hm = new HashMap<>();
       // hm.put("_id",pm.getKey());
        hm.put("user_id", pm.getUser_id());
        hm.put("nickname", pm.getNickname());
        hm.put("City", pm.getCity());
        hm.put("Street", pm.getStreet());
        hm.put("State", pm.getState());
        hm.put("Zip", pm.getZip());
        hm.put("property_type",pm.getProperty_type());
        hm.put("bath", pm.getProperty_type());
        hm.put("room", pm.getProperty_type());
        hm.put("area",pm.getArea());
        hm.put("rent",pm.getRent());
        hm.put("email",pm.getEmail());
        hm.put("Mobile",pm.getMobile());
        hm.put("description",pm.getDescription());
        if(pm.getStatus() == null)
            hm.put("Status","Created");
        else
            hm.put("Status",pm.getStatus());
        System.out.println("email........ "+hm.get("email"));

        return hm;

       // hm.put("",obj.)

    }

}
