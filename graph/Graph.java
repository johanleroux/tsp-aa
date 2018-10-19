package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import tsp.Ant;

public class Graph implements Iterable<Vertex> {
	private HashMap<Integer, Vertex> hashMap;
    private ArrayList<Vertex> list;
    private int totalEdges;
    
    public Graph() {
        clear();
    }

    public int getTotalVertices () {
        return hashMap.size();
    }

    public int getTotalEdges () {
        return totalEdges;
    }

    public boolean isEmpty () {
        return hashMap.isEmpty();
    }

    public void clear () {
        hashMap = new HashMap<>();
        list = new ArrayList<>();
        totalEdges = 0;
    }

    public void addVertex (Vertex vertex) {
        hashMap.put(vertex.hashCode(), vertex);
        list.add(vertex);
    }

    public Vertex getVertex (Node node) {
        return hashMap.get(node.hashCode());
    }

    public void addEdge (Vertex vertex, Node node) {
        vertex.addEdge(node);
        totalEdges++;
    }

    public Node[] getVertices () {
        Node[] nodes = new Node[getTotalVertices()];
        int i = 0;
        for (Vertex v : this) {
            nodes[i++] = v;
        }
        return nodes;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return list.iterator();
    }
    
    public void updatePheromone(Ant ant) {

        double eval = ant.fitness;

        double probability = (1 - 0.1);

        Node[] edges = ant.getTour();

        HashSet<Edge> hashSet = new HashSet<>();

        for (int i = 1; i < edges.length; i++) {
            Edge e1 = getVertex(edges[i-1]).getEdge(edges[i]);
            Edge e2 = getVertex(edges[i]).getEdge(edges[i-1]);

            // The pheromones.
            double p1 = e1.getPheromone();
            double p2 = e2.getPheromone();

            hashSet.add(e1);
            hashSet.add(e2);

            e1.setPheromone(probability*p1 + 1.0/eval);
            e2.setPheromone(probability*p2 + 1.0/eval);
        }

        // Evaporate the pheromones on all the rest of the edges.
        for (Vertex v : this) {
            for (Edge e : v) {
                if (!hashSet.contains(e)) {
                    double p = e.getPheromone();
                    e.setPheromone(probability*p);
                }
            }
        }

    }
    
    public static double getDistance (Node node1, Node node2) {
        double xDiff = node1.getX() - node2.getX();
        double yDiff = node1.getY() - node2.getY();

        return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (Vertex v : this) {
            sb.append(v + "\n");
        }

        return new String(sb);
    }
}
