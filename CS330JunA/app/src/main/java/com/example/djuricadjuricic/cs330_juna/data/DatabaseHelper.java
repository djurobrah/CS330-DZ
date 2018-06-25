package com.example.djuricadjuricic.cs330_juna.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "fit.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " +
                DatabaseContract.DatabaseEntry.TABLE_NAME + " (" +
                DatabaseContract.DatabaseEntry.COLUMN_IME + " TEXT NOT NULL, " +
                DatabaseContract.DatabaseEntry.COLUMN_PREZIME + " TEXT NOT NULL, " +
                DatabaseContract.DatabaseEntry.COLUMN_INDEKS + " INTEGER PRIMARY KEY" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.DatabaseEntry.TABLE_NAME);
        onCreate(db);
    }
}
