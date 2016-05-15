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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtility;
import project.team.cmpe277.com.magicrentals1.utility.PopUp;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;


/**
 * Created by savani on 5/4/16.
 */
public class UploadPropertyDataActivity extends AppCompatActivity implements TaskCompletedStatus{

    private static final String ERROR = "Please fill required entries";
    private static final String REQUIRED = "Required";
    Button btnSubmit;
    // FloatingActionButton btnSubmit;
    PropertyModel property;
    Boolean isValid = true;
    Spinner spinPropertyType;
    EditText area, street, city, state, zip, rent, email, mobile, description, other_details, nickname;
    ImageButton postPicBtn;
    private Intent popUp;
    Spinner SpinPropertyType, bath, rooms;
    private static final String TAG = "UploadProperty";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_THUMBNAIL_CAPTURE =2;
    private static final int PICK_IMAGE_REQUEST =3;
    private String mCurrentPhotoPath;
    private File uploadFile;
    String userid;
    Bitmap thumbnailImage;
    HashMap<String, String> formFields;
    HashMap<String,File> imageFiles ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setTitle("Edit Post");
        setContentView(R.layout.fragment_upload_data);
        Log.i(TAG,"Inside UploadProperty");
        imageFiles  = new HashMap<>();
        formFields  = new HashMap<>();
        postPicBtn =(ImageButton) findViewById(R.id.postPicButton);
        SharedPreferences sharedPreferences = getSharedPreferences("USER",Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("USERID",null);
        ActionBar actionBar = getSupportActionBar();
     //   actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
       // actionBar.set
     //   actionBar.setTitle("Magic Rentals");
      //  actionBar.set


//        street = (EditText)findViewById(R.id.street);
//        city = (EditText)findViewById(R.id.city);
//        state = ((EditText)findViewById(R.id.state));
//        zip = (EditText)findViewById(R.id.zipcode);
//        rent = (EditText)findViewById(R.id.rent);
//        email = (EditText)findViewById(R.id.email);
//        mobile = (EditText)findViewById(R.id.phone);
//        description = (EditText)findViewById(R.id.description);
//        rooms = (Spinner)findViewById(R.id.roomscount);
//        bath = (Spinner)findViewById(R.id.bathroomcount);

        postPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"You've clicked post button");
                popUp = new Intent(UploadPropertyDataActivity.this , PopUp.class);
                startActivityForResult(popUp, PopUp.UPLOAD_OR_TAKE_PHOTO);
            }
        });

        property = new PropertyModel();
        area = (EditText) findViewById(R.id.area);
        area.addTextChangedListener(new TextValidator(area));
        street = (EditText)findViewById(R.id.street);
        street.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = street.getText().toString();
                street.setError(null);
                System.out.println("validationnnn  main "+text+"   value");

                // length 0 means there is no text
                if (text.length() == 0) {
                    System.out.println("validationnnn  main required ");
                    street.setError("required");
                }
            }


        });
        other_details = (EditText)findViewById((R.id.other_details));
        nickname = (EditText)findViewById(R.id.nickname);

        city = (EditText)findViewById(R.id.city);
        city.addTextChangedListener(new TextValidator(city));
        state = ((EditText)findViewById(R.id.state));
        state.addTextChangedListener(new TextValidator(state));
        zip = (EditText)findViewById(R.id.zipcode);
        zip.addTextChangedListener(new TextValidator(zip));
        rent = (EditText)findViewById(R.id.rent);
        rent.addTextChangedListener(new TextValidator(rent));
        email = (EditText)findViewById(R.id.email);
        email.addTextChangedListener(new TextValidator(email));
        mobile = (EditText)findViewById(R.id.phone);
        description = (EditText)findViewById(R.id.description);
        spinPropertyType = (Spinner)findViewById(R.id.property_type);
        rooms = (Spinner)findViewById(R.id.roomscount);
        bath = (Spinner)findViewById(R.id.bathroomcount);
        property = new PropertyModel();




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.submit1:

                isValid = true;
                String key; //setting id for new property
                property.setUser_id(userid);
                property.setStatus("Created");
                System.out.println("in Savkwjjetwe........................." );
                //  Spinner spinProprtyType = (Spinner)v.findViewById(R.id.property_type);
                property.setProperty_type(spinPropertyType.getSelectedItem().toString());
                System.out.println("print     " + property.getProperty_type());
                validate(area);
