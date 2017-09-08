package de.chrlembeck.util.collections;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayEnumerationTest {

    @Test
    public void testArrayEnumeration() {
        final String[] strings = new String[] { "A", "B" };
        final ArrayEnumeration<String> aEnum = new ArrayEnumeration<>(strings);
        Assertions.assertTrue(aEnum.hasMoreElements());
        final String first = aEnum.nextElement();
        Assertions.assertSame(strings[0], first);
        Assertions.assertTrue(aEnum.hasMoreElements());
        final String second = aEnum.nextElement();
        Assertions.assertSame(strings[1], second);
        Assertions.assertFalse(aEnum.hasMoreElements());
        Assertions.assertThrows(NoSuchElementException.class, () -> aEnum.nextElement());
    }
}
