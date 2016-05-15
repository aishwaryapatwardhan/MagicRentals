package project.team.cmpe277.com.magicrentals1.utility;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by saipranesh on 5/7/16.
 */
public class MultipartUtilityAsyncTask extends AsyncTask<String, Void, JSONObject> {

    String charset = "UTF-8";
    String requestURL;
    HashMap<String,String> formFields;
    HashMap<String,File> imageFiles;
    String status = "";
    JSONObject httpStatus ;

    private Context mContext;
    private TaskCompletedStatus mCallback;


    public MultipartUtilityAsyncTask(Context context, HashMap<String,String> formFields, HashMap<String,File> imageFiles){
        this.formFields = formFields;
        this.imageFiles = imageFiles;
        this.mContext = context;
        this.mCallback = (TaskCompletedStatus) context;
        httpStatus = new JSONObject();
    }


    @Override
    protected JSONObject doInBackground(String... url) {


        requestURL =  url[0];

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            if(formFields != null){
                if(!(formFields.isEmpty())){
                    for (HashMap.Entry<String, String> entry : formFields.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        multipart.addFormField(key,value);

                    }
                }
            }

            if(imageFiles != null){
                if(!(imageFiles.isEmpty())){
                    for (HashMap.Entry<String, File> entry : imageFiles.entrySet()) {
                        String key = entry.getKey();
                        File value = entry.getValue();
                        multipart.addFilePart(key, value);
                    }
                }
            }



           /* List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");
            String responseString = "";
            String path= null;
            for (String line : response) {
                System.out.println(line);
                if(line.contains("status")){
                    status = line.substring(line.indexOf('\'') + 1, line.lastIndexOf('\''));
                }

            }
            if(status.equals("OK")){
                httpStatus = true;
            }else{
                httpStatus = false;
            }*/

            String response = multipart.finishString();

            if( response != null){
                try {
                    httpStatus = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException ex) {
            System.err.println(ex);
        }
        Log.i("MultiAsynk",httpStatus + "");
        return httpStatus;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }
}


