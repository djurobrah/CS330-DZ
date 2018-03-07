package metropolitan.com.student6.data;

import android.provider.BaseColumns;

public class DatabaseContract
{
    public static final class DatabaseEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "rezultati";
        public static final String COLUMN_INDEKS = "indeks";
        public static final String COLUMN_IME = "ime";
        public static final String COLUMN_POENI = "poeni";
        //public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}

