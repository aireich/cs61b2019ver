package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    @Test
    public void testTreeBuild() {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(2,3));
        points.add(new Point(1,5));
        points.add(new Point(4,2));
        points.add(new Point(4,5));
        points.add(new Point(3,3));
        points.add(new Point(4,4));
        KDTree k = new KDTree(points);
    }

    @Test
    public void testNearest() {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(2,3));
        points.add(new Point(1,5));
        points.add(new Point(4,2));
        points.add(new Point(4,5));
        points.add(new Point(3,3));
        points.add(new Point(4,4));
        KDTree k = new KDTree(points);
        Point result = k.nearest(0,7);
        assertEquals(result.getX(), 1, 0.01);
        assertEquals(result.getY(), 5, 0.01);
    }

    @Test
    public void testPerformance() {
        List<Point> points = new ArrayList<Point>();
        Random r = new Random(42);
        for (int i = 0; i < 100000; i++) {
            points.add(new Point(r.nextInt(1000), r.nextInt(1000)));
        }
        NaivePointSet np = new NaivePointSet(points);
        long startNP = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++) {
            np.nearest(r.nextInt(1000), r.nextInt(1000));
        }
        long endNP = System.currentTimeMillis();
        System.out.println("Total time elapsed on Naive: " + (endNP - startNP)/1000.0 +  " seconds.");

        KDTree kd = new KDTree(points);
        long startKD = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++) {
            kd.nearest(r.nextInt(1000), r.nextInt(1000));
        }
        long endKD = System.currentTimeMillis();
        System.out.println("Total time elapsed on KDTree: " + (endKD - startKD)/1000.0 +  " seconds.");

    }
}
