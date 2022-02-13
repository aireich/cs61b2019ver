package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet{

    private  List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        double minDistance = 9999;
        Point newP = new Point(x, y);
        Point result = null;
        for (Point p: points) {
            if (Point.distance(p, newP) < minDistance) {
                result = p;
            }
        }
        return result;
    }
}
