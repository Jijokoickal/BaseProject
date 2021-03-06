package com.android.demo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.demo.constants.Constants;


/**
 * Copyright (C) 2015 Abbvie. All rights reserved.
 */
public class NetworkUtil {
    /**
     * Helper method to check network availability
     *
     * @param context - calling context
     * @return - Returns true if network is available otherwise false
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase(Constants.WIFI) && ni.isConnected()) {
                haveConnectedWifi = true;
            }

            if (ni.getTypeName().equalsIgnoreCase(Constants.MOBILE) && ni.isConnected()) {
                haveConnectedMobile = true;
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
