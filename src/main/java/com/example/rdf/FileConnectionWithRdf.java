package com.example.rdf;

import java.io.File;
import java.io.FileNotFoundException;

public class FileConnectionWithRdf {
 
	public static void  main(String args[]) throws FileNotFoundException {

	File dir = new File("D:\\opal-graph\\");

	for (File file : dir.listFiles()) {
	    //Scanner s = new Scanner(file);
	    
	    System.out.println("my file content is"+file);
	    
	}
	
	
	
}
}
