package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    static class Position {
        private int x;
        private int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public HexWorld(){}

    public void addHexagon(TETile[][] world, TETile t, int size, int x, int y) {
        if (size < 1) {
            throw new IllegalArgumentException("input size should be more than 1");
        }
        if (!checkWorldSize(world, size, x, y)) {
            throw new IllegalArgumentException("world is too small or position is not appropriate");
        }
        fillTwoMiddle(world, t, size, x, y);
        fillUpside(world, t, size, x, y);
        fillDownSide(world, t, size, x, y);

    }

    private boolean checkWorldSize(TETile[][] world, int size, int x, int y) {
        if (x > world.length || y > world[0].length) {
            return false;
        }
        if (x + size + 2 * (size - 1) > world.length || y + size > world[0].length || y - size + 1 < 0 ){
            return false;
        }
        return true;
    }

    private void fillTwoMiddle(TETile[][] world, TETile t, int size, int x, int y) {
        for (int i = 0; i < size + 2 * (size - 1); i++) {
            world[x + i][y] = t;
            world[x + i][y + 1] = t;
        }
    }

    private void fillUpside(TETile[][] world, TETile t, int size, int x, int y) {
        for (int i = 0; i < size - 1; i++) {
            for(int j = 1 + i; j < size + 2 * (size - 1) - (1 + i); j++) {
                world[j + x][i + y + 2] = t;
            }
        }
    }

    private void fillDownSide(TETile[][] world, TETile t, int size, int x, int y) {
        for (int i = 0; i < size - 1; i++) {
            for(int j = 1 + i; j < size + 2 * (size - 1) - (1 + i); j++) {
                world[x + j][y - i - 1] = t;
            }
        }
    }

    private static Position bottomLeft(int size, int x, int y) {
        return new Position(x - size - (size - 1), y - size);
    }

    private static Position bottomRight(int size, int x, int y) {
        return new Position(x + size + (size - 1), y - size);
    }

    private static Position justBottom(int size, int x, int y) {
        return new Position(x, y - 2 * size);
    }

    private static TETile randomTile(int tileNum) {
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {
        int width = 50;
        int height = 50;
        int size = 3;
        Random r = new Random(42);
        Position base = new Position(25, 40);
        HexWorld h = new HexWorld();
        TERenderer ter = new TERenderer();

        ter.initialize(width, height);
        TETile[][] world = new TETile[width][height];
        for (int i = 0; i < world.length;  i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        Position middle = base;
        for (int i = 0; i < 5; i++) {
            middle = justBottom(size, middle.x, middle.y);
            h.addHexagon(world, randomTile(r.nextInt(3)), size, middle.x, middle.y);
        }

        Position middleLeft = bottomLeft(size, base.x, base.y);
        for (int i = 0; i < 4; i++) {
            middleLeft = justBottom(size, middleLeft.x, middleLeft.y);
            h.addHexagon(world,randomTile(r.nextInt(3)), size, middleLeft.x, middleLeft.y);
        }

        Position middleLeftLeft = bottomLeft(size, bottomLeft(size, base.x, base.y).x, bottomLeft(size, base.x, base.y).y);
        for (int i = 0; i < 3; i++) {
            middleLeftLeft = justBottom(size, middleLeftLeft.x, middleLeftLeft.y);
            h.addHexagon(world,randomTile(r.nextInt(3)), size, middleLeftLeft.x, middleLeftLeft.y);
        }

        Position middleRight = bottomRight(size, base.x, base.y);
        for (int i = 0; i < 4; i++) {
            middleRight = justBottom(size, middleRight.x, middleRight.y);
            h.addHexagon(world,randomTile(r.nextInt(3)), size, middleRight.x, middleRight.y);
        }

        Position middleLeftRight = bottomRight(size, bottomRight(size, base.x, base.y).x, bottomLeft(size, base.x, base.y).y);
        for (int i = 0; i < 3; i++) {
            middleLeftRight = justBottom(size, middleLeftRight.x, middleLeftRight.y);
            h.addHexagon(world,randomTile(r.nextInt(3)), size, middleLeftRight.x, middleLeftRight.y);
        }

        ter.renderFrame(world);
    }
}
