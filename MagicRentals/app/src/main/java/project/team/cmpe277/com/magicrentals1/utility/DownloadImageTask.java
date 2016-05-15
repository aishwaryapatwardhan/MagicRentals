package project.team.cmpe277.com.magicrentals1.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by saipranesh on 15-May-16.
 */
public class DownloadImageTask extends AsyncTask<String, Void , Bitmap> {

    ImageView mImageView;

    public DownloadImageTask(ImageView imageView){
        mImageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String inputUrl = urls[0];
        Bitmap imageIcon =  null;

        try {
            InputStream in = new URL(inputUrl).openStream();
            imageIcon = BitmapFactory.decodeStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageIcon;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }
}