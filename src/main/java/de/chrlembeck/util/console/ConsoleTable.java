package de.chrlembeck.util.console;

import de.chrlembeck.util.lang.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Hilfsklase zur Ausgabe einer spaltenweise ausgerichteten Tabelle auf der Konsole.
 *
 * @author Christoph Lembeck
 */
@SuppressWarnings("PMD.GodClass")
public class ConsoleTable implements Serializable {

    /**
     * Versionsnummer für die Serialisierung.
     */
    private static final long serialVersionUID = 7846284046029453353L;

    /**
     * Hält die Ausrichtungen der Tabellenspalten fest.
     */
    private final Alignment[] alignments;

    /**
     * Hält die horizontalen Ausrichtungen der Tabellenüberschriften fest.
     */
    private final Alignment[] headerAlignments;

    /**
     * Speichert die Einstellungen zur Darstellung der Rahmen und Abstände zwischen einzelnen Zellen und rund um die
     * ganze Tabelle.
     */
    private BorderConfiguration borderConfiguration;

    /**
     * Anzahl der Spalten in der Tabelle.
     */
    private final int columnCount;

    /**
     * Überschriften der einzelnen Spalten.
     */
    private String[] columnNames;

    /**
     * Aktuelle Breite der Tabellenspalten.
     */
    private transient int[] columnWidths;

    /**
     * Speichert die Zeilen der Tabelle. Jede Zeile hat die gleiche Anzahl an Elementen (Spalten).
     */
    private final List<String[]> rows;

    /**
     * Konstanten für die Ausrichtung der Inhalte in den Tabellenzellen und Spaltenüberschriften.
     *
     * @author Christoph Lembeck
     */
    public enum Alignment implements Serializable {

        /**
         * Linksbündige Ausrichtung.
         */
        LEFT,

        /**
         * Zentrierte Ausrichtung.
         */
        CENTER,

        /**
         * Rechtsbündige Ausrichtung.
         */
        RIGHT
    }

    /**
     * Klasse zur Definition der Zeichen, die für das Zeichnen der Tabellenrahmenlinien verwendet werden sollen.
     *
     * @author Christoph Lembeck
     */
    public static class BorderConfiguration {

        /**
         * Zeichen für die Verbindungsstellen am unteren Ende der Spaltentrenner einer Tabelle.
         */
        private char bottomConnector;

        /**
         * Zeichen für die untere, linke Ecke.
         */
        private char bottomLeftCorner;

        /**
         * Zeichen für die untere, rechte Ecke.
         */
        private char bottomRightCorner;

        /**
         * Abstand zwischen Zellinhalten und vertikalem Trenner.
         */
        private int columnSpacing;

        /**
         * Zeichen für die Verbindungsstellen in der Mitte der Tabelle.
         */
        private char crossSymbol;

        /**
         * Zeichen für horizontale Linien.
         */
        private char horizontalLine;

        /**
         * Zeichen für die Verbindungsstellen am linken Rand der Tabelle.
         */
        private char leftConnector;

        /**
         * True, falls der Rahmen am unteren Ende der Tabelle gezeichnet werden soll, false, falls nicht.
         */
        private boolean printBottomBorder;

        /**
         * True, falls zwischen den Spalten vertikale Trenner gezeichnet werden sollen, false, falls nicht.
         */
        private boolean printColumnSeparators;

        /**
         * True, falls zwischen Tabellenkopf und Tabelleninhalt ein horizontaler Trenner gezeichnet werden soll, false,
         * falls nicht.
         */
        private boolean printHeaderTableSeparator;

        /**
         * True, falls der Rahmen am linken Ende der Tabelle gezeichnet werden soll, false, falls nicht.
         */
        private boolean printLeftBorder;

        /**
         * True, falls der Rahmen am rechten Ende der Tabelle gezeichnet werden soll, false, falls nicht.
         */
        private boolean printRightBorder;

        /**
         * True, falls der Rahmen am oberen Ende der Tabelle gezeichnet werden soll, false, falls nicht.
         */
        private boolean printTopBorder;

        /**
         * Zeichen für die Verbindungsstellen am rechten Rand der Tabelle.
         */
        private char rightConnector;

        /**
         * Zeichen für die Verbindungsstellen am oberen Rand der Tabelle.
         */
        private char topConnector;

        /**
         * Zeichen für die obere, linke Ecke.
         */
        private char topLeftCorner;

        /**
         * Zeichen für die obere, rechte Ecke.
         */
        private char topRightCorner;

        /**
         * Zeichen für vertikale Linien.
         */
        private char verticalLine;

        /**
         * Gibt das Zeichen für die Verbindungsstellen am unteren Ende der Spaltentrenner einer Tabelle zurück.
         * 
         * @return Zeichen für die Verbindungsstellen am unteren Ende der Spaltentrenner einer Tabelle.
         */
        public char getBottomConnector() {
            return bottomConnector;
        }

