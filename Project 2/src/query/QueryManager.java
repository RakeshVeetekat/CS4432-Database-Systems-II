package query;

public class QueryManager {
	
	public static Query makeQuery(String string) {
		
		string = string.toLowerCase();
		
		String condition = string.substring(string.indexOf("where") + 5);
		
		Query query = new Query();
		
		if(condition.contains("randomv != ")) {
			query.setConditionInequal(Integer.parseInt(condition.replace("randomv != ", "").replaceAll("\\s+", "")));
		}	else if(condition.contains("randomv = ")) {
			query.setConditionEqual(Integer.parseInt(condition.replace("randomv = ", "").replaceAll("\\s+", "")));
		}
		
		return query;

	}
}
