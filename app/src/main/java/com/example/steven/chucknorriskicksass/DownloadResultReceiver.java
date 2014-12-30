package com.example.steven.chucknorriskicksass;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Steven on 30/12/14.
 */
public class DownloadResultReceiver extends ResultReceiver {
    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public DownloadResultReceiver(Handler handler) {
        super(handler);
    }

    private Receiver mReceiver;

    public interface Receiver{
        public void onReceiveResult(int resultCode,Bundle resultData);
    }

    public void setReceiver(Receiver receiver)
    {
        this.mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(mReceiver != null)
        {
            mReceiver.onReceiveResult(resultCode,resultData);
        }
    }
}
