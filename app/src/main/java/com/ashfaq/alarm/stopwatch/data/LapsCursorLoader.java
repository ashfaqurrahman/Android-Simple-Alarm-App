

package com.ashfaq.alarm.stopwatch.data;

import android.content.Context;

import com.ashfaq.alarm.data.SQLiteCursorLoader;
import com.ashfaq.alarm.stopwatch.Lap;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public class LapsCursorLoader extends SQLiteCursorLoader<Lap, LapCursor> {
    public static final String ACTION_CHANGE_CONTENT
            = "com.ashfaq.alarm.stopwatch.data.action.CHANGE_CONTENT";

    public LapsCursorLoader(Context context) {
        super(context);
    }

    @Override
    protected LapCursor loadCursor() {
        return new LapsTableManager(getContext()).queryItems();
    }

    @Override
    protected String getOnContentChangeAction() {
        return ACTION_CHANGE_CONTENT;
    }
}
