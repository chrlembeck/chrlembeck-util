package de.chrlembeck.util.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests für den ToStringWrapper
 *
 * @author Christoph Lembeck
 */
public class ToStringWrapperTest {

    /**
     * Testet die toString-Methode.
     */
    @Test
    public void testToString() {
        final ToStringWrapper<String> wrapper = new ToStringWrapper<String>("abcde",
                str -> new StringBuilder(str).reverse().toString());
        Assertions.assertEquals("edcba", wrapper.toString());
    }

    /**
     * Testet die getObject-Methode.
     */
    @Test
    public void testGetObject() {
        final String object = "abcde";
        final ToStringWrapper<String> wrapper = new ToStringWrapper<String>(object,
                str -> new StringBuilder(str).reverse().toString());
        Assertions.assertSame(object, wrapper.getObject());
    }
}