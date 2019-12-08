package dataStructure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Vertex {
    private int x;
    private int y;
    private int ix;
    private String label;
    private String name;
    private List<Edge> edges;

    public Vertex(int ix, String label, int x, int y, String name) {
        this.ix = ix;
        this.label = label;
        this.x = x;
        this.y = y;
        this.name = name;
        edges = new LinkedList<>();
    }

    public Edge findEdge(Vertex v) {
        for (Edge e : edges) {
            if (e.getV2() == v) {
                return e;
            }
        }
        return null;
    }

    void addEdge(Edge edge) {
        edges.add(edge);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIx() {
        return ix;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public List<Edge> getEdges() {
        return edges;
    }

}
