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
import de.chrlembeck.util.swing.icon.PaintedIconsHelper;

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
            addIcon(panel, PaintedIconsHelper.createBall(16, Color.RED), "RedBall(16)");
            addIcon(panel, PaintedIconsHelper.createZoom(16, Color.BLACK, 1.5f, Color.WHITE), "Zoom16");
            addIcon(panel, PaintedIconsHelper.createZoom(24, Color.DARK_GRAY, 2, Color.WHITE), "Zoom24");
            addIcon(panel, PaintedIconsHelper.createZoom(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE), "Zoom32");
            addIcon(panel, PaintedIconsHelper.createZoomMinus(16, Color.BLACK, 1.5f, Color.WHITE, Color.BLACK, 1),
                    "ZoomMinus");
            addIcon(panel, PaintedIconsHelper.createZoomMinus(24, Color.DARK_GRAY, 2, Color.WHITE, Color.RED, 1.5f),
                    "ZoomMinus");
            addIcon(panel, PaintedIconsHelper.createZoomMinus(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE, Color.RED, 2f),
                    "ZoomMinus");
            addIcon(panel, PaintedIconsHelper.createZoomPlus(16, Color.BLACK, 1.5f, Color.WHITE, Color.BLACK, 1), "ZoomPlus");
            addIcon(panel, PaintedIconsHelper.createZoomPlus(24, Color.DARK_GRAY, 2, Color.WHITE, Color.GREEN, 1.5f),
                    "ZoomPlus");
            addIcon(panel, PaintedIconsHelper.createZoomPlus(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE, Color.GREEN, 2f),
                    "ZoomPlus");
            addIcon(panel, PaintedIconsHelper.createZoomOriginal(16, Color.BLACK, 1.5f, Color.WHITE, Color.BLACK, 1),
                    "ZoomOriginal");
            addIcon(panel, PaintedIconsHelper.createZoomOriginal(24, Color.DARK_GRAY, 2, Color.WHITE, Color.LIGHT_GRAY, 1.5f),
                    "ZoomOriginal");
            addIcon(panel,
                    PaintedIconsHelper.createZoomOriginal(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE, Color.LIGHT_GRAY, 2f),
                    "ZoomOriginal");
            addIcon(panel, PaintedIconsHelper.createZoomFit(16, Color.BLACK, 1.5f, Color.WHITE, Color.BLACK, 1), "ZoomFit");
            addIcon(panel, PaintedIconsHelper.createZoomFit(24, Color.DARK_GRAY, 2, Color.WHITE, Color.GREEN, 1.5f),
                    "ZoomFit");
            addIcon(panel, PaintedIconsHelper.createZoomFit(32, Color.LIGHT_GRAY, 2.5f, Color.WHITE, Color.GREEN, 2f),
                    "ZoomFit");
            addIcon(panel, PaintedIconsHelper.createNavigateSectionStart(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY), "NavigateFirst");
            addIcon(panel, PaintedIconsHelper.createNavigateBackward(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIconsHelper.createNavigateForward(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIconsHelper.createNavigateSectionEnd(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY), "NavigateLast");
            addIcon(panel, PaintedIconsHelper.createNavigateSectionStart(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateFirst");
            addIcon(panel, PaintedIconsHelper.createNavigateBackward(24, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIconsHelper.createNavigateForward(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIconsHelper.createNavigateSectionEnd(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateLast");
            addIcon(panel, PaintedIconsHelper.createNavigateSectionStart(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateFirst");
            addIcon(panel, PaintedIconsHelper.createNavigateBackward(32, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIconsHelper.createNavigateForward(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIconsHelper.createNavigateSectionEnd(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateLast");
            addIcon(panel, PaintedIconsHelper.createNavigateDocumentStart(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigateFirst");
            addIcon(panel, PaintedIconsHelper.createNavigateFastBackward(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIconsHelper.createNavigateNextDouble(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigateNext");
            addIcon(panel, PaintedIconsHelper.createNavigateDocumentEnd(16, Color.BLACK, 1.5f, Color.LIGHT_GRAY),
                    "NavigateLast");
            addIcon(panel, PaintedIconsHelper.createNavigateDocumentStart(24, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigateFirst");
            addIcon(panel, PaintedIconsHelper.createNavigateFastBackward(24, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIconsHelper.createNavigateNextDouble(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIconsHelper.createNavigateDocumentEnd(24, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateLast");
            addIcon(panel, PaintedIconsHelper.createNavigateDocumentStart(32, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigateFirst");
            addIcon(panel, PaintedIconsHelper.createNavigateFastBackward(32, Color.BLACK, 2, Color.LIGHT_GRAY),
                    "NavigatePrevios");
            addIcon(panel, PaintedIconsHelper.createNavigateNextDouble(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateNext");
            addIcon(panel, PaintedIconsHelper.createNavigateDocumentEnd(32, Color.BLACK, 2, Color.LIGHT_GRAY), "NavigateLast");
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