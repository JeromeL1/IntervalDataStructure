# IntervalDataStructure

creates a set data structure to store the set as a series of intervals. Each sequence of contiguous numbers is represented with just two ints. For example, if you add numbers 10, 8, 3, 7, 2, 9, 3, 2, 10, 11 and 5, the set will store just three intervals: 2-3, 5-5, and 7-11. The intervals themselves are linked together in sequence. This implementation uses little space, provides fast iteration, and supports wide and sparse ranges of numbers. It’s not as fast at lookup and insertion.

## Interval.java

Integer interval as described above.

## Intset.java

interface for interval.java

## LinkedIntSet.java

linked structure of intervals.
