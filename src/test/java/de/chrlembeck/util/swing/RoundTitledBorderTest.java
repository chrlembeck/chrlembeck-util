package de.chrlembeck.util.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import de.chrlembeck.util.swing.border.RoundTitledBorder;

/**
 * Test zum RoundTitledBorder
 *
 * @author Christoph Lembeck
 */
@SuppressWarnings("PMD.UseUtilityClass")
@RunWith(JUnitPlatform.class)
public class RoundTitledBorderTest {

    /**
     * MouseListener, der prüft, ob ein Mausklick innerhalb des Titels des Rahmens stattgefunden hat oder außerhalb.
     *
     * @author Christoph Lembeck
     */
    static class MyMouseAdapter extends MouseAdapter {

        /**
         * Border, dessen Titel auf Mausklicks überprüft werden soll.
         */
        private RoundTitledBorder border;

        /**
         * Erstellt den Listener für den Border.
         *
         * @param border
         *            Border, dessen Titel auf Mausklicks überprüft werden soll.
         */
        public MyMouseAdapter(final RoundTitledBorder border) {
            this.border = border;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mousePressed(final MouseEvent event) {
            final Object source = event.getSource();
            if (source instanceof JComponent) {
                final JComponent comp = (JComponent) source;
                System.out.println(border.isInTitle(comp, event.getPoint()));
            }
        }
    }

    /**
     * Erstellt einem Frame mit ein paar Test-Rahmen.
     *
     * @param args
     *            Wird nicht verwendet.
     */
    public static void main(final String[] args) {
        SwingUtilities
        .invokeLater(() -> {
            final JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            final JPanel panel = new JPanel();
            panel.setOpaque(true);
            panel.setBackground(new Color(236, 235, 174));
            panel.setLayout(new BorderLayout());
            final JLabel label = new JLabel("TestLabel");
            final RoundTitledBorder border1 = new RoundTitledBorder(Color.BLACK, 1, 10, "Test1", Color.BLUE,
                    new Color(255, 255, 144),
                    new Font(
                            Font.SANS_SERIF, Font.PLAIN, 12));
            label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
                    border1));
            final RoundTitledBorder border2 = new RoundTitledBorder(Color.BLACK, 1, 10, "Test2", Color.BLUE,
                    new Color(255, 255, 144), new Font(
                            Font.SANS_SERIF, Font.PLAIN, 12));
            panel.setBorder(border2);
            label.setBackground(new Color(246, 245, 255));
            label.setOpaque(true);
            frame.getContentPane().setBackground(new Color(234, 255, 205));
            panel.add(label, BorderLayout.CENTER);
            frame.add(panel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
            label.addMouseListener(new MyMouseAdapter(border1));
            panel.addMouseListener(new MyMouseAdapter(border2));
        });
    }

}
