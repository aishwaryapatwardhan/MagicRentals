package project.team.cmpe277.com.magicrentals1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TenantLandlordMainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String USER_SELECTED_OPTION = "project.team.cmpe277.com.magicrentals1.TenantLandLordMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_landlord_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        Intent i = new Intent(this,LoginActivity.class);

        switch (id){
            case R.id.tenantBtn:
                i.putExtra(USER_SELECTED_OPTION,"t");
            case R.id.landlrdBtn:
                i.putExtra(USER_SELECTED_OPTION,"l");
        }

        startActivity(i);

    }
}
