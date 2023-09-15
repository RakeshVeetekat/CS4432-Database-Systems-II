package dataset;

public enum Dataset {
	
	A("Project3Dataset-A", 99, 100), B("Project3Dataset-B", 99, 100);
	
	private String dir;
	private int numFiles, recPerFile;

	private Dataset(String dir, int numFiles, int recPerFile) {
		this.dir = dir;
		this.numFiles = numFiles;
		this.recPerFile = recPerFile;
	}
	
	public String getDirectory() { 
		return this.dir;
	}
	
	public int getNumFiles() { 
		return this.numFiles; 
	}
	
	public int getRecordsPerFile() { 
		return this.recPerFile; 
	}
	
}
