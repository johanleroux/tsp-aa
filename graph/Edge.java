package graph;

public class Edge extends Node {

    public double pheromone;

    public Edge (int x, int y) {
        super(x, y);
        pheromone = 0.01;
    }

    @Override
    public String toString () {
        return "Edge {x=" + x +", y=" + y +"}";
    }
}
