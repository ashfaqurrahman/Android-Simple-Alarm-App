

package com.ashfaq.alarm.util;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public final class Preconditions {
    private Preconditions() {}

    public static <T> T checkNotNull(T obj) {
        if (null == obj)
            throw new NullPointerException();
        return obj;
    }
}
