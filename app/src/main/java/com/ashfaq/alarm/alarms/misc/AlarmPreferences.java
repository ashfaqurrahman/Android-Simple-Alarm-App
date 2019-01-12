

package com.ashfaq.alarm.alarms.misc;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;

import com.ashfaq.alarm.R;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 *
 * Utilities for reading alarm preferences.
 */
public final class AlarmPreferences {
    private static final String TAG = "AlarmPreferences";

    private AlarmPreferences() {}

    public static int snoozeDuration(Context c) {
        return readPreference(c, R.string.key_snooze_duration, 10);
    }

    // TODO: Consider renaming to hoursToNotifyInAdvance()
    public static int hoursBeforeUpcoming(Context c) {
        return readPreference(c, R.string.key_notify_me_of_upcoming_alarms, 2);
    }

    public static int minutesToSilenceAfter(Context c) {
        return readPreference(c, R.string.key_silence_after, 15);
    }

    public static int firstDayOfWeek(Context c) {
        return readPreference(c, R.string.key_first_day_of_week, 0 /* Sunday */);
    }

    public static int readPreference(Context c, @StringRes int key, int defaultValue) {
        String value = PreferenceManager.getDefaultSharedPreferences(c).getString(c.getString(key), null);
        return null == value ? defaultValue : Integer.parseInt(value);
    }
}
