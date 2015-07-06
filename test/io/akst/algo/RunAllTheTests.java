package io.akst.algo;

import io.akst.algo.week1.TestPercolation;
import io.akst.algo.week1.TestIndexUF;
import io.akst.algo.week2.TestDeque;
import io.akst.algo.week2.TestRandomizedQueue;
import io.akst.algo.week3.TestPoint;

import org.junit.runner.RunWith;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  TestPercolation.class,
  TestIndexUF.class,
  TestDeque.class,
  TestRandomizedQueue.class,
  TestPoint.class
})
public final class RunAllTheTests {}

