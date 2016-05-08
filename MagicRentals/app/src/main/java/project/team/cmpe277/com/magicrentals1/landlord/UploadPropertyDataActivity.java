package project.team.cmpe277.com.magicrentals1.landlord;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import project.team.cmpe277.com.magicrentals1.R;

/**
 * Created by savani on 5/4/16.
 */
public class UploadPropertyDataActivity extends AppCompatActivity {
    static String userid;
    private static final String ERROR = "Please fill required entries";
    private static final String REQUIRED = "required";
    Button btnSubmit;
   // FloatingActionButton btnSubmit;
    PropertyModel property;
    Boolean isValid = true;
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
        area.addTextChangedListener(new TextValidator(area));
        street = (EditText)findViewById(R.id.street);
        street.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = street.getText().toString();
                street.setError(null);
                System.out.println("validationnnn  main "+text+"   value");

                // length 0 means there is no text
                if (text.length() == 0) {
                    System.out.println("validationnnn  main required ");
                    street.setError("required");
                }
            }


        });
        city = (EditText)findViewById(R.id.city);
        city.addTextChangedListener(new TextValidator(city));
        state = ((EditText)findViewById(R.id.state));
        state.addTextChangedListener(new TextValidator(state));
        zip = (EditText)findViewById(R.id.zipcode);
        zip.addTextChangedListener(new TextValidator(zip));
        rent = (EditText)findViewById(R.id.rent);
        rent.addTextChangedListener(new TextValidator(rent));
        email = (EditText)findViewById(R.id.email);
        email.addTextChangedListener(new TextValidator(email));
        mobile = (EditText)findViewById(R.id.phone);
        description = (EditText)findViewById(R.id.description);
        spinPropertyType = (Spinner)findViewById(R.id.property_type);
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
                isValid = true;
                System.out.println("in Savkwjjetwe........................."+v.getId());
                //  Spinner spinProprtyType = (Spinner)v.findViewById(R.id.property_type);
                property.setProperty_type(spinPropertyType.getSelectedItem().toString());
                System.out.println("print     "+property.getProperty_type());
                validate(area);
//                if (area.getText().toString().length() == 0 )
//                    area.setError("Area is required");
                property.setArea(area.getText().toString());
                validate(street);
                property.setStreet(street.getText().toString());
                validate(city);
                property.setCity(city.getText().toString());
                validate(state);
                property.setState(state.getText().toString());
                isValid = LandlordUtils.isValidZip(zip.getText().toString()) ;
                property.setZip(zip.getText().toString());
                validate(rent);
                property.setRent(rent.getText().toString());

                if (!LandlordUtils.isValidEmail(email.getText().toString()))
                { email.setError("Invalid Email"); }

                property.setEmail(email.getText().toString());

                property.setMobile(mobile.getText().toString());

                property.setDescription(description.getText().toString());

                property.setRoom(rooms.getSelectedItem().toString());

                property.setBath(bath.getSelectedItem().toString());

                if(isValid){
//call service
                    Toast.makeText(UploadPropertyDataActivity.this, "Fine" , Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(UploadPropertyDataActivity.this, ERROR , Toast.LENGTH_LONG).show();

                //  convert into json and call service..

                //on success
//                PropertiesResultLab.getPropertiesResultLabNew(getApplicationContext());
//                Intent i = new Intent(getApplicationContext(), PropertiesListLandlordActivity.class);
//                i.putExtra("USERID", userid);
//                startActivity(i);



                // property.setArea(v.findViewById(R.id.area));

                //call service



            }
        } );
    }
    void validate(EditText etext){
        if (etext.getText().toString().length() == 0 ){
            isValid = false;
            etext.setError(REQUIRED);
        }

    }
}
