package intset;
/**
 * <p>
 * defines what is a set of Integer objects.
 * </p>
 * 
 * @author jeromeli
 * @version Mar 2.
 */
public interface IntSet extends Iterable<Integer> {

  /**
   * <p>
   * A method isEmpty.
   * </p>
   * 
   * @return boolean indicating the emptiness of the set.
   */
  public boolean isEmpty();

  /**
   * <p>
   * A method size.
   * </p>
   * 
   * @return as an int the number of integers in the set.
   */
  public int size();

  /**
   * <p>
   * the number of intervals used to represent the set. This method really should not be in the
   * interface of a set. It is included only to make your set easier to test.
   * </p>
   * 
   * @return the number of intervals used to represent the set.
   */
  public int getIntervalCount();

  /**
   * <p>
   * accepts an int parameter i and returns the ith interval in the set (with i starting at 0). If
   * no such interval exists, an IndexOutOfBoundsException is thrown. This method really should not
   * be in the interface of a set. It is included only to make your set easier to test.
   * </p>
   * 
   * @param i index of interval.
   * @return ith interval in the set.
   * @throws IndexOutOfBoundsException throws if there is no such interval.
   */
  public Interval getInterval(int i) throws IndexOutOfBoundsException;


  /**
   * A method contains that accepts an int parameter.
   * 
   * @param i index of interval.
   * @return true if and only if the parameter is in the set.
   */
  public boolean contains(int i);

  /**
   * <p>
   * If some interval already contains the int, nothing is done. For example, adding 3 to set 2-3,6
   * yields 2-3,6.
   * </p>
   * <p>
   * If the int is adjacent to just one existing interval, then the interval is adjusted to include
   * the new number. For example, adding 8 to 9-20,25-29 yields 8-20,25-29.
   * </p>
   * <p>
   * If the int falls between two intervals, the two intervals are coalesced into one. For example,
   * adding 5 to 1-4,6-7,99 yields 1-7,99.
   * </p>
   * <p>
   * Otherwise, a new interval is formed and inserted between any existing intervals so that
   * intervals are in sorted order. For example, adding 6 to 1,9-15 yields 1,6,9-15.
   * </p>
   * 
   * @param num number to be added in the interval.
   * @return true if it is added.
   */
  public boolean add(int num);


  /**
   * <p>
   * If no interval contains the int, a NoSuchElementException is thrown.
   * </p>
   * <p>
   * If the int is the sole member of its interval, the interval is removed. For example, removing 5
   * from 1,5,7-9 yields 1,7-9.
   * </p>
   * <p>
   * If the int is the bounds of its interval, the interval is adjusted to not include the number.
   * For example, removing 3 from 1-3 yields 1-2.
   * </p>
   * <p>
   * Otherwise, the number lies within an interval, and the interval must be broken into. For
   * example, remove 7 from 2,5-10 yields 2,5-6,8-10.
   * </p>
   * 
   * @param num to be removed.
   */
  public void remove(int num);


  /**
   * <p>
   * If the set is empty, "{}" is returned. Otherwise, a brace-enclosed and comma-separated sequence
   * of the intervals’ textual representations is returned. For example, if you add numbers 1, 5, 8,
   * 6, 7 to a set, its String representation is "{1,5-8}".
   * </p>
   * 
   * @return a textual representation of the set.
   */
  public String toString();


  /**
   * An equals method that accepts an Object as a parameter.
   * 
   * @param set object set.
   * @return true if and only if the parameter is an IntSet containing the same integers.
   */
  public boolean equals(Object set);


}
