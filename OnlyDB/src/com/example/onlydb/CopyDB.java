package com.example.onlydb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;

public class CopyDB {
	
	private Context mcontext;
	private final String DB_PATH;
	
	public CopyDB(Context context) {
		mcontext = context;
		DB_PATH = "/data/data/" + mcontext.getPackageName() + "/databases/";
	}
	
	public void toPhone(){
		Log.d("카피DB", "DB를 폰으로 카피합니다");
		
		File db_dir = new File(DB_PATH);
		File db_file = new File(DB_PATH + DBworker.DB_NAME);

		if (db_file.exists()) {
			db_file.delete();
			Log.d("카피DB", "DB가 존재하니 지웠삼");
		}
		
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			if (!db_dir.exists()) {
				db_dir.mkdir();
			}
			Log.d("카피DB", "디렉토리존재여부" + String.valueOf(db_dir.isDirectory()));
			is = mcontext.getAssets().open(DBworker.DB_NAME);

			fos = new FileOutputStream(db_file);

			int num = is.available();
			Log.d("num", "" + num);
			byte buffer[] = new byte[num];
			int length;
			while ((length = is.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
			}

			fos.flush();
			

			Log.d("카피DB", "폰으로의 카피를 완료하였습니다");
		} catch (Exception e) {
			Log.d("카피DB", "------error----");
			e.printStackTrace();
		} finally{
			try{
			is.close();
			fos.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
			
	}
	
	public void toSdcardTop(){
		Log.d("카피DB","결과물을 storage/sdcard/AAA 폴더로 복사합니다");
		
		File db_file = new File(DB_PATH + DBworker.DB_NAME);
		
		File aaa_dir = new File("/storage/sdcard0/AAA");
		File aaa_file = new File("/storage/sdcard0/AAA/StationDB.db");
		
		if(!aaa_dir.exists())
			aaa_dir.mkdir();
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(db_file);
			fos = new FileOutputStream(aaa_file);
			
			byte buffer[] = new byte[1024];
			
			while(true){
				if(fis.read(buffer)<0)
					break;
				fos.write(buffer);
			}
			
			fos.flush();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try{
			fis.close();
			fos.close();
			} catch (Exception e){
				Log.d("카피DB", "------error----");
				e.printStackTrace();
			}
		}
		
		Log.d("카피DB","storage/sdcard/AAA 폴더로의 복사가 완료되었습니다");
	}
}
