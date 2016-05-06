package project.team.cmpe277.com.magicrentals1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rekha on 5/1/2016.
 */
public class TenantSearchDetailFragment extends android.app.Fragment {

    private TextView streetValue;
    private TextView cityValue;
    private TextView zipcodeValue;
    private TextView stateValue;
    private TextView proptypeValue;
    private TextView numRoomsValue;
    private TextView numBathsValue;
    private TextView sqFeetValue;
    private TextView montlyRentValue;
    private TextView descriptionValue;
    private TextView depositValue;
    private TextView leaseTypeValue;
    private TextView contactNumberValue;
    private TextView contactEmailValue;
    private ImageView heartsImage;
    private ImageView iconImage;
    private Boolean heartflag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View detailview = inflater.inflate(R.layout.searchdetailfragment_tenant, container, false);
        streetValue = (TextView)detailview.findViewById(R.id.streetValue);
        cityValue = (TextView)detailview.findViewById(R.id.cityValue);
        zipcodeValue = (TextView)detailview.findViewById(R.id.zipcodeValue);
        stateValue = (TextView)detailview.findViewById(R.id.stateValue);
        proptypeValue = (TextView)detailview.findViewById(R.id.proptypeValue);
        numRoomsValue = (TextView)detailview.findViewById(R.id.numRoomsValue);
        numBathsValue = (TextView)detailview.findViewById(R.id.numBathsValue);
        sqFeetValue = (TextView)detailview.findViewById(R.id.sqFeetValue);
        montlyRentValue = (TextView)detailview.findViewById(R.id.monthlyRentValue);
        descriptionValue = (TextView)detailview.findViewById(R.id.descriptionValue);
        depositValue = (TextView)detailview.findViewById(R.id.depositValue);
        leaseTypeValue = (TextView)detailview.findViewById(R.id.leaseTypeValue);
        contactNumberValue = (TextView)detailview.findViewById(R.id.contactNumberValue);
        contactEmailValue = (TextView)detailview.findViewById(R.id.contactEmailValue);
        heartsImage = (ImageView)detailview.findViewById(R.id.heartsImage);
        iconImage = (ImageView)detailview.findViewById(R.id.iconImage);
        //Make API call to get the detail of the onclicked item
        new GetDetailAPI().execute("01");

        heartsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heartflag == false) {
                    Toast.makeText(getActivity().getApplicationContext(), "Added to favourites", Toast.LENGTH_LONG).show();
                    //api call to add to fav
                    int drawableId = getResources().getIdentifier("solidheart", "drawable", "project.team.cmpe277.com.magicrentals");
                    heartsImage.setBackgroundResource(drawableId);
                    heartflag = true;
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Removed from favourites", Toast.LENGTH_LONG).show();
                    //api call to remove from fav
                    int drawableId = getResources().getIdentifier("shallowheart", "drawable", "project.team.cmpe277.com.magicrentals");
                    heartsImage.setBackgroundResource(drawableId);
                    heartflag = false;
                }
            }
        });


        iconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Sai your code goes here to display the image in a new fragment
            }
        });

        return detailview;
    }



    public class GetDetailAPI extends AsyncTask<Object, Void, GridImageDetailItem> {
        private Exception exception;
        GridImageDetailItem gridImageDetailItem;


        public GridImageDetailItem doInBackground(Object... parameters) {

            try {
                //Make API call
                return gridImageDetailItem;
            } catch (Exception e) {
                // Log exception
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(GridImageDetailItem gridImageDetailItem) {
            //set all the display text
            streetValue.setText("190 Ryland Street");
            cityValue.setText("San Jose");
            zipcodeValue.setText("95110");
            stateValue.setText("California");
            proptypeValue.setText("Condo");
            numRoomsValue.setText("2");
            numBathsValue.setText("2");
            sqFeetValue.setText("1200");
            montlyRentValue.setText("$3200");
            descriptionValue.setText("Its a spacious apartment");
            depositValue.setText("$800");
            leaseTypeValue.setText("12 months lease");
            contactNumberValue.setText("4088568501");
            contactEmailValue.setText("rekha@gmail.com");

            int drawableId = getResources().getIdentifier("magicrentals1", "drawable", "project.team.cmpe277.com.magicrentals");
            iconImage.setBackgroundResource(drawableId);

            //call CheckIfAlreadyInfav
            new CheckIfAlreadyInFav().execute("01");
        }
    }

    public class CheckIfAlreadyInFav extends AsyncTask<Object, Void, Boolean> {
        private Exception exception;
        private Boolean result;

        public Boolean doInBackground(Object... parameters) {

            try {
                //Make API call to check if its already in fav table
                result = true;
                return result;
            } catch (Exception e) {
                // Log exception
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                //Set solid heart
                heartflag = true;
            }else{
                //Set hollow heart
                heartflag = false;
            }
            int drawableId = getResources().getIdentifier("shallowheart", "drawable", "project.team.cmpe277.com.magicrentals");
            heartsImage.setBackgroundResource(drawableId);
            heartflag = true;
        }
    }
}
