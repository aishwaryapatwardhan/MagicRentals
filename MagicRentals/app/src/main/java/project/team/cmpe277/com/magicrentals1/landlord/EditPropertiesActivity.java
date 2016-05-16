package project.team.cmpe277.com.magicrentals1.landlord;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.LoginActivity;
import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

/**
 * Created by savani on 5/5/16.
 */
public class EditPropertiesActivity extends AppCompatActivity implements TaskCompletedStatus {

    private static final String ERROR = "Please fill required entries";
    private static final String REQUIRED = "required";
    Boolean isValid = true;
    ArrayList<PropertyModel> mPropertyList;
    PropertiesResultLab mPropertyResultLab;
    PropertyModel mPropertyModel;
   // ArrayList<String> listPropertyTypes;
    //  ArrayList<String> bathroomArr;
    ArrayList<String> roomArr;
    EditText area, street, city, state, zip, rent, email, mobile, description, other_details, nickname;
    Spinner SpinPropertyType, bath, rooms;
    Button btnSubmit;
    String userid;
    private static final String TAG = "EditPropertiesActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_data);
        SharedPreferences sharedPreferences = getSharedPreferences("USER",Context.MODE_PRIVATE);
        userid = sharedPreferences.getString(LoginActivity.USERID,null);
        String key = getIntent().getExtras().getString("key");


       // userid = preferences.getString(LoginActivity.USERID,null);
     //   int selectedLine = preferences.getInt("selectedLine",0);
        Log.i(TAG, "Line selected .... "+key);
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(getApplicationContext());
        mPropertyList = mPropertyResultLab.getPropertyList();
        System.out.println("Size..... "+mPropertyList.size());
        //

        for (PropertyModel property : mPropertyList) {
            if (property.getKey().equals(key)) {
                System.out.println(property.getKey());
                mPropertyModel = property;
                break;
            }
        }
        if(mPropertyModel != null) {
            System.out.println("Modelll........  +++ " + mPropertyModel.getNickname() + "djdjd  .. " + userid);
            Spinner spinProprtyType = (Spinner) findViewById(R.id.property_type);
            //  R.string-spinProprtyType.get
//        String tempProperty = mPropertyModel.getProperty_type().toString();
//            spinProprtyType.setSelection(PropertyTypeE.mPropertyModel.);

            //   spinProprtyType.setSelection(listPropertyTypes.indexOf(mPropertyModel.getProperty_type()));

            area = (EditText) findViewById(R.id.area);
            area.setText(mPropertyModel.getArea());
            street = (EditText) findViewById(R.id.street);
            street.setText(mPropertyModel.getStreet());
            city = (EditText) findViewById(R.id.city);
            city.setText(mPropertyModel.getCity());
            state = ((EditText) findViewById(R.id.state));
            state.setText(mPropertyModel.getState());
            zip = (EditText) findViewById(R.id.zipcode);
            zip.setText(mPropertyModel.getZip());
            rent = (EditText) findViewById(R.id.rent);
            rent.setText(mPropertyModel.getRent());
            email = (EditText) findViewById(R.id.email);
            email.setText(mPropertyModel.getEmail());
            mobile = (EditText) findViewById(R.id.phone);
            mobile.setText(mPropertyModel.getMobile());
            description = (EditText) findViewById(R.id.description);
            description.setText(mPropertyModel.getDescription());
            other_details = (EditText) findViewById(R.id.other_details);
            other_details.setText(mPropertyModel.getOther_details());
            nickname = (EditText) findViewById(R.id.nickname);
            nickname.setText(mPropertyModel.getNickname());
            rooms = (Spinner) findViewById(R.id.roomscount);
            Log.i(TAG, Integer.parseInt(mPropertyModel.getRoom()) + "rooms");
            rooms.setSelection(Integer.parseInt(mPropertyModel.getRoom()) - 1);//roomArr.indexOf(mPropertyModel.getRoom()));
            bath = (Spinner) findViewById(R.id.bathroomcount);
            rooms.setSelection(Integer.parseInt(mPropertyModel.getBath()) - 1);
            // spinProprtyType.setSelection(3);//roomArr.indexOf(mPropertyModel.getBath()));
            //System.out.println("Hello    ......................."+property);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.submit1:
                isValid = true;

                validate(area);
                validate(street);
                validate(city);
                validate(state);
                validate(rent);
                LandlordUtils.isValidEmail(email.getText().toString());
                LandlordUtils.isValidZip(zip.getText().toString());

                mPropertyModel.setNickname(nickname.getText().toString());
                mPropertyModel.setArea(area.getText().toString());

                mPropertyModel.setStreet(street.getText().toString());

                mPropertyModel.setCity(city.getText().toString());

                mPropertyModel.setState(state.getText().toString());

                mPropertyModel.setZip(zip.getText().toString());

                mPropertyModel.setRent(rent.getText().toString());

                mPropertyModel.setEmail(email.getText().toString());

                mPropertyModel.setMobile(mobile.getText().toString());

                mPropertyModel.setDescription(description.getText().toString());

                mPropertyModel.setRoom(rooms.getSelectedItem().toString());

                mPropertyModel.setBath(bath.getSelectedItem().toString());

                mPropertyModel.setOther_details(other_details.getText().toString());

                System.out.println("Hello    ......................."+mPropertyModel + mPropertyModel.getBath());
                //  convert into json and call service..


                HashMap<String, String> hm = LandlordUtils.serialize(mPropertyModel);

                hm.put("_id", mPropertyModel.getKey());
                // HashMap<String, File> file = new HashMap<String, File>();
                String url = getString(R.string.url)+"/updatePostings";
                new MultipartUtilityAsyncTask(EditPropertiesActivity.this,hm, null).execute(url);
                //   PropertiesResultLab.getPropertiesResultLabNew(getApplicationContext());
//                Intent i = new Intent(getApplicationContext(), PropertiesListLandlordActivity.class);
//                i.putExtra("USERID", userid);
//                startActivity(i);
                EditPropertiesActivity.this.finish();
                break;
            case R.id.cancelform:
                this.finish();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addproperty, menu);
        return true;
    }

    void validate(EditText etext){
        if (etext.getText().toString().length() == 0 ){
            isValid = false;
            etext.setError(REQUIRED);
        }

    }


    public void onTaskCompleted(JSONObject jsonObject){
        //
        Log.i(TAG, "Response---- "+jsonObject );
        try {
            if(jsonObject.getInt("code") == 200){
                Toast.makeText(EditPropertiesActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(EditPropertiesActivity.this, "Error! Please try again.", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
