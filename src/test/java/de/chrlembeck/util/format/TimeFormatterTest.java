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
        Assertions.assertEquals("0 Nanosekunden", TimeFormatterHelper.nanosToText(0));
        Assertions.assertEquals("1 Nanosekunde", TimeFormatterHelper.nanosToText(1));
        Assertions.assertEquals("2 Nanosekunden", TimeFormatterHelper.nanosToText(2));
        Assertions.assertEquals("999 Nanosekunden", TimeFormatterHelper.nanosToText(999));
        Assertions.assertEquals("123 Nanosekunden", TimeFormatterHelper.nanosToText(123));
        Assertions.assertEquals("1 Mikrosekunde, 234 Nanosekunden", TimeFormatterHelper.nanosToText(1234));
        Assertions.assertEquals("1 Mikrosekunde", TimeFormatterHelper.nanosToText(1000));
        Assertions.assertEquals("2 Mikrosekunden", TimeFormatterHelper.nanosToText(2000));
        Assertions.assertEquals("1 Millisekunde", TimeFormatterHelper.nanosToText(1000 * 1000));
        Assertions.assertEquals("2 Millisekunden", TimeFormatterHelper.nanosToText(2000 * 1000));
        Assertions.assertEquals("1 Sekunde", TimeFormatterHelper.nanosToText(SECOND));
        Assertions.assertEquals("2 Sekunden", TimeFormatterHelper.nanosToText(2 * SECOND));
        Assertions.assertEquals("1 Minute", TimeFormatterHelper.nanosToText(MINUTE));
        Assertions.assertEquals("2 Minuten", TimeFormatterHelper.nanosToText(2 * MINUTE));
        Assertions.assertEquals("1 Stunde", TimeFormatterHelper.nanosToText(HOUR));
        Assertions.assertEquals("2 Stunden", TimeFormatterHelper.nanosToText(2 * HOUR));
        Assertions.assertEquals("1 Stunde", TimeFormatterHelper.nanosToText(HOUR));
        Assertions.assertEquals("1 Tag, 2 Stunden, 3 Minuten, 4 Sekunden",
                TimeFormatterHelper.nanosToText(DAY + 2 * HOUR + 3 * MINUTE + 4 * SECOND));
        Assertions.assertEquals("1 Tag, 2 Stunden, 3 Minuten, 4 Sekunden, 0 Millisekunden, 0 Mikrosekunden, 1 Nanosekunde",
                TimeFormatterHelper.nanosToText(DAY + 2 * HOUR + 3 * MINUTE + 4 * SECOND + 1));
        Assertions.assertEquals(
                "2 Tage, 23 Stunden, 59 Minuten, 59 Sekunden, 999 Millisekunden, 999 Mikrosekunden, 999 Nanosekunden",
                TimeFormatterHelper.nanosToText(3 * DAY - 1));
        Assertions.assertEquals(
                "106751 Tage, 23 Stunden, 47 Minuten, 16 Sekunden, 854 Millisekunden, 775 Mikrosekunden, 807 Nanosekunden",
                TimeFormatterHelper.nanosToText(Long.MAX_VALUE));

        Assertions.assertThrows(IllegalArgumentException.class, () ->TimeFormatterHelper.nanosToText(-10000));
    }
}