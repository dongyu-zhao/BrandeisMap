import dataStructure.Edge;
import dataStructure.MinHeap;
import dataStructure.TreeNode;
import dataStructure.Vertex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

public class Tour {
    public static void Prim(Graph g, Vertex J) {
        TreeNode[] treeNodes = new TreeNode[Constants.MAX_VERTEX];
        boolean[] visit = new boolean[Constants.MAX_VERTEX];
        visit[J.getIx()] = true;
        treeNodes[J.getIx()] = new TreeNode(J.getIx());
        MinHeap heap = new MinHeap(Constants.MAX_EDGE, Comparator.comparingInt(x -> g.edges[x].getLength()));
        for (Edge e : J.getEdges()) {
            heap.insert(e.getIx());
        }
        while (!heap.isEmpty()) {
            Edge e = g.edges[heap.deleteMin()];
            Vertex u = e.getV2();
            if (visit[u.getIx()]) continue;
            visit[u.getIx()] = true;
            treeNodes[u.getIx()] = new TreeNode(u.getIx());
            treeNodes[e.getV1().getIx()].addChild(treeNodes[u.getIx()]);
            for (Edge edge : u.getEdges()) {
                Vertex v = edge.getV2();
                if (!visit[v.getIx()]) {
                    heap.insert(edge.getIx());
                }
            }
        }
        try {
            FileWriter fileWriter1 = new FileWriter(new File("TourOutput//OutputTourP.txt"));
            FileWriter fileWriter2 = new FileWriter(new File("TourOutput//OutputTourPP.txt"));
            dfs(g, treeNodes[J.getIx()], fileWriter1, fileWriter2);
            fileWriter1.close();
            fileWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dfs(Graph g, TreeNode t1, FileWriter fw1, FileWriter fw2) throws IOException {
        if (t1.getChildren().isEmpty()) return;
        for (TreeNode t2 : t1.getChildren()) {
            Vertex u = g.vertices[t1.getVal()];
            Vertex v = g.vertices[t2.getVal()];
            Edge e1 = u.findEdge(v);
            Edge e2 = v.findEdge(u);
            fw1.write(String.format("%d %d %d %d\n",
                    (int)(u.getX()/Map.FACTOR), (int)(u.getY()/Map.FACTOR),
                    (int)(v.getX()/Map.FACTOR), (int)(v.getY()/Map.FACTOR)));
            fw2.write(String.format("%d - (%d,%d) [%s -> %s] %s\n",
                    e1.getIx(), u.getIx(), v.getIx(), u.getLabel(), v.getLabel(), e1.getName()));
            dfs(g, t2, fw1, fw2);
            fw2.write(String.format("%d - (%d,%d) [%s -> %s] %s\n",
                    e2.getIx(), v.getIx(), u.getIx(), v.getLabel(), u.getLabel(), e2.getName()));
        }
    }

    private static void Kruskal(Graph g, Vertex J) {

    }

    public static void main(String[] args) {
        Graph g = new Graph("data//MapDataVertices.txt", "data//MapDataEdges.txt");
        Prim(g, g.vertices[100]);
    }
}
