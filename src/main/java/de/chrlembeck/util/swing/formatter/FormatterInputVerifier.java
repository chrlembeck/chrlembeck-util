package de.chrlembeck.util.swing.formatter;

import java.text.ParseException;
import java.util.Objects;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.JTextComponent;

/**
 * InputVerifier für Swing-Text-Konponenten, der auf Grundlage eines übergebenen Formatters die Eingaben in den
 * Textfeldern auf konsistenz überprüft. Kann der Formatter die Eingaben fehlerfrei konvertieren, wird die Eingabe als
 * valide anerkannt, ansonsten abgewiesen.
 * 
 * @author Christoph Lembeck
 */
public class FormatterInputVerifier extends InputVerifier {

    /**
     * Der zur Verifizierung intern verwendete Formatter
     */
    private final AbstractFormatter formatter;

    /**
     * Modifier, der die Komponente je nach überprüfungsstatus visuell anpassen kann. (Optional)
     */
    private final InputVerifierComponentModifier modifier;

    /**
     * Erzeugt einen neuen Verifier mit dem übergebenen formatter
     * 
     * @param formatter
     *            Formatter für die Konvertierung der Eingaben.
     * @param modifier
     *            Optional angabe eines Modifiers, der das Textfeld je nach Verifizierungsstatus anpassen kann.
     */
    public FormatterInputVerifier(final AbstractFormatter formatter, final InputVerifierComponentModifier modifier) {
        this.formatter = Objects.requireNonNull(formatter);
        this.modifier = modifier;
    }

    /**
     * Prüft die Eingabe mit Hilfe des Formatters. Anschließend wir die Text-Komponente ggf. mit Hilfe des optionalen
     * Modifiers angepasst.
     */
    @Override
    public boolean verify(final JComponent comp) {
        final JTextComponent textComp = (JTextComponent) comp;
        final String text = textComp.getText();
        if (text.length() == 0) {
            textComp.setText("");
            modifyComponent(comp, true);
            return true;
        }
        try {
            final Object value = formatter.stringToValue(text);
            textComp.setText(formatter.valueToString(value));
            modifyComponent(comp, true);
            return true;
        } catch (final ParseException pe) {
            modifyComponent(comp, false);
            return false;
        }
    }

    /**
     * Passt das Textfeld mit Hilfe des gepeicherten Modifizierers an.
     * 
     * @param component
     *            Textfeld, auf dem die Prüfung stattgefunden hat.
     * @param isValid
     *            true, falls die Eingabe erlaubt ist, false bei einer falschen Eingabe.
     */
    private void modifyComponent(final JComponent component, final boolean isValid) {
        if (modifier != null) {
            modifier.modifyComponent(component, isValid);
        }
    }
}
