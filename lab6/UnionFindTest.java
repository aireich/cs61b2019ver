import org.junit.Test;
import static org.junit.Assert.*;
/*** Test for Weighted Quick Union*/
public class UnionFindTest {
    @Test
    public void testBasic() {
        UnionFind u = new UnionFind(10);
        assertTrue(u.connected(0, 0));
        assertFalse(u.connected(1, 9));
        u.union(0, 1);
        assertEquals(u.find(0), 0);
        assertEquals(u.find(1), 0);
        assertEquals(u.sizeOf(1), 2);
        assertTrue(u.connected(0, 1));
        assertFalse(u.connected(0, 5));
        u.union(5, 6);
        u.union(5, 7);
        assertFalse(u.connected(0, 5));
        assertEquals(u.sizeOf(7), 3);
        assertEquals(u.sizeOf(6), 3);
        assertEquals(u.find(6), 5);
        u.union(7, 1);
        assertTrue(u.connected(6, 0));
        assertTrue(u.connected(5, 0));
        assertTrue(u.connected(5, 1));
        assertEquals(u.sizeOf(0), 5);
        assertEquals(u.sizeOf(5), 5);
        assertEquals(u.find(1), 5);
        assertEquals(u.find(6), 5);
    }
}
