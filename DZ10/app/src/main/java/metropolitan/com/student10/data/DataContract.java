package metropolitan.com.student10.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract
{
    //put do glavnog contenta
    public static final String AUTHORITY = "metropolitan.com.student10";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MESSAGE = "message";

    public static final class MessageEntry implements BaseColumns
    {
        //put do contenta tabele
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MESSAGE).build();

        public static final String TABLE_NAME = "message";

        public static final String COLUMN_USER = "user";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
