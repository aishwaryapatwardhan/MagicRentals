package project.team.cmpe277.com.magicrentals1.landlord;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.LoginActivity;
import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtility;
import project.team.cmpe277.com.magicrentals1.utility.PopUp;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

/**
 * Created by savani on 5/5/16.
 */
public class EditPropertiesActivity extends AppCompatActivity implements TaskCompletedStatus {

    private static final String ERROR = "Please fill required entries";
    private static final String REQUIRED = "required";
    Boolean isValid = true;
    ArrayList<PropertyModel> mPropertyList;
    PropertiesResultLab mPropertyResultLab;
    PropertyModel mPropertyModel;
   // ArrayList<String> listPropertyTypes;
    //  ArrayList<String> bathroomArr;
    ArrayList<String> roomArr;
    EditText area, street, city, state, zip, rent, email, mobile, description, other_details, nickname;
    Spinner SpinPropertyType, bath, rooms;
    Button btnSubmit;
    String userid;
    private static final String TAG = "EditPropertiesActivity";
    ImageButton postPicBtn;
    private Intent popUp;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_THUMBNAIL_CAPTURE =2;
    private static final int PICK_IMAGE_REQUEST =3;
    private String mCurrentPhotoPath;
    private File uploadFile;
    Bitmap thumbnailImage;
    HashMap<String, String> formFields;
    HashMap<String,File> imageFiles ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_data);
        imageFiles  = new HashMap<>();
        formFields  = new HashMap<>();
        SharedPreferences sharedPreferences = getSharedPreferences("USER",Context.MODE_PRIVATE);
        userid = sharedPreferences.getString(LoginActivity.USERID,null);
        String key = getIntent().getExtras().getString("key");

        postPicBtn = (ImageButton)findViewById(R.id.postPicButton);
        postPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"You've clicked post button");
                popUp = new Intent(EditPropertiesActivity.this , PopUp.class);
                startActivityForResult(popUp, PopUp.UPLOAD_OR_TAKE_PHOTO);
            }
        });
       // userid = preferences.getString(LoginActivity.USERID,null);
     //   int selectedLine = preferences.getInt("selectedLine",0);
        Log.i(TAG, "Line selected .... "+key);
        mPropertyResultLab = PropertiesResultLab.getPropertiesResultLab(getApplicationContext());
        mPropertyList = mPropertyResultLab.getPropertyList();
        System.out.println("Size..... "+mPropertyList.size());
        //

        for (PropertyModel property : mPropertyList) {
            if (property.getKey().equals(key)) {
                System.out.println(property.getKey());
                mPropertyModel = property;
                break;
            }
        }
        if(mPropertyModel != null) {
            System.out.println("Modelll........  +++ " + mPropertyModel.getNickname() + "djdjd  .. " + userid);
            Spinner spinProprtyType = (Spinner) findViewById(R.id.property_type);
            //  R.string-spinProprtyType.get
//        String tempProperty = mPropertyModel.getProperty_type().toString();
//            spinProprtyType.setSelection(PropertyTypeE.mPropertyModel.);

            //   spinProprtyType.setSelection(listPropertyTypes.indexOf(mPropertyModel.getProperty_type()));

            area = (EditText) findViewById(R.id.area);
            area.setText(mPropertyModel.getArea());
            street = (EditText) findViewById(R.id.street);
            street.setText(mPropertyModel.getStreet());
            city = (EditText) findViewById(R.id.city);
            city.setText(mPropertyModel.getCity());
            state = ((EditText) findViewById(R.id.state));
            state.setText(mPropertyModel.getState());
            zip = (EditText) findViewById(R.id.zipcode);
            zip.setText(mPropertyModel.getZip());
            rent = (EditText) findViewById(R.id.rent);
            rent.setText(mPropertyModel.getRent());
            email = (EditText) findViewById(R.id.email);
            email.setText(mPropertyModel.getEmail());
            mobile = (EditText) findViewById(R.id.phone);
            mobile.setText(mPropertyModel.getMobile());
            description = (EditText) findViewById(R.id.description);
            description.setText(mPropertyModel.getDescription());
            other_details = (EditText) findViewById(R.id.other_details);
            other_details.setText(mPropertyModel.getOther_details());
            nickname = (EditText) findViewById(R.id.nickname);
            nickname.setText(mPropertyModel.getNickname());
            rooms = (Spinner) findViewById(R.id.roomscount);
            Log.i(TAG, Integer.parseInt(mPropertyModel.getRoom()) + "rooms");
            rooms.setSelection(Integer.parseInt(mPropertyModel.getRoom()) - 1);//roomArr.indexOf(mPropertyModel.getRoom()));
            bath = (Spinner) findViewById(R.id.bathroomcount);
            rooms.setSelection(Integer.parseInt(mPropertyModel.getBath()) - 1);



            new DownloadImageTask(postPicBtn).execute(mPropertyModel.getImages());
            // spinProprtyType.setSelection(3);//roomArr.indexOf(mPropertyModel.getBath()));
            //System.out.println("Hello    ......................."+property);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.submit1:
                isValid = true;

                validate(area);
                validate(street);
                validate(city);
                validate(state);
                validate(rent);
                LandlordUtils.isValidEmail(email.getText().toString());
                LandlordUtils.isValidZip(zip.getText().toString());

                mPropertyModel.setNickname(nickname.getText().toString());
                mPropertyModel.setArea(area.getText().toString());

                mPropertyModel.setStreet(street.getText().toString());

                mPropertyModel.setCity(city.getText().toString());

                mPropertyModel.setState(state.getText().toString());

                mPropertyModel.setZip(zip.getText().toString());

                mPropertyModel.setRent(rent.getText().toString());

                mPropertyModel.setEmail(email.getText().toString());

                mPropertyModel.setMobile(mobile.getText().toString());

                mPropertyModel.setDescription(description.getText().toString());

                mPropertyModel.setRoom(rooms.getSelectedItem().toString());

                mPropertyModel.setBath(bath.getSelectedItem().toString());

                mPropertyModel.setOther_details(other_details.getText().toString());

                System.out.println("Hello    ......................."+mPropertyModel + mPropertyModel.getBath());
                //  convert into json and call service..


                HashMap<String, String> hm = LandlordUtils.serialize(mPropertyModel);

                hm.put("_id", mPropertyModel.getKey());
                if(thumbnailImage != null){
                    uploadFile = new File(getFilesDir() , "userid" + SystemClock.currentThreadTimeMillis() + ".png");

                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(uploadFile);
                        thumbnailImage.compress(Bitmap.CompressFormat.PNG,100,out);
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageFiles.put("fileUpload",uploadFile);

                }

                // HashMap<String, File> file = new HashMap<String, File>();
                String url = getString(R.string.url)+"/updatePostings";
                new MultipartRequest().execute(url);
               // new MultipartUtilityAsyncTask(EditPropertiesActivity.this,hm, null).execute(url);
                //   PropertiesResultLab.getPropertiesResultLabNew(getApplicationContext());
//                Intent i = new Intent(getApplicationContext(), PropertiesListLandlordActivity.class);
//                i.putExtra("USERID", userid);
//                startActivity(i);
                EditPropertiesActivity.this.finish();
                break;
            case R.id.cancelform:
                this.finish();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addproperty, menu);
        return true;
    }

    void validate(EditText etext){
        if (etext.getText().toString().length() == 0 ){
            isValid = false;
            etext.setError(REQUIRED);
        }

    }


    public void onTaskCompleted(JSONObject jsonObject){
        //
        Log.i(TAG, "Response---- "+jsonObject );
        try {
            if(jsonObject.getInt("code") == 200){
                Toast.makeText(EditPropertiesActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(EditPropertiesActivity.this, "Error! Please try again.", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void , Bitmap> {

        ImageButton mImageView;

        private DownloadImageTask(ImageButton imageView){
            mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String inputUrl = urls[0];
            Bitmap imageIcon =  null;

            try {

                InputStream in = new URL(inputUrl).openStream();
                imageIcon = BitmapFactory.decodeStream(in);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageIcon;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PopUp.UPLOAD_OR_TAKE_PHOTO && data != null){
            String userOption = data.getStringExtra(PopUp.PHOTO_OPTION);

            Log.i(TAG,userOption + " this is the user option");
            Log.i(TAG,PopUp.UPLOAD_PHOTO_OPTION + " this is the upload photo option");
            Log.i(TAG,PopUp.TAKE_PHOTO_OPTION + " this is the take photo option");

            if(userOption.equals(PopUp.UPLOAD_PHOTO_OPTION)){
                Log.i(TAG,"You've clicked upload photo");
                uploadPicFromGallery();
            }else if(userOption.equals(PopUp.TAKE_PHOTO_OPTION)){
                Log.i(TAG,"You've clicked take photo");
                dispatchTakePictureIntent();
            }
        }else if( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Log.i(TAG,"Image Capture Successful");
            int targetW = postPicBtn.getWidth();
            int targetH = postPicBtn.getHeight();

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            int scaleFactor = 1;
            if((targetH > 0) || (targetW > 0)){
                scaleFactor = Math.min(photoW/targetW, photoH/targetH);
            }

            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;

            thumbnailImage = BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);

            postPicBtn.setImageBitmap(thumbnailImage);
            postPicBtn.setAdjustViewBounds(true);
            postPicBtn.setVisibility(View.VISIBLE);


        }else if( requestCode == REQUEST_THUMBNAIL_CAPTURE && resultCode == RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            postPicBtn.setImageBitmap(imageBitmap);

        }else if( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri =  data.getData();
            try {
                thumbnailImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                postPicBtn.setImageBitmap(thumbnailImage);
                postPicBtn.setAdjustViewBounds(true);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private File createImageFile() throws IOException{

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
      /* File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);*/
        File image = new File(getExternalFilesDir(null) , userid + timestamp + ".jpg");
        //   File test = new File(getFilesDir(),timestamp);
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(this.getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
                mCurrentPhotoPath = photoFile.getAbsolutePath();

            }catch(IOException ioe){
                ioe.printStackTrace();
            }

            if(photoFile != null){
                Log.i(TAG,"inside dispatchTakePictureIntent");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }else{
            Toast.makeText(getApplicationContext(),"Sorry, Camera not supported", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToGallery(){
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        // File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(uploadFile);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void captureThumbnailImage(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(this.getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_THUMBNAIL_CAPTURE);
        }
    }

    private void uploadPicFromGallery(){
        Intent uploadPicFromGallery = new Intent();
        uploadPicFromGallery.setType("image/*");
        uploadPicFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(uploadPicFromGallery,"Upload Picture"),3);
    }

    private class MultipartRequest extends AsyncTask<String , Void , JSONObject > {

        JSONObject httpStatus;

        @Override
        protected JSONObject doInBackground(String... strings) {
            String charset = "UTF-8";

            httpStatus = new JSONObject();
            String requestURL = strings[0];
            Log.i("RequestUrl",requestURL);
            try {

                MultipartUtility multipart = new MultipartUtility(requestURL, charset);

                if(!(formFields.isEmpty())){
                    for (HashMap.Entry<String, String> entry : formFields.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        multipart.addFormField(key,value);

                    }
                }

                if(imageFiles != null){
                    if(!(imageFiles.isEmpty())){
                        for (HashMap.Entry<String, File> entry : imageFiles.entrySet()) {
                            String key = entry.getKey();
                            File value = entry.getValue();
                            multipart.addFilePart(key, value);
                        }
                    }
                }

                String response = multipart.finishString();

                if( response != null){
                    try {
                        httpStatus = new JSONObject(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Log.i("Multipart",httpStatus.toString());

            } catch (IOException ex) {
                System.err.println(ex);
            }

            return httpStatus;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {
                if((jsonObject.has("code")) && (jsonObject.getInt("code") == 200)){
                    AlertDialog alertDialog = new AlertDialog.Builder(EditPropertiesActivity.this).create();
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Property created.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    EditPropertiesActivity.this.finish();
                                }
                            });
                    alertDialog.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

}
