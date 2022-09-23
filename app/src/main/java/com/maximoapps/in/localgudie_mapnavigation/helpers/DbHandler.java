package com.maximoapps.in.localgudie_mapnavigation.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.maximoapps.in.localgudie_mapnavigation.ui.activity.PlaceDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CM on 06-01-2020.
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "placesDB";
    private static final String TABLE_Users = "placeDetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NAMEE = "namee";
    private static final String KEY_LOC = "location";
    private static final String KEY_DESG = "designation";
    //IMAGE DETAILS
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_RATING = "rating";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_PLACE_ID = "place_id";

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_IMAGE + " TEXT," + KEY_RATING + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_DISTANCE + " TEXT," + KEY_CONTACT
                + " TEXT," + KEY_LOCATION + " TEXT," + KEY_PLACE_ID + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new Place Details
    public void insertPlaceDetails(String name, String image, String rating, String address, String distance, String contact, String location, String place_id) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_IMAGE, image);
        cValues.put(KEY_RATING, rating);
        cValues.put(KEY_ADDRESS, address);
        cValues.put(KEY_DISTANCE, distance);
        cValues.put(KEY_CONTACT, contact);
        cValues.put(KEY_LOCATION, location);
        cValues.put(KEY_LOC, location);
        cValues.put(KEY_PLACE_ID, place_id);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users, null, cValues);
        db.close();
    }

    // Get User Details
    public ArrayList<HashMap<String, String>> GetPlaceDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, image, rating, address, distance, contact, location, place_id FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("image", cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
            user.put("rating", cursor.getString(cursor.getColumnIndex(KEY_RATING)));
            user.put("address", cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
            user.put("distance", cursor.getString(cursor.getColumnIndex(KEY_DISTANCE)));
            user.put("contact", cursor.getString(cursor.getColumnIndex(KEY_CONTACT)));
            user.put("location", cursor.getString(cursor.getColumnIndex(KEY_LOCATION)));
            user.put("place_id", cursor.getString(cursor.getColumnIndex(KEY_PLACE_ID)));
            userList.add(user);
        }
        return userList;
    }

    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM " + TABLE_Users;
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_LOC, KEY_DESG}, KEY_ID + "=?", new String[]{String.valueOf(userid)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("designation", cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("location", cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            userList.add(user);
        }
        return userList;
    }

    // Delete User Details
    public void DeleteFavouritePlace(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_PLACE_ID + " = ?", new String[]{userid});
        db.close();
    }

    // Update User Details
    public int UpdatePlaceDetails(String location, String designation, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_LOC, location);
        cVals.put(KEY_DESG, designation);
        int count = db.update(TABLE_Users, cVals, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        return count;
    }


    //CHECK IF ITEM IS ALREADY IN FAVORITE LIST OR NOT
    public boolean isFavouritePlaceOrNot(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_Users + " WHERE " + KEY_PLACE_ID + " =?";
        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[]{id});
        boolean hasObject = false;
        if (cursor.moveToFirst()) {
            hasObject = true;
            //region if you had multiple records to check for, use this region.
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            //here, count is records found
            Log.d("TAG", String.format("%d records found", count));
            //endregion
        }
        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }
}