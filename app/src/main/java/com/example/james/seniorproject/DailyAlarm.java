package com.example.james.seniorproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class DailyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // remind the user to do their homework!
        Toast.makeText(context, "Time to check you planner!", Toast.LENGTH_LONG).show();
////
////        Snackbar snackbar = Snackbar
////                .make(context.view, "Time to check your planner!", Snackbar.LENGTH_LONG);
////        snackbar.setAction("OPEN", new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////            }
////        });
////        snackbar.setActionTextColor(Color.RED);
////        snackbar.show();

    }

    public void setAlarm(Context context, int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        long triggerAt = cal.getTimeInMillis();
        long intervalMillis = 24 * 3600 * 1000 /* one day */ ;

        //
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAt, intervalMillis, pendingIntent);
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, DailyAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
