package nyc.c4q.mid_unit_5_practical_assessment.services.db;

import android.provider.BaseColumns;

/**
 * Provides constants representing the User database contract.
 * Created by charlie on 1/19/18.
 */

final class UserDbContract {

    // Constants for the whole database
    static final String DB_NAME = "UsersDb";
    static final int DB_VERSION = 1;

    // Prevent instantiation
    private UserDbContract() {}

    // Constants for the Users table
    static final class UserTable implements BaseColumns {
        static final String TABLE_NAME = "users";
        static final String COL_NAME_TITLE = "title";
        static final String COL_NAME_FIRST = "first_name";
        static final String COL_NAME_LAST = "last_name";
        static final String COL_NAME_STREET = "street";
        static final String COL_NAME_CITY = "city";
        static final String COL_NAME_STATE = "state";
        static final String COL_NAME_POSTCODE = "postcode";
        static final String COL_NAME_EMAIL = "email";
        static final String COL_NAME_CELL = "cell";
        static final String COL_NAME_DOB = "dob";
        static final String COL_NAME_LG_IMG_URL = "large_image_url";
        static final String COL_NAME_MD_IMG_URL = "medium_image_url";
        static final String COL_NAME_TN_IMG_URL = "thumbnail_image_url";

        static final String QUERY_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COL_NAME_TITLE + " TEXT, " +
                COL_NAME_FIRST + " TEXT, " +
                COL_NAME_LAST + " TEXT, " +
                COL_NAME_STREET + " TEXT, " +
                COL_NAME_CITY + " TEXT, " +
                COL_NAME_STATE + " TEXT, " +
                COL_NAME_POSTCODE + " TEXT, " +
                COL_NAME_EMAIL + " TEXT, " +
                COL_NAME_CELL + " TEXT, " +
                COL_NAME_DOB + " TEXT, " +
                COL_NAME_LG_IMG_URL + " TEXT, " +
                COL_NAME_MD_IMG_URL + " TEXT, " +
                COL_NAME_TN_IMG_URL + " TEXT)";

        static final String QUERY_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
