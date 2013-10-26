package com.example.onlydb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/*상단에 검색텍스트창이 있는 리스트 프래그먼트
 * 검색어를 입력할때마다 리스너가 반응하여 
 * db에서 결과값을 가져오며 그것을 simplecursoradapter로 리스트뷰로 변환한다.
 * 이때 커서 관리는 loader가 담당한다
 */

public class StationSearchFragment extends ListFragment implements LoaderCallbacks<Cursor>{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_bus_search_layout, null);
		EditText ed = (EditText) view.findViewById(R.id.edittext_bussearch);
		
		TextWatcher watcher = new MyWatcher();
		ed.addTextChangedListener(watcher);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		String[] from = {"station_number","station_name"};
		int[] to = {android.R.id.text1, android.R.id.text2};
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null, from, to, 0);
		setListAdapter(adapter);
		
		// 로더 구분용 id (같으면 재사용, 다르면 create), 추가인자, 리스너
		getLoaderManager().initLoader(0, null, this);
	}
	
	class MyWatcher implements TextWatcher{

		@Override
		public void afterTextChanged(Editable s) {
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
		}
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}
	

}
