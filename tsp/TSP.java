package tsp;

import java.util.Random;

import graph.*;

public class TSP {
	public int antCount, locationCount, iterations;
	
	TspGui tspGui;
	private Random r;
	private Graph map;
	
	private Colony colony;
	private Ant overallFittest = null;
	
	public TSP(TspGui tspGui, Random r) {
		this.tspGui = tspGui;
		this.r = r;
		
		this.antCount = 100;
		this.iterations = 0;
		this.locationCount = 50;
		
		initMap();
		
		this.colony = new Colony(this.map);
		
		run();
	}
	
	public void run() {	
		for (iterations = 0; iterations < 1000; iterations++) {
			Ant iterationFittest = iterate();
			
			System.out.print("Iteration fittest ");
			System.out.println(iterationFittest);
			
			tspGui.draw(overallFittest, iterationFittest);
		}
			
		System.out.print("Fittest ant ");
		System.out.println(overallFittest);
	}

	public Ant iterate() {	
		this.colony.init(this.antCount);
		
		this.colony.travel();
		
		this.colony.update();
		
		Ant iterationFittest = colony.fittest();
		
		if (overallFittest == null) {
			overallFittest = iterationFittest;
        } else if (iterationFittest.fitness < overallFittest.fitness) {
        	overallFittest = iterationFittest;
        }
		
		return iterationFittest;
	}
	
	private void initMap() {
		Graph map = new Graph();
		Vertex[] locations = new Vertex[this.locationCount];
		
		for (int i = 0; i < this.locationCount; i++) {
			int x = randomBetween(10, 1270);
			int y = randomBetween(10, 710);
			Vertex location = new Vertex(x, y);
			locations[i] = location;
			map.addVertex(location);
		}

        for (Vertex v : map) {
            for (int i = 0; i < this.locationCount; i++) {
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
	
	
    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
