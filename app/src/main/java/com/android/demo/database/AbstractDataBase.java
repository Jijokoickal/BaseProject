package com.android.demo.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 321930 on 1/28/2015.
 */
public abstract class AbstractDataBase {

    protected SQLiteDatabase mSqLiteDatabase;

    // method to set table name
    protected abstract String getTableName();

    // method to set columns
    protected abstract String[] getColumns();

    // method to set values
    protected abstract Object loadColumns(Cursor c);

    // method to build content values
    protected abstract ContentValues buildColumns(Object obj);

    // method to set primary key
    protected abstract String getPrimaryKey();


    public AbstractDataBase(SQLiteDatabase sqLiteDatabase) {
        mSqLiteDatabase = sqLiteDatabase;
    }

    /**
     * Insert into Database
     *
     * @param object
     * @return
     */
    public long insert(final Object object) {
        if (object != null) {
            return insert(getTableName(), buildColumns(object));
        }
        return 0;
    }

    /**
     * @param tableName
     * @param cv
     * @return
     */
    private long insert(final String tableName, final ContentValues cv) {
        return mSqLiteDatabase.insert(tableName, null, cv);
    }

    /**
     * Select from database.
     *
     * @param selection
     * @param selectionArray
     * @param orderBy
     * @return
     */
    public Object select(final String selection, final String[] selectionArray,
                         final String orderBy) {
        final Cursor c = select(getTableName(), getColumns(), selection,
                selectionArray, null, null, orderBy, null);
        return loadColumns(c);
    }

    /**
     * @param tableName
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return
     */
    private Cursor select(final String tableName, final String[] columns,
                          final String selection, final String[] selectionArgs,
                          final String groupBy, final String having, final String orderBy,
                          final String limit) {
        return mSqLiteDatabase.query(tableName, columns, selection, selectionArgs,
                groupBy, having, orderBy, limit);
    }


    /**
     * Delete from database.
     *
     * @param whereCondition
     * @param whereArgs
     */
    public void delete(final String whereCondition, final String[] whereArgs) {
        delete(getTableName(), whereCondition, whereArgs);
    }

    /**
     * @param tableName
     * @param whereCondition
     * @param whereArgs
     * @return
     */
    private int delete(final String tableName, final String whereCondition,
                       final String[] whereArgs) {
        return mSqLiteDatabase.delete(tableName, whereCondition, whereArgs);
    }

    public void update(final Object obj, final String whereCondition,
                       final String[] whereArgs) {
        update(getTableName(), buildColumns(obj), whereCondition, whereArgs);
    }

    protected int update(final String tableName, final ContentValues cv,
                         final String whereCondition, final String[] whereArgs) {
        return mSqLiteDatabase.update(tableName, cv, whereCondition, whereArgs);
    }


    public Object selectAll(final String orderBy) {
        final Cursor c = select(getTableName(), getColumns(), null, null, null,
                null, orderBy, null);

        return loadColumns(c);
    }

    protected int getCount() {
        String query = " select count(*) from " + getTableName();
        Cursor cursor = mSqLiteDatabase.rawQuery(query, null);
        int count = 0;
        if (null != cursor && cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = cursor.getCount();
            cursor.deactivate();
//            cursor.close();
        }
        return count;
    }


}
