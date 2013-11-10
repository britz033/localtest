package ParsingClass;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 버스 아이템에다가 source를 쳐넣는다
 * busNumber, busInterval
 * interval 의 경우
 * 12 분, 11 분   : 두개 다 있을때 '평일:12분','휴일:11분'
 *   , 11분 : 앞에 빈칸 혹은 뒤에 빈칸 일 경우 그냥 '간격:11분' 붙임
 *   
 *   순환3-1:12 분,15 분
	101 (파계사방면):19 분, 
	101 (덕곡방면):97 분(일 8회), 
	101 (파계사방면(휴일)): ,21 분
	번호는 : 세미콜론으로 구분
 */
public class ParsingBusInterval extends ParsingWork{
	
	

	public ParsingBusInterval(ArrayList<String> source, ArrayList<BusItem> buslist) {
		super(source, buslist);
	}

	@Override
	protected void fillList(ArrayList<String> source, ArrayList<BusItem> buslist) {
		Pattern pattern = Pattern.compile("^([^:]+):([^,]+),(.+)$");
		for(int i=0;i<source.size();i++){
			Matcher matcher = pattern.matcher(source.get(i));
			matcher.find();
			buslist.get(i).setBusNumber(matcher.group(1));
			String day = matcher.group(2);
			String holiday = matcher.group(3);
			String interval = null;
			if(day.equals(" ")){
				interval = holiday;
			} else if (holiday.equals(" ")){
				interval = day;
			}
			
			interval = "평일:" + day +",휴일:" + holiday;
			buslist.get(i).setBusInterval(interval);
		}
	}


}
