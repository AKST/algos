package io.akst.algo.week3;

import edu.princeton.cs.introcs.StdDraw;
import java.util.Comparator;

class Point implements Comparable<Point> {

  public final Comparator<Point> SLOPE_ORDER;
  private final int x;
  private final int y;

  public Point(int initX, int initY) {
    this.x = initX;
    this.y = initY;
    this.SLOPE_ORDER = new SlopeComparator();
  }

  public void draw() {
    StdDraw.point(this.x, this.y);
  }

  public void drawTo(Point that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  public int compareTo(Point that) {
    if (this.y > that.y) {
      return 1;
    }
    else if (this.y == that.y) {
      if (this.x > that.x) {
        return 1;
      }
      else if (this.x == that.x) {
        return 0;
      }
      else {
        return -1;
      }
    }
    else {
      return -1;
    }
  }

  public double slopeTo(Point that) {

    return (((double) that.y) -
            ((double) this.y))
         / (((double) that.x) -
            ((double) this.x));
  }

  public String toString() {
    return "Point("+this.x+","+this.y+")";
  }

  private final class SlopeComparator implements Comparator<Point> {
    public int compare(Point a, Point b) {
      if (a.compareTo(b) == 0) {
        return 0;
      }
      else if (Point.this.slopeTo(a) == Point.this.slopeTo(b)) {
        return 0;
      }
      else if (Point.this.slopeTo(a) > Point.this.slopeTo(b)) {
        return 1;
      }
      else {// (Point.this.slopeTo(a) < Point.this.slopeTo(b)) {
        return -1;
      }
    }
  }
}

