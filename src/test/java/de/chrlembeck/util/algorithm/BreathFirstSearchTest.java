package de.chrlembeck.util.algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import de.chrlembeck.util.algorithm.Algorithms.StateWrapper;

/**
 * Tests für die Breitensuche.
 * 
 * @author Christoph Lembeck
 */
@RunWith(JUnitPlatform.class)
public class BreathFirstSearchTest {

    /**
     * Test der Breitensuche mit einem sehr einfachen Algorithmus.
     */
    @Test
    public void simpleTest() {
        final Function<StateWrapper<String>, Iterable<String>> stateProducer = (stateWrapper) -> {
            final String old = stateWrapper.getState();
            return Arrays.asList(old + "a", old + "b", old + "c");
        };
        final List<String> result = Algorithms.breadthFirstSearch("", stateProducer,
                wrapper -> System.out.println(wrapper.getState()), wrapper -> wrapper.getState().equals("abc"));
        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals("", result.get(0));
        Assertions.assertEquals("a", result.get(1));
        Assertions.assertEquals("ab", result.get(2));
        Assertions.assertEquals("abc", result.get(3));
    }
}