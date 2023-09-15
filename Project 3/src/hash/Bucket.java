package hash;

import java.util.ArrayList;
import data.JoinCondition;
import data.Record;

public class Bucket extends ArrayList<Record> {

	private static final long serialVersionUID = 1L;

	public Bucket() {
		super();
	}
	
	public Bucket meetsJoinCondition(JoinCondition cond, Record r2) {
		
		Bucket subBucket = new Bucket();
		
		for(Record r1 : this) {
			if(cond.meets(r1, r2)) {
				subBucket.add(r1);
			}
		}
		
		return subBucket;
		
	}
	
}
