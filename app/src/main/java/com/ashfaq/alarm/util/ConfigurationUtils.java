

package com.ashfaq.alarm.util;

import android.content.res.Resources;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public final class ConfigurationUtils {

    public static int getOrientation(Resources res) {
        return res.getConfiguration().orientation;
    }

    private ConfigurationUtils() {}

}
