package io.akst.algo.week1;


import java.util.Arrays;


public class IndexUF {
  private int count;
  private final int[] parent;
  private final int[] size;
  private final boolean[] status;


  /**
   * Initializes an empty union-find data structure with N isolated components 0 through N-1.
   * @throws java.lang.IllegalArgumentException if N < 0
   * @param N the number of objects
   */
  public IndexUF(int N, boolean initial) {
    size   = new int[N];
    parent = new int[N];
    status = new boolean[N];

    count = N;
    for (int i = 0; i < N; i++) {
      parent[i] = i;
      status[i] = false;
      size[i] = 1;
    }
  }

  /**
   * Returns the number of components.
   * @return the number of components (between 1 and N)
   */
  public int count() {
    return count;
  }

  /**
   * Returns the component identifier for the component containing site <tt>p</tt>.
   * @param p the integer representing one site
   * @return the component identifier for the component containing site <tt>p</tt>
   * @throws java.lang.IndexOutOfBoundsException unless 0 <= p < N
   */
  public int find(int pRaw) {
		// styling software won't let me get away with this
		int p = pRaw;
    //validate(p);
    while (p != parent[p]) {
      p = parent[p];
		}
    return p;
  }

  // validate that p is a valid index
  private void validate(int p) {
    int N = parent.length;
    if (p < 0 || p >= N) {
			String message = "index " + p + " is not between 0 and " + N;
      throw new IndexOutOfBoundsException(message);
    }
  }

  /**
   * Are the two sites <tt>p</tt> and <tt>q</tt> in the same component?
   * @param p the integer representing one site
   * @param q the integer representing the other site
   * @return <tt>true</tt> if the sites <tt>p</tt> and <tt>q</tt> are in the same
   *    component, and <tt>false</tt> otherwise
   * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
   */
  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  public boolean getIndex(int i) {
    return this.status[i];
  }

  public void setIndex(int i, boolean value) {
    this.status[i] = value;
  }


  /**
   * Merges the component containing site<tt>p</tt> with the component
   * containing site <tt>q</tt>.
   * @param p the integer representing one site
   * @param q the integer representing the other site
   * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
   */
  public void union(int p, int q) {
    int rootP = find(p);
    int rootQ = find(q);

    if (rootP == rootQ) return;

    if (size[rootP] < size[rootQ]) {
      parent[rootP] = rootQ;
      size[rootQ]  += size[rootP];
    }
    else {
      parent[rootQ] = rootP;
      size[rootP]  += size[rootQ];
    }
    count--;
  }

}

