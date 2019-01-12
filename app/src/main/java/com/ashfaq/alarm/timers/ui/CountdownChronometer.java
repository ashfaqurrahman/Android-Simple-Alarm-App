

package com.ashfaq.alarm.timers.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

import com.ashfaq.alarm.chronometer.BaseChronometer;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 *
 * A modified version of the framework's Chronometer widget to count down
 * towards the base time. The ability to count down was added to Chronometer
 * in API 24.
 */
public class CountdownChronometer extends BaseChronometer {
    private static final String TAG = "CountdownChronometer";

    public CountdownChronometer(Context context) {
        this(context, null, 0);
    }

    public CountdownChronometer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownChronometer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public CountdownChronometer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setCountDown(true);
    }
}
