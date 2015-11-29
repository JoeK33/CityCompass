package com.myreliablegames.joe.citycompass;

/**
 * Created by Joe on 11/18/2015.
 */
public class City {

    private String name;
    private Double latitude;
    private Double longitude;
    private double currentDegree;

    public City(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        currentDegree = 0;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public double getCurrentDegree() {
        return currentDegree;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCurrentDegree(double currentDegree) {
        this.currentDegree = currentDegree;
    }
}
