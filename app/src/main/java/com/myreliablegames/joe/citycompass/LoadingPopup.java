package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Joe on 11/14/2015.
 */
public class LoadingPopup extends ProgressDialog {

    public LoadingPopup(Context context, final DataCollector dataCollector, final Activity activity) {
        super(context);
        // show loading bar
        this.setMessage("Searching...");
        this.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dataCollector.cancel(true);
                activity.finish();
            }
        });
    }


}
