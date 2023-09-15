package query;

import data.Record;

public enum AggregationFunctionType {
	
	SUM_RANDOMV((bucket) -> {
		
		double sum = 0;
		for(Record record : bucket) { 
			sum += record.getRandomValue(); 
		}
		return sum;
		
	}), 
	
	AVG_RANDOMV((bucket) -> {
		
		double sum = 0;
		for(Record record : bucket) { 
			sum += record.getRandomValue(); 
		}
		return sum / bucket.size();
		
	});
	
	private AggregationFunction function;
	
	private AggregationFunctionType(AggregationFunction function) {
		this.function = function;
	}
	
	public AggregationFunction getFunction() { 
		return this.function; 
	};
	
}
