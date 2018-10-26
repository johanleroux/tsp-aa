package tsp;

import java.util.HashSet;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.Vertex;

public class Colony {
	public Ant[] ants;
	
	private Ant fittestAnt;
	private Graph map;
	
	public Colony(Graph map) {
		this.map = map;
	}

	public void init() {
		ants = new Ant[Configuration.antCount];
		
        for (int i = 0; i < Configuration.antCount; i++) {
            ants[i] = new Ant(map);
        }
	}

	public void travel() {
		fittestAnt = ants[0];

        for (Ant ant : ants) {
            ant.travel();
            
            if (ant.fitness() < fittestAnt.fitness()) 
            	fittestAnt = ant;
        }
	}
	
	public void update() {
		for (Ant ant : ants) {
            updatePheromone(ant);
        }
	}
	
	public void updatePheromone(Ant ant) {
        Node[] edges = ant.getTour();

        HashSet<Edge> hashSet = new HashSet<>();

        for (int i = 1; i < edges.length; i++) {
            Edge e1 = map.getVertex(edges[i-1]).getEdge(edges[i]);
            Edge e2 = map.getVertex(edges[i]).getEdge(edges[i-1]);

            double p1 = e1.pheromone;
            double p2 = e2.pheromone;

            hashSet.add(e1);
            hashSet.add(e2);

            e1.pheromone = Configuration.evaporation * p1 + 1.0 / ant.fitness();
            e2.pheromone = Configuration.evaporation * p2 + 1.0 / ant.fitness();
        }

        for (Vertex v : map) {
            for (Edge e : v) {
                if (!hashSet.contains(e)) {
                    double p = e.pheromone;
                    e.pheromone = Configuration.evaporation * p;
                }
            }
        }    
	}

	public Ant fittest() {
		return fittestAnt;
    }
    
    public double averageFitness() {
        double fitness = 0;

        for (Ant ant : ants) {
            fitness += ant.fitness();
        }

        return fitness / ants.length;
    }
}
