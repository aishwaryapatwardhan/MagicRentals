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
          //  JSONObject data = json.getJSONObject("data");
          //  JSONObject data = json;
            ArrayList<GridImageDetailItem> gdl = new ArrayList<GridImageDetailItem>();
            addr = json.getJSONObject("address");
            units = json.getJSONObject("units");
            contact = json.getJSONObject("Contact_info");

            GridImageDetailItem gridImageDetailItem = new GridImageDetailItem(json.getString("_id"), addr.getString("Street"), addr.getString("City"),
                    addr.getString("State"),
                    addr.getString("Zip"), json.getString("property_type"), units.getString("room"),
                    units.getString("bath"), units.getString("area"), json.getString("rent"), json.getString("description"),
                    json.getString("other_details"), json.getString("Images"),contact.getString("Mobile"),contact.getString("email"));
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
                .setContentText("New Postings Added")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle("MagicRentals Notification");

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build());
    }
}
