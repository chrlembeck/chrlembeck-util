package de.chrlembeck.util.swing.icon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

/**
 * Skalierbares, rundes Icon mit oder ohne plastischen Effekt.
 *
 * @author Christoph Lembeck
 */
public class BallIcon implements Icon {

    /**
     * Höhe und Breite des Icons
     */
    private int size;

    /**
     * Füllfarbe der Kugel.
     */
    private Color color;

    /**
     * Randfarbe der Kugel.
     */
    private Color borderColor;

    /**
     * Breite des Rands in Pixeln.
     */
    private float borderThickness;

    /**
     * Auf dem Icon darzustellendes Zeichen.
     */
    private Character symbol;

    /**
     * Farbe des Zeichens, dass auf der Kugel dargestellt werden soll.
     */
    private Color symbolColor;

    /**
     * Schriftart für das auf der Kugel darzustellende Zeichen.
     */
    private Font symbolFont;

    /**
     * {@code true} falls das Icon plastisch als Kugel gezeichnet werden soll, false, falls es flach erscheinen soll.
     */
    private boolean spatial;

    /**
     * Erstellt ein neues Icon mit den übergebenen Werten.
     * 
     * @param size
     *            Höhe und Breite des Icons.
     * @param color
     *            Füllfarbe des Icons.
     * @param borderThickness
     *            Breite des Rahmens in Pixeln.
     * @param borderColor
     *            Rahmenfarbe.
     * @param symbol
     *            Auf dem Icon darzustellendes Zeichen oder null für kein Zeichen.
     * @param symbolColor
     *            Farbe des darzustellenden Zeichens.
     * @param symbolFont
     *            Schriftart für das anzuzeigende Symbol.
     * @param spatial
     *            steuert die plastische oder flache Darstellung des Icons.
     */
    public BallIcon(final int size, final Color color, final float borderThickness, final Color borderColor,
            final Character symbol, final Color symbolColor,
            final Font symbolFont, final boolean spatial) {
        this.size = size;
        this.color = color;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        this.symbol = symbol;
        this.symbolColor = symbolColor;
        this.symbolFont = symbolFont;
        this.spatial = spatial;
    }

    /**
     * Gibt die Größe des Icons in Pixeln zurück.
     * 
     * @return Größe des Icons in Pixeln.
     */
    public int getSize() {
        return size;
    }

    /**
     * Git die Füllfarbe für das Icon zurück.
     * 
     * @return Füllfarbe für das Icon.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gibt die Rahmenfare für das Icon zurück.
     * 
     * @return Farbe des Iconrands.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon(final Component component, final Graphics graphics, final int xPos, final int yPos) {
        final Graphics2D g2d = (Graphics2D) graphics.create();
        final float radius = size / 2f;
        paintSphere(g2d, xPos + radius, yPos + radius, radius - borderThickness, color, spatial);
        g2d.setStroke(new BasicStroke(borderThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(borderColor);
        if (borderThickness > 0) {
            final Shape border = new Ellipse2D.Double(xPos + borderThickness / 2, yPos + borderThickness / 2,
                    size - borderThickness,
                    size - borderThickness);
            // Workaround für http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6431487
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2d.draw(border);
        }
        if (symbol != null) {
            g2d.setFont(symbolFont);
            g2d.setColor(symbolColor);
            if (spatial) {
                final Paint paint = createPaint(radius, radius, radius, symbolColor.brighter(), symbolColor,
                        symbolColor.darker());
                g2d.setPaint(paint);
            } else {
                g2d.setPaint(symbolColor);
            }
            final GlyphVector glyphVector = g2d.getFont().createGlyphVector(g2d.getFontRenderContext(),
                    new char[] { symbol.charValue() });
            final Shape glyph = glyphVector.getOutline();
            final Rectangle2D bounds2d = glyph.getBounds2D();
            final AffineTransform transform = AffineTransform.getTranslateInstance(
                    xPos - bounds2d.getX() + (size - bounds2d.getWidth()) / 2,
                    yPos - bounds2d.getY() + (size - bounds2d.getHeight())
                            / 2);
            final Shape shape = transform.createTransformedShape(glyph);
            g2d.fill(shape);
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

    /**
     * Zeichnet eine Kugel in das übergebene Graphics-Objekt.
     * 
     * @param graphics
     *            Objekt, mit dem die Kugel gezeichnet werden soll.
     * @param centerX
     *            X-Koordinate des Kugelmittelpunkts.
     * @param centerY
     *            Y-Koordinate des Kugelmittelpunkts.
     * @param radius
     *            Radius des äußeren Rands der Kugel.
     * @param color
     *            Farbe der Kugel.
     * @param spatial
     *            {@code true} falls der Kugel ein räumlicher Effekt verpasst werden soll, {@code false} falls die Kugel
     *            als flacher Kreis dargestellt werden soll.
     */
    static void paintSphere(final Graphics2D graphics, final float centerX, final float centerY, final float radius,
            final Color color, final boolean spatial) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (spatial) {
            final Paint paint = createPaint(centerX, centerY, radius, Color.WHITE, color, Color.BLACK);
            graphics.setPaint(paint);
        } else {
            graphics.setPaint(color);
        }
        final Shape circle = new Ellipse2D.Double(centerX - radius, centerY - radius, radius * 2, radius * 2);
        graphics.fill(circle);
    }

    /**
     * Erstellt den Fülleffekt für die Darstellung einer Kugel mit dreidimensionalem Lichteffekt.
     * 
     * @param centerX
     *            X-Koordinate des Kugelmittelpunkts.
     * @param centerY
     *            Y-Koordinate des Kugelmittelpunkts.
     * @param radius
     *            Radius der Kugel.
     * @param lightColor
     *            Farbe der hell angeleuchteten Seite der Kugel
     * @param color
     *            Farbe der Kugel.
     * @param darkColor
     *            Farbe der dunklen Seite der Kugel.
     * @return Fülleffekt für die räumlich dargestellte Kugel.
     */
    private static RadialGradientPaint createPaint(final float centerX, final float centerY, final float radius,
            final Color lightColor,
            final Color color, final Color darkColor) {
        return new RadialGradientPaint(centerX - 0.4f * radius, centerY - 0.4f * radius, radius * 2, new float[] { 0f,
                0.3f, 1f }, new Color[] { lightColor, color, darkColor });
    }
}