package project.team.cmpe277.com.magicrentals1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONArray;

/**
 * Created by Rekha on 5/4/2016.
 */
public class GCMPushReceiverService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        sendNotification(message);
    }
    private void sendNotification(String message){
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);

       /* try{

             JSONObject jsonobject = new JSONObject(sb.toString());

             JSONObject addr, units, contact;

             JSONObject jsonobject = jsonarray.getJSONObject(i);
                    addr = jsonobject.getJSONObject("address");
                    units = jsonobject.getJSONObject("units");
                    contact = jsonobject.getJSONObject("Contact_info");

                    GridImageDetailItem gridImageDetailItem = new GridImageDetailItem(jsonobject.getString("_id"), addr.getString("Street"), addr.getString("City"),
                            addr.getString("State"),
                            addr.getString("Zip"), jsonobject.getString("property_type"), units.getString("room"),
                            units.getString("bath"), units.getString("area"), jsonobject.getString("rent"), jsonobject.getString("description"),
                            jsonobject.getString("other_details"), jsonobject.getString("Images"),contact.getString("Mobile"),contact.getString("email"));

                }
                Intent i = new Intent(getActivity(), TenantSearchListActivity.class);
                startActivity(i);*/

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("MagicRentals Notification")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build());
    }
}
