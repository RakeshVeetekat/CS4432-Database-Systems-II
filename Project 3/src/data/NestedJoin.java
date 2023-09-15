package data;

import dataset.DataFileReader;
import dataset.Dataset;
import hash.Bucket;

public class NestedJoin {
	
	private Bucket startBucket;
	private Bucket results;
	private Dataset startDataset; 
	private Dataset joinDataset;
	private JoinCondition cond;
	
	public NestedJoin(Dataset startingDataset, Dataset joiningDataset, JoinCondition condition) {
		this.startDataset = startingDataset;
		this.joinDataset = joiningDataset;
		this.cond = condition;
		this.startBucket = new Bucket();
		this.results = new Bucket();
		this.join();
	}
	
	private void join() {
		
		DataFileReader reader = null;
		
		for(int fileNumber = 1; fileNumber <= this.startDataset.getNumFiles(); fileNumber++) {
			
			reader = new DataFileReader(this.startDataset, fileNumber);
			Bucket records = reader.getAllRecords();
			this.startBucket.addAll(records);
			
		}
		
		for(int fileNumber = 1; fileNumber <= this.joinDataset.getNumFiles(); fileNumber++) {
			
			reader = new DataFileReader(this.joinDataset, fileNumber);
			
			for(int n = 1; n <= this.joinDataset.getRecordsPerFile(); n++) {
				
				Record record = reader.getRecord(n);
				Bucket subBucket = this.startBucket.meetsJoinCondition(cond, record);
				this.results.addAll(subBucket);
				
			}
		}	
	}
	
	public Bucket getResults() { 
		return this.results; 
	}
	
}
