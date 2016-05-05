package project.team.cmpe277.com.magicrentals.landlord;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import project.team.cmpe277.com.magicrentals.R;

/**
 * Created by savani on 4/26/16.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class UploadDataFragment extends Fragment {
    Button btnSubmit;
    PropertyModel property;
    Spinner spinPropertyType;


    public UploadDataFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_upload_data, container, false);
        System.out.println("kjjdhdhjhhj .... ");
        String userid = getArguments().getString("USERID");
        System.out.println("userid  ... " +userid);
//        Spinner spinner = (Spinner) fragmentView.findViewById(R.id.property_type);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
//                (fragmentView.getContext(), R.array.property_type,
//                        android.R.layout.list_content);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
        //setContentView(R.layout.main);

        // find View-elements
        //tvOut = (TextView) findViewById(R.id.tvOut);

        btnSubmit = (Button) fragmentView.findViewById(R.id.submit1);
        System.out.println("hiii         ...........  ");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Read the data from the form and pass it to the backend service
                // Button btnSubmit = (Button) (v);
                System.out.println("in Savkwjjetwe.........................");
                Spinner spinProprtyType = (Spinner)v.findViewById(R.id.property_type);
                property.setProperty_type(spinProprtyType.getSelectedItem().toString());
                EditText area;
                area = (EditText)v.findViewById(R.id.area);
                property.setArea(area.getText().toString());
                EditText street = (EditText)v.findViewById(R.id.street);
                property.setStreet(street.getText().toString());
                EditText city = (EditText)v.findViewById(R.id.city);
                property.setCity(city.getText().toString());
                EditText state = ((EditText)v.findViewById(R.id.state));
                property.setState(state.getText().toString());
                EditText zip = (EditText)v.findViewById(R.id.zipcode);
                property.setZip(zip.getText().toString());
                EditText rent = (EditText)v.findViewById(R.id.rent);
                property.setRent(rent.getText().toString());
                EditText email = (EditText)v.findViewById(R.id.email);
                property.setEmail(email.getText().toString());
                EditText mobile = (EditText)v.findViewById(R.id.phone);
                property.setMobile(mobile.getText().toString());
                EditText description = (EditText)v.findViewById(R.id.description);
                property.setDescription(description.getText().toString());
                Spinner rooms = (Spinner)v.findViewById(R.id.roomscount);
                property.setRoom(rooms.getSelectedItem().toString());
                Spinner bath = (Spinner)v.findViewById(R.id.bathroomcount);
                property.setBath(bath.getSelectedItem().toString());
                System.out.println("Hello    ......................."+property);


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


        return inflater.inflate(R.layout.fragment_upload_data, container, false);
    }


}
