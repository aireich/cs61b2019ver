package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>{
    private Vertex start;
    private Vertex end;
    private double timeout;
    private int numStates;
    private ArrayHeapMinPQ<Vertex> fringe;
    private double startTime;
    private double timeElapsed;
    private AStarGraph<Vertex> input;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private boolean emptyFringe;
    private boolean foundSolution;
    private boolean timeExceeded;
    private Stopwatch sw;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.input = input;
        this.start = start;
        this.end = end;
        this.timeout = timeout;
        this.numStates = 0;
        this.fringe = new ArrayHeapMinPQ<Vertex>();
        this.startTime = System.nanoTime();
        this.timeElapsed = 0;
        this.fringe.add(start, input.estimatedDistanceToGoal(start, end));
        this.distTo = new HashMap<Vertex, Double>();
        this.distTo.put(start, 0.0);
        this.edgeTo = new HashMap<Vertex, Vertex>();
        this.emptyFringe = false;
        this.foundSolution = false;
        this.timeExceeded = false;
        this.sw = new Stopwatch();


        distTo.put(start, 0.0);
        fringe.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end));
        Vertex newlyDeque = fringe.removeSmallest();
        numStates += 1;
        for (WeightedEdge<Vertex> w: input.neighbors(newlyDeque)) {
            Vertex p = w.from();
            Vertex q = w.to();
            double e = w.weight();
            fringe.add(q, e + input.estimatedDistanceToGoal(q, end));
            edgeTo.put(q, p);
            distTo.put(q, e);
        }

        while (fringe.size() > 0 && !end.equals(newlyDeque) && sw.elapsedTime() < timeout) {
            newlyDeque= fringe.removeSmallest();
            numStates += 1;
            long currentTime = System.nanoTime();
//            timeElapsed =  currentTime - startTime;
            timeElapsed = sw.elapsedTime();
            for (WeightedEdge<Vertex> w: input.neighbors(newlyDeque)) {
                relax(w);
            }
        }
        if (fringe.size() <= 0) {
            emptyFringe = true;
        }
        if (end.equals(newlyDeque)) {
            foundSolution = true;
        }
        if (explorationTime() > timeout) {
            timeExceeded = true;
        }
    }

    private void relax (WeightedEdge<Vertex> weightedEdge) {
        Vertex p = weightedEdge.from();
        Vertex q = weightedEdge.to();
        double e = weightedEdge.weight();
        if (!distTo.containsKey(q)) {
            distTo.put(q, Double.POSITIVE_INFINITY);
        }
        if (!distTo.containsKey(p)) {
            distTo.put(p, Double.POSITIVE_INFINITY);
        }
        if (distTo.get(p) + e < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + e);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                fringe.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            }
        }
    }
    public SolverOutcome outcome() {
        if (emptyFringe) {
            return SolverOutcome.UNSOLVABLE;
        }
        if (foundSolution) {
            return SolverOutcome.SOLVED;
        }
        if (timeExceeded) {
            return SolverOutcome.TIMEOUT;
        }
        return SolverOutcome.SOLVED;
    }

    public List<Vertex> solution() {
        List<Vertex> result = new ArrayList<Vertex>();
        if (timeExceeded || emptyFringe) {
            return new ArrayList<Vertex>();
        }
        Vertex current = end;
        while (!current.equals(start)) {
            result.add(current);
            current = edgeTo.get(current);
        }
        result.add(start);

        List<Vertex> rev =new ArrayList<Vertex>();
        for (int i = result.size() - 1; i >= 0; i--) {
            rev.add(result.get(i));
        }
        return rev;
    }

    public double solutionWeight() {
        if (timeExceeded || emptyFringe) {
            return 0;
        }
        return distTo.get(end);
    }
    public int numStatesExplored() {
        return numStates;
    }
    public double explorationTime() {
//        return  (double) (timeElapsed/1000000);
        return timeElapsed;
    }
}



//package bearmaps.hw4;
//
//import bearmaps.proj2ab.DoubleMapPQ;
//import bearmaps.proj2ab.ExtrinsicMinPQ;
//import edu.princeton.cs.algs4.Stopwatch;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
//    private SolverOutcome outcome;
//    private double solutionWeight;
//    private int statesExplored;
//    private LinkedList<Vertex> solution;
//    private double timeSpent;
//
//    public AStarSolver(AStarGraph<Vertex> G, Vertex start, Vertex goal, double timeout) {
//        Stopwatch sw = new Stopwatch();
//        solution = new LinkedList<>();
//
//        Map<Vertex, Double> distTo = new HashMap<>();
//        Map<Vertex, Vertex> edgeTo = new HashMap<>();
//        ExtrinsicMinPQ<Vertex> pq = new DoubleMapPQ<>();
//        distTo.put(start, 0.0);
//        pq.add(start, distTo.get(start) + G.estimatedDistanceToGoal(start, goal));
//
//        while (pq.size() > 0 && !pq.getSmallest().equals(goal) && sw.elapsedTime() < timeout) {
//            Vertex p = pq.removeSmallest();
//            statesExplored += 1;
//            for (WeightedEdge<Vertex> e : G.neighbors(p)) {
//                Vertex q = e.to();
//                double w = e.weight();
//                if (!distTo.containsKey(q) || distTo.get(p) + w < distTo.get(q)) {
//                    distTo.put(q, distTo.get(p) + w);
//                    edgeTo.put(q, p);
//                    if (pq.contains(q)) {
//                        pq.changePriority(q, distTo.get(q) + G.estimatedDistanceToGoal(q, goal));
//                    } else {
//                        pq.add(q, distTo.get(q) + G.estimatedDistanceToGoal(q, goal));
//                    }
//                }
//            }
//        }
//
//        if (pq.size() == 0) {
//            outcome = SolverOutcome.UNSOLVABLE;
//        } else if (pq.getSmallest().equals(goal)) {
//            outcome = SolverOutcome.SOLVED;
//            Vertex p = pq.getSmallest();
//            solutionWeight = distTo.get(p);
//            while (p != null) {
//                solution.addFirst(p);
//                p = edgeTo.get(p);
//            }
//        } else {
//            outcome = SolverOutcome.TIMEOUT;
//        }
//        timeSpent = sw.elapsedTime();
//    }
//
//    @Override
//    public SolverOutcome outcome() {
//        return outcome;
//    }
//
//    @Override
//    public List<Vertex> solution() {
//        if (outcome == SolverOutcome.SOLVED) {
//            return solution;
//        }
//        return null;
//    }
//
//    @Override
//    public double solutionWeight() {
//        if (outcome == SolverOutcome.SOLVED) {
//            return solutionWeight;
//        }
//        return 0;
//    }
//
//    @Override
//    public int numStatesExplored() {
//        return statesExplored;
//    }
//
//    @Override
//    public double explorationTime() {
//        return timeSpent;
//    }
//}