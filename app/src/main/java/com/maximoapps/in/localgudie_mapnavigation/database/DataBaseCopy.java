package com.maximoapps.in.localgudie_mapnavigation.database;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseCopy extends SQLiteOpenHelper {
	private static final String DB_PATH = null;

	private Context mycontext;

	private static String DATABASE = "Milaap";
	private static String DB_NAME = DATABASE+".sqlite";
	public SQLiteDatabase myDataBase;

	public DataBaseCopy(Context context) throws IOException {
		super(context, DB_NAME, null, 1);
		this.mycontext = context;
		boolean dbexist = checkdatabase();

//		Log.d("Database exists", dbexist+"--");
		if (dbexist) {

			//createdatabase();
		} else {
			createdatabase();
		}

	}

	public void createdatabase() throws IOException {

		this.getReadableDatabase();
		try {
			copydatabase(this.mycontext);
		} catch (IOException e) {
			throw new Error("Error copying database");
		}

	}

	private boolean checkdatabase() {
		// SQLiteDatabase checkdb = null;
		boolean checkdb = false;
		try {

			String myPath = "/data/data/"
					+ mycontext.getPackageName() + "/databases/"+DATABASE+".sqlite";



			File dbfile = new File(myPath);

			checkdb = dbfile.exists();

		} catch (SQLiteException e) {

		}

		return checkdb;
	}

	private void copydatabase(Context context) throws IOException {

		// Open your local db as the input stream
		InputStream myinput = mycontext.getAssets().open(DB_NAME);



		// Open the empty db as the output stream
		OutputStream myoutput = new FileOutputStream("/data/data/"
				+ context.getPackageName() + "/databases/"+DATABASE+".sqlite");

		// transfer byte to inputfile to outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myinput.read(buffer)) > 0) {
			myoutput.write(buffer, 0, length);
		}

		// Close the streams
		myoutput.flush();
		myoutput.close();
		myinput.close();



	}

	public void opendatabase() throws SQLException {
		// Open the database
		String mypath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(mypath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	public synchronized void close() {
		if (myDataBase != null) {
			myDataBase.close();
		}
		super.close();
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}



}
