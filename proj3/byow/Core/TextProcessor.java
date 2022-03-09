package byow.Core;

import byow.TileEngine.TETile;

public interface TextProcessor {
    public boolean hasNext();
    public char getNext();
    public boolean isStarted();
    public void start();
    public boolean noSeedExist();
    public void move(TETile[][] world, char action);
    public TETile[][] getWorld();
    public char getPrevChar();
    public void setPrevChar(char c);
}
