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

//	(주)성안건너 (00304)#7021042100:128.62451676809218,35.914943211140496
//	(주)영화건너 (03092)#7111058300:128.42330333277417,35.63367166859499
//	(주)영화앞 (03091)#7111059100:128.42315333314116,35.63381166851292
	public void run() {
		DBworker dbWorker = new DBworker(context);
		SQLiteDatabase db = dbWorker.getDB();
		
		try {
			
			// 이름 , 정류소번호, id, long, lati
			

			InputStream is = context.getResources().openRawResource(R.raw.bus_station);

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line = null;

			reader.readLine();
			
			db.beginTransaction();
			while (true) {
				StringBuilder params = new StringBuilder();
				if ((line = reader.readLine()) == null)
					break;
				Pattern pattern = Pattern.compile("^(.+) \\((\\d+)\\)#(\\d+):(\\d+.\\d+),(\\d+.\\d+)$");
				Matcher matcher = pattern.matcher(line);
				matcher.find();

				params.append("'");
				params.append(matcher.group(2)).append("','");	// 번호
				params.append(matcher.group(1)).append("','");	// 이름
				params.append(matcher.group(4)).append("','");	// long
				params.append(matcher.group(5)).append("','");	// lati
				params.append(matcher.group(3)).append("'");	// id

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
