package ParsingClass;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class ParsingBusPath extends ParsingWork{

	public ParsingBusPath(ArrayList<String> source, ArrayList<BusItem> buslist) {
		super(source, buslist);
	}

	@Override
	protected void fillList(ArrayList<String> source, ArrayList<BusItem> busList) {
		
		Pattern pattern = Pattern.compile("^([^:]+):([^:]*)");
		for(int i=0;i<source.size();i++){
			Matcher matcher = pattern.matcher(source.get(i));
			matcher.find();
			busList.get(i).setBusFoward(matcher.group(1));
			busList.get(i).setBusBackward(matcher.group(2));
		}
	}
}
