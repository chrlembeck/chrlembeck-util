package de.chrlembeck.util.swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;

import de.chrlembeck.util.swing.SimpleDocumentListener;
import de.chrlembeck.util.swing.SwingUtil;
import de.chrlembeck.util.swing.action.DefaultAction;
import de.chrlembeck.util.swing.components.StrokeChooserDialog.FloatFormatter;
import de.chrlembeck.util.swing.formatter.BackgroundModifier;
import de.chrlembeck.util.swing.formatter.FormattedTextFieldVerifier;

/**
 * Dialog zur Auswahl einer Schriftart aus einer Liste aller installierten Schriftarten.
 *
 * @author Christoph Lembeck
 */
public final class FontChooserDialog extends JDialog {

    /**
     * Version number of the current class.
     * 
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = -8474070998135212792L;

    /**
     * Gibt Auskunft darüber, ob die DocumentListener für die Eingabfelder aktiv oder aufgrund von programmatischen
     * Änderungen an den Feldern ausgeschaltet sind.
     */
    private boolean listenerActive;

    /**
     * Label, in dem ein Beispieltext in der gewählten Schriftart angezeigt wird.
     */
    private JLabel resultLabel;

    /**
     * Hält fest, durch welche Aktion der Dialog geschlossen wurde.
     */
    private Action lastAction;

    /**
     * Liste der auswählbaren Schriftarten.
     */
    private JList<Font> nameList;

    /**
     * Liste der wählbaren Schriftstile.
     */
    private JList<StyleWrapper> styleList;

    /**
     * Eingabefeld für die gewünschte Schriftgröße.
     */
    private JFormattedTextField sizeField;

    /**
     * Eingabefeld für die Anzeige des gewählten Schriftstils.
     */
    private JTextField styleField;

    /**
     * Anzeigefeld für den gewählten Schriftnamen.
     */
    private JTextField nameField;

    /**
     * Auswahlfeld für einige Schriftgrößen.
     */
    private JList<SizeWrapper> sizeList;

    /**
     * Wird aufgerufen, wenn in den Eingabefeldern für Größe, Stil oder Schriftart eine Änderung vorgenommen wurde.
     * Aktualisiert darauf hin die Vorschau.
     * 
     * @param event
     *            Event aus dem Eingabefeld.
     */
    protected void updateFont(final DocumentEvent event) {
        final Font font = getSelectedFont();
        resultLabel.setFont(font);
        resultLabel.setText(font.getFamily());
        final float size = ((Float) sizeField.getValue()).floatValue();
        final ListModel<SizeWrapper> model = sizeList.getModel();
        final int selection = sizeList.getSelectedIndex();
        if (selection != -1) {
            sizeList.getSelectionModel().removeSelectionInterval(selection, selection);
        }
        for (int idx = 0; idx < model.getSize(); idx++) {
            if (model.getElementAt(idx).getSize() == size) {
                listenerActive = false;
                sizeList.setSelectedIndex(idx);
                sizeList.ensureIndexIsVisible(idx);
                listenerActive = true;
                break;
            }
        }
    }

    /**
     * CellRenderer zur Darstellung der Schriftart in der Auswahlliste.
     *
     * @author Christoph Lembeck
     */
    public class FontCellRenderer extends DefaultListCellRenderer {

        /**
         * Version number of the current class.
         * 
         * @see java.io.Serializable
         */
        private static final long serialVersionUID = 7636287864530018020L;

