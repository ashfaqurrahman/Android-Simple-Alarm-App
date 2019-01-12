

package com.ashfaq.alarm.stopwatch.data;

import android.database.Cursor;

import com.ashfaq.alarm.data.BaseItemCursor;
import com.ashfaq.alarm.stopwatch.Lap;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public class LapCursor extends BaseItemCursor<Lap> {

    public LapCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public Lap getItem() {
        Lap lap = new Lap();
        lap.setId(getLong(getColumnIndexOrThrow(LapsTable.COLUMN_ID)));
        lap.setT1(getLong(getColumnIndexOrThrow(LapsTable.COLUMN_T1)));
        lap.setT2(getLong(getColumnIndexOrThrow(LapsTable.COLUMN_T2)));
        lap.setPauseTime(getLong(getColumnIndexOrThrow(LapsTable.COLUMN_PAUSE_TIME)));
        lap.setTotalTimeText(getString(getColumnIndexOrThrow(LapsTable.COLUMN_TOTAL_TIME_TEXT)));
        return lap;
    }
}
