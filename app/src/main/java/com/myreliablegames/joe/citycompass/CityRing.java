package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 11/18/2015.
 */
public class CityRing implements SensorEventListener {

    List<City> cities;
    List<TextView> textViews;
    Activity activity;
    Compass compass;
    private SensorManager sensorManager;
    double radius;
    final TextView citiesAheadView;
    StringBuilder citiesAhead;

    public CityRing(Activity activity, Compass compass) {
        this.activity = activity;
        this.compass = compass;
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        textViews = new ArrayList<>();

        // ring radius is 1/4 the short side of the decive screen size
        radius = DisplayHelper.getShortSide(activity) / 4;

        citiesAheadView = new TextView(activity.getApplicationContext());
        citiesAheadView.setTextColor(Color.BLACK);
        citiesAheadView.setShadowLayer(15f, -5, 0, Color.WHITE);
        citiesAheadView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        lp.setMargins(0, 10, 0, 0); // a little top margin
        citiesAheadView.setLayoutParams(lp);


        activity.addContentView(citiesAheadView, lp);
        citiesAhead = new StringBuilder();


    }

    public void resume() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pause() {
        sensorManager.unregisterListener(this);
    }

    public void setCitiesAndUpdate(List<City> cities) {
        synchronized (cities) {
            if (this.cities != null) {
                this.cities.clear();
            }
            this.cities = cities;
        }
        placeCities();
        update();
    }

    public boolean hasCities() {

        if (cities != null) {
            return !cities.isEmpty();
        } else return false;
    }

    public void placeCities() {

        synchronized (cities) {

            for (City c : cities) {

                double radAngle = Math.toRadians(CityHelper.getBearing(c) + compass.getCompassDegree() - 90);

                TextView textView = new TextView(activity.getApplicationContext());
                textView.setText(" \u2022   " + (c.getName()) + " (" + Integer.toString(CityHelper.distanceToCity(c).intValue()) + " Mi)");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);
                textView.setShadowLayer(25f, 1, 1, Color.WHITE);

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.CENTER;
                textView.setLayoutParams(lp);

                textView.setTranslationX((float) radius * (float) Math.cos(radAngle));
                textView.setTranslationY((float) radius * (float) Math.sin(radAngle));

                // Set the rotation of the view.
                textView.setRotation((float) CityHelper.getBearing(c) + (float) compass.getCompassDegree() - 90);

                activity.addContentView(textView, lp);
                textViews.add(textView);

                // Log.v("CityRing", c.name + " Bearing: " + CityHelper.getBearing(c) + " Rad Angle: " + radAngle + " Compass Angle: " + compass.getCompassDegree() + " Current Degree: " + c.currentDegree);

            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (cities != null) {
            update();
        }
    }

    private void update() {
        RingUpdater.update(this);
    }

    public void clear() {
        synchronized (this.cities) {
            this.cities.clear();
        }
        for (TextView v : textViews) {
            ((ViewManager) v.getParent()).removeView(v);
        }
        textViews.clear();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
