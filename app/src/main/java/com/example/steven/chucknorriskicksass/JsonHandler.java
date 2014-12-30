package com.example.steven.chucknorriskicksass;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.List;

/**
 * Created by Steven on 30/12/14.
 */
public class JsonHandler implements ResponseHandler<String> {

private static final String TAG = "JsonHandler";
    @Override
    public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
         String result = null;

        String JSONResponse = new BasicResponseHandler().handleResponse(httpResponse);
        try {



                JSONObject main = (JSONObject) new JSONTokener(JSONResponse).nextValue();
                JSONObject value = main.getJSONObject("value");
                result = value.getString("joke");
                Log.d(TAG,"joke "+result);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
