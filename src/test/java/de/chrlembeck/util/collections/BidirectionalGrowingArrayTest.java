package de.chrlembeck.util.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;

/**
 * Tests für die Klasse SymmetricGrowingArray.
 * 
 * @author Christoph Lembeck
 */
//@RunWith(JUnitPlatform.class)
public class BidirectionalGrowingArrayTest {

    /**
     * Tests rund um das leere Array.
     */
    @Test
    public void testEmpty() {
        final BidirectionalGrowingArray<Integer> array = new BidirectionalGrowingArray<>(Integer[]::new);
        for (int i = -10; i <= 10; i++) {
            Assertions.assertEquals(null, array.get(i));
        }
    }

    /**
     * Testet die getRange-Methode.
     */
    @Test
    public void testRange() {
        final BidirectionalGrowingArray<Integer> array = new BidirectionalGrowingArray<>(Integer[]::new);
        try {
            Assertions.assertArrayEquals(new int[] { 0, 0 }, array.getRange());
            Assertions.fail("ArrayIndexOutOfBoundsException expected.");
        } catch (final ArrayIndexOutOfBoundsException e) {
            // expected
        }
        array.put(-1, Integer.valueOf(42));
        Assertions.assertArrayEquals(new int[] { -1, -1 }, array.getRange());
        array.put(1, Integer.valueOf(42));
        Assertions.assertArrayEquals(new int[] { -1, 1 }, array.getRange());
        array.put(-2, Integer.valueOf(42));
        Assertions.assertArrayEquals(new int[] { -2, 1 }, array.getRange());
        array.put(2, Integer.valueOf(42));
        Assertions.assertArrayEquals(new int[] { -2, 2 }, array.getRange());
    }

    /**
     * Testet die put und get-Methoden.
     */
    @Test
    public void testGet() {
        final BidirectionalGrowingArray<Integer> array = new BidirectionalGrowingArray<>(Integer[]::new);
        array.put(5, Integer.valueOf(42));
        Assertions.assertEquals(Integer.valueOf(42), array.get(5));
        array.put(-7, Integer.valueOf(17));
        Assertions.assertEquals(Integer.valueOf(17), array.get(-7));
        array.put(8, Integer.valueOf(13));
        Assertions.assertEquals(Integer.valueOf(13), array.get(8));
        array.put(-3, Integer.valueOf(36));
        Assertions.assertEquals(Integer.valueOf(36), array.get(-3));
        array.put(5, Integer.valueOf(16));
        Assertions.assertEquals(Integer.valueOf(16), array.get(5));
    }

    /**
     * Empty arrays shpoud have size zero.
     */
    @Test
    public void testZeroSize() {
        Assertions.assertEquals(0, new BidirectionalGrowingArray<>(Object[]::new).size());
    }
}