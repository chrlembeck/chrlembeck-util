package de.chrlembeck.util.collections;

import java.util.Arrays;
import java.util.TreeSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CollectionsUtilTest {

    @Test
    public void testNullOrEmpty() {
        Assertions.assertTrue(CollectionsUtil.isNullOrEmpty(null));
        Assertions.assertTrue(CollectionsUtil.isNullOrEmpty(new TreeSet<Float>()));
        Assertions.assertFalse(CollectionsUtil.isNullOrEmpty(Arrays.asList(1, 2, 3)));
    }
}
