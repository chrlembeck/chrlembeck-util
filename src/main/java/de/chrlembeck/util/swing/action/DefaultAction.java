package de.chrlembeck.util.swing.action;

import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

/**
 * Implementierung einer AbstractAction, der ein Consumer übergeben wird, der bei der Auslösung der Action aufgerufen
 * wird.
 * 
 * @author Christoph Lembeck
 */
public class DefaultAction extends AbstractAction {

    /**
     * Version number of the current class.
     * 
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 1438838518979077023L;

    /**
     * Consumer für die auftretenden ActionEvents. Wird aufgerufen, wenn die Action ausgelöst wird.
     */
    private Consumer<ActionEvent> actionConsumer;

    /**
     * Erstellt eine neue Action mit den übergebenen Werten.
     * 
     * @param actionConsumer
     *            Consumer für die auftretenden ActionEvents. Wird aufgerufen, wenn die Action ausgelöst wird.
     */
    public DefaultAction(final Consumer<ActionEvent> actionConsumer) {
        this(null, null, null, null, null, null, null, null, actionConsumer);
    }

    /**
     * Erstellt eine neue Action mit den übergebenen Werten.
     * 
     * @param name
     *            Name der Action.
     * @param shortDescription
     *            Kurzbeschreibung der Action für die Anzeige von Tooltips.
     * @param longDescription
     *            Beschreibung der Action für kontextsensitive Hilfe.
     * @param mnemonicKey
     *            KeyEvent-Key für die Anzeige eines Buchstabens zum schnelleren Aufruf der Action.
     * @param displayedMnemonicIndex
     *            Index des Zeichens, welches unterstrichen dargestellt werden soll.
     * @param acceleratorKey
     *            KeyStroke, der die Action auslösen soll.
     * @param smallIcon
     *            Icon zur Darstellung in Menüs für die Action.
     * @param largeIcon
     *            Icon zur Darstellung auf Buttons. Wird kein Icon übergeben, wird auf das smallIcon zurückgegriffen.
     * @param actionConsumer
     *            Consumer für die auftretenden ActionEvents. Wird aufgerufen, wenn die Action ausgelöst wird.
     * @see Action#NAME
     * @see Action#SHORT_DESCRIPTION
     * @see Action#LONG_DESCRIPTION
     * @see Action#SMALL_ICON
     * @see Action#LARGE_ICON_KEY
     * @see Action#MNEMONIC_KEY
     * @see Action#DISPLAYED_MNEMONIC_INDEX_KEY
     * @see Action#ACCELERATOR_KEY
     */
    public DefaultAction(final String name, final String shortDescription, final String longDescription,
            final Integer mnemonicKey,
            final Integer displayedMnemonicIndex, final KeyStroke acceleratorKey, final Icon smallIcon,
            final Icon largeIcon,
            final Consumer<ActionEvent> actionConsumer) {
        super();
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, shortDescription);
        putValue(LONG_DESCRIPTION, longDescription);
        putValue(SMALL_ICON, smallIcon);
        putValue(LARGE_ICON_KEY, largeIcon);
        putValue(MNEMONIC_KEY, mnemonicKey);
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, displayedMnemonicIndex);
        putValue(ACCELERATOR_KEY, acceleratorKey);
        this.actionConsumer = Objects.requireNonNull(actionConsumer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        actionConsumer.accept(actionEvent);
    }
}