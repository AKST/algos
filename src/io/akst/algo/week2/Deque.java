package io.akst.algo.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node head;
  private Node last;
  private int size = 0;

  public Iterator<Item> iterator() {
    return new DequeIterator(this.head);
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return this.size;
  }

  public void addFirst(Item item) {
    Node newNode = new Node(item);
    this.size += 1;
    if (this.head == null) {
      this.head = newNode;
      this.last = newNode;
    }
    else {
      Node oldNode = this.head;
      newNode.setNext(oldNode);
      oldNode.setPrev(newNode);
      this.head = newNode;
    }
  }

  public void addLast(Item item) {
    Node newNode = new Node(item);
    this.size += 1;
    if (this.last == null) {
      this.head = newNode;
      this.last = newNode;
    }
    else {
      Node oldNode = this.last;
      newNode.setPrev(oldNode);
      oldNode.setNext(newNode);
      this.last = newNode;
    }
  }

  public Item removeFirst() {
    if (this.head == null) {
      throw new NoSuchElementException();
    }

    this.size -= 1;

    if (this.head == this.last) {
      Item result = this.head.getItem();
      this.head.nullify();
      this.head = null;
      this.last = null;
      return result;
    }
    else {
      Node oldHead = this.head;
      Item result = this.head.getItem();
      this.head = oldHead.getNext();
      oldHead.nullify();
      return result;
    }
  }

  public Item removeLast() {
    if (this.last == null) {
      throw new NoSuchElementException();
    }

    this.size -= 1;

    if (this.head == this.last) {
      Item result = this.last.getItem();
      this.last.nullify();
      this.last = null;
      this.head = null;
      return result;
    }
    else {
      Node oldLast = this.last;
      Item result = this.last.getItem();
      this.last = oldLast.getPrev();
      oldLast.nullify();
      return result;
    }
  }

  private class Node {
    private Item item;
    private Node next = null;
    private Node prev = null;

    public Node(Item wrappedItem) {
      this.item = wrappedItem;
    }

    public Item getItem() {
      return item;
    }

    public Node getNext() {
      return next;
    }

    public Node getPrev() {
      return prev;
    }

    public void nullify() {
      this.item = null;
      this.next = null;
      this.prev = null;
    }

    public void setNext(Node newNext) {
      this.next = newNext;
    }

    public void setPrev(Node newPrev) {
      this.prev = newPrev;
    }
  }

  private class DequeIterator implements Iterator<Item> {
    private Node current;

    public DequeIterator(Node initialItem) {
      this.current = initialItem;
    }

    public boolean hasNext() {
      return current != null;
    }

    public Item next() {
      Item item    = this.current.getItem();
      this.current = this.current.getNext();
      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

}
