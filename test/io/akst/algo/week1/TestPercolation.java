package io.akst.algo.week1;

import org.junit.Test;
import org.junit.Assert;

public class TestPercolation extends Assert {

  @Test public void initiallyClosed() {
    Percolation p = new Percolation(2);
    assertFalse(p.isOpen(1, 1));
  }

  @Test public void openWillOpen() {
    Percolation p = new Percolation(2);
    p.open(1, 1);
    assertTrue(p.isOpen(1, 1));
  }

  @Test public void twoDownOpenIsFull() {
    Percolation p = new Percolation(3);
    p.open(1, 1);
    p.open(2, 1);
    assertTrue(p.isFull(2, 1));
  }

  @Test public void singleNonSurfaceOpenIsNotFull() {
    Percolation p = new Percolation(3);
    p.open(2, 1);
    assertFalse(p.isFull(2, 1));
  }

  @Test public void byDefaultNoPercolation() {
    Percolation p = new Percolation(3);
    assertFalse(p.percolates());
  }

  @Test public void straighDownPercolation() {
    Percolation p = new Percolation(3);
    p.open(1, 1);
    p.open(2, 1);
    p.open(3, 1);
    assertTrue(p.percolates());
  }

}
