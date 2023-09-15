package hash;

import data.Record;

@FunctionalInterface
public interface KeyGenerator {
	String make(Record record);
}
