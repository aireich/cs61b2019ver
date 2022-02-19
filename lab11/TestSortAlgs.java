import edu.princeton.cs.algs4.Queue;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> q = new Queue<String>();
        q.enqueue("a");
        q.enqueue("z");
        q.enqueue("b");
        Queue<String> qs = QuickSort.quickSort(q);
        String prev = qs.peek();
        while (qs.size() > 0 ) {
            assertTrue(qs.peek().compareTo(prev) >= 0);
            prev = qs.dequeue();
        }
    }

    @Test
    public void testMergeSort() {
        Queue<String> q = new Queue<String>();
        q.enqueue("a");
        q.enqueue("z");
        q.enqueue("b");
        Queue<String> qs = MergeSort.mergeSort(q);
        String prev = qs.peek();
        while (qs.size() > 0 ) {
            assertTrue(qs.peek().compareTo(prev) >= 0);
            prev = qs.dequeue();
        }
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
