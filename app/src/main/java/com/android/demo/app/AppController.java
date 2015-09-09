package com.android.demo.app;

import android.app.Application;
import android.provider.Settings;

import com.android.demo.constants.Constants;
import com.android.demo.database.DatabaseHelper;
import com.android.demo.manager.DatabaseManager;
import com.android.demo.preferences.SecurePreferences;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * This is the Application instance class.
 * This will start when the application starts.
 */
public class AppController extends Application {

    private static AppController mInstance;
    private static SecurePreferences mSecurePreferences;

    /**
     * This is the constructor of AppController
     */
    public AppController() {
        mInstance = this;
    }

    /**
     * this is for returning the appcontroller singleton instance
     *
     * @return instance of the AppController
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        /* load library for sqlite */
        SQLiteDatabase.loadLibs(getApplicationContext());

       /* initializing database instances */
        DatabaseManager.initializeInstance(new DatabaseHelper(getApplicationContext()));
        //Initialize the preferences
        mSecurePreferences = new SecurePreferences(getApplicationContext(), Constants.APP_PREFERENCES, getAndroidId(), true);
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

    /**
     * @return the secure preferences of the application.
     */
    public SecurePreferences getPreferences() {
        return mSecurePreferences;
    }
}
