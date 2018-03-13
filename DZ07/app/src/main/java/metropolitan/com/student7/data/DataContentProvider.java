package metropolitan.com.student7.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DataContentProvider extends ContentProvider
{
    public static final int PREDMETI = 100;
    public static final int PREDMET_SA_ID = 101;

    private DataHelper dataHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DataContract.AUTHORITY, DataContract.PATH_PREDMETI, PREDMETI);
        uriMatcher.addURI(DataContract.AUTHORITY, DataContract.PATH_PREDMETI + "/#", PREDMET_SA_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate()
    {
        Context context = getContext();
        dataHelper = new DataHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        final SQLiteDatabase db = dataHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor c;

        switch (match)
        {
            case PREDMETI:
            {
                c =  db.query(DataContract.PredmetiEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
            {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        int match = sUriMatcher.match(uri);

        switch (match)
        {
            case PREDMETI:
            {
                return "vnd.android.cursor.dir" + "/" + DataContract.AUTHORITY + "/" + DataContract.PATH_PREDMETI;
            }
            case PREDMET_SA_ID:
            {
                return "vnd.android.cursor.item" + "/" + DataContract.AUTHORITY + "/" + DataContract.PATH_PREDMETI;
            }
            default:
            {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        final SQLiteDatabase db = dataHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match)
        {
            case PREDMETI:
            {
                long id = db.insert(DataContract.PredmetiEntry.TABLE_NAME, null, values);
                if ( id > 0 )
                {
                    returnUri = ContentUris.withAppendedId(DataContract.PredmetiEntry.CONTENT_URI, id);
                }
                else
                {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
            {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        final SQLiteDatabase db = dataHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        int tasksDeleted;

        switch (match)
        {
            case PREDMETI:
            {
                tasksDeleted = db.delete(DataContract.PredmetiEntry.TABLE_NAME, null, null);
                break;
            }
            //!!!!!!!
            case PREDMET_SA_ID:
            {
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(DataContract.PredmetiEntry.TABLE_NAME, "_id=?", new String[]{id});
                break;
            }
            default:
            {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (tasksDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        int tasksUpdated;

        int match = sUriMatcher.match(uri);

        switch (match)
        {
            case PREDMET_SA_ID:
            {
                String id = uri.getPathSegments().get(1);
                tasksUpdated = dataHelper.getWritableDatabase().update(DataContract.PredmetiEntry.TABLE_NAME, values, "_id=?", new String[]{id});
                break;
            }
            default:
            {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (tasksUpdated != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksUpdated;
    }
}
