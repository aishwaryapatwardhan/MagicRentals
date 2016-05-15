package project.team.cmpe277.com.magicrentals1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        try{


            JSONObject json = new JSONObject(message);
            JSONObject addr, units, contact;
            JSONObject data = json.getJSONObject("data");
            ArrayList<GridImageDetailItem> gdl = new ArrayList<GridImageDetailItem>();
            addr = data.getJSONObject("address");
            units = data.getJSONObject("units");
            contact = data.getJSONObject("Contact_info");

            GridImageDetailItem gridImageDetailItem = new GridImageDetailItem(data.getString("_id"), addr.getString("Street"), addr.getString("City"),
                    addr.getString("State"),
                    addr.getString("Zip"), data.getString("property_type"), units.getString("room"),
                    units.getString("bath"), units.getString("area"), data.getString("rent"), data.getString("description"),
                    data.getString("other_details"), data.getString("Images"),contact.getString("Mobile"),contact.getString("email"));
            //gridImageDetailItem.setCount(Integer.parseInt(data.getString("view_count")));
            gdl.add(gridImageDetailItem);
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
            String userid = preferences.getString(LoginActivity.USERID, null);
            PropSingleton.get(getApplicationContext()).clearList();
            PropSingleton.get(getApplicationContext()).setGridImageDetailItems(gdl);
        }
        catch (JSONException e){
            System.out.print("Hi");
        }
        Intent intent = new Intent(this,TenantSearchListActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("MagicRentals Notification")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build());
    }
}
