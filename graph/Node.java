package graph;

public abstract class Node {
    int x, y;

    public Node (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }
    
    public double distanceTo (Node other) {
        double xDiff = getX() - other.getX();
        double yDiff = getY() - other.getY();

        return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return y == node.y && x == node.x;
    }

    @Override
    public int hashCode () {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString () {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
