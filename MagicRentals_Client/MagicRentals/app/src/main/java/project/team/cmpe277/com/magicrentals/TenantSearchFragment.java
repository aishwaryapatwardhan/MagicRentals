package project.team.cmpe277.com.magicrentals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Rekha on 4/26/2016.
 */
public class TenantSearchFragment extends android.app.Fragment implements AdapterView.OnItemSelectedListener {

    private static String userid;
    private EditText locationvalue;
    private EditText keywordvalue;
    private Spinner propertyvalue;
    private Spinner pricerangevalue;
    private Button mSearch;

    SearchParameters sp = new SearchParameters();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View searchview = inflater.inflate(R.layout.searchfragment_tenant, container, false);


        propertyvalue = (Spinner) searchview.findViewById(R.id.propertyvalue);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this.getActivity(), R.array.proptypelist, android.R.layout.simple_spinner_item);

        propertyvalue.setAdapter(adapter1);

        pricerangevalue = (Spinner) searchview.findViewById(R.id.pricerangevalue);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this.getActivity(), R.array.pricerangelist, android.R.layout.simple_spinner_item);

        pricerangevalue.setAdapter(adapter2);

        keywordvalue = (EditText) searchview.findViewById(R.id.keywordvalue);
        locationvalue = (EditText) searchview.findViewById(R.id.locationvalue);
        mSearch = (Button) searchview.findViewById(R.id.search);


        keywordvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(null)) {
                    sp.setKeywords("");
                } else {
                    sp.setKeywords(s.toString());
                }
            }
        });


        locationvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(null)) {
                    sp.setLocation("");
                } else {

                    //This should be set to current location when no location is mentioned
                    sp.setLocation(s.toString());
                }
            }
        });

        //When Search is initiated

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make an API call to display the search results
                Intent i = new Intent(getContext(), TenantSearchListActivity.class);
                i.putExtra("USERID", TenantSearchActivity.userid);
                startActivity(i);
            }
        });

        return searchview;
    }

    //Get the values from Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.propertyvalue) {
            TextView mytext = (TextView) view;
            sp.setPropertytype(mytext.getText().toString());

        } else if (parent.getId() == R.id.pricerangevalue) {
            TextView mytext = (TextView) view;
            sp.setPricerange(mytext.getText().toString());
        }
    }

    //When nothing is selected  in Spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        sp.setPropertytype("");
        sp.setPricerange("");
    }

}
