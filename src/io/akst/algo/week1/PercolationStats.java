package io.akst.algo.week1;


import java.util.Random;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.Stopwatch;


public class PercolationStats {

  private final Random random = new Random();
  private final double[] times;

  private final int N;
  private final int T;


  /**
   * whether the experinment has run yet, this is persisted
   * PercolationStats doesn't run the experinment more than once
   */
  private boolean hasRan = false;

  public PercolationStats(final int N, final int T) {
    if (N < 0 || T < 0) {
      throw new IllegalArgumentException("T and N cannot be less than 0");
    }
    this.times = new double[T];
    this.N = N;
    this.T = T;
  }

  public double mean() {
    this.run();
    return StdStats.mean(this.times);
  }

  public double stddev() {
    this.run();
    return StdStats.stddev(this.times);
  }

  public double confidenceLo() {
    this.run();
    return this.mean() - (1.96 * this.stddev());
  }

  public double confidenceHi() {
    this.run();
    return this.mean() + (1.96 * this.stddev());
  }

  /**
   * runs if the experiment has not yet run.
   */
  private void run() {
    if (!this.hasRan) {
      Stopwatch watch = new Stopwatch();
      for (int iteration = 0; iteration < this.T; iteration++) {

        double startTime = watch.elapsedTime();

        Percolation percolation = new Percolation(this.N);
        do {
          int row = 1 + random.nextInt(N);
          int col = 1 + random.nextInt(N);
          percolation.open(row, col);
        } while (percolation.percolates());

        double endTime = watch.elapsedTime();
        this.times[iteration] = endTime - startTime;
      }
      this.hasRan = true;
    }
  }

  public static void main(String[] args) {
    int gridSize    = Integer.parseInt(args[0], 10);
    int experiments = Integer.parseInt(args[1], 10);

    PercolationStats stats = new PercolationStats(gridSize, experiments);

    double mean   = stats.mean();
    double stddev = stats.stddev();
    double coLo   = stats.confidenceLo();
    double coHi   = stats.confidenceHi();

    //
    // %-19s is for leveling output with the length
    // of the largest prefix `confidence interval`.
    //
    System.out.format("%-23s = %f%n", "mean", mean);
    System.out.format("%-23s = %f%n", "stddev", stddev);
    System.out.format("%-23s = %f %f%n", "95% confidence interval", coLo, coHi);
  }

}
