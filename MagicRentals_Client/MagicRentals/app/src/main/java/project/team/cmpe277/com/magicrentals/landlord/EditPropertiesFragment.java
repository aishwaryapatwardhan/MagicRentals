package project.team.cmpe277.com.magicrentals.landlord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.zip.Inflater;

import project.team.cmpe277.com.magicrentals.R;

/**
 * Created by savani on 5/3/16. edit screen.. pass user id and property id
 */
public class EditPropertiesFragment extends Fragment {

    ArrayList<PropertyModel> mPropertyList;
    PropertiesResultLab mPropertyResultLab;
    PropertyModel mPropertyModel;
    ArrayList<String> listPropertyTypes;
  //  ArrayList<String> bathroomArr;
    ArrayList<String> roomArr;
    public EditPropertiesFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View editView = inflater.inflate(R.layout.fragment_upload_data, container, false);
       // String userid = getArguments().getString("USERID");
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
        EditText area;
        area = (EditText)editView.findViewById(R.id.area);
        area.setText(mPropertyModel.getArea());
        EditText street = (EditText)editView.findViewById(R.id.street);
        street.setText(mPropertyModel.getStreet());
        EditText city = (EditText)editView.findViewById(R.id.city);
        city.setText(mPropertyModel.getCity());
        EditText state = ((EditText)editView.findViewById(R.id.state));
        state.setText(mPropertyModel.getState());
        EditText zip = (EditText)editView.findViewById(R.id.zipcode);
        zip.setText(mPropertyModel.getZip());
        EditText rent = (EditText)editView.findViewById(R.id.rent);
        rent.setText(mPropertyModel.getRent());
        EditText email = (EditText)editView.findViewById(R.id.email);
        email.setText(mPropertyModel.getEmail());
        EditText mobile = (EditText)editView.findViewById(R.id.phone);
        mobile.setText(mPropertyModel.getMobile());
        EditText description = (EditText)editView.findViewById(R.id.description);
        description.setText(mPropertyModel.getDescription());

        Spinner rooms = (Spinner)editView.findViewById(R.id.roomscount);
        spinProprtyType.setSelection(roomArr.indexOf(mPropertyModel.getRoom()));
        Spinner bath = (Spinner)editView.findViewById(R.id.bathroomcount);
        spinProprtyType.setSelection(roomArr.indexOf(mPropertyModel.getBath()));
        //System.out.println("Hello    ......................."+property);


        return editView;


    }
}
