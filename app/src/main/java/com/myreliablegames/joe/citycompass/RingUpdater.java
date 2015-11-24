package com.myreliablegames.joe.citycompass;

import android.widget.TextView;

import java.util.Iterator;

/**
 * Created by Joe on 11/24/2015.
 */
public class RingUpdater {

    private RingUpdater() {
    }


    public static void update(final CityRing ring) {

        ring.citiessAhead.setLength(0);

        new Thread(new Runnable() {
            public void run() {

                int i = 0;
                Iterator iterator = ring.cities.iterator();
                while (iterator.hasNext()) {

                    final City c = (City) iterator.next();
                    final TextView textView = ring.textViews.get(i);
                    final double radAngle = Math.toRadians((CityHelper.getBearing(c) + ring.compass.getCompassDegree() - 90 + 360) % 360);
                    final double degAngle = (CityHelper.getBearing(c) + ring.compass.getCompassDegree() - 90 + 360) % 360;

                    textView.post(new Runnable() {
                        public void run() {
                            textView.animate().translationX((float) (ring.radius + textView.getWidth() / 2) * (float) Math.cos(radAngle));
                            textView.animate().translationY((float) (ring.radius + textView.getWidth() / 2) * (float) Math.sin(radAngle));

                            if (c.currentDegree > 90 && c.currentDegree < 275) {

                                textView.animate().rotation((float) degAngle + 180).setDuration(100);
                                textView.setRotation((float) degAngle + 180);
                            } else {
                                double rotDegree = RotateHelper.getProperRotation(ring.compass, degAngle);
                                textView.animate().rotation((float) rotDegree).setDuration(100);
                                textView.setRotation((float) rotDegree);
                            }
                            textView.setTranslationX((float) (ring.radius + textView.getWidth() / 2) * (float) Math.cos(radAngle));
                            textView.setTranslationY((float) (ring.radius + textView.getWidth() / 2) * (float) Math.sin(radAngle));
                        }
                    });


                    c.currentDegree = degAngle;
                    i++;

                    // offset 90 degrees so cities near top of compass rose are displayed
                    if (c.currentDegree > 265 && c.currentDegree < 275) {
                        ring.activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ring.citiessAhead.append((c.name) + " (" + Integer.toString(CityHelper.distanceToCity(c).intValue()) + " Mi)" + "\n");
                            }
                        });
                    }
                    // move the bullet point to the other side when city textview flips
                    if (c.currentDegree > 90 && c.currentDegree < 275) {

                        ring.activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText((c.name) + " (" + Integer.toString(CityHelper.distanceToCity(c).intValue()) + " Mi)" + "  \u2022 ");
                            }
                        });
                    } else {

                        ring.activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(" \u2022  " + (c.name) + " (" + Integer.toString(CityHelper.distanceToCity(c).intValue()) + " Mi)");
                            }
                        });
                    }
                }

                ring.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ring.citiesAheadView.setText(ring.citiessAhead.toString());
                    }
                });
            }
        }).start();
    }
}
