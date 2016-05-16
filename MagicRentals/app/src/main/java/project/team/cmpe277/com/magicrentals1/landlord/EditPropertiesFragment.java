package project.team.cmpe277.com.magicrentals1.landlord;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals1.LoginActivity;
import project.team.cmpe277.com.magicrentals1.R;

/**
 * Created by savani on 5/3/16. edit screen.. pass  property id
 */
public class EditPropertiesFragment extends Fragment {

    ArrayList<PropertyModel> mPropertyList;
    PropertiesResultLab mPropertyResultLab;
    PropertyModel mPropertyModel;
    ArrayList<String> listPropertyTypes;
  //  ArrayList<String> bathroomArr;
    ArrayList<String> roomArr;
    EditText area, street, city, state, zip, rent, email, mobile, description;
    Spinner SpinPropertyType, bath, rooms;
    Button btnSubmit;
    String userid;
    public EditPropertiesFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View editView = inflater.inflate(R.layout.fragment_upload_data, container, false);
       // String userid = getArguments().getString("USERID");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userid = sharedPreferences.getString(LoginActivity.USERID,null);
        int selectedLine = getArguments().getInt("selectedLine");
        listPropertyTypes = new ArrayList<>();
       System.out.println("Array .property..... "+R.array.property_type);
        roomArr = new ArrayList<>(R.array.rooms);
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(getContext());
        mPropertyList = mPropertyResultLab.getPropertyList();
        //mpropertyModel = mPropertyList.get()
        mPropertyModel = mPropertyList.get(selectedLine);
        Spinner spinProprtyType = (Spinner)editView.findViewById(R.id.property_type);


        spinProprtyType.setSelection(listPropertyTypes.indexOf(mPropertyModel.getProperty_type()));

        area = (EditText)editView.findViewById(R.id.area);
        area.setText(mPropertyModel.getArea());
         street = (EditText)editView.findViewById(R.id.street);
        street.setText(mPropertyModel.getStreet());
         city = (EditText)editView.findViewById(R.id.city);
        city.setText(mPropertyModel.getCity());
         state = ((EditText)editView.findViewById(R.id.state));
        state.setText(mPropertyModel.getState());
         zip = (EditText)editView.findViewById(R.id.zipcode);
        zip.setText(mPropertyModel.getZip());
         rent = (EditText)editView.findViewById(R.id.rent);
        rent.setText(mPropertyModel.getRent());
         email = (EditText)editView.findViewById(R.id.email);
        email.setText(mPropertyModel.getEmail());
         mobile = (EditText)editView.findViewById(R.id.phone);
        mobile.setText(mPropertyModel.getMobile());
         description = (EditText)editView.findViewById(R.id.description);
        description.setText(mPropertyModel.getDescription());
        Spinner rooms = (Spinner)editView.findViewById(R.id.roomscount);
        spinProprtyType.setSelection(roomArr.indexOf(mPropertyModel.getRoom()));
       // Spinner bath = (Spinner)editView.findViewById(R.id.bathroomcount);
        spinProprtyType.setSelection(2);//roomArr.indexOf(mPropertyModel.getBath()));
        //System.out.println("Hello    ......................."+property);
        btnSubmit = (Button) editView.findViewById(R.id.submit1);
        btnSubmit.setText("Save");
        return editView;

    }
}
