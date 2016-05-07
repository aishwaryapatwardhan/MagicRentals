package project.team.cmpe277.com.magicrentals1.landlord;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.team.cmpe277.com.magicrentals1.R;
import project.team.cmpe277.com.magicrentals1.landlord.PropertyModel;
import project.team.cmpe277.com.magicrentals1.utility.PopUp;

/**
 * Created by savani on 5/4/16.
 */
public class UploadPropertyDataActivity extends AppCompatActivity {
    static String userid;
    Button btnSubmit;
    PropertyModel property;
    Spinner spinPropertyType;
    EditText area, street, city, state, zip, rent, email, mobile, description;
    ImageButton postPicBtn;
    private Intent popUp;
    Spinner SpinPropertyType, bath, rooms;
    private static final String TAG = "UploadProperty";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_THUMBNAIL_CAPTURE =2;
    private static final int PICK_IMAGE_REQUEST =3;
    private String mCurrentPhotoPath;
    private File uploadFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_data);
        Log.i(TAG,"Inside UploadProperty");
         postPicBtn =(ImageButton) findViewById(R.id.postPicButton);
         area = (EditText) findViewById(R.id.area);
         street = (EditText)findViewById(R.id.street);
         city = (EditText)findViewById(R.id.city);
         state = ((EditText)findViewById(R.id.state));
         zip = (EditText)findViewById(R.id.zipcode);
         rent = (EditText)findViewById(R.id.rent);
         email = (EditText)findViewById(R.id.email);
         mobile = (EditText)findViewById(R.id.phone);
         description = (EditText)findViewById(R.id.description);
         rooms = (Spinner)findViewById(R.id.roomscount);
         bath = (Spinner)findViewById(R.id.bathroomcount);

        postPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"You've clicked post button");
                popUp = new Intent(UploadPropertyDataActivity.this , PopUp.class);
                startActivityForResult(popUp, PopUp.UPLOAD_OR_TAKE_PHOTO);
            }
        });

        property = new PropertyModel();


       /* userid = getIntent()
                .getSerializableExtra("USERID").toString();*/

        btnSubmit = (Button) findViewById(R.id.submit1);
        System.out.println("hiii         ...........  ");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Read the data from the form and pass it to the backend service
                // Button btnSubmit = (Button) (v);
                System.out.println("in Savkwjjetwe........................."+v.getId());
              //  Spinner spinProprtyType = (Spinner)v.findViewById(R.id.property_type);
               // property.setProperty_type(spinProprtyType.getSelectedItem().toString());


                property.setArea(area.getText().toString());

                property.setStreet(street.getText().toString());

                property.setCity(city.getText().toString());

                property.setState(state.getText().toString());

                property.setZip(zip.getText().toString());

                property.setRent(rent.getText().toString());

                if (LandlordUtils.isValidEmail(email.getText().toString()));
                email.setError("Invalid Email");

                property.setEmail(email.getText().toString());

                property.setMobile(mobile.getText().toString());

                property.setDescription(description.getText().toString());

                property.setRoom(rooms.getSelectedItem().toString());

                property.setBath(bath.getSelectedItem().toString());
                System.out.println("Hello    ......................."+property + property.getBath());
              //  convert into json and call service..
                PropertiesResultLab.getPropertiesResultLabNew(getApplicationContext());
                Intent i = new Intent(getApplicationContext(), PropertiesListLandlordActivity.class);
                i.putExtra("USERID", userid);
                startActivity(i);







                //   CheckBox ch1=(CheckBox)v.findViewById(R.id.condo);
                // if (ch1.isChecked())
                // property.setProperty_type(String.valueOf(R.string.condo));


                // property.setArea(v.findViewById(R.id.area));

                //call service




                //user_id , Street , City, State  , Zip    , property_type ,
                // bath , room , area , rent  , email , Mobile , description ,
                // Images (need to decide this yet..), other_details ,  Status , view_count


            }
        } );
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                postPicBtn.setImageBitmap(bitmap);
                postPicBtn.setAdjustViewBounds(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private File createImageFile() throws IOException{

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
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

}
