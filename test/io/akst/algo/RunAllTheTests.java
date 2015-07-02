package io.akst.algo;

import io.akst.algo.week1.TestPercolation;
import io.akst.algo.week1.TestIndexUF;

import org.junit.runner.RunWith;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  TestPercolation.class,
  TestIndexUF.class
})
public final class RunAllTheTests {}

