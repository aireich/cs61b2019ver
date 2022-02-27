package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private List<Node> nodes;
    private List<Point> points;
    private Map<Point, Node> mapNodePoint;
    private MyTrieSet trie;
    private Map<String, String> mapName;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        nodes = this.getNodes();
        points = new ArrayList<>();
        mapNodePoint = new HashMap<>();
        trie = new MyTrieSet();
        mapName = new HashMap<>();

        for(Node n: nodes) {
            if (neighbors(n.id()).size() > 0) {
                Point temp = new Point(n.lon(), n.lat());
                points.add(temp);
                mapNodePoint.put(temp, n);
            }

            if (n.name() != null && n.name().length() >= 1) {
                String name = cleanString(n.name());
                if (name.length() >= 1) {
                    trie.add(name);
                    mapName.put(name, n.name());
                }
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        WeirdPointSet w = new WeirdPointSet(points);
        Point near = w.nearest(lon, lat);
        System.out.println(near.toString());
        return mapNodePoint.get(near).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        List<String> trieWithPrefix = trie.keysWithPrefix(prefix);
        if (trieWithPrefix != null) {
            for(String s: trieWithPrefix) {
                result.add(mapName.get(s));
            }
        }
        return result;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Node n : nodes) {
            if (cleanString(n.name()).equals(cleanString(locationName))) {
                Map<String, Object> m = new HashMap<>();
                m.put("lat", n.lat());
                m.put("lon", n.lon());
                m.put("name", n.name());
                m.put("id", n.id());
                result.add(m);
            }
        }
        return result;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
