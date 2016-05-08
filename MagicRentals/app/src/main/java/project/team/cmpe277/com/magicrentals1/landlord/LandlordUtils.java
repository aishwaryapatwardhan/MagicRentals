package project.team.cmpe277.com.magicrentals1.landlord;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by savani on 5/5/16.
 */
public  class LandlordUtils {
    private static final String ZIP_REGEX = "\\d{5}";

        public static boolean isValidEmail(String email) {
            String EMAIL_PATTERN = ".+\\@.+\\..+";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

    public  static boolean isValidZip(String zip){
        return Pattern.matches(ZIP_REGEX, zip);
    }

}
