package io.akst.algo.week2;

import org.junit.Test;
import org.junit.Assert;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class TestDeque extends Assert {

	@Test public void emptyOnInit() {
		Deque<Boolean> bools = new Deque<>();
    assertTrue(bools.isEmpty());
    Assert.assertEquals(bools.size(), 0);
  }

	@Test public void appendingIncreaseSize() {
		Deque<Boolean> bools = new Deque<>();
    bools.addFirst(true);
    Assert.assertEquals(bools.size(), 1);
	}

  @Test public void addFirstAndRemoveFirstEmpty() {
		Deque<Boolean> bools = new Deque<>();
    bools.addFirst(true);
    bools.removeFirst();
    assertTrue(bools.isEmpty());
    Assert.assertEquals(bools.size(), 0);
  }

  @Test public void addFirstAndRemoveLastEmpty() {
		Deque<Boolean> bools = new Deque<>();
    bools.addFirst(true);
    bools.removeLast();
    assertTrue(bools.isEmpty());
    Assert.assertEquals(bools.size(), 0);
  }

  @Test public void addLastAndRemoveFirstEmpty() {
		Deque<Boolean> bools = new Deque<>();
    bools.addLast(true);
    bools.removeFirst();
    assertTrue(bools.isEmpty());
    Assert.assertEquals(bools.size(), 0);
  }

  @Test public void addLastAndRemoveLastEmpty() {
		Deque<Boolean> bools = new Deque<>();
    bools.addLast(true);
    bools.removeLast();
    assertTrue(bools.isEmpty());
    Assert.assertEquals(bools.size(), 0);
  }

  @Test public void add5FirstRemove5FirstEqual() {
    Deque<Integer> ints = new Deque<>();
    ints.addFirst(1); // (1)
    ints.addFirst(2); // (2) 1
    ints.addFirst(3); // (3) 2 1
    ints.addFirst(4); // (4) 3 2 1
    ints.addFirst(5); // (5) 4 3 2 1
    Assert.assertEquals((int) ints.removeFirst(), 5); // (5) 4 3 2 1
    Assert.assertEquals((int) ints.removeFirst(), 4); // (4) 3 2 1
    Assert.assertEquals((int) ints.removeFirst(), 3); // (3) 2 1
    Assert.assertEquals((int) ints.removeFirst(), 2); // (2) 1
    Assert.assertEquals((int) ints.removeFirst(), 1); // (1)

    assertTrue(ints.isEmpty());
    Assert.assertEquals(ints.size(), 0);
  }

  @Test public void add5FirstRemove5LastEqual() {
    Deque<Integer> ints = new Deque<>();
    ints.addFirst(1); // (1)
    ints.addFirst(2); // (2) 1
    ints.addFirst(3); // (3) 2 1
    ints.addFirst(4); // (4) 3 2 1
    ints.addFirst(5); // (5) 4 3 2 1
    Assert.assertEquals((int) ints.removeLast(), 1); // 5 4 3 2 (1)
    Assert.assertEquals((int) ints.removeLast(), 2); // 5 4 3 (2)
    Assert.assertEquals((int) ints.removeLast(), 3); // 5 4 (3)
    Assert.assertEquals((int) ints.removeLast(), 4); // 5 (4)
    Assert.assertEquals((int) ints.removeLast(), 5); // (5)

    assertTrue(ints.isEmpty());
    Assert.assertEquals(ints.size(), 0);
  }

  @Test public void add5LastRemove5FirstEqual() {
    Deque<Integer> ints = new Deque<>();
    ints.addLast(1); // (1)
    ints.addLast(2); // 1 (2)
    ints.addLast(3); // 1 2 (3)
    ints.addLast(4); // 1 2 3 (4)
    ints.addLast(5); // 1 2 3 4 (5)
    Assert.assertEquals((int) ints.removeFirst(), 1); // (1) 2 3 4 5
    Assert.assertEquals((int) ints.removeFirst(), 2); // (2) 3 4 5
    Assert.assertEquals((int) ints.removeFirst(), 3); // (3) 4 5
    Assert.assertEquals((int) ints.removeFirst(), 4); // (4) 5
    Assert.assertEquals((int) ints.removeFirst(), 5); // (5)

    assertTrue(ints.isEmpty());
    Assert.assertEquals(ints.size(), 0);
  }

  @Test public void add5LastRemove5LastEqual() {
    Deque<Integer> ints = new Deque<>();
    ints.addLast(1); // (1)
    ints.addLast(2); // 1 (2)
    ints.addLast(3); // 1 2 (3)
    ints.addLast(4); // 1 2 3 (4)
    ints.addLast(5); // 1 2 3 4 (5)
    Assert.assertEquals((int) ints.removeLast(), 5); // 1 2 3 4 (5)
    Assert.assertEquals((int) ints.removeLast(), 4); // 1 2 3 (4)
    Assert.assertEquals((int) ints.removeLast(), 3); // 1 2 (3)
    Assert.assertEquals((int) ints.removeLast(), 2); // 1 (2)
    Assert.assertEquals((int) ints.removeLast(), 1); // (1)

    assertTrue(ints.isEmpty());
    Assert.assertEquals(ints.size(), 0);
  }

  @Test(expected=NullPointerException.class)
  public void throwOnAddFirstNull() {
    Deque<Object> objects = new Deque<>();
    objects.addFirst(null);
  }

  @Test(expected=NullPointerException.class)
  public void throwOnAddLastNull() {
    Deque<Object> objects = new Deque<>();
    objects.addLast(null);
  }

  @Test(expected=NoSuchElementException.class)
  public void throwOnRemoveFirstWhileEmpty() {
    Deque<Object> objects = new Deque<>();
    objects.removeFirst();
  }

  @Test(expected=NoSuchElementException.class)
  public void throwOnRemoveLastWhileEmpty() {
    Deque<Object> objects = new Deque<>();
    objects.removeLast();
  }

  @Test(expected=NoSuchElementException.class)
  public void throwOnIteratorNextWhileEmpty() {
    Deque<Object> objects = new Deque<>();
    Iterator<Object> iter = objects.iterator();
    iter.next();
  }

  @Test(expected=UnsupportedOperationException.class)
  public void throwOnIteratorRemove() {
    Deque<Object> objects = new Deque<>();
    Iterator<Object> iter = objects.iterator();
    iter.remove();
  }

}
