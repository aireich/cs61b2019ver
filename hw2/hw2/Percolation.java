package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    int[][] grid;
    int N;
    UnionFind u;
    int top;
    int bottom;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException();
        }
        this.grid = new int[N][N];
        this.N = N;
        this.u = new UnionFind(N * N + 2);
        this.top = N * N;
        this.bottom = N * N + 1;
        for (int i = 0; i < N; i++) {
            u.union(xyTo1d(0, i), top);
        }
        for (int i = 0; i < N; i++) {
            u.union(xyTo1d(N - 1, i), bottom);
        }
    }


    /**Private helper method to translate 2d coordinates into a 1d representative number**/
    private int xyTo1d(int x, int y) {
        if (x >= N || y >= N || x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }
        return N * x + y;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row >= N || row < 0 || col >= N || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        grid[row][col] = 1;
        // If a neighbor is open, then this site will be open
        if (grid[bound(row - 1)][ bound(col)] == 1 && (bound(row - 1) != row || bound(col) != col)) {
            u.union(xyTo1d(row, col), xyTo1d(bound(row - 1), bound(col)));
        }
       if (grid[bound(row)][ bound(col - 1)] == 1 && (bound(row) != row || bound(col - 1) != col)) {
            u.union(xyTo1d(row, col), xyTo1d(bound(row), bound(col - 1)));
        }
        if (grid[bound(row + 1)][ bound(col)] == 1 && (bound(row + 1) != row || bound(col) != col)) {
            u.union(xyTo1d(row, col), xyTo1d(bound(row + 1), bound(col)));
        }
        if (grid[bound(row)][ bound(col + 1)] == 1 && (bound(row) != row || bound(col + 1) != col)) {
            u.union(xyTo1d(row, col), xyTo1d(bound(row), bound(col + 1)));
        }
    }


    /**Private helper method to translate given coordinates into valid coordinates**/
    private int bound(int x) {
        if (x >= N) {
            return N - 1;
        } else if (x < 0) {
            return 0;
        }
        return x;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return u.connected(xyTo1d(row, col), top) && u.connected(xyTo1d(row, col), bottom);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return u.connected(xyTo1d(row, col), top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        if (!percolates()) {
            return 0;
        }
        if (u.find(top) == top || u.find(bottom) == bottom) {
            return Math.abs(u.sizeOf(top) - u.sizeOf(bottom));
        } else {
            return 1;
        }
    }

    // does the system percolate?
    public boolean percolates() {
        return u.connected(top, bottom);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {

    }
}
