package com.myreliablegames.joe.citycompass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Joe on 11/14/2015.
 */
public class LoadingPopup extends ProgressDialog {

    Context context;

    public LoadingPopup(Context context, final GetDataTask getDataTask, final Activity activity) {
        super(context);
        this.context = context;
        // show loading bar
        this.setMessage("Searching...");
        this.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getDataTask.cancel(true);
                activity.finish();
            }
        });
    }


}
