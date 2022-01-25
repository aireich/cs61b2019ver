/***Implementation of Weighted Quick Union
 * @author aireich*/

public class UnionFind {

    /**The representation of initial set*/
    int[] parent;

    /** Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets.
       @param n the number of items*/
    public UnionFind(int n) {
        this.parent = new int[n];
        for (int i = 0; i < parent.length; i++) {
            this.parent[i] = i;
        }
    }

    /** Throws an exception if v1 is not a valid index.
     * @param vertex an int item*/
    private void validate(int vertex) {
        if (vertex > parent.length || vertex < 0) {
            throw new IllegalArgumentException("Not a valid vertex");
        }
    }

    /** Returns the size of the set v1 belongs to.
     * @param v1 an int item
     * @return the size of the set which v1 belongs to*/
    public int sizeOf(int v1) {
        validate(v1);
        if (parent[find(v1)] < 0) {
            return (parent[find(v1)]) / -1;
        }
        return 1;
    }

    /** Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root.
     @param v1 an int item
     @return the parent int node of v1*/
    public int parent(int v1) {
        validate(v1);
        return this.parent[v1];
    }

    /** Returns true if nodes v1 and v2 are connected.
     * @param v1 an int node
     * @param v2 an int node
     * @return if the two int nodes are connected*/
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (find(v1) == find(v2)) {
            return true;
        }
        return false;
    }

    /** Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data.
     @param v1 an int node
     @param v2 an int node*/
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (parent[find(v1)] == v1) {
            parent[v1] = -1;
        }
        if (parent[find(v2)] == v2) {
            parent[v2] = -1;
        }

        if (connected(v1, v2)) {
            return;
        }
        int v2root = find(v2);
        int v1root = find(v1);
        if (sizeOf(v1) >= sizeOf(v2)) {
            parent[v1root] =  (parent[v1root] / -1 + parent[v2root] / -1) * (-1);
            parent[find(v2)] = v1;
        } else {
            parent[find(v2)] = (parent[find(v2)] / -1 + parent[v1root] / -1) * (-1);
            parent[find(v1)] = v2;
        }

    }

    /** Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time.
     @param vertex an int node
     @return the root int node of vertex*/
    public int find(int vertex) {
        while (parent[vertex] >= 0 && parent[vertex] != vertex) {
            vertex = parent[vertex];
        }
        return vertex;
    }

}
