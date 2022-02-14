import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SeparableEnemySolver {

    Graph g;

    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     * @source https://github.com/PigZhuJ/cs61b_sp19/blob/master/clab9/SeparableEnemySolver.java
     */

    public boolean isSeparable() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String label : g.labels()) {
            if (!map.containsKey(label)) {
                if (!isSeparableHelper(map, label, 1, label)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSeparableHelper(HashMap<String, Integer> map, String current, int expectedValue, String prev) {
        if (map.containsKey(current)) {
            return map.get(current) == expectedValue;
        }
        map.put(current, expectedValue);
        for (String neighbor : g.neighbors(current)) {
            if (!neighbor.equals(prev)) {
                if (!isSeparableHelper(map, neighbor, -1 * expectedValue, current)) {
                    return false;
                }
            }
        }
        return true;
    }
//    public boolean isSeparable() {
//        String start = findCycle();
//        int cnt = isSeparableHelper(new HashSet<String >(), start, 0, null);
//        if (cnt % 2 != 0 ) {
//            return false;
//        }
//        return true;
//    }
//
//    private int isSeparableHelper(HashSet<String> visited, String current, int cnt, String prev) {
//        if (visited.contains(current)) {
//            return cnt;
//        }
//        visited.add(current);
//        for (String neighbor : g.neighbors(current)) {
//            if (!neighbor.equals(prev)) {
//                cnt = isSeparableHelper(visited, neighbor, cnt + 1, current);
//            }
//        }
//        return 0;
//    }
//
//    private String findCycle() {
//        HashSet<String> visited = new HashSet<String>();
//        String prev = null;
//        for (String label: g.labels()) {
//            visited.add(label);
//            for (String child: g.neighbors(label)) {
//                if (visited.contains(child) && !child.equals(prev)) {
//                    return child;
//                }
//            }
//            prev = label;
//        }
//        return null;
//    }


    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */

}
