package io.akst.algo.week1;

import org.junit.Test;
import org.junit.Assert;

public class TestIndexUF extends Assert {

	@Test public void initialIndexsNotConnected() {
		IndexUF<Boolean> bools = new IndexUF<>(3, true);
		assertFalse(bools.connected(0, 2));
	}

	@Test public void connectedAfterUnion() {
		IndexUF<Boolean> bools = new IndexUF<>(3, true);
		bools.union(0, 2);
		assertTrue(bools.connected(0, 2));
	}

	@Test public void connectedAfterDeepUnion() {
		IndexUF<Boolean> bools = new IndexUF<>(3, true);
		bools.union(0, 1);
		bools.union(1, 2);
		assertTrue(bools.connected(0, 2));
	}

}
