package de.chrlembeck.util.collections;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * Tests für die ArrayEnumeration
 *
 * @author Christoph Lembeck
 */
@RunWith(JUnitPlatform.class)
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
        Assert.assertTrue(aEnum.hasMoreElements());
        final String second = aEnum.nextElement();
        Assertions.assertSame(strings[1], second);
        Assertions.assertFalse(aEnum.hasMoreElements());
        Assertions.assertThrows(NoSuchElementException.class, aEnum::nextElement);
    }
}
