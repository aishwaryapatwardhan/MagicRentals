package project.team.cmpe277.com.magicrentals.landlord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import project.team.cmpe277.com.magicrentals.R;
import project.team.cmpe277.com.magicrentals.landlord.PropertyModel;

/**
 * Created by savani on 5/4/16.
 */
public class UploadPropertyDataActivity extends AppCompatActivity {
    static String userid;
    Button btnSubmit;
    PropertyModel property;
    Spinner spinPropertyType;
    EditText area, street, city, state, zip, rent, email, mobile, description;
    Spinner SpinPropertyType, bath, rooms;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_data);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        //System.out.println("in Savkwjjetwe........................."+v.getId());

        area = (EditText) findViewById(R.id.area);
         street = (EditText)findViewById(R.id.street);
         city = (EditText)findViewById(R.id.city);
         state = ((EditText)findViewById(R.id.state));
         zip = (EditText)findViewById(R.id.zipcode);
         rent = (EditText)findViewById(R.id.rent);
         email = (EditText)findViewById(R.id.email);
         mobile = (EditText)findViewById(R.id.phone);
         description = (EditText)findViewById(R.id.description);
         rooms = (Spinner)findViewById(R.id.roomscount);
         bath = (Spinner)findViewById(R.id.bathroomcount);

        property = new PropertyModel();


        userid = getIntent()
                .getSerializableExtra("USERID").toString();

        btnSubmit = (Button) findViewById(R.id.submit1);
        System.out.println("hiii         ...........  ");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Read the data from the form and pass it to the backend service
                // Button btnSubmit = (Button) (v);
                System.out.println("in Savkwjjetwe........................."+v.getId());
              //  Spinner spinProprtyType = (Spinner)v.findViewById(R.id.property_type);
               // property.setProperty_type(spinProprtyType.getSelectedItem().toString());


                property.setArea(area.getText().toString());

                property.setStreet(street.getText().toString());

                property.setCity(city.getText().toString());

                property.setState(state.getText().toString());

                property.setZip(zip.getText().toString());

                property.setRent(rent.getText().toString());

                property.setEmail(email.getText().toString());

                property.setMobile(mobile.getText().toString());

                property.setDescription(description.getText().toString());

                property.setRoom(rooms.getSelectedItem().toString());

                property.setBath(bath.getSelectedItem().toString());
                System.out.println("Hello    ......................."+property + property.getBath());
              //  convert into json and call service..
                PropertiesResultLab.getPropertiesResultLabNew(getApplicationContext());
                Intent i = new Intent(getApplicationContext(), PropertiesListLandlordActivity.class);
                i.putExtra("USERID", userid);
                startActivity(i);


                //   CheckBox ch1=(CheckBox)v.findViewById(R.id.condo);
                // if (ch1.isChecked())
                // property.setProperty_type(String.valueOf(R.string.condo));


                // property.setArea(v.findViewById(R.id.area));

                //call service




                //user_id , Street , City, State  , Zip    , property_type ,
                // bath , room , area , rent  , email , Mobile , description ,
                // Images (need to decide this yet..), other_details ,  Status , view_count


            }
        } );
    }
}
