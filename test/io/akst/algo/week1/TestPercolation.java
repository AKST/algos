package io.akst.algo.week1;

import org.junit.Test;
import org.junit.Assert;

public class TestPercolation extends Assert {

  @Test public void initiallyClosed() {
    Percolation p = new Percolation(2);
    assertFalse(p.isOpen(0, 0));
  }

  @Test public void openWillOpen() {
    Percolation p = new Percolation(2);
    p.open(0, 0);
    assertTrue(p.isOpen(0, 0));
  }

  @Test public void twoDownOpenIsFull() {
    Percolation p = new Percolation(3);
    p.open(0, 0);
    p.open(1, 0);
    assertTrue(p.isFull(1, 0));
  }

  @Test public void singleNonSurfaceOpenIsNotFull() {
    Percolation p = new Percolation(3);
    p.open(1, 0);
    assertFalse(p.isFull(1, 0));
  }

  @Test public void byDefaultNoPercolation() {
    Percolation p = new Percolation(3);
    assertFalse(p.percolates());
  }

  @Test public void straighDownPercolation() {
    Percolation p = new Percolation(3);
    p.open(0, 0);
    p.open(1, 0);
    p.open(2, 0);
    assertTrue(p.percolates());
  }

}
