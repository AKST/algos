package io.akst.algo.week2;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Assert;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class TestRandomizedQueue extends Assert {


  @Test public void handlesEnqueuingOverInitArraySize() {
		RandomizedQueue<Integer> ints = new RandomizedQueue<>();
    for (int i = 0; i < 100; i++) {
      ints.enqueue(i);
    }
    Assert.assertEquals(ints.size(), 100);
  }

  @Test public void handlesDequeing() {
		RandomizedQueue<Integer> ints = new RandomizedQueue<>();
    for (int i = 0; i < 100; i++) {
      ints.enqueue(i);
    }
    for (int i = 0; i < 100; i++) {
      assertNotNull(ints.dequeue());
    }
    Assert.assertEquals(ints.size(), 0);
  }

  @Test public void isEmptyBeforeEnqueue() {
    RandomizedQueue<Object> objects = new RandomizedQueue<>();
    assertTrue(objects.isEmpty());
    Assert.assertEquals(objects.size(), 0);
  }

	@Test public void appendingIncreaseSize() {
		RandomizedQueue<Boolean> bools = new RandomizedQueue<>();
    bools.enqueue(true);
    Assert.assertEquals(bools.size(), 1);
	}

  @Test public void addFirstAndRemoveFirstEmpty() {
		RandomizedQueue<Boolean> bools = new RandomizedQueue<>();
    bools.enqueue(true);
    Assert.assertEquals(bools.dequeue(), true);
    assertTrue(bools.isEmpty());
    Assert.assertEquals(bools.size(), 0);
  }

  @Test(expected=NullPointerException.class)
  public void throwOnEnqueueNull() {
    RandomizedQueue<Object> objects = new RandomizedQueue<>();
    objects.enqueue(null);
  }

  @Test(expected=NoSuchElementException.class)
  public void throwOnSampleWhileEmpty() {
    RandomizedQueue<Object> objects = new RandomizedQueue<>();
    objects.sample();
  }

  @Test(expected=NoSuchElementException.class)
  public void throwOnDequeueWhileEmpty() {
    RandomizedQueue<Object> objects = new RandomizedQueue<>();
    objects.dequeue();
  }

  @Test(expected=NoSuchElementException.class)
  public void throwOnIteratorNextWhileEmpty() {
    RandomizedQueue<Object> objects = new RandomizedQueue<>();
    Iterator<Object> iter = objects.iterator();
    iter.next();
  }

  @Test(expected=UnsupportedOperationException.class)
  public void throwOnIteratorRemove() {
    RandomizedQueue<Object> objects = new RandomizedQueue<>();
    Iterator<Object> iter = objects.iterator();
    iter.remove();
  }

}
