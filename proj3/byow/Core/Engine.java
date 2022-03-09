package byow.Core;

import byow.SaveDemo.Editor;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import java.awt.*;
import java.io.*;
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
    private Position playerPosition;
    private Position doorPosition;
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
        TERenderer teRenderer = new TERenderer();
        TETile[][] finalWorldFrame = null;
        StringBuilder sb = new StringBuilder();
        char prev = 0;
        while (StdDraw.hasNextKeyTyped()) {
            char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (c == 'n' ) {
                    if (started) {
                        continue;
                    } else {
                        start();
                    }
                }
                if (c == ':') {
                    continue;
                }
                if (c == 'q') {
                    if (prev == ':') {
                        end();
                    }
                }
                if (c == 's') {
                    if (Character.isDigit(prev) && started) {
                        seed = Integer.parseInt(sb.toString());
                        finalWorldFrame = generateNewWorld(seed);
                    } else {
                        moveAvatar(finalWorldFrame, 's');
                    }
                }
                if (Character.isDigit(c) && started) {
                    sb.append(c);
                }
                if (c == 'w' || c == 'a' || c == 'd') {
                    moveAvatar(finalWorldFrame, c);
                }
                if (c == 'l') {
                    loadStatus();
                }
            prev = c;
            teRenderer.renderFrame(finalWorldFrame);
        }
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
//        si.initialize(WIDTH, HEIGHT);
        while(si.hasNext()) {
            if (si.shouldStart() && !started) {
                start();
                char nextChar = si.getNext();
//                si.addChar(nextChar);
                si.addActions(nextChar);
//                prevStatus += si.getNext();
            } else if (!started && si.shouldLoad()) {
                loadStatus();
            } else if (si.checkNextNumber() && started && !si.seedFinished() && !checkSeedExist()) {
                char nextChar = si.getNext();
//                si.addChar(nextChar);
                si.addActions(nextChar);
//                prevStatus += nextNumber;
                si.addNumber(nextChar);
                if (started && si.seedShouldFinish()) {
                    seed = si.getSeed();
                    si.getNext();
                    finalWorldFrame = generateNewWorld(seed);
                }
            } else if (started && si.seedFinished()) {
                char next = si.getNext();
//                si.addChar(next);
                si.addActions(next);
                moveAvatar(finalWorldFrame, next);
                //                    prevStatus += si.getNext();
            } else if (si.shouldEnd()) {
                end();
            }
        }


        return finalWorldFrame;
    }

    private void moveAvatar(TETile[][] world, char action) {
        switch (action) {
            case 's':
                if (validatePosition(world, playerPosition.moveDown(1))) {
                    playerPosition = playerPosition.moveDown(1);
                    drawAvatar(world, playerPosition, playerPosition.moveUp(1));
                }
            case 'w' :
                if (validatePosition(world, playerPosition.moveUp(1))) {
                    playerPosition = playerPosition.moveUp(1);
                    drawAvatar(world, playerPosition, playerPosition.moveDown(1));
                }
            case 'a' :
                if (validatePosition(world, playerPosition.moveLeft(1))) {
                    playerPosition = playerPosition.moveLeft(1);
                    drawAvatar(world, playerPosition, playerPosition.moveRight(1));
                }
            case 'd' :
                if (validatePosition(world, playerPosition.moveRight(1))) {
                    playerPosition = playerPosition.moveRight(1);
                    drawAvatar(world, playerPosition, playerPosition.moveLeft(1));
                }
            default:
                drawAvatar(world, playerPosition, playerPosition);
        }
    }

    private static boolean validatePosition(TETile[][] world, Position p) {
       return !world[p.getX()][p.getY()].equals(Tileset.WALL);
    }

    private static StringInput loadStatus() {
        File f = new File("./save_data.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (StringInput) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no Editor has been saved yet, we return a new one. */
        return new StringInput(" ");
    }

    public void saveStatus(InputType it) {
        File f = new File("./save_data.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(it);
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            System.exit(0);
        }
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
        playerPosition = getRandomInnerSpace();
        drawAvatar(world, playerPosition, playerPosition);
        doorPosition = getRandomInnerSpace();
        drawDoor(world, doorPosition);
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

    private void drawAvatar(TETile[][] world, Position p, Position prev) {
        world[prev.getX()][prev.getY()] = Tileset.FLOOR;
        world[p.getX()][p.getY()] = Tileset.AVATAR;
    }

    private void drawDoor(TETile[][] world, Position p) {
        world[p.getX()][p.getY()] = Tileset.UNLOCKED_DOOR;
    }

    private Position getRandomInnerSpace() {
        return floors.iterator().next();
    }

    public void start() {
        started = true;
    }

    public void end() {
        ended = true;
        System.exit(0);
    }

    public boolean ifEnd() {
        return ended;
    }


    public boolean checkSeedExist() {
        return seed != 0;
    }


    public static void main(String[] args) {
        renderStringInput();
    }

    public static void renderNewWorld(){
        Engine e = new Engine();
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = e.generateNewWorld(123);
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

    public static void renderStringInput() {
        Engine e = new Engine();
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        world = e.interactWithInputString("N123SSWWWS:Q");
        ter.renderFrame(world);

    }
}
