package qfind.com.qfindappandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import qfind.com.qfindappandroid.historyPage.HistoryItem;
import qfind.com.qfindappandroid.historyPage.HistoryPageDataModel;
import qfind.com.qfindappandroid.historyPage.HistoryPageMainModel;

/**
 * Created by MoongedePC on 24-Jan-18.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "qfindManager";

    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_FAVORITE = "favorite";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMG = "thumbnail";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DAY = "day";

    SQLiteDatabase writeDB, readDB;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_HISTORY = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_DAY + " TEXT,"
                + KEY_IMG + " INTEGER," + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_HISTORY);

        String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE_FAVORITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_IMG + " TEXT,"
                + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addHistory(HistoryItem history) {
        writeDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        HistoryItem historyMain = new HistoryItem();
        values.put(KEY_DAY, historyMain.getDay());
        values.put(KEY_TITLE, history.getTitke());
        values.put(KEY_IMG, history.getImage());
        values.put(KEY_DESCRIPTION, history.getDescription());

        // Inserting Row
        writeDB.insert(TABLE_HISTORY, null, values);
        writeDB.close(); // Closing database connection
    }

    // Getting All Contacts
    public List<HistoryItem> getAllContacts() {
        List<HistoryItem> historyList = new ArrayList<HistoryItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;

        writeDB = this.getWritableDatabase();
        Cursor cursor = writeDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//                HistoryPageMainModel historyMain = new HistoryPageMainModel();
                HistoryItem history = new HistoryItem();
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setTitke(cursor.getString(1));
                history.setDay(cursor.getString(2));
                history.setImage(cursor.getString(3));
                history.setDescription(cursor.getString(4));
                // Adding contact to list
                historyList.add(history);
            } while (cursor.moveToNext());
        }

        // return list
        return historyList;
    }

//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }

}
