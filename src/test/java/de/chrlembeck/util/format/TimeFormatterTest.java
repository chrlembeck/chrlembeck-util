package de.chrlembeck.util.format;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests für den TimeFormatter
 * 
 * @author Christoph Lembeck
 */
public class TimeFormatterTest {

    /**
     * Anzahl der Millisekunden für einen Tag.
     */
    private static final long DAY = 1000L * 1000 * 1000 * 60 * 60 * 24;

    /**
     * Anzahl der Millisekunden für eine Stunde.
     */
    private static final long HOUR = 1000L * 1000 * 1000 * 60 * 60;

    /**
     * Anzahl der Millisekunden für eine Minute.
     */
    private static final long MINUTE = 1000L * 1000 * 1000 * 60;

    /**
     * Anzahl der Millisekunden für eine Sekunde.
     */
    private static final long SECOND = 1000L * 1000 * 1000;

    /**
     * Tested die Klasse TimeFormatter.
     */
    @Test
    public void testNanosToText() {
        Assertions.assertEquals("0 Nanosekunden", TimeFormatter.nanosToText(0));
        Assertions.assertEquals("1 Nanosekunde", TimeFormatter.nanosToText(1));
        Assertions.assertEquals("2 Nanosekunden", TimeFormatter.nanosToText(2));
        Assertions.assertEquals("999 Nanosekunden", TimeFormatter.nanosToText(999));
        Assertions.assertEquals("123 Nanosekunden", TimeFormatter.nanosToText(123));
        Assertions.assertEquals("1 Mikrosekunde, 234 Nanosekunden", TimeFormatter.nanosToText(1234));
        Assertions.assertEquals("1 Mikrosekunde", TimeFormatter.nanosToText(1000));
        Assertions.assertEquals("2 Mikrosekunden", TimeFormatter.nanosToText(2000));
        Assertions.assertEquals("1 Millisekunde", TimeFormatter.nanosToText(1000 * 1000));
        Assertions.assertEquals("2 Millisekunden", TimeFormatter.nanosToText(2000 * 1000));
        Assertions.assertEquals("1 Sekunde", TimeFormatter.nanosToText(SECOND));
        Assertions.assertEquals("2 Sekunden", TimeFormatter.nanosToText(2 * SECOND));
        Assertions.assertEquals("1 Minute", TimeFormatter.nanosToText(MINUTE));
        Assertions.assertEquals("2 Minuten", TimeFormatter.nanosToText(2 * MINUTE));
        Assertions.assertEquals("1 Stunde", TimeFormatter.nanosToText(HOUR));
        Assertions.assertEquals("2 Stunden", TimeFormatter.nanosToText(2 * HOUR));
        Assertions.assertEquals("1 Stunde", TimeFormatter.nanosToText(HOUR));
        Assertions.assertEquals("1 Tag, 2 Stunden, 3 Minuten, 4 Sekunden",
                TimeFormatter.nanosToText(1 * DAY + 2 * HOUR + 3 * MINUTE + 4 * SECOND));
        Assertions.assertEquals("1 Tag, 2 Stunden, 3 Minuten, 4 Sekunden, 0 Millisekunden, 0 Mikrosekunden, 1 Nanosekunde",
                TimeFormatter.nanosToText(1 * DAY + 2 * HOUR + 3 * MINUTE + 4 * SECOND + 1));
        Assertions.assertEquals(
                "2 Tage, 23 Stunden, 59 Minuten, 59 Sekunden, 999 Millisekunden, 999 Mikrosekunden, 999 Nanosekunden",
                TimeFormatter.nanosToText(3 * DAY - 1));
        Assertions.assertEquals(
                "106751 Tage, 23 Stunden, 47 Minuten, 16 Sekunden, 854 Millisekunden, 775 Mikrosekunden, 807 Nanosekunden",
                TimeFormatter.nanosToText(Long.MAX_VALUE));

        try {
            TimeFormatter.nanosToText(-10000);
            Assertions.fail("IllegalArgumentException expected");
        } catch (final IllegalArgumentException iae) {
            // expected exception
        }
    }
}
