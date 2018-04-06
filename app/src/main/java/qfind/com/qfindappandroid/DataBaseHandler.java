package qfind.com.qfindappandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private final String KEY_ID = "id";
    private String KEY_TITLE = "title";
    private String KEY_IMG = "thumbnail";
    private String KEY_DESCRIPTION = "description";
    private String KEY_TITLE_ARABIC = "title_arabic";
    private String KEY_DESCRIPTION_ARABIC = "description_arabic";
    private String KEY_DAY = "day";
    private String KEY_TIME = "time";
    private String KEY_PAGE_ID = "page_id";
    private String KEY_DAYTIME = "provider_date_time";


    SQLiteDatabase writeDB, readDB;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_HISTORY = "CREATE TABLE IF NOT EXISTS " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_DAY + " DATE,"
                + KEY_DAYTIME + " DATETIME," + KEY_TIME + " TIME," + KEY_IMG + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_TITLE_ARABIC + " TEXT," + KEY_DESCRIPTION_ARABIC + " TEXT,"
                + KEY_PAGE_ID + " INTEGER " + ")";

        db.execSQL(CREATE_TABLE_HISTORY);

        String CREATE_TABLE_FAVORITE = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_DAYTIME + " DATETIME," + KEY_IMG + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_TITLE_ARABIC + " TEXT,"
                + KEY_DESCRIPTION_ARABIC + " TEXT,"
                + KEY_PAGE_ID + " INTEGER " + ")";

        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addHistory(HistoryItem history) {
        writeDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DAY, history.getDay());
        values.put(KEY_TIME, history.getTime());
        values.put(KEY_TITLE, history.getTitke());
        values.put(KEY_IMG, history.getImage());
        values.put(KEY_DESCRIPTION, history.getDescription());
        values.put(KEY_PAGE_ID, history.getPageId());
        values.put(KEY_DAYTIME, history.getDayTime());
        values.put(KEY_TITLE_ARABIC, history.getTitleArabic());
        values.put(KEY_DESCRIPTION_ARABIC, history.getDescriptionArabic());
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
        values.put(KEY_DAYTIME, favorite.getDatetime());
        values.put(KEY_TITLE_ARABIC, favorite.getItemArabic());
        values.put(KEY_DESCRIPTION_ARABIC, favorite.getItemDescriptionArabic());
        // Inserting Row
        writeDB.insert(TABLE_FAVORITE, null, values);
        writeDB.close(); // Closing database connection
    }

    public List<HistoryDateCount> getDateCount() {

        List<HistoryDateCount> historyDateCounts = new ArrayList<HistoryDateCount>();
        String selectQuery = "SELECT " + KEY_DAY + ", COUNT(*) FROM "
                + TABLE_HISTORY + " GROUP BY " + KEY_DAY + " order by " + KEY_DAYTIME + " desc";
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
        String selectQuery = "select * from " + TABLE_HISTORY + " where " + KEY_DAY + "= '" + day + "' "
                + " order by " + KEY_DAYTIME + " DESC ";
        Cursor cursor = writeDB.rawQuery(selectQuery, null);
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HistoryItem history = new HistoryItem();
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setTitke(cursor.getString(1));
                history.setDay(cursor.getString(2));
                history.setDayTime(cursor.getString(3));
                history.setTime(cursor.getString(4));
                history.setImage(cursor.getString(5));
                history.setDescription(cursor.getString(6));
                history.setTitleArabic(cursor.getString(7));
                history.setDescriptionArabic(cursor.getString(8));
                history.setPageId(cursor.getInt(9));
                historyList.add(history);
            } while (cursor.moveToNext());
        }
        // return list
        return historyList;
    }


    public List<FavoriteModel> getAllFavorites() {
        List<FavoriteModel> favoriteList = new ArrayList<FavoriteModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE + " order by " + KEY_DAYTIME + " DESC ";
        ;

        writeDB = this.getWritableDatabase();
        Cursor cursor = writeDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FavoriteModel favModel = new FavoriteModel();
                favModel.setId(Integer.parseInt(cursor.getString(0)));
                favModel.setItem(cursor.getString(1));
                favModel.setDatetime(cursor.getString(2));
                favModel.setUrl(cursor.getString(3));
                favModel.setItemDescription(cursor.getString(4));
                favModel.setItemArabic(cursor.getString(5));
                favModel.setItemDescriptionArabic(cursor.getString(6));
                favModel.setPageId(cursor.getInt(7));
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

    public Cursor checkHistoryByDay(int id) {
        ArrayList<String> dayslist = new ArrayList<String>();
        writeDB = this.getWritableDatabase();
        String selectQuery = "SELECT " + KEY_DAYTIME
                + " FROM " + TABLE_HISTORY
                + " WHERE " + KEY_PAGE_ID + " = " + id
                + " ORDER BY " + KEY_DAYTIME + " DESC";

        Cursor cursor = writeDB.rawQuery(selectQuery, null);
        boolean value = cursor.moveToFirst();
        if (value) {
            do {
                dayslist.add(cursor.getString(0));
            } while (cursor.moveToNext());

        }
        return cursor;
    }


    public void updateHistory(HistoryItem history, int id, String day) {
        writeDB = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_HISTORY + " SET "
                + KEY_DAY + " = '" + history.getDay() + "' , "
                + KEY_DAYTIME + " = '" + history.getDayTime() + "' , "
                + KEY_TIME + " = '" + history.getTime() + "' "
                + " WHERE " + KEY_PAGE_ID + " = " + id + " AND " + KEY_DAY + " = '" + day + "'";

        Cursor cursor = writeDB.rawQuery(updateQuery, null);
        if (cursor.moveToFirst()) {
            System.out.println(" updated.........");
        }
        cursor.close();
        writeDB.close();
    }


    public void updateFavorite(int id, String day) {
        writeDB = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_FAVORITE + " SET "
                + KEY_DAYTIME + " = '" + day + "' "
                + " WHERE " + KEY_PAGE_ID + " = " + id;

        Cursor cursor = writeDB.rawQuery(updateQuery, null);
        if (cursor.moveToFirst()) {
            System.out.println(" updated.........");
        }
        cursor.close();
        writeDB.close();
    }

    public void deleteFavorite(int id) {
        writeDB = this.getWritableDatabase();
        writeDB.delete(TABLE_FAVORITE, KEY_PAGE_ID + " = " + id, null);
        writeDB.close();
    }

    public void deleteHistory(String lastdate) {
        writeDB = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_HISTORY + " WHERE " + KEY_DAY + "< '" + lastdate + "' ";
        writeDB.rawQuery(deleteQuery, null);
        writeDB.close();
    }

    public void deleteHistoryAfter(String today) {
        writeDB = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_HISTORY + " WHERE " + KEY_DAY + "> '" + today + "' ";
        writeDB.rawQuery(deleteQuery, null);
        writeDB.close();
    }

}
