package project.team.cmpe277.com.magicrentals1.landlord;

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

    public static PropertiesResultLab getPropertiesResultLabNew(Context context){
        sPropertiesResultLab = new PropertiesResultLab(context.getApplicationContext());
        return  sPropertiesResultLab;
    }

    public  void delete(ArrayList<Integer> al){
        //call service..
        System.out.println("Delete......   "+sPropertiesResultLab.mPropertyList.get(1).nickname);



        System.out.println("del......"+al.get(0)+"... row ...." +sPropertiesResultLab.mPropertyList.remove(1));
        //sPropertiesResultLab.mPropertyList.get(1);


    }

    public void cancel(ArrayList<Integer> al){

    }

    public ArrayList<PropertyModel> getPropertyList(){
        return mPropertyList;
    }

    private ArrayList<PropertyModel> retrieveProperties(){
         ArrayList<PropertyModel> tempPropertyList;
        PropertyModel p = new PropertyModel();
        p.setKey("001");
        p.setNickname("First House");
        p.setStreet("2u28 duu");
        p.setCity("San Jose");
        p.setState("CA");
        p.setZip("83993");
        p.setView_count("10");
        p.setProperty_type("Villa");
        p.setBath("2");
        p.setView_count("123");
        p.setRent("12333");
        p.setArea("2222");
        p.setBath("2");
        p.setRoom("3");
        p.setDescription("sjsjsjs ejej");
        tempPropertyList = new ArrayList<>();
        tempPropertyList.add(p);
        PropertyModel p1 = new PropertyModel();
        p1.setKey("002");
        p1.setNickname("Sec House");
        p1.setStreet("2u2jj8 duu");
        p1.setCity("San Joskke");
        p1.setState("CAjj");
        p1.setZip("8j993");
        p1.setView_count("39");
        p1.setView_count("100");
        p1.setRent("12333");
        p1.setArea("2222");
        p1.setBath("2");
        p1.setRoom("3");
        p1.setDescription("hi jjfjf lfo");
       // tempPropertyList = new ArrayList<>();
        tempPropertyList.add(p1);

        PropertyModel p3 = new PropertyModel();
        p3.setKey("003");
        p3.setNickname("Third House");
        p3.setStreet("2u2jejjjjj8 duu");
        p3.setCity("Seeean Joskke");
        p3.setState("C ejAjj");
        p3.setZip("82kkj993");
        p3.setView_count("334");
        p3.setView_count("144");
        p3.setRent("12333");
        p3.setArea("2222");
        p3.setBath("2");
        p3.setRoom("3");
        p3.setDescription("sjkkks dfj heii oo");
        // tempPropertyList = new ArrayList<>();
        tempPropertyList.add(p3);


        return tempPropertyList;


    }

}
