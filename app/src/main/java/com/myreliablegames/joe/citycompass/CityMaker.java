package com.myreliablegames.joe.citycompass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joe on 11/18/2015.
 */
public class CityMaker {

    private CityMaker() {
    }

    public static List getCities(JSONObject object) throws JSONException {

        List cityList = new LinkedList();
        JSONArray results = object.getJSONArray("geonames");

        for (int i = 0; i < results.length(); i++) {
            JSONObject obj = results.getJSONObject(i);
            cityList.add(new City(obj.getString("name"), obj.getDouble("lat"), obj.getDouble("lng")));
        }
        return cityList;
    }
}
