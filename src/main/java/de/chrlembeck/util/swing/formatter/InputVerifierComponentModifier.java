package de.chrlembeck.util.swing.formatter;

import javax.swing.JComponent;

/**
 * Interface für Hilfsklassen zur optischen veränderung von Swing-Komponenten, bei denen eine Eingabevalidierung
 * stattgefunden hat. Die Komponenten können hierüber im Falle einer fehlerhaften Eingabe visuell hervorgehoben werden.
 *
 * @author Christoph Lembeck
 */
public interface InputVerifierComponentModifier {

    /**
     * Führt die Veränderungen an der Komponente je nach Prüfergebnis durch.
     * 
     * @param component
     *            Anzupassende Komponente
     * @param isValid
     *            true, falls die Eingabe erlaubt ist, false bei einer ungültigen Eingabe.
     */
    void modifyComponent(JComponent component, boolean isValid);
}
