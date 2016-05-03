package project.team.cmpe277.com.magicrentals.landlord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.zip.Inflater;

import project.team.cmpe277.com.magicrentals.R;

/**
 * Created by savani on 5/3/16. edit screen.. pass user id and property id
 */
public class EditPropertiesFragment extends Fragment {

    ArrayList<PropertyModel> mPropertyList;
    PropertiesResultLab mPropertyResultLab;
    PropertyModel mpropertyModel;
    public EditPropertiesFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View editView = inflater.inflate(R.layout.fragment_upload_data, container, false);
        String userid = getArguments().getString("USERID");
        int selected_line = getArguments().getInt("selected_line");
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(getContext());
        mPropertyList = mPropertyResultLab.getPropertyList();
        //mpropertyModel = mPropertyList.get()
        return editView;


    }
}
