package metropolitan.com.student6.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "rezultati.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " +
                DatabaseContract.DatabaseEntry.TABLE_NAME + " (" +
                DatabaseContract.DatabaseEntry.COLUMN_INDEKS + " INTEGER PRIMARY KEY, " +
                DatabaseContract.DatabaseEntry.COLUMN_IME + " TEXT NOT NULL, " +
                DatabaseContract.DatabaseEntry.COLUMN_POENI + " INTEGER NOT NULL " +
                //DatabaseContract.DatabaseEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
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
