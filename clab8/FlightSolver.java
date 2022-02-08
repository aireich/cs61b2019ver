import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 *
 * @source https://github.com/PigZhuJ/cs61b_sp19/blob/master/clab8/FlightSolver.java
 */
public class FlightSolver {
    int max  = 0;

    public FlightSolver(ArrayList<Flight> flights) {
        PriorityQueue<Flight> minStartPQ = new PriorityQueue<Flight>(new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return Integer.compare(o1.startTime(), o2.startTime());
            }
        });

        PriorityQueue<Flight> minEndPQ = new PriorityQueue<Flight>(new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return Integer.compare(o1.endTime(), o2.endTime());
            }
        });

        minStartPQ.addAll(flights);
        minEndPQ.addAll(flights);
        int currentMax = 0;
        while(minStartPQ.size() > 0) {
            if(minStartPQ.peek().startTime() < minEndPQ.peek().endTime()) {
                currentMax += minStartPQ.poll().passengers();
            } else {
                Flight a = minEndPQ.poll();
                currentMax -= a.passengers();
            }
            if (currentMax > max) {
                max = currentMax;
            }
        }

    }

    public int solve() {
        return max;
    }

}
