import dataStructure.Edge;
import dataStructure.MinHeap;
import dataStructure.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    private Vertex[] vertices;
    private Edge[] edges;

    public Graph(String verticesFileName, String edgesFileName) {
        vertices = new Vertex[Constants.MAX_VERTEX];
        edges = new Edge[Constants.MAX_EDGE];
        readVertices(verticesFileName);
        readEdges(edgesFileName);
    }

    private void readVertices(String filename) {
        try {
            int i = 0;
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty() || line.startsWith("//")) continue;
                String[] eles = line.split(" ", 5);
                int ix = Integer.parseInt(eles[0]);
                String label = eles[1];
                int x = Integer.parseInt(eles[2]);
                int y = Integer.parseInt(eles[3]);
                String name = eles[4].substring(1, eles[4].length() - 1);
                vertices[i] = new Vertex(ix, label, x, y, name);
                i ++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Format is wrong");
        }
    }

    private void readEdges(String filename) {
        try {
            int i = 0;
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty() || line.startsWith("//")) continue;
                String[] eles = line.split(" ", 10);
                // # label1 label2 v1 v2 length, angle, direction (C) name
                int ix = Integer.parseInt(eles[0]);
                Vertex v1 = vertices[Integer.parseInt(eles[3])];
                Vertex v2 = vertices[Integer.parseInt(eles[4])];
                int length = Integer.parseInt(eles[5]);
                int angle = Integer.parseInt(eles[6]);
                String direction = eles[7];
                char type = eles[8].charAt(1);
                String name = eles[9].substring(1, eles[9].length() - 1);
                edges[i] = new Edge(ix, v1, v2, length, angle, direction, type, name);
                i ++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Format is wrong");
        }
    }

    public List<Edge> shortestPath(Vertex start, Vertex end, boolean hasSkateboard, boolean minTime) {
        Deque<Edge> reversedPath = new LinkedList<>();
        int[] parents = new int[Constants.MAX_VERTEX];
        Arrays.fill(parents, -1);
        int[] costs = new int[Constants.MAX_VERTEX];
        for (int i = 0; i < costs.length; i ++) {
            costs[i] = Constants.INFINITE_COST;
        }
        costs[start.getIx()] = 0;
        MinHeap unvisits = new MinHeap(Constants.MAX_VERTEX, Comparator.comparingInt(x -> costs[x]));
        for (int i = 0; i < Constants.MAX_VERTEX; i ++) {
            unvisits.insert(i);
        }
        while (!unvisits.isEmpty()) {
            Vertex u = vertices[unvisits.deleteMin()];
            if (u == end) break;
            for (Edge e : u.getEdges()) {
                Vertex v = e.getV2();
                if (costs[v.getIx()] > costs[u.getIx()] + e.getCost(hasSkateboard, minTime)) {
                    costs[v.getIx()] = costs[u.getIx()] + e.getCost(hasSkateboard, minTime);
                    unvisits.update(v.getIx());
                    parents[v.getIx()] = u.getIx();
                }
            }
        }
        int ix = end.getIx();
        while (parents[ix] != -1) {
            reversedPath.push(vertices[parents[ix]].findEdge(vertices[ix]));
            ix = parents[ix];
        }
        List<Edge> path = new LinkedList<>();
        while (!reversedPath.isEmpty()) {
            path.add(reversedPath.pop());
        }
        return ix == start.getIx() ? path : null;
    }

    public Vertex getVertex(int i) {
        return vertices[i];
    }

    public Vertex findVertexByName(String name) {
        for (Vertex v : vertices) {
            if (v == null) continue;
            if (v.getName().toLowerCase().contains(name.toLowerCase())) {
                return v;
            }
        }
        return null;
    }
}
