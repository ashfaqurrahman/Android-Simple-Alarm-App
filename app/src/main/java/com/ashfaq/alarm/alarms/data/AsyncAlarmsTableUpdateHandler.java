

package com.ashfaq.alarm.alarms.data;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.ashfaq.alarm.data.AsyncDatabaseTableUpdateHandler;
import com.ashfaq.alarm.R;
import com.ashfaq.alarm.alarms.Alarm;
import com.ashfaq.alarm.list.ScrollHandler;
import com.ashfaq.alarm.alarms.misc.AlarmController;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 * TODO: Consider making an AsyncDatabaseChangeHandlerWithSnackbar abstract class
 */
public final class AsyncAlarmsTableUpdateHandler extends AsyncDatabaseTableUpdateHandler<Alarm, AlarmsTableManager> {
    private static final String TAG = "AsyncAlarmsTableUpdateHandler";

    private final View mSnackbarAnchor;
    private final AlarmController mAlarmController;

    /**
     * @param context the Context from which we get the application context
     * @param snackbarAnchor
     */
    public AsyncAlarmsTableUpdateHandler(Context context, View snackbarAnchor,
                                         ScrollHandler scrollHandler,
                                         AlarmController alarmController) {
        super(context, scrollHandler);
        mSnackbarAnchor = snackbarAnchor;
        mAlarmController = alarmController;
    }

    @Override
    protected AlarmsTableManager onCreateTableManager(Context context) {
        return new AlarmsTableManager(context);
    }

    @Override
    protected void onPostAsyncDelete(Integer result, final Alarm alarm) {
        mAlarmController.cancelAlarm(alarm, false, false);
        if (mSnackbarAnchor != null) {
            // TODO: Consider adding delay to allow the alarm item animation
            // to finish first before we show the snackbar. Inbox app does this.
            String message = getContext().getString(R.string.snackbar_item_deleted,
                    getContext().getString(R.string.alarm));
            Snackbar.make(mSnackbarAnchor, message, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbar_undo_item_deleted, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            asyncInsert(alarm);
                        }
                    }).show();
        }
    }

    @Override
    protected void onPostAsyncInsert(Long result, Alarm alarm) {
        mAlarmController.scheduleAlarm(alarm, true);
    }

    @Override
    protected void onPostAsyncUpdate(Long result, Alarm alarm) {
        mAlarmController.scheduleAlarm(alarm, true);
    }
}
