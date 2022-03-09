package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class KeyboardText extends SuperText {
    private StringBuilder sb;

    public KeyboardText(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void initialize() {
        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(width / 2, height * 2 / 3, "CS61B: GAME");
        StdDraw.text(width / 2, height * 2 / 3 - 1, "Author: aireich");
        StdDraw.text(width / 2, height / 2, "NEW GAME (N)");
        StdDraw.text(width / 2, height / 2 - 1, "LOAD GAME (L)");
        StdDraw.text(width / 2, height / 2 - 2, "QUIT (Q)");
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    @Override
    public boolean hasNext() {
        return StdDraw.hasNextKeyTyped();
    }

    @Override
    public char getNext() {
        return StdDraw.nextKeyTyped();
    }

}
