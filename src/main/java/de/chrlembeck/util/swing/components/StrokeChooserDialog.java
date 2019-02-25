package de.chrlembeck.util.swing.components;

import de.chrlembeck.util.lang.StringUtils;
import de.chrlembeck.util.swing.SimpleDocumentListener;
import de.chrlembeck.util.swing.SwingUtil;
import de.chrlembeck.util.swing.action.DefaultAction;
import de.chrlembeck.util.swing.formatter.BackgroundModifier;
import de.chrlembeck.util.swing.formatter.FormattedTextFieldVerifier;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Path2D;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentListener;

/**
 * dialog zur Auswahl eines Linienstils zum Zeichnen von Linien auf Graphics2D-Objekten. Aus der in diesem Dialog
 * getroffenen Auswahl kann ein Objekt des Type BasicStroke erstellt werden.
 *
 * @author Christoph Lembeck
 * @see BasicStroke
 */
@SuppressWarnings("PMD.GodClass")
public final class StrokeChooserDialog extends JDialog {

    /**
     * Version number of the current class.
     * 
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 7145312267107831807L;

    /**
     * Eingabefeld für die Definition der Linienbreite.
     */
    private transient JFormattedTextField widthField;

    /**
     * Eingabfeld für die Definition der Gehrungswinkel-Grenze.
     * 
     * @see BasicStroke#getMiterLimit()
     */
    private transient JFormattedTextField miterLimitField;

    /**
     * Auswahlschaltfläche für das stumpfe Beenden von Linien.
     */
    private transient JRadioButton capButtButton;

    /**
     * Auswahlschaltfläche für das abgerundete Beenden von Linien.
     */
    private transient JRadioButton capRoundButton;

    /**
     * Auswahlschaltfläche für das geradlinige verbinden von aneinanderstoßenden Liniensegmenten.
     */
    private transient JRadioButton joinMiterButton;

    /**
     * Auswahlschaltfläche für das abgerundete Zusammenfügen von Liniensegmenten.
     */
    private transient JRadioButton joinRoundButton;

    /**
     * Liste für die Auswahl einer Definition für die Art der gestrichelten Linienführung.
     */
    private transient JList<DashInfo> dashList;

    /**
     * Mögliche Ergebnisse der Linienstilauswahl.
     * 
     * @author Christoph Lembeck
     */
    public enum Action {

        /**
         * Der Benutzer hat den Dialog mit dem OK-Button verlassen.
         */
        OK,

        /**
         * Der Benutzer hat die Farbauswahl abgebrochen.
         */
        CANCEL
    }

    /**
     * Label, welches das Icon zur Darstellung der aktuellen Auswahl enthält.
     */
    private transient JLabel exampleLabel;

    /**
     * Hält fest, mit welcher Aktion der Dialog beendet wurde.
     */
    private Action lastAction;

    /**
     * Erstellt einen neuen Dialog mit dem übergebenen Dialog als parent.
     * 
     * @param owner
     *            Parent-Dialog, über dem dieser Dialog angezeigt werden soll.
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param initialStroke
     *            Vorauswahl des Linienstils.
     * @param modalityType
     *            ModalityType mit dem der Dialog initialisiert werden soll.
     */
    public StrokeChooserDialog(final Dialog owner, final String dialogTitle, final BasicStroke initialStroke,
            final ModalityType modalityType) {
        super(owner, dialogTitle, modalityType);
        init(initialStroke);
    }

    /**
     * Erstellt einen neuen Dialog mit dem übergebenen Frame als parent.
     * 
     * @param owner
     *            Parent-Frame, über dem dieser Dialog angezeigt werden soll.
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param initialStroke
     *            Vorauswahl des Linienstils.
     * @param modalityType
     *            ModalityType mit dem der Dialog initialisiert werden soll.
     */
    public StrokeChooserDialog(final Frame owner, final String dialogTitle, final BasicStroke initialStroke,
            final ModalityType modalityType) {
        super(owner, dialogTitle, modalityType);
        init(initialStroke);
    }

