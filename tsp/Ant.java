package tsp;

import java.util.*;
import graph.*;

public class Ant {
    private Graph map;
    private Node current;
    private HashSet<Node> tabuList;
    private ArrayList<Node> route;
	private int fitness = 0;
    
    public Ant (Graph map) {
        this.map = map;
    }

    public void travel () {
        current = this.map.getVertices()[0];
        tabuList = new HashSet<>();
        route = new ArrayList<>();
        
    	while (!destinationReached()) {
	        if (map.getTotalVertices() == route.size()) {
	            route.add(route.get(0));
	            return;
	        }
	
	        Edge e = nextRoute();
	        tabuList.add(e);
	        route.add(e);
	        current = e;
    	}
    }

    public boolean destinationReached () {   	
        return map.getTotalVertices() + 1 == route.size();
    }

    public Node[] getTour () {
        if (!destinationReached()) {
            throw new IllegalStateException("Cannot return an incomplete tour.");
        }

        Node[] nodes = new Node[route.size()];

        for (int i = 0; i < route.size(); i++) {
            nodes[i] = route.get(i);
        }

        return nodes;
    }

    public double fitness () {
        if (fitness != 0) return fitness;

        int eval = 0;

        for (int i = 1; i < route.size(); i++) {
            eval += route.get(i).distanceTo(route.get(i-1));
        }

        fitness = eval;
        return eval;
    }

    public Edge nextRoute () {
        ArrayList<Pair<Edge, Double>> probabilities = probabilities();
        double r = new Random().nextDouble();

        for (Pair<Edge, Double> pair : probabilities) {
            if (r <= pair.item2) {
                return pair.item1;
            }
        }

        throw new AssertionError("No Edge could be selected.");
    }

    private ArrayList<Pair<Edge, Double>> probabilities () {
        double denominator = denominator();
        ArrayList<Pair<Edge, Double>> probabilities = new ArrayList<>(validEdges());

        for (Edge e : map.getVertex(current)) {
            if (tabuList.contains(e)) continue;
            Pair<Edge, Double> pair = new Pair<>();

            if (probabilities.size() == 0) {
                pair.item2 = desirability(e) / denominator;
            } else {
                int i = probabilities.size() - 1;
                pair.item2 = probabilities.get(i).item2 + desirability(e) / denominator;
            }

            pair.item1 = e;
            probabilities.add(pair);
        }

        return probabilities;
    }

    private int validEdges () {
        int i = 0;
        for (Edge e : map.getVertex(current)) {
            if (!tabuList.contains(e)) {
                i++;
            }
        }
        return i;
    }

    private double denominator () {
        double denominator = 0.0;
        for (Edge e : map.getVertex(current)) {
            if (tabuList.contains(e)) continue;
            denominator += desirability(e);
        }
        return denominator;
    }

    private double desirability (Edge e) {
        double pheromone = Math.pow(e.pheromone, Configuration.alpha);
        double distance = current.distanceTo(e);
        double distanceValue = Math.pow(1/distance, Configuration.beta);
        return pheromone * distanceValue;
    }

    private static class Pair<T, E> {
        T item1;
        E item2;
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append(fitness + " ");
        boolean flag = false;
        for (Node node : route) {
            if (flag) sb.append(" -> ");
            flag = true;
            sb.append("{ " + node.getX() + " - " + node.getY() + " }");
        }

        return new String(sb);
    }
}
