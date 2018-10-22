package tsp;

import java.util.Random;
import graph.*;

public class TSP {
	TspGui tspGui;
	private Random r;
	
	private Statistics stats;
	private Graph map;	
	private Colony colony;
	
	public TSP(TspGui tspGui) {
		this.tspGui = tspGui;
		this.r = new Random(523515415);
		initMap();
		
		this.stats = new Statistics();

		while(Configuration.testRun && stats.testRuns < Configuration.testRuns) {
			this.colony = new Colony(this.map);
			
			this.stats.reset();
		
			run();
			
			stats.testRuns++;
		}
	}
	
	public void run() {		
		// Set random pheromone levels
        for (Vertex v : map) {
            for (Edge e : v) {
            	e.setPheromone(0 + (Configuration.pheromone - 0) * r.nextDouble());
            }
        }
        
        while (stats.stuckIterations < 100) {
			iterate();
			
			stats.iterations++;
			
			tspGui.draw(stats);	
        }
			
		System.out.print("Fittest ant ");
		System.out.println(stats.overallBest);
	}

	public void iterate() {
		// Place all ants on origin node
		this.colony.init();
		
		// Let ants travel
		this.colony.travel();
		
		// Update Pheromones		
		this.colony.update();
		
		// Calculate statistics
		stats.iterationBest = colony.fittest();
		
		if (stats.overallBest == null) {
			stats.overallBest = stats.iterationBest;
			stats.stuckIterations = 0;
        } else if (stats.iterationBest.fitness < stats.overallBest.fitness) {
        	stats.overallBest = stats.iterationBest;
        	stats.stuckIterations = 0;
        } else {
        	stats.stuckIterations++;
        }
	}
	
	private void initMap() {
		Graph map = new Graph();
		Vertex[] locations = new Vertex[Configuration.locationCount];
		
		for (int i = 0; i < Configuration.locationCount; i++) {
			int x = randomBetween(10, 1270);
			int y = randomBetween(10, 710);
			Vertex location = new Vertex(x, y);
			locations[i] = location;
			map.addVertex(location);
		}

        for (Vertex v : map) {
            for (int i = 0; i < Configuration.locationCount; i++) {
                if (locations[i] != v) {
                	map.addEdge(v, locations[i]);
                }
            }
        }
        
        this.map = map;
    }
	
	private int randomBetween(int min, int max) {
		return r.nextInt((max - min) + 1) + min;
	}
}
