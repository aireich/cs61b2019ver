package byow.Core;

public class Place {
    private Position base;
    private int width;
    private int height;
    public Place(Position base) {
        this.base = base;
    }

    public Position getBase() {
        return base;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

//    public boolean intersectOrTouchWith(Place room) {
//        boolean XBelow = base.getX() + width < room.getBase().getX();
//        boolean XAbove = base.getX() > room.getBase().getX() + room.getWidth();
//        boolean YBelow = base.getY() + height < room.getBase().getY();
//        boolean YAbove = base.getY() > room.getBase().getY() + room.getHeight();
//        return (!XBelow && !XAbove) || (!YAbove && !YBelow);
//    }
}
