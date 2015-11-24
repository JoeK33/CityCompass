package com.myreliablegames.joe.citycompass;

/**
 * Created by Joe on 11/18/2015.
 */
public class City {

    public String name;
    public Double latitude;
    public Double longitude;
    public double currentDegree;

    public City(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        currentDegree = 0;
    }

}
