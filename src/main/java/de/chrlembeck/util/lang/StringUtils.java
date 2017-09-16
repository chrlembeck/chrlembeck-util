package de.chrlembeck.util.lang;

/**
 * Enthält einige Hilfsmethoden zur Verarbeitung von Zeichenketten.
 * 
 * @author Christoph Lembeck
 */
@SuppressWarnings("PMD.UseUtilityClass")
public final class StringUtils {

    /**
     * Hilfs-Array für die formatierung von Bytes als Hexadezimale Darstellung.
     */
    private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };

    /**
     * Gibt true zurück, wenn die Zeichenkette null ist oder kein Zeichen enthält.
     * 
     * @param value
     *            Zu prüfende Zeichenkette.
     * @return true, falls die Zeichenkette null ist oder kein Zeichen enthält.
     * @see String#isEmpty()
     */
    public static boolean isEmpty(final String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Wandelt das übergebene Byte-Array in eine Zeichenkette, in der die Bytes in hexadezimaler Schreibweise enthalten
     * sind, um. Jedes Byte wird dabei durch genau zwei Zeichen im Bereich von (00..FF) dargestellt. Beispiel: [255,20]
     * --&gt; &quot;FF14&quot;
     * 
     * @param bytes
     *            Bytes, die textuell dargestellt werden sollen.
     * @return Die Bytes als hexadezimale Darstellung.
     */
    public static String toHexString(final byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (final byte b : bytes) {
            final int high = (b & 0xf0) >> 4;
            final int low = b & 0xf;
            builder.append(HEX[high]);
            builder.append(HEX[low]);
        }
        return builder.toString();
    }

    /**
     * Ermittelt die Länge der Zeichenkette. {@code null} wird dabei als Länge 0 angesehen.
     * 
     * @param text
     *            Text, dessen Länge ermittelt werden soll.
     * @return Länge des Texts oder 0, falls null übergeben wurde.
     */
    public static int lengthNullToZero(final String text) {
        return text == null ? 0 : text.length();
    }

    /**
     * Verwandelt den ersten Buchstaben der übergebenen Zeichenkette in einen Großbuchstaben.
     * 
     * @param text
     *            Zeichenkette, deren erster Buchstabe umgewandelt werden soll.
     * @return Zeichenkette mit dem ersten Buchstaben als Großbuchstabe, null, falls die Eingabe null war oder eine
     *         leere Zeichenkette, falls die Zeichenkette vorher auch leer war.
     */
    public static String toFirstUpper(final String text) {
        return text == null ? null
                : text.isEmpty() ? ""
                        : Character.isUpperCase(text.charAt(0)) ? text
                                : Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    /**
     * Verwandelt den ersten Buchstaben der übergebenen Zeichenkette in einen Kleinbuchstaben.
     * 
     * @param text
     *            Zeichenkette, deren erster Buchstabe umgewandelt werden soll.
     * @return Zeichenkette mit dem ersten Buchstaben als Kleinbuchstabe, null, falls die Eingabe null war oder eine
     *         leere Zeichenkette, falls die Zeichenkette vorher auch leer war.
     */
    public static String toFirstLower(final String text) {
        return text == null ? null
                : text.isEmpty() ? ""
                        : Character.isLowerCase(text.charAt(0)) ? text
                                : Character.toLowerCase(text.charAt(0)) + text.substring(1);
    }
}