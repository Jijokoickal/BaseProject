package com.android.demo.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseManager {

	private AtomicInteger mOpenCounter = new AtomicInteger();
	private static DatabaseManager mDataBaseManagerInstance;
	private static SQLiteOpenHelper mSqLiteOpenHelper;
	private SQLiteDatabase mSqLiteDatabase;


	public static synchronized void initializeInstance(SQLiteOpenHelper sqLiteOpenHelper) {
		mDataBaseManagerInstance = new DatabaseManager();
		mSqLiteOpenHelper = sqLiteOpenHelper;
	}

	public static DatabaseManager getInstance() {
		if (mDataBaseManagerInstance == null) {
			throw new IllegalStateException(DatabaseManager.class.getSimpleName() + "not initialize, call initialize instance first");
		}
		return mDataBaseManagerInstance;
	}

	public synchronized SQLiteDatabase openDataBase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			mSqLiteDatabase = mSqLiteOpenHelper.getReadableDatabase();
		}
		return mSqLiteDatabase;
	}

	public synchronized void closeDataBase() {
		if (mOpenCounter.decrementAndGet() == 0) {
			mSqLiteOpenHelper.close();
		}
	}
}
