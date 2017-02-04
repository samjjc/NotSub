package com.example.johnny.notsub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Johnny on 2017-02-04.
 */

public class UsageDbHelper extends SQLiteOpenHelper {

    private  static final String DATABASE_NAME = "packages.db";
    private static final int DATABASE_VERSION = 1;

    public UsageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + UsageContract.AppEntry.TABLE_NAME+"("
                +UsageContract.AppEntry.COLUMN_PACKAGE_NAME+" TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
