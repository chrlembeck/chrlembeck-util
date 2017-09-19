package de.chrlembeck.util.swing.icon;

import java.awt.Color;
import java.awt.Paint;

import javax.swing.Icon;

import de.chrlembeck.util.swing.icon.NavigationIcon.Direction;
import de.chrlembeck.util.swing.icon.ZoomIcon.Sign;

/**
 * Factoryklasse zur Erzeugung einiger skalierbarer Icons.
 *
 * @author Christoph Lembeck
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class PaintedIcons {

    /**
     * Erzeugt einen farbigen Ball beliebiger Größe.
     * 
     * @param size
     *            Höhe und Breite des Icons.
     * @param color
     *            Farbe des Balls.
     * @return Das erzeugt Ball-Icon.
     */
    public static BallIcon createBall(final int size, final Color color) {
        return new BallIcon(size, color, 0, null, null, null, null, true);
    }

    /**
     * Erstellt ein Icon für die Vergrößerung eines Zoomfaktors.
     * 
     * @param size
     *            Höhe und Breite des Icons in Pixeln.
     * @param borderPaint
     *            Zeichenstil für den Icon-Rahmen.
     * @param borderThickness
     *            Dicke der gezeichneten Linien.
     * @param fillPaint
     *            Füllstil für den Inhalt der Zoom-Icons.
     * @param signPaint
     *            Zeichstil für das in dem Icon verwendete Symbol.
     * @param signThickness
     *            Linienstärke für das im Icon dargestellte Symbol.
     * @return Das Erzeugte Icon.
     */
    public static Icon createZoomPlus(final int size, final Paint borderPaint, final float borderThickness,
            final Paint fillPaint, final Paint signPaint, final float signThickness) {
        return new ZoomIcon(size, Sign.PLUS, borderPaint, borderThickness, fillPaint, signPaint, signThickness);
    }

    /**
     * Erstellt ein Icon für die Verkleinerung eines Zoomfaktors.
     * 
     * @param size
     *            Höhe und Breite des Icons in Pixeln.
     * @param borderPaint
     *            Zeichenstil für den Icon-Rahmen.
     * @param borderThickness
     *            Dicke der gezeichneten Linien.
     * @param fillPaint
     *            Füllstil für den Inhalt der Zoom-Icons.
     * @param signPaint
     *            Zeichstil für das in dem Icon verwendete Symbol.
     * @param signThickness
     *            Linienstärke für das im Icon dargestellte Symbol.
     * @return Das Erzeugte Icon.
     */
    public static Icon createZoomMinus(final int size, final Paint borderPaint, final float borderThickness,
            final Paint fillPaint, final Paint signPaint, final float signThickness) {
        return new ZoomIcon(size, Sign.MINUS, borderPaint, borderThickness, fillPaint, signPaint, signThickness);
    }

    /**
     * Erstellt ein Icon für eine unbestimmte Veränderung des Zoomfaktors. Hier wird kein spezielles Symbol in das Icon
     * gemalt.
     * 
     * @param size
     *            Höhe und Breite des Icons in Pixeln.
     * @param borderPaint
     *            Zeichenstil für den Icon-Rahmen.
     * @param borderThickness
     *            Dicke der gezeichneten Linien.
     * @param fillPaint
     *            Füllstil für den Inhalt der Zoom-Icons.
     * @return Das Erzeugte Icon.
     */
    public static Icon createZoom(final int size, final Paint borderPaint, final float borderThickness,
            final Paint fillPaint) {
        return new ZoomIcon(size, Sign.NONE, borderPaint, borderThickness, fillPaint, null, 0);
    }

    /**
     * Erstellt ein Icon für die Anpassung des Zoomfaktors, so dass die gesamte Zeichenfläche dargestellt werden kann.
     * 
     * @param size
     *            Höhe und Breite des Icons in Pixeln.
     * @param borderPaint
     *            Zeichenstil für den Icon-Rahmen.
     * @param borderThickness
     *            Dicke der gezeichneten Linien.
     * @param fillPaint
     *            Füllstil für den Inhalt der Zoom-Icons.
     * @param signPaint
     *            Zeichstil für das in dem Icon verwendete Symbol.
     * @param signThickness
     *            Linienstärke für das im Icon dargestellte Symbol.
     * @return Das Erzeugte Icon.
     */
    public static Icon createZoomFit(final int size, final Paint borderPaint, final float borderThickness,
            final Paint fillPaint, final Paint signPaint, final float signThickness) {
        return new ZoomIcon(size, Sign.FIT, borderPaint, borderThickness, fillPaint, signPaint, signThickness);
    }

    /**
     * Erstellt ein Icon für das Setzen des Zoomfaktors auf den Normalwert.
     * 
     * @param size
     *            Höhe und Breite des Icons in Pixeln.
     * @param borderPaint
     *            Zeichenstil für den Icon-Rahmen.
     * @param borderThickness
     *            Dicke der gezeichneten Linien.
     * @param fillPaint
     *            Füllstil für den Inhalt der Zoom-Icons.
     * @param signPaint
     *            Zeichstil für das in dem Icon verwendete Symbol.
     * @param signThickness
     *            Linienstärke für das im Icon dargestellte Symbol.
     * @return Das Erzeugte Icon.
     */
    public static Icon createZoomOriginal(final int size, final Paint borderPaint, final float borderThickness,
            final Paint fillPaint, final Paint signPaint, final float signThickness) {
        return new ZoomIcon(size, Sign.ORIGINAL, borderPaint, borderThickness, fillPaint, signPaint, signThickness);
    }

    public static Icon createNavigateFirst(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.FIRST, paint, borderThickness, fillPaint);
    }

    public static Icon createNavigateLast(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.LAST, paint, borderThickness, fillPaint);
    }

    public static Icon createNavigateNext(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.NEXT, paint, borderThickness, fillPaint);
    }

    public static Icon createNavigatePrevious(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.PREVIOUS, paint, borderThickness, fillPaint);
    }

    public static Icon createNavigateFirstDouble(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.FIRST_DOUBLE, paint, borderThickness, fillPaint);
    }

    public static Icon createNavigateLastDouble(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.LAST_DOUBLE, paint, borderThickness, fillPaint);
    }

    public static Icon createNavigateNextDouble(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.NEXT_DOUBLE, paint, borderThickness, fillPaint);
    }

    public static Icon createNavigatePreviousDouble(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.PREVIOUS_DOUBLE, paint, borderThickness, fillPaint);
    }
}