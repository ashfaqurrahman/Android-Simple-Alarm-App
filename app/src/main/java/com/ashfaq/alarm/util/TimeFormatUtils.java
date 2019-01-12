

package com.ashfaq.alarm.util;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import static android.text.format.DateFormat.getTimeFormat;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public final class TimeFormatUtils {

    private TimeFormatUtils() {}

    public static String formatTime(Context context, long millis) {
        return getTimeFormat(context).format(new Date(millis));
    }

    public static String formatTime(Context context, int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        return formatTime(context, cal.getTimeInMillis());
    }
}
