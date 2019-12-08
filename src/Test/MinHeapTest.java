import dataStructure.MinHeap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class MinHeapTest {
    Random random = new Random();

    @Test
    public void deleteMinTestWithoutComparator() {
        int num = 10;
        MinHeap minHeap = new MinHeap(num);
        Queue<Integer> pq = new PriorityQueue<>(num);
        for (int i = 0; i < num; i ++) {
            int x = random.nextInt(num);
            minHeap.insert(x);
            pq.offer(x);
        }
        for (int i = 0; i < num; i ++) {
            assertEquals(minHeap.deleteMin(), pq.poll());
        }
    }

    @Test
    public void deleteMinTestWithComparator() {
        int num = 10;
        MinHeap minHeap = new MinHeap(num, (Integer x, Integer y) -> y - x);
        Queue<Integer> pq = new PriorityQueue<>(num, (Integer x, Integer y) -> y - x);
        for (int i = 0; i < num; i ++) {
            int x = random.nextInt(num);
            minHeap.insert(x);
            pq.offer(x);
        }
        for (int i = 0; i < num; i ++) {
            assertEquals(minHeap.deleteMin(), pq.poll());
        }
    }
}
