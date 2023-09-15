package query;

import java.util.Set;
import data.NestedJoin;
import data.Record;
import dataset.DataFileReader;
import dataset.Dataset;
import hash.Bucket;
import hash.HashTable;

public class Query {

	public static void performHashJoin() {

		long begin = System.currentTimeMillis();

		HashTable tbl = new HashTable(Dataset.A, (record) -> {
			
			int randV = record.getRandomValue();
			String key = Integer.toBinaryString(randV);
			return key;
			
		});

		DataFileReader rdr = null;

		System.out.println("Results:");

		Bucket resultBucket = new Bucket();

		for (int n = 1; n <= Dataset.B.getNumFiles(); n++) {
			
			rdr = new DataFileReader(Dataset.B, n);

			for (int i = 1; i < Dataset.B.getRecordsPerFile(); i++) {
				
				Record record = rdr.getRecord(i);
				Bucket bucket = tbl.getBucket(record);
				resultBucket.addAll(bucket);

				for (Record r : bucket) {
					System.out.println("\t" + r.getIdentifierString() + ", " + r.getNameString() + ", "
							+ record.getIdentifierString() + ", " + record.getNameString());
				}
			}

		}

		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - begin) + " ms");
		
	}

	public static void performBlockLevelNestedLoopJoin() {

		long begin = System.currentTimeMillis();

		NestedJoin join = new NestedJoin(Dataset.A, Dataset.B, (r1, r2) -> {
			return r1.getRandomValue() > r2.getRandomValue();
		});

		long end = System.currentTimeMillis();

		Bucket results = join.getResults();

		System.out.println("Result count: " + results.size());
		System.out.println("Execution time: " + (end - begin) + " ms");

	}

	public static void performHashBasedAggregationFunction(Dataset dataset, AggregationFunctionType af) {
		
		long begin = System.currentTimeMillis();
		
		HashTable table = new HashTable(dataset, (record) -> {
			return record.getNameString();
		});
		 
		Set<String> keys = table.keySet();
		System.out.println("Results:");
		
		for(String key : keys) {
			
			Bucket group = table.getBucket(key);
			double result = af.getFunction().aggregate(group);
			System.out.println("\t" + key + ", " + result);
			
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - begin) + " ms");
		
	}
	
}
