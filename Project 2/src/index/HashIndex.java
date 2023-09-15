package index;

import java.util.ArrayList;
import java.util.HashMap;

import recordhash.Loader;
import recordhash.Record;

public class HashIndex implements Index {
	
	private HashMap<Integer, ArrayList<IndexPointer>> index;
	
	public HashIndex() {
		index = new HashMap<Integer, ArrayList<IndexPointer>>();
	
		load();
	}
	
	private void load() {
		Record records[] = Loader.getEveryRecord();
		
		for(Record record : records) {
			IndexPointer ptr = new IndexPointer(record.getFileNumber(), record.getRecordNumber());
			
			put(record.getRandomValue(), ptr);
			
		}
		
	}

	
	public void put(Integer randomValue, IndexPointer indexPointer) {
		
		ArrayList<IndexPointer> tempList = null;
		
		if(index.containsKey(randomValue)) {
			tempList = index.get(randomValue);
		} else {
			tempList = new ArrayList<IndexPointer>();
		}
		
		tempList.add(indexPointer);
		
		index.put(randomValue, tempList);
		
	}

	public ArrayList<Record> get(Integer randomValue) {
		ArrayList<IndexPointer> list = index.get(randomValue);
		
		if(list == null) return null;
		
		ArrayList<Record> results = new ArrayList<Record>();
		
		
		for(IndexPointer ptr : list) {
			results.add(Loader.getRecord(ptr));
		}
		
		return results;
	}
	
	public String toString() { 
		return "HashIndex used";
	}
	
}
