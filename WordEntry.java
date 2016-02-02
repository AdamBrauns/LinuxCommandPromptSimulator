package Program3v3;

//Adam Brauns Program 3 and 4

import java.util.ArrayList;

//Class is used to make word tree
public class WordEntry implements Comparable<Object> {
	
	//declaring variables
	String name;
	String word;
	ArrayList<Integer> line;
	String path;
	//getters and setters
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Integer> getLine() {
		return line;
	}
	public void setLine(ArrayList<Integer> line) {
		this.line = line;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	//constructor
	public WordEntry(String word, ArrayList<Integer> page, String name, String path) {
		super();
		this.name = name;
		this.word = word;
		this.line = page;
		this.path = path;
	}
	//compare to method comparing the words
	public int compareTo(Object arg0) {
		if(arg0.getClass() == String.class){
			if (((String) arg0).compareToIgnoreCase(this.getWord()) == 0)
				return 0;
			if (((String) arg0).compareToIgnoreCase(this.getWord()) == 1)
				return 1;
			else
				return -1;
		}
		
		if (arg0.getClass() != this.getClass())
			return -1;
		if (((WordEntry) arg0).getWord().compareToIgnoreCase(this.getWord()) == 0)
			return 0;
		if (((WordEntry) arg0).getWord().compareToIgnoreCase(this.getWord()) == 1)
			return 1;
		else
			return -1;
	}
	//to string method
	public String toString(){
		return "( " + this.getWord() + ")";
	}
}
