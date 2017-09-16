package de.chrlembeck.util.swing.formatter;

import java.awt.Color;

import javax.swing.JComponent;

/**
 * Hilfsklasse zur visuellen Darstellung von Eingabefehlern in Text-Komponenten durch Änderung der Hintergrundfarbe je
 * nach gültigen oder ungültigen Eingaben.
 *
 * @author Christoph Lembeck
 */
public class BackgroundModifier implements InputVerifierComponentModifier {

    /**
     * Name der Client-Property, in der der alte Hintergrund für die Komponente zwischengespeichert wird.
     */
    private static final String CLIENT_PROPERTY_KEY_OLD_BACKGROUND = "invalidInputOldBackground";

    /**
     * Farbe für den Hintergrund bei ungültigen Eingaben.
     */
    private Color invalidBackgroundColor;

    /**
     * Erstellt eine neue Instanz mit der übergebenen Farbe als Hintergrund für die Hervorhebung ungültiger Eingaben.
     * 
     * @param invalidBackgroundColor
     *            Farbe für die Darstellung einer ungültigen Eingabe.
     */
    public BackgroundModifier(final Color invalidBackgroundColor) {
        this.invalidBackgroundColor = invalidBackgroundColor;
    }

    /**
     * Erstellt eine neue Instanz mit der Farbe Orange als Hintergrund für die Hervorhebung ungültiger Eingaben.
     */
    public BackgroundModifier() {
        this(Color.ORANGE);
    }

    /**
     * Ändert die für die Darstellung ungültiger Eingaben verwendete Hintergrundfarbe.
     * 
     * @param invalidBackgroundColor
     *            Neue Farbe zur Hervorhebung ungültiger Eingaben.
     */
    public void setInvalidBackgroundColor(final Color invalidBackgroundColor) {
        this.invalidBackgroundColor = invalidBackgroundColor;
    }

    /**
     * Gibt die zur Darstellung ungültiger Eingaben verwendete Hintergrundfarbe zurück.
     * 
     * @return Farbe zur Hervorhebung ungültiger Eingaben.
     */
    public Color getInvalidBackgroundColor() {
        return invalidBackgroundColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyComponent(final JComponent component, final boolean isValid) {
        if (isValid) {
            final Color background = (Color) component
                    .getClientProperty(CLIENT_PROPERTY_KEY_OLD_BACKGROUND);
            if (background != null) {
                component.setBackground(background);
                component.putClientProperty(CLIENT_PROPERTY_KEY_OLD_BACKGROUND, null);
            }
        } else {
            final Color background = component.getBackground();
            if (component.getClientProperty(CLIENT_PROPERTY_KEY_OLD_BACKGROUND) == null) {
                component.putClientProperty(CLIENT_PROPERTY_KEY_OLD_BACKGROUND,
                        background);
            }
            component.setBackground(invalidBackgroundColor);
        }
    }
}