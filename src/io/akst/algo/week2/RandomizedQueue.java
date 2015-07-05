package io.akst.algo.week2;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Random;

/**
 * RandomizedQueue.java
 * @author Angus K S Thomsen
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

  private static final Random RANDOM = new Random();
  private Item[] items;
  private int size;

  @SuppressWarnings("unchecked")
  public RandomizedQueue() {
    this.items = (Item[]) new Object[1];
    this.size = 0;
  }

  public boolean isEmpty() {
    return this.size == 0;
  }

  public int size() {
    return size;
  }

  /**
   * O(1m)
   */
  public void enqueue(Item newItem) {
    if (newItem == null) {
      throw new NullPointerException();
    }
    else if (this.size == this.items.length) {
      this.resize(this.items.length * 2);
    }
    this.items[this.size] = newItem;
    this.size += 1;
  }

  /**
   * O(1m)
   */
  public Item dequeue() {
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    int derefIndex = RANDOM.nextInt(this.size);
    Item result = this.items[derefIndex];
    this.items[derefIndex] = this.items[this.size - 1];

    //
    // prevent loitering
    //
    this.items[this.size - 1] = null;
    this.size -= 1;

    if (this.size <= this.items.length / 4) {
      this.resize(this.items.length / 4);
    }

    return result;
  }

  /**
   * 0(1)
   */
  public Item sample() {
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    int derefIndex = RANDOM.nextInt(this.size);
    Item result = this.items[derefIndex];
    this.items[derefIndex] = this.items[this.size - 1];

    return result;
  }

  /**
   * 0(n)
   */
  @SuppressWarnings("unchecked")
  public Iterator<Item> iterator() {
    Item[] iterItems = (Item[]) new Object[this.size];

    for (int i = 0; i < this.size; i++) {
      iterItems[i] = this.items[i];
    }
    return new APIRandomizedQueueIterator(iterItems);
  }

  /**
   * used for the growing and shrinking of the array
   */
  @SuppressWarnings("unchecked")
  private void resize(int newArraySize) {
    Item[] newItems = (Item[]) new Object[newArraySize];
    Item[] oldItems = this.items;

    for (int i = 0; i < this.size; i++) {
      newItems[i] = oldItems[i];
      oldItems[i] = null;
    }

    this.items = newItems;
  }

  private class APIRandomizedQueueIterator implements Iterator<Item> {
    private Item[] items;
    private int index;

    public APIRandomizedQueueIterator(Item[] initialItems) {
      this.items = initialItems;
      this.index = RandomizedQueue.this.size;
    }

    public Item next() {
      if (this.hasNext()) {
        int derefIndex = RANDOM.nextInt(this.index);

        Item result = this.items[derefIndex];
        this.items[derefIndex] = this.items[this.index - 1];

        //
        // prevent loitering
        //
        this.items[this.index - 1] = null;
        this.index -= 1;

        if (this.index == 0) {
          //
          // when empty remove
          //
          this.items = null;
        }

        return result;
      }
      else {
        throw new NoSuchElementException();
      }
    }

    public boolean hasNext() {
      return this.index > 0;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  public static void main(String args[]) {}

}

