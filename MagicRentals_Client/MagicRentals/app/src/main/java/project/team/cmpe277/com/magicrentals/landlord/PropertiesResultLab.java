package project.team.cmpe277.com.magicrentals.landlord;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by savani on 4/26/16.
 */
public class PropertiesResultLab {
    private ArrayList<PropertyModel> mPropertyList;
    private static PropertiesResultLab sPropertiesResultLab;
    private Context mAppContext;

    private PropertiesResultLab(Context appContext){
        mAppContext = appContext;
        mPropertyList = new ArrayList<>();

        mPropertyList = retrieveProperties();

    }

    public static PropertiesResultLab getPropertiesResultLab(Context context){
        if(sPropertiesResultLab == null){
            sPropertiesResultLab = new PropertiesResultLab(context.getApplicationContext());
        }
        return sPropertiesResultLab;
    }

    public  void delete(ArrayList<Integer> al){

    }

    public ArrayList<PropertyModel> getPropertyList(){
        return mPropertyList;
    }

    private ArrayList<PropertyModel> retrieveProperties(){
         ArrayList<PropertyModel> tempPropertyList;
        PropertyModel p = new PropertyModel();
        p.setNickname("First House");
        p.setStreet("2u28 duu");
        p.setCity("San Jose");
        p.setState("CA");
        p.setZip("83993");
        p.setView_count("10");
        p.setProperty_type("Villa");
        p.setBath("2");
        tempPropertyList = new ArrayList<>();
        tempPropertyList.add(p);
        PropertyModel p1 = new PropertyModel();
        p1.setNickname("Sec House");
        p1.setStreet("2u2jj8 duu");
        p1.setCity("San Joskke");
        p1.setState("CAjj");
        p1.setZip("8j993");
        p1.setView_count("39");
       // tempPropertyList = new ArrayList<>();
        tempPropertyList.add(p1);

        PropertyModel p3 = new PropertyModel();
        p3.setNickname("Third House");
        p3.setStreet("2u2jejjjjj8 duu");
        p3.setCity("Seeean Joskke");
        p3.setState("C ejAjj");
        p3.setZip("82kkj993");
        p3.setView_count("334");
        // tempPropertyList = new ArrayList<>();
        tempPropertyList.add(p3);


        return tempPropertyList;


    }

}
