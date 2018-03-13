package metropolitan.com.student7.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "konsultacije.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " +
                DataContract.PredmetiEntry.TABLE_NAME + " (" +
                DataContract.PredmetiEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.PredmetiEntry.COLUMN_SIFRA + " INTEGER, " +
                DataContract.PredmetiEntry.COLUMN_NAZIV + " TEXT NOT NULL, " +
                DataContract.PredmetiEntry.COLUMN_POENI + " INTEGER NOT NULL " +
                //DatabaseContract.DatabaseEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.PredmetiEntry.TABLE_NAME);
        onCreate(db);
    }
}
