

package com.ashfaq.alarm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ashfaq.alarm.alarms.data.AlarmsTable;
import com.ashfaq.alarm.stopwatch.data.LapsTable;
import com.ashfaq.alarm.timers.data.TimersTable;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public class ClockAppDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ClockAppDatabaseHelper";
    private static final String DB_NAME = "clock_app.db";
    private static final int VERSION_1 = 1;

    private static ClockAppDatabaseHelper sDatabaseHelper;

    public static ClockAppDatabaseHelper getInstance(Context context) {
        if (sDatabaseHelper == null)
            sDatabaseHelper = new ClockAppDatabaseHelper(context);
        return sDatabaseHelper;
    }

    /**
     * @param context the Context with which the application context will be retrieved
     */
    private ClockAppDatabaseHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, VERSION_1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AlarmsTable.onCreate(db);
        TimersTable.onCreate(db);
        LapsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AlarmsTable.onUpgrade(db, oldVersion, newVersion);
        TimersTable.onUpgrade(db, oldVersion, newVersion);
        LapsTable.onUpgrade(db, oldVersion, newVersion);
    }
}
