package dataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import data.Record;
import hash.Bucket;

public class DataFileReader {

	private int fileNumber;
	private Dataset dataset;

	public DataFileReader(Dataset dataset, int fileNumber) {
		this.fileNumber = fileNumber;
		this.dataset = dataset;
	}

	public String getLocalPath() {
		return this.dataset.getDirectory() + "/" + dataset.toString() + this.fileNumber + ".txt";
	}

	public Record getRecord(int recordNumber) {

		try {
			
			File file = new File(getLocalPath());
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);

			int recordStringLength = Record.RECORD_STRING_LENGTH;
			int recordStringOffset = (recordNumber - 1) * recordStringLength;
			char input[] = new char[recordStringLength];

			br.skip(recordStringOffset);
			br.read(input, 0, recordStringLength);
			br.close();
			return makeRecord(input);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Record makeRecord(char input[]) {

		Dataset dataset = Dataset.valueOf(input[0] + "");

		int fileNum = Integer.parseInt(new String(input, 1, 2));
		int recordNum = Integer.parseInt(new String(input, 7, 3));
		String name = new String(input, 12, 4);
		String address = new String(input, 21, 7);
		int randomV = Integer.parseInt(new String(input, 33, 4));
		
		Record record = new Record(dataset, fileNum, recordNum, name, address, randomV);

		return record;
		
	}
	
	
	public Bucket getAllRecords() {
		
		Bucket bucket = new Bucket();
		
		for(int x = 1; x <= dataset.getRecordsPerFile(); x++) {
			Record record = getRecord(x);
			bucket.add(record);
		}
		
		return bucket;
		
	}

}
