package project.team.cmpe277.com.magicrentals.landlord;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import project.team.cmpe277.com.magicrentals.R;

/**
 * Created by savani on 4/26/16.
 */
public class PropertyListAdapter extends ArrayAdapter<PropertyModel> {
    ArrayList<PropertyModel> mPropertyList;
    int resource ;
    Context context;
    LayoutInflater inflater;
    Activity activity;

    public PropertyListAdapter(Context context, int resource, ArrayList<PropertyModel> propertyList) {
        super(context, resource, propertyList);
        activity = (Activity)context;
        mPropertyList = propertyList;

    }
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            LayoutInflater inflater;
            System.out.println("Savani   .......   "+Context.LAYOUT_INFLATER_SERVICE);
            //convertView =
             convertView = activity.getLayoutInflater()
                    .inflate(R.layout.landlord_property_row, null);
        }
        PropertyModel property = getItem(position);
        TextView  nicknameV = (TextView) convertView.findViewById(R.id.nick_name);
        nicknameV.setText(property.getNickname());

        TextView countV = (TextView)convertView.findViewById(R.id.view_count);
        countV.setText(property.getView_count());

        TextView  streetV = (TextView) convertView.findViewById(R.id.house_street);
        streetV.setText(property.getStreet());

        TextView  cityV = (TextView) convertView.findViewById(R.id.house_city);
        cityV.setText(property.getCity());

        TextView stateV = (TextView) convertView.findViewById(R.id.house_state);
        stateV.setText(property.getState());

        TextView zipV = (TextView) convertView.findViewById(R.id.house_zip);
        zipV.setText(property.getZip());

        return convertView;



    }

}
