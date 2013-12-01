package com.example.onlydb;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
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
			SourceFileReader addSource = new SourceFileReader(context, R.raw.bus_add2);
//			SourceFileReader pathSource = new SourceFileReader(context, R.raw.buspath);
			
			ArrayList<String> busCode = addSource.getSourceString();
//			ArrayList<String> busPath = pathSource.getSourceString();
			
			// 0-89	3690000110	1회	문양역건너,문양역앞,문양기지창앞,문양리입구2,동곡초등학교    도도엉,나아닐ㅇ,,
			// x 번호 x id x 인터벌 x for x back
			// 번호는 있고, id랑 인터벌, for, back 넣기. i 순으로
			
			db.beginTransaction();
			for (int i = 0; i < busCode.size(); i++) {
				Pattern pattern = Pattern.compile("^([^\\t.]+)\\t([^\\t.]+)\\t([^\\t.]+)\\t([^\\t.]+)\\t?(.*)$");
				Matcher matcher = pattern.matcher(busCode.get(i));
				
				matcher.find();
				String busNum = matcher.group(1);
				String busId = matcher.group(2);
				String busInterval = matcher.group(3);
				String busForward = matcher.group(4);
				String busBackward = matcher.group(5);

				StringBuilder sb = new StringBuilder();
				
				sb.append("bus_id='"+busId+"',");
				sb.append("bus_interval='"+busInterval+"',");
				sb.append("bus_forward='"+busForward+"',");
				if(TextUtils.isEmpty(busBackward)){
					sb.append("bus_backward=null");
				} else
					sb.append("bus_backward='"+busBackward+"'");
				
				if(i==6 || i==7)
					Log.d("디버그",busNum);
				dbWorker.updateTable("busInfo",sb.toString(),"_id", i+241);
//				dbWorker.insertTable("busInfo", "bus_number,bus_id,bus_forward", "'"+busNum+"','"+busId+"','임시'");
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
