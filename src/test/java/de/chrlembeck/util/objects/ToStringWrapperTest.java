package de.chrlembeck.util.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToStringWrapperTest {

    @Test
    public void testToString() {
        final ToStringWrapper<String> wrapper = new ToStringWrapper<String>("abcde",
                str -> new StringBuilder(str).reverse().toString());
        Assertions.assertEquals("edcba", wrapper.toString());
    }

    @Test
    public void testGetObject() {
        final String object = "abcde";
        final ToStringWrapper<String> wrapper = new ToStringWrapper<String>(object,
                str -> new StringBuilder(str).reverse().toString());
        Assertions.assertSame(object, wrapper.getObject());
    }
}