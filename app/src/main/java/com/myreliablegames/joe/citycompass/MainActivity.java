package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity  {

    private DataCollector dataCollector;
    private Compass compass;
    private Locater locater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locater = new Locater(this);
        compass = new Compass(this);
        dataCollector = new DataCollector(MainActivity.this, this);
        Double[] latLng = locater.getLatLng();
        dataCollector.execute(Double.toString(latLng[0]), Double.toString(latLng[1]));


    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.pause();
    }


}