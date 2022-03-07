package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.junit.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int maxNumOfHallways = 4;
    private boolean started;
    private boolean ended;
    private int seed;
    private StringBuilder sb;
    private String prevStatus;
    private boolean worldGenerated;
    /**contains all Room instance */
    private List<Room> rooms;
    /** contains all Hallway instance **/
    private List<Hallway> hallways;
    /** contains all Position inside Room or Hallway**/
    private HashSet<Position> floors;
    /** contains all Room instance with its number of connected Hallway**/
    private HashMap<Room, Integer> hallwayCntOfRoom;

    public Engine() {
        this.started = false;
        this.ended = false;
        this.seed = 0;
        this.sb = new StringBuilder();
        this.prevStatus = "";
        this.worldGenerated = false;
        this.rooms = new ArrayList<>();
        this.hallways = new ArrayList<>();
        this.floors = new HashSet<>();
        this.hallwayCntOfRoom = new HashMap<>();
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

    /*** Generate a new TETile 2D array.
     * The basic idea is firstly generating random number of Room with random location and random size.
     * Then for each of them choose another random Room instance and connect them with a Hallway.
     * Thus, each Room will have at least one Hallway and at most maxNumOfHallways.
     * For each Hallway, its start and end Position define its length. Its walls are built beside
     * these two positions (left and right if vertical, up and down if horizontal). These two positions are
     * drawn as WALL.
     * After connecting all Room, we will draw Floor tile to cover the overlapped rooms or hallways.
     * In other words, for a position, if it's inside a Room or a Hallway, it will be drawn as Floor instead of
     * WALL or NOTHING.
     * @param seed random integer seed
     * */
    public TETile[][] generateNewWorld(int seed) {
        Random r = new Random(seed);
        int width = WIDTH;
        int height = HEIGHT;
        TETile[][] world = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        int numOfRoom = r.nextInt(width);
        int numOfHallway = r.nextInt(width);
//        System.out.println("numOfRooms: " + numOfRoom);
//        System.out.println("numOfHallway: " + numOfHallway);
        for (int i = 0; i < numOfRoom; i++) {
            generateNewRoom(world, r);
        }
        for (Room room : rooms) {
            if (hallwayCntOfRoom.get(room) < 1) {
                generateNewPairOfHallway(world, room, rooms, r);
            }
        }
        coverOverlapWallWithFloor(world);
        worldGenerated = true;
        return world;
    }


   /** Cover the grid with floor tile if this position belongs to an inner space of a room or hallway
    * @param world 2D TETile array
    * **/
    private void coverOverlapWallWithFloor(TETile[][] world) {
        for (Position p: floors) {
            drawSingleFloor(world, p);
        }
    }

    /** Generate a new room in a 2D TETile array
     * @param world 2D TETile array
     * @param r Random instance
     * */
    private void generateNewRoom(TETile[][] world, Random r) {
        Room newRoom = new Room(r, new Position(r.nextInt(WIDTH), r.nextInt(HEIGHT)),
                RandomUtils.uniform(r, 4, WIDTH/5), RandomUtils.uniform(r, 4, HEIGHT/5));
        if (newRoom.checkSelfValidation()) {
            addNewRoomToWorld(world, newRoom);
//            System.out.println(newRoom.toString());
        }
    }

    /** Generate a pair of hallway connecting to rooms
     * @param r a Random instance
     * @param world 2D TETile array
     * @param rooms list of rooms
     * @param startRoom one decided Room instance
     * **/
    private void generateNewPairOfHallway(TETile[][] world, Room startRoom, List<Room> rooms, Random r) {
        Room targetRoom = rooms.get(r.nextInt(rooms.size() - 1));
        if (!targetRoom.equals(startRoom)) {
            if (hallwayCntOfRoom.get(startRoom) < maxNumOfHallways && hallwayCntOfRoom.get(targetRoom) < maxNumOfHallways) {
                Position selectedStart = startRoom.getInnerSpace().iterator().next();
                Position selectedEnd = targetRoom.getInnerSpace().iterator().next();
                Position corner = new Position(selectedStart.getX(), selectedEnd.getY());
                Hallway firstHallway = new Hallway(selectedStart, corner);
                if (addNewHallway(world, firstHallway)) {
                    hallwayCntOfRoom.put(startRoom, hallwayCntOfRoom.get(startRoom) + 1);
                    drawSingleFloor(world, corner);
                }
                Hallway secondHallway = new Hallway(corner, selectedEnd);
                if (addNewHallway(world, secondHallway)) {
                    hallwayCntOfRoom.put(targetRoom, hallwayCntOfRoom.get(targetRoom) + 1);
                    drawSingleFloor(world, corner);
                }
            }
        }
    }



    /** Add new room to 2D TETile array
     * @param world 2D TETile array
     * @param room a Room instance
     * */
    private boolean addNewRoomToWorld(TETile[][] world, Room room) {
        int width = WIDTH;
        int height = HEIGHT;
        boolean noOverlap = true;
        Position base = room.getBase();
//        if (rooms.size() > 0) {
//            for (Room prev: rooms) {
//                if (room.intersectOrTouchWith(prev)) {
//                    noOverlap = false;
//                }
//            }
//        }
        if (room.checkWorldValidation(width, height) && noOverlap) {
            drawSingleWall(world, base, base.moveRight(room.getWidth() - 1));
            drawSingleWall(world, base.moveUp(room.getHeight() - 1),
                    base.moveUp(room.getHeight() - 1).moveRight(room.getWidth() - 1));
            drawSingleWall(world, base, base.moveUp(room.getHeight() - 1));
            drawSingleWall(world, base.moveRight(room.getWidth() - 1),
                    base.moveRight(room.getWidth() - 1).moveUp(room.getHeight() - 1));
            rooms.add(room);
            floors.addAll(room.getInnerSpace());
            hallwayCntOfRoom.put(room, 0);
            return true;
        }
        return false;

    }


    /** add a hallway to 2D TETile array
     * @param world 2D TETile array
     * @param hallway a Hallway instance
     * */
    private boolean addNewHallway(TETile[][] world, Hallway hallway) {
        boolean valid = hallway.checkSelfValidation() && hallway.checkWorldValidation(world) ;
        if (valid) {
//            System.out.println(hallway.toString());
            drawHallway(world, hallway);
            hallways.add(hallway);
            floors.addAll(hallway.getInnerSpace());
            return true;
        }
        return false;
    }

    /** draw a Hallway instance on a 2D TETile array
     * @param hallway a Hallway instance
     * @param world 2D TETile array
     * **/
    public void drawHallway(TETile[][] world, Hallway hallway) {
        if (hallway.isVertical()) {
            drawSingleWall(world, hallway.getStart().moveLeft(1), hallway.getEnd().moveLeft(1));
            drawSingleWall(world, hallway.getStart().moveRight(1), hallway.getEnd().moveRight(1));
            drawSingleBlock(world, hallway.getStart());
            drawSingleBlock(world, hallway.getEnd());
        }
        else if (hallway.isHorizontal()) {
            drawSingleWall(world, hallway.getStart().moveUp(1), hallway.getEnd().moveUp(1));
            drawSingleWall(world, hallway.getStart().moveDown(1), hallway.getEnd().moveDown(1));
            drawSingleBlock(world, hallway.getStart());
            drawSingleBlock(world, hallway.getEnd());
        }
        else {
            throw new RuntimeException(" not a valid hallway");
        }

    }


    /**
     *  draw a WALL on a 2D TETile array
     * @param world 2D TETile array
     * @param end end position
     * @param start start position
     *  **/
    private void drawSingleWall(TETile[][] world, Position start, Position end) {
        if (start.getY() == end.getY()) {
            int realStartX = Math.min(start.getX(), end.getX());
            int realEndX = Math.max(start.getX(), end.getX());
            for (int i = realStartX; i <= realEndX; i++) {
                world[i][start.getY()] = Tileset.WALL;
            }
        }
        else if (start.getX() == end.getX()) {
            int realStartY = Math.min(start.getY(), end.getY());
            int realEndY = Math.max(start.getY(), end.getY());
            for (int i = realStartY; i <= realEndY; i++) {
                world[start.getX()][i] = Tileset.WALL;
            }
        } else {
            System.out.println(start.toString() + " " + end.toString());
            throw new IllegalArgumentException(" Not in line");
        }
    }

    private void drawSingleBlock(TETile[][] world, Position p) {
        world[p.getX()][p.getY()] = Tileset.WALL;
    }

    private void drawSingleFloor(TETile[][] world, Position p) {
        world[p.getX()][p.getY()] = Tileset.FLOOR;
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
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = e.generateNewWorld(456);
        ter.renderFrame(world);
    }

    public static void renderNewHallway() {
        Engine e = new Engine();
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        Hallway test = new Hallway(new Position(20,10), new Position(10,10));
        Hallway test2 = new Hallway(new Position(20,6), new Position(10,6));
        e.addNewHallway(world, test);
        e.addNewHallway(world, test2);
        e.coverOverlapWallWithFloor(world);
        ter.renderFrame(world);

    }
}
