package com.example.james.seniorproject;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class Util {
    public static String formatDateDifference(Date startDate, Date endDate) {
        double dif = (double) Math.abs(endDate.getTime() - startDate.getTime());
        String timeSpan = null;
        long days = Math.round(dif / DateUtils.DAY_IN_MILLIS);
        if (days == 0)
            return "today";
        if (days == 1) {
            timeSpan = "1 day";
        } else if (days < 7) {
            timeSpan = days + " days";
        } else {
            long weeks = Math.round(dif / DateUtils.WEEK_IN_MILLIS);
            if (weeks == 1)
                timeSpan = "about 1 week";
            else
                timeSpan = "about " + weeks + " weeks";
        }

        if (endDate.before(startDate)) {
            return timeSpan + " ago";
        } else {
            return "in " + timeSpan;
        }
    }
}
