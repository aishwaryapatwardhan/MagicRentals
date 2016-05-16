package project.team.cmpe277.com.magicrentals1.utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import project.team.cmpe277.com.magicrentals1.R;

public class PopUpTenantLandlord extends AppCompatActivity {

    private Button mTenantButton;
    private Button mLandlordButton;

    public static final String USER_OPTION = "project.team.cmpe277.com.magicrentals1.utility.PopUp.USER_OPTION";
    public static final String LANDLORD_OPTION = "project.team.cmpe277.com.magicrentals1.utility.PopUp.LANDLORD_OPTION";
    public static final String TENANT_OPTION = "project.team.cmpe277.com.magicrentals1.utility.PopUp.TENANT_OPTION";
    public static final int USER_OR_LANDLORD = 2234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_tl);

        mTenantButton = (Button) findViewById(R.id.selectTenantBtn);
        mLandlordButton = (Button) findViewById(R.id.selectLandlordBtn);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.3));

        mTenantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserAction(TENANT_OPTION);
                finish();
            }
        });

        mLandlordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserAction(LANDLORD_OPTION);
                finish();
            }
        });


    }

    private void sendUserAction(String userOption){
        Intent sendUploadPropertyDataActivity = new Intent();
        sendUploadPropertyDataActivity.putExtra(USER_OPTION,userOption);
        setResult(USER_OR_LANDLORD,sendUploadPropertyDataActivity);
    }
}

