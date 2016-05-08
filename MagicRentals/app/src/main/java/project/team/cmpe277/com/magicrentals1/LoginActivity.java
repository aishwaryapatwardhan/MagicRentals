package project.team.cmpe277.com.magicrentals1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private SignInButton googleSignIn;
    private int RC_SIGN_IN;
    private String gcmtoken;
    private String userid = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing the facebook sdk and initializing the callbackmanager
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    gcmtoken = intent.getStringExtra("token");
                    Toast.makeText(getApplicationContext(),"GCM token:"+gcmtoken,Toast.LENGTH_LONG).show();
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
                afterSuccessfulLogin(loginResult.getAccessToken().getUserId(),loginResult.getAccessToken().getToken(),"");
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    private void afterSuccessfulLogin(String userid, String token, String email) {
        //Navigate to next activity

        //new LoginApi().execute("URL",userid,token,email);
        this.userid = userid;

        Map parampost = new HashMap();
        parampost.put("userid",userid);
        parampost.put("devicetoken",gcmtoken);
        parampost.put("email", email);

        /*try{
            new LoginApi().execute("login-api",parampost);
        }catch(Exception e){
            e.printStackTrace();
        }*/

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


    @SuppressWarnings("unchecked")
    public class LoginApi extends AsyncTask<Object, Void, String> {
        String urlString = null;
        HashMap<String,String> inputMap;
        String apiId = null;


        public String doInBackground(Object... parameters) {
            urlString = parameters[0].toString();
            inputMap = (HashMap<String,String>)parameters[1];
            String response = null;
            HttpURLConnection conn = null;
            String line = null;
            URL url;

            try {
                url = new URL(inputMap.get(urlString));


            } catch (MalformedURLException e) {
                // Log exception
                throw new IllegalArgumentException("Invalid URL");
            }
            StringBuilder builder = new StringBuilder();
            Iterator<Map.Entry<String,String>> iterator= inputMap.entrySet().iterator();

            while(iterator.hasNext()){
                Map.Entry<String,String> param = iterator.next();
                builder.append(param.getKey()).append('=').append(param.getValue());
                if(iterator.hasNext()){
                    builder.append('&');
                }
            }
            String body = builder.toString();
            Log.v("Parameters",body);
            byte[] bytes=body.getBytes();
            try{
                conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setFixedLengthStreamingMode(bytes.length);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(bytes);
                outputStream.close();

                int status = conn.getResponseCode();
                if(status != 200){
                    throw new IOException("Post fail in Login Api");
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder1 = new StringBuilder();
                while((line = bufferedReader.readLine())!=null){
                    builder1.append(line+'\n');
                }
                response=builder1.toString();
                return response;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if(response == null){
                //do something when no response
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }else{
                try {
                    JSONObject object = (JSONObject)new JSONTokener(response).nextValue();
                    String statuscode = object.getString("statuscode");
                    if(statuscode.equals(200)){
                        Intent i = new Intent(getApplicationContext(), TenantSearchActivity.class);
                        i.putExtra("USERID", userid);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Login API Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // Appropriate error handling code
                }

            }
        }
    }
}
