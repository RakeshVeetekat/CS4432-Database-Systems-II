package index;

import java.util.ArrayList;

import recordhash.Loader;
import recordhash.Record;

public class DefaultIndex implements Index {

	public ArrayList<Record> get(Integer value) {
		
		ArrayList<Record> list = new ArrayList<Record>();
		
		Record all[] = Loader.getEveryRecord();
			
		for(Record r : all) {
			if(value == r.getRandomValue()) {
				list.add(r);
			}
		}
		
		return list;
	}

	public void put(Integer value, IndexPointer indexPointer) {
		
	}
	
	public String toString() { 
		return "Full Table Scan used";
	}

}
