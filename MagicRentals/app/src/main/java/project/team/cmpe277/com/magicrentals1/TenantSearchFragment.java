package project.team.cmpe277.com.magicrentals1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
    AlertDialog actions;

    SearchParameters sp = new SearchParameters();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select notification frequency");
        String[] options = { "Real-time", "Daily", "Weekly" };
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
    }

    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0: // api call with real time
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent setup with real-time notification", Toast.LENGTH_LONG).show();
                    break;
                case 1: // api call with daily
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent setup with daily notification", Toast.LENGTH_LONG).show();
                    break;
                case 2: // weekly
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent setup with weekly notification", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(getActivity().getApplicationContext(), "Search Agent not setup", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

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
                Intent i = new Intent(getActivity(), TenantSearchListActivity.class);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_login, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_search) {
            //make api call to save the search agent - only one search agent - update existing search agent
            actions.show();
            return true;
        }
        else if (id == R.id.favorites) {
            //migrate to new favourites activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class LoginApi extends AsyncTask<Object, Void, Boolean> {
        private Exception exception;



        public Boolean doInBackground(Object... parameters) {

            try {

                return true;
            } catch (Exception e) {
                // Log exception
                this.exception = e;
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

        }
    }

}
