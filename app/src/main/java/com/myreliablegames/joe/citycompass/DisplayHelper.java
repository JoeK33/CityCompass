package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Joe on 11/29/2015.
 * This class helps with the phone screen size.
 */
public class DisplayHelper {

    private DisplayHelper() {
    }

    public static double getShortSide(Activity activity) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        double screenWidth = displaymetrics.widthPixels;
        double screenHeight = displaymetrics.heightPixels;
        double shortSide;

        if (screenWidth <= screenHeight) {
            shortSide = screenWidth;
        } else shortSide = screenHeight;

        return shortSide;

    }
}
