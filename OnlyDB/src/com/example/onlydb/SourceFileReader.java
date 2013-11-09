package com.example.onlydb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;

public class SourceFileReader {

	Context context;
	ArrayList<String> result = new ArrayList<String>();

	public SourceFileReader(Context context, int rawId) {
		this.context = context;

		work(rawId);
	}

	private void work(int source) {
		try {
			InputStream is = context.getResources().openRawResource(source);

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line = null;

			reader.readLine(); // 일부러 한줄 건너뜀 첫줄에 오류가 있는 경우때문에
			while (true) {
				line = reader.readLine();
				if(line == null)
					break;
				result.add(line);
			}
			
			reader.close();
			is.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getSourceString(){
		return result;
	}

}
