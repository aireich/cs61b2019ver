package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public class StringText implements TextProcessor{

    private String s;
    private boolean started;
    private int index;
    private int width;
    private int height;
    private char prev;
    private TETile[][] world;

    public StringText(String s, int width, int height) {
        this.s = s;
        this.started = false;
        this.index = 0;
        this.width =width;
        this.height = height;
        this.world = new TETile[width][height];
    }

    @Override
    public boolean hasNext() {
        return index < s.length();
    }

    @Override
    public char getNext() {
        if (hasNext()) {
            char result = s.toCharArray()[index];
            index += 1;
            return result;
        }
        return 0;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public boolean noSeedExist() {
        return false;
    }

    @Override
    public void move(TETile[][] world, char action) {

    }

    @Override
    public char getPrevChar() {
        return prev;
    }

    @Override
    public TETile[][] getWorld() {
        return world;
    }

    @Override
    public void setPrevChar(char c) {
        prev = c;
    }
}
