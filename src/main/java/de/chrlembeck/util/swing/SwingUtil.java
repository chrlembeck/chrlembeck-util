package de.chrlembeck.util.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JScrollPane;

/**
 * Hilfsmethoden für die Arbeit mit Swing-Komponenten.
 *
 * @author Christoph Lembeck
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class SwingUtil {

    /**
     * Positioniert das übergebene Anwendungsfenster auf der Mitte des Bildschirms.
     * 
     * @param window
     *            Fenster, welches ausgerichtet werden soll.
     */
    public static void centerToScreen(final Window window) {
        final Dimension screenSize = window.getToolkit().getScreenSize();
        // Oberen, linken Startpunkt ermitteln
        int xPos = (screenSize.width - window.getWidth()) / 2;
        int yPos = (screenSize.height - window.getHeight()) / 2;
        // Prüfen, ob Fenster oben oder links aus dem Bildschirm ragt
        xPos = Math.max(xPos, 0);
        yPos = Math.max(yPos, 0);
        window.setLocation(xPos, yPos);
    }

    /**
     * Positioniert das übergebene Anwendungsfenster mittig über dem angegebenen parent-Fenster.
     * 
     * @param window
     *            Fenster, welches über seinem parent ausgerichtet werden soll.
     * @param parent
     *            Fenster, über dem das andere Fenster ausgerichtet wird.
     */
    public static void centerToParent(final Window window, final Window parent) {
        final Dimension screenSize = window.getToolkit().getScreenSize();
        final Rectangle bounds = parent.getBounds();
        // Oberen, linken Startpunkt ermitteln
        int xPos = bounds.x + (bounds.width - window.getWidth()) / 2;
        int yPos = bounds.y + (bounds.height - window.getHeight()) / 2;
        // Prüfen, ob Fenster unten oder rechts aus dem Bildschirm ragt
        xPos = Math.min(xPos, screenSize.width - window.getWidth());
        yPos = Math.min(yPos, screenSize.height - window.getHeight());
        // Prüfen, ob Fenster oben oder links aus dem Bildschirm ragt
        xPos = Math.max(xPos, 0);
        yPos = Math.max(yPos, 0);
        window.setLocation(xPos, yPos);
    }

    /**
     * Sucht nach einer ScrollPane, in die die übergebene Komponente direkt oder indirekt eingebettet ist.
     * 
     * @param comp
     *            Komponente, zu der die umgebende ScrollPane gesucht werden soll.
     * @return ScrollPane, in die die Komponente direkt oder indirekt eingebettet ist oder null, falls die Komponenten
     *         nicht in einer ScrollPane enthalten ist.
     */
    public static JScrollPane findSurroundingScrollPane(final Component comp) {
        Container container = comp.getParent();
        while (container != null) {
            if (container instanceof JScrollPane) {
                return (JScrollPane) container;
            }
            container = container.getParent();
        }
        return null;
    }
}