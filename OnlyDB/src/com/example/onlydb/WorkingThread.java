package com.example.onlydb;

import java.util.ArrayList;

import android.content.Context;

public class WorkingThread extends Thread {

	private Context context;

	public WorkingThread(Context context) {
		this.context = context;
	}

	public void run() {
		ArrayList<String> source1 = new SourceFileReader(context, MainActivity.SOURCE_ID1).getSourceString();
		ArrayList<String> source2 = new SourceFileReader(context, MainActivity.SOURCE_ID2).getSourceString();

		final int INDEX = source1.size();
		
		ArrayList<BusItem> buslist = new ArrayList<BusItem>();

		// 미리 버스수만큼 아이템 추가
		for (int i = 0; i < INDEX; i++) {
			buslist.add(new BusItem());
		}

		// 파싱하고 데이터를 버스아이템에 추가
		new ParsingBusInterval(source1, buslist);
		new ParsingBusPath(source2, buslist);

		// 완성된 buslist 로 insert문 values파라메터 구성해서 쿼리로 날림

		String[] params = new String[INDEX];
		StringBuilder sb = new StringBuilder();

		// (number,interval,forward,backward)
		for (int i = 0; i < INDEX; i++) {
			sb.append("(").append(buslist.get(i).getBusNumber()).append(",").append(buslist.get(i).getBusInterval());
			sb.append(",").append(buslist.get(i).getBusFoward()).append(",").append(buslist.get(i).getBusBackward()).append(")");
			
			params[i] = sb.toString();
		}
		
		DBworker db = new DBworker(context);
		db.insertDB(MainActivity.TABLE_NAME, params);
	}
}
