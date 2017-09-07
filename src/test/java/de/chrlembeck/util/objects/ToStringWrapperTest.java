package de.chrlembeck.util.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToStringWrapperTest {

    @Test
    public void testToString() {
        final ToStringWrapper<String> wrapper = new ToStringWrapper<String>("abcde",
                s -> new StringBuilder(s).reverse().toString());
        Assertions.assertEquals("edcba", wrapper.toString());
    }

    @Test
    public void testGetObject() {
        final String st = "abcde";
        final ToStringWrapper<String> wrapper = new ToStringWrapper<String>(st,
                s -> new StringBuilder(s).reverse().toString());
        Assertions.assertSame(st, wrapper.getObject());
    }
}