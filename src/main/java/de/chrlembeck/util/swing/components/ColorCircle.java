package de.chrlembeck.util.swing.components;

import de.chrlembeck.util.swing.components.plaf.AbstractColorCircleUI;
import de.chrlembeck.util.swing.components.plaf.DefaultColorCircleUI;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.UIManager;

/**
 * Farbkreis für die Anzeige und Auswahl einer Farbe anhand des HSV-Farbschemas.
 *
 * @author Christoph Lembeck
 */
public class ColorCircle extends JComponent {

    /**
     * Version number of the current class.
     * 
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 6947081686991902570L;

    /**
     * ID für die UI, die in dieser Komponente verwendet werden soll.
     */
    @SuppressWarnings("PMD.FieldNamingConventions")
    private static final String uiClassID = "ColorCircleUI";

    /**
     * ID für den Farbwert der Farbe aus dem HSV-Farbraum.
     */
    public static final String PROPERTY_HUE = "hue";

    /**
     * ID für die Farbsättigung der Farbe aus dem HSV-Farbraum.
     */
    public static final String PROPERTY_SATURATION = "saturation";

    /**
     * ID für den Helligkeitswert der Farbe aus dem HSV-Farbraum.
     */
    public static final String PROPERTY_BLACKNESS_VALUE = "blacknessValue";

    /**
     * Aktueller Farbwert der Komponente.
     */
    private int hue;

    /**
     * Aktuelle Farbsättigung der Komponente.
     */
    private int saturation;

    /**
     * Aktueller Helligkeitswert der Komponente.
     */
    private int blacknessValue;

    static {
        UIManager.put(uiClassID, DefaultColorCircleUI.class.getName());
    }

    /**
     * Erstellt eine neue Farbdarstellungskomponente.
     */
    public ColorCircle() {
        super();
        updateUI();
    }

