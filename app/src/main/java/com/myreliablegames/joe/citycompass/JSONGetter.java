package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joe on 11/14/2015.
 */
public class JSONGetter {

    private static final String TAG = "JSON Getter";

    private JSONGetter() {
    }

    public static JSONObject getJSONFromUrl(URL url, final Activity activity) {

        if (url == null) {
            return new JSONObject();
        }
        HttpURLConnection urlConnection;
        JSONObject object = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream inStream;
            inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null) {
                response += temp;
            }
            bReader.close();
            inStream.close();
            urlConnection.disconnect();
            object = (JSONObject) new JSONTokener(response).nextValue();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                            activity.getApplicationContext(),
                            activity.getApplicationContext().getResources().getString((R.string.not_connected)),
                            Toast.LENGTH_SHORT).show();
                }
            });

        }

        if (object != null) {
            return object;
        } else {
            return new JSONObject();
        }

    }
}
