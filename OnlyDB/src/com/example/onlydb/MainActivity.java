package com.example.onlydb;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity implements NotifyComplete{

	private static final String TAG_FRAGMENT_SEARCH = "fragment_search";
	public final static String TABLE_NAME = "busInfo";
	public final static int SOURCE_ID1 = R.raw.bus_code;
	public final static int SOURCE_ID2 = R.raw.buspath;
	private CopyDB copyDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		copyDB = new CopyDB(this);
		copyDB.toPhone();
		
		new BusUpdateThread(this).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class StationProvider extends ContentProvider {

		@Override
		public int delete(Uri uri, String selection, String[] selectionArgs) {
			return 0;
		}

		@Override
		public String getType(Uri uri) {
			return null;
		}

		@Override
		public Uri insert(Uri uri, ContentValues values) {
			return null;
		}

		@Override
		public boolean onCreate() {
			return false;
		}

		@Override
		public Cursor query(Uri uri, String[] projection, String selection,
				String[] selectionArgs, String sortOrder) {
			return null;
		}

		@Override
		public int update(Uri uri, ContentValues values, String selection,
				String[] selectionArgs) {
			return 0;
		}
	}

	@Override
	public void complete() {
		
		copyDB.toSdcardTop();
		
		Log.d("DB작업","완료되었습니다");
		
		Uri pakageUri = Uri.parse("package:"+getPackageName());
		Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, pakageUri);
		startActivity(intent);
	}

}
