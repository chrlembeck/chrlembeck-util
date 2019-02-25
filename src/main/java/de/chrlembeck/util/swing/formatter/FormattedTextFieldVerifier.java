package de.chrlembeck.util.swing.formatter;

import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * InputVerifier für FormattedTextFields, der auf Grundlage des Formatters des Textfields die Eingaben in den
 * Textfeldern auf konsistenz überprüft. Kann der Formatter die Eingaben fehlerfrei konvertieren, wird die Eingabe als
 * valide anerkannt, ansonsten abgewiesen.
 *
 * @author Christoph Lembeck
 */
public class FormattedTextFieldVerifier extends InputVerifier {

    /**
     * Modifier, der die Komponente je nach überprüfungsstatus visuell anpassen kann. (Optional)
     */
    private final InputVerifierComponentModifier modifier;

    /**
     * Erstellt eine neue Instanz mit dem übergebenen Modifier zur Anpassung der überprüften Textfelder.
     * 
     * @param modifier
     *            Modifier zur Anpassung der überprüften Textfelder.
     */
    public FormattedTextFieldVerifier(final InputVerifierComponentModifier modifier) {
        super();
        this.modifier = modifier;
    }

    /**
     * Prüft die Eingabe mit Hilfe des Formatters des FormattedTextFields. Anschließend wir die Text-Komponente ggf. mit
     * Hilfe des optionalen Modifiers angepasst.
     */
    @Override
    public boolean verify(final JComponent input) {
        if (input instanceof JFormattedTextField) {
            final JFormattedTextField ftf = (JFormattedTextField) input;
            final AbstractFormatter formatter = ftf.getFormatter();
            if (formatter != null) {
                final String text = ftf.getText();
                try {
                    formatter.stringToValue(text);
                    modifyComponent(input, true);
                    return true;
                } catch (final ParseException pe) {
                    modifyComponent(input, false);
                    return false;
                }
            }
        }
        modifyComponent(input, true);
        return true;
    }

    /**
     * Passt das Textfeld mit Hilfe des gepeicherten Modifizierers an.
     * 
     * @param component
     *            Textfeld, auf dem die Prüfung stattgefunden hat.
     * @param isValid
     *            true, falls die Eingabe erlaubt ist, false bei einer falschen Eingabe.
     */
    private void modifyComponent(final JComponent component,
            final boolean isValid) {
        if (modifier != null) {
            modifier.modifyComponent(component, isValid);
        }
    }
}