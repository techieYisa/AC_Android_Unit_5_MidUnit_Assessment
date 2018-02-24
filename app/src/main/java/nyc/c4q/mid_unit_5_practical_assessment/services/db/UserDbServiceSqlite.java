package nyc.c4q.mid_unit_5_practical_assessment.services.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.mid_unit_5_practical_assessment.model.User;

/**
 * Helper class to save/retrieve Users to/from a local database
 * <p>
 * Created by charlie on 1/19/18.
 */

public class UserDbServiceSqlite extends SQLiteOpenHelper implements UserDbService {

    private static final String TAG = "UserDbServiceSqlite";

    //----------------------------------------------------------------------------------------
    // Implement the Singleton pattern - SQLiteOpenHelper is a large class, so we don't want
    // to accidentally create multiple instances and fill up memory.
    //----------------------------------------------------------------------------------------
    private static UserDbServiceSqlite instance;

    public static UserDbServiceSqlite getInstance(Context context) {
        if (instance == null) {
            // Careful not to hold onto an Activity or Fragment context!
            instance = new UserDbServiceSqlite(context.getApplicationContext());
        }
        return instance;
    }

    private UserDbServiceSqlite(Context context) {
        super(context, UserDbContract.DB_NAME, null, UserDbContract.DB_VERSION);
    }


    //----------------------------------------------------------------------------------------
    // Implement the required abstract methods from SQLiteOpenHelper
    //----------------------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDbContract.UserTable.QUERY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserDbContract.UserTable.QUERY_DROP);
        onCreate(db);
    }


    //----------------------------------------------------------------------------------------
    // Implement the methods defined in the UserDbService interface
    //----------------------------------------------------------------------------------------
    @Override
    public boolean saveUser(User user) {
        SQLiteDatabase db = getWritableDatabase();

        // insert() will return -1 on failure, or a positive # on success
        long newRowId = db.insert(UserDbContract.UserTable.TABLE_NAME, null,
                getContentValues(user));

        db.close();
        return (newRowId > 0); // False if insert() failed, else True
    }

    @Override
    public boolean saveUsers(List<User> users) {
        SQLiteDatabase db = getWritableDatabase();

        boolean success = true;

        for (User user : users) {
            long newRowId = db.insert(UserDbContract.UserTable.TABLE_NAME, null,
                    getContentValues(user));
            success &= (newRowId > 0); // Overall success will turn false if any one insertion fails.
        }

        db.close();
        return success;
    }

    @Override
    public List<User> getUsers() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(UserDbContract.UserTable.TABLE_NAME,
                null, // null for columns is like doing `SELECT *`, i.e. select all columns)
                null, // selection (WHERE clause)
                null, // selectionArgs (value to plug into the WHERE clause)
                null, // groupBy clause
                null, // having clause
                null); // order by clause

        List<User> users = new ArrayList<>();

        if (cursor.moveToFirst()) { // will return false if cursor is empty
            while (!cursor.isAfterLast()) {
                users.add(getUserFromCurrentRowOfCursor(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        return users;
    }

    @Override
    public User getUserById(int id) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(UserDbContract.UserTable.TABLE_NAME,
                null, // null for columns is like doing `SELECT *`, i.e. select all columns)
                UserDbContract.UserTable._ID + " = ?", // selection (WHERE clause)
                new String[]{Integer.toString(id)}, // selectionArgs (value to plug into the WHERE clause)
                null, // groupBy clause
                null, // having clause
                null, // order by clause
                "1"); // limit results to a single row from the table

        User user = null;

        if (cursor.moveToFirst()) {
            user = getUserFromCurrentRowOfCursor(cursor);
        }

        cursor.close();
        db.close();
        return user;
    }

    @Override
    public void clearUsers() {
        Log.d(TAG, "clearUsers: clearing Users from local database");
        SQLiteDatabase db = getWritableDatabase();
        db.delete(UserDbContract.UserTable.TABLE_NAME, null, null);
        db.close();
    }


    //----------------------------------------------------------------------------------------
    // Helper methods to keep our code D.R.Y. and make the above methods more readable
    //----------------------------------------------------------------------------------------
    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDbContract.UserTable.COL_NAME_TITLE, user.getTitle());
        values.put(UserDbContract.UserTable.COL_NAME_FIRST, user.getFirstName());
        values.put(UserDbContract.UserTable.COL_NAME_LAST, user.getLastName());
        values.put(UserDbContract.UserTable.COL_NAME_STREET, user.getStreet());
        values.put(UserDbContract.UserTable.COL_NAME_CITY, user.getCity());
        values.put(UserDbContract.UserTable.COL_NAME_STATE, user.getState());
        values.put(UserDbContract.UserTable.COL_NAME_POSTCODE, user.getPostCode());
        values.put(UserDbContract.UserTable.COL_NAME_EMAIL, user.getEmail());
        values.put(UserDbContract.UserTable.COL_NAME_CELL, user.getCell());
        values.put(UserDbContract.UserTable.COL_NAME_DOB, user.getDob());
        values.put(UserDbContract.UserTable.COL_NAME_LG_IMG_URL, user.getLargeImageUrl());
        values.put(UserDbContract.UserTable.COL_NAME_MD_IMG_URL, user.getMediumImageUrl());
        values.put(UserDbContract.UserTable.COL_NAME_TN_IMG_URL, user.getThumbnailImageUrl());
        return values;
    }

    private User getUserFromCurrentRowOfCursor(Cursor cursor) {
        return new User.Builder(
                cursor.getInt(cursor.getColumnIndex(UserDbContract.UserTable._ID)), // use primary key value as ID
                cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_FIRST)),
                cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_LAST)))
                .setTitle(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_TITLE)))
                .setStreet(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_STREET)))
                .setCity(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_CITY)))
                .setState(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_STATE)))
                .setPostCode(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_POSTCODE)))
                .setEmail(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_EMAIL)))
                .setCell(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_CELL)))
                .setDob(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_DOB)))
                .setLargeImageUrl(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_LG_IMG_URL)))
                .setMediumImageUrl(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_MD_IMG_URL)))
                .setThumbnailImageUrl(cursor.getString(cursor.getColumnIndex(UserDbContract.UserTable.COL_NAME_TN_IMG_URL)))
                .build();
    }
}
