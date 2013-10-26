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

	public static final String DB_NAME = "CopyTestDB";
	
	SQLiteDatabase db;
	Context mcontext;

	public DBworker(Context context) {
		mcontext = context;
		Log.d("dbworker","start");
//		copyDB();
//		Log.d("copyDB","complete");
		
		SQLiteOpenHelper helper = new SQLiteOpenHelper(context, DB_NAME, null, 1) {
			
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			}
			
			@Override
			public void onCreate(SQLiteDatabase db) {
				CreateSql csql = new CreateSql(db,mcontext);
			}
		};
		
		db = helper.getWritableDatabase();
	}
	
	private void copyDB() {
		final String DB_PATH = "/data/data/" + mcontext.getPackageName() + "/databases/";
		File db_dir = new File(DB_PATH);
		File db_file = new File(DB_PATH + DB_NAME);

		if (!db_file.exists()) {
			try {
				if(!db_dir.exists()){
					db_dir.mkdir();
				}
				Log.d("create","createdb");
				Log.d("directory",String.valueOf(db_dir.isDirectory()));
				InputStream is = mcontext.getAssets().open(DB_NAME);

				FileOutputStream fos = new FileOutputStream(db_file);

				int num = is.available();
				Log.d("num", "" + num);
				byte buffer[] = new byte[num];
				int length;
				while ((length = is.read(buffer)) != -1) {
					fos.write(buffer, 0, length);
				}

				fos.flush();
				is.close();
				fos.close();

				Log.d("copy db", "------complete----");
			} catch (Exception e) {
				Log.d("copy db", "------error----");
				e.printStackTrace();
			}
		}
	}
	
	public Cursor selectDB(String select){
		String sql = "select * from stationInfo";
		return db.rawQuery(sql, null);
	}
	
	

}
