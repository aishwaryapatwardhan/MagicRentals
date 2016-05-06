package project.team.cmpe277.com.magicrentals1;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Rekha on 5/4/2016.
 */
public class GCMTokenRefreshListenerService extends InstanceIDListenerService {

    //When token refresh start the service
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
