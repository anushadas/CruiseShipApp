/**************************************************************************************
 * DBHelper class is a helper class to our package which unables us to deal with the***
 * application database and link SQLiteDatabase to other classes of the package.*******
 * DBHelper contains all the query execution methods and database related methods******
 * i.e. like db open close perspectives.**************************************************
 **************************************************************************************/
package com.cruise.preetika.cruiseapp.database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.cruise.preetika.cruiseapp.handlers.InvoiceHandler;
import com.cruise.preetika.cruiseapp.utils.Constants;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;


/**
 * @author Preetika
 *
 */
public class DBHelper {

	private SQLiteDatabase db;
	private final Context context;
	private final AppData dbHelper;
	public static int no;
	public static DBHelper db_helper = null;
	
	public static DBHelper getInstance(Context context){
		try{
			if(db_helper == null){
				db_helper = new DBHelper(context);
				db_helper.open();
			}
		}catch(IllegalStateException e){
			//db_helper already open
		}
		return db_helper;
	}

	/*
	 * set context of the class and initialize AppData Object
	 */

	public DBHelper(Context c) {
		context = c;
		dbHelper = new AppData(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
	}

	/*
	 * close databse.
	 */
	public void close() {
		if (db.isOpen()) {
			db.close();
		}
	}

	/*
	 * open database
	 */
	public void open() throws SQLiteException {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (Exception e) {
			Log.v("open database Exception", "error=="+e.getMessage());
			db = dbHelper.getReadableDatabase();
		}
	}
	public boolean CheckIsDataAlreadyInDBorNot(String TableName,
											   String where) {

		//String Query = "Select * from " + TableName + " where " + dbfield + "= '" + fieldValue+"'";

		Cursor cursor = db.query(TableName,null,where,null,null,null,null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;

	}
	public void deleteRecords(String table, String whereClause, String[] whereArgs){
		db.delete(table, whereClause, whereArgs);
	}

	public void deleteTable(String tablename, String whereClause, String[] whereArgs){
		try{
			db.delete(tablename, whereClause, whereArgs);
		}catch(Exception e){
			Log.v("Delete table exception",e.toString());
		}
	}
	
	/*
	 * Get count of all tables in a table as per the condition
	 */

	public int getFullCount(String table, String where) {
		Cursor cursor = db.query(false, table, null, where, null, null, null, null, null);
		try {
			if (cursor != null) {
				cursor.moveToFirst();
				no = cursor.getCount();
				cursor.close();
			}
		} finally {
			cursor.close();
		}
		return no;
	}

	public boolean dbOpenCheck() {
		try{
			return db.isOpen();
		}catch(Exception e){
			return false;
		}
	}

	/*
	 * Get value of any table as per the condition.
	 */

	public String getValue(String table, String column, String where) {
		Cursor result = db.query(false, table, new String[] { column }, where,
				null, null, null, null, null);
		String value = "";
		try {
			if (result.moveToFirst()) {
				value = result.getString(0);
			} else {
				return null;
			}
		} finally {
			result.close();
		}
		return value;
	}

	/*
	 * Get Multiple Values from column of any specified table.
	 */

	public String[] getValues(boolean b, String table, String column, String where, String orderBy) {
		ArrayList<String> savedAns = new ArrayList<String>();
		Cursor result = null;
		String[] y;
		try {
			result = db.query(b, table, new String[] { column }, where, null, null, null, orderBy, null);
			if (result.moveToFirst()) {
				do {
					savedAns.add(result.getString(result.getColumnIndex(column)));
				} while (result.moveToNext());
			} else {
				return null;
			}
			y = savedAns.toArray(new String[result.getCount()]);
		} finally {
			result.close();
		}
		return y;
	}


	public Cursor getTableRecords(String tablename, String[] columns, String where, String orderby){
		Cursor cursor =  db.query(false, tablename, columns,where, null, null, null, orderby, null);
		return cursor;
	}


	public int updateRecords(String table, ContentValues values, String whereClause, String[] whereArgs){
		int a = db.update(table, values, whereClause, whereArgs);
		return a;
	}


	public void insertInTableViaArray(String tableName, String[] columns, String[] values){
		try {
			ContentValues newValues = new ContentValues();
			for(int i=0; i<columns.length; i++){
				newValues.put(columns[i], values[i]);
			}
			db.insert(tableName, null, newValues);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public long insertContentVals(String tableName, ContentValues content){
		long id=0;
		try{
			id = db.insert(tableName, null, content);
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public List<InvoiceHandler> getAllInvoices(int userId) {
		List<InvoiceHandler> invoices = new LinkedList<InvoiceHandler>();

		// select invoice query
		String query = "SELECT  "+ Constants.SERVICE_NAME+ ", "+ Constants.CHARGES+ ", "+
				Constants.BOOKING_DATETIME+ "," + Constants.BOOKING_PERSON+" FROM " + Constants.TRANSACTION+ " WHERE " + Constants.USERID
				+ " = '"+ userId+ "'";

		// get reference of the cruise database
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Cursor c= db.rawQuery(query,null );

		// parse all results
		InvoiceHandler invoice = null;
		if (cursor.moveToFirst()) {
			do {
				invoice = new InvoiceHandler();
				invoice.setService(cursor.getString(0));
				invoice.setCharges(cursor.getDouble(1));
				invoice.setDate(cursor.getString(2));
				invoice.setBookingPerson(cursor.getString(3));

				// Add invoice to invoices
				invoices.add(invoice);
			} while (cursor.moveToNext());
		}
		return invoices;
	}
}