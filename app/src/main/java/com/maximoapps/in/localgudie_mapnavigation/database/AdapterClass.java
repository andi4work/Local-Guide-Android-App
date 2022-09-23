package com.maximoapps.in.localgudie_mapnavigation.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AdapterClass {
    private Context context;
    private SQLiteDatabase database;
    private CreateDB dbHelper;
    private static String DATABASE_TABLE="";

    public AdapterClass(Context pcontext,String pDBTableName) {
        // TODO Auto-generated constructor stub
        this.context=pcontext;
        this.DATABASE_TABLE=pDBTableName;

    }
    public AdapterClass Open()throws SQLException{
        dbHelper = new CreateDB(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        dbHelper.close();
    }

    public long insertquery(String[] pColumnNames,String[] pColumnValues){//calendar_update_status, String notification_time,String notification_before,String post_notification) {


        ContentValues initialValues = createContentValues(pColumnNames,pColumnValues);

        return database.insert(DATABASE_TABLE, null, initialValues);

    }


    public boolean updatequery(String args,String[] pColumnNames,String[] pColumnValues){//, String calendar_update_status, String notification_time,String notification_before,String post_notification) {
        ContentValues updateValues = createContentValues(pColumnNames,pColumnValues);//calendar_update_status,notification_time,notification_before,post_notification);

        return database.update(DATABASE_TABLE, updateValues, args, null) > 0;
    }
    public boolean deletequery(String args) {
        return database.delete(DATABASE_TABLE, args, null) > 0;
    }

    public Cursor fetchRecords(String[] pColumnNames,String args) {
        return database.query(DATABASE_TABLE, pColumnNames, args, null, null,
                null, null,null);
    }

    public Cursor RandomFetch(String[] pColumnNames,String args, String orderyBy) {
        return database.query(DATABASE_TABLE, pColumnNames, args, null, null,
                null, orderyBy,null);
    }

    public ArrayList<ArrayList<Object>> getAllRowsAsArrays(String query)
    {
        ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();

        Cursor cursor;

        try
        {
            // ask the database object to create the cursor.
            cursor=database.rawQuery(query, null);


            // move the cursor's pointer to position zero.
            cursor.moveToFirst();

            // if there is data after the current cursor position, add it
            // to the ArrayList.
            for(int index=0;index<cursor.getCount();index++){
                ArrayList<Object> dataList = new ArrayList<Object>();
                for(int index_j=0;index_j<cursor.getColumnCount();index_j++){
                    dataList.add(cursor.getString(index_j));

                }
                dataArrays.add(dataList);

                cursor.moveToNext();
            }

            cursor.close();
        }
        catch (SQLException e)
        {
//  Log.e("DB Error", e.toString());
            e.printStackTrace();
        }

        // return the ArrayList that holds the data collected from
        // the database.
        return dataArrays;
    }
    private ContentValues createContentValues(String[] pColumnNames,String[] pColumnValues) {
        ContentValues values = new ContentValues();
        for(int i=0;i<pColumnValues.length;i++){
            values.put(pColumnNames[i], pColumnValues[i]);
        }
        return values;
    }

    public Cursor fetchJoins(String joins) {

        return database.rawQuery(joins, null);

    }
}
