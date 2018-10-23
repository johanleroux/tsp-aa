package tsp;

import java.io.*;
import java.util.ArrayList;

public class Report {
	ArrayList<Double> bests, averages;

	public Report() {
		bests = new ArrayList<Double>();
		averages = new ArrayList<Double>();
	}

	public void clear() {
		bests.clear();
		averages.clear();
	}

	public void addIteration(double best, double avg) {
		bests.add(best);
		averages.add(avg);
	}

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
				
				for (int i = 0; i < bests.size(); i++) {
					String line = String.format(
						"%d,%.4f,%.4f",
						i,
						bests.get(i),
						averages.get(i)
					);
					writer.println(line);
				}
				writer.close();
				clear();
		        break;
		    } 
		    catch (IOException e) {
		        e.printStackTrace(); 
		    }
	    }
	}
}