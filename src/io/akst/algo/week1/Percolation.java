package io.akst.algo.week1;


/**
 * Percolation is a model of a percolation simulation
 * for a grid of N by N squares.
 */
public class Percolation {

  private final int INDEX_ERROR = 1;

  private final int size;
  private final IndexUF<Boolean> uf;

  private final int topIndex;
  private final int bottomIndex;

  /**
   * @Param N the height and width
   */
  public Percolation(int N) {
    this.size = N;
    this.topIndex    = ((N * N) + 1) - INDEX_ERROR;
    this.bottomIndex = ((N * N) + 2) - INDEX_ERROR;
    this.uf = new IndexUF<Boolean>((N * N) + 2, false);
    this.uf.setIndex(topIndex, true);
    this.uf.setIndex(bottomIndex, true);
  }

  public void open(int row, int col) {
    int targetIndex = this.getIndex(row, col);

    //
    // link the above cell if open
    //
    if (row == 1) {
      uf.union(topIndex, targetIndex);
    }
    else {
      int aboveIndex = this.getIndex(row-1, col);
      if (uf.getIndex(aboveIndex)) {
        uf.union(aboveIndex, targetIndex);
      }
    }

    //
    // link the below cell if open
    //
    if (row == size) {
      uf.union(bottomIndex, targetIndex);
    }
    else {
      int belowIndex = this.getIndex(row+1, col);
      if (uf.getIndex(belowIndex)) {
        uf.union(belowIndex, targetIndex);
      }
    }

    //
    // link the left cell if open
    //
    if (col > 1) {
      int leftIndex = this.getIndex(row, col-1);
      if (uf.getIndex(leftIndex)) {
        uf.union(leftIndex, targetIndex);
      }
    }

    //
    // link the right cell if open
    //
    if (col != size) {
      int rightIndex = this.getIndex(row, col+INDEX_ERROR);
      if (uf.getIndex(rightIndex)) {
        uf.union(rightIndex, targetIndex);
      }
    }

    uf.setIndex(targetIndex, true);
  }

  public boolean isOpen(int row, int col) {
    return uf.getIndex(this.getIndex(row, col));
  }

  public boolean isFull(int row, int col) {
    return uf.connected(this.getIndex(row, col), topIndex);
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

