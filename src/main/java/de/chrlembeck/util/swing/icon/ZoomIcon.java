package de.chrlembeck.util.swing.icon;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

/**
 * Icons für die Symbolisierung verschiedener Zoom-Funktionen.
 *
 * @author Christoph Lembeck
 */
public class ZoomIcon implements Icon {

    /**
     * Größe des Icons in Pixeln.
     */
    private int size;

    /**
     * Füllstil für den Inhalt der Zoom-Icons.
     */
    private Paint fillPaint;

    /**
     * Zeichenstil für den Icon-Rahmen.
     */
    private Paint borderPaint;

    /**
     * Dicke der gezeichneten Linien.
     */
    private float borderThickness;

    /**
     * Zeichstil für das in dem Icon verwendete Symbol.
     */
    private Paint signPaint;

    /**
     * Linienstärke für das im Icon dargestellte Symbol.
     */
    private float signThickness;

    /**
     * Symbol im Innern des Icons.
     */
    private Sign sign;

    /**
     * Mögliche Symbole für das Innere des ZoomIcons.
     *
     * @author Christoph Lembeck
     */
    public enum Sign {

        /**
         * Plus-Symbol für das Vergrößern.
         */
        PLUS,

        /**
         * Minus-Symbol für das Verkleinern.
         */
        MINUS,

        /**
         * Kein Symbol.
         */
        NONE,

        /**
         * Symbol zur Anzeige der Originalgröße.
         */
        ORIGINAL,

        /**
         * Symbol zum Einpassen des Dokuments an die vorhandene Zeichenfläche.
         */
        FIT
    }

    /**
     * Erstellt ein neues Icon aus den übergebenen Werten.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param sign
     *            Symbol im Innern des Icons.
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
     */
    public ZoomIcon(final int size, final Sign sign, final Paint borderPaint, final float borderThickness,
            final Paint fillPaint, final Paint signPaint,
            final float signThickness) {
        this.size = size;
        this.sign = sign;
        this.borderPaint = borderPaint;
        this.fillPaint = fillPaint;
        this.borderThickness = borderThickness;
        this.signPaint = signPaint;
        this.signThickness = signThickness;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon(final Component component, final Graphics graphics, final int xPos, final int yPos) {
        final Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.translate(xPos, yPos);
        final float radius = size / 5f * 2f;
        final Ellipse2D borderEllipse = new Ellipse2D.Double(borderThickness / 2, borderThickness / 2, 2 * radius
                - borderThickness, 2 * radius - borderThickness);
        final Ellipse2D innerEllipse = new Ellipse2D.Double(borderThickness, borderThickness, 2 * radius - 2
                * borderThickness, 2 * radius - 2 * borderThickness);
        final Line2D line = new Line2D.Double(radius + 1 / Math.sqrt(2) * radius, radius + 1 / Math.sqrt(2) * radius,
                size
                        - borderThickness,
                size - borderThickness);
        g2d.setStroke(new BasicStroke(borderThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setPaint(fillPaint);
        g2d.fill(innerEllipse);
        g2d.setPaint(borderPaint);
        g2d.draw(borderEllipse);
        g2d.setStroke(new BasicStroke(borderThickness * 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(line);

        if (sign != Sign.NONE) {
            g2d.setStroke(new BasicStroke(signThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setPaint(signPaint);
        }
        if (sign == Sign.MINUS || sign == Sign.PLUS) {
            final double ext = radius - borderThickness - 2 * signThickness;
            final Line2D minus = new Line2D.Double(radius - ext, radius, radius + ext, radius);
            g2d.draw(minus);
            if (sign == Sign.PLUS) {
                final Line2D plus = new Line2D.Double(radius, radius - ext, radius, radius + ext);
                g2d.draw(plus);
            }
        }

        if (sign == Sign.ORIGINAL) {
            final double ext = radius - borderThickness - 2 * signThickness;
            final double xOffset = ext / 3;
            final Line2D diagonal = new Line2D.Double(radius - ext + xOffset, radius, radius + xOffset, radius - ext);
            g2d.draw(diagonal);
            final Line2D vertical = new Line2D.Double(radius + xOffset, radius - ext, radius + xOffset, radius + ext);
            g2d.draw(vertical);
        }

        if (sign == Sign.FIT) {
            final double ext = radius * 7 / 8 - borderThickness - 2 * signThickness;
            final Rectangle2D rect = new Rectangle2D.Double(radius - ext, radius - ext, 2 * ext, 2 * ext);
            g2d.draw(rect);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconWidth() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconHeight() {
        return size;
    }
}