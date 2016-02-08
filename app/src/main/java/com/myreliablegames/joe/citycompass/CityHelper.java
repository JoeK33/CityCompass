package com.myreliablegames.joe.citycompass;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Joe on 11/18/2015.
 */
public class CityHelper {

    private CityHelper() {
    }

    public static Double distanceToCity(City city, Context context) {
        Double[] location = MainActivity.locater.getLatLng();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String unit = sharedPreferences.getString("units", "Mi");
        Double distance = DistanceCalculator.distance(location[0], location[1], city.getLatitude(), city.getLongitude(), "M");

        if (unit.equalsIgnoreCase("Km")) {
            distance = DistanceCalculator.distance(location[0], location[1], city.getLatitude(), city.getLongitude(), "K");
        } else if (unit.equalsIgnoreCase("N-Mi")) {
            distance = DistanceCalculator.distance(location[0], location[1], city.getLatitude(), city.getLongitude(), "N");
        }
        return distance;
    }


    public static double getBearing(City city) {

        Double[] location = MainActivity.locater.getLatLng();

        double userLat = Math.toRadians(location[0]);
        double userLong = location[1];

        double cityLat = Math.toRadians(city.getLatitude());
        double cityLong = city.getLongitude();

        double longDiff = Math.toRadians(cityLong - userLong);

        double y = Math.sin(longDiff) * Math.cos(cityLat);
        double x = Math.cos(userLat) * Math.sin(cityLat) - Math.sin(userLat) * Math.cos(cityLat) * Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    public static String appendUnits(Context context) {
        String unitString;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        unitString = sharedPreferences.getString("units", "Mi");

        return " " + unitString + ")";
    }


}



