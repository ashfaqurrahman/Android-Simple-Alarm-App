

package com.ashfaq.alarm.stopwatch.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

import com.ashfaq.alarm.chronometer.BaseChronometer;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 *
 * A modified version of the framework's Chronometer widget that shows
 * up to hundredths of a second.
 */
public class ChronometerWithMillis extends BaseChronometer {
    private static final String TAG = "ChronometerWithMillis";

    public ChronometerWithMillis(Context context) {
        this(context, null, 0);
    }

    public ChronometerWithMillis(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChronometerWithMillis(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public ChronometerWithMillis(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setShowCentiseconds(true, false);
    }
}
