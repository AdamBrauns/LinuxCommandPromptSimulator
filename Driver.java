package Program3v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//******Adam Brauns - Program 3*******

public class Driver {
	//Main class
	public static void main(String[] args) {
			Shell shell = new Shell(); 	//creating shell object
			Directory back = new Directory("home");		//creating root
			shell.setCwd(back);		//setting both the root and the current working dir
			shell.setRoot(back);
			System.out.println("Welcome to Fake UNIX."+'\n');
			boolean menu = true;	//keep running until menu is false
			Scanner s = new Scanner(System.in);		//making scanner
			while(menu){	
				System.out.print("$");
				String input = s.nextLine();
				String[] first = input.split("[\\s]");	//array splitting user input
				
				//MKDIR FUNCTION
				if(first[0].equals("mkdir")){
					if(first.length==2){
						shell.mkdir(first[1]);		//calling the mkdir method
					}else if(first.length==3){
						if(first[1].equals("-p")){
							shell.mkdirp(first[2]);	//calling mkdirp method
						}else{
							shell.mkdirSpaces(input);	//calling the mkdirSpaces method
						}
					}else{
						shell.mkdirSpaces(input);	//calling the mkdirSpaces method
					}
					
				//RM FUNCTION
				}else if(first[0].equals("rm")){ 
					if(first.length==3){
						if(first[1].equals("-r")){
							shell.rmD(first[2]); 	//remove directory method
						}
					}else if(first.length==2){
						//IMPLEMENT FOR PROGRAM 4
						if(first.length==2){
							shell.rmF(first[1]);
						}
					}
				//MV FUNCTION	
				}else if(first[0].equals("mv")){ 
					if(first.length==3){
						shell.mv(first[1], first[2]);
					}
				//CD FUNCTION
				}else if(first[0].equals("cd")){
					
					if(first.length==2)
						shell.cd(first[1]);  //calling the cd method
					else
						System.out.println("Invalid cd command. Try again");
					
				//PWD FUNCTION
				}else if(first[0].equals("pwd")){
					
					System.out.println(shell.pwd());  //print the pwd 
					
				}else if(first[0].equals("ls")){
					if(first.length==1){
						shell.lsAlone();   //call the ls method
					}else if(first.length>1){
						if(first[1].equals("-r")){
							for(int i = 2; i<first.length; i++){
								shell.lsR(first[i]);
							}	
						}else{
							for(int i = 1; i<first.length; i++){
								shell.lsPath(first[i]); //call the ls path method
							}
						}
					}else{
						System.out.println("Invalid ls command. Please try again.");
					}
				//EDIT FUNCTION
				}else if(first[0].equals("edit")){   
					if(first.length==2){
						shell.edit(first[1]);
					}
				//CAT FUNCTION	
				}else if(first[0].equals("cat")){
					for(int i = 1; i < first.length; i++){
						shell.cat(first[i]);
					}
				//UPDATEDB FUNCTION	
				}else if(first[0].equals("updatedb")){
					shell.updatedb();
				//LOCATE FUNCTION	
				}else if(first[0].equals("locate")){
					if(first.length==2){
						shell.locate(first[1]);
					}
				//SEARCH FUNCTION	
				}else if(first[0].equals("search")){ 
					
					//NOT WORKING YET
					for(int i = 1; i < first.length; i++){
						shell.search(first[i]);
					}
				//EXIT FUNCTION	
				}else if(first[0].equals("exit")){ 	
					System.out.println("Good-bye!");
					menu = false;   //change menu to false ending program
				}
			} // End menu while loop
	} //end main
} //end driver