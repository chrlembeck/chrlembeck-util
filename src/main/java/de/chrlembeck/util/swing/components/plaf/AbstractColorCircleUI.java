package de.chrlembeck.util.swing.components.plaf;

import javax.swing.plaf.ComponentUI;

import de.chrlembeck.util.swing.components.ColorCircle;
import de.chrlembeck.util.swing.components.ColorCircle.HSV;

/**
 * Oberklasse für die UIComponents zur Darstellung und Steuerung der Farbauswahlkomponente ColorCircle.
 * 
 * @author Christoph Lembeck
 * @see ColorCircle
 */
public abstract class AbstractColorCircleUI extends ComponentUI {

    /**
     * Prüft, ob der Punkt mit den übergebenen Koordinaten auf der Fläche des Rings für die Farbauswahl liegt.
     * 
     * @param circle
     *            ColorCircle-Komponente, auf der die Position geprüft werden soll.
     * @param xPos
     *            X-Koordinate des zu prüfenden Punktes.
     * @param yPos
     *            Y-Koordinste des zu prüfenden Punktes.
     * @return true, falls der Punkt auf dem Farbring liegt, false, falls er im Innern oder außerhalb des Rings liegt.
     */
    abstract boolean isInColorBelt(ColorCircle circle, int xPos, int yPos);

    /**
     * Prüft, ob der Punkt mit den übergebenen Koordinaten auf der Fläche des inneren Dreiecks für die Farbauswahl
     * liegt.
     * 
     * @param circle
     *            ColorCircle-Komponente, auf der die Position geprüft werden soll.
     * @param xPos
     *            X-Koordinate des zu prüfenden Punktes.
     * @param yPos
     *            Y-Koordinste des zu prüfenden Punktes.
     * @return true, falls der Punkt innerhalb des Dreiecks für die Farbauswahl liegt, sonst false.
     */
    abstract boolean isInColorTriangle(ColorCircle circle, int xPos, int yPos);

    /**
     * Ermittelt die Farbe eines Punktes innerhalb des Farbauswahldreiecks und gibt sie als Farbe im HSV-Farbraum
     * zurück.
     * 
     * @param circle
     *            ColorCircle-Komponente, auf der die Farbe ermittelt werden soll.
     * @param xPos
     *            X-Koordinate des zu prüfenden Punktes.
     * @param yPos
     *            Y-Koordinste des zu prüfenden Punktes.
     * @return Farbwert an der angegebenen Stelle in Form einer HSV-Angabe.
     */
    abstract HSV getHSVFromcolorTriangle(ColorCircle circle, int xPos, int yPos);

    /**
     * Ermittelt den Farbwert im Sinne des HSV-Farbraums anhand der Koordinaten auf einem Farbkreis.
     * 
     * @param circle
     *            Farbkreis, aus dem der Farbwert ermittelt werden soll.
     * @param xPos
     *            X-Koordinate des zu prüfenden Punktes.
     * @param yPos
     *            Y-Koordinste des zu prüfenden Punktes.
     * @return Farbwert des Punktes auf dem Farbkreis.
     */
    abstract int getHueFromColorBelt(ColorCircle circle, int xPos, int yPos);
}