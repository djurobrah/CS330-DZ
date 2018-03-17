package metropolitan.com.student10.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "cs330-dz10.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " +
                DataContract.MessageEntry.TABLE_NAME + " (" +
                DataContract.MessageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.MessageEntry.COLUMN_USER + " TEXT NOT NULL, " +
                DataContract.MessageEntry.COLUMN_BODY + " TEXT NOT NULL, " +
                DataContract.MessageEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.MessageEntry.TABLE_NAME);
        onCreate(db);
    }
}
