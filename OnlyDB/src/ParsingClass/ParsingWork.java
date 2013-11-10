package ParsingClass;

import java.util.ArrayList;

abstract public class ParsingWork {
	public ParsingWork(ArrayList<String> source, ArrayList<BusItem> buslist) {
		fillList(source,buslist);
	}
	
	abstract protected void fillList(ArrayList<String> source, ArrayList<BusItem> buslist);
}
