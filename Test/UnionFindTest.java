import MapFolder.dataStructure.UnionFind;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnionFindTest {
    @Test
    public void test1() {
        UnionFind uf = new UnionFind(6);
        uf.connect(0, 1);
        uf.connect(0, 2);
        uf.connect(1, 3);
        uf.connect(0, 1);
        uf.connect(4, 5);
        assertTrue(uf.find(0, 1));
        assertTrue(uf.find(0, 2));
        assertTrue(uf.find(0, 3));
        assertFalse(uf.find(0, 4));
        assertFalse(uf.find(0, 5));

        assertTrue(uf.find(4, 5));
        assertFalse(uf.find(3, 4));
        assertTrue(uf.find(2, 3));
        assertFalse(uf.find(1, 5));
    }
}
