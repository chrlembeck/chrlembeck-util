package de.chrlembeck.util.swing.components.plaf;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import javax.swing.JComponent;

import de.chrlembeck.util.swing.components.ColorCircle;
import de.chrlembeck.util.swing.components.ColorCircle.HSV;

/**
 * Standardimplementierung der UI-Komponente zur Darstellung eines Farbkreises zur Farbauswahl.
 * 
 * @author Christoph Lembeck
 */
public class DefaultColorCircleUI extends AbstractColorCircleUI {

    /**
     * Breite des Farbauswahlrings in Pixeln.
     */
    private static final int BELT_WIDTH = 20;

    /**
     * 
     */
    ColorChooserMouseListener mouseListener = new ColorChooserMouseListener();

    /**
     * {@inheritDoc}
     */
    @Override
    public void installUI(final JComponent component) {
        super.installUI(component);
        component.addMouseListener(mouseListener);
        component.addMouseMotionListener(mouseListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uninstallUI(final JComponent component) {
        super.uninstallUI(component);
        component.removeMouseListener(mouseListener);
        component.removeMouseMotionListener(mouseListener);
    }

    /**
     * MouseListener für die Reaktion auf Mausclicks innerhalb der Farbauswahlkomponente.
     *
     * @author Christoph Lembeck
     */
    class ColorChooserMouseListener extends MouseAdapter {

        /**
         * Hält fest, ob der letzte Mausklick auf der Fläche des Farbauswahlrings stattgefunden hat.
         */
        private boolean pressedInBelt;

        /**
         * Gibt an, ob der letzte Mausklick innerhalb des Farbauswahldreiecks stattgefunden hat.
         */
        private boolean pressedInTriangle;

        /**
         * {@inheritDoc}
         */
        @Override
        public void mousePressed(final MouseEvent event) {
            final ColorCircle circle = (ColorCircle) event.getSource();
            final Point point = event.getPoint();
            if (isInColorBelt(circle, point.x, point.y)) {
                circle.setHue(getHueFromColorBelt(circle, point.x, point.y));
                pressedInBelt = true;
                pressedInTriangle = false;
            } else {
                pressedInBelt = false;
                if (isInColorTriangle(circle, point.x, point.y)) {
                    final HSV hsv = getHSVFromcolorTriangle(circle, point.x, point.y);
                    if (hsv != null) {
                        circle.setSaturation(hsv.getSaturation());
                        circle.setBlacknessValue(hsv.getBlacknessValue());
                    }
                    pressedInTriangle = true;
                } else {
                    pressedInTriangle = false;
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseDragged(final MouseEvent mouseEvent) {
            final ColorCircle circle = (ColorCircle) mouseEvent.getSource();
            final Point point = mouseEvent.getPoint();
            if (pressedInBelt) {
                circle.setHue(getHueFromColorBelt(circle, point.x, point.y));
            } else if (pressedInTriangle) {
                final HSV hsv = getHSVFromcolorTriangle(circle, point.x, point.y);
                if (hsv != null) {
                    circle.setSaturation(hsv.getSaturation());
                    circle.setBlacknessValue(hsv.getBlacknessValue());
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseReleased(final MouseEvent mouseEvent) {
            pressedInBelt = false;
            pressedInTriangle = false;
        }
    }

    /**
     * Farbverlaufskontext für das Zeichnen eines Farbauswahldreiecks aus dem HSV-Farbraum.
     * 
     * @author Christoph Lembeck
     */
    public static class HSVGradientPaintContext implements PaintContext {

        /**
         * Position der schwarzen Ecke des Dreiecks.
         */
        private Point2D black;

        /**
         * Euklidischer Abstand zwischen schwarzer und weißer Ecke.
         */
        private float distBlackToTopWhite;

        /**
         * Euklidischer Abstand zwischen farbiger und weißer Ecke.
         */
        private float distTopToWhite;

        /**
         * Differenz der x-Koordinate von der schwarzen zur farbigen Ecke.
         */
        private float dxBlackToTop;

        /**
         * Differenz der y-Koordinate von der schwarzen zur farbigen Ecke.
         */
        private float dyBlackToTop;

        /**
         * Aktueller Farbwert für die Darstellung des Dreiecks.
         */
        private int hue;

        /**
         * Cache für ein Berechnungszwischenergebnis, welches häufiger verwendet wird.
         */
        private float numerator;

        /**
         * Cache für das Raster, falls dieses mehrfach nachgefragt wird.
         */
        private WritableRaster rasterCache;

        /**
         * Position der Farbigen Ecke des Dreiecks.
         */
        private Point2D top;

        /**
         * Differenz der x-Koordinaten von der farbigen zur weißen Ecke.
         */
        private float dxTopToWhite;

        /**
         * Differenz der y-Koordinate von der farbigen zur weißen Ecke.
         */
        private float dyTopToWhite;

        /**
         * Position der weißen Ecke des Dreiecks.
         */
        private Point2D white;

        /**
         * Verbindungslinie von der schwarzen zur Wheißen Ecke.
         */
        private Line2D lineBlackToWhite;

        /**
         * Verbindungslinie von der farigen zur weißen Ecke.
         */
        private Line2D lineTopToWhite;

        /**
         * Erstell ein neues Farbverlaufsobjekt für das angegebene Dreieck und den gewünschten HSV-Farbwert.
         * 
         * @param top
         *            Obere Spitze des Dreiecks.
         * @param white
         *            Ecke des Dreiecks, in dem Weiß dargestellt werden soll.
         * @param black
         *            Ecke des Dreiecks, in dem Schwarz dargestellt werden soll.
         * @param hue
         *            Farbwert für das zu zeichnende Dreieck.
         */
        public HSVGradientPaintContext(final Point2D top, final Point2D white, final Point2D black, final int hue) {
            this.top = top;
            this.white = white;
            this.black = black;
            this.hue = hue;
            this.lineTopToWhite = new Line2D.Double(top, white);
            this.distBlackToTopWhite = (float) lineTopToWhite.ptLineDist(black);
            this.distTopToWhite = (float) top.distance(white);
            this.dxTopToWhite = (float) (white.getX() - top.getX());
            this.dyTopToWhite = (float) (white.getY() - top.getY());
            this.dxBlackToTop = (float) (top.getX() - black.getX());
            this.dyBlackToTop = (float) (top.getY() - black.getY());
            this.numerator = dxBlackToTop * dyTopToWhite - dyBlackToTop * dxTopToWhite;
            this.lineBlackToWhite = new Line2D.Double(black, white);
        }

        @Override
        public void dispose() {
            // nothing to dispose
        }

        /**
         * Ermittelt die Farbsättigung der Farbe an der Position im Dreieck.
         * 
         * @param xPos
         *            X-Koordinate des zu prüfenden Punktes.
         * @param yPos
         *            Y-Koordinste des zu prüfenden Punktes.
         * @return Farbsättigung an der angegebenen Stelle in Form einer HSV-Angabe.
         */
        final int getSaturation(final int xPos, final int yPos) {
            if (lineBlackToWhite.relativeCCW(xPos, yPos) < 0) {
                return 0;
            }

            final float blackToPoint_x = (float) (xPos - black.getX());
            final float blackToPoint_y = (float) (yPos - black.getY());
            // schnittpunkt = black * coeff + blackToPoint
            final float coeff = numerator / (blackToPoint_x * dyTopToWhite - dxTopToWhite * blackToPoint_y);
            final Point2D schnittpunkt = new Point2D.Double(black.getX() + coeff * blackToPoint_x, black.getY() + coeff
                    * blackToPoint_y);
            final float sat = (float) (white.distance(schnittpunkt) / distTopToWhite);
            return Math.min(255, (int) (sat * 255));
        }

        /**
         * Ermittelt die Farbe eines Punktes innerhalb des Farbauswahldreiecks und gibt ihre RGBA-Werte als Array
         * zurück.
         * 
         * @param xPos
         *            X-Koordinate des zu prüfenden Punktes.
         * @param yPos
         *            Y-Koordinste des zu prüfenden Punktes.
         * @return Farbe an der angegebenen Stelle in Form eines RGBA-Arrays.
         */
        private int[] getColor(final int xPos, final int yPos) {
            final int blacknessValue = getBlacknessValue(xPos, yPos);
            final int saturation = getSaturation(xPos, yPos);
            final Color color = ColorCircle.getColorByHSV(hue, saturation, blacknessValue);
            return new int[] { color.getRed(), color.getGreen(), color.getBlue(), 255 };
        }

        /**
         * Ermittelt den Helligkeitswert der Farbe an der Position im Dreieck.
         * 
         * @param xPos
         *            X-Koordinate des zu prüfenden Punktes.
         * @param yPos
         *            Y-Koordinste des zu prüfenden Punktes.
         * @return Helligkeitswert an der angegebenen Stelle in Form einer HSV-Angabe.
         */
        final int getBlacknessValue(final int xPos, final int yPos) {
            if (lineTopToWhite.relativeCCW(xPos, yPos) > 0) {
                return 255;
            }
            // value aus der Entfernung des Punkts von der Geraden 'Top-White' berechnen
            final float distTopWhiteToP = (float) new Line2D.Float(top, white).ptLineDist(xPos, yPos);
            return Math.max(0, 255 - (int) (distTopWhiteToP / distBlackToTopWhite * 255));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ColorModel getColorModel() {
            return ColorModel.getRGBdefault();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Raster getRaster(final int xPos, final int yPos, final int width, final int height) {
            final WritableRaster raster;
            if (rasterCache != null && rasterCache.getWidth() >= width && rasterCache.getHeight() >= height) {
                raster = rasterCache;
            } else {
                raster = getColorModel().createCompatibleWritableRaster(width, height);
                if (width <= 64 && height <= 64) {
                    rasterCache = raster;
                }
            }
            for (int ix = 0; ix < width; ix++) {
                for (int iy = 0; iy < height; iy++) {
                    raster.setPixel(ix, iy, getColor(xPos + ix, yPos + iy));
                }
            }
            return raster;
        }
    }

    public static DefaultColorCircleUI createUI(final JComponent component) {
        return new DefaultColorCircleUI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HSV getHSVFromcolorTriangle(final ColorCircle circle, final int xPos, final int yPos) {
        final int hue = circle.getHue();
        final Insets insets = circle.getInsets();
        final int width = circle.getWidth() - insets.left - insets.right;
        @SuppressWarnings("PMD.AccessorMethodGeneration")
        final int height = circle.getHeight() - insets.top - insets.bottom;
        final Point start = getTopLeftPosition(insets);
        final int size = Math.min(width, height);
        final int radius = size / 2;
        final Point center = getCenter(start, width, height);
        final Point2D top = getTopPoint(center, radius, hue);
        final Point2D white = getWhitePoint(center, radius, hue);
        final Point2D black = getBlackPoint(center, radius, hue);
        final HSVGradientPaintContext context = new HSVGradientPaintContext(top, white, black, hue);
        final int saturation = context.getSaturation(xPos, yPos);
        final int blacknessValue = context.getBlacknessValue(xPos, yPos);
        return new HSV(hue, saturation, blacknessValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHueFromColorBelt(final ColorCircle circle, final int xPos, final int yPos) {
        final Insets insets = circle.getInsets();
        final Point start = getTopLeftPosition(insets);
        final int width = circle.getWidth() - insets.left - insets.right;
        @SuppressWarnings("PMD.AccessorMethodGeneration")
        final int height = circle.getHeight() - insets.top - insets.bottom;
        final Point center = getCenter(start, width, height);
        return (180 + (int) (Math.atan2(center.x - xPos, yPos - center.y) / Math.PI * 180)) % 360;
    }

    @Override
    public Dimension getMinimumSize(final JComponent component) {
        return getPreferredSize(component);
    }

    @Override
    public Dimension getPreferredSize(final JComponent component) {
        return new Dimension(200, 200);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInColorBelt(final ColorCircle circle, final int xPos, final int yPos) {
        final Insets insets = circle.getInsets();
        final Point start = getTopLeftPosition(insets);
        final int width = circle.getWidth() - insets.left - insets.right;
        @SuppressWarnings("PMD.AccessorMethodGeneration")
        final int height = circle.getHeight() - insets.top - insets.bottom;
        final int size = Math.min(width, height);
        final int radius = size / 2;
        final Point center = getCenter(start, width, height);
        final Shape outerCircle = new Ellipse2D.Float(center.x - radius, center.y - radius, size, size);
        final Shape innerCircle = new Ellipse2D.Float(center.x - radius + BELT_WIDTH, center.y - radius + BELT_WIDTH,
                size
                        - BELT_WIDTH * 2,
                size - BELT_WIDTH * 2);
        return outerCircle.contains(xPos, yPos) && !innerCircle.contains(xPos, yPos);
    }

    /**
     * Gibt die Koordinate für den oberen Punkt des Farbdreiecks zurück.
     * 
     * @param center
     *            Mittelpunkt des Farbkreiese.
     * @param radius
     *            Radius des Farbkreises.
     * @param hue
     *            Farbwert, der im Farbkreis ausgewählt ist.
     * @return Koordinate für den oberen Punkt des Farbdreiecks.
     */
    public Point2D getTopPoint(final Point center, final int radius, final int hue) {
        return new Point2D.Float(center.x + (radius - BELT_WIDTH) * (float) Math.sin(hue * Math.PI / 180), center.y
                - (radius - BELT_WIDTH) * (float) Math.cos(hue * Math.PI / 180));
    }

    /**
     * Gibt die Koordinate für den weißen Punkt des Farbdreiecks zurück.
     * 
     * @param center
     *            Mittelpunkt des Farbkreiese.
     * @param radius
     *            Radius des Farbkreises.
     * @param hue
     *            Farbwert, der im Farbkreis ausgewählt ist.
     * @return Koordinate für den weißen Punkt des Farbdreiecks.
     */
    public Point2D getWhitePoint(final Point center, final int radius, final int hue) {
        return new Point2D.Float(center.x + (radius - BELT_WIDTH) * (float) Math.sin((hue + 120) * Math.PI / 180),
                center.y
                        - (radius - BELT_WIDTH) * (float) Math.cos((hue + 120) * Math.PI / 180));
    }

    /**
     * Gibt die Koordinate für den schwarzen Punkt des Farbdreiecks zurück.
     * 
     * @param center
     *            Mittelpunkt des Farbkreiese.
     * @param radius
     *            Radius des Farbkreises.
     * @param hue
     *            Farbwert, der im Farbkreis ausgewählt ist.
     * @return Koordinate für den schwarzen Punkt des Farbdreiecks.
     */
    public Point2D getBlackPoint(final Point center, final int radius, final int hue) {
        return new Point2D.Float(center.x + (radius - BELT_WIDTH) * (float) Math.sin((hue + 240) * Math.PI / 180),
                center.y
                        - (radius - BELT_WIDTH) * (float) Math.cos((hue + 240) * Math.PI / 180));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInColorTriangle(final ColorCircle circle, final int xPos, final int yPos) {
        final int hue = circle.getHue();
        final Insets insets = circle.getInsets();
        final Point start = getTopLeftPosition(insets);
        final int width = circle.getWidth() - insets.left - insets.right;
        @SuppressWarnings("PMD.AccessorMethodGeneration")
        final int height = circle.getHeight() - insets.top - insets.bottom;
        final int size = Math.min(width, height);
        final int radius = size / 2;
        final Point center = getCenter(start, width, height);
        final Point2D top = getTopPoint(center, radius, hue);
        final Point2D white = getWhitePoint(center, radius, hue);
        final Point2D black = getBlackPoint(center, radius, hue);
        final Path2D triangle = getTriangle(top, white, black);
        return triangle.contains(xPos, yPos);
    }

    /**
     * Erzeugt aus den drei übergebenen Eckpunkten ein Pfad-Objekt, welches das Dreieck beschreibt.
     * 
     * @param top
     *            Position der farbigen Ecke des Dreiecks.
     * @param white
     *            Position der weißen Ecke des Dreiecks.
     * @param black
     *            Position der schwarzen Ecke des Dreiecks.
     * @return Pfad-Objekt, welches das Dreieck darstellt.
     */
    Path2D getTriangle(final Point2D top, final Point2D white, final Point2D black) {
        final Path2D triangle = new Path2D.Float(Path2D.WIND_NON_ZERO, 3);
        triangle.moveTo(top.getX(), top.getY());
        triangle.lineTo(white.getX(), white.getY());
        triangle.lineTo(black.getX(), black.getY());
        triangle.closePath();
        return triangle;
    }

    /**
     * Ermittelt aus dem Insets-Objekt die obere linke Ecke, bei der das Zeichnen der Komponenten beginnen kann.
     * 
     * @param insets
     *            Insets für das Objekt
     * @return Obere, linke Ecke des Insets-Objekt als die Position, an der die Komponente gezeichnet werden soll.
     */
    @SuppressWarnings("PMD.AccessorMethodGeneration") // due to pmd bug...
    private Point getTopLeftPosition(final Insets insets) {
        return new Point(insets.left, insets.top);
    }

    /**
     * Ermittelt die Koordinaten der Mitte der Komponente.
     * 
     * @param start
     *            Obere, linke Ecke der Komponente
     * @param width
     *            Breite des Teils der Komponente, in dem sie gezeichnet werden darf (also ohne einen möglichen Rahmen).
     * @param height
     *            Höhe des Teils der Komponente, in dem sie gezeichnet werden darf (also ohne einen möglichen Rahmen).
     * @return Koordinate des Mittelpunkts des Zeichenbereichs.
     */
    private Point getCenter(final Point start, final int width, final int height) {
        return new Point(start.x + width / 2, start.y + height / 2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(final Graphics graphics, final JComponent component) {
        final ColorCircle circle = (ColorCircle) component;
        final Graphics2D g2d = (Graphics2D) graphics;
        final int hue = circle.getHue();
        final int saturation = circle.getSaturation();
        final int blacknessValue = circle.getBlacknessValue();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final Insets insets = circle.getInsets();
        final Point start = getTopLeftPosition(insets);
        final int width = circle.getWidth() - insets.left - insets.right;
        @SuppressWarnings("PMD.AccessorMethodGeneration")
        final int height = circle.getHeight() - insets.top - insets.bottom;
        final int size = Math.min(width, height);
        final int radius = size / 2;
        final Point center = getCenter(start, width, height);
        final int anz = 180;
        final float eps = 360f / anz * 0.1f;
        for (int i = 0; i < anz; i++) {
            final Arc2D arc = new Arc2D.Float(center.x - radius, center.y - radius, size, size, -i * 360 / anz + 90, 360
                    / anz + eps, Arc2D.PIE);
            g2d.setColor(ColorCircle.getColorByHSV(i * 360 / anz, 255, 255));
            g2d.fill(arc);
        }
        g2d.setColor(circle.getBackground());
        g2d.fillOval(center.x - radius + BELT_WIDTH, center.y - radius + BELT_WIDTH, size - BELT_WIDTH * 2,
                size - BELT_WIDTH
                        * 2);
        if (hue > 30 && hue < 210) {
            g2d.setColor(Color.BLACK);
        } else {
            g2d.setColor(Color.WHITE);
        }
        Shape pointer = new Rectangle2D.Float(radius - BELT_WIDTH, -1, BELT_WIDTH + 1, 2);
        final AffineTransform transform = AffineTransform.getTranslateInstance(center.x, center.y);
        transform.rotate((hue - 90) * Math.PI / 180);
        pointer = transform.createTransformedShape(pointer);
        g2d.fill(pointer);
        final Point2D top = getTopPoint(center, radius, hue);
        final Point2D white = getWhitePoint(center, radius, hue);
        final Point2D black = getBlackPoint(center, radius, hue);
        final Point2D saturationPoint = new Point2D.Float((float) (saturation * top.getX() + (255 - saturation)
                * white.getX()) / 255, (float) (saturation * top.getY() + (255 - saturation) * white.getY()) / 255);
        final Point2D colorPoint = new Point2D.Float(
                (float) (blacknessValue * saturationPoint.getX() + (255 - blacknessValue) * black.getX()) / 255,
                (float) (blacknessValue * saturationPoint.getY() + (255 - blacknessValue) * black.getY()) / 255);

        final Path2D triangle = getTriangle(top, white, black);
        g2d.setColor(Color.WHITE);
        g2d.setPaint(new HSVGradientPaint(top, white, black, hue));
        g2d.fill(triangle);
        if (blacknessValue - saturation / 3 < 127) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(Color.BLACK);
        }
        final Shape colorSelector = new Ellipse2D.Double(colorPoint.getX() - 3, colorPoint.getY() - 3, 6, 6);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(colorSelector);
    }

    /**
     * Paint-Objekt zum Malen des Farbverlaufs innerhalb des Dreiecks.
     *
     * @author Christoph Lembeck
     */
    static class HSVGradientPaint implements Paint {

        /**
         * Position der schwarzen Ecke des Dreiecks.
         */
        private Point2D black;

        /**
         * Farbwert der farbigen Ecke des Dreiecks.
         */
        private int hue;

        /**
         * Position der farbigen Ecke des Dreiecks.
         */
        private Point2D top;

        /**
         * Position der weißen Ecke des Dreiecks.
         */
        private Point2D white;

        /**
         * Erstellt das Paint-Objekt anhand der übergebenen Werte.
         * 
         * @param top
         *            Position der farbigen Ecke des Dreiecks.
         * @param white
         *            Position der weißen Ecke des Dreiecks.
         * @param black
         *            Position der schwarzen Ecke des Dreiecks.
         * @param hue
         *            Farbwert für die Farbe der farbigen Ecke des Dreiecks.
         */
        public HSVGradientPaint(final Point2D top, final Point2D white, final Point2D black, final int hue) {
            super();
            this.top = top;
            this.white = white;
            this.black = black;
            this.hue = hue;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public PaintContext createContext(final ColorModel colorModel, final Rectangle deviceBounds,
                final Rectangle2D userBounds,
                final AffineTransform xform, final RenderingHints hints) {
            return new HSVGradientPaintContext(top, white, black, hue);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.awt.Transparency#getTransparency()
         */
        @Override
        public int getTransparency() {
            return OPAQUE;
        }
    }
}