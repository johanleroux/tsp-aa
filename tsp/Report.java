package tsp;

import java.io.*;

public class Report {
	public void write() {
		File dataFolder = new File("data");
		
	    if (!dataFolder.exists()) {
	    	new File("data").mkdirs();
	    }
	    
	    int counter = 1;
	    
	    while(true) {
	    	String fileName = String.format(
				"data/test_%.2f_%.2f_%.2f_%.2f_%d.csv",
				Configuration.alpha,
				Configuration.beta,
				Configuration.evaporation,
				Configuration.pheromone,
				counter
			);
		    
	    	File file = new File(fileName);
		    
		    if (file.exists() && !file.isDirectory()) {
			    counter++;
			    continue;
		    }
		    
		    try {
		        PrintWriter writer = new PrintWriter(file); 
		  
		        writer.println("The first line");
		        writer.println("The second line");
		        writer.close();
		        break;
		    } 
		    catch (IOException e) {
		        e.printStackTrace(); 
		    }
	    }
	}
}