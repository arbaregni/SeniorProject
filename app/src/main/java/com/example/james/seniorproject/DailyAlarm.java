package com.example.james.seniorproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

class DailyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // remind the user to do their homework!
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "studentPlanner:dailyAlarm");
        wakeLock.acquire(10*60*1000L /*10 minutes*/);

        // display the message
        Toast.makeText(context, "Time to check you planner!", Toast.LENGTH_LONG).show();

//        Snackbar snackbar = Snackbar
//                .make(context.view, "Time to check your planner!", Snackbar.LENGTH_LONG);
//        snackbar.setAction("OPEN", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        snackbar.setActionTextColor(Color.RED);
//        snackbar.show();

        wakeLock.release();
    }

    public void setAlarm(Context context) {

    }
}
