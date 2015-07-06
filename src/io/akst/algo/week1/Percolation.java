package io.akst.algo.week1;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * Percolation is a model of a percolation simulation
 * for a grid of N by N squares.
 */
public class Percolation {

  private static final int INDEX_ERROR = 1;

  private final int size;
  private final WeightedQuickUnionUF uf;
  private final boolean[] siteStatus;

  private final int topIndex;
  private final int bottomIndex;

  /**
   * @Param N the height and width
   */
  public Percolation(int N) {
    this.size = N;
    this.topIndex    = ((N * N) + 1) - INDEX_ERROR;
    this.bottomIndex = ((N * N) + 2) - INDEX_ERROR;
    this.siteStatus = new boolean[(N * N) + 2];
    this.siteStatus[topIndex] = true;
    this.siteStatus[bottomIndex] = true;
    this.uf = new WeightedQuickUnionUF((N * N) + 2);
  }

  public void open(int row, int col) {
    int targetIndex = this.getIndex(row, col);

    // if site open continue
    if (this.siteStatus[targetIndex]) { return; }

    //
    // link the above cell if open
    //
    if (row == 1) {
      uf.union(topIndex, targetIndex);
      int belowIndex = this.getIndex(row+1, col);
      if (this.siteStatus[belowIndex]) {
        uf.union(targetIndex, belowIndex);
      }
    }
    //
    // link the below cell if open
    //
    else if (row == size) {
      uf.union(targetIndex, bottomIndex);
      int aboveIndex = this.getIndex(row-1, col);
      if (this.siteStatus[aboveIndex]) {
        uf.union(targetIndex, aboveIndex);
      }
    }
    //
    // in between the top and the bottom
    //
    else {
      int aboveIndex = this.getIndex(row-1, col);
      if (this.siteStatus[aboveIndex]) {
        uf.union(targetIndex, aboveIndex);
      }
      int belowIndex = this.getIndex(row+1, col);
      if (this.siteStatus[belowIndex]) {
        uf.union(targetIndex, belowIndex);
      }
    }

    //
    // link the below cell if open
    //

    //
    // link the left cell if open
    //
    if (col > 1) {
      int leftIndex = this.getIndex(row, col-1);
      if (this.siteStatus[leftIndex]) {
        uf.union(targetIndex, leftIndex);
      }
    }

    //
    // link the right cell if open
    //
    if (col != size) {
      int rightIndex = this.getIndex(row, col+INDEX_ERROR);
      if (this.siteStatus[rightIndex]) {
        uf.union(targetIndex, rightIndex);
      }
    }

    this.siteStatus[targetIndex] = true;
  }

  public boolean isOpen(int row, int col) {
    int index = this.getIndex(row, col);
    return this.siteStatus[index];
  }

  public boolean isFull(int row, int col) {
    int index = this.getIndex(row, col);
    return uf.connected(index, topIndex);
  }

  public boolean percolates() {
    return uf.connected(topIndex, bottomIndex);
  }

  private int getIndex(int rawRow, int rawCol) {
    int row = rawRow - INDEX_ERROR;
    int col = rawCol - INDEX_ERROR;

    if (row >= size || row < 0) {
      String message = "row index "+rawRow+" out of bounds";
      throw new IndexOutOfBoundsException(message);
    }
    else if (col >= size || col < 0) {
      String message = "col index "+rawCol+" out of bounds";
      throw new IndexOutOfBoundsException(message);
    }
    else {
      return (row * this.size) + col;
    }
  }

  public static void main(String[] args) {}

}

