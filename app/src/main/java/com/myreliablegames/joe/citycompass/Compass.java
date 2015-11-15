package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView;

/**
 * Created by Joe on 11/14/2015.
 */
public class Compass implements SensorEventListener {

    private ImageView compassNeedle;
    private float currentCompassDegree = 0f;
    private SensorManager sensorManager;

    public Compass(Activity activity) {
        compassNeedle = (ImageView) activity.findViewById(R.id.imageViewCompassNeedle);
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
    }

    public void resume() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pause() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        compassNeedle.setRotation(-degree);
        currentCompassDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // unused
    }
}
