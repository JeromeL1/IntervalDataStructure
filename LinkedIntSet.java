package intset;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * maintains a set integers using a linked sequence of Interval.
 * </p>
 * 
 * @author jeromeli
 * 
 */
public class LinkedIntSet implements IntSet {

  private Link<Interval> tail;
  private Link<Interval> head;
  private int intervalCount;
  private int size;
  private boolean ifAdded;


  /**
   * <p>
   * constructor.
   * </p>
   */
  LinkedIntSet() {
    clear();
  }

  /**
   * <p>
   * Remove all elements.
   * </p>
   */
  public void clear() {
    tail = new Link<Interval>(null, null); // Create trailer
    head = new Link<Interval>(null, tail); // Create header
    tail.setPrev(head);
    intervalCount = 0;
    size = 0;
    ifAdded = false;
  }

  @Override
  public boolean isEmpty() {

    return intervalCount == 0;
  }

  @Override
  public int size() {

    return size;
  }

  @Override
  public int getIntervalCount() {

    return intervalCount;
  }

  @Override
  public Interval getInterval(int i) throws IndexOutOfBoundsException {

    Link<Interval> temp = head;

    if (intervalCount == 0) {
      throw new IndexOutOfBoundsException();
    }

    if (i < 0 || i > intervalCount) {

      throw new IndexOutOfBoundsException();

    }

    for (int j = 0; j <= i; j++) {

      temp = temp.next();

    }

    return temp.element();
  }

  @Override
  public boolean contains(int i) {

    boolean result = false;
    Link<Interval> curr = head;

    for (int j = 0; j < intervalCount; j++) {

      curr = curr.next();

      if (curr != tail) {
        if (curr.element().getLowerBound() <= i && i <= curr.element().getUpperBound()) {
          result = true;
          break;
        }
      }
    }
    return result;
  }

  @Override
  public boolean add(int num) {

    Link<Interval> curr;
    Link<Interval> right;
    Link<Interval> left;

    ifAdded = false;
    curr = head;

    // no intervals.
    if (intervalCount == 0) {

      insertNewLink(num, num, head, tail, false);

    } else {

      boolean inSet = false;
      // checks if an interval contains the num.
      for (int i = 0; i < intervalCount; i++) {

        curr = curr.next();

        if (curr.element().getLowerBound() > num) {
          break;
        } else if (curr.element().contains(num)) {
          inSet = true;
          break;
        }

      }

      // if num is not in bound, then it has to be in between the intervals.
      if (!inSet) {

        right = getClosestLink(num, "right");
        left = getClosestLink(num, "left");

        if (right == tail) {

          int leftUpper = left.element().getUpperBound();
          if (num == leftUpper + 1) {

            encloseNum(curr, num);

          } else {

            insertNewLink(num, num, left, tail, false);

          }

        } else if (left == head) {

          int rightLower = right.element().getLowerBound();
          if (num == rightLower - 1) {

            encloseNum(curr, num);

          } else {

            insertNewLink(num, num, head, right, false);

          }

        } else {

          int rightLower = right.element().getLowerBound();
          int leftUpper = left.element().getUpperBound();
          int leftLower = left.element().getLowerBound();
          int rightUpper = right.element.getUpperBound();

          if (num == leftUpper + 1 && num == rightLower - 1) {

            insertNewLink(leftLower, rightUpper, left, right, true);

          } else if (num == leftUpper + 1) {

            encloseNum(left, num);

          } else if (num == rightLower - 1) {

            encloseNum(right, num);

          } else if (num > leftUpper + 1 && num < rightLower + 1) {

            insertNewLink(num, num, left, right, false);

          }
        }
      }
    }
    return ifAdded;
  }

  /**
   * <p>
   * private helper method that inserts a new link of interval or merges two intervals and creates a
   * new link that replaces the left and right links.
   * </p>
   * 
   * @param lower lower number.
   * @param upper upper number.
   * @param leftLink left link.
   * @param rightLink right link.
   * @param merge if right and left link should be merged.
   */
  private void insertNewLink(int lower, int upper, Link<Interval> leftLink,
      Link<Interval> rightLink, boolean merge) {

    Interval interval = null;
    Link<Interval> link = null;

    if (merge) {

      interval = new Interval(lower, upper);
      link = new Link<Interval>(interval, leftLink.prev(), rightLink.next());

      leftLink.prev().setNext(link);
      rightLink.next().setPrev(link);

      intervalCount--;
      size++;
      ifAdded = true;

    } else {

      interval = new Interval(lower, upper);
      link = new Link<Interval>(interval, leftLink, rightLink);

      leftLink.setNext(link);
      rightLink.setPrev(link);

      intervalCount++;
      size++;
      ifAdded = true;
    }

  }

