package project.team.cmpe277.com.magicrentals.landlord;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by savani on 5/5/16.
 */
public  class LandlordUtils {


        public static boolean isValidEmail(String email) {
            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

        // validating password with retype password
        private boolean isValidPassword(String pass) {
            if (pass != null && pass.length() > 6) {
                return true;
            }
            return false;
        }


}
