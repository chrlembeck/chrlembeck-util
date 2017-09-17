package de.chrlembeck.util.swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import de.chrlembeck.util.swing.SwingUtil;
import de.chrlembeck.util.swing.action.DefaultAction;

/**
 * Swing-Dialog zur Auswahl einer Farbe anhand des HSV-Farbsystems.
 * 
 * @author Christoph Lembeck
 */
public class ColorChooserDialog extends JDialog {

    /**
     * Version number of the current class.
     * 
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = -4003712731425962452L;

    /**
     * Mögliche Ergebnisse der Farbauswahl.
     * 
     * @author Christoph Lembeck
     */
    public enum Result {

        /**
         * Der Benutzer hat den Dialog mit dem OK-Button verlassen.
         */
        OK_BUTTON,

        /**
         * Der Benutzer hat die Farbauswahl abgebrochen.
         */
        CANCEL_BUTTON
    }

    /**
     * Hält die Aktion fest, die zum Beenden des Dialogs geführt hat.
     */
    protected Result lastButton;

    /**
     * Panel, in dem die eigentliche Farbauswahl vorgenommen wird.
     */
    private ColorChooserPanel colorPanel;

    /**
     * Erstellt einen neuen Farbauswahldialog ohne parent-Bezeihung.
     */
    public ColorChooserDialog() {
        super();
        init();
    }

    /**
     * Erstellt einen neuen Dialog mit dem übergebenen Frame als parent.
     * 
     * @param owner
     *            Parent-Objekt des Dialogs.
     */
    public ColorChooserDialog(final Frame owner) {
        super(owner);
        init();
    }

    /**
     * Erstellt einen neuen Dialog mit dem übergebenen Window als parent.
     * 
     * @param owner
     *            Parent-Objekt des Dialogs.
     */
    public ColorChooserDialog(final Window owner) {
        super(owner);
        init();
    }

    /**
     * Erstellt einen neuen Dialog mit dem übergebenen Dialog als parent.
     * 
     * @param owner
     *            Parent-Objekt des Dialogs.
     */
    public ColorChooserDialog(final Dialog owner) {
        super(owner);
        init();
    }

    /**
     * Erstellt und öffnet einen Farbauswahldialog über der angegebenen Komponenten.
     * 
     * @param owner
     *            Komponente, über der der Dialog positioniert werden soll.
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param preselection
     *            Vorauswahl für die Farbwahl.
     * @return Vom Benutzer ausgewählte Farbe oder null, falls die Auswahl abgebrochen wurde.
     */
    public static Color openColorChooser(final Component owner, final String dialogTitle, final Color preselection) {
        final Component root = SwingUtilities.getRoot(owner);
        ColorChooserDialog chooser;
        if (root instanceof Dialog) {
            chooser = new ColorChooserDialog((Dialog) root);
            SwingUtil.centerToParent(chooser, (Dialog) root);
        } else if (root instanceof Frame) {
            chooser = new ColorChooserDialog((Frame) root);
            SwingUtil.centerToParent(chooser, (Frame) root);
        } else if (root instanceof Window) {
            chooser = new ColorChooserDialog((Window) root);
            SwingUtil.centerToParent(chooser, (Window) root);
        } else {
            chooser = new ColorChooserDialog();
            SwingUtil.centerToScreen(chooser);
        }
        chooser.setModal(true);
        chooser.setSelectedColor(preselection);
        chooser.setTitle(dialogTitle);
        chooser.setVisible(true);
        return chooser.lastButton == Result.OK_BUTTON ? chooser.getSelectedColor() : null;
    }

    /**
     * Initialisiert die aktuell ausgewählte Farbe und das Feld zur Anzeige der Referenzfarbe mit dem übergebenen Wert.
     * 
     * @param preselection
     *            Farbe, die initial in der Auswahl und als Referenzfarbe angezeigt werden soll.
     */
    private void setSelectedColor(final Color preselection) {
        colorPanel.setSelectedColor(preselection);
        colorPanel.setReferenceColor(preselection);
    }

    /**
     * Gibt die aktuell ausgewählte Farbe zurück.
     * 
     * @return Aktuell ausgewählte Farbe.
     */
    private Color getSelectedColor() {
        return colorPanel.getSelectedColor();
    }

    /**
     * Initialisiert die Komponente und erstell alle enthaltenen GUI-Komponenten.
     */
    private void init() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent event) {
                lastButton = Result.CANCEL_BUTTON;
                dispose();
            }
        });
        setLayout(new BorderLayout());
        colorPanel = new ColorChooserPanel();
        add(colorPanel, BorderLayout.CENTER);
        final JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(new JButton(
                new DefaultAction("Abbrechen", null, null, null, null, null, null, null,
                        (actionEvent) -> cancelDialog())));
        buttonPanel
                .add(new JButton(
                        new DefaultAction("OK", null, null, null, null, null, null, null,
                                (actionEvent) -> okDialog())));
        pack();
    }

    /**
     * Dient zum Testen des Dialogs.
     * 
     * @param args
     *            Nicht verwendet.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            openColorChooser(null, "Farbauswahl", new Color(255, 0, 127, 127));
        });
    }

    /**
     * Schließt den Dialog und hält fest, dass die Farbauswahl vom Benutzer abgebrochen wurde.
     */
    private void cancelDialog() {
        lastButton = Result.CANCEL_BUTTON;
        dispose();
    }

    /**
     * Schließt den Dialog und hält fest, dass der Benutzer sich für eine Farbe entschieden hat.
     */
    private void okDialog() {
        lastButton = Result.OK_BUTTON;
        dispose();
    }
}