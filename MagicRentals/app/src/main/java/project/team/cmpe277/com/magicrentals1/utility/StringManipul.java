package project.team.cmpe277.com.magicrentals1.utility;

/**
 * Created by Rekha on 5/12/2016.
 */
public class StringManipul {

    public static String replaceSpaces(String strg) {
        int spaceCount = 0, newLength = 0, i = 0;
        int length = strg.length();
        char[] str = strg.toCharArray();

        for (i = 0; i < length; i++) {
            if (str[i] == ' ')
                spaceCount++;
        }

        newLength = length + (spaceCount * 2);
        str[newLength] = '\0';
        for (i = length - 1; i >= 0; i--) {
            if (str[i] == ' ') {
                str[newLength - 1] = '0';
                str[newLength - 2] = '2';
                str[newLength - 3] = '%';
                newLength = newLength - 3;
            } else {
                str[newLength - 1] = str[i];
                newLength = newLength - 1;
            }
        }
        return str.toString();
    }

    public static String replace(String str) {
        return str.replaceAll(" ", "%20");
    }
}
