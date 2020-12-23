package com.iquinteros.lodgefinder.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteConnection extends SQLiteOpenHelper {

    private static final String DB_NAME = "lodge_finder";
    private static final int VERSION = 1;

    public SqliteConnection(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE login(id integer, email text, password text, UNIQUE(email))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
