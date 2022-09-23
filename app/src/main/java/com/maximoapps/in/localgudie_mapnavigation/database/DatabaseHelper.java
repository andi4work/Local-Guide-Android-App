package com.maximoapps.in.localgudie_mapnavigation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LocalGudie";
    public static final String FAVOURITE_TABLE_NAME = "FavouritePlaces";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "create table " + FAVOURITE_TABLE_NAME + "(id INTEGER PRIMARY KEY, name name)"
            );
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FAVOURITE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String place_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", place_name);
        db.replace(FAVOURITE_TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList getAllFavourite() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select (id ||' : '||name ||' : ') as name from " + FAVOURITE_TABLE_NAME, null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            if ((res != null) && (res.getCount() > 0))
                array_list.add(res.getString(res.getColumnIndex("name")));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean update(String name, String place_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + FAVOURITE_TABLE_NAME + " SET name = " + "'" + name);
        return true;
    }

    public boolean delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from " + FAVOURITE_TABLE_NAME);
        return true;
    }
}