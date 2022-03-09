package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class KeyBoardInput implements InputType{

    private String s;
    private String actions;
    private StringBuilder sb;
    private int seed;

    public KeyBoardInput() {
        this.s = "";
        this.actions = "";
        this.sb = new StringBuilder();
        this.seed = 0;
    }

    @Override
    public boolean seedShouldFinish() {
        return false;
    }

    @Override
    public void addActions(char c) {
        actions += c;
    }

    @Override
    public void addChar(char c) {
        s += c;
    }

    @Override
    public boolean shouldLoad() {
        return false;
    }

    @Override
    public void addNumber(char c) {
        sb.append(c);
    }

    @Override
    public boolean seedFinished() {
        return false;
    }

    @Override
    public boolean checkNextNumber() {
        return false;
    }

    @Override
    public boolean shouldEnd() {
        return Character.toLowerCase(StdDraw.nextKeyTyped())== ':' && Character.toLowerCase(StdDraw.nextKeyTyped())== 'q';
    }

    @Override
    public boolean shouldStart() {
        return Character.toLowerCase(StdDraw.nextKeyTyped())== 'n';
    }

    @Override
    public char getNext() {
        return Character.toLowerCase(StdDraw.nextKeyTyped());
    }

    @Override
    public boolean hasNext() {
        return StdDraw.hasNextKeyTyped();
    }

    @Override
    public int getSeed() {
        return Integer.parseInt(sb.toString());
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
}
