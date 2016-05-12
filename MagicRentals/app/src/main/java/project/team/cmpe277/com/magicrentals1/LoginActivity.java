package project.team.cmpe277.com.magicrentals1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import project.team.cmpe277.com.magicrentals1.landlord.PropertiesListLandlordActivity;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtility;
import project.team.cmpe277.com.magicrentals1.utility.MultipartUtilityAsyncTask;
import project.team.cmpe277.com.magicrentals1.utility.TaskCompletedStatus;

public class LoginActivity extends AppCompatActivity implements TaskCompletedStatus, GoogleApiClient.OnConnectionFailedListener{

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private SignInButton googleSignIn;
    private int RC_SIGN_IN;
    private String gcmtoken;
    public static String urlip;


    private String userid;
    private static final String TAG = "LoginActivity";
    public static final String USERID = "project.team.cmpe277.com.magicrentals1.USERID" ;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Following is the code to store user-id and retrieve the user-id using shared preferences.
      /*  SharedPreferences sharedPreferences = this.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("userid",PLACE_YOUR_USER_ID).apply();

        String useridCheck = sharedPreferences.getString("userid",null);

        SharedPreferences preferences = context.getSharedPreferences(TAG,Context.MODE_PRIVATE);

        Log.i(TAG, useridCheck); */

        //Initializing the facebook sdk and initializing the callbackmanager
        urlip = getString(R.string.url);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    gcmtoken = intent.getStringExtra("token");
                    //Toast.makeText(getApplicationContext(),"GCM token:"+gcmtoken,Toast.LENGTH_LONG).show();
                }else if(intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                    Toast.makeText(getApplicationContext(),"GCM registration error:",Toast.LENGTH_LONG).show();
                } else {
                    //tobe
                }
            }
        };

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(ConnectionResult.SUCCESS != resultCode){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                Toast.makeText(getApplicationContext(),"Google Play Service is not installed/enabled on this device",Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode,getApplicationContext());
            } else {
                Toast.makeText(getApplicationContext(),"This device doesnot support for Google Play Service!",Toast.LENGTH_LONG).show();
            }
        }else {
            Intent i = new Intent(this, GCMRegistrationIntentService.class);
            startService(i);
        }

        //Programmatic Key Hash Generation

        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "project.team.cmpe277.com.magicrentals",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/

        //Google Signin setup

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        setContentView(R.layout.activity_login);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        googleSignIn = (SignInButton) findViewById(R.id.sign_in_button);


        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent i = new Intent(getApplicationContext(), TenantSearchActivity.class);
                i.putExtra("USERID", userid);
                startActivity(i); */
                //Intent i = new Intent(getApplicationContext(), PropertiesListLandlordActivity.class);
                //startActivity(i);
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Migrate to new activity on successful login
                //loginResult.getAccessToken().getUserId();
                //loginResult.getAccessToken().getToken();
                afterSuccessfulLogin(loginResult.getAccessToken().getUserId(), loginResult.getAccessToken().getToken(), "");

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Attempt Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "Login Attempt Failed", Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("LoginActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("LoginActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //acct.getEmail
            //acct.getId
            //acct.getIdToken
            afterSuccessfulLogin(acct.getId(),acct.getIdToken(),acct.getEmail());
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(getApplicationContext(), "Login Attempt Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);


            handleSignInResult(result);
        }

        //onactivityresult will pass the control to callbackManager's onActivityResult
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    private void afterSuccessfulLogin(String userid, String token, String email) {
        //Navigate to next activity

        //new LoginApi().execute("URL",userid,token,email);
        String url = getString(R.string.url)+"/addUser";

        HashMap<String,String> input = new HashMap<String,String>();
        input.put("uid",userid);
        input.put("email",email);
        input.put("deviceID",gcmtoken);

        new MultipartUtilityAsyncTask(this,input,null).execute(url);

        //Adding userid to shared preferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(USERID,userid).commit();

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://project.team.cmpe277.com.magicrentals/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://project.team.cmpe277.com.magicrentals/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onTaskCompleted(JSONObject result) {

        Intent i = new Intent(getApplicationContext(), TenantSearchActivity.class);
        i.putExtra("USERID", userid);
        startActivity(i);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
