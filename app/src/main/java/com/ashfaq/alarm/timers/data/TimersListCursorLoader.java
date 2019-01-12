

package com.ashfaq.alarm.timers.data;

import android.content.Context;

import com.ashfaq.alarm.timers.Timer;
import com.ashfaq.alarm.data.SQLiteCursorLoader;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public class TimersListCursorLoader extends SQLiteCursorLoader<Timer, TimerCursor> {
    public static final String ACTION_CHANGE_CONTENT
            = "com.ashfaq.alarm.timers.data.action.CHANGE_CONTENT";

    public TimersListCursorLoader(Context context) {
        super(context);
    }

    @Override
    protected TimerCursor loadCursor() {
        return new TimersTableManager(getContext()).queryItems();
    }

    @Override
    protected String getOnContentChangeAction() {
        return ACTION_CHANGE_CONTENT;
    }
}
