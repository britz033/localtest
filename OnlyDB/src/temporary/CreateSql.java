package temporary;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.onlydb.R;

public class CreateSql {
	
	Context mcontext;

	public CreateSql(SQLiteDatabase db, Context context){
		Log.d("DB 작성 클래스 ","시작");
		mcontext = context;
		ArrayList<StationItem> stationList = null;
		try {
			stationList = makeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sql = "CREATE TABLE stationInfo(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"station_number TEXT NOT NULL," +
				"station_name TEXT NOT NULL," +
				"station_latitude DOUBLE NOT NULL," +
				"station_longitude DOUBLE NOT NULL)";
		db.execSQL(sql);
		
		db.beginTransaction();
		
		try {
			for (int i = 0; i < stationList.size(); i++) {
				sql = "INSERT INTO stationInfo(station_number,station_name,station_latitude,station_longitude) VALUES("
						+ "'"
						+ stationList.get(i).getStation_number()
						+ "',"
						+ "'"
						+ stationList.get(i).getStation_name()
						+ "',"
						+ stationList.get(i).getStation_latitude()
						+ ","
						+ stationList.get(i).getStation_longitude() + ")";
				db.execSQL(sql);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.d("insert","error");
		} finally {
			db.endTransaction();
		}
	}
	
	private ArrayList<StationItem> makeList() throws Exception{
		Log.d("파일추출","시작");
		ArrayList<StationItem> stationList = new ArrayList<StationItem>();
		
		InputStream is = mcontext.getResources().openRawResource(R.raw.latitude);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
		
		/*
		 * 01125	,	1차서한화성타운앞	,	http://map.naver.com/?dlevel=11&x=128.5288182&y=35.8541694&stationId=433544&enc=b64
		   02227	,	2.28기념중앙공원건너1	,	http://map.naver.com/?dlevel=11&x=128.5979063&y=35.8706364&stationId=432058&enc=b64
		 */
		while(true){
			String line = reader.readLine();
			if(line == null)
				break;
			Pattern pattern = Pattern.compile("^(\\d+)\\s+,\\s+(\\S+)[,\\s]+\\S+x=(\\d+.\\d+)&y=(\\d+.\\d+)");
			Matcher matcher = pattern.matcher(line);
			
			if(matcher.find()){
				StationItem item = new StationItem();
				item.station_number = matcher.group(1);
				item.station_name = matcher.group(2);
				item.station_latitude = Double.valueOf(matcher.group(4));
				item.station_longitude = Double.valueOf(matcher.group(3));
				stationList.add(item);
			}
		}
		
		return stationList;
	}
	
	@Getter @Setter class StationItem{
		private String station_number;
		private String station_name;
		private double station_latitude;
		private double station_longitude;
	}

}
