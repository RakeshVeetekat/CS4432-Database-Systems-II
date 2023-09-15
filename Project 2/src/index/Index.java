package index;

import java.util.ArrayList;

import recordhash.Record;

public interface Index {
	ArrayList<Record> get(Integer value);
	
	void put(Integer value, IndexPointer indexPointer);

	String toString();

}
