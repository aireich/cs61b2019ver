package byow.Core;
import java.util.HashSet;
import java.util.Random;

public class Room extends Place{
    private Position base;
    private Position[] doors;
    private int width;
    private int height;
    private int numOfDoor;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    public Room(Position base) {
        super(base);
    }

    public Room(Random r, Position base, int width, int height, int numOfDoor) {
        super(base);
        this.base = base;
        this.width = width;
        this.height = height;
        this.numOfDoor = numOfDoor;
        this.doors = new Position[numOfDoor];
        if (checkSelfValidation()) {
            initializeDoor(r);
        }
    }

    public HashSet<Position> getInnerSpace() {
        HashSet<Position> result = new HashSet<>();
        for (int i = base.getX() + 1; i < base.getX() + width - 2; i++) {
            for (int j = base.getY() + 1; j < base.getY() + height - 2; j++) {
                result.add(new Position(i, j));
            }
        }
        return result;
    }

    public void initializeDoor(Random r) {
        for (int i = 0; i < numOfDoor; i++) {
            int side = RandomUtils.uniform(r, 4);
            if (side == LEFT) {
                doors[i] = new Position(base.getX(), base.getY() + RandomUtils.uniform(r, 1, height - 1));
            }
            if (side == RIGHT) {
                doors[i] = new Position(base.getX() + width - 1, base.getY() + RandomUtils.uniform(r, 1, height - 1));
            }
            if (side == UP) {
                doors[i] = new Position(base.getX() + RandomUtils.uniform(r, 1, width - 1), base.getY());
            }
            if (side == DOWN) {
                doors[i] = new Position(base.getX() + RandomUtils.uniform(r, 1, width - 1), base.getY() + height - 1);
            }
        }
    }

    public boolean checkSelfValidation() {
        if (width < 2 || height < 2 || numOfDoor < 1) {
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

    public void setBase(Position base) {
        this.base = base;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNumOfDoor() {
        return numOfDoor;
    }

    public void setNumOfDoor(int numOfDoor) {
        this.numOfDoor = numOfDoor;
    }

    public Position[] getDoors() {
        return doors;
    }

    @Override
    public String toString() {
        return "Base X: " + base.getX() + " Base Y: " + base.getY()
                + " width: " + width + " height: " + height + " numOfDoors: " + numOfDoor;
    }
}