        /**
         * Gibt das Zeichen für die Darstellung der unteren, linken Ecke aus.
         * 
         * @return Zeichen für die Darstellung der unteren, linken Ecke.
         */
        public char getBottomLeftCorner() {
            return bottomLeftCorner;
        }

        /**
         * Gibt das Zeichen für die Darstellung der unteren, rechten Ecke aus.
         * 
         * @return Zeichen für die Darstellung der unteren, rechten Ecke.
         */
        public char getBottomRightCorner() {
            return bottomRightCorner;
        }

        /**
         * Gibt den Abstand zwischen vertikalen Linien zwischen den Spalten und dem eigentlichen Zellinhalt zurück.
         * 
         * @return Abstand zwischen Spaltentrennern und Zellinhalt in Zeichen.
         */
        public int getColumnSpacing() {
            return columnSpacing;
        }

        /**
         * Gibt das Symbol für die Darstellung von Linienverbindungen innerhalb der Tabelle zurück.
         * 
         * @return Symbol für die Darstellung von Kreuzungen der vertikalen und horizontalen Linien.
         */
        public char getCrossSymbol() {
            return crossSymbol;
        }

        /**
         * Gibt das Zeichen für die Darstellung der horizontalen Linien zurück.
         * 
         * @return Zeichen für die Darstellung der horizontalen Linien in der Tabelle.
         */
        public char getHorizontalLine() {
            return horizontalLine;
        }

        /**
         * Gibt das Zeichen für die Verbindungsstellen am linken Ende der Spaltentrenner einer Tabelle zurück.
         * 
         * @return Zeichen für die Verbindungsstellen am linken Ende der Spaltentrenner einer Tabelle.
         */
        public char getLeftConnector() {
            return leftConnector;
        }

        /**
         * Gibt das Zeichen für die Verbindungsstellen am rechten Ende der Spaltentrenner einer Tabelle zurück.
         * 
         * @return Zeichen für die Verbindungsstellen am rechten Ende der Spaltentrenner einer Tabelle.
         */
        public char getRightConnector() {
            return rightConnector;
        }

        /**
         * Gibt das Zeichen für die Verbindungsstellen am oberen Ende der Spaltentrenner einer Tabelle zurück.
         * 
         * @return Zeichen für die Verbindungsstellen am oberen Ende der Spaltentrenner einer Tabelle.
         */
        public char getTopConnector() {
            return topConnector;
        }

        /**
         * Gibt das Zeichen für die Darstellung der oberen, linken Ecke aus.
         * 
         * @return Zeichen für die Darstellung der oberen, linken Ecke.
         */
        public char getTopLeftCorner() {
            return topLeftCorner;
        }

        /**
         * Gibt das Zeichen für die Darstellung der oberen, rechten Ecke aus.
         * 
         * @return Zeichen für die Darstellung der oberen, rechten Ecke.
         */
        public char getTopRightCorner() {
            return topRightCorner;
        }

        /**
         * Gibt das Zeichen für die Darstellung der vertikalen Linien zurück.
         * 
         * @return Zeichen für die Darstellung der vertikalen Linien in der Tabelle.
         */
        public char getVerticalLine() {
            return verticalLine;
        }

        /**
         * Gibt aus, ob der untere Rand der Tabelle dargestellt werden soll.
         * 
         * @return true, falls der untere Rand der Tabelle dargestellt werden soll, false, falls nicht.
         */
        public boolean isPrintBottomBorder() {
            return printBottomBorder;
        }

        /**
         * Gibt aus, ob die Trennlinien zwischen den Spalten gezeichnet werden sollen.
         * 
         * @return true, falls die Trennlinien zwischen den Spalten gezeichnet werden sollen, sonst false.
         */
        public boolean isPrintColumnSeparators() {
            return printColumnSeparators;
        }

        /**
         * Gibt aus, ob die Trennlinie zwischen Tabellenkopf und Tabelleninhalt gezeichnet werden solle.
         * 
         * @return true, falls die Trennlinien zwischen Tabellenkopf und Tabelleninhalt gezeichnet werden soll, sonst
         *         false.
         */
        public boolean isPrintHeaderTableSeparator() {
            return printHeaderTableSeparator;
        }

        /**
         * Gibt aus, ob der linke Rand der Tabelle dargestellt werden soll.
         * 
         * @return true, falls der linke Rand der Tabelle dargestellt werden soll, false, falls nicht.
         */
        public boolean isPrintLeftBorder() {
            return printLeftBorder;
        }

        /**
         * Gibt aus, ob der rechte Rand der Tabelle dargestellt werden soll.
         * 
         * @return true, falls der rechte Rand der Tabelle dargestellt werden soll, false, falls nicht.
         */
        public boolean isPrintRightBorder() {
            return printRightBorder;
        }

        /**
         * Gibt aus, ob der obere Rand der Tabelle dargestellt werden soll.
         * 
         * @return true, falls der obere Rand der Tabelle dargestellt werden soll, false, falls nicht.
         */
        public boolean isPrintTopBorder() {
            return printTopBorder;
        }

