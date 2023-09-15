package hash;

import java.util.HashMap;
import data.Record;
import dataset.DataFileReader;
import dataset.Dataset;

public class HashTable extends HashMap<String, Bucket> {
	
	private static final long serialVersionUID = 1L;
	private Dataset data;
	private KeyGenerator generator;
	
	private static final int BUCKETS = 500;
	
	public HashTable(Dataset dataset, KeyGenerator generator) {
		
		this.data = dataset;
		this.generator = generator;
		this.loadData();
		
	}
	
	private void loadData() {
		
		for(int n = 1; n <= this.data.getNumFiles(); n++) {
			
			DataFileReader rdr = new DataFileReader(this.data, n);
			
			for(int i = 1; i <= this.data.getRecordsPerFile(); i++) {
				Record record = rdr.getRecord(i);
				this.put(record);
			}
			
		}
		
	}
	
	public void put(Record record) {
		
		if(record == null) {
			return;
		}
		
		String key = getKey(record);
		Bucket bucket = super.get(key);
		
		if(bucket == null) {
			bucket = new Bucket();
		}
		
		bucket.add(record);
		super.put(key, bucket);
		
	}
	
	public String getKey(Record record) {
		return generator.make(record);
	}
	
	public Bucket getBucket(Record record) {
		return get(getKey(record));
	}
	
	public Bucket getBucket(String key) {
		return super.get(key);
	}
	
}
