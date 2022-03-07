package byow.Core;
import byow.TileEngine.TETile;

import java.util.HashSet;
import java.util.List;

public class Hallway {
    private final Position start;
    private final Position end;
    private boolean vertical;
    private boolean horizontal;

    public Hallway(Position start, Position end) {
        this.start = start;
        this.end = end;
        if (start.getX() == end.getX()) {
            vertical = true;
        } else if (start.getY() == end.getY()) {
            horizontal = true;
        }
    }

    public boolean checkSelfValidation() {
        return (vertical || horizontal) && !start.equals(end);
    }

    /** check if the Hallway is inside the boundary of 2D TETile array
     * */
    public boolean checkWorldValidation(TETile[][] world) {
        if (start.getX() == world.length - 1 || start.getX() == world[0].length - 1 ||
        end.getX() == world.length - 1 || end.getX() == world[0].length - 1 ||
        start.getY() == world.length - 1 || start.getY() == world[0].length - 1||
                end.getY() == world.length - 1 || end.getY() == world[0].length - 1 ) {
            return false;
        }
        return true;
    }


    /** get the inner space of a Hallway instance
     * @return a HashSet which contains all Position inside the Hallway except WALL (in other words, boundary)
     * ***/
    public HashSet<Position> getInnerSpace() {
        HashSet<Position> innerSpace = new HashSet<>();
        if (horizontal) {
            int realStartX = Math.min(start.getX(), end.getX());
            int realEndX = Math.max(start.getX(), end.getX());
            for (int i = realStartX + 1; i < realEndX; i++) {
                innerSpace.add(new Position(i, start.getY()));
            }
        }
        if (vertical) {
            int realStartY = Math.min(start.getY(), end.getY());
            int realEndY = Math.max(start.getY(), end.getY());
            for (int i = realStartY + 1; i < realEndY; i++) {
                innerSpace.add(new Position(start.getX(), i));
            }
        }
        return innerSpace;
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Hallway{" +
                "startX= " + start.getX() +
                " startY= " + start.getY() +
                " , endX= " + end.getX() +
                " endX= " + end.getY() +
                " , vertical=" + vertical +
                " , horizontal=" + horizontal +
                '}';
    }
}
