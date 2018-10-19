package graph;

public class Edge extends Node {

    private double pheromone;

    public Edge (int x, int y) {
        super(x, y);
        pheromone = 0.01;
    }

    public void setPheromone (double pheromone) {
        this.pheromone = pheromone;
    }

    public double getPheromone () {
        return pheromone;
    }

    @Override
    public String toString () {
        return "Edge{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
