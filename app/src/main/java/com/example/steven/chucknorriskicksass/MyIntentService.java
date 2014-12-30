package com.example.steven.chucknorriskicksass;

import android.app.IntentService;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * Created by Steven on 30/12/14.
 */
public class MyIntentService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    public static final String TAG = "MyIntentService";
    public static final String URL = "http://api.icndb.com/jokes/random?";
    public MyIntentService() {
        super(MyIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");

        Bundle bundle = new Bundle();

        receiver.send(STATUS_RUNNING,Bundle.EMPTY);
        Log.d(TAG,"Service started");


        String[]results = null;



            AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
            HttpGet request = new HttpGet(URL);
            JsonHandler handler = new JsonHandler();

            try {
                results = new String[5];
                for (int i = 0; i<results.length;i++)
                {

                    results[i] = mClient.execute(request,handler);

                }
                Log.d(TAG,"results length : "+results.length);
                bundle.putStringArray("result",results);
                receiver.send(STATUS_FINISHED,bundle);


            } catch (IOException e) {
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }catch (Exception e)
            {
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }

        Log.d(TAG,"Service stopping!");
        this.stopSelf();

    }
}