  /**
   * encloses the number.
   * 
   * @param curr current node.
   * @param num number to enclose.
   */
  private void encloseNum(Link<Interval> curr, int num) {

    curr.element().enclose(num);
    size++;
    ifAdded = true;

  }

  /**
   * 
   * <p>
   * for a list that has at least one interval link. It gets the 2 closest intervals links from the
   * number, one of which could be head link or tail link.
   * </p>
   * 
   * @param num num to be added.
   * @param key right side right or left side link.
   * @return returns the closest node to num, could be head or tail.
   */
  private Link<Interval> getClosestLink(int num, String key) {

    Link<Interval> result;
    result = head;


    for (int i = 0; i < intervalCount; i++) {

      result = result.next();

      // gets the link whose lower bound is the closest to the number to be added.
      if (num < result.element().getLowerBound()) {

        if (key.equals("right")) {
          break;
        } else if (key.equals("left")) {
          result = result.prev();
        }
        // checks if number is between the last interval and tail.
      } else if (result.next() == tail && num > result.element().getUpperBound()) {

        if (key.equals("right")) {
          result = result.next();
          break;
        } else if (key.equals("left")) {
          break;
        }

      }

    }

    return result;
  }



  @Override
  public void remove(int num) {

    Link<Interval> curr;
    Link<Interval> firstHalf;
    Link<Interval> secondHalf;
    Interval first;
    Interval second;
    boolean contains;
    Interval currInterval;


    contains = false;
    curr = head;
    currInterval = null;

    for (int i = 0; i < intervalCount; i++) {

      curr = curr.next();

      if (curr.element().contains(num)) {
        contains = true;
        break;
      }
    }

    if (contains) {

      currInterval = curr.element();

      if (currInterval.size() == 1) {

        curr.prev().setNext(curr.next());
        curr.next().setPrev(curr.prev());
        intervalCount--;
        size--;

      } else if (currInterval.getLowerBound() == num) {

        currInterval.setLowerBound(num + 1);
        size--;

      } else if (currInterval.getUpperBound() == num) {

        currInterval.setUpperBound(num - 1);
        size--;

      } else {

        first = new Interval(curr.element().getLowerBound(), num - 1);
        second = new Interval(num + 1, curr.element().getUpperBound());

        firstHalf = new Link<Interval>(first, null, null);
        secondHalf = new Link<Interval>(second, firstHalf, null);

        firstHalf.setNext(secondHalf);
        firstHalf.setPrev(curr.prev());
        secondHalf.setNext(curr.next());
        curr.prev().setNext(firstHalf);
        curr.next().setPrev(secondHalf);


        size--;
        intervalCount++;

      }

    } else {
      throw new NoSuchElementException();
    }

  }


  @Override
  public String toString() {

    String result = "{";
    Link<Interval> curr = head;

    for (int i = 0; i < intervalCount; i++) {
      curr = curr.next();
      result += curr.element().toString();
      if (i != intervalCount - 1) {
        result += ",";
      }
    }

    return result += "}";

  }

  @Override
  public Iterator<Integer> iterator() {

    return new Iterator<Integer>() {

      int currNum = 0;
      int currNumIndexInSet = 0;
      int intervalIndex = 0;
      Link<Interval> curr = head;

      @Override
      public boolean hasNext() {

        return currNum < size && intervalIndex < intervalCount;
      }

      @Override
      public Integer next() {
        int result = 0;
        boolean valid = false;

        if (intervalIndex < intervalCount) {

          if (curr == head) {
            curr = curr.next();
          } else if (currNumIndexInSet >= curr.element().size()) {
            curr = curr.next();
            currNumIndexInSet = 0;
            intervalIndex++;
          }

        }

        if (hasNext()) {
          result = curr.element().getLowerBound() + currNumIndexInSet;
          currNum++;
          currNumIndexInSet++;
          valid = true;
        }

        if (!valid) {
          throw new NoSuchElementException();
        }

        return result;
      }

    };
  }

  /*
   * OpenDSA Project Distributed under the MIT License
   * 
   * Copyright (c) 2011-2016 - Ville Karavirta and Cliff Shaffer
   */

  class Link<E> { // Doubly linked list node
    private E element;
    private Link<E> next;
    private Link<E> prev;

    // Constructors
    Link(E it, Link<E> inp, Link<E> inn) {
      element = it;
      prev = inp;
      next = inn;
    }

    Link(Link<E> inp, Link<E> inn) {
      prev = inp;
      next = inn;
    }

    // Get and set methods for the data members
    public E element() {
      return element;
    } // Return the value

    public E setElement(E it) {
      return element = it;
    } // Set element value

    public Link<E> next() {
      return next;
    } // Return next link

    public Link<E> setNext(Link<E> nextval) {
      return next = nextval;
    } // Set next link

    public Link<E> prev() {
      return prev;
    } // Return prev link

    public Link<E> setPrev(Link<E> prevval) {
      return prev = prevval;
    } // Set prev link
  }

}
