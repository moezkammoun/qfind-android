package qfind.com.qfindappandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import qfind.com.qfindappandroid.favoritePage.FavoriteModel;
import qfind.com.qfindappandroid.historyPage.HistoryDateCount;
import qfind.com.qfindappandroid.historyPage.HistoryItem;

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
    private static final String KEY_PAGE_ID = "page_id";

    SQLiteDatabase writeDB, readDB;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_HISTORY = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_DAY + " TEXT,"
                + KEY_IMG + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_PAGE_ID + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE_HISTORY);

        String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE_FAVORITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_IMG + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_PAGE_ID + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addHistory(HistoryItem history) {
        writeDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DAY, history.getDay());
        values.put(KEY_TITLE, history.getTitke());
        values.put(KEY_IMG, history.getImage());
        values.put(KEY_DESCRIPTION, history.getDescription());
        values.put(KEY_PAGE_ID, history.getPageId());

        // Inserting Row
        writeDB.insert(TABLE_HISTORY, null, values);
        writeDB.close(); // Closing database connection
    }

    public void addFavorite(FavoriteModel favorite) {
        writeDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, favorite.getItem());
        values.put(KEY_IMG, favorite.getUrl());
        values.put(KEY_DESCRIPTION, favorite.getItemDescription());
        values.put(KEY_PAGE_ID, favorite.getPageId());

        // Inserting Row
        writeDB.insert(TABLE_FAVORITE, null, values);
        writeDB.close(); // Closing database connection
    }

    public List<HistoryDateCount> getDateCount() {

        List<HistoryDateCount> historyDateCounts = new ArrayList<HistoryDateCount>();
        String selectQuery = "SELECT " + KEY_DAY + ", COUNT(*) FROM " + TABLE_HISTORY + " GROUP BY " + KEY_DAY + " order by " + KEY_DAY + " desc";
        writeDB = this.getWritableDatabase();
        Cursor cursor = writeDB.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HistoryDateCount history = new HistoryDateCount();
                history.setDay(cursor.getString(0));
                history.setCount(Integer.parseInt(cursor.getString(1)));
                historyDateCounts.add(history);
            } while (cursor.moveToNext());
        }
        return historyDateCounts;
    }

    public List<HistoryItem> getAllHistory(String day) {
        List<HistoryItem> historyList = new ArrayList<HistoryItem>();
        String selectQuery = "select * from " + TABLE_HISTORY + " where " + KEY_DAY + "='" + day + "'";
        Cursor cursor = writeDB.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HistoryItem history = new HistoryItem();
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setTitke(cursor.getString(1));
                history.setDay(cursor.getString(2));
                history.setImage(cursor.getString(3));
                history.setDescription(cursor.getString(4));
                history.setPageId(cursor.getInt(5));
                historyList.add(history);
            } while (cursor.moveToNext());
        }
        // return list
        return historyList;
    }


    public List<FavoriteModel> getAllFavorites() {
        List<FavoriteModel> favoriteList = new ArrayList<FavoriteModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE;

        writeDB = this.getWritableDatabase();
        Cursor cursor = writeDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FavoriteModel favModel = new FavoriteModel();
                favModel.setId(Integer.parseInt(cursor.getString(0)));
                favModel.setItem(cursor.getString(1));
                favModel.setUrl(cursor.getString(2));
                favModel.setItemDescription(cursor.getString(3));
                favModel.setPageId(cursor.getInt(4));
                favoriteList.add(favModel);
            } while (cursor.moveToNext());
        }
        return favoriteList;
    }

    public boolean checkFavoriteById(int id) {

        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE + " WHERE " + KEY_PAGE_ID + " = " + id;

        writeDB = this.getWritableDatabase();
        Cursor cursor = writeDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkHistoryById(int id) {
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY + " WHERE " + KEY_PAGE_ID + " = " + id;

        writeDB = this.getWritableDatabase();
        Cursor cursor = writeDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public void updateFavorite(FavoriteModel favorite, int id) {
        writeDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, favorite.getItem());
        values.put(KEY_IMG, favorite.getUrl());
        values.put(KEY_DESCRIPTION, favorite.getItemDescription());
        values.put(KEY_PAGE_ID, favorite.getPageId());
        writeDB.update(TABLE_FAVORITE, values, KEY_PAGE_ID + " = " + id, null);
        writeDB.close();
    }

    public void updateHistory(HistoryItem history, int id) {
        writeDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DAY, history.getDay());
        values.put(KEY_TITLE, history.getTitke());
        values.put(KEY_IMG, history.getImage());
        values.put(KEY_DESCRIPTION, history.getDescription());
        values.put(KEY_PAGE_ID, history.getPageId());
        writeDB.update(TABLE_HISTORY, values, KEY_PAGE_ID + " = " + id, null);
        writeDB.close();
    }

    public void deleteFavorite(int id) {
         writeDB = this.getWritableDatabase();
        writeDB.delete(TABLE_FAVORITE, KEY_PAGE_ID + " = " + id, null);
        writeDB.close();
    }

    public void deleteHistory(String lastdate) {
        writeDB = this.getWritableDatabase();
        writeDB.delete(TABLE_HISTORY, KEY_DAY + " < " + lastdate, null);
        writeDB.close();
    }
}
