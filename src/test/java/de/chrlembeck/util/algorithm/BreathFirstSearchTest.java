package de.chrlembeck.util.algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import de.chrlembeck.util.algorithm.AlgorithmUtils.StateWrapper;

/**
 * Tests für die Breitensuche.
 * 
 * @author Christoph Lembeck
 */
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
        final List<String> result = AlgorithmUtils.breadthFirstSearch("", stateProducer,
                wrapper -> System.out.println(wrapper.getState()), wrapper -> wrapper.getState().equals("abc"));
        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals("", result.get(0));
        Assertions.assertEquals("a", result.get(1));
        Assertions.assertEquals("ab", result.get(2));
        Assertions.assertEquals("abc", result.get(3));
    }
}