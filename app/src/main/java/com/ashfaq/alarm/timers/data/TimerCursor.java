

package com.ashfaq.alarm.timers.data;

import android.database.Cursor;

import com.ashfaq.alarm.timers.Timer;
import com.ashfaq.alarm.data.BaseItemCursor;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public class TimerCursor extends BaseItemCursor<Timer> {

    public TimerCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public Timer getItem() {
        if (isBeforeFirst() || isAfterLast())
            return null;
        int hour = getInt(getColumnIndexOrThrow(TimersTable.COLUMN_HOUR));
        int minute = getInt(getColumnIndexOrThrow(TimersTable.COLUMN_MINUTE));
        int second = getInt(getColumnIndexOrThrow(TimersTable.COLUMN_SECOND));
        String label = getString(getColumnIndexOrThrow(TimersTable.COLUMN_LABEL));
//            String group = getString(getColumnIndexOrThrow(COLUMN_GROUP));
        Timer t = Timer.create(hour, minute, second, ""/*group*/, label);
        t.setId(getLong(getColumnIndexOrThrow(TimersTable.COLUMN_ID)));
        t.setEndTime(getLong(getColumnIndexOrThrow(TimersTable.COLUMN_END_TIME)));
        t.setPauseTime(getLong(getColumnIndexOrThrow(TimersTable.COLUMN_PAUSE_TIME)));
        t.setDuration(getLong(getColumnIndexOrThrow(TimersTable.COLUMN_DURATION)));
        return t;
    }
}
