package de.chrlembeck.util.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import de.chrlembeck.util.swing.icon.BallIcon;
import de.chrlembeck.util.swing.icon.PaintedIcons;

/**
 * Tests zu den Icons
 * 
 * @author Christoph Lembeck
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class IconTest {

    /**
     * Erstellt ein Fenster, in dem verschiedene Icons dargestellt werden.
     * 
     * @param args
     *            Wird nicht verwendet.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            final JPanel panel = new JPanel();
            frame.add(panel, BorderLayout.CENTER);
            panel.setLayout(new FlowLayout());
            final Font font = new Font(Font.DIALOG, Font.BOLD, 12);
            final float border = 0;
            addIcon(panel, new BallIcon(16, Color.RED, border, Color.DARK_GRAY, 'x', Color.WHITE, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.GREEN, border, Color.DARK_GRAY, 'Â', Color.BLACK, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.BLUE, border, Color.DARK_GRAY, 'y', Color.WHITE, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.YELLOW, border, Color.DARK_GRAY, '€', Color.BLACK, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.PINK, border, Color.DARK_GRAY, 'Ö', Color.BLACK, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.MAGENTA, border, Color.DARK_GRAY, 'g', Color.BLACK, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.BLACK, border, Color.DARK_GRAY, '8', Color.WHITE, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.WHITE, border, Color.DARK_GRAY, '0', Color.BLACK, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.LIGHT_GRAY, border, Color.DARK_GRAY, '1', Color.BLACK, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.GRAY, border, Color.DARK_GRAY, 'M', Color.WHITE, font, true),
                    "BallIcon(16)");
            addIcon(panel, new BallIcon(16, Color.DARK_GRAY, border, Color.DARK_GRAY, 'i', Color.WHITE, font, true),
                    "BallIcon(16)");
            addIcon(panel, PaintedIcons.createBall(16, Color.RED), "RedBall(16)");
            addIcon(panel, PaintedIcons.createZoom(16, Color.BLACK, 1.5f, Color.WHITE), "Zoom16");
            addIcon(panel, PaintedIcons.createZoom(24, Color.DARK_GRAY, 2, Color.WHITE), "Zoom24");
            addIcon(panel, PaintedIcons.createZoom(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE), "Zoom32");
            addIcon(panel, PaintedIcons.createZoomMinus(16, Color.BLACK, 1.5f, Color.WHITE, Color.BLACK, 1),
                    "ZoomMinus");
            addIcon(panel, PaintedIcons.createZoomMinus(24, Color.DARK_GRAY, 2, Color.WHITE, Color.RED, 1.5f),
                    "ZoomMinus");
            addIcon(panel, PaintedIcons.createZoomMinus(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE, Color.RED, 2f),
                    "ZoomMinus");
            addIcon(panel, PaintedIcons.createZoomPlus(16, Color.BLACK, 1.5f, Color.WHITE, Color.BLACK, 1), "ZoomPlus");
            addIcon(panel, PaintedIcons.createZoomPlus(24, Color.DARK_GRAY, 2, Color.WHITE, Color.GREEN, 1.5f),
                    "ZoomPlus");
            addIcon(panel, PaintedIcons.createZoomPlus(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE, Color.GREEN, 2f),
                    "ZoomPlus");
            addIcon(panel, PaintedIcons.createZoomOriginal(16, Color.BLACK, 1.5f, Color.WHITE, Color.BLACK, 1),
                    "ZoomOriginal");
            addIcon(panel, PaintedIcons.createZoomOriginal(24, Color.DARK_GRAY, 2, Color.WHITE, Color.LIGHT_GRAY, 1.5f),
                    "ZoomOriginal");
            addIcon(panel,
                    PaintedIcons.createZoomOriginal(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE, Color.LIGHT_GRAY, 2f),
                    "ZoomOriginal");
            addIcon(panel, PaintedIcons.createZoomFit(16, Color.BLACK, 1.5f, Color.WHITE, Color.BLACK, 1), "ZoomFit");
            addIcon(panel, PaintedIcons.createZoomFit(24, Color.DARK_GRAY, 2, Color.WHITE, Color.GREEN, 1.5f),
                    "ZoomFit");
            addIcon(panel, PaintedIcons.createZoomFit(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE, Color.GREEN, 2f),
                    "ZoomFit");
            addIcon(panel, PaintedIcons.createNavigateSectionStart(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY), "NavigateFirst");
            addIcon(panel, PaintedIcons.createNavigateBackward(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIcons.createNavigateForward(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIcons.createNavigateSectionEnd(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY), "NavigateLast");
            addIcon(panel, PaintedIcons.createNavigateSectionStart(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateFirst");
            addIcon(panel, PaintedIcons.createNavigateBackward(24, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIcons.createNavigateForward(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIcons.createNavigateSectionEnd(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateLast");
            addIcon(panel, PaintedIcons.createNavigateSectionStart(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateFirst");
            addIcon(panel, PaintedIcons.createNavigateBackward(32, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIcons.createNavigateForward(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIcons.createNavigateSectionEnd(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateLast");
            addIcon(panel, PaintedIcons.createNavigateDocumentStart(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigateFirst");
            addIcon(panel, PaintedIcons.createNavigateFastBackward(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIcons.createNavigateNextDouble(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigateNext");
            addIcon(panel, PaintedIcons.createNavigateDocumentEnd(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigateLast");
            addIcon(panel, PaintedIcons.createNavigateDocumentStart(24, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigateFirst");
            addIcon(panel, PaintedIcons.createNavigateFastBackward(24, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIcons.createNavigateNextDouble(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIcons.createNavigateDocumentEnd(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateLast");
            addIcon(panel, PaintedIcons.createNavigateDocumentStart(32, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigateFirst");
            addIcon(panel, PaintedIcons.createNavigateFastBackward(32, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIcons.createNavigateNextDouble(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIcons.createNavigateDocumentEnd(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateLast");
            frame.pack();
            frame.setVisible(true);
        });
    }

    /**
     * Fügt dem Panel ein Icon mit Beschreibungstext hinzu.
     * 
     * @param panel
     *            Panel, auf dem das Icon dargestellt wird.
     * @param icon
     *            Icon, welches gemalt werden soll.
     * @param text
     *            Text, der neben dem Icon angezeigt werden soll.
     */
    private static void addIcon(final JPanel panel, final Icon icon, final String text) {
        final JLabel label = new JLabel(text, icon, SwingConstants.LEFT);
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        panel.add(label);
    }
}