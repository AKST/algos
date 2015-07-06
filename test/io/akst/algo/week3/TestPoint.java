package io.akst.algo.week3;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Ignore;

public class TestPoint extends Assert {

  //
  // comparison
  //

  @Test public void comp_0n0_0n0() {
    Point a = new Point(0, 0);
    Point b = new Point(0, 0);
    Assert.assertEquals(a.compareTo(b), 0);
  }

  @Test public void comp_1n0_0n0() {
    Point a = new Point(1, 0);
    Point b = new Point(0, 0);
    Assert.assertEquals(a.compareTo(b), 1);
  }

  @Test public void comp_0n1_0n0() {
    Point a = new Point(0, 1);
    Point b = new Point(0, 0);
    Assert.assertEquals(a.compareTo(b), 1);
  }

  @Test public void comp_0n0_1n0() {
    Point a = new Point(0, 0);
    Point b = new Point(1, 0);
    Assert.assertEquals(a.compareTo(b), -1);
  }

  @Test public void comp_0n0_0n1() {
    Point a = new Point(0, 0);
    Point b = new Point(0, 1);
    Assert.assertEquals(a.compareTo(b), -1);
  }

  @Test public void comp_1n0_1n0() {
    Point a = new Point(1, 0);
    Point b = new Point(1, 0);
    Assert.assertEquals(a.compareTo(b), 0);
  }

  @Test public void comp_1n1_1n0() {
    Point a = new Point(1, 1);
    Point b = new Point(1, 0);
    Assert.assertEquals(a.compareTo(b), 1);
  }

  @Test public void comp_1n0_1n1() {
    Point a = new Point(1, 0);
    Point b = new Point(1, 1);
    Assert.assertEquals(a.compareTo(b), -1);
  }

  //
  // slope
  //

  @Test public void slope_0n0_0n0() {
    Point a = new Point(0, 0);
    Point b = new Point(0, 0);
    Assert.assertEquals(a.slopeTo(b), (0.0 - 0.0) / (0.0 - 0.0), 1);
  }

  @Test public void slope_0n0_5n0() {
    Point a = new Point(0, 0);
    Point b = new Point(5, 0);
    Assert.assertEquals(a.slopeTo(b), (0.0 - 0.0) / (5.0 - 0.0), 1);
  }

  @Test public void slope_5n0_0n0() {
    Point a = new Point(5, 0);
    Point b = new Point(0, 0);
    Assert.assertEquals(a.slopeTo(b), (0.0 - 0.0) / (0.0 - 5.0), 1);
  }

  @Test public void slope_0n0_0n5() {
    Point a = new Point(0, 0);
    Point b = new Point(0, 5);
    Assert.assertEquals(a.slopeTo(b), (5.0 - 0.0) / (0.0 - 0.0), 1);
  }

  @Test public void slope_0n5_0n0() {
    Point a = new Point(0, 5);
    Point b = new Point(0, 0);
    Assert.assertEquals(a.slopeTo(b), (0.0 - 5.0) / (0.0 - 0.0), 1);
  }

  @Test public void slope_5n0_5n0() {
    Point a = new Point(5, 0);
    Point b = new Point(5, 0);
    Assert.assertEquals(a.slopeTo(b), (0.0 - 0.0) / (5.0 - 5.0), 1);
  }

  @Test public void slope_5n0_5n5() {
    Point a = new Point(5, 0);
    Point b = new Point(5, 5);
    Assert.assertEquals(a.slopeTo(b), (5.0 - 0.0) / (5.0 - 5.0), 1);
  }

  @Test public void slope_5n5_5n0() {
    Point a = new Point(5, 5);
    Point b = new Point(5, 0);
    Assert.assertEquals(a.slopeTo(b), (0.0 - 5.0) / (5.0 - 5.0), 1);
  }

  @Test public void slope_5n5_5n5() {
    Point a = new Point(5, 5);
    Point b = new Point(5, 5);
    Assert.assertEquals(a.slopeTo(b), (5.0 - 5.0) / (5.0 - 5.0), 1);
  }

  //
  // SLOPE_ORDER
  //


  // |
  // |   C
  // |
  // A _ _ _
  // |
  // |   B
  // |
  @Test public void slopeHMirrorLesser() {
    Point a = new Point(0, 0);
    Point b = new Point(2, -2);
    Point c = new Point(2, 2);
    Assert.assertEquals(-1, a.SLOPE_ORDER.compare(b, c));
  }

  //       |
  //   B   |   C
  //       |
  // _ _ _ A _ _ _
  @Test public void slopeVMirrorLesser() {
    Point a = new Point(0, 0);
    Point b = new Point(-2, 2);
    Point c = new Point(2, 2);
    Assert.assertEquals(-1, a.SLOPE_ORDER.compare(b, c));
  }

  //       |
  //       |   C
  //       |
  // _ _ _ A _ _ _
  //       |
  //   B   |
  //       |
  @Test public void slopeInvertEqual() {
    Point a = new Point(0, 0);
    Point b = new Point(2, 2);
    Point c = new Point(-2, -2);
    Assert.assertEquals(0, a.SLOPE_ORDER.compare(b, c));
  }

  // |
  // |   C
  // | B
  // A _ _ _
  @Test public void slopeSameEqual() {
    Point a = new Point(0, 0);
    Point b = new Point(1, 1);
    Point c = new Point(2, 2);
    Assert.assertEquals(0, a.SLOPE_ORDER.compare(b, c));
  }

  // |
  // |   C
  // |   B
  // A _ _ _
  @Test public void slopeBelowLess() {
    Point a = new Point(0, 0);
    Point b = new Point(2, 1);
    Point c = new Point(2, 2);
    Assert.assertEquals(-1, a.SLOPE_ORDER.compare(b, c));
  }

  // |
  // | B C
  // |
  // A _ _ _
  @Test public void slopeAboveLess() {
    Point a = new Point(0, 0);
    Point b = new Point(1, 2);
    Point c = new Point(2, 2);
    Assert.assertEquals(1, a.SLOPE_ORDER.compare(b, c));
  }

}
