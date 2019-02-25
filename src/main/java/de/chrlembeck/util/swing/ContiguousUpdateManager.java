package de.chrlembeck.util.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 * UpdateManager für Textkomponenten, der im Gegensatz zur Standardimplementierung mehrere zusammenhängende Änderungen
 * zu einer Undo-Aktion zusammenfassen kann. Zusammenhängende Änderungen sind dabei kontinuierliche Texteingaben bis zum
 * Abschluss einer Zeile. Einfügeoperationen von mehreren Zeichen, Zeilenumbrüche und Eingaben an nicht
 * zusammenhängenden Positionen führen jeweils zur Erstellung neuer Undo-Aktionen.
 * 
 * @author Christoph Lembeck
 */
public class ContiguousUpdateManager extends UndoManager {

    /**
     * Version number of the current class.
     * 
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 4833041236176074626L;

    /**
     * Textkomponente, für die das Undo-Management übernommen werden soll.
     */
    private final JTextComponent textComponent;

    /**
     * Aktuelles Edit-Objekt, zu dem gegebenenfalls noch weitere Änderungen hinzugefügt werden sollen.
     */
    protected ContiguousEdit currentEdit;

    /**
     * Position des Cursors nach der letzten Änderung.
     */
    private int lastCaretPosition;

    /**
     * Länge des Textdokuments nach der letzten Änderung.
     */
    private int lastLength;

    /**
     * Erstellt einen neuen UpdateManager für die übergebene Textkomponenten und fügt den Manager direkt als Listener
     * für Änderungen hinzu.
     * 
     * @param textComponent
     *            Text-Komponente, für die das Undo-Management übernommen werden soll.
     */
    public ContiguousUpdateManager(final JTextComponent textComponent) {
        this.textComponent = textComponent;
        textComponent.getDocument().addUndoableEditListener(this);
    }

    /**
     * Prüft, ob das EditEvent zu einer bereits angefangenen Änderung hinzugefügt werden kann, oder ob es sich um eine
     * neue Änderung handelt. Bei zusammenhängengen Änderungen (also hintereinander eingegebenen Zeichen) werden die
     * Änderungen zu einer Undo-Aktion zusammengefasst. Zeilenwechsel, Einfügeoperationen oder Eingaben an nicht
     * zusammenhängenden Positionen erzeugen jeweils eine neue Undo-Aktion.
     * 
     * @param editEvent
     *            Event von der Text-Komponente.
     */
    @Override
    public void undoableEditHappened(final UndoableEditEvent editEvent) {
        final UndoableEdit edit = editEvent.getEdit();

        // Gibt es kein offenes Undo, erstellen wir ein neues
        if (currentEdit == null) {
            currentEdit = createNewEdit(edit);
            return;
        }

        final int caretPosition = textComponent.getCaretPosition();
        final int offsetChange = caretPosition - lastCaretPosition;
        final Document document = textComponent.getDocument();
        final int deltaLength = document.getLength() - lastLength;
        if (caretPosition == lastCaretPosition && edit instanceof DefaultDocumentEvent) {
            // Prüfen, ob es sich um eine Attribut-Änderung handelt
            final DefaultDocumentEvent defaultDocEvent = (DefaultDocumentEvent) edit;
            if (DocumentEvent.EventType.CHANGE.equals(defaultDocEvent.getType())) {
                currentEdit.addEdit(edit);
                return;
            }
        } else if (deltaLength == 1 && offsetChange == 1 && "\n".equals(getCharAt(document, lastCaretPosition))) {
            // Bei Eingabe eines Zeilenwechsels fängt ein neuer Edit an.
            currentEdit.end();
            currentEdit = createNewEdit(edit);
            return;
        } else if (offsetChange == deltaLength && Math.abs(offsetChange) == 1) {
            // Zusammenhängende Änderungen der Länge 1 werden zusammengefasst.
            currentEdit.addEdit(edit);
            lastCaretPosition = caretPosition;
            lastLength = document.getLength();
            return;
        } else {
            // Bei allen anderen Änderungen fangen wir ein neues Edit-Objekt an.
            currentEdit.end();
            currentEdit = createNewEdit(edit);
        }
    }

    /**
     * Erzeugt ein neues Undo-Objekt für eine neue Änderung.
     * 
     * @param edit
     *            Edit, wie er von der Textkomponente übergeben wurde.
     * @return ContiguousEdit-Objekt für die Zusammenfassung ggf. weiterer, zusammenhängender Änderungen.
     */
    private ContiguousEdit createNewEdit(final UndoableEdit edit) {
        lastCaretPosition = textComponent.getCaretPosition();
        lastLength = textComponent.getDocument().getLength();
        currentEdit = new ContiguousEdit(edit);
        addEdit(currentEdit);
        return currentEdit;
    }

    /**
     * Liest das Zeichen an Position {@code pos} aus dem Dokument aus.
     * 
     * @param document
     *            Dokument, aus dem gelesen werden soll.
     * @param pos
     *            Position des Zeichens, welches gelesen werden soll
     * @return Zeichen an der gewünschten Stelle oder null, falls die Position ungültig war.
     */
    private static String getCharAt(final Document document, final int pos) {
        try {
            return document.getText(pos, 1);
        } catch (final BadLocationException e) {
            return null;
        }
    }

    /**
     * Edit-Objekt für die Zusammenfassung mehrerer zusammenhängender Änderungen.
     * 
     * @author Christoph Lembeck
     */
    private class ContiguousEdit extends CompoundEdit {

        /**
         * Version number of the current class.
         * 
         * @see java.io.Serializable
         */
        private static final long serialVersionUID = 1283278942832943727L;

        /**
         * Erstellt ein neues Objekt und fügt die übergebene Änderung als initiale Änderung hinzu.
         * 
         * @param edit
         *            Initiale Änderung für dieses Objekt.
         */
        public ContiguousEdit(final UndoableEdit edit) {
            addEdit(edit);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void undo() throws CannotUndoException {
            // bei einem Undo Beenden wir das aktuelle Edit.
            if (currentEdit != null) {
                currentEdit.end();
                currentEdit = null;
            }
            super.undo();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isInProgress() {
            return false;
        }
    }
}