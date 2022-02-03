import edu.princeton.cs.introcs.StdRandom;

import java.security.Key;

/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        int result = 0;
        for (int i = 1; i <= N; i++) {
            int depth = (int) Math.floor(Math.log(i) / Math.log(2));
            result += depth;
        }
        return result;
    }


    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        return (double) optimalIPL(N) / (double) N;
    }

    public static double deleteAndInsert(BST bst, int M) {
        bst.deleteTakingSuccessor(bst.getRandomKey());
        int item = StdRandom.uniform(M);
        if (!bst.contains(item)) {
            bst.add(item);
        }
        return bst.averageDepth();
    }

    public static double deleteAndInsertEx3(BST bst, int M) {
        bst.deleteTakingRandom(bst.getRandomKey());
        int item = StdRandom.uniform(M);
        if (!bst.contains(item)) {
            bst.add(item);
        }
        return bst.averageDepth();
    }

//    public static void main(String[] args) {
////        System.out.println(Math.floor(Math.log(4) / Math.log(2)));
//        System.out.println(optimalIPL(8));
//        System.out.println(optimalAverageDepth(8));
//    }
}
