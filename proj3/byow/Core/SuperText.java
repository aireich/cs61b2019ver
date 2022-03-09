package byow.Core;

import byow.TileEngine.TETile;

/**A super class that defines the general process between keyboard input and string input.
 * The only difference is how they hasNext() and getNext() which will be assured by
 * TextProcessor interface**/
public class SuperText implements TextProcessor {
    protected StringBuilder sb;
    protected int seed;
    protected boolean started;
    protected int index;
    protected int width;
    protected int height;
    protected char prev;
    protected TETile[][] world;
    protected Position playerPosition;
    protected Position doorPosition;

    public SuperText() {
        this.seed = 0;
        this.started = false;
        this.index = 0;
        this.width = width;
        this.height = height;
        this.sb = new StringBuilder();
    }

    public void addNumber(char c) {
        sb.append(c);
    }

    public void setSeed() {
        seed = Integer.parseInt(sb.toString());
    }

    public int getSeed() {
        return seed;
    }

    public void setWorld(TETile[][] world) {
        this.world = world;
    }

    public void setPlayerPosition(Position playerPosition) {
        this.playerPosition = playerPosition;
        Engine.drawAvatar(world, playerPosition, playerPosition);
    }

    public void setDoorPosition(Position doorPosition) {
        this.doorPosition = doorPosition;
        Engine.drawDoor(world, doorPosition);
    }

    public boolean isStarted() {
        return started;
    }

    public void start() {
        started = true;
    }

    public boolean noSeedExist() {
        return seed == 0;
    }

    public void move(char action) {
        assert playerPosition != null;
        playerPosition = Engine.moveAvatar(world, action, playerPosition);
    }

    public char getPrevChar() {
        return prev;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public void setPrevChar(char c) {
        prev = c;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public char getNext() {
        return 0;
    }
}

