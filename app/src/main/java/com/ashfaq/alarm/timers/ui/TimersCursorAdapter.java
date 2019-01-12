

package com.ashfaq.alarm.timers.ui;

import android.view.ViewGroup;

import com.ashfaq.alarm.timers.data.AsyncTimersTableUpdateHandler;
import com.ashfaq.alarm.list.BaseCursorAdapter;
import com.ashfaq.alarm.list.OnListItemInteractionListener;
import com.ashfaq.alarm.timers.Timer;
import com.ashfaq.alarm.timers.data.TimerCursor;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public class TimersCursorAdapter extends BaseCursorAdapter<Timer, TimerViewHolder, TimerCursor> {

    private final AsyncTimersTableUpdateHandler mAsyncTimersTableUpdateHandler;

    public TimersCursorAdapter(OnListItemInteractionListener<Timer> listener,
                               AsyncTimersTableUpdateHandler asyncTimersTableUpdateHandler) {
        super(listener);
        mAsyncTimersTableUpdateHandler = asyncTimersTableUpdateHandler;
    }

    @Override
    protected TimerViewHolder onCreateViewHolder(ViewGroup parent, OnListItemInteractionListener<Timer> listener, int viewType) {
        return new TimerViewHolder(parent, listener, mAsyncTimersTableUpdateHandler);
    }


}
