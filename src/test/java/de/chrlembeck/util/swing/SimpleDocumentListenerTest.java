package de.chrlembeck.util.swing;

import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class SimpleDocumentListenerTest {

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

    static class TestConsumer implements Consumer<DocumentEvent> {

        private boolean called;

        private DocumentEvent lastEvent;

        @Override
        public void accept(final DocumentEvent event) {
            called = true;
            this.lastEvent = event;
        }

        public boolean wasCalled() {
            return called;
        }

        public void reset() {
            called = false;
            lastEvent = null;
        }

        public DocumentEvent getLastEvent() {
            return lastEvent;
        }
    }
}