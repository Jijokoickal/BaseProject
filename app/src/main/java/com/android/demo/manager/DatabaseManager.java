package com.android.demo.manager;



import com.android.demo.app.AppController;
import com.android.demo.log.AppLog;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (C) 2015 Abbvie. All rights reserved.
 * Thread safe db manager class for opening and closing the database
 */
public class DatabaseManager {

    private static DatabaseManager sDatabaseManager;

    //SQLite helper object
    private static SQLiteOpenHelper sDatabaseOpenHelper;

    //integer to check whether db is currently opened or closed.
    private final AtomicInteger mOpenCounter = new AtomicInteger();

    //SQLite database object
    private SQLiteDatabase mSQLiteDatabase;

    private final String mPassword = AppController.getInstance().getAndroidId();

    /*As private constructor is used so can not create object of this class directly.*/
    private DatabaseManager() {

    }

    /**
     * initialize manager instance.
     *
     * @param helper -SQLiteOpenHelper object
     */
    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (sDatabaseManager == null) {
            sDatabaseManager = new DatabaseManager();
            sDatabaseOpenHelper = helper;
        }
    }

    /**
     * singleton instance for DatabaseManager class
     *
     * @return
     */
    public static synchronized  DatabaseManager getInstance() throws IllegalStateException {
        if (sDatabaseManager == null) {
            AppLog.logString(DatabaseManager.class.getSimpleName()
                    + " is not initialized, call initializeInstance(..) method first.");
        }

        return sDatabaseManager;
    }


    /**
     * open db by checking teh atomic variable.
     *
     * @return
     */
    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1 ) {
            // Opening new database
            mSQLiteDatabase = sDatabaseOpenHelper.getWritableDatabase(mPassword);
        }
        return mSQLiteDatabase;
    }

    /**
     * close db by checking teh atomic variable.
     *
     * @return
     */
    public synchronized void closeDatabaseOnAppExit() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mSQLiteDatabase.close();
        }
    }
}
