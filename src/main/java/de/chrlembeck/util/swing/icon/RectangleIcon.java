package de.chrlembeck.util.swing.icon;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;

/**
 * Icon, das nur aus einem gefüllten Rechteck mit Rahmen besteht.
 *
 * @author Christoph Lembeck
 */
public class RectangleIcon implements Icon {

    /**
     * Füllstil für den Iconhintergrund.
     */
    private Paint backgroundPaint;

    /**
     * Füllstil für den Ellipsenhintergrund.
     */
    private Paint fillPaint;

    /**
     * Breite des Icons in Pixeln.
     */
    private int iconWidth;

    /**
     * Höhe des Icons in Pixeln.
     */
    private int iconHeight;

    /**
     * Breite des Rechtecks in Pixeln inclusive Rahmen.
     */
    private float rectangleWidth;

    /**
     * Höhe des Rechtecks in Pixeln inclusive Rahmen.
     */
    private float rectangleHeight;

    /**
     * Linienstil für das Zeichnen des Rahmens.
     */
    private BasicStroke borderStroke;

    /**
     * Füllstil für das Zeichnen des Rahmens.
     */
    private Paint borderPaint;

    /**
     * Radius zum Abrunden Ecken.
     */
    private float edgeRadius;

    /**
     * Erstellt ein neues Icon aus den übergebenen Daten.
     * 
     * @param iconWidth
     *            Breite des Icons in Pixeln.
     * @param iconHeight
     *            Höhe des Icons in Pixeln.
     * @param rectangleWidth
     *            Breite des Rechtecks in Pixeln inclusive Rahmen.
     * @param rectangleHeight
     *            Höhe des Rechtecks in Pixeln inclusive Rahmen.
     * @param borderStroke
     *            Linienstil für das Zeichnen des Rahmens.
     * @param borderPaint
     *            Füllstil für das Zeichnen des Rahmens.
     * @param fillPaint
     *            Füllstil für den Ellipsenhintergrund.
     * @param backgroundPaint
     *            Füllstil für den Iconhintergrund.
     * @param edgeRadius
     *            Radius zum Abrunden Ecken.
     */
    public RectangleIcon(final int iconWidth, final int iconHeight, final float rectangleWidth,
            final float rectangleHeight,
            final BasicStroke borderStroke, final Paint borderPaint, final Paint fillPaint, final Paint backgroundPaint,
            final float edgeRadius) {
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.rectangleWidth = rectangleWidth;
        this.rectangleHeight = rectangleHeight;
        this.borderStroke = borderStroke;
        this.borderPaint = borderPaint;
        this.fillPaint = fillPaint;
        this.backgroundPaint = backgroundPaint;
        this.edgeRadius = edgeRadius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon(final Component component, final Graphics graphics, final int xPos, final int yPos) {
        final Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final float xOffset = (iconWidth - rectangleWidth) / 2;
        final float yOffset = (iconHeight - rectangleHeight) / 2;
        final float borderThickness = borderStroke.getLineWidth();
        g2d.setPaint(backgroundPaint);

        g2d.fill(new Rectangle2D.Float(xPos, yPos, iconWidth, iconHeight));
        g2d.setPaint(fillPaint);
        g2d.fill(new RoundRectangle2D.Float(xPos + xOffset + borderThickness, yPos + yOffset + borderThickness,
                rectangleWidth - 2
                        * borderThickness,
                rectangleHeight - 2 * borderThickness, edgeRadius - borderThickness, edgeRadius
                        - borderThickness));
        if (borderThickness > 0) {
            g2d.setPaint(borderPaint);
            g2d.setStroke(borderStroke);
            g2d.draw(new RoundRectangle2D.Float(xPos + xOffset + borderThickness / 2,
                    yPos + yOffset + borderThickness / 2, rectangleWidth
                            - borderThickness,
                    rectangleHeight - borderThickness, edgeRadius - borderThickness / 2, edgeRadius
                            - borderThickness / 2));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconWidth() {
        return iconWidth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconHeight() {
        return iconHeight;
    }
}