        /**
         * Setzt das Zeichen für die Verbindungsstellen am unteren Ende der Spaltentrenner der Tabelle.
         * 
         * @param bottomConnector
         *            Zeichen für die Verbindungsstellen am unteren Ende der Spaltentrenner der Tabelle.
         */
        public void setBottomConnector(final char bottomConnector) {
            this.bottomConnector = bottomConnector;
        }

        /**
         * Legt das Zeichen für die Darstellung der unteren, linken Ecke fest.
         * 
         * @param bottomLeftCorner
         *            Neues Zeichen für die Darstellung der unteren, linken Ecke.
         */
        public void setBottomLeftCorner(final char bottomLeftCorner) {
            this.bottomLeftCorner = bottomLeftCorner;
        }

        /**
         * Legt das Zeichen für die Darstellung der unteren, rechten Ecke fest.
         * 
         * @param bottomRightCorner
         *            Neues Zeichen für die Darstellung der unteren, rechten Ecke.
         */
        public void setBottomRightCorner(final char bottomRightCorner) {
            this.bottomRightCorner = bottomRightCorner;
        }

        /**
         * Legt den Abstand zwischen vertikalen Linien zwischen den Spalten und dem eigentlichen Zellinhalt fest.
         * 
         * @param columnSpacing
         *            Abstand zwischen Spaltentrennern und Zellinhalt in Zeichen.
         */
        public void setColumnSpacing(final int columnSpacing) {
            this.columnSpacing = columnSpacing;
        }

        /**
         * Legt das Symbol für die Darstellung von Linienverbindungen innerhalb der Tabelle fest.
         * 
         * @param crossSymbol
         *            Neues Symbol für die Darstellung von Kreuzungen der vertikalen und horizontalen Linien.
         */
        public void setCrossSymbol(final char crossSymbol) {
            this.crossSymbol = crossSymbol;
        }

        /**
         * Legt das Zeichen für die Darstellung horizontaler Linien fest.
         * 
         * @param horizontalLine
         *            Neues Zeichen für die Darstellung horizontaler Linien in der Tabelle.
         */
        public void setHorizontalLine(final char horizontalLine) {
            this.horizontalLine = horizontalLine;
        }

        /**
         * Setzt das Zeichen für die Verbindungsstellen am linken Ende der Spaltentrenner der Tabelle.
         * 
         * @param leftConnector
         *            Zeichen für die Verbindungsstellen am linken Ende der Spaltentrenner der Tabelle.
         */
        public void setLeftConnector(final char leftConnector) {
            this.leftConnector = leftConnector;
        }

        /**
         * Legt fest, ob der untere Rand der Tabelle dargestellt werden soll.
         * 
         * @param printBottomBorder
         *            true, falls der untere Rand der Tabelle dargestellt werden soll, false, falls nicht.
         */
        public void setPrintBottomBorder(final boolean printBottomBorder) {
            this.printBottomBorder = printBottomBorder;
        }

        /**
         * Legt fest, ob die Trennlinien zwischen den Spalten gezeichnet werden sollen.
         * 
         * @param printColumnSeparators
         *            true, falls die Trennlinien zwischen den Spalten gezeichnet werden sollen, sonst false.
         */
        public void setPrintColumnSeparators(final boolean printColumnSeparators) {
            this.printColumnSeparators = printColumnSeparators;
        }

        /**
         * Legt fest, ob die Trennlinie zwischen Tabellenkopf und Tabelleninhalt gezeichnet werden soll.
         * 
         * @param printHeaderTableSeparator
         *            true, falls die Trennlinie zwischen Tabellenkopf und Tabelleninhalt gezeichnet werden soll, sonst
         *            false.
         */
        public void setPrintHeaderTableSeparator(final boolean printHeaderTableSeparator) {
            this.printHeaderTableSeparator = printHeaderTableSeparator;
        }

        /**
         * Legt fest, ob der linke Rand der Tabelle dargestellt werden soll.
         * 
         * @param printLeftBorder
         *            true, falls der linke Rand der Tabelle dargestellt werden soll, false, falls nicht.
         */
        public void setPrintLeftBorder(final boolean printLeftBorder) {
            this.printLeftBorder = printLeftBorder;
        }

        /**
         * Legt fest, ob der rechte Rand der Tabelle dargestellt werden soll.
         * 
         * @param printRightBorder
         *            true, falls der rechte Rand der Tabelle dargestellt werden soll, false, falls nicht.
         */
        public void setPrintRightBorder(final boolean printRightBorder) {
            this.printRightBorder = printRightBorder;
        }

        /**
         * Legt fest, ob der obere Rand der Tabelle dargestellt werden soll.
         * 
         * @param printTopBorder
         *            true, falls der obere Rand der Tabelle dargestellt werden soll, false, falls nicht.
         */
        public void setPrintTopBorder(final boolean printTopBorder) {
            this.printTopBorder = printTopBorder;
        }

