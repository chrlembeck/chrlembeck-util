package de.chrlembeck.util.swing.icon;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

/**
 * Icon in Form eines Mauszeigers.
 *
 * @author Christoph Lembeck
 */
public class PointerIcon implements Icon {

    /**
     * Größe des Zeigers, wie er z.B. in Windows verwendet wird.
     */
    public static final int ORIGINAL_POINTER_SIZE = 19;

    /**
     * Höhe des Icons in Pixeln.
     */
    final private int iconHeight;

    /**
     * Größe des dargestellten Zeigers in Pixeln.
     */
    final private int pointerSize;

    /**
     * Breite des Icons in Pixeln.
     */
    final private int iconWidth;

    /**
     * Füllstil für den Zeiger.
     */
    private Paint fillPaint;

    /**
     * Linienstil für die Umrandung des Zeigers.
     */
    private BasicStroke stroke;

    /**
     * Zeichenstil für den Rahmen des Zeigers.
     */
    private Paint borderPaint;

    /**
     * Erstellt ein neues Icon mit den übergebenen Werten.
     * 
     * @param iconWidth
     *            Breite des Icons in Pixeln.
     * @param iconHeight
     *            Höhe des Icons in Pixeln.
     * @param pointerSize
     *            Größe des dargestellten Zeigers in Pixeln.
     * @param stroke
     *            Linienstil für die Umrandung des Zeigers.
     * @param borderPaint
     *            Zeichenstil für den Rahmen des Zeigers.
     * @param fillPaint
     *            Füllstil für den Zeiger.
     */
    public PointerIcon(final int iconWidth, final int iconHeight, final int pointerSize, final BasicStroke stroke,
            final Paint borderPaint, final Paint fillPaint) {
        this.iconHeight = iconHeight;
        this.iconWidth = iconWidth;
        this.pointerSize = pointerSize;
        this.stroke = stroke;
        this.fillPaint = fillPaint;
        this.borderPaint = borderPaint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon(final Component component, final Graphics graphics, final int xPos, final int yPos) {
        final Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.translate(xPos, yPos);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final Point2D top = new Point2D.Double(0, 0);
        final Point2D topLeft = new Point2D.Double(0, 16);
        final Point2D topRight = new Point2D.Double(11, 11);
        final Point2D middleLeft = new Point2D.Double(4, 12);
        final Point2D middleRight = new Point2D.Double(6, 11);
        final Point2D bottomLeft = new Point2D.Double(7, 18);
        final Point2D bottomRight = new Point2D.Double(9, 17);
        final Path2D path = new Path2D.Double();
        path.moveTo(top.getX(), top.getY());
        path.lineTo(topRight.getX(), topRight.getY());
        path.lineTo(middleRight.getX(), middleRight.getY());
        path.lineTo(bottomRight.getX(), bottomRight.getY());
        path.lineTo(bottomLeft.getX(), bottomLeft.getY());
        path.lineTo(middleLeft.getX(), middleLeft.getY());
        path.lineTo(topLeft.getX(), topLeft.getY());
        path.closePath();
        if (pointerSize != ORIGINAL_POINTER_SIZE) {
            path.transform(AffineTransform.getScaleInstance((float) (pointerSize) / ORIGINAL_POINTER_SIZE,
                    (float) (pointerSize) / ORIGINAL_POINTER_SIZE));
        }
        final Rectangle2D bounds = path.getBounds2D();
        path.transform(AffineTransform.getTranslateInstance((iconWidth - bounds.getWidth()) / 2,
                (iconHeight - bounds.getHeight()) / 2));
        g2d.setPaint(fillPaint);
        g2d.fill(path);
        g2d.setStroke(stroke);
        g2d.setPaint(borderPaint);
        g2d.draw(path);
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