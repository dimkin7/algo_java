// https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n ≤ 0 or trials ≤ 0");
        }
        results = new double[trials];
        this.trials = trials;

        // performs T independent computational experiments
        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                // Use StdRandom to generate random numbers;
                per.open(StdRandom.uniformInt(n) + 1, StdRandom.uniformInt(n) + 1);
            }
            results[i] = (double) per.numberOfOpenSites() / (double) (n * n);
        }

    }

    //  use StdStats to compute the sample mean and sample standard deviation.
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / (Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / (Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");
    }

}
