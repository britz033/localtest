package com.example.onlydb;

import java.util.ArrayList;

import ParsingClass.BusItem;
import ParsingClass.ParsingBusInterval;
import ParsingClass.ParsingBusPath;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private static final String TAG_FRAGMENT_SEARCH = "fragment_search";
	public final static String TABLE_NAME = "busInfo";
	public final static int SOURCE_ID1 = R.raw.interval;
	public final static int SOURCE_ID2 = R.raw.buspath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CopyDB hehe = new CopyDB(this);
		WorkingThread thread = new WorkingThread(this);
		thread.start();
//		attachFragment();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void attachFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		StationSearchFragment ssfragment = new StationSearchFragment();
		ft.add(R.id.container, ssfragment, TAG_FRAGMENT_SEARCH);
		ft.commit();
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

}
