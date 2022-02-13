package bearmaps;

import java.util.List;

/**Effective KDTree
 * @author aireich*/
public class KDTree {

    private class Node{
        Point point;
        boolean compareX;
        Node left;
        Node right;

        public Node(Point point, boolean compareX) {
            this.point = point;
            this.compareX = compareX;
        }
    }

    private List<Point> points;

    private Node root;

    public KDTree(List<Point> points) {
        this.root = new Node(points.get(0), true);
        this.points = points;
        for (Point p: points) {
            put(root, p, root.compareX);
        }
    }

    private Node put(Node n, Point p, boolean compareX) {
        if (n == null) {
            return new Node(p, compareX);
        }
        if (n.compareX == true) {
            if (p.getX() < n.point.getX()) {
                n.left = put(n.left, p, !n.compareX);
            } else {
                n.right = put(n.right, p, !n.compareX);
            }
        } else {
            if (p.getY() < n.point.getY()) {
                n.left = put(n.left, p, !n.compareX);
            } else {
                n.right = put(n.right, p, !n.compareX);
            }
        }
        return  n;
    }


    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node result =  nearestHelper(root, goal, root);
        return result.point;
    }

    private Node nearestHelper(Node n, Point goal, Node best) {
        Node goodSide;
        Node badSide;
        boolean badSideUseful = false;
        if (n == null) {
            return best;
        }
        if (Point.distance(n.point, goal) <  Point.distance(best.point, goal)) {
            best = n;
        }
        if (n.compareX == true) {
            if (n.point.getX() < goal.getX()) {
                goodSide = n.left;
                badSide = n.right;
            } else {
                goodSide = n.right;
                badSide = n.left;
            }
        } else {
            if (n.point.getY() < goal.getY()) {
                goodSide = n.left;
                badSide = n.right;
            } else {
                goodSide = n.right;
                badSide = n.left;
            }
        }

        if (n.compareX == true && (n.point.getX() - goal.getX()) * (n.point.getX() - goal.getX())
                < Point.distance(best.point, goal)) {
                badSideUseful = true;
        }
        if (n.compareX == false && (n.point.getY() - goal.getY()) * (n.point.getY() - goal.getY())
                < Point.distance(best.point, goal)) {
            badSideUseful = true;
        }
        if (badSideUseful == true) {
            best = nearestHelper(badSide, goal, best);
        }
        best = nearestHelper(goodSide, goal, best);
        return best;
    }


}
