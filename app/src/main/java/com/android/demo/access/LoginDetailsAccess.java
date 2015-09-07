package com.android.demo.access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.android.demo.data.LoginDetailsData;
import com.android.demo.database.AbstractDataBase;
import com.android.demo.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by 321930 on 1/28/2015.
 */
public class LoginDetailsAccess extends AbstractDataBase {

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_USERNAME = "Username";
	public static final String COLUMN_PASSWORD = "Password";
	public static final String COLUMN_LOGIN_TIME = "UserLoginTime";

	public static final String TABLE_NAME = "tbl_login_details";

	private LoginDetailsData mLoginDetailsData = null;


	public LoginDetailsAccess(SQLiteDatabase database) {
		super(database);
	}

	@Override
	protected String getPrimaryKey() {
		return COLUMN_ID;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected String[] getColumns() {
		return new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_LOGIN_TIME};
	}

	@Override
	protected Object loadColumns(Cursor c) {
		final ArrayList<LoginDetailsData> listLoginDetails = new ArrayList<LoginDetailsData>();
		LoginDetailsData loginDetailsData;

		if (null != c && c.getCount() > 0) {
			c.moveToFirst();
			do {
				loginDetailsData = new LoginDetailsData();

				loginDetailsData.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
				loginDetailsData.setUserName(c.getString(c.getColumnIndex(COLUMN_USERNAME)));
				loginDetailsData.setPassword(c.getString(c.getColumnIndex(COLUMN_PASSWORD)));
				loginDetailsData.setLoginTime(c.getLong(c.getColumnIndex(COLUMN_LOGIN_TIME)));

				listLoginDetails.add(loginDetailsData);

			} while (c.moveToNext());
		}

		return listLoginDetails;
	}

	@Override
	protected ContentValues buildColumns(Object obj) {
		mLoginDetailsData = (LoginDetailsData) obj;
		ContentValues contentValues = new ContentValues();

		//contentValues.put(COLUMN_ID, mLoginDetailsData.getId());
		contentValues.put(COLUMN_USERNAME, mLoginDetailsData.getUserName());
		contentValues.put(COLUMN_PASSWORD, mLoginDetailsData.getPassword());
		contentValues.put(COLUMN_LOGIN_TIME, mLoginDetailsData.getLoginTime());

		return contentValues;
	}
}
