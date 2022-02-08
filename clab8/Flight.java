import java.util.Comparator;

/**
 * Represents a flight in the Flight problem.
 */
public class Flight {

    int startTime;
    int endTime;
    int passengers;

    public Flight(int start, int end, int numPassengers) {
        startTime = start;
        endTime = end;
        passengers = numPassengers;
    }

    public int startTime() {
        return startTime;
    }

    public int endTime() {
        return endTime;
    }

    public int passengers() {
        return passengers;
    }

    public static Comparator<Integer> comp = (p1, p2) -> {
        int diff = p1 - p2;
        return diff;
    };
}
