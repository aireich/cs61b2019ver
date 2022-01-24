package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus p = new Clorus(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), p.color());
        p.move();
        assertEquals(1.97, p.energy(), 0.01);
        p.move();
        assertEquals(1.94, p.energy(), 0.01);
        p.stay();
        assertEquals(1.95, p.energy(), 0.01);
        p.stay();
        assertEquals(1.96, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus p = new Clorus(2);
        Clorus child = p.replicate();
        assertEquals(p.energy(), 1,0.001);
        assertEquals(child.energy(), 1, 0.001);
        Clorus child2 = child.replicate();
        assertEquals(child2.energy(), 0.5, 0.001);
        assertEquals(child.energy(), 0.5, 0.001);
        assertEquals(p.energy(), 1,0.001);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus p = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy < 1 and no Plip around. Move to empty space
        p = new Clorus(0.5);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Impassible());
        allEmpty.put(Direction.BOTTOM, new Impassible());
        allEmpty.put(Direction.LEFT, new Impassible());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = p.chooseAction(allEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.RIGHT);

        assertEquals(expected, actual);


        //Emtpy space around and one Plip around. Attack Plip
        p = new Clorus(0.5);
        HashMap<Direction, Occupant> allEmpty1 = new HashMap<Direction, Occupant>();
        allEmpty1.put(Direction.TOP, new Impassible());
        allEmpty1.put(Direction.BOTTOM, new Impassible());
        allEmpty1.put(Direction.LEFT, new Plip(1.0));
        allEmpty1.put(Direction.RIGHT, new Empty());

        actual = p.chooseAction(allEmpty1);
        expected = new Action(Action.ActionType.ATTACK, Direction.LEFT);

        assertEquals(expected, actual);

        //No empty sapce and one Plip around. Stay.
        p = new Clorus(0.5);
        HashMap<Direction, Occupant> allEmpty2 = new HashMap<Direction, Occupant>();
        allEmpty2.put(Direction.TOP, new Impassible());
        allEmpty2.put(Direction.BOTTOM, new Impassible());
        allEmpty2.put(Direction.LEFT, new Clorus(1.0));
        allEmpty2.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(allEmpty2);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);
    }
}
