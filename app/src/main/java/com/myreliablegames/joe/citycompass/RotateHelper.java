package com.myreliablegames.joe.citycompass;

/**
 * Created by Joe on 11/24/2015.
 * <p/>
 * This helper method corrects rotation angles so things don't rotate 360 over the compass axis when rotating from 0 to 360 or vice versa.
 */
public class RotateHelper {

    public static double getProperRotation(Compass compass, double initialDegree) {

        double currentCompassDegree = compass.getCompassDegree();
        double degree = initialDegree;

        // correct rotation angles. otherwise needle does a 360 when it crosses the 0 point
        if (currentCompassDegree < -180) {
            currentCompassDegree += 360;
        }
        while (degree - currentCompassDegree < -180) {
            degree += 360;
        }
        while (degree - currentCompassDegree > 180) {
            degree -= 360;
        }
        return degree;
    }
}
