package de.chrlembeck.util.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * Tests für den ToStringWrapper
 *
 * @author Christoph Lembeck
 */
@RunWith(JUnitPlatform.class)
public class ToStringWrapperTest {

    /**
     * Testet die toString-Methode.
     */
    @Test
    public void testToString() {
        final ToStringWrapper<String> wrapper = new ToStringWrapper<>("abcde",
                str -> new StringBuilder(str).reverse().toString());
        Assertions.assertEquals("edcba", wrapper.toString());
    }

    /**
     * Testet die getObject-Methode.
     */
    @Test
    public void testGetObject() {
        final String object = "abcde";
        final ToStringWrapper<String> wrapper = new ToStringWrapper<>(object,
                str -> new StringBuilder(str).reverse().toString());
        Assertions.assertSame(object, wrapper.getObject());
    }
}