        /**
         * Setzt das Zeichen für die Verbindungsstellen am rechten Ende der Spaltentrenner der Tabelle.
         * 
         * @param rightConnector
         *            Zeichen für die Verbindungsstellen am rechten Ende der Spaltentrenner der Tabelle.
         */
        public void setRightConnector(final char rightConnector) {
            this.rightConnector = rightConnector;
        }

        /**
         * Setzt das Zeichen für die Verbindungsstellen am oberen Ende der Spaltentrenner der Tabelle.
         * 
         * @param topConnector
         *            Zeichen für die Verbindungsstellen am oberen Ende der Spaltentrenner der Tabelle.
         */
        public void setTopConnector(final char topConnector) {
            this.topConnector = topConnector;
        }

        /**
         * Legt das Zeichen für die Darstellung der oberen, linken Ecke fest.
         * 
         * @param topLeftCorner
         *            Neues Zeichen für die Darstellung der oberen, linken Ecke.
         */
        public void setTopLeftCorner(final char topLeftCorner) {
            this.topLeftCorner = topLeftCorner;
        }

        /**
         * Legt das Zeichen für die Darstellung der oberen, rechten Ecke fest.
         * 
         * @param topRightCorner
         *            Neues Zeichen für die Darstellung der oberen, rechten Ecke.
         */
        public void setTopRightCorner(final char topRightCorner) {
            this.topRightCorner = topRightCorner;
        }

        /**
         * Legt das Zeichen für die Darstellung vertikaler Linien fest.
         * 
         * @param verticalLine
         *            Neues Zeichen für die Darstellung vertikaler Linien in der Tabelle.
         */
        public void setVerticalLine(final char verticalLine) {
            this.verticalLine = verticalLine;
        }
    }

    /**
     * Erstellt eine neue Tabelle mit der übergebenen Anzahl an Spalten.
     * 
     * @param columnCount
     *            Anzahl der in der Tabelle enthaltenen Spalten.
     */
    public ConsoleTable(final int columnCount) {
        this.columnCount = columnCount;
        this.alignments = new Alignment[columnCount];
        this.headerAlignments = new Alignment[columnCount];
        this.columnWidths = new int[columnCount];
        Arrays.fill(alignments, Alignment.LEFT);
        Arrays.fill(headerAlignments, Alignment.CENTER);
        rows = new ArrayList<String[]>();
        this.borderConfiguration = createDefaultBorderCofiguration();
    }

    /**
     * Fügt eine neue Zeile zu der Tabelle hinzu.
     * 
     * @param entries
     *            Inhalte der Zellen der Spalte in der Reihenfolge von links nach rechts.
     * @throws IllegalArgumentException
     *             falls die Anzahl der Überschriften nicht mit der Anzahl der Spalten übereinstimmt.
     */
    public void addRow(final String... entries) {
        if (entries.length != columnCount) {
            throw new IllegalArgumentException("The table has " + columnCount + " columns, but the new row has "
                    + entries.length + " entries: " + Arrays.toString(entries));
        }
        for (int i = 0; i < columnCount; i++) {
            columnWidths[i] = Math.max(columnWidths[i], StringUtils.lengthNullToZero(entries[i]));
        }
        rows.add(entries);
    }

    /**
     * Hilfsmethode zum mehrfachen Hinzufügen eines Zeichens zu einem StringBuilder.
     * 
     * @param stringBuilder
     *            StringBuilder, zu dem das Zeichen hinzugefügt werden soll.
     * @param character
     *            Hinzuzufügendes Zeichen.
     * @param count
     *            Anzahl, in der das Zeichen hinzugefügt werden soll.
     */
    protected static final void append(final StringBuilder stringBuilder, final char character, final int count) {
        for (int i = 0; i < count; i++) {
            stringBuilder.append(character);
        }
    }

    /**
     * Fügt, falls benötigt, einen gewünschten Leerraum zwischen Zelleninhalten und Zellenumrandung ein.
     * 
     * @param stringBuilder
     *            StringBuilder, in den die Tabelle geschrieben wird.
     */
    protected final void appendColumnSeparator(final StringBuilder stringBuilder) {
        append(stringBuilder, ' ', getColumnSpacing());
        if (isPrintColumnSeparators()) {
            stringBuilder.append(borderConfiguration.getVerticalLine());
            append(stringBuilder, ' ', getColumnSpacing());
        }
    }

    /**
     * Fügt den linken Rand einer Zeile hinzu, falls ein solcher gewünscht ist. Ansonsten wird der StringBuilder nicht
     * verändert.
     * 
     * @param stringBuilder
     *            StringBuilder, an den der linke Rand hinzugefügt werden soll.
     * @see #isPrintLeftBorder()
     */
    protected final void appendLeftBorder(final StringBuilder stringBuilder) {
        if (isPrintLeftBorder()) {
            stringBuilder.append(borderConfiguration.getVerticalLine());
            append(stringBuilder, ' ', getColumnSpacing());
        }
    }

