package main;

import java.util.ArrayList;

import index.ArrayIndex;
import index.DefaultIndex;
import index.HashIndex;
import index.Index;
import query.Query;
import query.QueryManager;
import recordhash.Loader;
import recordhash.Record;

public class DatabaseManager {

	private DefaultIndex defaultIndex;
	private HashIndex hashIndex;
	private ArrayIndex arrayIndex;
	private boolean indexGenerated;

	public DatabaseManager() {
		
		defaultIndex = new DefaultIndex();
		indexGenerated = false;

	}

	void createIndexes() {
		
		hashIndex = new HashIndex();
		arrayIndex = new ArrayIndex();

		indexGenerated = true;

		Loader.flush();
		
	}

	public void executeQuery(String query_str) {
		
		Loader.flush();

		Query query = QueryManager.makeQuery(query_str);

		Index choosen = null;

		if (!indexGenerated) {
			choosen = defaultIndex;
		} else if (query.getType() == Query.Type.EQUAL || query.getType() == Query.Type.INEQUAL) {
			choosen = arrayIndex;
		} else if (query.getType() == Query.Type.RANGE) {
			choosen = hashIndex;
		}
		
		System.out.println("Results:");
		
		ArrayList<Record> results = query.execute(choosen);
	
		
		if(results != null && results.size() > 0) {
			for(Record r : results) {
				System.out.println("\t" + r);
			}
	
			System.out.println("Index: " + choosen);
			System.out.println("Time: " + query.getQueryDuration());
			System.out.println("Disk Blocks Read: " + Loader.getLoadedBlockCount());
		} else {
			System.out.println("\tNo results found.");
		}
	}
}
