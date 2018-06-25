package com.example.djuricadjuricic.cs330_juna.data;

import android.provider.BaseColumns;

public class DatabaseContract
{
    public static final class DatabaseEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "student";
        public static final String COLUMN_IME = "ime";
        public static final String COLUMN_PREZIME = "prezime";
        public static final String COLUMN_INDEKS = "indeks";
    }
}

