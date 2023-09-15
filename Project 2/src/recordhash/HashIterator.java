package recordhash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.String;

public class HashIterator implements Iterator<String> {
	
	private String strBlock;
	private String[] recordStrings;
	private int currPosition;
	
	public HashIterator(String strBlock) {
		this.strBlock = strBlock;
		this.currPosition = 0;
		this.recordStrings = splitString(this.strBlock, Record.RECORD_SIZE);
	}

	@Override
	public boolean hasNext() {
		return currPosition < recordStrings.length;
	}

	@Override
	public String next() {
		
		String recordString = recordStrings[currPosition];
		currPosition++;
		
		return recordString;
		
	}
	
	public int getRecordCount() { 
		return this.recordStrings.length; 
	}
	
	public static String[] splitString(String strBlock, int size) {
		
		List<String> sections = new ArrayList<String>();
		
		int length = strBlock.length();
		
		for(int x = 0; x < length; x += size) {
			sections.add(strBlock.substring(x, Math.min(length, x + size)));
		}
		return sections.toArray(new String[0]);
		
	}

}