//                if (area.getText().toString().length() == 0 )
//                    area.setError("Area is required");
                property.setArea(area.getText().toString());
                validate(street);
                property.setStreet(street.getText().toString());
                validate(city);
                property.setCity(city.getText().toString());
                validate(state);
                property.setState(state.getText().toString());
                if (!LandlordUtils.isValidZip(zip.getText().toString())) {
                    isValid = false;
                    zip.setError("Should be 5 digit!");
                }
                property.setZip(zip.getText().toString());
                validate(rent);
                property.setRent(rent.getText().toString());

                if (!LandlordUtils.isValidEmail(email.getText().toString())) {
                    email.setError("Invalid Email");
                }
                property.setEmail(email.getText().toString());
                property.setMobile(mobile.getText().toString());
                System.out.println("sjsk " + mobile.getText().toString());
                property.setDescription(description.getText().toString());
                property.setRoom(rooms.getSelectedItem().toString());
                property.setBath(bath.getSelectedItem().toString());
                property.setOther_details(other_details.getText().toString());
                property.setNickname(nickname.getText().toString());
                if (isValid) {
                    formFields = LandlordUtils.serialize(property);
                    String url = getString(R.string.url) + "/addPostings";
                    System.out.println("BATH... " + property.getBath() + "  Room.." + property.getRoom() + "  Email" + property.getEmail());
                    PropertiesResultLab propertiesResultLab = PropertiesResultLab.
                            getPropertiesResultLab(getApplicationContext());
                    ArrayList<PropertyModel> propertyList = propertiesResultLab.getPropertyList();

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

                    //new MultipartUtilityAsyncTask(UploadPropertyDataActivity.this, hm, null).execute(url);
                    new MultipartRequest().execute(url);
                    key = property.getUser_id() + property.getStreet() + property.getCity() + property.getZip();
                    property.setKey(key);
                    propertyList.add(property);



                    //   UploadPropertyDataActivity.this.finish();

//call service
                    //  Toast.makeText(UploadPropertyDataActivity.this, "Fine" , Toast.LENGTH_LONG).show();
                } else
                    //     Toast.makeText(UploadPropertyDataActivity.this, ERROR , Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Error in data");
                break;
            case R.id.cancelform:
                this.finish();
                break;
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

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);

            postPicBtn.setImageBitmap(bitmap);
            postPicBtn.setAdjustViewBounds(true);
            postPicBtn.setVisibility(View.VISIBLE);
            addToGallery();


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
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
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




    public void onTaskCompleted(JSONObject jsonObject){
        //
        Log.i(TAG, "Response---- "+jsonObject );
      //  System.out.println("response++++++ "+jsonObject);
        try {
            if(jsonObject.getInt("code") == 200){
              //  Toast.makeText(UploadPropertyDataActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
            }else{
             //   Toast.makeText(UploadPropertyDataActivity.this, "Error! Please try again.", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class MultipartRequest extends AsyncTask<String , Void , JSONObject > {

        JSONObject httpStatus;

        @Override
        protected JSONObject doInBackground(String... strings) {
            String charset = "UTF-8";

                httpStatus = new JSONObject();
          //  String requestURL = "http://10.0.2.2:3000/addPostings";
              String requestURL = strings[0];
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

               /* multipart.addFormField("user_id", "Cool Pictures");
                multipart.addFormField("Street", "Cool Pictures");
                multipart.addFormField("City", "Cool Pictures");
                multipart.addFormField("State", "Cool Pictures");
                multipart.addFormField("Zip", "Cool Pictures");
                multipart.addFormField("property_type", "Cool Pictures");
                multipart.addFormField("bath", "Cool Pictures");
                multipart.addFormField("room", "Cool Pictures");
                multipart.addFormField("area", "Cool Pictures");
                multipart.addFormField("rent", "Cool Pictures");
                multipart.addFormField("email", "Cool Pictures");
                multipart.addFormField("Mobile", "Cool Pictures");
                multipart.addFormField("description", "Cool Pictures");*/

               // multipart.addFilePart("fileUpload", uploadFile);

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
                    AlertDialog alertDialog = new AlertDialog.Builder(UploadPropertyDataActivity.this).create();
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Property created.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    UploadPropertyDataActivity.this.finish();
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

