package intset;
/**
 * <p>
 * Interval to encapsulate a bounded, inclusive, number range.
 * </p>
 * 
 * @author jeromeli
 * @version mar 8.
 */
public class Interval {

  private int lowerBound;
  private int upperBound;

  /**
   * 
   * <p>
   * A constructor that accepts two int parameters for the lower and upper bound of the interval.
   * The bounds are inclusive. If the lower bound exceeds the upper bound, an
   * IllegalArgumentException is thrown.
   * </p>
   * 
   * @param lower lower bound.
   * @param upper upper bound.
   */
  public Interval(int lower, int upper) {

    if (lower > upper) {

      throw new IllegalArgumentException("lower bound has to be lower or equal to upper bound.");

    }

    lowerBound = lower;
    upperBound = upper;

  }

  /**
   * 
   * <p>
   * accepts an int parameter. The interval’s bounds are adjusted, if necessary, to include the
   * parameter in the interval.
   * </p>
   * 
   * @param newNum the num to be enclosed.
   */
  public void enclose(int newNum) {

    if (newNum < lowerBound) {
      setLowerBound(newNum);
    } else if (newNum > upperBound) {
      setUpperBound(newNum);
    }

  }


  /**
   * <p>
   * getter.
   * </p>
   * 
   * @return lower bound.
   */
  public int getLowerBound() {
    return lowerBound;
  }

  /**
   * <p>
   * setter.
   * </p>
   * 
   * @param lowerBound sets lower bound.
   */
  public void setLowerBound(int lowerBound) {

    if (lowerBound > upperBound) {
      throw new IllegalArgumentException();
    }

    this.lowerBound = lowerBound;
  }

  /**
   * <p>
   * getter.
   * </p>
   * 
   * @return upper bound.
   */
  public int getUpperBound() {

    return upperBound;
  }

  /**
   * 
   * <p>
   * setter.
   * </p>
   * 
   * @param upperBound sets upperbound.
   */
  public void setUpperBound(int upperBound) {

    if (upperBound < lowerBound) {
      throw new IllegalArgumentException();
    }

    this.upperBound = upperBound;
  }

  /**
   * <p>
   * check if a number is in bound.
   * </p>
   * 
   * @param num number.
   * @return true if and only if the parameter falls within the interval’s bounds.
   */
  public boolean contains(int num) {

    return lowerBound <= num && upperBound >= num;

  }

  /**
   * <p>
   * A toString method that returns a textual representation of this interva.
   * </p>
   * 
   * @return text representation.
   */
  public String toString() {

    String result = null;

    if (lowerBound == upperBound) {
      result = String.format("%d", lowerBound);
    } else {
      result = String.format("%d-%d", lowerBound, upperBound);
    }

    return result;

  }

  /**
   * 
   * <p>
   * size of interval.
   * </p>
   * 
   * @return size of interval.
   */
  public int size() {

    return upperBound - lowerBound + 1;
    
  }

}
