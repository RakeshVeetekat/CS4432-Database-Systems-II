package data;

@FunctionalInterface
public interface JoinCondition {
	boolean meets(Record r1, Record r2);
}
