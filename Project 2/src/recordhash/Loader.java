package recordhash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import index.IndexPointer;

public class Loader {
	
	private static Record[] everyRecord = null;
	
	private final static String strDataset = "Project2Dataset";
	
	private final static int NUM_FILES = 99;
	
	private static HashMap<Integer, Record[]> loadedBlocks = new HashMap<Integer, Record[]>();
	
	public static Record[] getEveryRecord() {
		
		if(everyRecord != null) {
			return everyRecord;
		}
		
		List<Record> records = new ArrayList<Record>();
		
		for(int x = 1; x <= NUM_FILES; x++) {
			
			for(Record r : getRecords(x)) {
				records.add(r);
			}
		}
		
		everyRecord = records.toArray(new Record[0]);
		
		return everyRecord;
		
	}
	
	public static Record[] getRecords(int blockNumber) {
		
		if(loadedBlocks.containsKey(blockNumber)) {
			return loadedBlocks.get(blockNumber);
		}
		
		
		try {
			
			String blockString = Files.readAllLines(Paths.get(strDataset + "/F" + blockNumber + ".txt")).get(0);
		
			HashIterator blockIterator = new HashIterator(blockString);
		
			List<Record> records = new ArrayList<Record>();
			
			while(blockIterator.hasNext()) {
				String recordString = blockIterator.next();
				//now lets make it into a record
				
				Record record = Record.parseRecord(recordString);
				
				records.add(record);
			}
		
			
			loadedBlocks.put(blockNumber, records.toArray(new Record[0]));
			
			return getRecords(blockNumber);
		} catch (IOException e) {
			System.out.println("Could not read file #" + blockNumber);
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	public static Record getRecord(IndexPointer ptr) {
		
		if(ptr.getRecordNumber() == 0) System.out.println("f: " + ptr.getFileNumber() + " r: " + ptr.getRecordNumber());
		
		return getRecords(ptr.getFileNumber())[ptr.getRecordNumber() - 1];
		
	}
	
	public static int getLoadedBlockCount() {
		return loadedBlocks.size();
	}
	
	public static void flush() {
		everyRecord = null;
		loadedBlocks = new HashMap<Integer, Record[]>();
	}
}
