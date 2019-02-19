package de.chrlembeck.util.swing;

import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests für den SimpleDocumentListener
 *
 * @author Christoph Lembeck
 */
public class SimpleDocumentListenerTest {

    /**
     * Prüft, ob der Listener bei den drei Operationen auf Document-Objekten aufgerufen wird.
     *
     * @throws Exception
     *             Falls es einen Fehler im Test gibt.
     */
    @Test
    public void testListener() throws Exception {
        final DefaultStyledDocument doc = new DefaultStyledDocument();
        final TestConsumer consumer = new TestConsumer();
        Assertions.assertFalse(consumer.wasCalled());
        final SimpleDocumentListener listener = new SimpleDocumentListener(consumer);
        doc.addDocumentListener(listener);
        doc.insertString(0, "Hello", new SimpleAttributeSet());
        Assertions.assertTrue(consumer.wasCalled());
        Assertions.assertEquals(consumer.getLastEvent().getType(), EventType.INSERT);
        consumer.reset();
        final SimpleAttributeSet italicStyle = new SimpleAttributeSet();
        StyleConstants.setItalic(italicStyle, true);
        doc.setCharacterAttributes(0, 5, italicStyle, false);
        Assertions.assertTrue(consumer.wasCalled());
        Assertions.assertEquals(EventType.CHANGE, consumer.getLastEvent().getType());
        consumer.reset();
        doc.remove(0, 5);
        Assertions.assertTrue(consumer.wasCalled());
        Assertions.assertEquals(EventType.REMOVE, consumer.getLastEvent().getType());
        consumer.reset();
    }

    /**
     * Registirert das Aufrufen der accept-Methode.
     *
     * @author Christoph Lembeck
     */
    static class TestConsumer implements Consumer<DocumentEvent> {

        /**
         * true, falls die accept-Methode aufgerufen wurde.
         */
        private boolean called;

        /**
         * Referenz auf das zuletzt an den Listener übergebene Event.
         */
        private DocumentEvent lastEvent;

        /**
         * {@inheritDoc}
         */
        @Override
        public void accept(final DocumentEvent event) {
            called = true;
            this.lastEvent = event;
        }

        /**
         * Gibt zurück, ob die accept-Methode seit dem letzten Reset aufgerufen wurde.
         *
         * @return true, falls accept aufgerufen wurde, sonst false.
         */
        public boolean wasCalled() {
            return called;
        }

        /**
         * Setzt den Listener wieder in den Anfangszustand zurück.
         */
        public void reset() {
            called = false;
            lastEvent = null;
        }

        /**
         * Gibt das zuletzt an den Listener übergebene Event zurück.
         *
         * @return Das Letzte erhaltene DocumentEvent.
         */
        public DocumentEvent getLastEvent() {
            return lastEvent;
        }
    }
}