    /**
     * Gibt das zur Darstellung verwendet UI-Objekt zurück.
     * 
     * @return UI-Objekt zur Darstellung der Komponente.
     */
    public AbstractColorCircleUI getUI() {
        return (AbstractColorCircleUI) ui;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateUI() {
        setUI(UIManager.getUI(this));
    }

    /**
     * Rechnet eine RGB-Farbe in eine HSV-Farbe um.
     * 
     * @param red
     *            Rotwert der Farbe.
     * @param green
     *            Grünwert der Farbe.
     * @param blue
     *            Blauwert der Farbe.
     * @return Farbe in Form von HSV-Koordinaten.
     */
    public static Hsv getHsv(final int red, final int green, final int blue) {
        final int hue;
        final int saturation;
        final int max = Math.max(red, Math.max(green, blue));
        final int min = Math.min(red, Math.min(green, blue));
        if (red == green && green == blue) {
            hue = 0;
            saturation = 0;
        } else {
            saturation = 255 * (max - min) / max;
            if (red == max) {
                hue = (360 + (60 * (green - blue)) / (max - min)) % 360;
            } else if (green == max) {
                hue = 120 + (60 * (blue - red)) / (max - min);
            } else {
                hue = 240 + (60 * (red - green)) / (max - min);
            }
        }
        return new Hsv(hue, saturation, max);
    }

    /**
     * Berechnet eine RGB-Farbe anhand der HSV-Farbkoordinaten.
     * 
     * @param hsv
     *            HSV-Darstellung der Farbe.
     * @return RGB-Darstellung der Farbe.
     */
    public Color getColor(final Hsv hsv) {
        return getColorByHsv(hsv.getHue(), hsv.getSaturation(), hsv.getBlacknessValue());
    }

    /**
     * Repräsentiert einen Farbwert des HSV-Farbraums. Hierin sind Farben durch Farbwert, Farbsättigung und Helligkeit
     * definiert.
     * 
     * @author Christoph Lembeck
     */
    @SuppressWarnings("PMD.ShortClassName")
    public static class Hsv {

        /**
         * Farbwert oder Farbwinkel auf dem Farbkreis.
         */
        private final int hue;

        /**
         * Farbsättigung. 0 entspricht Neutralgrau, 255 entspricht der vollen Farbsättigung.
         */
        private final int saturation;

        /**
         * Hellwert. 0 entspricht Schwarz, 255 der vollen Farbhelligkeit.
         */
        private final int value;

        /**
         * Erstellt eine neue HSV-Farbe anhand der übergebenen Werte.
         * 
         * @param hue
         *            Farbwert oder Farbwinkel auf dem Farbkreis.
         * @param saturation
         *            Farbsättigung. 0 entspricht Neutralgrau, 255 entspricht der vollen Farbsättigung.
         * @param value
         *            Hellwert. 0 entspricht Schwarz, 255 der vollen Farbhelligkeit.
         */
        public Hsv(final int hue, final int saturation, final int value) {
            this.hue = hue;
            this.saturation = saturation;
            this.value = value;
        }

        /**
         * Gibt die Farbsättigung des HSV-Farbmodells für die aktuell ausgewählte Farbe zurück.
         * 
         * @return HSV-Farbsättigung der Farbe.
         */
        public int getSaturation() {
            return saturation;
        }

        /**
         * Gibt den Hellwert des HSV-Farbmodells für die aktuell ausgewählte Farbe zurück.
         * 
         * @return HSV-Hellwert der Farbe.
         */
        public int getBlacknessValue() {
            return value;
        }

        /**
         * Gibt den Farbwert (Winkel) des HSV-Farbmodells für die aktuell ausgewählte Farbe zurück.
         * 
         * @return HSV-Farbwert der Farbe.
         */
        public int getHue() {
            return hue;
        }

        @Override
        public String toString() {
            return "HSV(" + hue + ", " + saturation + ", " + value + ")";
        }
    }

    /**
     * Berechnet eine RGB-Farbe anhand der HSV-Farbkoordinaten.
     * 
     * @param hue
     *            Farbwert oder Farbwinkel auf dem Farbkreis.
     * @param saturation
     *            Farbsättigung. 0 entspricht Neutralgrau, 255 entspricht der vollen Farbsättigung.
     * @param value
     *            Hellwert. 0 entspricht Schwarz, 255 der vollen Farbhelligkeit.
     * @return RGB-Darstellung der Farbe.
     */
    public static Color getColorByHsv(int hue, final int saturation, final int value) {
        hue = hue % 360;
        final int innerColorIndex = hue % 60;
        final int firstValue = (value * (255 - saturation)) / 255;
        @SuppressWarnings("PMD.PrematureDeclaration")
        final int secondValueAsc = (value * (255 - saturation * innerColorIndex / 60)) / 255;
        final int secondValueDesc = (value * (255 - saturation * (60 - innerColorIndex) / 60)) / 255;
        if (hue < 60) {
            return new Color(value, secondValueDesc, firstValue);
        }
        if (hue < 120) {
            return new Color(secondValueAsc, value, firstValue);
        }
        if (hue < 180) {
            return new Color(firstValue, value, secondValueDesc);
        }
        if (hue < 240) {
            return new Color(firstValue, secondValueAsc, value);
        }
        if (hue < 300) {
            return new Color(secondValueDesc, firstValue, value);
        }
        return new Color(value, firstValue, secondValueAsc);
    }

    /**
     * Gibt die aktuell gewählte Farbe in Form des RGB-Farbraums zurück.
     * 
     * @return Farbe in den RGB-Farbkoordinaten.
     */
    public Color getColorByHsv() {
        return getColorByHsv(hue, saturation, blacknessValue);
    }

    /**
     * Gibt den aktuell gewählten Farbwert aus dem HSV-Farbraum zurück.
     * 
     * @return Farbwert der aktuellen Farbe.
     */
    public int getHue() {
        return hue;
    }

    /**
     * Legt den aktuell gewählten Farbwert aus dem HSV-Farbraum fest.
     * 
     * @param newHue
     *            Farbwert der aktuellen Farbe.
     */
    public void setHue(final int newHue) {
        final int oldHue = hue;
        this.hue = newHue;
        firePropertyChange(PROPERTY_HUE, oldHue, newHue);
        repaint();
    }

    /**
     * Gibt die aktuell gewählte Farbsättigung aus dem HSV-Farbraum zurück.
     * 
     * @return Farbsättigung der aktuellen Farbe.
     */
    public int getSaturation() {
        return saturation;
    }

    /**
     * Gibt den aktuell gewählten Helligkeitswert aus dem HSV-Farbraum zurück.
     * 
     * @return Helligkeitswert der aktuellen Farbe.
     */
    public int getBlacknessValue() {
        return blacknessValue;
    }

    /**
     * Legt die aktuell gewählte Farbsättigung aus dem HSV-Farbraum fest.
     * 
     * @param newSaturation
     *            Farbsättigung der aktuellen Farbe.
     */
    public void setSaturation(final int newSaturation) {
        final int oldSaturation = saturation;
        this.saturation = newSaturation;
        firePropertyChange(PROPERTY_SATURATION, oldSaturation, newSaturation);
        repaint();
    }

    /**
     * Legt den aktuell gewählten Helligkeitswert aus dem HSV-Farbraum fest.
     * 
     * @param newValue
     *            Helligkeitswert der aktuellen Farbe.
     */
    public void setBlacknessValue(final int newValue) {
        final int oldValue = blacknessValue;
        this.blacknessValue = newValue;
        firePropertyChange(PROPERTY_BLACKNESS_VALUE, oldValue, newValue);
        repaint();
    }
}