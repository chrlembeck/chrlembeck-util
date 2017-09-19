package de.chrlembeck.util.swing.icon;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

/**
 * Icon, das nur aus einer gefüllten Ellipse mit Rahmen besteht.
 *
 * @author Christoph Lembeck
 */
public class EllipseIcon implements Icon {

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
     * Breite der Ellipse in Pixeln inclusive Rahmen.
     */
    private float ellipseWidth;

    /**
     * Höhe der Ellipse in Pixeln inclusive Rahmen.
     */
    private float ellipseHeight;

    /**
     * Linienstil für das Zeichnen des Rahmens.
     */
    private BasicStroke borderStroke;

    /**
     * Füllstil für das Zeichnen des Rahmens.
     */
    private Paint borderPaint;

    /**
     * Erstellt ein neues Icon aus den übergebenen Daten.
     * 
     * @param iconWidth
     *            Breite des Icons in Pixeln.
     * @param iconHeight
     *            Höhe des Icons in Pixeln.
     * @param ellipseWidth
     *            Breite der Ellipse in Pixeln inclusive Rahmen.
     * @param ellipseHeight
     *            Höhe der Ellipse in Pixeln inclusive Rahmen.
     * @param borderStroke
     *            Linienstil für das Zeichnen des Rahmens.
     * @param borderPaint
     *            Füllstil für das Zeichnen des Rahmens.
     * @param fillPaint
     *            Füllstil für den Ellipsenhintergrund.
     * @param backgroundPaint
     *            Füllstil für den Iconhintergrund.
     */
    public EllipseIcon(final int iconWidth, final int iconHeight, final float ellipseWidth, final float ellipseHeight,
            final BasicStroke borderStroke,
            final Paint borderPaint, final Paint fillPaint, final Paint backgroundPaint) {
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.ellipseWidth = ellipseWidth;
        this.ellipseHeight = ellipseHeight;
        this.borderStroke = borderStroke;
        this.borderPaint = borderPaint;
        this.fillPaint = fillPaint;
        this.backgroundPaint = backgroundPaint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon(final Component component, final Graphics graphics, final int xPos, final int yPos) {
        final Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final float xOffset = (iconWidth - ellipseWidth) / 2;
        final float yOffset = (iconHeight - ellipseHeight) / 2;
        final float borderThickness = borderStroke.getLineWidth();
        g2d.setPaint(backgroundPaint);
        g2d.fill(new Rectangle2D.Float(xPos, yPos, iconWidth, iconHeight));
        g2d.setPaint(fillPaint);
        g2d.fill(new Ellipse2D.Float(xPos + xOffset + borderThickness, yPos + yOffset + borderThickness,
                ellipseWidth - 2 * borderThickness,
                ellipseHeight - 2 * borderThickness));
        g2d.setPaint(borderPaint);
        g2d.setStroke(borderStroke);
        g2d.draw(new Ellipse2D.Float(xPos + xOffset + borderThickness / 2, yPos + yOffset + borderThickness / 2,
                ellipseWidth
                        - borderThickness,
                ellipseHeight - borderThickness));
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