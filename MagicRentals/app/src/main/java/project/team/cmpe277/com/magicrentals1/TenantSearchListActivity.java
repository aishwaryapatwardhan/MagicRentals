package project.team.cmpe277.com.magicrentals1;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Rekha on 4/27/2016.
 */
public class TenantSearchListActivity extends AppCompatActivity {

    public static String userid;
    private static final String TAG = "TenSrchLA";
    SharedPreferences preferences = this.getSharedPreferences(TAG, Context.MODE_PRIVATE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchlistactivity_tenant);

        userid = preferences.getString(LoginActivity.USERID,null);
        Log.i(TAG,userid);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.searchlist_container, new TenantSearchListFragment()).commit();
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

}
