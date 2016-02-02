package Program3v3;

import java.util.ArrayList;

import Program3v3.Directory.File;

//******Adam Brauns - Program 3 & 4*******

public class Shell {
	
	ArrayList<FileEntry> fileEntries = new ArrayList<FileEntry>();
	BinarySearchTree<FileEntry> fileTree = new BinarySearchTree<FileEntry>();
	//Creating directories 
	private Directory root;
	private Directory cwd;
	//getRoot method
	public Directory getRoot() {
		return root;
	}
	//setRoot method
	public void setRoot(Directory root) {
		this.root = root;
	}
	//getCwd method
	public Directory getCwd() {
		return cwd;
	}
	//setCwd
	public void setCwd(Directory cwd) {
		this.cwd = cwd;
	}
	//mkdir method
	public void mkdir(String strDir){
		String[] array = strDir.split("[/]");  //split the input
		try{
			Directory temp = cwd;
			for(int i = 0; i < array.length-1; i++){
				temp = temp.findSubDirectory(array[i]);  //going down the tree
			}
			temp.addDir(new Directory(array[array.length-1])); //adding a new directory to the temp
		}catch(Exception e){
			System.out.println("mkdir: cannot create directory '"+strDir+"': No file or directory."); //catch failure and print message
		}
	}
	//mkdirSpaces method
	public void mkdirSpaces(String strDir){
		String newStr = strDir.substring(6,strDir.length());  //creating new string
		String[] array = newStr.split("[\\s/]"); //split new string 
		for(int i = 0; i<array.length;i++){
			root.addDir(new Directory(array[i]));  //adding directories to the root.
		}
	}
	//mkdirp method
	public void mkdirp(String strDir){		
		String[] array = strDir.split("[/]");   //splitting the string
		Directory temp = cwd;
		for(int i = 0; i < array.length; i++){
			if(temp.findSubDirectory(array[i])==null){
				temp.addDir(new Directory(array[i]));  //if its not found create directory
				temp = temp.findSubDirectory(array[i]);
			}else{
				temp = temp.findSubDirectory(array[i]); //otherwise go to next directory
			}
		}
	}
	//CD method
	public void cd(String strDir){
		if(strDir.equals("..")){
			if(cwd.getParent()==null){
				System.out.println("CD is already at root"); //print message if it cant go any higher
			}else{
				cwd = cwd.getParent(); //Change the directory to the parent
			}
		}else if(strDir.equals("home") || strDir.equals("/")){
			cwd = root;
		}else{
			String[] array = strDir.split("[/]");  //splitting string
			Directory temp = cwd;
			if(array.length==1){
				if(array[0].equals("home")){
					cwd = root; //if user inputs home change it to the home directory
					return; 
				}
			}
			for(int i = 0; i < array.length; i++){
				if(cwd.findSubDirectory(array[i])==null){
					System.out.println("Failed to change directories. Try again.");
					cwd = temp; //setting cwd to what it was before
					return;
				}else{
					cwd = cwd.findSubDirectory(array[i]);  //otherwise change directory
				}
			}//end for
		}//end if else		
	}//end class
	//rmD method
	public void rmD(String strDir){
		String[] array = strDir.split("[/]");  //split string
		Directory temp = cwd;
		for(int i = 0; i < array.length-1; i++){
			if(temp.findSubDirectory(array[i])==null){
				System.out.println("Directory does not exist");  //if its not found print and return
				return;
			}else{
				temp = temp.findSubDirectory(array[i]); //otherwise go through tree
			}
		}		
		try{
			ArrayList<Directory> list = (ArrayList<Directory>) temp.getSubDirectories();
			list.remove(temp.findSubDirectory(array[array.length-1]));  //remove from the list
		}catch(Exception e){
			System.out.println("Directory could not be removed. Try again."); //catch if failed
		}
	}
	public void rmF(String path){
		
		String[] check = path.split("[.]");
		
		if(check[check.length-1].equals("txt")){
			String[] array = path.split("[/]");  //split string
			Directory temp = cwd;
			for(int i = 0; i < array.length-1; i++){
				if(temp.findSubDirectory(array[i])==null){
					System.out.println("Directory does not exist");  //if its not found print and return
					return;
				}else{
					temp = temp.findSubDirectory(array[i]); //otherwise go through tree
				}
			}		
			try{
				ArrayList<File> list = (ArrayList<File>) temp.getFiles();
				list.remove(temp.getFile(array[array.length-1]));  //remove from the list
			}catch(Exception e){
				System.out.println("Text file could not be removed. Try again."); //catch if failed
			}
		}else{	
			System.out.println("rm: cannot remove '"+check[0]+ "': is a directory. " );
		}
	}
	//ls method
	public void lsAlone(){
		cwd.lsAlone();
	}
	//ls path method
	public void lsPath(String path){
		String[] array = path.split("[/]");  //split string
		Directory temp = cwd;
		for(int i = 0; i < array.length; i++){
			if(temp.findSubDirectory(array[i])==null){
				System.out.println("Directory does not exist");  //if its not found print and return
				return;
			}else{
				temp = temp.findSubDirectory(array[i]); //otherwise go through tree
			}
		}
		temp.lsAlone();
	}
	public void lsR(String path){
		String[] array = path.split("[/]");  //split string
		Directory temp = cwd;
		for(int i = 0; i < array.length; i++){
			if(temp.findSubDirectory(array[i])==null){
				System.out.println("Directory does not exist");  //if its not found print and return
				return;
			}else{
				temp = temp.findSubDirectory(array[i]); //otherwise go through tree
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(temp.getName());
		System.out.println(sb.toString()+":");
		temp.lsRec(temp);
	}
	//pwd method
	public String pwd(){ 
		return cwd.getAbsolutePath(cwd);  //call getAbsolutePath method for the cwd
	}
	//edit method
	public String edit(String fileName){
		if(cwd.isInFile(fileName)){
			//file exists
			cwd.editFile(fileName);
		}else{
			//file does not exist
			cwd.makeFile(fileName);
		}
		return null;
	}
	//cat method
	public void cat(String fileName){
		cwd.cat(fileName);
	}
	//search and sort by freq
	public void search(String word){
		ArrayList<WordEntry> list = root.search(word);
		WordEntry[] sort = new WordEntry[list.size()]; //make new array
		int index = 0;
		for(WordEntry item : list){
			sort[index] = item;
			index++;
		}
	    boolean check = true;   // set flag to true to begin first pass
	    WordEntry temp;   //holding variable
	    while ( check ){
	    	check= false;    //set flag to false awaiting a possible swap
	    	for(int j=0;  j < sort.length-1;  j++ ){
	    		if ( sort[ j ].line.size() < sort[j+1].line.size() ){   // change to > for ascending sort
	    			temp = sort[ j ];                //swap elements
	    			sort[ j ] = sort[ j+1 ];
	    			sort[ j+1 ] = temp;
	    			check = true;              //shows a swap occurred  
	    		} 
	    	} 
	    }
	    printEntryArray(sort);
	}
	//print entry method to print them out in order
	public void printEntryArray(WordEntry[] array){
		for(int i = 0; i < array.length; i++){
			System.out.println(array[i].getPath());
		}
	}
	//mv method
	public void mv(String path1, String path2){
		//make sure its a text file
		String[] srcSub = path1.split("[.]");
		if(srcSub.length>1){
			if(srcSub[1].equals("txt")){
				root.mv(path1, path2); //it was a text file
			}else{
				System.out.println("src provided was not a text file. Try again");  //was not a text file try again
			}
		}else{
			System.out.println("src provided was not a text file. Try again");  //was not a text file try again
		}
	}
	//locate method
	public void locate(String fileName){

		//check for either a text file or a directory 
		String[] filCheck = fileName.split("[.]");
		if(filCheck[filCheck.length-1].equals("txt")){
			FileEntry fileEntry = new FileEntry(fileName);  //make new file entry
			FileEntry check = fileTree.find(fileEntry);
			if(check!=null){
				ArrayList<String> list = check.getPath();   //if it is found make a life and print it
				for(String item : list){
					System.out.println(item);
				}
			}
		}else{
			ArrayList<String> test = new ArrayList<String>();    //if it is a directory
			ArrayList<String> list = root.locateDir(fileName, test);
			for(String item : list){
				System.out.println(item);
			}
			
		}
	}
	//updating the filetree db
	public void updatedb(){	
		fileTree = new BinarySearchTree<FileEntry>();  //make a new binary search tree
		fileEntries = new ArrayList<FileEntry>(); //make a new file entry arraylist
		root.updatedb(fileTree, fileEntries);  
	}
}//end class
