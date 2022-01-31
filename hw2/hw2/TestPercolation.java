package hw2;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestPercolation {
    @Test
    public void testBasic() {
        Percolation p = new Percolation(5);
        assertFalse(p.isOpen(0,0));
        assertFalse(p.isOpen(4,4));
        p.open(0,1);
        assertTrue(p.isFull(0,1));
        p.open(1,2);
        assertFalse(p.percolates());
        p.open(1,3);
        assertFalse(p.percolates());
        p.open(2,3);
        assertFalse(p.percolates());
        p.open(2,2);
        assertFalse(p.percolates());
        p.open(2,1);
        assertFalse(p.percolates());
        p.open(3,1);
        assertFalse(p.percolates());
        p.open(3,2);
        assertFalse(p.percolates());
        p.open(3,3);
        assertFalse(p.percolates());
        p.open(3,4);
        assertFalse(p.percolates());
        assertFalse(p.isFull(3,4));
        assertFalse(p.isOpen(3,4));
        p.open(1,1);
        p.open(4,4);
        assertTrue(p.percolates());
        assertTrue(p.isOpen(3,4));
        assertTrue(p.isOpen(2,2));
        assertTrue(p.isOpen(0,0));
        assertEquals(p.numberOfOpenSites(), 1);
    }

    public void testNumberOfOpenSites() {
        Percolation p = new Percolation(5);
        assertFalse(p.isOpen(0,0));
    }

}