    /**
     * Fügt den rechten Rand einer Zeile hinzu, falls ein solcher gewünscht ist. Ansonsten wird der StringBuilder nicht
     * verändert.
     * 
     * @param stringBuilder
     *            StringBuilder, an den der rechte Rand hinzugefügt werden soll.
     * @see #isPrintRightBorder()
     */
    protected final void appendRightBorder(final StringBuilder stringBuilder) {
        if (isPrintRightBorder()) {
            append(stringBuilder, ' ', getColumnSpacing());
            stringBuilder.append(borderConfiguration.getVerticalLine());
        }
    }

    /**
     * Fügt die Repräsentation der Tabelle an den übergebenen StringBuilder an. Zeilenumbrüche werden dabei mit
     * hinzugefügt.
     * 
     * @param stringBuilder
     *            StringBuilder, in den die Tabelle geschrieben werden soll.
     */
    public void appendTo(final StringBuilder stringBuilder) {
        if (isPrintTopBorder()) {
            // oberer Rahmen
            printHorizontalSeparator(stringBuilder, borderConfiguration.getTopLeftCorner(),
                    borderConfiguration.getTopConnector(),
                    borderConfiguration.getTopRightCorner());
        }
        if (columnNames != null) {
            appendLeftBorder(stringBuilder);
            appendValue(stringBuilder, columnNames[0], columnWidths[0], headerAlignments[0]);
            for (int i = 1; i < columnCount; i++) {
                appendColumnSeparator(stringBuilder);
                appendValue(stringBuilder, columnNames[i], columnWidths[i], headerAlignments[i]);
            }
            appendRightBorder(stringBuilder);
            stringBuilder.append('\n');
            if (isPrintHeaderTableSeparator()) {
                // Trennlinie zwischen Überschrift und Tabelle
                printHorizontalSeparator(stringBuilder, borderConfiguration.getLeftConnector(),
                        borderConfiguration.getCrossSymbol(),
                        borderConfiguration.getRightConnector());
            }
        }
        for (final String[] row : rows) {
            appendLeftBorder(stringBuilder);
            appendValue(stringBuilder, row[0], columnWidths[0], alignments[0]);
            for (int i = 1; i < columnCount; i++) {
                appendColumnSeparator(stringBuilder);
                appendValue(stringBuilder, row[i], columnWidths[i], alignments[i]);
            }
            appendRightBorder(stringBuilder);
            stringBuilder.append('\n');
        }
        if (isPrintBottomBorder()) {
            // unterer Rahmen
            printHorizontalSeparator(stringBuilder, borderConfiguration.getBottomLeftCorner(),
                    borderConfiguration.getBottomConnector(),
                    borderConfiguration.getBottomRightCorner());
        }
    }

    /**
     * Fügt den Inhalt einer Zelle zu dem StringBuilder hinzu. Dabei wird ein ggf. benötigter Leerraum mit hinzugefügt
     * und die Ausrichtung des Inhalts gemäß Vorgaben berücksichtigt.
     * 
     * @param stringBuilder
     *            StringBuilder, zu dem der Zelleninhalt hinzugefügt werden soll.
     * @param text
     *            Texttuelle Darstellung des Inhalts.
     * @param columnWidth
     *            Breite der Spalte, in der die Zelle sich befindet.
     * @param alignment
     *            Ausrichtung der Inhalte der Spalte.
     */
    protected static final void appendValue(final StringBuilder stringBuilder, final String text, final int columnWidth,
            final Alignment alignment) {
        final int length = StringUtils.lengthNullToZero(text);
        final int space = columnWidth - length;
        final int leftSpace;
        final int rightSpace;
        switch (alignment) {
            case LEFT:
                leftSpace = 0;
                rightSpace = space;
                break;
            case CENTER:
                leftSpace = space / 2;
                rightSpace = space - leftSpace;
                break;
            case RIGHT:
                leftSpace = space;
                rightSpace = 0;
                break;
            default:
                throw new IllegalArgumentException("Unknown alignment: " + alignment);
        }
        append(stringBuilder, ' ', leftSpace);
        if (text != null) {
            stringBuilder.append(text);
        }
        append(stringBuilder, ' ', rightSpace);
    }

    /**
     * Erstellt die Standardeinstellungen für das Layout der Tabellenrahmenlinien.
     * 
     * @return Standardeinstellungen für das Rahmenlayout.
     */
    public final BorderConfiguration createDefaultBorderCofiguration() {
        final BorderConfiguration config = new BorderConfiguration();
        config.setBottomConnector('+');
        config.setTopConnector('+');
        config.setLeftConnector('+');
        config.setRightConnector('+');
        config.setVerticalLine('|');
        config.setHorizontalLine('-');
        config.setTopLeftCorner('+');
        config.setTopRightCorner('+');
        config.setBottomLeftCorner('+');
        config.setBottomRightCorner('+');
        config.setCrossSymbol('+');
        config.setPrintBottomBorder(true);
        config.setPrintLeftBorder(true);
        config.setPrintRightBorder(true);
        config.setPrintTopBorder(true);
        config.setPrintColumnSeparators(true);
        config.setPrintHeaderTableSeparator(true);
        config.setColumnSpacing(1);
        return config;
    }

