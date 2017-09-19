package de.chrlembeck.util.swing.border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import de.chrlembeck.util.swing.icon.BallIcon;

/**
 * Tests für den IconBorder
 *
 * @author Christoph Lembeck
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class IconBorderTest {

    /**
     * Öffnet ein Fenster zur Anzeige des Borders.
     * 
     * @param args
     *            Wird nicht verwendet.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame();
            final JPanel panel = new JPanel();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            panel.setBorder(new IconBorder(
                    new BallIcon(16, Color.RED, 1, Color.DARK_GRAY, 'x', Color.WHITE, new Font(Font.DIALOG, 0, 12),
                            true),
                    0,
                    BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                            BorderFactory.createLoweredBevelBorder())));

            frame.add(panel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
    }
}