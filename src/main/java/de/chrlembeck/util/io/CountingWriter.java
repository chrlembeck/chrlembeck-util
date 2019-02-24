package de.chrlembeck.util.io;

import java.io.IOException;
import java.io.Writer;

/**
 * Writer, der die Anzahl der geschriebenen Zeichen und Zeilen während des schreibens zählt.
 *
 * @author Christoph Lembeck
 */
public class CountingWriter extends Writer {

    /**
     * Writer, in den die Zeichen nach dem Zählen geschrieben werden.
     */
    private final Writer writer;

    /**
     * Anzahl der geschriebenen Zeichen.
     */
    private long characterCount;

    /**
     * Anzahl der geschriebenen Zeilen.
     */
    private long lineCount = 1;

    /**
     * Erstellt einen neuen Writer mit dem übergebenen Writer als Ziel.
     * 
     * @param writer
     *            Writer, in den die Zeichen nach dem Zählen geschrieben werden sollen.
     */
    public CountingWriter(final Writer writer) {
        this.writer = writer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final char[] cbuf, final int off, final int len) throws IOException {
        writer.write(cbuf, off, len);
        characterCount += len;
        for (int i = 0; i < len; i++) {
            if (cbuf[off + i] == '\n') {
                lineCount++;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        writer.close();
    }

    /**
     * Gibt die Anzahl der bislang geschriebenen Zeichen zurück.
     * 
     * @return Anzahl der innerhalb des Writers geschriebenen Zeichen.
     */
    public long getCharacterCount() {
        return characterCount;
    }

    /**
     * Gibt die Anzahl der bislang geschriebenen Zeilen zurück. Eine neue Zeile wird immer dann angenommen, wenn das
     * Zeichen '\n' geschrieben wurde.
     * 
     * @return Anzahl der bislang geschribenen Zeilen beginnend bei 1.
     */
    public long getLineCount() {
        return lineCount;
    }
}