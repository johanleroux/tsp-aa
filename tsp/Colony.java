package tsp;

import graph.Graph;

public class Colony {
	public Ant[] ants;
	
	private Ant fittestAnt;
	private Graph map;
	
	public Colony(Graph map) {
		this.map = map;
	}

	public void init(int antCount) {
		ants = new Ant[antCount];
		
        for (int i = 0; i < antCount; i++) {
            ants[i] = new Ant(map);
        }
	}

	public void travel() {
		fittestAnt = ants[0];

        for (Ant ant : ants) {
            while (ant.notFinished()) {
                ant.travel();
                ant.fitness();
                
                if (ant.fitness < fittestAnt.fitness) 
                	fittestAnt = ant;
            }
        }
	}
	
	public void update() {
		for (Ant ant : ants) {
            map.updatePheromone(ant);
        }
	}

	public Ant fittest() {
		return fittestAnt;
	}
}
