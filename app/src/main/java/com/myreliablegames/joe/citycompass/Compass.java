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
    private double currentCompassDegree = 0f;
    private SensorManager sensorManager;
    private double degree;
    private float rotDegree;

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
        // get the angle rotated around the z-axis. negative to make the needle rotate the right direction
        degree = -event.values[0];
        // correct rotation so needle does not do a 360 over the north axis
        rotDegree = (float) RotateHelper.getProperRotation(this, degree);
        compassNeedle.animate().rotation(rotDegree).setDuration(150);
        compassNeedle.setRotation(rotDegree);
        currentCompassDegree = rotDegree;

        // Log.v("Compass", Double.toString(degree));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public double getCompassDegree() {
        return currentCompassDegree;
    }
}
