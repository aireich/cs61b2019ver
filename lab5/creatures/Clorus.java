package creatures;

import huglife.*;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    private int r;
    private int g;
    private int b;

    public Clorus(double energy) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        this.energy = energy;
    }

    public Clorus() {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = 0;
    }

    @Override
    public String name() {
        return "clorus";
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        boolean anyEmpty = false;
        boolean anyPlip = false;
        for (Direction direction: neighbors.keySet()) {
            if (neighbors.get(direction).name().equals("empty")) {
                anyEmpty = true;
                emptyNeighbors.addFirst(direction);
            } else if (neighbors.get(direction).name().equals("plip")) {
                anyPlip = true;
                plipNeighbors.addFirst(direction);
            }
        }

        if (!anyEmpty) {
            return new Action(Action.ActionType.STAY);
        }

        if (anyPlip) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plipNeighbors));
        }

        if (energy > 1.0) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
    }

    @Override
    public Color color() {
        return color(34, 0, 231);
    }

    @Override
    public Clorus replicate() {
        Clorus child = new Clorus(energy / 2);
        energy /= 2;
        return child;
    }

    @Override
    public double energy() {
        return energy;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void stay() {
        energy += 0.01;
    }
}
