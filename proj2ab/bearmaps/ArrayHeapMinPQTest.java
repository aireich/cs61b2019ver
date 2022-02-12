package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;

public class ArrayHeapMinPQTest {
    @Test
    public void testBasics() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        for(int i = 0; i < 500; i++) {
            a.add("hi" + i, i);
        }
        for(int i = 0; i < 500; i++) {
            assertTrue(a.contains("hi" + i));
        }
        assertEquals(a.size(), 500);
        assertEquals(a.getSmallest(), "hi0");
        a.removeSmallest();
        assertEquals(a.getSmallest(), "hi1");
        while(a.size() > 0) {
            assertNotNull(a.removeSmallest());
        }

        ArrayHeapMinPQ<Integer> b = new ArrayHeapMinPQ<>();
        for(int i = 0; i < 500; i++) {
            b.add(i, i);
        }
        assertTrue(b.getSmallest() == 0);
        b.removeSmallest();
        assertTrue(b.getSmallest() == 1);
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        for(int i = 0; i < 500; i++) {
            a.add("hi" + i, i);
        }
        for (int i = 0; i <500; i++) {
            a.changePriority("hi" + i, 499-i);
        }
        assertEquals(a.getSmallest(), "hi499");
    }

    @Test
    public void testSink() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10; i ++) {
            a.add(i, i);
        }
        a.removeSmallest();
        a.removeSmallest();
    }

    @Test
    public void testRandomSequence() {
        int testTime = 500;
        int[] ar = new int[testTime];
        for (int i = 0; i < testTime; i++) {
            ar[i] = i;
        }
        Random r = new Random(42);
        for (int i = ar.length - 1; i > 0; i--) {
            int index = r.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
//        int record = -1;
//        for (int i = 0; i < 500 ; i++ ) {
//            if (ar[i] == record) {
//                System.out.println("joke");
//            }
//            record = ar[i];
//        }
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> ae = new NaiveMinPQ<>();
        for (int i = 0; i < testTime; i++) {
            a.add("hi" + ar[i], ar[i]);
            ae.add("hi" + ar[i], ar[i]);
            assertEquals(a.getSmallest(), ae.getSmallest());
            assertEquals(a.size(), ae.size());
        }
        assertEquals(a.size(), ae.size());
        while (a.size() > 0) {
            assertEquals(a.removeSmallest(), ae.removeSmallest());
        }
    }

    @Test
    public void testPerformance() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            a.add("hi" + i, i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed on ArrayHeapMinPQ: " + (end - start)/1000.0 +  " seconds.");
        NaiveMinPQ<String> b = new NaiveMinPQ<>();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            b.add("hi" + i, i);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Total time elapsed on NaiveMinPQ: " + (end2 - start1)/1000.0 +  " seconds.");
    }
}
