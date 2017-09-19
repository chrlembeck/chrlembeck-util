package de.chrlembeck.util.swing.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
 * Border-Klasse zur Verwendung mit beliebigen Swing-Komponenten. Der IconBorder verziert die Komponente dabei mit einem
 * Icon, welches in der oberen rechten Ecke der Komponente dargestellt wird. Dies kann z.B. dafür verwendet werden, auf
 * gewisse Stati oder Fehlersituationen hinzuweisen.
 * 
 * @author Christoph Lembeck
 */
public class IconBorder extends AbstractBorder {

    /**
     * Version number of the current class.
     * 
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 6052604068767456843L;

    /**
     * Icon, welches rechts oben dargestellt werden soll.
     */
    private Icon icon;

    /**
     * Border, der um die Komponente herum gezeichnet werden soll. Das Icon befindet sich innerhalb der Grenzen des
     * äußeren Borders.
     */
    private Border outerBorder;

    /**
     * Abstand des Icons vom äußeren Border.
     */
    private int space;

    /**
     * Erstellt einen Border mit den übergebenen Werten.
     * 
     * @param icon
     *            Icon, welches rechts oben dargestellt werden soll.
     * @param space
     *            Abstand des Icons vom äußeren Border.
     * @param outerBorder
     *            Border, der um die Komponente herum gezeichnet werden soll. Das Icon befindet sich innerhalb der
     *            Grenzen des äußeren Borders.
     */
    public IconBorder(final Icon icon, final int space, final Border outerBorder) {
        this.icon = icon;
        this.space = space;
        this.outerBorder = outerBorder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintBorder(final Component component, final Graphics graphics, final int startX, final int startY,
            final int width,
            final int height) {
        outerBorder.paintBorder(component, graphics, startX, startY, width, height);
        final Insets insets = outerBorder.getBorderInsets(component);
        final int yOffset = insets.top + space;
        final int xOffset = width - insets.right - icon.getIconWidth() - space;
        icon.paintIcon(component, graphics, startX + xOffset, startY + yOffset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Insets getBorderInsets(final Component component) {
        final Insets insets = outerBorder.getBorderInsets(component);
        insets.right += icon.getIconWidth() + space;
        insets.left += space;
        insets.top += space;
        insets.bottom += space;
        return insets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}