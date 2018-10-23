package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Vertex extends Node implements Iterable<Edge> {

    private HashMap<Integer, Edge> hashMap = new HashMap<>();
    private ArrayList<Edge> list;

    public Vertex (int x, int y) {
    	super(x, y);
        hashMap = new HashMap<>();
        list = new ArrayList<>();
    }

    public void addEdge (Node n) {
        if (n instanceof Vertex) {
            Edge e = new Edge(n.getX(), n.getY());
            hashMap.put(e.hashCode(), e);
            list.add(e);
        }
        else {
            hashMap.put(n.hashCode(), (Edge)n);
            list.add((Edge)n);
        }
    }

    public Edge getEdge (Node n) {
        return hashMap.get(n.hashCode());
    }

    public boolean contains (Node n) {
        return hashMap.containsValue(n.hashCode());
    }

    public int getTotalEdges () {
        return hashMap.size();
    }

    @Override
    public Iterator<Edge> iterator () {
        return list.iterator();
    }

    @Override
    public String toString () {
        return "Vertex {x=" + x + ", y=" + y + ", edges=" + hashMap +"}";
    }

}

