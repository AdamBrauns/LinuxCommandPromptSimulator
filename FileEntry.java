package Program3v3;

//Adam Brauns program 3 and 4

import java.util.ArrayList;

//Class used to create fileTree
public class FileEntry implements Comparable{

	//variables used
	String fileName;
	ArrayList<String> path;
	
	//getters and setters
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public ArrayList<String> getPath() {
		return path;
	}
	public void setPath(ArrayList<String> path) {
		this.path = path;
	}
	//constructor
	public FileEntry(String fileName){
		super();
		this.fileName=fileName;
		path = new ArrayList<String>();
	}
	//compare to method comparing file names
	public int compareTo(Object o) {
		if (o.getClass() != this.getClass())
			return -1;
		if (((FileEntry) o).getFileName().compareToIgnoreCase(this.getFileName()) == 0)
			return 0;
		if (((FileEntry) o).getFileName().compareToIgnoreCase(this.getFileName()) == 1)
			return 1;
		else
			return -1;
	}
}
