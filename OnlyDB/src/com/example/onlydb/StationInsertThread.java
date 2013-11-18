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

	public StationInsertThread(Context context) {
		this.context = context;
	}

	// (station_number,station_name,station_longitude,station_latitude,station_favorite)
	// (주)성안건너 (00304):128.62451676809218,35.914943211140496
	public void run() {
		DBworker dbWorker = new DBworker(context);
		SQLiteDatabase db = dbWorker.getDB();
		
		try {
			
			

			InputStream is = context.getResources().openRawResource(R.raw.bus_station);

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line = null;

			reader.readLine();
			
			db.beginTransaction();
			while (true) {
				StringBuilder params = new StringBuilder();
				if ((line = reader.readLine()) == null)
					break;
				Pattern pattern = Pattern.compile("^(.+) \\((\\d+)\\):(\\d+.\\d+),(\\d+.\\d+)$");
				Matcher matcher = pattern.matcher(line);
				matcher.find();

				params.append("'");
				params.append(matcher.group(2)).append("','");
				params.append(matcher.group(1)).append("','");
				params.append(matcher.group(3)).append("','");
				params.append(matcher.group(4)).append("'");

				dbWorker.insertStationTable(params.toString());
			}
			db.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
			Log.d("스레드","완료했습니다");
		}
	}
}
