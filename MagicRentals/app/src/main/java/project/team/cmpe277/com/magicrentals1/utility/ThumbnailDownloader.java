package project.team.cmpe277.com.magicrentals1.utility;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import android.os.*;


/**
 * Created by Rekha on 5/3/2016.
 */
public class ThumbnailDownloader<Token> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    private static final int MESSAGE_PRELOAD = 1;
    private LruCache<String,Bitmap> mMemoryCache;
    private final int CACHE_SIZE ;

    Handler mHandler;
    Map<Token, String> requestMap =
            Collections.synchronizedMap(new HashMap<Token, String>());

    Handler mResponseHandler;
    Listener<Token> mListener;
    public interface Listener<Token> {
        void onThumbnailDownloaded(Token token, Bitmap thumbnail);
    }
    public void setListener(Listener<Token> listener) {
        mListener = listener;
    }


    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()) / 1024;
        CACHE_SIZE = maxMemory / 4;
        mMemoryCache = new LruCache<String,Bitmap>(CACHE_SIZE);
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    @SuppressWarnings("unchecked")
                    Token token = (Token)msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    handleRequest(token);
                }else if (msg.what == MESSAGE_PRELOAD){
                    String url = (String)msg.obj;
                    preload(url);
                }
            }
        };
    }

    public void queueThumbnail(Token token, String url) {
        Log.i(TAG, "Got an URL: " + url);
        requestMap.put(token, url);
        mHandler
                .obtainMessage(MESSAGE_DOWNLOAD, token)
                .sendToTarget();
    }

    private void handleRequest(final Token token) {

            final String url = requestMap.get(token);
            if (url == null)
                return;

            final Bitmap bitmap = mMemoryCache.get(url);

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(requestMap.get(token) != url){
                        return;
                    }
                    requestMap.remove(token);

                    mListener.onThumbnailDownloaded(token,bitmap);
                }
            });


    }

    public void clearQueue() {
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if(getBitmapFromMemoryCache(key) == null){
            mMemoryCache.put(key,bitmap);
            Log.i(TAG,"cached values : " + key + " " + bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key){
        return mMemoryCache.get(key);
    }

    private void preload(final Token token){
        String url = requestMap.get(token);
        preload(url);
    }

    private void preload(String url){
        if(url == null) return;

        if(mMemoryCache.get(url) != null) return;

        Bitmap bitmap = getBitmap(url);

        if(bitmap != null){
            mMemoryCache.put(url,bitmap);
        }
    }

    private Bitmap getBitmap(String url){
        try {
            URL urlS = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlS.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void queuePreload(String url){
        if(mMemoryCache.get(url) != null) return;

        mHandler.
                obtainMessage(MESSAGE_PRELOAD,url).sendToTarget();

    }

    public Bitmap checkCache(String url){
        return mMemoryCache.get(url);
    }

}

