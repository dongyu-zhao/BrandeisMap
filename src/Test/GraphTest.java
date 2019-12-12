import dataStructure.Edge;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GraphTest {
    @Test
    public void testShortestDistancePathWithoutSkateboard() {
        Graph g = new Graph("data//MapDataVertices.txt", "data//MapDataEdges.txt");
        List<Edge> path = g.shortestPath(g.findVertex("castle"), g.findVertex("gordon field"), false, false);
        assertEquals(9, path.size());
    }

    @Test
    public void testShortestTimePathWithSkateboard() {
        Graph g = new Graph("data//MapDataVertices.txt", "data//MapDataEdges.txt");
        List<Edge> path = g.shortestPath(g.findVertex("castle"), g.findVertex("gordon field"), true, true);
        assertEquals(10, path.size());
    }
}
