package com.android.demo.database;

import android.content.Context;

import com.android.demo.access.DemoDataAccess;
import com.android.demo.constants.Constants;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "Database.db";
    // Database Version
    private static final int DATABASE_VERSION = Constants.DB_VERSION;
    private static final String DROP_TABLE_PRE_FIX = "DROP TABLE IF EXISTS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(DemoDataAccess.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // create new tables
        db.execSQL(DROP_TABLE_PRE_FIX + " " + DemoDataAccess.TABLE_NAME);
        onCreate(db);
    }
}

