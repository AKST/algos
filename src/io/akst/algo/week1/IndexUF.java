package io.akst.algo.week1;

import java.lang.reflect.Array;


public class IndexUF<T extends Object> {
  private Indexed<T>[] parent;  // parent[i] = parent of i
  private int count;     // number of components

  /**
   * Initializes an empty union-find data structure with N isolated components 0 through N-1.
   * @throws java.lang.IllegalArgumentException if N < 0
   * @param N the number of objects
   */
  @SuppressWarnings("unchecked")
  public IndexUF(int N, T initial) {
    Indexed<T> sample = new Indexed<T>(0, initial);
    parent = ((Indexed<T>[]) Array.newInstance(sample.getClass(), N));

    //parent = (Indexed<T>[]) new Object[N];
    count = N;
    for (int i = 0; i < N; i++) {
      parent[i] = new Indexed<T>(i, initial);
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
  public Indexed<T> find(int pRaw) {
		// styling software won't let me get away with this
		int p = pRaw;
    validate(p);
    while (p != parent[p].index) {
      p = parent[p].index;
		}
    return parent[p];
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

  public T getIndex(int i) {
    return this.parent[i].getValue();
  }

  public void setIndex(int i, T value) {
    this.parent[i].setValue(value);
  }


  /**
   * Merges the component containing site<tt>p</tt> with the component
   * containing site <tt>q</tt>.
   * @param p the integer representing one site
   * @param q the integer representing the other site
   * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
   */
  public void union(int p, int q) {
    Indexed<T> rootP = find(p);
    Indexed<T> rootQ = find(q);
    if (rootP == rootQ) return;
    parent[rootP.index] = rootQ;
    count--;
  }


  private static class Indexed<T> {
    private int index;
    private T value;

    public Indexed(int i, T value) {
      this.index = i;
      this.value = value;
    }

		public int getIndex() {
			return this.index;
		}

		public T getValue() {
			return this.value;
		}

		public void setValue(T newValue) {
			this.value = newValue;
		}
  }
}

