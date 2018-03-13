package metropolitan.com.student7.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract
{
    //put do glavnog contenta
    public static final String AUTHORITY = "metropolitan.com.student7";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_PREDMETI = "predmeti";

    //entry tabele "predmeti"
    public static final class PredmetiEntry implements BaseColumns
    {
        //put do contenta tabele
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PREDMETI).build();

        public static final String TABLE_NAME = "predmeti";

        public static final String COLUMN_SIFRA = "sifra";
        public static final String COLUMN_NAZIV = "naziv";
        public static final String COLUMN_POENI = "poeni";
    }
}
