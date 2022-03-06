package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int maxNumOfDoors = 4;
    private boolean started;
    private boolean ended;
    private int seed;
    private StringBuilder sb;
    private String prevStatus;
    private boolean worldGenerated;
    private List<Room> rooms;
    private List<Hallway> hallways;

    public Engine() {
        this.started = false;
        this.ended = false;
        this.seed = 0;
        this.sb = new StringBuilder();
        this.prevStatus = "";
        this.worldGenerated = false;
        this.rooms = new ArrayList<>();
        this.hallways = new ArrayList<>();
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {

    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        TETile[][] finalWorldFrame = null;
        StringInput si = new StringInput(input);
        while(si.hasNext()) {
            if (si.shouldStart() && !started) {
                start();
                prevStatus += si.getNext();
            } else if (!started && si.shouldLoad()) {
                if (prevStatusExist()) {
                    interactWithInputString(prevStatus); ///?????
                } else {
                    throw new NoSuchElementException("No Load Found");
                }
            } else if (si.checkNextNumber() && started && !si.seedFinished() && !checkSeedExist()) {
                char nextNumber = si.getNext();
                prevStatus += nextNumber;
                si.addNumber(nextNumber);
            } else if (started && si.seedFinished()) {
                if (worldGenerated) {
                    prevStatus += si.getNext();
                }
                else {
                    seed = si.getSeed();
                    finalWorldFrame = generateNewWorld(seed);
                }
            }
        }


        return finalWorldFrame;
    }

    public TETile[][] generateNewWorld(int seed) {
        Random r = new Random(seed);
        int width = 40;
        int height = 30;
        TETile[][] world = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        int numOfRoom = r.nextInt(width);
        int numOfHallway = r.nextInt(width);
        System.out.println("numOfRooms: " + numOfRoom);
        System.out.println("numOfHallway: " + numOfHallway);
        for (int i = 0; i < numOfRoom; i++) {
            Room newRoom = new Room(r, new Position(r.nextInt(width), r.nextInt(height)),
                    RandomUtils.uniform(r, 4, width/5), RandomUtils.uniform(r, 4, height/5),
                    RandomUtils.uniform(r, 1, maxNumOfDoors));
//            if (newRoom.checkSelfValidation() && addNewRoom(world, newRoom)) {
//                System.out.println(newRoom.toString());
//            }
        }
        for (int i = 0; i < numOfHallway; i++) {
            Hallway newHallway = new Hallway(new Position(r.nextInt(width), r.nextInt(height)),
                    new Position(r.nextInt(width), r.nextInt(height)));
            addNewHallway(world, newHallway);
        }
        worldGenerated = true;
        return world;
    }

    private boolean addNewRoom(TETile[][] world, Room room) {
        int width = world.length;
        int height = world[0].length;
        boolean noOverlap = true;
        Position base = room.getBase();
        if (rooms.size() > 0) {
            for (Room prev: rooms) {
                if (room.intersectOrTouchWith(prev)) {
                    noOverlap = false;
                }
            }
        }
        if (room.checkWorldValidation(width, height) && noOverlap) {
            drawSingleWall(world, base, base.moveRight(room.getWidth() - 1));
            drawSingleWall(world, base.moveUp(room.getHeight() - 1),
                    base.moveUp(room.getHeight() - 1).moveRight(room.getWidth() - 1));
            drawSingleWall(world, base, base.moveUp(room.getHeight() - 1));
            drawSingleWall(world, base.moveRight(room.getWidth() - 1),
                    base.moveRight(room.getWidth() - 1).moveUp(room.getHeight() - 1));
            rooms.add(room);
            return true;
        }
        return false;

    }

    private boolean addNewHallway(TETile[][] world, Hallway hallway) {
        boolean valid = hallway.selfValidate();
//        if (hallways.size() > 0) {
//            for (Hallway h: hallways) {
//                if (hallway.intersectOrTouchWith(h)){
//                    valid = false;
//                }
//            }
//        }
        if (valid) {
            drawHallway(world, hallway);
            hallways.add(hallway);
            return true;
        }
        return false;
    }

    public void drawHallway(TETile[][] world, Hallway hallway) {
        Position corner = hallway.getCorner();
        if (hallway.getLShape() == Hallway.LEFT_UP) {
            drawSingleWall(world, corner, hallway.getStart());
            drawSingleWall(world, corner, hallway.getEnd());
            drawSingleWall(world, corner.moveRight(2).moveUp(2), hallway.getStart().moveUp(2));
            drawSingleWall(world, corner.moveRight(2).moveUp(2), hallway.getEnd().moveRight(2));
            System.out.println("leftup on: " + hallway.toString());
        }
        else if (hallway.getLShape() == Hallway.LEFT_DOWN) {
            drawSingleWall(world, corner, hallway.getStart());
            drawSingleWall(world, hallway.getEnd(), corner);
            drawSingleWall(world, corner.moveDown(2).moveRight(2), hallway.getStart().moveDown(2));
            drawSingleWall(world, hallway.getEnd().moveRight(2), corner.moveDown(2).moveRight(2));
            System.out.println("leftdown on: " + hallway.toString());
        }
        else if (hallway.getLShape() == Hallway.RIGHT_UP) {
            drawSingleWall(world, hallway.getStart(), corner);
            drawSingleWall(world, corner, hallway.getEnd());
            drawSingleWall(world, hallway.getStart().moveUp(2), corner.moveLeft(2).moveUp(2));
            drawSingleWall(world, corner.moveLeft(2).moveUp(2), hallway.getEnd().moveLeft(2));
            System.out.println("rightup on: " + hallway.toString());
        }
        else if (hallway.getLShape() == Hallway.RIGHT_DOWN) {
            drawSingleWall(world, hallway.getStart(), corner);
            drawSingleWall(world, corner, hallway.getEnd());
            drawSingleWall(world, hallway.getStart().moveDown(2), corner.moveLeft(2).moveDown(2));
            drawSingleWall(world, corner.moveLeft(2).moveDown(2), hallway.getEnd().moveLeft(2));
            System.out.println("rightdown on: " + hallway.toString());
        }
//        else if (hallway.isVertical()) {
//            drawSingleWall(world, hallway.getStart(), hallway.getEnd().moveLeft(2));
//            drawSingleWall(world, hallway.getStart().moveRight(2), hallway.getEnd());
//            System.out.println("vertical on: " + hallway.toString());
//        }
//        else if (hallway.isHorizontal()) {
//            drawSingleWall(world, hallway.getStart(), hallway.getEnd().moveDown(2));
//            drawSingleWall(world, hallway.getStart().moveUp(2), hallway.getEnd());
//            System.out.println("horizontal on: " + hallway.toString());
//        }
        else {
            System.out.println(hallway.toString());
            throw new RuntimeException("not a valid hallway");
        }
    }

    public void drawSingleWall(TETile[][] world, Position start, Position end) {
        if (start.getY() == end.getY()) {
            int realStartX = Math.min(start.getX(), end.getX());
            int realEndX = Math.max(start.getX(), end.getX());
            for (int i = realStartX; i < realEndX; i++) {
                world[i][start.getY()] = Tileset.WALL;
            }
        }
        else if (start.getX() == end.getX()) {
            int realStartY = Math.min(start.getY(), end.getY());
            int realEndY = Math.max(start.getY(), end.getY());
            for (int i = realStartY; i < realEndY; i++) {
                world[start.getX()][i] = Tileset.WALL;
            }
        } else {
            System.out.println(start.toString() + " " + end.toString());
            throw new IllegalArgumentException(" Not in line");
        }
    }

    public void start() {
        started = true;
    }


    public void end() {
        ended = false;
    }

    public boolean ifEnd() {
        return ended;
    }

    public String prevStatus() {
        return prevStatus;
    }

    public boolean checkSeedExist() {
        return seed != 0;
    }

    public boolean prevStatusExist() {
        return prevStatus.length() > 0;
    }

    public static void main(String[] args) {
        renderNewWorld();
    }

    public static void renderNewWorld(){
        Engine e = new Engine();
        TERenderer ter = new TERenderer();
        ter.initialize(40, 30);
        TETile[][] world = e.generateNewWorld(456);
        ter.renderFrame(world);
    }

    public static void renderNewHallway() {
        Engine e = new Engine();
        TERenderer ter = new TERenderer();
        int width = 40;
        int height = 30;
        ter.initialize(40, 30);
        TETile[][] world = new TETile[40][30];
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 30; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        Hallway test = new Hallway(new Position(20,10), new Position(10,8));
        e.addNewHallway(world, test);
        ter.renderFrame(world);

    }
}
