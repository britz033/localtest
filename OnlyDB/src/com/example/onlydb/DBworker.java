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

	public static final String DB_NAME = "StationDB.db";

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
				Log.d("DB생성에 들어갔습니다", "몬가 잘못되었삼");
//				 CreateSql csql = new CreateSql(db, mcontext);
			}
		};

		db = helper.getWritableDatabase();
	}

	// _id , number, interval, forward, backward, favorite
	public void insertDB(String tableName, String params) {
		String sql = "INSERT INTO " + tableName + "(bus_number,bus_interval,bus_forward,bus_backward)" + " VALUES("
				+ params + ")";
		db.execSQL(sql);
	}
	
	public void insertStationTable(String params){
		String sql =  "INSERT INTO stationInfo(station_number,station_name,station_longitude,station_latitude) "
				+ "VALUES(" + params + ")";
		db.execSQL(sql);
	}
	
	public void closeDB(){
		db.close();
	}

	public Cursor selectDB(String select) {
		String sql = "select * from stationInfo";
		return db.rawQuery(sql, null);
	}
	
	public SQLiteDatabase getDB(){
		return db;
	}

}
