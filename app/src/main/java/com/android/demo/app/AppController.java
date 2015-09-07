package com.android.demo.app;

import android.app.Application;
import android.provider.Settings;

import com.android.demo.constants.Constants;
import com.android.demo.database.DatabaseHelper;
import com.android.demo.manager.DatabaseManager;
import com.android.demo.preferences.SecurePreferences;

/**
 * Created by 321930 on 8/20/2015.
 */
public class AppController extends Application {

    private static AppController mInstance;
    private static SecurePreferences mSecurePreferences;

    public AppController() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        /* load library for sqlite */
        net.sqlcipher.database.SQLiteDatabase.loadLibs(this);

       /* initializing database instances */
        DatabaseManager.initializeInstance(new DatabaseHelper(getApplicationContext()));
        mSecurePreferences = new SecurePreferences(getApplicationContext(), Constants.APP_PREFERENCES, getAndroidId(), true);

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * get android device id
     *
     * @return
     */
    public String getAndroidId() {
        return Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

   /* public SecurePreferences getPreferences() {
        return mSecurePreferences;
    }*/
}
