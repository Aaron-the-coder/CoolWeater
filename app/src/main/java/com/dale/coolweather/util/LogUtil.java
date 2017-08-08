package com.dale.coolweather.util;

import android.util.Log;

/**
 * Created by dale on 2017/8/8.
 */

public class LogUtil {
    private static int LEVEL_VERBOSE = 0;
    private static int LEVEL_DEBUG = 1;
    private static int LEVEL_INFO = 2;
    private static int LEVEL_WARN = 3;
    private static int LEVEL_ERROR = 4;
    private static int LEVEL_NOTHING = 5;

    private static int CURRENT_LEVEL = LEVEL_ERROR;

    public static void v(String tag, String content) {
        if (CURRENT_LEVEL <= LEVEL_VERBOSE)
        Log.v(tag, content);
    }
    public static void d(String tag, String content) {
        if (CURRENT_LEVEL <= LEVEL_DEBUG)
        Log.d(tag, content);
    }
    public static void i(String tag, String content) {
        if (CURRENT_LEVEL <= LEVEL_INFO)
        Log.i(tag, content);
    }
    public static void w(String tag, String content) {
        if (CURRENT_LEVEL <= LEVEL_WARN)
        Log.w(tag, content);
    }
    public static void e(String tag, String content) {
        if (CURRENT_LEVEL <= LEVEL_ERROR)
        Log.e(tag, content);
    }
}
