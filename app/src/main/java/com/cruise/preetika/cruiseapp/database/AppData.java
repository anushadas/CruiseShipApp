/**************************************************************************************
 * AppData class is responsible for the creation of the database tables in the app.****
 * This class is extended by SQLiteOpenHelper -- An inbuilt class which checks*********
 * whether the database is already created in the device or not. The first run*********
 * of the application on the device creates the database in it.************************
 **************************************************************************************/

package com.cruise.preetika.cruiseapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cruise.preetika.cruiseapp.utils.Constants;


/**
 * @author Preetika
 *
 */
public class AppData extends SQLiteOpenHelper {
	String[] catid, catname, testid, testname, cat, subcat, testtype;
	String[] save_cat = new String[]{"Generated Tests","Featured Tests","Test Packs"};
	String[] save_cat_ids = new String[]{"C1","C2","C3"};
	
	public AppData(Context context, String DatabaseName, String nullColumnHack, int databaseVersion) {
		super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
	}
	
	/*
	 ****************************************************************************************
	 * The onCreate function of this class is responsible for the check on the application***
	 * database and creates a new database only if it is not present. The function creates***
	 * two tables TEST_INFO and TEST_DETAILS. TEST_INFO contains all the data regarding test*
	 * specifications (XML ID, TEST ID, TEST NAME, TOTAL SECTIONS, TOTAL QUESTIONS)**********
	 * and result of the user (ATTEMPTED, CORRECT, SCORE, COMPLETION TIME).****************** 
	 ****************************************************************************************
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			String table1 = "CREATE TABLE IF NOT EXISTS " + Constants.USER_INFO + " ("
					+ Constants.USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ Constants.USERNAME +" TEXT, "
					+ Constants.PASSWORD + " TEXT, "
					+ Constants.NAME + " TEXT, "
					+ Constants.EMAIL + " TEXT, "
					+ Constants.PHONE + " TEXT); "
					+ " UNIQUE ("+Constants.USERNAME+")";


			
			String table2 = "CREATE TABLE IF NOT EXISTS " + Constants.ROOM + " ("
					+ Constants.ROOM_ID + " INTEGER PRIMARY KEY , "
					+ Constants.USERID + " INTEGER, "
					+ Constants.ROOMT_TYPE + " INTEGER, "
					+ Constants.DECK_NO + " TEXT, "
					+ Constants.PEOPLE_ACCOMPANYING + " INTEGER, "
					+ " FOREIGN KEY ("+Constants.USERID+") REFERENCES "+Constants.USER_INFO+"("+Constants.USERID+"));";

			
			String table3 = "CREATE TABLE IF NOT EXISTS " + Constants.TRANSACTION + " ("
					+ Constants.USERID + " INTEGER , "
					+ Constants.ROOM_ID + " INTEGER, "
					+ Constants.TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ Constants.SERVICE_NAME + " TEXT, "
					+ Constants.ACTIVITY_TYPE + " INTEGER, "
					+ Constants.DATE+ " TEXT, "
					+ Constants.TIME + " TEXT, "
					+ Constants.BOOKING_PERSON + " TEXT, "
					+ Constants.BOOKING_DATETIME + " TEXT, "
					+ Constants.CHARGES+ " REAL); ";
			

			
			String[] queries = new String[]{table1, table2, table3};
			for(int j=0; j<queries.length; j++){
				db.execSQL(queries[j]);
			}
		} catch (Exception e) {
			Log.v("create table exception", e.getMessage());
		}
	}
	

	/*
	 * **************************************************************************************
	 * The onUpgrade function of this class is responsible for the check on the application**
	 * database version and drop the old database tables only if the current version has been
	 * changed i.e. the parameters of the function oldVersion is not equal to new version and
	 * create new tables with the new schema.************************************************ 
	 * **************************************************************************************
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}


}