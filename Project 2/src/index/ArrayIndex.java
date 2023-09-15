package index;

import java.util.ArrayList;

import recordhash.Loader;
import recordhash.Record;

public class ArrayIndex implements Index {
	
	private ArrayList<IndexPointer> index[];
	
	public ArrayIndex() {
		this.index = new ArrayList[5000];
		load();
	}

	public ArrayList<Record> get(Integer value) {
		
		ArrayList<Record> res = new ArrayList<Record>();
		
		if(index[value - 1] == null) return res;
		
		for(IndexPointer ptr : index[value - 1]) {
			res.add(Loader.getRecord(ptr));
		}
		
		return res;
	
	}

	public void put(Integer value, IndexPointer indexPointer) {
		
		if(index[value - 1] == null) {
			index[value - 1] = new ArrayList<IndexPointer>();
		}
	
		index[value - 1].add(indexPointer);
		
	}
	
	public void load() {
		
		Record records[] = Loader.getEveryRecord();
		
		for(Record record : records) {
			IndexPointer ptr = new IndexPointer(record.getFileNumber(), record.getRecordNumber());
			
			put(record.getRandomValue(), ptr);
		}
	}
	
	public String toString() { 
		return "Array-Based Index used"; 
	}



}
