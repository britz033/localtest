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

	public BusUpdateThread(Context context) {
		this.context = context;
		threadEnd = (NotifyComplete) context;
	}

	/*

동화시설집단지구(종점)1;동화사입구건너;동화교2;시교육연수원입구건너;도학동;방짜유기박물관건너;서당마을건너;공산초등학교앞;미곡동2;구암동1;미대동1;팔공보성아파트앞;봉무청구새들마을건너;봉무레포츠공원건너;불로천주교회앞;불로전통시장건너;대구국제공항건너;아양교역(1번출구);동구청앞;동대구역지하도1;평화시장앞;강남약국건너;칠성시장앞;2.28기념중앙공원건너1;곽병원앞2;섬유회관건너1;큰장네거리1;달서교회건너;서구청앞;진달래아파트건너;중리시장앞;서대구공단네거리3;대구의료원라파엘웰빙센터건너;2차서한화성타운앞;용산2동주민센터건너2;대백한라무지개타운앞;성서푸른마을아파트앞;신당동주민센터건너;계명문화대학앞;계명대학교동문앞;세원정공건너;모다아울렛앞;신흥버스;	신흥버스;모다아울렛건너;세원정공앞;계명대학교동문건너2;계명문화대학건너;신당동주민센터앞;성서서한2차타운1;대백한라무지개타운건너;용산2동주민센터앞;2차서한화성타운건너;대구의료원라파엘웰빙센터;서대구공단네거리4;중리롯데캐슬앞;진달래아파트앞;서구청건너;달서교회앞;큰장네거리4;섬유회관앞;곽병원건너;2.28기념중앙공원앞;칠성시장건너;강남약국앞1;평화시장건너;동대구역지하도2;동구청건너;아양교역(2번출구);대구국제공항앞;불로전통시장앞;불로천주교회건너;봉무레포츠공원앞;봉무청구새들마을앞;팔공보성아파트건너;미대동2;구암동2;미곡동1;공산초등학교건너;서당마을앞;방짜유기박물관앞;도학동1;시교육연수원입구앞;동화교1;동화사입구앞;동화시설집단지구(종점)2;:
가창중학교건너;냉천리1;스파밸리2;중석타운앞;가창면사무소앞;화성파크뷰건너;대자연맨션;파동1차시장앞;수성못오거리2;정화우방팔레스건너;수성구보건소건너;동성초등학교건너;대구은행역(1번출구);경북대학교치과병원;2호선반월당역1;약령시건너;경상감영공원건너;홈플러스대구점건너;경북도청앞;산격주공아파트건너;무태네거리(시외방향);무태조야동주민센터건너2;동서변리벤빌건너;북구구민운동장앞;운암초등학교건너;부영3단지;칠곡그린빌3차앞;칠곡그린빌2차앞;칠곡부영1단지앞;대구산재병원건너;학정청아람앞;칠곡경북대병원후문건너;	칠곡경북대병원후문앞;학정청아람건너;대구산재병원앞;동화골든빌(동편);칠곡그린빌2차건너;칠곡그린빌3차건너;칠곡3차화성타운2;운암초등학교앞;북구구민운동장건너;동서변리벤빌앞;무태조야동주민센터앞2;무태네거리(시내방향);산격주공아파트앞;경북도청건너;홈플러스대구점앞;경상감영공원앞;약령시앞;2호선반월당역2;경대사대부설고등학교앞;대구은행역(2번출구);동성초등학교앞;수성구보건소앞;정화우방팔레스;수성못오거리1;파동1차시장건너;대자연맨션건너;화성파크뷰앞;가창면사무소건너;허브힐즈입구;스파밸리1;냉천리2;가창중학교앞;가창초등학교건너;:
범물1동주민센터건너;범물청구맨션앞;동아백화점수성점앞;지산청구타운앞;동아스포츠센터(지산초교앞);황금네거리1;어린이회관앞;그랜드호텔건너;법원앞;대구중앙고등학교건너;중구청앞;2.28기념중앙공원건너1;곽병원앞2;섬유회관건너1;원대금류타운건너;팔달시장앞;대백인터빌앞;대구병원앞;태전삼거리1;대구과학대학1;대구은행칠곡지점건너;칠곡동화아파트건너;읍내동주민센터건너;봉암동(칠곡군)1;동명면사무소앞;동명교통;	동명교통;동명면사무소건너;봉암동(칠곡군)2;칠곡중학교앞;칠곡동화아파트앞;대구은행칠곡지점앞;대구과학대학2;태전삼거리2;대구병원건너;대백인터빌건너;팔달시장건너;원대금류타운앞;섬유회관앞;곽병원건너;2.28기념중앙공원앞;중구청건너;대구중앙고등학교앞;법원건너;그랜드호텔앞;어린이회관건너1;황금네거리2;동아스포츠센터(지산중학교앞);지산청구타운건너;동아백화점수성점건너;범물청구맨션건너;범물1동주민센터앞;:
근린공원축구장앞;구지청아람건너;유가차천;달성우체국앞;논공가톨릭병원앞;북리2;상리공단;논공읍사무소건너1;옥포초등학교앞;화원고등학교앞;화원초등학교앞;대곡역(2번출구);	대곡역(1번출구);화원초등학교건너;화원고등학교건너;옥포초등학교건너;논공읍사무소;상리공단건너;북리1;논공가톨릭병원건너;달성우체국건너;유가차천1;구지청아람앞;근린공원축구장건너;:
	 */

	public void run() {
		DBworker dbWorker = new DBworker(context);
		SQLiteDatabase db = dbWorker.getDB();

		try {
			SourceFileReader addSource = new SourceFileReader(context, R.raw.bus_path4);
			ArrayList<String> busCode = addSource.getSourceString();

			String busForward = null;
			String busBackward = null;

			db.beginTransaction();
			for (int i = 0; i < busCode.size(); i++) {
				
//				Pattern pattern = Pattern.compile("^[^\\t]+\\t(\\d+)\\t([^\\t]+)\\t([^\\t]+)\\t?(.*)$");
				Pattern pattern = Pattern.compile("^([^\\t]+)\\t?(.*):$");
				Matcher matcher = pattern.matcher(busCode.get(i));
				
				matcher.find();
				
				busForward = matcher.group(1);
				busBackward = matcher.group(2);
				
				if(busBackward == null){
					busBackward = "";
				}
				int id = i+1;
				String sql = "UPDATE busInfo SET bus_forward='" + busForward + "'" + ",bus_backward='"
						+ busBackward + "' WHERE _id=" + id;
				db.execSQL(sql);
			}
			db.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			threadEnd.complete();
		}

	}

}
