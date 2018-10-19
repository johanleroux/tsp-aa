package tsp;

import java.util.*;
import graph.*;

public class Ant {
    private Graph map;
    private Node current;
    private HashSet<Node> tabuList;
    private ArrayList<Node> route;
	public int fitness;
    
    public Ant (Graph map) {
        this.map = map;
        
        // Set first location on map as current position
        current = this.map.getVertices()[0];
        tabuList = new HashSet<>();
        route = new ArrayList<>();
    }

    /**
     * Select a random vertex from the graph.
     * @return      a random vertex
     */
    private Vertex getRandomVertex () {
        int r = new Random().nextInt(map.getTotalVertices());
        Iterator<Vertex> it = map.iterator();
        for (int i = 0; i < r; i++) {
            it.next();
        }
        return it.next();
    }

    /**
     * Allow the Ant to travel to the next Vertex.
     */
    public void travel () {

        if (!notFinished()) {
            throw new IllegalStateException("Cannot travel since the tour is complete.");
        }

        // If there are no more Edges left, add the first one to the end.
        if (map.getTotalVertices() == route.size()) {
            route.add(route.get(0));
            return;
        }

        Edge e = nextEdge();
        tabuList.add(e);
        route.add(e);
        current = e;
    }

    /**
     * Check if the Ant has made a complete tour around the graph.
     * The number of vertices in the tour should be one greater than
     * the total vertices in the graph as the Ant has to come back
     * to its starting position at the end.
     * @return      true if it has made a complete tour
     */
    public boolean notFinished () {   	
        return map.getTotalVertices() + 1 != route.size();
    }

    /**
     * Get the completed tour that the ant travelled.
     * @return      the tour that the Ant travelled
     */
    public Node[] getTour () {
        if (notFinished()) {
            throw new IllegalStateException("Cannot return an incomplete tour.");
        }

        Node[] nodes = new Node[route.size()];

        for (int i = 0; i < route.size(); i++) {
            nodes[i] = route.get(i);
        }

        return nodes;
    }

    /**
     * Get the evaluation of the completed tour.
     * @return      the sum of the total distances
     */
    public double fitness () {
        int eval = 0;

        for (int i = 1; i < route.size(); i++) {
            eval += Graph.getDistance(route.get(i), route.get(i-1));
        }

        this.fitness = eval;
        return eval;
    }

    /**
     * Get the Edge that the ant should travel to next. Accounts for both
     * the pheromones and distances of all the edges.
     * @return      the Edge to travel across
     */
    public Edge nextEdge () {
        ArrayList<Pair<Edge, Double>> probabilities = probabilities();
        double r = new Random().nextDouble();

        for (Pair<Edge, Double> pair : probabilities) {
            if (r <= pair.item2) {
                return pair.item1;
            }
        }

        throw new AssertionError("No Edge could be selected.");
    }

    /**
     * Get the probabilities of all the edges and return them in an array.
     * Example: if there are 4 edges, each with a probability of 0.25, then
     * the array list will contain [0.25, 0.50, 0.75, 1.00].
     * @return      the probabilities of each edge
     */
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

    /**
     * Get the number of Edges that are valid from the current position of the ant.
     * Edges that have been travelled to previously are invalid.
     * @return      the quantity of valid edges
     */
    private int validEdges () {
        int i = 0;
        for (Edge e : map.getVertex(current)) {
            if (!tabuList.contains(e)) {
                i++;
            }
        }
        return i;
    }

    /**
     * Calculate the denominator for the formula that determines the probability
     * of an ant moving from the current location to another.
     * @return      the sum of all the probabilities of each edge
     */
    private double denominator () {
        double denominator = 0.0;
        for (Edge e : map.getVertex(current)) {
            if (tabuList.contains(e)) continue;
            denominator += desirability(e);
        }
        return denominator;
    }

    /**
     * Calculate the pheromone on Edge e, to the power of alpha.
     * Calculate the desirability of Edge e based on distance, to the power of beta.
     * Get the product of the two results.
     * @param e     the Edge to perform the calculations on
     * @return      the desirability of the Edge
     */
    private double desirability (Edge e) {
        double pheromone = Math.pow(e.getPheromone(), 1); // Alpha
        double distance = Graph.getDistance(current, e);
        double distanceValue = Math.pow(1/distance, 2); // Beta
        return pheromone * distanceValue;
    }

    /**
     * Holds a pair of items.
     * @param <T>   the first item type
     * @param <E>   the second item type
     */
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
