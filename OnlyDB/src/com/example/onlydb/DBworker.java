package com.example.onlydb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import temporary.CreateSql;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* db작업용
 * 쿼리의 종류는 역이름, 역번호
 * 이름검색후 역목록 커서리턴 (역찾기, 
 * 역번호검색후 역목록 커서리턴 ,
 * 이름검색후 역위치 커서리턴 ,
 */

public class DBworker {

	public static final String DB_NAME = "StationDB2";

	SQLiteDatabase db;
	Context mcontext;

	public DBworker(Context context) {
		mcontext = context;
		Log.d("dbworker", "start");

		SQLiteOpenHelper helper = new SQLiteOpenHelper(context, DB_NAME, null, 1) {

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				CreateSql csql = new CreateSql(db, mcontext);
			}
		};

		db = helper.getWritableDatabase();
	}

	// _id , number, interval, forward, backward, favorite
	public void insertDB(String tableName, String[] params) {

		db.beginTransaction();
		try {
			for (int i = 0; i < params.length; i++) {
				String sql = "INSERT INTO " + tableName + " VALUES(" + params[i] + ")";
				db.execSQL(sql);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	public Cursor selectDB(String select) {
		String sql = "select * from stationInfo";
		return db.rawQuery(sql, null);
	}

}
