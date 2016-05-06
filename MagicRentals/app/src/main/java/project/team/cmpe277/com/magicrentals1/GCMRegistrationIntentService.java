package project.team.cmpe277.com.magicrentals1;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by Rekha on 5/4/2016.
 */
public class GCMRegistrationIntentService extends IntentService {
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";


    public GCMRegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        registerGCM();
    }

    private void registerGCM(){
        Intent registrationComplete = null;
        String token = null;
        try{
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
            Log.v("GCMRegIntentService","token:"+token);
            //Notifying user about registration complete
            registrationComplete = new Intent(REGISTRATION_SUCCESS);
            registrationComplete.putExtra("token",token);
        }catch(Exception e){
            Log.v("GCMRegIntentService","Registration error");
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
