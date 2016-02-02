package Program3v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Program3v3.Directory.File;

//******Adam Brauns - Program 3 & 4*******

public class Directory {
	
	//Declaring variables
	private List<File> files;
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	private List<Directory> subDirectories;
	private Directory parent;
	private String name;
	//File class
	public class File implements Comparable<Object>{
		private BinarySearchTree<WordEntry> wordTree;
		private String fileName;
		private String content;
		private Directory parent;
		//getters and setters
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getName() {
			return fileName;
		}
		public void setName(String fileName) {
			this.fileName = fileName;
		}
		//constructors 
		public File(String fileName){
			this.fileName=fileName;
			this.wordTree = new BinarySearchTree<WordEntry>();
		}
		public File(String fileName, Directory parent){
			this.fileName=fileName;
			this.wordTree = new BinarySearchTree<WordEntry>();
			this.parent = parent;
		}
		public File(String fileName, String content, Directory parent){
			this.fileName=fileName;
			this.content = content;
			this.wordTree = new BinarySearchTree<WordEntry>();
			this.parent = parent;
			populateTree(content);
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		//populate tree to start
		public void populateTree(String fileName) {
			ArrayList<WordEntry> entries = new ArrayList<WordEntry>();
			try {
				String[] splitLine = fileName.split("[\\s\\W]");
				int lineNumber = 1;
				boolean added = false;
				for (int i = 0; i < splitLine.length; i++) {
					added = false;
					WordEntry temp = new WordEntry(splitLine[i], new ArrayList<Integer>(), getFileName(), pwdString(parent));
					if (entries.size() != 0) {
						for (int j = 0; j < entries.size(); j++) {
							if (entries.get(j).compareTo(temp) == 0) {
								entries.get(j).getLine().add(lineNumber);
								added = true;
							} // end if
						} // end for j loop
						if (!added) {
							temp.getLine().add(lineNumber);
							entries.add(temp);
							wordTree.add(temp);
						} // end if
					} else {
						temp.getLine().add(lineNumber);
						entries.add(temp);
						wordTree.add(temp);
					} // end else
					lineNumber++;
				} // end for i loop
			} catch (Exception e) {
				System.out.println("Unable to create tree");
			}
		}
		//used to search
		public WordEntry search(String word){
			WordEntry wordWrapper = new WordEntry(word, new ArrayList<Integer>(), getFileName(), pwdString(parent));
			return wordTree.find(wordWrapper);
		}
		//comparing file names
		@Override
		public int compareTo(Object o) {
			if (o.getClass() != this.getClass())
				return -1;
			
			if (((File) o).getFileName().compareToIgnoreCase(this.getFileName()) == 0)
				return 0;
			if (((File) o).getFileName().compareToIgnoreCase(this.getFileName()) == 1)
				return 1;
			else
				return -1;
		}
		//returns abs path
		public String pwdString(Directory dir){
			StringBuilder sb = new StringBuilder();  //creating string builder
			sb.insert(0, getFileName());
			sb.insert(0, "/");
			while(dir.getParent() != null){  //building string builder
				sb.insert(0, dir.getName());
				sb.insert(0, "/");
				dir = dir.getParent();
			}
			sb.insert(0,dir.getName());
			return sb.toString();
		}
		//prints abs path
		public void pwd(){
			Directory dir = getParent();
			StringBuilder sb = new StringBuilder();  //creating string builder
			sb.insert(0, getFileName());
			sb.insert(0, "/");
			while(dir.getParent() != null){  //building string builder
				sb.insert(0, dir.getName());
				sb.insert(0, "/");
				dir = dir.getParent();
			}
			sb.insert(0,dir.getName());
			System.out.println(sb.toString());
		}
		
	}//End File
	public Directory(String name){
		this.name = name;
		this.subDirectories = new ArrayList<Directory>();
		this.files = new ArrayList<File>();
	}
	//Directory constructor name and parent
	public Directory(String name, Directory parent) {
		this.name = name;
		this.parent = parent;
		this.subDirectories = new ArrayList<Directory>();
		this.files = new ArrayList<File>();
	}
	//addDir method
	public void addDir(Directory subDir) {
		boolean inside = false;  //variable used to see if its inside it
		for (Directory dir : subDirectories) {
			if(dir.getName().equals(subDir.getName())){
				inside = true;  //if its found set to true
			}
		}
		if(!inside){
			subDir.setParent(this);  //set parent
			subDirectories.add(subDir);	 //add it to directory
		}else{
			System.out.println("Directory already exists");
		}
	}
	//getName method
	public String getName(){
		return name;
	}
	//removeDirectory method
	public void removeDirectories() {
		this.subDirectories.clear();
	}
	//getParent method
	public Directory getParent() {
		return this.parent;
	}
	//setParent method
	public void setParent(Directory parent) {
		this.parent = parent;
	}
	//getSubDirectories method
	public List<Directory> getSubDirectories() {
		return subDirectories;
	}
	//setSubDirectories method
	public void setSubDirectories(List<Directory> subDirectories) {
		this.subDirectories = subDirectories;
	}
	//getAbsolutePath method
	public String getAbsolutePath(Directory dir) {
		StringBuilder sb = new StringBuilder();  //creating string builder
		while(dir.getParent() != null){  //building string builder
			sb.insert(0, dir.getName());
			sb.insert(0, "/");
			dir = dir.getParent();
		}
		sb.insert(0,dir.getName());
		return sb.toString();
	}
	//findSubDirectory method
	public Directory findSubDirectory(String strDir){
		ArrayList<Directory> subDs = (ArrayList<Directory>) getSubDirectories(); 
		for (Directory item : subDs) {
			if(item.getName().equals(strDir)){
				return item;  //return the directory
			}
		}
		return null; //otherwise return null
	}
	//checking if its in the file
	public boolean isInFile(String fileName){
		ArrayList<File> list = (ArrayList<File>) files;
		for(File item : list){
			if(item.getName().equals(fileName)){
				return true;    //return true if its found
			}
		}
		return false;
	}
	//making the file
	public void makeFile(String fileName){
		Scanner s = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		boolean check = true;
		while (check){
            String input = s.nextLine();
            if (input.contains("<<EOF>>")){
            	sb.append(input);
            	sb.append("\n");
            	check = false;    //break when <<EOF>> is found
            }else{
            	 sb.append(input);
            	 sb.append("\n");
            }
        }
		//getting rid of the <<EOF>>
		String content = sb.toString();
		String[] eofRem = content.split("[<<EOF>>]");
		File file = new File(fileName, eofRem[0], this);
		files.add(file);
		
	}
	//editing the text file
	public void editFile(String fileName){
		File file = getFile(fileName);
		System.out.println("Old String contents: "+"\n\n"+file.content+"\n"+"Replace with: "+"\n");
		files.remove(file);  //remove then create new file
		makeFile(fileName);
	}
	//getting text file
	public File getFile(String fileName){
		ArrayList<File> list = (ArrayList<File>) files;
		for(File item : list){
			if(item.getName().equals(fileName)){
				return item;   //return the file if found otherwise return null
			}
		}
		return null;
	}
	//cat method
	public void cat(String fileName){
		try{
			File file = getFile(fileName);
			System.out.println(file.content); //print content
			System.out.println();
		}catch(Exception e){
			System.out.println("File not found to print contents");
		}
	}
	//ls alone
	public void lsAlone(){
		ArrayList<Directory> test = (ArrayList<Directory>) getSubDirectories();
		if(test.size()!=0)
			for(Directory item: test)
				System.out.print(item.getName() + '\t'); //print out all the names
		
		System.out.println();
		ArrayList<File> test2 = (ArrayList<File>) files;
		if(test2.size()!=0)
			for(File item: test2)
				System.out.print(item.getName() + '\t'); //print out all the names
		System.out.println();
	}
	//mv method
	public void mv(String path1, String path2){
	
		String[] array = path1.split("[/]");  //split string
		Directory temp = this;
		for(int i = 0; i < array.length-1; i++){
			if(temp.findSubDirectory(array[i])==null){
				System.out.println("Directory does not exist");  //if its not found print and return
				return;
			}else{
				temp = temp.findSubDirectory(array[i]); //otherwise go through tree
			}
		}		
		try{
			if(temp.getFile(array[array.length-1])==null){
				System.out.println(array[array.length-1]+" Does not exist. Try again.");
			}
			File file = temp.getFile(array[array.length-1]);
			String[] array2 = path2.split("[/]");
			Directory temp2 = this; 
			for(int i = 0; i < array2.length; i++){
				if(temp2.findSubDirectory(array2[i])==null){
					System.out.println("Directory does not exist");  //if its not found print and return
					return;
				}else{
					temp2 = temp2.findSubDirectory(array2[i]); //otherwise go through tree
				}
			}	
			ArrayList<File> list = (ArrayList<File>) temp2.getFiles();
			ArrayList<File> list2 = (ArrayList<File>) temp.getFiles();
			for(File item: list){
				if(item.getName().equals(file.getName())){
					//if it is found
					list.remove(item);
					list.add(file);
					list2.remove(file);
					return;
				}
			}
			//otherwise
			list.add(file);	
			list2.remove(file);
		}catch(Exception e){
			System.out.println("Directory could not be removed. Try again."); //catch if failed
		}
	}
	//used in search
	public ArrayList<WordEntry> search(String word){
		ArrayList<WordEntry> entryList = new ArrayList<WordEntry>();
		for(Directory item: subDirectories){
			entryList.addAll(item.search(word)); //go through all directories
		}
		//go through all files
		for(File item: files){
			WordEntry E = item.search(word);
			if(E!=null){
				entryList.add(E);
			}
		}
		return entryList;
	}
	//used in locate
	public ArrayList<FileEntry> locate(String fileName, BinarySearchTree<FileEntry> fileTree){
		ArrayList<FileEntry> list = new ArrayList<FileEntry>();
		for(Directory item: subDirectories){
			item.locate(fileName, fileTree); //go through all directories
		}
		//go through all files
		for(File item: files){
			FileEntry test = new FileEntry(item.getFileName());
			FileEntry E = fileTree.find(test);
			if(E!=null){
				list.add(E);
			}
		}
		return list;
	}
	//updatedb method
	public void updatedb(BinarySearchTree<FileEntry> tree, ArrayList<FileEntry> fileEntry){
		for(Directory item : subDirectories){
			item.updatedb(tree, fileEntry); //go through all directories
		}
		for(File item : files){
			FileEntry file = new FileEntry(item.getName()); //go through all files
			try {
				boolean added = false;
				if (fileEntry.size() != 0) {
					for (FileEntry item2 : fileEntry) {
						if (item2.compareTo(file) == 0) {
							item2.getPath().add(item.pwdString(item.parent));
							added = true;
						} // end if
					} // end for j loop
					if (!added) {
						file.getPath().add(item.pwdString(item.parent));
						fileEntry.add(file);
						tree.add(file);
					} // end if
				} else {
					file.getPath().add(item.pwdString(item.parent));
					fileEntry.add(file);
					tree.add(file);
				} // end else
			} catch (Exception e) {
				System.out.println("Unable to create tree");
			}			
		}
	}
	//lsRec method
	public void lsRec(Directory start){
		for(Directory item : subDirectories){
			System.out.println(item.getName()); //go through all directories
		}
		for(Directory item : subDirectories){
			item.lsRecHelper(start);
			item.lsRec(start);
		}	
	}
	//getting the path for lsRec
	public void lsRecHelper(Directory start){	
		Directory temp = this;
		StringBuilder sb = new StringBuilder();
		while(!temp.equals(start)){
			sb.insert(0, temp.getName());
			sb.insert(0, "/");
			temp = temp.getParent(); 
		}
		sb.insert(0, start.getName());
		sb.append(":");
		System.out.println(sb.toString());
	}
	//locateDir method returns a list of all the direcotries
	public ArrayList<String> locateDir(String name, ArrayList list){
		for(Directory item : subDirectories){
			if(item.getName().equals(name)){
				list.add(item.getAbsolutePath(item));
			}
			item.locateDir(name, list); // for all directories
		}
		return list;
	}
}//END CLASS
