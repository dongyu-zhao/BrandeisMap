import dataStructure.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

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
            outputPath1(g, treeNodes[J.getIx()], fileWriter1, fileWriter2);
            fileWriter1.close();
            fileWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void outputPath1(Graph g, TreeNode t1, FileWriter fw1, FileWriter fw2) throws IOException {
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
            outputPath1(g, t2, fw1, fw2);
            fw2.write(String.format("%d - (%d,%d) [%s -> %s] %s\n",
                    e2.getIx(), v.getIx(), u.getIx(), v.getLabel(), u.getLabel(), e2.getName()));
        }
    }

    private static void KruskalShortcut(Graph g, Vertex J) {
        boolean[] mstEdge = new boolean[Constants.MAX_EDGE];
        UnionFind uf = new UnionFind(Constants.MAX_VERTEX);
        MinHeap heap = new MinHeap(Constants.MAX_EDGE, Comparator.comparingInt(x -> g.edges[x].getLength()));
        for (Edge e : g.edges) {
            if (e != null) heap.insert(e.getIx());
        }
        while (!heap.isEmpty()) {
            Edge e = g.edges[heap.deleteMin()];
            int i = e.getV1().getIx();
            int j = e.getV2().getIx();
            if (uf.find(i, j)) continue;
            uf.connect(i, j);
            mstEdge[e.getIx()] = true;
            mstEdge[e.getV2().findEdge(e.getV1()).getIx()] = true;
        }

        boolean[] visit = new boolean[Constants.MAX_VERTEX];
        GroupTreeNode[] treeNodes = new GroupTreeNode[Constants.MAX_VERTEX];
        treeNodes[J.getIx()] = new GroupTreeNode(J);
        buildGroupTree(g, J, treeNodes, mstEdge, visit);

        try {
            FileWriter fileWriter = new FileWriter(new File("TourOutput//OutputTourJ.txt"));
            outputPath2(g, treeNodes[J.getIx()], fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildGroupTree(Graph g, Vertex u, GroupTreeNode[] treeNodes, boolean[] mstEdge, boolean[] visit) {
        if (visit[u.getIx()]) return;
        visit[u.getIx()] = true;
        for (Edge e : u.getEdges()) {
            if (mstEdge[e.getIx()]) {
                Vertex v = e.getV2();
                if (visit[v.getIx()]) continue;
                treeNodes[v.getIx()] = new GroupTreeNode(v);
                treeNodes[u.getIx()].addChild(treeNodes[v.getIx()]);
                buildGroupTree(g, v, treeNodes, mstEdge, visit);
            }
        }
    }

    private static void outputPath2(Graph g, GroupTreeNode t1, FileWriter fw) throws IOException {
        if (t1.getChildren().isEmpty()) return;
        for (List<GroupTreeNode> ls : t1.getChildren()) {
            Vertex u = t1.getVertex();
            for (GroupTreeNode t2 : ls) {
                u = outputPath2Helper(t2, u, fw);
                outputPath2(g, t2, fw);
            }
            outputPath2Helper(t1, u, fw);
        }
    }

    private static Vertex outputPath2Helper(GroupTreeNode t1, Vertex u, FileWriter fw) throws IOException {
        Vertex v = t1.getVertex();
        Edge e = u.findEdge(v);
        fw.write(String.format("%d - (%d,%d) [%s -> %s] %s\n",
                e.getIx(), u.getIx(), v.getIx(), u.getLabel(), v.getLabel(), e.getName()));
        return v;
    }

    public static void main(String[] args) {
        Graph g = new Graph("data//MapDataVertices.txt", "data//MapDataEdges.txt");
        Prim(g, g.vertices[100]);
        KruskalShortcut(g, g.vertices[100]);
    }
}
