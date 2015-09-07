package com.android.demo.log;


import android.util.Log;

import com.android.demo.BuildConfig;
import com.android.demo.constants.Constants;

/**
 * Application Log class
 */
public class AppLog {
    // Application Tag

    // Log a String
    public static void logString(String message) {
        // If log is enabled, the print the log
        if (Constants.IS_LOG_ENABLED && BuildConfig.DEBUG) {
            Log.i(Constants.APP_TAG, " " + message);
        }
    }
}