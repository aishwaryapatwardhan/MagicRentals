package project.team.cmpe277.com.magicrentals1.utility;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import project.team.cmpe277.com.magicrentals1.R;

public class PopUp extends AppCompatActivity {

    private Button mTakePhoto;
    private Button mUploadPhoto;

    public static final String PHOTO_OPTION = "project.team.cmpe277.com.magicrentals1.utility.PopUp.PHOTO_OPTION";
    public static final String TAKE_PHOTO_OPTION =  "project.team.cmpe277.com.magicrentals1.utility.PopUp.TAKE_PHOTO_OPTION";
    public static final String UPLOAD_PHOTO_OPTION = "project.team.cmpe277.com.magicrentals1.utility.PopUp.UPLOAD_PHOTO_OPTION";
    public static final int UPLOAD_OR_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        mTakePhoto = (Button) findViewById(R.id.takePhoto);
        mUploadPhoto = (Button) findViewById(R.id.uploadPhoto);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width =dm.widthPixels;
        int height = dm.heightPixels ;

        getWindow().setLayout((int)(width * 0.7), (int)(height * 0.5));

       mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserAction(TAKE_PHOTO_OPTION);
                finish();
            }
        });

        mUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserAction(UPLOAD_PHOTO_OPTION);
                finish();
            }
        });




    }

    private void sendUserAction(String userOption){
        Intent sendUploadPropertyDataActivity = new Intent();
        sendUploadPropertyDataActivity.putExtra(PHOTO_OPTION,userOption);
        setResult(UPLOAD_OR_TAKE_PHOTO,sendUploadPropertyDataActivity);
    }
}
