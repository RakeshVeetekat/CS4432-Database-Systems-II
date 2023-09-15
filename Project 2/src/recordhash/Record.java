package recordhash;

public class Record {

	public final static int RECORD_SIZE = 40;
	
	private int randomValue;
	private String address;
	private String name;
	private int fileNumber;
	private int recordNumber;
	
	public Record(int fileNumber, int recordNumber, int randomValue, String address, String name) {
		
		this.fileNumber = fileNumber;
		this.recordNumber = recordNumber;
		this.randomValue = randomValue;
		this.address = address;
		this.name = name;
		
	}
	
	public static Record parseRecord(String query_string) {
		
 		String[] parts = query_string.split(", ");
		
		int fileNumber = Integer.parseInt(parts[0].substring(1, 3));
		int recordNumber = Integer.parseInt(parts[0].substring(7));
		
		String name = parts[1].substring(0, parts[1].length() - 3);
		String address = parts[2].substring(0, parts[2].length() - 3);
		
		int randomValue = Integer.parseInt(parts[3].substring(0, parts[3].indexOf(".")));
		
		return new Record(fileNumber, recordNumber, randomValue, address, name);
		
	}
	
	public static String padNumber(int number, int length) {
		return String.format("%0" + length + "d", number);
	}
	
	public int getFileNumber() { 
		return fileNumber; 
	}
	
	public int getRecordNumber() { 
		return recordNumber; 
	}
	
	public int getRandomValue() { 
		return randomValue;
	}
	
	public String toString() {
		
		String pFN = padNumber(fileNumber, 2);
		String pRN = padNumber(recordNumber, 3);
		
		return "F" + pFN
		+ "-Rec" + pRN + ", " + name +pRN+ ", " + address + pRN + ", " + padNumber(randomValue, 4) + "...";
		
	}
	
}
