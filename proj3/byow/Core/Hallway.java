package byow.Core;

import java.awt.*;
import java.util.HashSet;

public class Hallway extends Place {
    private Position start;
    private Position end;
    private Position corner;
    private boolean isLShape;
    private boolean horizontal;
    private boolean vertical;
    public static final int LEFT_UP = 0;
    public static final int LEFT_DOWN = 1;
    public static final int RIGHT_UP = 2;
    public static final int RIGHT_DOWN = 3;
    private int LShape = 5;

    public Hallway(Position start, Position end) {
        super(start);
        this.start = start;
        this.end = end;
        corner = createCorner();
        if (Math.max(start.getX(), end.getX()) == Math.min(start.getX(), end.getX()) + 2) {
            vertical = true;
        } else if (Math.max(start.getY(), end.getY()) == Math.min(start.getY(), end.getY()) + 2) {
            horizontal = true;
        } else if ((start.getX() != end.getX()) && (start.getY() != end.getY())) {
            isLShape = true;
            LShape = decideLShape();
        }
    }

//    public HashSet<Position> getInnerSpace() {
//        HashSet<Position> innerSpace = new HashSet<>();
//        for (int i)
//        return innerSpace;
//    }

    public boolean selfValidate() {
        if (Position.absX(start, end) < 3 ||Position.absY(start, end) < 3) {
            return false;
        } if (start.getY() == end.getY() || start.getX() == end.getX()) {
            return false;
        }
        return true;
    }

    private int decideLShape() {
        if (start.getX() < end.getX() && start.getY() < end.getY()) {
            return RIGHT_UP;
        }
        if (start.getX() < end.getX() && start.getY() > end.getY()) {
            return RIGHT_DOWN;
        }
        if (start.getX() > end.getX() && start.getY() < end.getY()) {
            return LEFT_UP;
        }
        if (start.getX() > end.getX() && start.getY() > end.getY()) {
            return LEFT_DOWN;
        }
        return 5;
    }

    public int getLShape() {
        return LShape;
    }

    public Position createCorner() {
        return new Position(end.getX(), start.getY());
    }

    public Position getCorner() {
        return corner;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isLShape() {
        return isLShape;
    }

    public boolean isVertical() {
        return vertical;
    }

    public int LHorizontalLength() {
        if (isLShape) {
            return start.getX() < end.getX() ? end.getX() - start.getX() : start.getX() - end.getX();
        }
        return -1;
    }

    public int LVerticalLength() {
        if (isLShape) {
            return start.getY() < end.getY() ? end.getY() - start.getY() : start.getY() - end.getY();
        }
        return -1;
    }

    public Position getEnd() {
        return end;
    }

    public Position getStart() {
        return start;
    }

    public void setEnd(Position end) {
        this.end = end;
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public int getLength() {
        if (horizontal) {
            return start.getX() < end.getX() ? end.getX() - start.getX() : start.getX() - end.getX();
        }
        if (vertical) {
            return start.getY() < end.getY() ? end.getY() - start.getY() : start.getY() - end.getY();
        }
        throw new IllegalArgumentException("Not a straight hallway");
    }

    public int getWidth() {
        if (horizontal || vertical) {
            return start.getX() < end.getX() ? end.getX() - start.getX() : start.getX() - end.getX();
        }
        throw new IllegalArgumentException("Not a straight hallway");
    }

    public int getHeight() {
        if (vertical || horizontal) {
            return start.getY() < end.getY() ? end.getY() - start.getY() : start.getY() - end.getY();
        }
        throw new IllegalArgumentException("Not a straight hallway");
    }

    public boolean intersectOrTouchWith(Hallway hallway) {
        Hallway thisOne = new Hallway(start, corner);
        Hallway thisTwo = new Hallway(corner, end);
        Hallway hallway1 = new Hallway(hallway.getStart(), hallway.getCorner());
        Hallway hallway2 = new Hallway(hallway.getCorner(), hallway.getBase());
        return !intersectOrTouchWithHelper(thisOne, hallway1) &&
                !intersectOrTouchWithHelper(thisOne, hallway2) &&
                !intersectOrTouchWithHelper(thisTwo, hallway1) &&
                !intersectOrTouchWithHelper(thisTwo, hallway2);
    }

    private static boolean intersectOrTouchWithHelper(Hallway h1, Hallway h2) {
        boolean XBelow = h1.getStart().getX() + h1.getWidth() < h2.getStart().getX();
        boolean XAbove = h1.getStart().getX()> h2.getStart().getX() + h2.getWidth();
        boolean YBelow = h1.getStart().getY() + h1.getHeight() < h2.getBase().getY();
        boolean YAbove = h1.getStart().getY() > h2.getBase().getY() + h2.getHeight();
        return (!XBelow && !XAbove) || (!YAbove && !YBelow);
    }

    @Override
    public String toString() {
        return "Hallway{" +
                "startX=" + start.getX() +
                " startY=" + start.getY() +
                ", endX=" + end.getX() +
                " endY=" + end.getY() +
                ", cornerX=" + corner.getX() +
                " cornerY=" + corner.getY() +
                ", isLShape=" + isLShape +
                ", horizontal=" + horizontal +
                ", vertical=" + vertical +
                ", LShape=" + LShape +
                '}';
    }
}
