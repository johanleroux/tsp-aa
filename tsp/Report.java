package tsp;

import java.io.*;
import java.util.ArrayList;

public class Report {
	ArrayList<Double> overall_bests, iteration_bests, averages;

	public Report() {
		overall_bests = new ArrayList<Double>();
		iteration_bests = new ArrayList<Double>();
		averages = new ArrayList<Double>();
	}

	public void clear() {
		overall_bests.clear();
		iteration_bests.clear();
		averages.clear();
	}

	public void addIteration(double overall, double iteration, double avg) {
		overall_bests.add(overall);
		iteration_bests.add(iteration);
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

				writer.println("gen,best,iteration,avg");
				
				for (int i = 2; i < averages.size(); i++) {
					String line = String.format(
						"%d,%.4f,%.4f,%.4f",
						i,
						overall_bests.get(i),
						iteration_bests.get(i),
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