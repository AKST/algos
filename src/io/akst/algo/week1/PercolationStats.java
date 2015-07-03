package io.akst.algo.week1;


import java.util.Random;


public class PercolationStats {

  final private Random random = new Random();
  final private double[] times;

  final private int N;
  final private int T;

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
    double sum = 0;
    for (int index = 0; index < this.T; index++) {
      sum += this.times[index];
    }
    return sum / this.T;
  }

  public double stddev() {
    this.run();
    double sum = 0;
    double sqSum = 0;
    for (int index = 0; index < this.T; index++) {
      sum   += this.times[index];
      sqSum += this.times[index] * this.times[index];
    }
    double mean = sum / this.T;
    double variance = sqSum / this.T - mean * mean;
    double stddev =  Math.sqrt(variance);
    return stddev;
  }

  public double confidenceLo() {
    this.run();
    return this.mean() - 1.96 * this.stddev();
  }

  public double confidenceHi() {
    this.run();
    return this.mean() + 1.96 * this.stddev();
  }

  /**
   * whether the experinment has run yet, this is persisted
   * PercolationStats doesn't run the experinment more than once
   */
  private boolean hasRan = false;

  /**
   * runs if the experiment has not yet run.
   */
  private void run() {
    if (!this.hasRan) {
      for (int iteration = 0; iteration < this.T; iteration++) {

        long startTime = System.nanoTime();

        Percolation percolation = new Percolation(this.N);
        do {
          int row = random.nextInt(N);
          int col = random.nextInt(N);
          percolation.open(row, col);
        } while (percolation.percolates());

        long endTime = System.nanoTime();
        this.times[iteration] = (endTime - startTime) / 1E9;
      }
      this.hasRan = true;
    }
  }

  public static void main(String[] args) {
    int gridSize    = Integer.parseInt(args[0], 10);
    int experiments = Integer.parseInt(args[1], 10);

    PercolationStats stats = new PercolationStats(gridSize, experiments);

    double mean         = stats.mean();
    double stddev       = stats.stddev();
    double confidenceLo = stats.confidenceLo();
    double confidenceHi = stats.confidenceHi();

    //
    // %-19s is for leveling output with the length
    // of the largest prefix `confidence interval`.
    //
    System.out.format("%-19s = %f%n",    "mean",                mean);
    System.out.format("%-19s = %f%n",    "stddev",              stddev);
    System.out.format("%-19s = %f %f%n", "confidence interval", confidenceLo, confidenceHi);
  }

}