        /**
         * Erstellt einen neuen Renderer.
         */
        public FontCellRenderer() {
            setOpaque(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Component getListCellRendererComponent(final JList<?> list, final Object fontObject, final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            super.getListCellRendererComponent(list, fontObject, index, isSelected, cellHasFocus);
            final Font font = (Font) fontObject;
            setFont(font);
            setText(font.getFamily());
            return this;
        }
    }

    /**
     * Mögliche Entscheidungen, durch die ein Dialog geschlossen wurde.
     * 
     * @author Christoph Lembeck
     */
    enum Action {

        /**
         * Der Benutzer hat eine Auswahl getroffen.
         */
        OK,

        /**
         * Die Auswahl wurde abgebrochen.
         */
        CANCEL;
    }

    /**
     * Erstellt einen neuen Dialog mit dem übergebenen Dialog als parent.
     * 
     * @param owner
     *            Parent-Dialog, über dem dieser Dialog angezeigt werden soll.
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param initialFont
     *            Vorauswahl der Schriftart.
     * @param modalityType
     *            ModalityType mit dem der Dialog initialisiert werden soll.
     */
    public FontChooserDialog(final Dialog owner, final String dialogTitle, final Font initialFont,
            final ModalityType modalityType) {
        super(owner, dialogTitle, modalityType);
        init(initialFont);
    }

    /**
     * Erstellt einen neuen Dialog mit dem übergebenen Frame als parent.
     * 
     * @param owner
     *            Parent-Frame, über dem dieser Dialog angezeigt werden soll.
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param initialFont
     *            Vorauswahl der Schriftart.
     * @param modalityType
     *            ModalityType mit dem der Dialog initialisiert werden soll.
     */
    public FontChooserDialog(final Frame owner, final String dialogTitle, final Font initialFont,
            final ModalityType modalityType) {
        super(owner, dialogTitle, modalityType);
        init(initialFont);
    }

    /**
     * Erstellt einen neuen Dialog mit dem übergebenen Window als parent.
     * 
     * @param owner
     *            Parent-Window, über dem dieser Dialog angezeigt werden soll.
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param initialFont
     *            Vorauswahl der Schriftart.
     * @param modalityType
     *            ModalityType mit dem der Dialog initialisiert werden soll.
     */
    public FontChooserDialog(final Window owner, final String dialogTitle, final Font initialFont,
            final ModalityType modalityType) {
        super(owner, dialogTitle, modalityType);
        init(initialFont);
    }

    /**
     * Erstellt einen neuen Dialog.
     * 
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param initialFont
     *            Vorauswahl der Schriftart.
     */
    public FontChooserDialog(final String dialogTitle, final Font initialFont) {
        super();
        setTitle(dialogTitle);
        init(initialFont);
    }

    /**
     * Erstellt und initialisiert die in dem Dialog enthaltenen GUI-Elemente.
     * 
     * @param initialFont
     *            Schriftart, welche als Vorauswahl in dem Dialog angezeigt werden soll.
     */
    private final void init(final Font initialFont) {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent event) {
                cancelDialog();
            }
        });
        setLayout(new BorderLayout());
        final JPanel buttonPanel = new JPanel();
        final JPanel mainPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(new JButton(new DefaultAction("Abbrechen", null, null, null, null, null, null, null,
                (event) -> cancelDialog())));
        buttonPanel
                .add(new JButton(
                        new DefaultAction("OK", null, null, null, null, null, null, null, (event) -> okDialog())));
        mainPanel.setLayout(new GridBagLayout());

