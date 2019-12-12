package MapFolder.dataStructure;

import java.util.Arrays;

public class UnionFind {
    private int[] parent;

    public UnionFind(int capacity) {
        parent = new int[capacity];
        Arrays.fill(parent, -1);
    }

    private int root(int i) {
        if (parent[i] == -1) return i;
        int r = root(parent[i]);
        parent[i] = r;
        return r;
    }


    public boolean find(int i, int j) {
        return root(i) == root(j);
    }

    public void connect(int i, int j) {
        if (!find(i, j)) {
            parent[root(j)] = root(i);
        }
    }
}
