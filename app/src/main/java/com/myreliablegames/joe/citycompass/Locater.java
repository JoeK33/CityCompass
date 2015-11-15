package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by Joe on 11/14/2015.
 */
public class Locater {
    private LocationManager locationManager;
    private final String TAG = "Locater";
    private double longitude, latitude;


    public Locater(Activity activity){
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

    }


public Double[] getLatLng() {

      try {
          Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            Log.v(TAG, "Long: " + longitude + " Lat: " + latitude);
        }catch(Exception e){
            Log.v(TAG,e.toString());
        }

    return new Double[]{latitude,longitude};
}
}