        final JLabel nameLabel = new JLabel("Schritart");
        final JLabel sizeLabel = new JLabel("Größe");
        final JLabel styleLabel = new JLabel("Stil");
        resultLabel = new JLabel(initialFont.getFamily());
        resultLabel.setFont(initialFont);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameField = new JTextField();
        nameField.setEditable(false);
        nameField.setFocusable(false);
        styleField = new JTextField();
        styleField.setEditable(false);
        styleField.setFocusable(false);
        final FloatFormatter sizeFormatter = new StrokeChooserDialog.FloatFormatter(6, 128);
        sizeField = new JFormattedTextField(sizeFormatter);
        sizeField.setInputVerifier(new FormattedTextFieldVerifier(new BackgroundModifier(new Color(255, 255, 127))));
        final GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final String[] fontFamilies = localGraphicsEnvironment.getAvailableFontFamilyNames();
        Arrays.parallelSort(fontFamilies);
        @SuppressWarnings("PMD.UseArrayListInsteadOfVector")
        final Vector<Font> fonts = new Vector<>(fontFamilies.length);
        for (final String family : fontFamilies) {
            final Font font = new Font(family, Font.PLAIN, 12);
            if (font.canDisplayUpTo(family) == -1) {
                fonts.add(font);
            }
        }
        nameList = new JList<Font>(fonts);
        sizeList = new JList<SizeWrapper>(new SizeWrapper[] { new SizeWrapper(8), new SizeWrapper(9),
                new SizeWrapper(10),
                new SizeWrapper(11), new SizeWrapper(12), new SizeWrapper(14), new SizeWrapper(16), new SizeWrapper(18),
                new SizeWrapper(20), new SizeWrapper(22), new SizeWrapper(24), new SizeWrapper(28), new SizeWrapper(32),
                new SizeWrapper(36), new SizeWrapper(40), new SizeWrapper(48), new SizeWrapper(56), new SizeWrapper(64),
                new SizeWrapper(80), new SizeWrapper(96) });
        styleList = new JList<StyleWrapper>(StyleWrapper.getStyles());
        final JScrollPane fontScrollPane = new JScrollPane(nameList);
        final JScrollPane sizeScrollPane = new JScrollPane(sizeList);
        nameList.addListSelectionListener(selectionEvent -> {
            if (nameList.getSelectedIndex() >= 0) {
                nameField.setText(nameList.getSelectedValue().getFamily());
            }
        });
        sizeList.addListSelectionListener(selectionEvent -> {
            if (sizeList.getSelectedIndex() >= 0 && listenerActive) {
                sizeField.setValue(sizeList.getSelectedValue().getSize());
            }
        });
        styleList.addListSelectionListener(selectionEvent -> {
            if (styleList.getSelectedIndex() >= 0) {
                styleField.setText(styleList.getSelectedValue().toString());
            }
        });
        sizeField.setValue(Float.valueOf(initialFont.getSize2D()));
        styleList.setSelectedValue(new StyleWrapper(initialFont.getStyle()), false);
        nameField.setText(initialFont.getFamily());
        int maxHeight = 0;
        for (final Font font : fonts) {
            if (font.getFamily().equals(initialFont.getFamily())) {
                nameList.setSelectedValue(font, true);
            }
            final JLabel label = new JLabel(font.getFamily());
            label.setFont(font);
            maxHeight = Math.max(maxHeight, label.getPreferredSize().height);
        }
        nameList.setCellRenderer(new FontCellRenderer());
        nameList.setFixedCellHeight(maxHeight);
        nameField.getDocument().addDocumentListener(new SimpleDocumentListener(this::updateFont));
        sizeField.getDocument().addDocumentListener(new SimpleDocumentListener(this::updateFont));
        styleField.getDocument().addDocumentListener(new SimpleDocumentListener(this::updateFont));
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.WHITE);
        final JScrollPane resultPane = new JScrollPane(resultLabel);
        resultPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Beispiel"),
                BorderFactory.createLoweredBevelBorder()));
        styleList.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.DARK_GRAY));
        fontScrollPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.DARK_GRAY));
        sizeScrollPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.DARK_GRAY));
        sizeField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        styleField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        nameField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        mainPanel.add(nameLabel,
                new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                        new Insets(15, 20, 4, 10), 0, 0));
        mainPanel.add(nameField,
                new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                        new Insets(4, 20, 0, 10), 0, 0));
        mainPanel.add(fontScrollPane, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 20, 4, 10), 0, 0));

        mainPanel.add(styleLabel, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(15, 10, 4, 10), 0, 0));
        mainPanel.add(styleField, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(4, 10, 0, 10), 0, 0));
        mainPanel.add(styleList,
                new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                        new Insets(0, 10, 4, 10), 0, 0));

        mainPanel.add(sizeLabel,
                new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                        new Insets(15, 10, 4, 20), 0, 0));
        mainPanel.add(sizeField,
                new GridBagConstraints(2, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                        new Insets(4, 10, 0, 20), 0, 0));
        mainPanel.add(sizeScrollPane, new GridBagConstraints(2, 2, 1, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 10, 4, 20), 0, 0));
        mainPanel.add(resultPane, new GridBagConstraints(0, 3, GridBagConstraints.REMAINDER, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 20, 4, 20), 0, 0));
        listenerActive = true;
        pack();
    }

    /**
     * Schließt den Dialog und hält fest, dass die Auswahl abgebrochen wurde.
     */
    protected void cancelDialog() {
        lastAction = Action.CANCEL;
        dispose();
    }

    /**
     * Schließt den Dialog und hält fest, dass der Benutzer sich für eine Auswahl entschieden hat.
     */
    protected void okDialog() {
        lastAction = Action.OK;
        dispose();
    }

    /**
     * Dient der formatierten Ausgabe der Schriftgröße innerhalb des Auswahldialogs.
     *
     * @author Christoph Lembeck
     */
    static class SizeWrapper {

        /**
         * Anzuzeigende Schriftgröße in PT.
         */
        private final float size;

        /**
         * Zur Darstellung verwendetes Zahlenformat.
         */
        private final NumberFormat numberFormat;

        /**
         * Erstellt den Wrapper für die übergbene Schriftgröße.
         * 
         * @param size
         *            Schriftgröße in PT.
         */
        public SizeWrapper(final float size) {
            this.size = size;
            numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
            numberFormat.setMinimumFractionDigits(0);
            numberFormat.setMaximumFractionDigits(1);
        }

        /**
         * Gibt die enthaltene Schriftgröße zurück.
         * 
         * @return Enthaltene Schriftgröße.
         */
        public float getSize() {
            return size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return numberFormat.format(size);
        }
    }

    /**
     * Dient der texttuellen Darstellung des Schriftschnitts und -stils.
     *
     * @author Christoph Lembeck
     */
    static class StyleWrapper {

        /**
         * Anzuzeigender Schriftstil, wie er im Font-Objekt verwendet wird.
         */
        private int style;

        /**
         * Erstellt einen neuen Wrapper zur Anzeige des Schriftstils.
         * 
         * @param style
         *            Stil aus dem Font-Objekt.
         */
        StyleWrapper(final int style) {
            this.style = style;
        }

        /**
         * Gibt ein Array mit allen verfügbaren Schriftstilen zurück.
         * 
         * @return Array mit den Schriftstilen Normal, Kuriv, Fett und Kursiv und Fett.
         */
        public static StyleWrapper[] getStyles() {
            return new StyleWrapper[] { new StyleWrapper(Font.PLAIN), new StyleWrapper(Font.ITALIC),
                    new StyleWrapper(Font.BOLD), new StyleWrapper(Font.BOLD + Font.ITALIC) };
        }

        /**
         * Gbit den enthaltenen Schriftstil als int zurück.
         * 
         * @return Schriftstil als int.
         * @see Font#getStyle()
         */
        public int getStyle() {
            return style;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            switch (style) {
                case Font.PLAIN:
                    return "Normal";
                case Font.BOLD:
                    return "Fett";
                case Font.ITALIC:
                    return "Kursiv";
                case Font.BOLD + Font.ITALIC:
                    return "Fett+Kursiv";
                default:
                    return Integer.toString(style);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + style;
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final StyleWrapper other = (StyleWrapper) obj;
            return style == other.style;
        }
    }

    /**
     * Erstellt und öffnet einen Modaldialog zur Auswahl einer Schriftart.
     * 
     * @param owner
     *            Komponente, über der der Dailog dargestellt werden soll.
     * @param dialogTitle
     *            Im Dialog anzuzeigender Dialogtitel.
     * @param initialFont
     *            Initiale Auswahl der Schriftart inklusive Schriftstil.
     * @return Vom Benutzer gewählte Schriftart oder null falls die Auswahl abgebrochen wurde.
     */
    public static Font openFontDialog(final Component owner, final String dialogTitle, final Font initialFont) {
        final Component root = SwingUtilities.getRoot(owner);
        FontChooserDialog fontDialog;
        if (root instanceof Dialog) {
            fontDialog = new FontChooserDialog((Dialog) root, dialogTitle, initialFont, ModalityType.APPLICATION_MODAL);
            SwingUtil.centerToParent(fontDialog, (Dialog) root);
        } else if (root instanceof Frame) {
            fontDialog = new FontChooserDialog((Frame) root, dialogTitle, initialFont, ModalityType.APPLICATION_MODAL);
            SwingUtil.centerToParent(fontDialog, (Frame) root);
        } else if (root instanceof Window) {
            fontDialog = new FontChooserDialog((Window) root, dialogTitle, initialFont, ModalityType.APPLICATION_MODAL);
            SwingUtil.centerToParent(fontDialog, (Window) root);
        } else {
            fontDialog = new FontChooserDialog(dialogTitle, initialFont);
            fontDialog.setModalityType(ModalityType.APPLICATION_MODAL);
            SwingUtil.centerToScreen(fontDialog);
        }
        fontDialog.setVisible(true);
        return fontDialog.lastAction == Action.OK ? fontDialog.getSelectedFont() : null;
    }

    /**
     * Gibt die aktuell ausgewählte Schriftart zurück.
     * 
     * @return Schriftart und Stil der aktuellen Auswahl.
     */
    public Font getSelectedFont() {
        final Font font = nameList.getSelectedValue();
        final int style = styleList.getSelectedValue().getStyle();
        final float size = ((Float) sizeField.getValue()).floatValue();
        final Font result = font.deriveFont(style, size);
        return result;
    }

    /**
     * Dient dem Testen der Klasse.
     * 
     * @param args
     *            Wird nicht verwendet.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println(openFontDialog(null, "Schriftart", new Font(Font.SERIF, Font.ITALIC, 36)));
        });
    }
}