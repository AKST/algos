package io.akst.algo.week2;


import edu.princeton.cs.introcs.StdIn;


public class Subset {

  public static void main(String[] args) {
    int readMax = Integer.parseInt(args[0]);
    RandomizedQueue<String> queue = new RandomizedQueue<>();

    for (int i = 0; i < readMax; i++) {
      String input = StdIn.readString();
      queue.enqueue(input);
    }

    for (String word : queue) {
      System.out.println(word);
    }
  }
}

