package com.maximoapps.in.localgudie_mapnavigation.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CreateDB extends SQLiteOpenHelper{
	Context mContext;

	public CreateDB(Context context) {

		super(context,DATABASE_NAME,null,DATABASE_VERSION);

	}


	private static final String DATABASE_NAME = "Milaap.sqlite";
	private static final int DATABASE_VERSION = 1;

	@Override
	public void onCreate(SQLiteDatabase db) {

//		onCreate(db);



	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

//		db.execSQL("Drop Table if exists user_profile");
		onCreate(db);
	}
}
