package de.chrlembeck.util.format;

/**
 * Hilfsklasse zur Formatierung von Zeitangaben.
 * 
 * @author Christpoh Lembeck
 */
public final class TimeFormatterHelper {

    /**
     * Provater Konstruktor zur Verhinderung von Instanzen der Klasse.
     */
    private TimeFormatterHelper() {
    }

    /**
     * Erstellt eine ausformulierte Zeitspanne auf Basis von Nanosekunden. Kann z.B. bei der Ausgabe von Laufzeiten in
     * Log-Nachrichten verwendet werden. Eine Ausgabe hat z.B. die Form "1 Tag, 0 Stunden, 4 Minuten, 12 Sekunden, 134
     * Millisekunden, 423 Mikrosekunden, 1 Nanosekunde" oder "1 Minute, 12 Sekunden".
     * 
     * @param nanos
     *            Zeitspanne in Nanosekunden.
     * @return Textuelle Repräsentation der Zeitspanne.
     * @see System#nanoTime()
     */
    public static String nanosToText(final long nanos) {
        if (nanos < 0) {
            throw new IllegalArgumentException("Non negative value expected, but " + nanos + " found.");
        }

        long time = nanos;
        boolean wasZero = true;
        final StringBuilder output = new StringBuilder();
        final long nanosOnly = time % 1000;
        if (nanosOnly != 0 || time == 0) {
            output.append(nanosOnly);
            output.append(" Nanosekunde" + (nanosOnly == 1 ? "" : "n"));
            wasZero = false;
        }
        time /= 1000;
        final long micro = time % 1000;
        if (time > 0 && (micro > 0 || !wasZero)) {
            output.insert(0, micro + " Mikrosekunde" + (micro == 1 ? "" : "n") + (wasZero ? "" : ", "));
            wasZero = false;
        }
        time /= 1000;
        final long millis = time % 1000;
        if (time > 0 && (millis > 0 || !wasZero)) {
            output.insert(0, millis + " Millisekunde" + (millis == 1 ? "" : "n") + (wasZero ? "" : ", "));
            wasZero = false;
        }
        time /= 1000;
        final long secs = time % 60;
        if (time > 0 && (secs > 0 || !wasZero)) {
            output.insert(0, secs + " Sekunde" + (secs == 1 ? "" : "n") + (wasZero ? "" : ", "));
            wasZero = false;
        }
        time /= 60;
        final long minutes = time % 60;
        if (time > 0 && (minutes > 0 || !wasZero)) {
            output.insert(0, minutes + " Minute" + (minutes == 1 ? "" : "n") + (wasZero ? "" : ", "));
            wasZero = false;
        }
        time /= 60;
        final long hours = time % 24;
        if (time > 0 && (hours > 0 || !wasZero)) {
            output.insert(0, hours + " Stunde" + (hours == 1 ? "" : "n") + (wasZero ? "" : ", "));
            wasZero = false;
        }
        time /= 24;
        if (time > 0) {
            output.insert(0, time + " Tag" + (time == 1 ? "" : "e") + (wasZero ? "" : ", "));
        }
        return output.toString();
    }
}