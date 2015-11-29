package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private GetDataTask getDataTask;
    private Compass compass;
    public static Locater locater;
    private CityRing cityRing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        locater = new Locater(this);
        compass = new Compass(this);
        cityRing = new CityRing(this, compass);
        getDataTask = new GetDataTask(MainActivity.this, this, cityRing);
        new Buttons(this);

    }

    // refresh after settings activity closes. delayed because cities don't update otherwise
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.resume();
        cityRing.resume();
        // search for cities if there aren't any
        if (!cityRing.hasCities()) {
            refresh();
        }
    }

    public void refresh() {
        if (cityRing.hasCities()) {
            cityRing.clear();
        }
        Double[] latLng = locater.getLatLng();
        getDataTask = new GetDataTask(MainActivity.this, this, cityRing);
        getDataTask.execute(Double.toString(latLng[0]), Double.toString(latLng[1]));
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.pause();
        cityRing.pause();
        getDataTask.pause();
    }
}