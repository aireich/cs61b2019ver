package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class StringInput implements InputType{

    private String s;
    private String actions;
    private int index;
    private boolean started;
    private boolean ended;
    private int seed;
    private StringBuilder sb;
    private boolean seedFinished;
    private boolean seedShouldFinish;

    public StringInput(String s) {
        this.s = s;
        this.index = 0;
        this.started = false;
        this.ended = false;
        this.seed = 0;
        this.sb = new StringBuilder();
        this.actions = "";
        this.seedFinished = false;
        this.seedShouldFinish = false;
    }
    @Override
    public void initialize(int width, int height) {
        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(width / 2, height * 2/ 3, "CS61B: GAME");
        StdDraw.text(width / 2, height * 2/ 3 - 1, "Author: aireich");
        StdDraw.text(width / 2, height / 2, "NEW GAME (N)");
        StdDraw.text(width / 2, height / 2 - 1, "LOAD GAME (L)");
        StdDraw.text(width / 2, height / 2 - 2, "QUIT (Q)");
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    @Override
    public void addChar(char c) {
        s = s + c;
    }

    @Override
    public boolean hasNext() {
        return index < s.length();
    }

    public boolean checkPosition(int i) {
        return i < s.length();
    }

    public char peekAt(int i) {
        return Character.toLowerCase(s.charAt(i));
    }

    @Override
    public char getNext() {
        char c = Character.toLowerCase(s.charAt(index));
        index += 1;
        return c;
    }

    @Override
    public boolean shouldStart() {
        return Character.toLowerCase(s.charAt(index)) == 'n';
    }

    @Override
    public boolean shouldEnd() {
        if (checkPosition(index + 1)) {
            return Character.toLowerCase(s.charAt(index)) == ':' && Character.toLowerCase(peekAt(index + 1)) == 'q';
        }
        return false;
    }

    @Override
    public boolean checkNextNumber() {
        return peekAt(index) == '1' || peekAt(index) == '2'|| peekAt(index) == '3' || peekAt(index) == '4' || peekAt(index) == '5' ||
                peekAt(index) == '6' ||peekAt(index) == '7' ||peekAt(index) == '8' || peekAt(index) == '9' ||peekAt(index) == '0' ;
    }

    @Override
    public void addNumber(char c) {
        sb.append(c);
    }

    @Override
    public int getSeed() {
        return Integer.parseInt(sb.toString());
    }

    @Override
    public boolean seedFinished() {
        if (seed != 0) {
            seedFinished = true;
        }
        return seedFinished;
    }

    @Override
    public boolean seedShouldFinish() {
        if (seed == 0 && getSeed() != 0 && peekAt(index) == 's' && !seedFinished) {
            seedShouldFinish = true;
            seed = getSeed();
        } else if (seedFinished) {
            seedShouldFinish = false;
        }
        return seedShouldFinish;
    }

    @Override
    public boolean shouldLoad() {
        return Character.toLowerCase(s.charAt(index)) == 'l';
    }

    @Override
    public void addActions(char c) {
        actions += c;
    }
}
