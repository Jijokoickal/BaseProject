package com.android.demo.access;

import android.content.ContentValues;
import android.database.Cursor;

import com.android.demo.constants.Constants;
import com.android.demo.database.AbstractDb;
import com.android.demo.pojo.DemoData;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

/**
 * This is the sample data access class created for reference.
 */
public class DemoDataAccess extends AbstractDb {

    public static final String TABLE_NAME = "demo_data_table";

    //Column names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "_name";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " " + Constants.FIELD_INTEGER + " PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
            COLUMN_NAME + " " + Constants.FIELD_VARCHAR + ")";

    private DemoData demoData = null;

    public DemoDataAccess(SQLiteDatabase database) {
        super(database);
    }

    @Override
    protected String getPrimaryKey() {
        return COLUMN_ID;
    }

    @Override
    protected String[] getColumns() {
        return new String[]{COLUMN_ID, COLUMN_NAME};
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Object loadColumns(Cursor c) {

        ArrayList<DemoData> codeInfoDataList = new ArrayList<>();
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                demoData = new DemoData();

                demoData.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                demoData.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));

                codeInfoDataList.add(demoData);
            } while (c.moveToNext());
            c.close();
        }
        return codeInfoDataList;
    }

    @Override
    protected ContentValues buildColumns(Object obj) {
        demoData = (DemoData) obj;

        ContentValues contentValues = new ContentValues();

        /**
         * primary keys will not be written in to content values,
         * they will be automatically increment,
         * unless specified.
         */
        //contentValues.put(COLUMN_ID, demoData.getId());

        contentValues.put(COLUMN_NAME, demoData.getName());

        return contentValues;
    }
}