    /**
     * Erstellt die Rahmenkonfiguration für Rahmen mit einfachen Linien, die mit Unicode-Sonderzeichen versehen werden
     * sollen.
     * 
     * @param roundCorner
     *            true, falls die Ecken abgerundet werden sollen, false, falls die Ecken nicht abgerundet werden sollen.
     * @return Rahmenkonfiguration für grafisch anspruchsvolle Rahmen aus Unicode-Sonderzeichen.
     * 
     * @see <a href=
     *      "http://de.wikipedia.org/wiki/Unicodeblock_Rahmenzeichnung">http://de.wikipedia.org/wiki/Unicodeblock_Rahmenzeichnung</a>
     */
    public BorderConfiguration createUnicodeBorderCofiguration(final boolean roundCorner) {
        final BorderConfiguration config = new BorderConfiguration();
        config.setBottomConnector('\u2534'); // BOX DRAWINGS LIGHT UP AND HORIZONTAL
        config.setTopConnector('\u252C'); // BOX DRAWINGS LIGHT DOWN AND HORIZONTAL
        config.setLeftConnector('\u251C'); // BOX DRAWINGS LIGHT VERTICAL AND RIGHT
        config.setRightConnector('\u2524'); // BOX DRAWINGS LIGHT VERTICAL AND LEFT
        config.setVerticalLine('\u2502'); // BOX DRAWINGS LIGHT VERTICAL
        config.setHorizontalLine('\u2500'); // BOX DRAWINGS LIGHT HORIZONTAL
        config.setTopLeftCorner(roundCorner ? '\u256D' : '\u250C'); // TOP LEFT CORNER
        config.setTopRightCorner(roundCorner ? '\u256E' : '\u2510'); // TOP RIGH CORNER
        config.setBottomLeftCorner(roundCorner ? '\u2570' : '\u2514'); // BOTTOM LEFT CORNER
        config.setBottomRightCorner(roundCorner ? '\u256F' : '\u2518'); // BOTTOM RIGHT CORNER
        config.setCrossSymbol('\u253C'); // BOX DRAWINGS LIGHT VERTICAL AND HORIZONTAL
        config.setPrintBottomBorder(true);
        config.setPrintLeftBorder(true);
        config.setPrintRightBorder(true);
        config.setPrintTopBorder(true);
        config.setPrintColumnSeparators(true);
        config.setPrintHeaderTableSeparator(true);
        config.setColumnSpacing(1);
        return config;
    }

    /**
     * Erstellt die Rahmenkonfiguration für Rahmen mit doppelten Linien, die mit Unicode-Sonderzeichen versehen werden
     * sollen.
     * 
     * @return Rahmenkonfiguration für grafisch anspruchsvolle Rahmen aus Unicode-Sonderzeichen.
     * 
     * @see <a href=
     *      "http://de.wikipedia.org/wiki/Unicodeblock_Rahmenzeichnung">http://de.wikipedia.org/wiki/Unicodeblock_Rahmenzeichnung</a>
     */
    public BorderConfiguration createUnicodeDoubleBorderCofiguration() {
        final BorderConfiguration config = new BorderConfiguration();
        config.setBottomConnector('\u2569'); // BOX DRAWINGS DOUBLE UP AND HORIZONTAL
        config.setTopConnector('\u2566'); // BOX DRAWINGS DOUBLE DOWN AND HORIZONTAL
        config.setLeftConnector('\u2560'); // BOX DRAWINGS DOUBLE VERTICAL AND RIGHT
        config.setRightConnector('\u2563'); // BOX DRAWINGS DOUBLE VERTICAL AND LEFT
        config.setVerticalLine('\u2551'); // BOX DRAWINGS DOUBLE VERTICAL
        config.setHorizontalLine('\u2550'); // BOX DRAWINGS DOUBLE HORIZONTAL
        config.setTopLeftCorner('\u2554'); // BOX DRAWINGS DOUBLE DOWN AND RIGHT
        config.setTopRightCorner('\u2557'); // BOX DRAWINGS DOUBLE DOWN AND LEFT
        config.setBottomLeftCorner('\u255A'); // BOX DRAWINGS DOUBLE UP AND RIGHT
        config.setBottomRightCorner('\u255D'); // BOX DRAWINGS DOUBLE UP AND LEFT
        config.setCrossSymbol('\u256C'); // BOX DRAWINGS DOUBLE VERTICAL AND HORIZONTAL
        config.setPrintBottomBorder(true);
        config.setPrintLeftBorder(true);
        config.setPrintRightBorder(true);
        config.setPrintTopBorder(true);
        config.setPrintColumnSeparators(true);
        config.setPrintHeaderTableSeparator(true);
        config.setColumnSpacing(1);
        return config;
    }

