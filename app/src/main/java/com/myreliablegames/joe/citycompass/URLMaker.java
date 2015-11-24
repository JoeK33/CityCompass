package com.myreliablegames.joe.citycompass;

import android.content.Context;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Joe on 11/15/2015.
 */
public class URLMaker {

    private URLMaker() {
    }

    private static final String TAG = "URL Maker";

    public static URL getURL(Double latitude, Double longitude, int numCities, int searchRadius, Context context) {

        // example url: http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo

        GeoLocation[] location = GeoLocation.fromDegrees(latitude, longitude).boundingCoordinates(searchRadius, Constants.EARTH_RADIUS_MILES);

        StringBuilder urlString = new StringBuilder();
        urlString.append("http://api.geonames.org/citiesJSON?");
        urlString.append("&north=").append(location[1].getLatitudeInDegrees());
        urlString.append("&south=").append(location[0].getLatitudeInDegrees());
        urlString.append("&east=").append(location[1].getLongitudeInDegrees());
        urlString.append("&west=").append(location[0].getLongitudeInDegrees());
        urlString.append("&username=").append(context.getResources().getString((R.string.username)));
        urlString.append("&maxRows=");
        urlString.append(numCities);

        Log.v("JSON URL", urlString.toString());

        URL url = null;

        try {
            url = new URL(urlString.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }

        return url;
    }
}
