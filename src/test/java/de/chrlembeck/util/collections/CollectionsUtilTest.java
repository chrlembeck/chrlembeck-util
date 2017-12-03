package de.chrlembeck.util.collections;

import java.util.Arrays;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * Tests für die CollectionsUtil-Klasse.
 *
 * @author Christoph Lembeck
 */
@RunWith(JUnitPlatform.class)
public class CollectionsUtilTest {

    /**
     * Testet die isNullOrEmpty-Methode.
     */
    @Test
    public void testNullOrEmpty() {
        Assert.assertTrue(CollectionsUtil.isNullOrEmpty(null));
        Assert.assertTrue(CollectionsUtil.isNullOrEmpty(new TreeSet<Float>()));
        Assert.assertFalse(CollectionsUtil.isNullOrEmpty(Arrays.asList(1, 2, 3)));
    }
}