package project.team.cmpe277.com.magicrentals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * Created by Rekha on 4/25/2016.
 */
public class TenantSearch extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static String userid;
    private EditText locationvalue;
    private EditText keywordvalue;
    private Spinner propertyvalue;
    private Spinner pricerangevalue;
    private Button mSearch;

    SearchParameters sp = new SearchParameters();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchfragment_tenant);

        userid = getIntent()
                .getSerializableExtra("USERID").toString();

        Spinner propertyvalue = (Spinner) findViewById(R.id.propertyvalue);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.proptypelist, android.R.layout.simple_spinner_item);

        propertyvalue.setAdapter(adapter1);

        Spinner pricerangevalue = (Spinner) findViewById(R.id.pricerangevalue);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.pricerangelist, android.R.layout.simple_spinner_item);

        pricerangevalue.setAdapter(adapter2);

        keywordvalue = (EditText) findViewById(R.id.keywordvalue);
        locationvalue = (EditText) findViewById(R.id.locationvalue);
        mSearch = (Button) findViewById(R.id.search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
