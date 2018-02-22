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
    private String KEY_TITLE = "title";
    private String KEY_IMG = "thumbnail";
    private String KEY_DESCRIPTION = "description";
    private String KEY_TITLE_ARABIC = "title_arabic";
    private String KEY_DESCRIPTION_ARABIC = "description_arabic";
    private String KEY_DAY = "day";
    private String KEY_TIME = "time";
    private String KEY_PAGE_ID = "page_id";
    private String KEY_LOCATION = "provider_location";
    private String KEY_MOBILE = "provider_mobile";
    private String KEY_WEBSITE = "provider_website";
    private String KEY_ADDRESS = "provider_address";
    private String KEY_OPENING_TIME = "provider_opening_time";
    private String KEY_MAIL = "provider_mail";
    private String KEY_FACEBOOK = "provider_facebook";
    private String KEY_LINKEDIN = "provider_linkedin";
    private String KEY_INSTAGRAM = "provider_instagram";
    private String KEY_TWITTER = "provider_twitter";
    private String KEY_SNAPCHAT = "provider_snapchat";
    private String KEY_GOOGLE_PLUS = "provider_google_plus";
    private String KEY_MAP = "provider_map_location";


    SQLiteDatabase writeDB, readDB;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_HISTORY = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_DAY + " DATE,"
                + KEY_TIME + " TIME," + KEY_IMG + " TEXT," + KEY_DESCRIPTION + " TEXT,"
                + KEY_PAGE_ID + " INTEGER" + KEY_LOCATION + " TEXT," + KEY_MOBILE + " TEXT,"
                + KEY_WEBSITE + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_OPENING_TIME + " TEXT,"
                + KEY_MAIL + " TEXT," + KEY_FACEBOOK + " TEXT," + KEY_LINKEDIN + " TEXT,"
                + KEY_INSTAGRAM + " TEXT," + KEY_TWITTER + " TEXT," + KEY_SNAPCHAT + " TEXT,"
                + KEY_GOOGLE_PLUS + " TEXT," + KEY_MAP + " TEXT,"
                + KEY_TITLE_ARABIC + " TEXT," + KEY_DESCRIPTION_ARABIC + " TEXT " + ")";

        db.execSQL(CREATE_TABLE_HISTORY);

        String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE_FAVORITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_IMG + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_PAGE_ID + " INTEGER"
                + KEY_LOCATION + " TEXT," + KEY_MOBILE + " TEXT," + KEY_WEBSITE + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_OPENING_TIME + " TEXT," + KEY_MAIL + " TEXT,"
                + KEY_FACEBOOK + " TEXT," + KEY_LINKEDIN + " TEXT," + KEY_INSTAGRAM + " TEXT,"
                + KEY_TWITTER + " TEXT," + KEY_SNAPCHAT + " TEXT," + KEY_GOOGLE_PLUS + " TEXT,"
                + KEY_MAP + " TEXT," + KEY_TITLE_ARABIC + " TEXT,"
                + KEY_DESCRIPTION_ARABIC + " TEXT " + ")";
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
        values.put(KEY_MOBILE, history.getProviderPhone());
        values.put(KEY_WEBSITE, history.getProviderWebsite());
        values.put(KEY_ADDRESS, history.getProviderAddress());
        values.put(KEY_OPENING_TIME, history.getProviderOpeningTime());
        values.put(KEY_MAIL, history.getProviderMail());
        values.put(KEY_FACEBOOK, history.getProviderFacebook());
        values.put(KEY_LINKEDIN, history.getProviderLinkedIn());
        values.put(KEY_INSTAGRAM, history.getProviderInstagram());
        values.put(KEY_TWITTER, history.getProviderTwitter());
        values.put(KEY_SNAPCHAT, history.getProviderSnapchat());
        values.put(KEY_GOOGLE_PLUS, history.getProviderGooglePlus());
        values.put(KEY_MAP, history.getProviderLatlong());
        values.put(KEY_IMG, history.getProviderThumbnail());
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
        values.put(KEY_MOBILE, favorite.getProviderPhone());
        values.put(KEY_WEBSITE, favorite.getProviderWebsite());
        values.put(KEY_ADDRESS, favorite.getProviderAddress());
        values.put(KEY_OPENING_TIME, favorite.getProviderOpeningTime());
        values.put(KEY_MAIL, favorite.getProviderMail());
        values.put(KEY_FACEBOOK, favorite.getProviderFacebook());
        values.put(KEY_LINKEDIN, favorite.getProviderLinkedIn());
        values.put(KEY_INSTAGRAM, favorite.getProviderInstagram());
        values.put(KEY_TWITTER, favorite.getProviderTwitter());
        values.put(KEY_SNAPCHAT, favorite.getProviderSnapchat());
        values.put(KEY_GOOGLE_PLUS, favorite.getProviderGooglePlus());
        values.put(KEY_MAP, favorite.getProviderLatlong());
        values.put(KEY_TITLE_ARABIC, favorite.getItemArabic());
        values.put(KEY_DESCRIPTION_ARABIC, favorite.getItemDescriptionArabic());

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
        String selectQuery = "select * from " + TABLE_HISTORY + " where " + KEY_DAY + "='" + day + "'"
                + " order by " + KEY_DAY;
        Cursor cursor = writeDB.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HistoryItem history = new HistoryItem();
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setTitke(cursor.getString(1));
                history.setDay(cursor.getString(2));
                history.setTime(cursor.getString(3));
                history.setImage(cursor.getString(4));
                history.setDescription(cursor.getString(5));
                history.setPageId(cursor.getInt(6));
                history.setProviderPhone(cursor.getString(7));
                history.setProviderWebsite(cursor.getString(8));
                history.setProviderAddress(cursor.getString(9));
                history.setProviderOpeningTime(cursor.getString(10));
                history.setProviderMail(cursor.getString(11));
                history.setProviderFacebook(cursor.getString(12));
                history.setProviderLinkedIn(cursor.getString(13));
                history.setProviderInstagram(cursor.getString(14));
                history.setProviderTwitter(cursor.getString(15));
                history.setProviderSnapchat(cursor.getString(16));
                history.setProviderGooglePlus(cursor.getString(17));
                history.setProviderLatlong(cursor.getString(18));
                history.setTitleArabic(cursor.getString(19));
                history.setDescriptionArabic(cursor.getString(20));
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
                favModel.setProviderPhone(cursor.getString(5));
                favModel.setProviderWebsite(cursor.getString(6));
                favModel.setProviderAddress(cursor.getString(7));
                favModel.setProviderOpeningTime(cursor.getString(8));
                favModel.setProviderMail(cursor.getString(9));
                favModel.setProviderFacebook(cursor.getString(10));
                favModel.setProviderLinkedIn(cursor.getString(11));
                favModel.setProviderInstagram(cursor.getString(12));
                favModel.setProviderTwitter(cursor.getString(13));
                favModel.setProviderSnapchat(cursor.getString(14));
                favModel.setProviderGooglePlus(cursor.getString(15));
                favModel.setProviderLatlong(cursor.getString(16));
                favModel.setItemArabic(cursor.getString(17));
                favModel.setItemDescriptionArabic(cursor.getString(18));
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


    public String checkHistoryById(int id) {
        String days = "";
        writeDB = this.getWritableDatabase();
        String selectQuery = "SELECT " + KEY_DAY
                + " FROM " + TABLE_HISTORY
                + " WHERE " + KEY_PAGE_ID + " = " + id;
//                + " AND " + KEY_DAY + " = '" + today + " '";
        Cursor cursor = writeDB.rawQuery(selectQuery, null);
        boolean value = cursor.moveToFirst();
        if (value) {
//            days.add(0,cursor.getString(0));
            days = cursor.getString(0);
        }
        return days;
    }

    public Cursor checkHistoryByDay(int id) {
//        int id=0;
        ArrayList<String> dayslist = new ArrayList<String>();
        writeDB = this.getWritableDatabase();
        String selectQuery = "SELECT " + KEY_DAY
                + " FROM " + TABLE_HISTORY
                + " WHERE " + KEY_PAGE_ID + " = " + id
                + " ORDER BY " + KEY_DAY + " DESC";
//        + KEY_DAY + " = '" + today + " '"

        Cursor cursor = writeDB.rawQuery(selectQuery, null);
        boolean value = cursor.moveToFirst();
        if (value) {
            do {
                dayslist.add(cursor.getString(0));
            } while (cursor.moveToNext());

        }
        return cursor;
    }


    public void updateHistory(HistoryItem history, int id, String today) {
        writeDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DAY, history.getDay());
        values.put(KEY_TIME, history.getTime());
        values.put(KEY_TITLE, history.getTitke());
        values.put(KEY_IMG, history.getImage());
        values.put(KEY_DESCRIPTION, history.getDescription());
        values.put(KEY_PAGE_ID, history.getPageId());
        values.put(KEY_MOBILE, history.getProviderPhone());
        values.put(KEY_WEBSITE, history.getProviderWebsite());
        values.put(KEY_ADDRESS, history.getProviderAddress());
        values.put(KEY_OPENING_TIME, history.getProviderOpeningTime());
        values.put(KEY_MAIL, history.getProviderMail());
        values.put(KEY_FACEBOOK, history.getProviderFacebook());
        values.put(KEY_LINKEDIN, history.getProviderLinkedIn());
        values.put(KEY_INSTAGRAM, history.getProviderInstagram());
        values.put(KEY_TWITTER, history.getProviderTwitter());
        values.put(KEY_SNAPCHAT, history.getProviderSnapchat());
        values.put(KEY_GOOGLE_PLUS, history.getProviderGooglePlus());
        values.put(KEY_MAP, history.getProviderLatlong());
        values.put(KEY_IMG, history.getProviderThumbnail());
        values.put(KEY_TITLE_ARABIC, history.getTitleArabic());
        values.put(KEY_DESCRIPTION_ARABIC, history.getDescriptionArabic());
        String whereClause = KEY_PAGE_ID + " = " + id + " and " + KEY_DAY + " = '" + today + " '";

        writeDB.update(TABLE_HISTORY, values, whereClause, null);
        writeDB.close();
    }

    public void deleteFavorite(int id) {
        writeDB = this.getWritableDatabase();
        writeDB.delete(TABLE_FAVORITE, KEY_PAGE_ID + " = " + id, null);
        writeDB.close();
    }

    public void deleteHistory(String lastdate) {
        writeDB = this.getWritableDatabase();
        writeDB.delete(TABLE_HISTORY, KEY_DAY + " < " + "' " + lastdate + " '", null);
        writeDB.close();
    }

    public List<HistoryItem> getHistoryById(int id) {

        List<HistoryItem> historyItems = new ArrayList<HistoryItem>();
        String selectQuery = "select * from " + TABLE_HISTORY + " where " + KEY_PAGE_ID + "='" + id + "'";
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
                history.setProviderPhone(cursor.getString(6));
                history.setProviderWebsite(cursor.getString(7));
                history.setProviderAddress(cursor.getString(8));
                history.setProviderOpeningTime(cursor.getString(9));
                history.setProviderMail(cursor.getString(10));
                history.setProviderFacebook(cursor.getString(11));
                history.setProviderLinkedIn(cursor.getString(12));
                history.setProviderInstagram(cursor.getString(13));
                history.setProviderTwitter(cursor.getString(14));
                history.setProviderSnapchat(cursor.getString(15));
                history.setProviderGooglePlus(cursor.getString(16));
                history.setProviderLatlong(cursor.getString(17));
                historyItems.add(history);
            } while (cursor.moveToNext());
        }
        // return list
        return historyItems;
    }
}
