import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    private List<Bear> bears;
    private List<Bed> beds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        this.bears = bears;
        this.beds = beds;
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        List<Bear> result = new ArrayList<Bear>();
        for (int i = 0; i < bears.size(); i++) {
            if (bears.get(i).compareTo(beds.get(i)) == 0) {
                result.add(bears.get(i));
            }
        }
        return result;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        List<Bed> result = new ArrayList<Bed>();
        for (int i = 0; i < bears.size(); i++) {
            if (bears.get(i).compareTo(beds.get(i)) == 0) {
                result.add(beds.get(i));
            }
        }
        return result;
    }
}