    /**
     * Gibt die aktuellen Einstellungen für das Rahmenlayout zurück.
     * 
     * @return Aktuelles Rahmenlayout.
     */
    public BorderConfiguration getBorderConfiguration() {
        return borderConfiguration;
    }

    /**
     * Gibt die Anzahl der Spalten der Tabelle zurück.
     * 
     * @return Anzahl der Spalten in der Tabelle.
     */
    public int getColumCount() {
        return columnCount;
    }

    /**
     * Gibt die Anzahl der Leerzeichen zwischen den Spalten und den vertikalen Trennlinien zurück.
     * 
     * @return Anzahl der Leerzeichen zwsichen Spalten und vertikalen Trennlinien.
     */
    public int getColumnSpacing() {
        return borderConfiguration.getColumnSpacing();
    }

    /**
     * Gibt zurück, ob der untere Rahmen der Tabelle gezeichnet werden soll.
     * 
     * @return true, falls der untere Rahmen gezeichnet werden soll, false, falls nicht.
     */
    public boolean isPrintBottomBorder() {
        return borderConfiguration.isPrintBottomBorder();
    }

    /**
     * Gibt zurück, ob die Trennlinien zwischen den spalten gezeichnet werden sollen oder nicht.
     * 
     * @return true, falls die Spaltentrennlinien gezeichnet werden sollen, sonst false.
     */
    public boolean isPrintColumnSeparators() {
        return borderConfiguration.isPrintColumnSeparators();
    }

    /**
     * Gibt zurück, ob die Trennlinie zwischen Tabellenkopf und Tabelleninhalt gezeichnet werden soll.
     * 
     * @return true, falls die Trennlinie zwischen Kopf und Inhalt gezeichnet werden soll, sonst false.
     */
    public boolean isPrintHeaderTableSeparator() {
        return borderConfiguration.isPrintHeaderTableSeparator();
    }

    /**
     * Gibt zurück, ob der linke Rahmen der Tabelle gezeichnet werden soll.
     * 
     * @return true, falls der linke Rahmen gezeichnet werden soll, sonst false.
     */
    public boolean isPrintLeftBorder() {
        return borderConfiguration.isPrintLeftBorder();
    }

    /**
     * Gibt zurück, ob der rechte Rahmen der Tabelle gezeichnet werden soll.
     * 
     * @return true, falls der rechte Rahmen gezeichnet werden soll, sonst false.
     */
    public boolean isPrintRightBorder() {
        return borderConfiguration.isPrintRightBorder();
    }

    /**
     * Gibt zurück, ob der obere Rahmen der Tabelle gezeichnet werden soll.
     * 
     * @return true, falls der obere Rahmen gezeichnet werden soll, sonst false.
     */
    public boolean isPrintTopBorder() {
        return borderConfiguration.isPrintTopBorder();
    }

    /**
     * Fügt einen horizontalen Trennstrich inclusive Zeilenumbruch ein.
     * 
     * @param stringBuilder
     *            StringBuilder, an den der Trennstrich angehängt werden soll.
     * @param leftConnector
     *            Symbol für den Anschluss des linken Tabellenrands.
     * @param middleConnector
     *            Symbol für den Anschluss von Rahmen in der Mitte.
     * @param rightConnector
     *            Symbol für den Anschluss des rechten Tabellenrands.
     */
    protected void printHorizontalSeparator(final StringBuilder stringBuilder, final char leftConnector,
            final char middleConnector,
            final char rightConnector) {
        if (isPrintLeftBorder()) {
            stringBuilder.append(leftConnector);
            append(stringBuilder, borderConfiguration.getHorizontalLine(), getColumnSpacing());
        }
        append(stringBuilder, borderConfiguration.getHorizontalLine(), columnWidths[0]);
        for (int i = 1; i < columnCount; i++) {
            if (isPrintColumnSeparators()) {
                append(stringBuilder, borderConfiguration.getHorizontalLine(), getColumnSpacing());
                stringBuilder.append(middleConnector);
                append(stringBuilder, borderConfiguration.getHorizontalLine(), getColumnSpacing());
            }
            append(stringBuilder, borderConfiguration.getHorizontalLine(), columnWidths[i]);
        }
        if (isPrintRightBorder()) {
            append(stringBuilder, borderConfiguration.getHorizontalLine(), getColumnSpacing());
            stringBuilder.append(rightConnector);
        }
        stringBuilder.append('\n');
    }

