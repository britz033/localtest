package com.example.onlydb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;

public class CopyDB {
	public CopyDB(Context mcontext) {
		Log.d("카피DB", "시작합니다");
		final String DB_PATH = "/data/data/" + mcontext.getPackageName() + "/databases/";
		File db_dir = new File(DB_PATH);
		File db_file = new File(DB_PATH + DBworker.DB_NAME);

		if (db_file.exists()) {
			db_file.delete();
			Log.d("카피DB", "DB가 존재하니 지웠삼");
		}
		
		try {
			if (!db_dir.exists()) {
				db_dir.mkdir();
			}
			Log.d("카피DB", "디렉토리존재여부" + String.valueOf(db_dir.isDirectory()));
			InputStream is = mcontext.getAssets().open(DBworker.DB_NAME);

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

			Log.d("카피DB", "------complete----");
		} catch (Exception e) {
			Log.d("카피DB", "------error----");
			e.printStackTrace();
		}
	}
}
