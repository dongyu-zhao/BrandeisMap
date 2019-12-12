package MapFolder.dataStructure;

import java.util.Comparator;

public class MinHeap {
    private int[] data;
    private int[] ixs;
    private int size;
    private Comparator<Integer> comparator;

    public MinHeap(int capacity) {
        this(capacity, (Integer x, Integer y) -> x - y);
    }

    public MinHeap(int capacity, Comparator<Integer> comparator) {
        data = new int[capacity + 1];
        ixs = new int[capacity];
        size = 0;
        this.comparator = comparator;
    }

    private void percolateUp(int i) {
        int parent = i / 2;
        while (i > 1 && comparator.compare(data[parent], data[i]) > 0) {
            swap(parent, i);
            i = parent;
            parent = i / 2;
        }
    }

    private void percolateDown(int i) {
        while (true) {
            int lChild = i * 2;
            int rChild = lChild + 1;
            int smallChild;
            if (rChild <= size) {
                smallChild = comparator.compare(data[lChild], data[rChild]) <= 0 ? lChild : rChild;
            } else if (lChild == size) {
                smallChild = lChild;
            } else break;

            if (comparator.compare(data[i], data[smallChild]) > 0) {
                swap(i, smallChild);
            } else break;
            i = smallChild;
        }
    }

    private void swap(int i, int j) {
        int temp = ixs[data[i]];
        ixs[data[i]] = ixs[data[j]];
        ixs[data[j]] = temp;

        temp = data[i];
        data[i] = data[j];
        data[j] = temp;

    }

    public int deleteMin() {
        int out = data[1];
        swap(1, size);
        ixs[data[size]] = -1;
        size --;
        percolateDown(1);
        return out;
    }

    public void insert(int elem) {
        size ++;
        data[size] = elem;
        ixs[elem] = size;
        percolateUp(size);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void update(int i) {
        int ix = ixs[i];
        percolateUp(ix);
        percolateDown(ix);
    }
}
