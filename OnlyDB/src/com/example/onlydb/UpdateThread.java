package com.example.onlydb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UpdateThread extends Thread {

	private Context context;
	private NotifyComplete threadEnd;

	public UpdateThread(Context context) {
		this.context = context;
		threadEnd = (NotifyComplete) context;
	}

	public void run() {
		DBworker dbWorker = new DBworker(context);
		SQLiteDatabase db = dbWorker.getDB();

		Cursor cursor = null;
		try {
			final double value = 0.000384;
			final double value2 = 0.000172;
			
			String sql = "select _id,station_latitude,station_longitude from stationInfo";
			cursor = db.rawQuery(sql, null);
			int id = 0;
			double latitude = 0;
			double longitude = 0;
			double result = 0;
			double result2 = 0;
			db.beginTransaction();
			for (int i = 0; i < cursor.getCount(); i++) {

				id = cursor.getInt(0);
				latitude = cursor.getDouble(1);
				longitude = cursor.getDouble(2);
				
				
				if (id % 2 == 0) {
					result = (double)id * value;
					result2 = (double)id * value2;
				} else {
					result = (double)id* value;
					result2 = (double)id* value2;
				}
				
				latitude += result;
				longitude += result2;
				
				String sql2 = "UPDATE stationInfo SET station_latitude=" + latitude + ",station_longitude=" + longitude + " WHERE _id=" + id;
				db.execSQL(sql2);
			}
			db.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			cursor.close();
			db.close();
			cursor = null;
			db = null;
			threadEnd.complete();
		}

	}

}