    /**
     * Erstellt einen neuen Dialog mit dem übergebenen Window als parent.
     * 
     * @param owner
     *            Parent-Window, über dem dieser Dialog angezeigt werden soll.
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param initialStroke
     *            Vorauswahl des Linienstils.
     * @param modalityType
     *            ModalityType mit dem der Dialog initialisiert werden soll.
     */
    public StrokeChooserDialog(final Window owner, final String dialogTitle, final BasicStroke initialStroke,
            final ModalityType modalityType) {
        super(owner, dialogTitle, modalityType);
        init(initialStroke);
    }

    /**
     * Erstellt einen neuen Dialog zur Auswahl eines Linienstils.
     * 
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param initialStroke
     *            Vorauswahl des Linienstils.
     */
    public StrokeChooserDialog(final String dialogTitle, final BasicStroke initialStroke) {
        super();
        setTitle(dialogTitle);
        init(initialStroke);
    }

    /**
     * Initialisiert den Dialog und erzeugt die darin enthaltenen GUI-Objekte.
     * 
     * @param initialStroke
     *            Linienzeichenstil, der als Vorauswahl dienen soll.
     */
    private final void init(final BasicStroke initialStroke) {
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
        buttonPanel.add(new JButton(
                new DefaultAction("Abbrechen", null, null, null, null, null, null, null, (event) -> cancelDialog())));
        buttonPanel
                .add(new JButton(
                        new DefaultAction("OK", null, null, null, null, null, null, null, (event) -> okDialog())));
        mainPanel.setLayout(new GridBagLayout());
        capRoundButton = new JRadioButton("Rund", initialStroke.getEndCap() == BasicStroke.CAP_ROUND);
        final JRadioButton capSquareButton = new JRadioButton("Quadratisch",
                initialStroke.getEndCap() == BasicStroke.CAP_SQUARE);
        capButtButton = new JRadioButton("Stumpf", initialStroke.getEndCap() == BasicStroke.CAP_BUTT);
        final ButtonGroup capGroup = new ButtonGroup();
        capGroup.add(capRoundButton);
        capGroup.add(capSquareButton);
        capGroup.add(capButtButton);
        final JPanel capPanel = new JPanel();
        capPanel.setLayout(new GridLayout(3, 1));
        capPanel.add(capRoundButton);
        capPanel.add(capSquareButton);
        capPanel.add(capButtButton);
        capPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                "Linienenden"));

        joinRoundButton = new JRadioButton("Rund", initialStroke.getLineJoin() == BasicStroke.JOIN_ROUND);
        final JRadioButton joinBevelButton = new JRadioButton("Abgeflacht",
                initialStroke.getLineJoin() == BasicStroke.JOIN_BEVEL);
        joinMiterButton = new JRadioButton("Gehrung", initialStroke.getLineJoin() == BasicStroke.JOIN_MITER);
        final ButtonGroup joinGroup = new ButtonGroup();
        joinGroup.add(joinRoundButton);
        joinGroup.add(joinBevelButton);
        joinGroup.add(joinMiterButton);
        final JPanel joinPanel = new JPanel();
        joinPanel.setLayout(new GridLayout(3, 1));
        joinPanel.add(joinRoundButton);
        joinPanel.add(joinBevelButton);
        joinPanel.add(joinMiterButton);
        joinPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                "Verbindung"));

        final JLabel widthLabel = new JLabel("Breite");
        final FloatFormatter widthFormatter = new FloatFormatter(0, 20);
        widthField = new JFormattedTextField(widthFormatter);
        widthField.setColumns(5);
        widthField.setInputVerifier(new FormattedTextFieldVerifier(new BackgroundModifier(new Color(255, 255, 127))));
        widthField.setValue(Float.valueOf(initialStroke.getLineWidth()));
        widthField.setHorizontalAlignment(SwingConstants.RIGHT);

        final JLabel miterLimitLabel = new JLabel("Gehrungsgrenze");
        final FloatFormatter miterLmitFormatter = new FloatFormatter(1f, 100f);
        miterLimitField = new JFormattedTextField(miterLmitFormatter);
        miterLimitField.setColumns(5);
        miterLimitField
                .setInputVerifier(new FormattedTextFieldVerifier(new BackgroundModifier(new Color(255, 255, 127))));
        miterLimitField.setValue(Float.valueOf(initialStroke.getMiterLimit()));
        miterLimitField.setHorizontalAlignment(SwingConstants.RIGHT);

        @SuppressWarnings("PMD.UseArrayListInsteadOfVector")
        final Vector<DashInfo> dashes = new Vector<>();
        dashes.add(new DashInfo(null, 0));
        dashes.add(new DashInfo(new float[] { 1, 1 }, 0));
        dashes.add(new DashInfo(new float[] { 2, 2 }, 0));
        dashes.add(new DashInfo(new float[] { 2, 3 }, 0));
        dashes.add(new DashInfo(new float[] { 3, 3 }, 0));
        dashes.add(new DashInfo(new float[] { 5, 5 }, 0));
        dashes.add(new DashInfo(new float[] { 5, 5, 1, 5 }, 0));
        dashes.add(new DashInfo(new float[] { 3, 3, 10, 3 }, 0));
        dashes.add(new DashInfo(new float[] { 10, 10 }, 0));
        final DashInfo currentDash = new DashInfo(initialStroke.getDashArray(), initialStroke.getDashPhase());
        if (!dashes.contains(currentDash)) {
            dashes.add(currentDash);
        }
        dashList = new JList<DashInfo>(dashes);
        dashList.setSelectedValue(currentDash, true);
        dashList.setCellRenderer(new DashCellRenderer());
        dashList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane dashScrollPane = new JScrollPane(dashList);
        dashScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        exampleLabel = new JLabel(createIcon());
        exampleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Beispiel"),
                BorderFactory.createEmptyBorder(0, 20, 0, 20)));

        mainPanel.add(capPanel, new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
        mainPanel.add(joinPanel, new GridBagConstraints(0, 1, 2, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
        mainPanel.add(widthLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        mainPanel.add(widthField, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        mainPanel.add(miterLimitLabel, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        mainPanel.add(miterLimitField, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        mainPanel.add(exampleLabel, new GridBagConstraints(2, 0, 1, GridBagConstraints.REMAINDER, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
        mainPanel.add(dashScrollPane, new GridBagConstraints(0, 4, 2, 1, 0, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));

        capRoundButton.addActionListener(actionEvent -> selectionChanged());
        capButtButton.addActionListener(actionEvent -> selectionChanged());
        capSquareButton.addActionListener(actionEvent -> selectionChanged());
        joinBevelButton.addActionListener(actionEvent -> selectionChanged());
        joinMiterButton.addActionListener(actionEvent -> selectionChanged());
        joinRoundButton.addActionListener(actionEvent -> selectionChanged());
        dashList.addListSelectionListener(actionEvent -> selectionChanged());
        widthField.addActionListener(actionEvent -> selectionChanged());
        miterLimitField.addActionListener(actionEvent -> selectionChanged());
        final DocumentListener documentChangeListener = new SimpleDocumentListener(docEvent -> selectionChanged());
        miterLimitField.getDocument().addDocumentListener(documentChangeListener);
        widthField.getDocument().addDocumentListener(documentChangeListener);
        pack();
    }

    /**
     * Wird aufgerufen, wenn der Benutzer ein Änderung an der Zeichendefinition vorgenommen hat. Das Beispielicon wird
     * dann aktualisiert.
     */
    protected void selectionChanged() {
        exampleLabel.setIcon(createIcon());
    }

    /**
     * Erstellt ein Icon zur Demonstration der aktuellen Linienwahl.
     * 
     * @return Icon zur Darstellung der aktuellen Linenwahl.
     */
    private Icon createIcon() {
        return new ExampleIcon(getSelectedStroke());
    }

    /**
     * Formatter für die Ein- und Ausgabe von Fließkommazahlen in Swing-Textkomponenten mit der Validierung der
     * eingegebenen Werte zwischen festlegbaren Grenzen.
     *
     * @author Christoph Lembeck
     */
    static class FloatFormatter extends AbstractFormatter {

        /**
         * Version number of the current class.
         * 
         * @see java.io.Serializable
         */
        private static final long serialVersionUID = -2787329722522632193L;

        /**
         * Minimalwert für gültige Eingaben.
         */
        private final float min;

        /**
         * Maximalwert für gültige Eingaben.
         */
        private final float max;

        /**
         * Zahlenformat zur Ausgabe der Zahlen.
         */
        private final NumberFormat format;

        /**
         * Erstellt einen neuen Formatter mit der angegebenen Unter- und Obergrenze für gültige Eingaben.
         * 
         * @param min
         *            Minimalwert für gültige Eingaben.
         * @param max
         *            Maximalwert für gültige Eingaben.
         */
        public FloatFormatter(final float min, final float max) {
            this.min = min;
            this.max = max;
            format = NumberFormat.getInstance(Locale.GERMANY);
            format.setGroupingUsed(false);
            format.setMinimumFractionDigits(0);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("PMD.PreserveStackTrace")
        public Object stringToValue(final String text) throws ParseException {
            if (StringUtils.isEmpty(text)) {
                throw new ParseException("no input", 0);
            }
            try {
                final Float value = Float.valueOf(text.replace(',', '.'));
                if (value < min || value > max) {
                    throw new ParseException("out of range", 0);
                }
                return value;
            } catch (final NumberFormatException nfe) {
                throw new ParseException(nfe.getLocalizedMessage(), 0);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String valueToString(final Object value) throws ParseException {
            if (value == null) {
                return "";
            }
            return format.format(value);
        }
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
     * Erstellt und öffnet einen Dialog zur Auswahl des Linienstils und gibt die vom Benutzer getroffene Auswahl als
     * Ergebnis zurück.
     * 
     * @param owner
     *            Komponente, über der der Dialog dargestellt werden soll.
     * @param dialogTitle
     *            Titel des Dialogs.
     * @param initialStroke
     *            Vorbelegung der Linienstilauswahl.
     * @return Vom Benutzer getroffene Auswahl oder null, falls die Auswahl abgebrochen wurde.
     */
    public static BasicStroke openStrokeDialog(final Component owner, final String dialogTitle,
            final BasicStroke initialStroke) {
        final Component root = SwingUtilities.getRoot(owner);
        StrokeChooserDialog strokeDialog;
        if (root instanceof Dialog) {
            strokeDialog = new StrokeChooserDialog((Dialog) root, dialogTitle, initialStroke,
                    ModalityType.APPLICATION_MODAL);
            SwingUtil.centerToParent(strokeDialog, (Dialog) root);
        } else if (root instanceof Frame) {
            strokeDialog = new StrokeChooserDialog((Frame) root, dialogTitle, initialStroke,
                    ModalityType.APPLICATION_MODAL);
            SwingUtil.centerToParent(strokeDialog, (Frame) root);
        } else if (root instanceof Window) {
            strokeDialog = new StrokeChooserDialog((Window) root, dialogTitle, initialStroke,
                    ModalityType.APPLICATION_MODAL);
            SwingUtil.centerToParent(strokeDialog, (Window) root);
        } else {
            strokeDialog = new StrokeChooserDialog(dialogTitle, initialStroke);
            strokeDialog.setModalityType(ModalityType.APPLICATION_MODAL);
            SwingUtil.centerToScreen(strokeDialog);
        }
        strokeDialog.setVisible(true);
        return strokeDialog.lastAction == Action.OK ? strokeDialog.getSelectedStroke() : null;
    }

    /**
     * Hilfsobjekt zur Speicherung von Optionen für das Zeichnen von gestruchelten Linien (Morsezeichen).
     *
     * @author Christoph Lembeck
     */
    static class DashInfo {

        /**
         * Offset, mit dem das Zeichnen der gestrichelten Linie beginnt.
         * 
         * @see BasicStroke#getDashPhase()
         */
        private float dashPhase;

        /**
         * Längendefinitionen für das Muster ger gestrichelten Linie.
         * 
         * @see BasicStroke#getDashArray()
         */
        private float[] dash;

        /**
         * Erstellt ein neues Objekt mit der Definition des Linienmusters.
         * 
         * @param dash
         *            Längendefinitionen für das Muster ger gestrichelten Linie.
         * @param dashPhase
         *            Offset, mit dem das Zeichnen der gestrichelten Linie beginnt.
         */
        public DashInfo(final float[] dash, final float dashPhase) {
            this.dash = dash == null ? null : dash.clone();
            this.dashPhase = dashPhase;
        }

        /**
         * Gibt die Längendefinitionen für das Muster ger gestrichelten Linie zurück.
         * 
         * @return Längendefinitionen für das Muster ger gestrichelten Linie.
         * @see BasicStroke#getDashArray()
         */
        @SuppressWarnings("PMD.MethodReturnsInternalArray")
        public float[] getDash() {
            return dash;
        }

        /**
         * Gibt den Offset, mit dem das Zeichnen der gestrichelten Linie beginnt, zurück.
         * 
         * @return Offset, mit dem das Zeichnen der gestrichelten Linie beginnt.
         * @see BasicStroke#getDashPhase()
         */
        public float getDashPhase() {
            return dashPhase;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(dash);
            result = prime * result + Float.floatToIntBits(dashPhase);
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
            final DashInfo other = (DashInfo) obj;
            if (!Arrays.equals(dash, other.dash)) {
                return false;
            }
            return Float.floatToIntBits(dashPhase) == Float.floatToIntBits(other.dashPhase);
        }
    }

    /**
     * Liest die Auswahl den Benutzers aus und erzeugt daraus ein entsprechendes Stroke-Objekt.
     * 
     * @return Auswahl des Linienstils, wie der Benutzer sie im Dialog getroffen hat.
     */
    public BasicStroke getSelectedStroke() {
        final float width = ((Float) widthField.getValue()).floatValue();
        final int cap = capRoundButton.isSelected() ? BasicStroke.CAP_ROUND
                : capButtButton.isSelected() ? BasicStroke.CAP_BUTT
                        : BasicStroke.CAP_SQUARE;
        final int join = joinMiterButton.isSelected() ? BasicStroke.JOIN_MITER
                : joinRoundButton.isSelected() ? BasicStroke.JOIN_ROUND : BasicStroke.JOIN_BEVEL;
        final float miterLimit = ((Float) miterLimitField.getValue()).floatValue();
        final DashInfo dash = dashList.getSelectedValue();

        return new BasicStroke(width, cap, join, miterLimit, dash == null ? null : multiply(dash.getDash(), width),
                dash == null ? 0
                        : dash.getDashPhase() * width);
    }

    /**
     * Multiplizier alle Elemente des Arrays mit dem übergebenen Faktor und gibt die Ergebnisse als neues Array zurück.
     * 
     * @param array
     *            Array, dessen Werte mit dem Faktor multipliziert werden sollen.
     * @param factor
     *            Faktor, mit dem die Werte mutlipliziert werden sollen.
     * @return Neues Array mit den multipliziertwn Werten.
     */
    private float[] multiply(final float[] array, final float factor) {
        if (array == null) {
            return null;
        }
        final float[] newArray = new float[array.length];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = array[i] * factor;
        }
        return newArray;
    }

    /**
     * Dient dem Testen der Klasse.
     * 
     * @param args
     *            Wird nicht verwendet.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> openStrokeDialog(new JFrame(), "Linienstil", new BasicStroke()));
    }

    /**
     * CellRenderer für die Darstellung einer gestrichelten Linienart in einer Auswahlkomponente.
     *
     * @author Christoph Lembeck
     */
    static class DashCellRenderer extends JLabel implements ListCellRenderer<DashInfo> {

        /**
         * Version number of the current class.
         * 
         * @see java.io.Serializable
         */
        private static final long serialVersionUID = -9138269210159811807L;

        @Override
        public Component getListCellRendererComponent(final JList<? extends DashInfo> list, final DashInfo value,
                final int index,
                final boolean isSelected, final boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            setIcon(new DashIcon(value));
            return this;
        }

    }

    /**
     * Icon zur Darstellung der aktuell ausgewählten Linienart.
     *
     * @author Christoph Lembeck
     */
    static class ExampleIcon implements Icon {

        /**
         * Aktuelle Linienart.
         */
        private BasicStroke stroke;

        /**
         * Erstellt das Icon für die Darstellung einer Linienart.
         * 
         * @param stroke
         *            Gewünschte Linienart für die Darstellung.
         */
        public ExampleIcon(final BasicStroke stroke) {
            this.stroke = stroke;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void paintIcon(final Component component, final Graphics graphics, final int xPos, final int yPos) {
            final Graphics2D g2d = (Graphics2D) graphics.create();
            g2d.translate(xPos, yPos);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            final float strokeWidth = stroke.getLineWidth();
            final int width = getIconWidth();
            final int height = getIconHeight();
            final float radius = (width - strokeWidth) / 10;
            final float radius2 = radius * 2;
            final float kappa = (float) (4 * (Math.sqrt(2) - 1) / 3);
            final Path2D path = new Path2D.Double();
            path.moveTo(strokeWidth / 2, strokeWidth / 2);
            path.lineTo(width - strokeWidth / 2 - radius, strokeWidth / 2);
            path.curveTo(width - strokeWidth / 2 - radius + kappa * radius, strokeWidth / 2, width - strokeWidth / 2,
                    strokeWidth / 2 + radius - kappa * radius, width - strokeWidth / 2, strokeWidth / 2 + radius);
            path.lineTo(width - strokeWidth / 2, height - strokeWidth / 2 - radius2);
            path.curveTo(width - strokeWidth / 2 - radius2 * kappa, height - strokeWidth / 2 - radius2,
                    width - strokeWidth
                            / 2 - radius2,
                    height - strokeWidth / 2 - radius2 * kappa, width - strokeWidth / 2 - radius2, height
                            - strokeWidth / 2);
            path.lineTo(strokeWidth / 2 + radius2, height - strokeWidth / 2);
            path.lineTo(strokeWidth / 2, height - strokeWidth / 2 - radius2);
            path.lineTo(strokeWidth / 2, height * 2 / 3f);
            path.lineTo(width / 2f, height / 2f);
            path.lineTo(strokeWidth / 2, height * 1 / 3f);
            path.closePath();

            g2d.setStroke(stroke);
            g2d.setColor(Color.WHITE);
            g2d.fill(path);
            if (strokeWidth > 0) {
                g2d.setColor(Color.BLACK);
                g2d.draw(path);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIconWidth() {
            return 200;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIconHeight() {
            return 200;
        }
    }

    /**
     * Icon für die Darstellung einer Linienart in einer Auswahlbox.
     *
     * @author Christoph Lembeck
     */
    static class DashIcon implements Icon {

        /**
         * Darzustellende Linienart.
         */
        private BasicStroke stroke;

        /**
         * Erstellt das Icon mit der gewünschten Definition der gestrichelten Linienabstände.
         * 
         * @param dash
         *            Info über die Struktur der Abstände in der gestrichelten Linie.
         */
        public DashIcon(final DashInfo dash) {
            this.stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash.getDash(),
                    dash.getDashPhase());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void paintIcon(final Component component, final Graphics graphics, final int xPos, final int yPos) {
            final Graphics2D g2d = (Graphics2D) graphics.create();
            g2d.setStroke(stroke);
            g2d.setColor(Color.BLACK);
            g2d.drawLine(xPos + 5, getIconHeight() / 2, xPos + getIconWidth() - 5, getIconHeight() / 2);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIconWidth() {
            return 200;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIconHeight() {
            return 19;
        }
    }
}