    /**
     * Berechnet die Spaltenbreiten anhand der bislang eingefügten Tabelleninhalte neu.
     */
    protected void recalculateColumnWidths() {
        Arrays.fill(columnWidths, 0);
        if (columnNames != null) {
            for (int i = 0; i < columnCount; i++) {
                columnWidths[i] = Math.max(columnWidths[i], StringUtils.lengthNullToZero(columnNames[i]));
            }
        }
        for (final String[] row : rows) {
            for (int i = 0; i < columnCount; i++) {
                columnWidths[i] = Math.max(columnWidths[i], StringUtils.lengthNullToZero(row[i]));
            }
        }
    }

    /**
     * Legt die horizontale Ausrichtung des Spalteninhalts für die angegebene Spalte fest.
     * 
     * @param columnIndex
     *            Index der Spalte, dessen Ausrichtung bestimmt werden soll.
     * @param alignment
     *            Neue Ausrichtung für die Spalte.
     * @see Alignment
     */
    public void setAlignment(final int columnIndex, final Alignment alignment) {
        this.alignments[columnIndex] = alignment;
    }

    /**
     * Setzt die Einstellungen für das Rahmenlinienlayout.
     * 
     * @param borderConfiguration
     *            Neue Layoutrichtlinien für das Rahmenlayout.
     */
    public void setBorderConfiguration(final BorderConfiguration borderConfiguration) {
        this.borderConfiguration = borderConfiguration;
    }

    /**
     * Legt die Spaltenüberschriften für die einzelnen Spalten fest.
     * 
     * @param headerNames
     *            Überschriften in der Reihenfolge von links nach rechts.
     * @throws IllegalArgumentException
     *             falls die Anzahl der Überschriften nicht mit der Anzahl der Spalten übereinstimmt.
     */
    public void setColumnNames(final String... headerNames) {
        if (headerNames.length != columnCount) {
            throw new IllegalArgumentException("The table has " + columnCount + " columns, but there are "
                    + headerNames.length + " column names: " + Arrays.toString(headerNames));
        }
        this.columnNames = headerNames.clone();
        recalculateColumnWidths();
    }

    /**
     * Legt den Abstand zwischen vertikalen linien und den Zellinhalten fest.
     * 
     * @param columnSpacing
     *            Abstand zwischen vertikalen Linen und Zellinhalt in Zeichen.
     */
    public void setColumnSpacing(final int columnSpacing) {
        borderConfiguration.setColumnSpacing(columnSpacing);
    }

    /**
     * Legt fest, ob die Tabelle am unteren Rand mit einem Rahmen versehen werden soll.
     * 
     * @param printBottomBorder
     *            true, falls der Rahmen gezeichnet werden soll, false, falls nicht.
     */
    public void setPrintBottomBorder(final boolean printBottomBorder) {
        borderConfiguration.setPrintBottomBorder(printBottomBorder);
    }

    /**
     * Legt fest, ob zwischen den Spalten eine Trennlinie gezeichnet werden soll.
     * 
     * @param printColumnSeparators
     *            true, falls die vertikalen lininen gezeichnet werden sollen, sonst false.
     */
    public void setPrintColumnSeparators(final boolean printColumnSeparators) {
        borderConfiguration.setPrintColumnSeparators(printColumnSeparators);
    }

    /**
     * Legt fest, ob zwischen dem Tabellenkopf und dem Tabelleninhalt eine horizontale Linie eingezeichnet werden soll.
     * 
     * @param value
     *            true, falls die Trennlinie zwischen Tabellenkopf und Tabelleninhalt gezeichnet werden soll.
     */
    public void setPrintHeaderTableSeparator(final boolean value) {
        borderConfiguration.setPrintHeaderTableSeparator(value);
    }

    /**
     * Legt fest, ob die Tabelle am linken Rand mit einem Rahmen versehen werden soll.
     * 
     * @param printLeftBorder
     *            true, falls der Rahmen gezeichnet werden soll, false, falls nicht.
     */
    public void setPrintLeftBorder(final boolean printLeftBorder) {
        borderConfiguration.setPrintLeftBorder(printLeftBorder);
    }

    /**
     * Legt fest, ob die Tabelle am rechten Rand mit einem Rahmen versehen werden soll.
     * 
     * @param printRightBorder
     *            true, falls der Rahmen gezeichnet werden soll, false, falls nicht.
     */
    public void setPrintRightBorder(final boolean printRightBorder) {
        borderConfiguration.setPrintRightBorder(printRightBorder);
    }

    /**
     * Legt fest, ob die Tabelle am oberen Rand mit einem Rahmen versehen werden soll.
     * 
     * @param printTopBorder
     *            true, falls der Rahmen gezeichnet werden soll, false, falls nicht.
     */
    public void setPrintTopBorder(final boolean printTopBorder) {
        borderConfiguration.setPrintTopBorder(printTopBorder);
    }

    /**
     * Gibt die Tabelle als String-Repräsentation zurück. Hierin sind die Zeilenumbrüche für eine korrekte Darstellung
     * bereits enthalten.
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        appendTo(stringBuilder);
        return stringBuilder.toString();
    }
}