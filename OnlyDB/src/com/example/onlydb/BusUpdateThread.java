package com.example.onlydb;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BusUpdateThread extends Thread {

	private Context context;
	private NotifyComplete threadEnd;
	
	public BusUpdateThread(Context context){
		this.context = context;
		threadEnd = (NotifyComplete)context;
	}
	
	public void run(){
		DBworker dbWorker = new DBworker(context);
		SQLiteDatabase db = dbWorker.getDB();
		
		try{
			SourceFileReader codeSource = new SourceFileReader(context, R.raw.bus_code);
//			SourceFileReader pathSource = new SourceFileReader(context, R.raw.buspath);
			
			ArrayList<String> busCode = codeSource.getSourceString();
//			ArrayList<String> busPath = pathSource.getSourceString();
			
			//급행2	1000002000	9 분,11 분
			//급행3	1000003000	11 분,12 분
			// x 탭 x 탭 x
			
			
			db.beginTransaction();
			for (int i = 0; i < busCode.size(); i++) {
				Pattern pattern = Pattern.compile("^(.+)\\t(.+)\\t(.+)$");
				Matcher matcher = pattern.matcher(busCode.get(i));
				
				matcher.find();
				String busNum = matcher.group(1);
				String busId = matcher.group(2);
				String busInterval = matcher.group(3);
				
				pattern = Pattern.compile("^(.+),(.+)$");
				matcher = pattern.matcher(busInterval);
				matcher.find();
				String day = matcher.group(1);
				String holyDay = matcher.group(2);
				
//				101 (파계사방면)	3000101000	19 분, 
//				101 (덕곡방면)	3000101001	97 분(일 8회), 
//				101 (파계사방면(휴일))	3000101002	 ,21 분
				
				if(day.equals(" "))
					busInterval = "휴일:"+holyDay;
				else if(holyDay.equals(" "))
					busInterval = day;
				else
					busInterval = "평일:"+day+", 휴일:"+holyDay;
				
				StringBuilder sb = new StringBuilder();
				
				sb.append("bus_number=").append("'" +busNum+ "'").append(",");
				sb.append("bus_interval=").append("'" +busInterval+ "'").append(",");
				sb.append("bus_id=").append("'" +busId+ "'");
				
				dbWorker.updateBusTable(sb.toString(), i+1);
			}
			db.setTransactionSuccessful();
			
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			db.endTransaction();
			threadEnd.complete();
		}
		
	}
	
}
