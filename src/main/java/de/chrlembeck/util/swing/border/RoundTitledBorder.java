package de.chrlembeck.util.swing.border;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;

import javax.swing.JComponent;
import javax.swing.border.Border;

import de.chrlembeck.util.lang.StringUtils;

/**
 * Rahmen für die Verwendung mit beliebigen Swing-Komponenten. Der Rahmen besitzt abgerundete Ecken und optional einen
 * oben im Rahmen angezeigten Titel.
 * 
 * @author Christoph Lembeck
 */
public class RoundTitledBorder implements Border {

    /**
     * Farbe für das Zeichnen des Rahmens.
     */
    private Color borderColor;

    /**
     * Schriftart für das Zeichnen des Rahmentitels.
     */
    private Font font;

    /**
     * Breite des Rahmens in Pixeln.
     */
    private float lineWidth;

    /**
     * Radius für das Abrunden der Ecken.
     */
    private int radius;

    /**
     * Angezeigter Titel im oberen Bereich des Rahmens.
     */
    private String title;

    /**
     * Füllfarbe für den Titelbereich des Rahmens.
     */
    private Color titleBackground;

    /**
     * Farbe für die Darstellung des Rahmentitels.
     */
    private Color titleForeground;

    /**
     * Bereich, in dem der Titel des Rahmens dargestellt wird.
     */
    private RoundRectangle2D titleBorder;

    /**
     * Erstellt einen neuen Rahmen mit den übergebenen Daten.
     * 
     * @param borderColor
     *            Farbe für das Zeichnen des Rahmens.
     * @param lineWidth
     *            Breite des Rahmens in Pixeln.
     * @param radius
     *            Radius für das Abrunden der Ecken.
     * @param title
     *            Angezeigter Titel im oberen Bereich des Rahmens.
     * @param titleForeground
     *            Farbe für die Darstellung des Rahmentitels.
     * @param titleBackground
     *            Füllfarbe für den Titelbereich des Rahmens.
     * @param font
     *            Schriftart für das Zeichnen des Rahmentitels.
     */
    public RoundTitledBorder(final Color borderColor, final float lineWidth, final int radius, final String title,
            final Color titleForeground,
            final Color titleBackground, final Font font) {
        super();
        this.borderColor = borderColor;
        this.lineWidth = lineWidth;
        this.radius = radius;
        this.title = title;
        this.titleForeground = titleForeground;
        this.titleBackground = titleBackground;
        this.font = font;
    }

    @Override
    public Insets getBorderInsets(final Component component) {
        final int size = radius / 2 + (int) Math.ceil(lineWidth);
        if (StringUtils.isEmpty(title)) {
            return new Insets(size, size, size, size);
        } else {
            final int titleHeight;
            titleHeight = getTitleHeight(component);
            return new Insets(titleHeight + size, size, size, size);
        }
    }

    /**
     * Berechnet den Bereich, in dem der Titel des Rahmens dargestellt wird.
     * 
     * @param startX
     *            X-Koordinate für das Zeichnen des Rahmens.
     * @param startY
     *            Y-Koordinate für das Zeichnen des Rahmens.
     * @param titleHeight
     *            Höhe des Titel-Bereichs.
     * @param textBounds
     *            Bereich, der zum Zeichnen der Titelüberschrift benötigt wird.
     * @return Bereich für die Darstellung des Titels.
     */
    private Float getTitleBorder(final int startX, final int startY, final int titleHeight,
            final Rectangle2D textBounds) {
        return new RoundRectangle2D.Float(startX + radius / 2 + lineWidth, startY + lineWidth / 2,
                (float) textBounds.getWidth()
                        + titleHeight - lineWidth,
                titleHeight - lineWidth, titleHeight - lineWidth, titleHeight - lineWidth);
    }

    /**
     * Berechnet die benötigte Höhe für die Darstellung einer Überschrift.
     * 
     * @param component
     *            Komponente, die den Rahmen derhalten soll.
     * @return Benötigte Höhe für die Darstellung der Überschrift.
     */
    private int getTitleHeight(final Component component) {
        final int titleHeight = 2 + 2 * (int) Math.ceil(lineWidth) + component.getFontMetrics(font).getAscent()
                + component.getFontMetrics(font).getDescent();
        return titleHeight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    /**
     * Prüft, ob der übergebene Punkt innerhalb der Überschrift des Rahmens liegt.
     * 
     * @param comp
     *            Komponente, um die der Rahmen gelegt wurde.
     * @param point
     *            Punkt, dessen Lage überprüft werden soll.
     * @return true, falls der Punkt innerhalb des Überschriftenbereichs liegt, sonst false.
     */
    public boolean isInTitle(final JComponent comp, final Point point) {
        return titleBorder.contains(point);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintBorder(final Component component, final Graphics graphics, final int startX, final int startY,
            final int width,
            final int height) {
        final Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int titleHeight;
        titleBorder = null;
        Shape shape = null;
        if (StringUtils.isEmpty(title)) {
            titleHeight = 0;
        } else {
            g2d.setFont(font);
            final GlyphVector glyphVector = g2d.getFont().createGlyphVector(g2d.getFontRenderContext(), title);
            titleHeight = getTitleHeight(component);
            final Shape glyph = glyphVector.getOutline();
            final Rectangle2D textBounds = glyph.getBounds2D();
            titleBorder = getTitleBorder(startX, startY, titleHeight, textBounds);
            final AffineTransform textTransform = AffineTransform.getTranslateInstance(
                    -textBounds.getX() + titleBorder.getX()
                            + (titleBorder.getWidth() - textBounds.getWidth()) / 2,
                    -textBounds.getY() + titleBorder.getY()
                            + (titleBorder.getHeight() - textBounds.getHeight()) / 2);
            shape = textTransform.createTransformedShape(glyph);
        }
        final RoundRectangle2D border = new RoundRectangle2D.Float(startX + lineWidth / 2,
                startY + lineWidth / 2 + titleHeight / 2f, width
                        - lineWidth,
                height - lineWidth - titleHeight / 2f, radius, radius);

        final Rectangle rect = new Rectangle(startX, startY, width, height);
        final Area background = new Area(rect);
        background.subtract(new Area(border));
        final Container parent = component.getParent();
        if (parent != null) {
            final Color bgColor = parent.getBackground();
            g2d.setColor(bgColor);
            g2d.fill(background);
        }

        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(lineWidth));
        g2d.draw(border);
        if (!StringUtils.isEmpty(title)) {
            g2d.setColor(titleBackground);
            g2d.fill(titleBorder);
            g2d.setColor(borderColor);
            g2d.draw(titleBorder);
        }
        g2d.setColor(titleForeground);
        g2d.fill(shape);
    }
}