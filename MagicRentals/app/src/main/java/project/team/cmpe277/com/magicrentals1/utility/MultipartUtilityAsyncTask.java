package project.team.cmpe277.com.magicrentals1.utility;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by saipranesh on 5/7/16.
 */
public class MultipartUtilityAsyncTask extends AsyncTask<String, Void, Boolean> {

    String charset = "UTF-8";
    String requestURL;
    HashMap<String,String> formFields;
    HashMap<String,File> imageFiles;
    String status = "";
    Boolean httpStatus;

    public MultipartUtilityAsyncTask(HashMap<String,String> formFields, HashMap<String,File> imageFiles){
        this.formFields = formFields;
        this.imageFiles = imageFiles;
    }


    @Override
    protected Boolean doInBackground(String... url) {


        requestURL =  url[0];

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            if(!(formFields.isEmpty())){
                for (HashMap.Entry<String, String> entry : formFields.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    multipart.addFormField(key,value);

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




            List<String> response = multipart.finish();

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
            }

        } catch (IOException ex) {
            System.err.println(ex);
        }

        return httpStatus;
    }
}


