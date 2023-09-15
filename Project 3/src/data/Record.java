package data;

import dataset.Dataset;

public class Record {

	private Dataset dataset;
	private int fileNumber, recordNumber, randomV;
	private String name, address;
	
	public static final int RECORD_STRING_LENGTH = 40;
	
	public Record(Dataset dataset, int fileNumber, int recordNumber, String name, String address, int randomV) {
		this.dataset = dataset;
		this.fileNumber = fileNumber;
		this.recordNumber = recordNumber;
		this.name = name;
		this.address = address;
		this.randomV = randomV;
	}
	
	public String toString() {
		
		String recordNumberString = padZerotoInt(this.recordNumber, 3);
		String identifierString = this.getIdentifierString();
		String nameString = this.getNameString();
		String addressString = this.address + recordNumberString;
		String randomValueString = padZerotoInt(this.randomV, 4);
		
		String output = identifierString + ", " + nameString + ", " + addressString + ", " + randomValueString + "...";
		return output;
		
	}
	
	public int getRandomValue() { 
		return this.randomV; 
	}
	
	public String getIdentifierString() {
		String fileNumberString = padZerotoInt(this.fileNumber, 2);
		String recordNumberString = padZerotoInt(this.recordNumber, 3);
		return dataset.toString() + fileNumberString + "-" + "Rec" + recordNumberString;
	}
	
	public String getNameString() {
		String recordNumberString = padZerotoInt(this.recordNumber, 3);
		return this.name + recordNumberString;
	}
	
	public static String padZerotoInt(int number, int length) {
		return String.format("%0" + length + "d", number);
	}
	
}
