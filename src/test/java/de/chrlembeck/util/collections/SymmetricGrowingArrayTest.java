package de.chrlembeck.util.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class SymmetricGrowingArrayTest {

    @Test
    public void testEmpty() {
        SymmetricGrowingArray<Integer> array = new SymmetricGrowingArray<>(Integer[]::new);
        for (int i = -10; i <=10; i++) {
            Assertions.assertEquals(null, array.get(i));
        }
    }

    @Test
    public void testSize() {
        SymmetricGrowingArray<Integer> array = new SymmetricGrowingArray<>(Integer[]::new);
        Assertions.assertArrayEquals(new int[] {0,0}, array.getSize());
        array.put(-1, Integer.valueOf(42));
        Assertions.assertArrayEquals(new int[] {-1,0}, array.getSize());
        array.put(1, Integer.valueOf(42));
        Assertions.assertArrayEquals(new int[] {-1,1}, array.getSize());
        array.put(-2, Integer.valueOf(42));
        Assertions.assertArrayEquals(new int[] {-2,1}, array.getSize());
        array.put(2, Integer.valueOf(42));
        Assertions.assertArrayEquals(new int[] {-2,2}, array.getSize());
    }

    @Test
    public void testGet() {
        SymmetricGrowingArray<Integer> array = new SymmetricGrowingArray<>(Integer[]::new);
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
}