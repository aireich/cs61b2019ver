package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    int N;
    int T;
    PercolationFactory pf;
    Percolation p;
    int[] xt;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        this.N = N;
        this.pf = pf;
        this.p = pf.make(N);
        this.xt = new int[T];
        for (int i = 0; i < T; i++) {
            int cnt = 0;
            while(!this.p.percolates()) {
                int row = StdRandom.uniform(N * N - 1);
                int col = StdRandom.uniform(N * N - 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    cnt += 1;
                }
            }
            this.xt[i] = cnt;
        }
    }
    public double mean() {
        return StdStats.mean(this.xt);
    }
    public double stddev() {
        return StdStats.stddev(this.xt);
    }
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
