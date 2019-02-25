package de.chrlembeck.util.swing.icon;

import de.chrlembeck.util.swing.icon.NavigationIcon.Direction;
import de.chrlembeck.util.swing.icon.ZoomIcon.Sign;
import java.awt.Color;
import java.awt.Paint;
import javax.swing.Icon;

/**
 * Factoryklasse zur Erzeugung einiger skalierbarer Icons.
 *
 * @author Christoph Lembeck
 */
public class PaintedIconsHelper {

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

    /**
     * Erstellt das Icon für die Navigation an den Anfang eines Abschnitts.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param paint
     *            Zeichenstil für den Rand des Icons.
     * @param borderThickness
     *            Linienbreite für das Zeichnen des Icons.
     * @param fillPaint
     *            Füllstil für das innere des Icons.
     * @return Das erszeugte Icon.
     */
    public static Icon createNavigateSectionStart(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.SECTION_START, paint, borderThickness, fillPaint);
    }

    /**
     * Erstellt das Icon für die Navigation an das Ende eines Abschnitts.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param paint
     *            Zeichenstil für den Rand des Icons.
     * @param borderThickness
     *            Linienbreite für das Zeichnen des Icons.
     * @param fillPaint
     *            Füllstil für das innere des Icons.
     * @return Das erszeugte Icon.
     */
    public static Icon createNavigateSectionEnd(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.SECTION_END, paint, borderThickness, fillPaint);
    }

    /**
     * Erstellt das Icon für die Navigation um einen Schritt vor.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param paint
     *            Zeichenstil für den Rand des Icons.
     * @param borderThickness
     *            Linienbreite für das Zeichnen des Icons.
     * @param fillPaint
     *            Füllstil für das innere des Icons.
     * @return Das erszeugte Icon.
     */
    public static Icon createNavigateForward(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.FORWARD, paint, borderThickness, fillPaint);
    }

    /**
     * Erstellt das Icon für die Navigation um einen Schritt zurück.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param paint
     *            Zeichenstil für den Rand des Icons.
     * @param borderThickness
     *            Linienbreite für das Zeichnen des Icons.
     * @param fillPaint
     *            Füllstil für das innere des Icons.
     * @return Das erszeugte Icon.
     */
    public static Icon createNavigateBackward(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.BACKWARD, paint, borderThickness, fillPaint);
    }

    /**
     * Erstellt das Icon für die Navigation an den Anfang des Dokuments.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param paint
     *            Zeichenstil für den Rand des Icons.
     * @param borderThickness
     *            Linienbreite für das Zeichnen des Icons.
     * @param fillPaint
     *            Füllstil für das innere des Icons.
     * @return Das erszeugte Icon.
     */
    public static Icon createNavigateDocumentStart(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.DOCUMENT_START, paint, borderThickness, fillPaint);
    }

    /**
     * Erstellt das Icon für die Navigation an das Ende des Dokuments.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param paint
     *            Zeichenstil für den Rand des Icons.
     * @param borderThickness
     *            Linienbreite für das Zeichnen des Icons.
     * @param fillPaint
     *            Füllstil für das innere des Icons.
     * @return Das erszeugte Icon.
     */
    public static Icon createNavigateDocumentEnd(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.DOCUMENT_END, paint, borderThickness, fillPaint);
    }

    /**
     * Erstellt das Icon für die Navigation um mehrere Schritte vor.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param paint
     *            Zeichenstil für den Rand des Icons.
     * @param borderThickness
     *            Linienbreite für das Zeichnen des Icons.
     * @param fillPaint
     *            Füllstil für das innere des Icons.
     * @return Das erszeugte Icon.
     */
    public static Icon createNavigateNextDouble(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.FAST_FORWARD, paint, borderThickness, fillPaint);
    }

    /**
     * Erstellt das Icon für die Navigation um mehrere Schritte zurück.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param paint
     *            Zeichenstil für den Rand des Icons.
     * @param borderThickness
     *            Linienbreite für das Zeichnen des Icons.
     * @param fillPaint
     *            Füllstil für das innere des Icons.
     * @return Das erszeugte Icon.
     */
    public static Icon createNavigateFastBackward(final int size, final Paint paint, final float borderThickness,
            final Paint fillPaint) {
        return new NavigationIcon(size, Direction.FAST_BACKWARD, paint, borderThickness, fillPaint);
    }
}