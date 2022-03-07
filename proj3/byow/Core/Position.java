package byow.Core;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position moveUp(int i) {
        return new Position(x, y + i);
    }

    public Position moveDown(int i) {
        return new Position(x, y - i);
    }

    public Position moveLeft(int i) {
        return new Position(x - i, y);
    }

    public Position moveRight(int i) {
        return new Position(x + i, y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public static int absX(Position a, Position b) {
        return Math.abs(a.getX() - b.getX());
    }

    public static int absY(Position a, Position b) {
        return Math.abs(a.getY() - b.getY());
    }

    public static int absMin(Position a, Position b) {
        return Math.min(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
    }

    public boolean equals(Position p) {
        return x == p.getX() &&  y == p.getY();
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
