package byow.Core;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class Room{
    private final Position base;
    private final int width;
    private final int height;


    public Room(Random r, Position base, int width, int height) {
        this.base = base;
        this.width = width;
        this.height = height;
    }
    /** get the inner space of a Room instance
     * @return a HashSet which contains all Position inside the Room except WALL (in other words, boundary)
     * ***/
    public HashSet<Position> getInnerSpace() {
        HashSet<Position> result = new HashSet<>();
        for (int i = base.getX() + 1; i < base.getX() + width - 1; i++) {
            for (int j = base.getY() + 1; j < base.getY() + height - 1; j++) {
                result.add(new Position(i, j));
            }
        }
        return result;
    }

    public boolean checkSelfValidation() {
        if (width < 3 || height < 3) {
            return false;
        }
        return true;
    }

    public boolean checkWorldValidation(int w, int h) {
        if (base.getX() + width > w) {
            return false;
        }
        if (base.getY() + height > h) {
            return false;
        }

        return true;
    }

    /***
     * check if the Room instance intersect or touch with another Room instance
     * */
    public boolean intersectOrTouchWith(Room room) {
        boolean XBelow = base.getX() + width < room.getBase().getX();
        boolean XAbove = base.getX() > room.getBase().getX() + room.getWidth();
        boolean YBelow = base.getY() + height < room.getBase().getY();
        boolean YAbove = base.getY() > room.getBase().getY() + room.getHeight();
        return (!XBelow && !XAbove) || (!YAbove && !YBelow);
    }

    public Position getBase() {
        return base;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    @Override
    public String toString() {
        return "Base X: " + base.getX() + " Base Y: " + base.getY()
                + " width: " + width + " height: " + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return width == room.width && height == room.height && Objects.equals(base, room.base);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, width, height);
    }
}
