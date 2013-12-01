package com.example.onlydb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StationInsertThread extends Thread {

	private Context context;
	private NotifyComplete threadEnd;

	public StationInsertThread(Context context) {
		this.context = context;
		threadEnd = (NotifyComplete) context;
	}

	public void run() {
		DBworker dbWorker = new DBworker(context);
		SQLiteDatabase db = dbWorker.getDB();

		db.execSQL("ALTER TABLE stationInfo ADD COLUMN station_pass TEXT");

		try {

			// 이름 , 정류소번호, id, long, lati

			InputStream is = context.getResources().openRawResource(R.raw.passid);

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line = null;

			reader.readLine();

			int index = 1;
			db.beginTransaction();
			while (true) {
				StringBuilder params = new StringBuilder("station_pass=");
				if ((line = reader.readLine()) == null)
					break;
				Pattern pattern = Pattern.compile(":(.+)$");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					params.append("'");
					params.append(matcher.group(1)).append("'"); // 이름
				} else {
					Log.d("매치실패",line);
					index++;
					continue;
				}

				dbWorker.updateTable("stationInfo", params.toString(), "_id", index++);
			}
			db.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
			threadEnd.complete();
		}
	}
}
