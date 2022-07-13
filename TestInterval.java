package intset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntervalTest {

  Interval good;

  @BeforeEach
  void setUp() {

    good = new Interval(0, 10);

  }

  @Test
  void instantiateArgumentExceptionTest() {

    assertThrows(IllegalArgumentException.class, () -> new Interval(4, 3));


  }
  
  @Test
  void setBoundArgumentExceptionTest() {

    assertThrows(IllegalArgumentException.class, () -> good.setLowerBound(11));
    assertThrows(IllegalArgumentException.class, () -> good.setUpperBound(-1));

  }
  
  @Test
  void settersGettersTest() {

    good.setLowerBound(10);

    assertEquals(10, good.getLowerBound());
    
    assertTrue("10".equals(good.toString()));

    good.setUpperBound(20);

    assertEquals(20, good.getUpperBound());

    
  }
  
  @Test
  void encloseTest() {

    good.setLowerBound(10);

    good.enclose(0);

    assertEquals(0, good.getLowerBound());

    good.enclose(30);
    
    assertEquals(30, good.getUpperBound());
    
    assertTrue("0-30".equals(good.toString()));
    
    good.enclose(29);
    
    assertTrue("0-30".equals(good.toString()));
    
  }
  
  @Test
  void containsTest() {
    
    assertTrue(good.contains(5));
    assertTrue(good.contains(0));
    assertTrue(good.contains(10));
    assertFalse(good.contains(-1));
    assertFalse(good.contains(11));
    
  }
  
  @Test
  void sizeTest() {
    
    assertEquals(11, good.size());
    good.setLowerBound(10);
    assertEquals(1, good.size());
    
  }
  


}
