package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;


public class GetDataTask extends AsyncTask<String, Void, JSONObject> {
    private final String TAG = "GetDataTask";

    private Context context;
    private LoadingPopup loadingPopup;
    private Activity activity;
    private CityRing cityRing;
    SharedPreferences sharedPreferences;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingPopup.show();

    }

    public GetDataTask(Context context, Activity activity, CityRing cityRing) {
        this.cityRing = cityRing;
        this.context = context;
        this.activity = activity;
        loadingPopup = new LoadingPopup(context, this, activity);

    }

    @Override
    protected JSONObject doInBackground(String... params) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        // send in user's lat and long to make search query
        URL url = URLMaker.getURL(
                Double.valueOf(params[0]),
                Double.valueOf(params[1]),
                Integer.parseInt(sharedPreferences.getString("cities", Constants.DEFAULT_CITIES)),
                Integer.parseInt(sharedPreferences.getString("radius", Constants.DEFAULT_SEARCH_RADIUS)),
                context);

        JSONObject object = JSONGetter.getJSONFromUrl(url, this.activity);


        return object;
    }

    // memory leaks if dialog is not removed when paused
    public void pause() {
        loadingPopup.dismiss();
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        loadingPopup.dismiss();

        List cityList = new LinkedList();

        try {
            cityList.addAll(CityMaker.getCities(result));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v(TAG, e.toString());
        }
        cityRing.setCitiesAndUpdate(cityList);
    }
}
