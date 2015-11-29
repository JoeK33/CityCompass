package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Joe on 11/29/2015.
 */
public class Buttons {

    public Buttons(final MainActivity mainActivity){

        final ImageButton refreshButton = (ImageButton) mainActivity.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivity.refresh();
            }
        });

        final ImageButton settingsButton = (ImageButton) mainActivity.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, SettingsActivity.class);
                mainActivity.startActivityForResult(intent, 1);
            }
        });
    }
}
