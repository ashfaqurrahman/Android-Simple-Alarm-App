

package com.ashfaq.alarm.alarms.data;

import android.content.Context;

import com.ashfaq.alarm.alarms.Alarm;
import com.ashfaq.alarm.data.SQLiteCursorLoader;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public class AlarmsListCursorLoader extends SQLiteCursorLoader<Alarm, AlarmCursor> {
    public static final String ACTION_CHANGE_CONTENT
            = "com.ashfaq.alarm.alarms.data.action.CHANGE_CONTENT";

    public AlarmsListCursorLoader(Context context) {
        super(context);
    }

    @Override
    protected AlarmCursor loadCursor() {
        return new AlarmsTableManager(getContext()).queryItems();
    }

    @Override
    protected String getOnContentChangeAction() {
        return ACTION_CHANGE_CONTENT;
    }
}
