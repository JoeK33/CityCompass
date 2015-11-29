package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Joe on 11/14/2015.
 */
public class Locater {

    private LocationManager locationManager;
    private final String TAG = "Locater";
    private double longitude, latitude;
    private Activity activity;

    public Locater(Activity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        if (!isLocationEnabled(activity)) {
            Toast.makeText(activity.getApplicationContext(),
                    activity.getApplicationContext().getResources().getString((R.string.no_gps)),
                    Toast.LENGTH_SHORT).show();
        }

        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            longitude = Constants.DEFAULT_LONGITUDE;
            latitude = Constants.DEFAULT_LATITUDE;
        }
    }

    public Double[] getLatLng() {

        if (!isLocationEnabled(activity)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getApplicationContext(),
                            activity.getApplicationContext().getResources().getString((R.string.no_gps)),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        try

        {
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        } catch (
                Exception e
                )

        {
            Log.v(TAG, e.toString());
        }

        return new Double[]

                {
                        latitude, longitude
                }

                ;
    }

    public static boolean isLocationEnabled(Context context) {

        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
