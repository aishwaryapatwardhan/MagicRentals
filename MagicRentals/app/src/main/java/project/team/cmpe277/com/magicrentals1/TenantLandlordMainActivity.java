package project.team.cmpe277.com.magicrentals1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class TenantLandlordMainActivity extends AppCompatActivity{


    private Button TenantBtn;
    private Button LandlordBtn;

    public static final String USER_SELECTED_OPTION = "project.team.cmpe277.com.magicrentals1.TenantLandLordMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_landlord_main);
        final TenantLandlordMainActivity tenantLand = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);
        final Intent i = new Intent(tenantLand, LoginActivity.class);
        final Context context = getApplicationContext();
        TenantBtn = (Button) findViewById(R.id.tenantBtn);


        TenantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TenantLandlordMainActivity.this, LoginActivity.class);
                i.putExtra(USER_SELECTED_OPTION, "t");
                startActivity(i);
            }
        });
        LandlordBtn = (Button) findViewById(R.id.landlrdBtn);
        LandlordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TenantLandlordMainActivity.this, LoginActivity.class);
                i.putExtra(USER_SELECTED_OPTION, "l");
                startActivity(i);
            }
        });
    }


}
