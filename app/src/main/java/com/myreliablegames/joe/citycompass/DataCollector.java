package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.URL;


public class DataCollector extends AsyncTask<String, Void, JSONObject> {
    private final String TAG = "Data Collector";

    private Context mContext;
    private LoadingPopup loadingPopup;
    private Activity mActivity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingPopup.show();
    }

    public DataCollector(Context context, Activity activity) {
        mContext = context;
        mActivity = activity;
        loadingPopup = new LoadingPopup(context, this, activity);
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        // send in user's lat and long to make search query
        URL url = URLMaker.getURL(Double.valueOf(params[0]), Double.valueOf(params[1]), mContext);

        JSONObject object = JSONGetter.getJSONFromUrl(url);

        return object;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        loadingPopup.hide();
    }
}
