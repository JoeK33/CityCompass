package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    private GetDataTask getDataTask;
    private Compass compass;
    public static Locater locater;
    private CityRing ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        locater = new Locater(this);
        compass = new Compass(this);
        ring = new CityRing(this, compass);
        getDataTask = new GetDataTask(MainActivity.this, this, ring);

        final ImageButton button = (ImageButton) findViewById(R.id.refreshButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ring.clear();
                Double[] latLng = locater.getLatLng();
                getDataTask = new GetDataTask(MainActivity.this, MainActivity.this, ring);
                getDataTask.execute(Double.toString(latLng[0]), Double.toString(latLng[1]));
            }
        });

        final ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.resume();
        ring.resume();
        // search for cities if there aren't any
        if (!ring.hasCities()) {
            Double[] latLng = locater.getLatLng();
            getDataTask = new GetDataTask(MainActivity.this, this, ring);
            getDataTask.execute(Double.toString(latLng[0]), Double.toString(latLng[1]));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.pause();
        ring.pause();
        getDataTask.pause();
    }


}