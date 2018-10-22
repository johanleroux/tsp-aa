package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import tsp.Ant;
import tsp.Configuration;

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

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (Vertex v : this) {
            sb.append(v + "\n");
        }

        return new String(sb);
    }
}
