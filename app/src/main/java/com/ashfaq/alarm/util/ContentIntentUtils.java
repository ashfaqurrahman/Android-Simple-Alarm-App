

package com.ashfaq.alarm.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.ashfaq.alarm.MainActivity;
import com.ashfaq.alarm.list.RecyclerViewFragment;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 *
 * Helper to create content intents for e.g. notifications that should
 * open the app, scroll to the specified page, and then scroll to the
 * item with the specified stable id.
 */
public final class ContentIntentUtils {

    public static PendingIntent create(@NonNull Context context, int targetPage, long stableId) {
        Intent intent = new Intent(context, MainActivity.class)
                .setAction(RecyclerViewFragment.ACTION_SCROLL_TO_STABLE_ID)
                .putExtra(MainActivity.EXTRA_SHOW_PAGE, targetPage)
                .putExtra(RecyclerViewFragment.EXTRA_SCROLL_TO_STABLE_ID, stableId);
        return PendingIntent.getActivity(context, (int) stableId, intent, FLAG_CANCEL_CURRENT);
    }

    private ContentIntentUtils() {}
}
