package index;

public class IndexPointer {
	
	private int fileNumber;
	private int recordNumber;
	
	public IndexPointer(int fileNumber, int recordNumber) {
		this.fileNumber = fileNumber;
		this.recordNumber = recordNumber;
	}
	
	public int getFileNumber() {
		return fileNumber;
	}
	public int getRecordNumber() {
		return recordNumber;
	}

}
