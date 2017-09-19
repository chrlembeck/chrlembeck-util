package de.chrlembeck.util.collections;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests für die ArrayEnumeration
 *
 * @author Christoph Lembeck
 */
public class ArrayEnumerationTest {

    /**
     * Testet die Enumeration mit zwei Elementen.
     */
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
