package de.chrlembeck.util.swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import de.chrlembeck.util.lang.StringUtils;
import de.chrlembeck.util.swing.SimpleDocumentListener;
import de.chrlembeck.util.swing.components.ColorCircle.HSV;
import de.chrlembeck.util.swing.formatter.BackgroundModifier;
import de.chrlembeck.util.swing.formatter.FormatterInputVerifier;

/**
 * Panel zur Darstellung einer HSV-Farbauswahl-Komponente.
 *
 * @author LeC
 */
@SuppressWarnings("PMD.GodClass")
public final class ColorChooserPanel extends JPanel {

    /**
     * Version number of the current class.
     * 
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * Konstanten zur Bestimmung eines Eingabefeldes für die verschiedenen Komponenten zur Farbauswahl.
     *
     * @author LeC
     */
    enum Field {

        /**
         * Eingabefeld für den Helligkeitswert der Farbe im HSV-Farbraum.
         */
        BLACKNESS_VALUE,

        /**
         * Eingabefeld für den Blauanteil im RGB-Farbraum.
         */
        BLUE,

        /**
         * Information über Änderungen des Helligkeitswertes durch Klick auf das Dreieck.
         */
        CIRCLE_BLACKNESS_VALUE,

        /**
         * Information über Änderung des Farbwertes durch Klick auf den Farbkreis.
         */
        CIRCLE_HUE,

        /**
         * Information über Änderungen der Farbintensität durch Klick auf das Dreieck.
         */
        CIRCLE_SATURATION,

        /**
         * Eingabefeld für den Grünanteil im RGB-Farbraum.
         */
        GREEN,

        /**
         * Eingabefeld für den Farbwert im HSV-Farbraum.
         */
        HUE,

        /**
         * Eingabefeld für die Eingabe der Farbwerte als Hexadezimale RGB-Werte.
         */
        NAME,

        /**
         * Einagebfeld für den Rotanteil im RGB-Farbraum.
         */
        RED,

        /**
         * Eingabefeld für die Farbsättigung im HSV-Farbraum.
         */
        SATURATION,

        /**
         * Schieberegler für die Transparenz der Farbe.
         */
        TRANSPARENCY_SLIDER,

        /**
         * Eingabefeld für die Transparenz der Farbe.
         */
        TRANSPARENCY_TEXT
    }

    /**
     * Eingabefeld für den Helligkeitswert (value) der Farbe.
     */
    private JTextField blacknessValueField;

    /**
     * Eingabefeld für den Blauanteil im RGB-Farbraum.
     */
    private JTextField blueField;

    /**
     * Panel, in dem die Farbauswahlkomponente dargestellt wird.
     */
    ColorCircle colorCirclePanel;

    /**
     * Einagebfeld für den Grünanteil im RGB-Farbraum.
     */
    private JTextField greenField;

    /**
     * Einagebfeld für den Farbwert im HSV-Farbraum.
     */
    private JTextField hueField;

    /**
     * Zeigt an, ob die Eingabelistener momentan aktiv sind oder ausgeschaltet wurden, damit programmatische Änderungen
     * der Felder nicht zu neuen Events führen.
     */
    boolean listenersEnabled;

    /**
     * Einagebfeld für die Hexadezimale Schreibweise der Farbe als RGB-Werte.
     */
    private JTextField nameField;

    /**
     * Komponente zur Darstellung der voreingestellten Vergleichsfarbe. Dies ist in der Regel die Farbe, die ein Element
     * vor der Auswahl hatte.
     */
    private ShowColorComponent referenceColorPanel;

    /**
     * Einagebfeld für den Rotanteil der Farbe im RGB-Farbraum.
     */
    private JTextField redField;

    /**
     * Einagefeld für die farbsättigung im HSV-Farbraum.
     */
    private JTextField saturationField;

    /**
     * Komponente zur Darstellung der aktuell ausgewählten Farbe.
     */
    private ShowColorComponent selectedColorPanel;

    /**
     * Einagefeld für die Transparenz der Farbe.
     */
    private JTextField transparencyField;

    /**
     * Schieberegler für die Transparenz der Farbe.
     */
    JSlider transparencySlider;

    /**
     * Einagabevalidierer für die Eingabe von Zahlen zwischen 0 und 255.
     */
    private InputVerifier inputVerifier255;

    /**
     * Einagabevalidierer für die Eingabe von Zahlen zwischen 0 und 359.
     */
    private InputVerifier inputVerifier359;

    /**
     * Erstell ein neues Farbauswahlpanel.
     */
    public ColorChooserPanel() {
        init();
    }

    /**
     * Wird aufgerufen, wenn eine textuelle Eingabe eines Farbwertes in den Eingabefeldern vorgenommen wurde. Sorgt
     * dafür, dass die Darstellung und ggf. die restlichen Eingabefelder aktualisiert werden.
     * 
     * @param documentEvent
     *            Event, welches von dem Eingafeld produziert wurde.
     * @param field
     *            ID des Feldes, aus dem das Event stammt.
     */
    @SuppressWarnings("PMD.EmptyCatchBlock")
    private void documentUpdated(final DocumentEvent documentEvent, final Field field) {
        if (listenersEnabled) {
            try {
                final Document document = documentEvent.getDocument();
                final String text = document.getText(0, document.getLength());
                if (field == Field.NAME) {
                    fieldUpdated(field, 0);
                } else {
                    final int value = Math.max(0, Math.min(255, Integer.parseInt(text)));
                    fieldUpdated(field, value);
                }
            } catch (NumberFormatException | BadLocationException e) {
                // do nothing
            }
        }
    }

    /**
     * Erstellt und konfiguriert das Eingabefeld für den HSV-Helligkeitswert der Farbe.
     * 
     * @return Fertig konfiguriertes Eingabefeld.
     */
    private JTextField createBlacknessValueField() {
        blacknessValueField = new JTextField(3);
        blacknessValueField.getDocument()
                .addDocumentListener(
                        new SimpleDocumentListener(docEvent -> documentUpdated(docEvent, Field.BLACKNESS_VALUE)));
        blacknessValueField.setInputVerifier(inputVerifier255);
        return blacknessValueField;
    }

    /**
     * Erstellt und konfiguriert das Eingabefeld für den RGB-Blauwert der Farbe.
     * 
     * @return Fertig konfiguriertes Eingabefeld.
     */
    private JTextField createBlueField() {
        blueField = new JTextField(3);
        blueField.getDocument()
                .addDocumentListener(new SimpleDocumentListener(docEvent -> documentUpdated(docEvent, Field.BLUE)));
        blueField.setInputVerifier(inputVerifier255);
        return blueField;
    }

    /**
     * Erstellt und initialisiert das Panel für die Darstellung der Farbauswahlkomponente.
     * 
     * @return Fertig konfiguriertes Farbauswahlpanel.
     */
    private JComponent createColorCirclePanel() {
        colorCirclePanel = new ColorCircle();
        colorCirclePanel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (listenersEnabled) {
                    final String property = evt.getPropertyName();
                    if (ColorCircle.PROPERTY_HUE.equals(property)) {
                        fieldUpdated(Field.CIRCLE_HUE, colorCirclePanel.getHue());
                    }
                    if (ColorCircle.PROPERTY_SATURATION.equals(property)) {
                        fieldUpdated(Field.CIRCLE_SATURATION, colorCirclePanel.getSaturation());
                    }
                    if (ColorCircle.PROPERTY_BLACKNESS_VALUE.equals(property)) {
                        fieldUpdated(Field.CIRCLE_BLACKNESS_VALUE, colorCirclePanel.getBlacknessValue());
                    }
                }
            }
        });
        return colorCirclePanel;
    }

    /**
     * Erstellt und konfiguriert das Eingabefeld für den RGB-Grünwert der Farbe.
     * 
     * @return Fertig konfiguriertes Eingabefeld.
     */
    private JTextField createGreenField() {
        greenField = new JTextField(3);
        greenField.getDocument()
                .addDocumentListener(new SimpleDocumentListener(docEvent -> documentUpdated(docEvent, Field.GREEN)));
        greenField.setInputVerifier(inputVerifier255);
        return greenField;
    }

    /**
     * Erstellt und konfiguriert das Eingabefeld für den HSV-Farbwert der Farbe.
     * 
     * @return Fertig konfiguriertes Eingabefeld.
     */
    private JTextField createHueField() {
        hueField = new JTextField(3);
        hueField.getDocument()
                .addDocumentListener(new SimpleDocumentListener(docEvent -> documentUpdated(docEvent, Field.HUE)));
        hueField.setInputVerifier(inputVerifier359);
        return hueField;
    }

    /**
     * Erstellt ein Label mit der Standardschrift für diese Komponenten.
     * 
     * @param name
     *            Angezeigter Text des Labels.
     * @return Label mit der für die Komponente passenden Schrift.
     */
    private JLabel createLabel(final String name) {
        final JLabel label = new JLabel(name);
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        return label;
    }

    /**
     * Erstellt und konfiguriert das links in der Komponente dargestellte Panel. Dieses enthält die grafischen
     * Farbauswahlkomponenten.
     * 
     * @return Fertig konfiurierteds Panel für den linken Bereich der Komponente.
     */
    private final JPanel createLeftPanel() {
        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(createColorCirclePanel(), BorderLayout.CENTER);
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());

        final JPanel colorsPanel = new JPanel();
        colorsPanel.setLayout(new GridBagLayout());
        colorsPanel.add(createPreselectionPanel(), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        colorsPanel.add(createSelectedColorPanel(), new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        bottomPanel.add(colorsPanel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        colorsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        leftPanel.add(bottomPanel, BorderLayout.SOUTH);
        return leftPanel;
    }

    /**
     * Erstellt und initialisiert das Eingabefeld für die Eingabe der RGB-Farbwerte als hexadezimale Darstellung.
     * 
     * @return Textfeld für die hexadezimalen RGB-Werte.
     */
    private JTextField createNameField() {
        nameField = new JTextField(7);
        nameField.getDocument()
                .addDocumentListener(new SimpleDocumentListener(docEvent -> documentUpdated(docEvent, Field.NAME)));
        return nameField;
    }

    /**
     * Erstellt und initialisiert den Anzeigebereich für die Voreingestellte Referenzfarbe als grafische Darstellung.
     * 
     * @return Fertig konfiguriertes Panel zur Darstellung der Referenzfarbe.
     */
    private Component createPreselectionPanel() {
        referenceColorPanel = new ShowColorComponent();
        referenceColorPanel.setOpaque(true);
        referenceColorPanel.setPreferredSize(new Dimension(60, 60));
        referenceColorPanel.setMinimumSize(new Dimension(60, 60));
        referenceColorPanel.setBackground(Color.RED);
        return referenceColorPanel;
    }

    /**
     * Erstellt und konfiguriert das Eingabefeld für den RGB-Rotwert der Farbe.
     * 
     * @return Fertig konfiguriertes Eingabefeld.
     */
    private JTextField createRedField() {
        redField = new JTextField(3);
        redField.getDocument()
                .addDocumentListener(new SimpleDocumentListener(docEvent -> documentUpdated(docEvent, Field.RED)));
        redField.setInputVerifier(inputVerifier255);
        return redField;
    }

    /**
     * Erstellt und kofiguriert den rechten Bereich des Panels. Hier sind die Texteingabefelder enthalten, über die die
     * Farbe definiert werden kann.
     * 
     * @return Fertig konfiguriertes Panel für den rechten bereich der Komponente.
     */
    private final JPanel createRightPanel() {
        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.add(createLabel("Ton"), new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createHueField(), new GridBagConstraints(2, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createLabel("Rot"), new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createRedField(), new GridBagConstraints(4, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

        rightPanel.add(createLabel("Sättigung"), new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createSaturationField(), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createLabel("Grün"), new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createGreenField(), new GridBagConstraints(4, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

        rightPanel.add(createLabel("Intensität"), new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createBlacknessValueField(), new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createLabel("Blau"), new GridBagConstraints(3, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createBlueField(), new GridBagConstraints(4, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

        rightPanel.add(createLabel("Deckkraft"), new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createTransparencySlider(), new GridBagConstraints(2, 3, 2, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createTransparencyField(), new GridBagConstraints(4, 3, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

        rightPanel.add(createLabel("Name"), new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        rightPanel.add(createNameField(), new GridBagConstraints(2, 4, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        return rightPanel;
    }

    /**
     * Erstellt und konfiguriert das Eingabefeld für den HSV-Farbsättigungswert der Farbe.
     * 
     * @return Fertig konfiguriertes Eingabefeld.
     */
    private JTextField createSaturationField() {
        saturationField = new JTextField(3);
        saturationField.getDocument().addDocumentListener(
                new SimpleDocumentListener(docEvent -> documentUpdated(docEvent, Field.SATURATION)));
        saturationField.setInputVerifier(inputVerifier255);
        return saturationField;
    }

    /**
     * Erstellt und initialisiert den Anzeigebereich für die gerade ausgewählte Farbe als grafische Darstellung.
     * 
     * @return Fertig konfiguriertes Panel zur Darstellung der Farbe.
     */
    private Component createSelectedColorPanel() {
        selectedColorPanel = new ShowColorComponent();
        selectedColorPanel.setOpaque(true);
        selectedColorPanel.setPreferredSize(new Dimension(60, 60));
        selectedColorPanel.setMinimumSize(new Dimension(60, 60));
        selectedColorPanel.setBackground(Color.RED);
        return selectedColorPanel;
    }

    /**
     * Erstellt und konfiguriert das Eingabefeld für den Transparenzwert der Farbe.
     * 
     * @return Fertig konfiguriertes Eingabefeld.
     */
    private JTextField createTransparencyField() {
        transparencyField = new JTextField(3);
        transparencyField.getDocument().addDocumentListener(
                new SimpleDocumentListener(docEvent -> documentUpdated(docEvent, Field.TRANSPARENCY_TEXT)));
        transparencyField.setInputVerifier(inputVerifier255);
        return transparencyField;
    }

    /**
     * Erstellt und konfiguriert den Schieberegler für die Transparen der Farbe.
     * 
     * @return Fertig konfigurierter Schieberegler.
     */
    private JSlider createTransparencySlider() {
        transparencySlider = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 255);
        transparencySlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent event) {
                if (listenersEnabled) {
                    final int value = transparencySlider.getValue();
                    fieldUpdated(Field.TRANSPARENCY_SLIDER, value);
                }
            }
        });
        return transparencySlider;
    }

    /**
     * Deaktiviert die Eingabelistener, so dass programmatische Aktualisierungen der Felder nicht zu neuen Events
     * führen.
     */
    private void disableUpdateListeners() {
        listenersEnabled = false;
    }

    /**
     * Aktiviert die Eingabelistener nach einer Aktualisierung der Felder wieder, so dass weitere Eingaben wieder
     * verarbeitet werden.
     */
    private void enableUpdateListeners() {
        listenersEnabled = true;
    }

    /**
     * Wird aufgerufen, wenn ein Eingabefeld zur Definition der Farbe verändert wurde. Passt darauf hin die anderen
     * Felder und die angezeigte Farbe an.
     * 
     * @param field
     *            Feld, welches angepasst wurde.
     * @param value
     *            Neuer Wert in dem Feld.
     */
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public void fieldUpdated(final Field field, final int value) {
        disableUpdateListeners();
        try {
            switch (field) {
                case TRANSPARENCY_TEXT:
                    transparencySlider.setValue(value);
                    break;
                case TRANSPARENCY_SLIDER:
                    transparencyField.setText(Integer.toString(value));
                    break;
                case RED:
                case GREEN:
                case BLUE:
                    HSV hsv = ColorCircle.getHSV(getRed(), getGreen(), getBlue());
                    hueField.setText(Integer.toString(hsv.getHue()));
                    saturationField.setText(Integer.toString(hsv.getSaturation()));
                    blacknessValueField.setText(Integer.toString(hsv.getBlacknessValue()));
                    colorCirclePanel.setHue(hsv.getHue());
                    colorCirclePanel.setSaturation(hsv.getSaturation());
                    colorCirclePanel.setBlacknessValue(hsv.getBlacknessValue());
                    break;
                case HUE:
                    colorCirclePanel.setHue(getHue());
                    Color color = colorCirclePanel.getColorByHSV();
                    redField.setText(Integer.toString(color.getRed()));
                    greenField.setText(Integer.toString(color.getGreen()));
                    blueField.setText(Integer.toString(color.getBlue()));
                    break;
                case SATURATION:
                    colorCirclePanel.setSaturation(getSaturation());
                    color = colorCirclePanel.getColorByHSV();
                    redField.setText(Integer.toString(color.getRed()));
                    greenField.setText(Integer.toString(color.getGreen()));
                    blueField.setText(Integer.toString(color.getBlue()));
                    break;
                case BLACKNESS_VALUE:
                    colorCirclePanel.setBlacknessValue(getBlacknessValue());
                    color = colorCirclePanel.getColorByHSV();
                    redField.setText(Integer.toString(color.getRed()));
                    greenField.setText(Integer.toString(color.getGreen()));
                    blueField.setText(Integer.toString(color.getBlue()));
                    break;
                case CIRCLE_HUE:
                    hueField.setText(Integer.toString(value));
                    color = colorCirclePanel.getColorByHSV();
                    redField.setText(Integer.toString(color.getRed()));
                    greenField.setText(Integer.toString(color.getGreen()));
                    blueField.setText(Integer.toString(color.getBlue()));
                    break;
                case CIRCLE_SATURATION:
                    saturationField.setText(Integer.toString(value));
                    color = colorCirclePanel.getColorByHSV();
                    redField.setText(Integer.toString(color.getRed()));
                    greenField.setText(Integer.toString(color.getGreen()));
                    blueField.setText(Integer.toString(color.getBlue()));
                    break;
                case CIRCLE_BLACKNESS_VALUE:
                    blacknessValueField.setText(Integer.toString(value));
                    color = colorCirclePanel.getColorByHSV();
                    redField.setText(Integer.toString(color.getRed()));
                    greenField.setText(Integer.toString(color.getGreen()));
                    blueField.setText(Integer.toString(color.getBlue()));
                    break;
                case NAME:
                    String text = nameField.getText();
                    if (text.charAt(0) == '#') {
                        text = text.substring(1);
                    }
                    if (text.length() == 6 || text.length() == 8) {
                        try {
                            final int colorValue = Integer.parseUnsignedInt(text, 16);
                            final int alpha = colorValue >> 24 & 0xFF;
                            final int red = colorValue >> 16 & 0xFF;
                            final int green = colorValue >> 8 & 0xFF;
                            final int blue = colorValue & 0xFF;
                            redField.setText(Integer.toString(red));
                            greenField.setText(Integer.toString(green));
                            blueField.setText(Integer.toString(blue));
                            hsv = ColorCircle.getHSV(red, green, blue);
                            hueField.setText(Integer.toString(hsv.getHue()));
                            saturationField.setText(Integer.toString(hsv.getSaturation()));
                            blacknessValueField.setText(Integer.toString(hsv.getBlacknessValue()));
                            colorCirclePanel.setHue(hsv.getHue());
                            colorCirclePanel.setSaturation(hsv.getSaturation());
                            colorCirclePanel.setBlacknessValue(hsv.getBlacknessValue());
                            if (text.length() == 8) {
                                transparencyField.setText(Integer.toString(alpha));
                                transparencySlider.setValue(alpha);
                            }
                        } catch (final NumberFormatException nfe) {
                            // ignore
                        }
                    }
                    break;
                default:
            }
            selectedColorPanel.setBackground(new Color(getRed(), getGreen(), getBlue(), getTransparency()));
            if (field != Field.NAME) {
                updateNameField();
            }
        } finally {
            enableUpdateListeners();
        }
    }

    /**
     * Gibt den Hellwert des HSV-Farbmodells für die aktuell ausgewählte Farbe zurück.
     * 
     * @return HSV-Hellwert der Farbe.
     * @see HSV#getBlacknessValue()
     */
    public int getBlacknessValue() {
        try {
            return Math.min(255, Math.max(0, Integer.parseInt(blacknessValueField.getText())));
        } catch (final NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Gibt den Blauwert des RGB-Farbmodells für die aktuell ausgewählte Farbe zurück.
     * 
     * @return Blauwert der Farbe.
     */
    public int getBlue() {
        try {
            return Math.min(255, Math.max(0, Integer.parseInt(blueField.getText())));
        } catch (final NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Gibt den Grünwert des RGB-Farbmodells für die aktuell ausgewählte Farbe zurück.
     * 
     * @return Grünwert der Farbe.
     */
    public int getGreen() {
        try {
            return Math.min(255, Math.max(0, Integer.parseInt(greenField.getText())));
        } catch (final NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Gibt den Farbwert (Winkel) des HSV-Farbmodells für die aktuell ausgewählte Farbe zurück.
     * 
     * @return HSV-Farbwert der Farbe.
     * @see HSV#getHue()
     */
    public int getHue() {
        try {
            return Math.min(360, Math.max(0, Integer.parseInt(hueField.getText())));
        } catch (final NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Gibt den Rotwert des RGB-Farbmodells für die aktuell ausgewählte Farbe zurück.
     * 
     * @return Rotwert der Farbe.
     */
    public int getRed() {
        try {
            return Math.min(255, Math.max(0, Integer.parseInt(redField.getText())));
        } catch (final NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Gibt die Farbsättigung des HSV-Farbmodells für die aktuell ausgewählte Farbe zurück.
     * 
     * @return HSV-Farbsättigung der Farbe.
     * @see HSV#getSaturation()
     */
    public int getSaturation() {
        try {
            return Math.min(255, Math.max(0, Integer.parseInt(saturationField.getText())));
        } catch (final NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Gibt die aktuell ausgewählte Farbe zurück.
     * 
     * @return Aktuell im Farbdreieck ausgewählte Farbe.
     */
    public Color getSelectedColor() {
        return selectedColorPanel.getBackground();
    }

    /**
     * Gibt die gewünschte Transparenz für die aktuell ausgewählte Farbe zurück.
     * 
     * @return Transparenz der Farbe.
     */
    public int getTransparency() {
        try {
            return Math.min(255, Math.max(0, Integer.parseInt(transparencyField.getText())));
        } catch (final NumberFormatException nfe) {
            return 255;
        }
    }

    /**
     * Dient dem Konvertieren der textuellen Eingaben in gültige Farbwerte. Eine numerische Obergrenze legt fest, welche
     * Zahlen von einem Eingabefeld akzeptiert werden.
     *
     * @author Christoph Lembeck
     */
    static class NumberFormatter extends AbstractFormatter {

        /**
         * Version number of the current class.
         * 
         * @see java.io.Serializable
         */
        private static final long serialVersionUID = -4262814356520019841L;

        /**
         * Obergrenze für die akzeptierten Eingaben.
         */
        private int maxValue;

        /**
         * Erstellt einen neuen Formatter mit der gewünschten Obergrenze für die eingegebenen Zahlen
         * 
         * @param max
         *            Obergrenze für akzeptierte Eingaben.
         */
        public NumberFormatter(final int max) {
            this.maxValue = max;
        }

        /**
         * Konvertiert den Text in eine Zahl oder wirft eine Exception, wenn die Zahl kleiner 0 oder größer als die
         * definierte Obergrenze ist.
         */
        @Override
        @SuppressWarnings("PMD.PreserveStackTrace")
        public Integer stringToValue(final String text) throws ParseException {
            if (StringUtils.isEmpty(text)) {
                return Integer.valueOf(0);
            }
            try {
                final int value = Integer.parseInt(text);
                if (value < 0) {
                    throw new ParseException("number to small " + value + " < 0.", 0);
                }
                if (value > maxValue) {
                    throw new ParseException("number to large " + value + " > " + maxValue + ".", 0);
                }
                return Integer.valueOf(value);
            } catch (final NumberFormatException nfe) {
                throw new ParseException(nfe.getLocalizedMessage(), 0);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String valueToString(final Object value) throws ParseException {
            return value.toString();
        }
    }

    /**
     * Initialisiert die Komponente erstellt die enthaltenen GUI-Komponenten.
     */
    private final void init() {
        inputVerifier255 = new FormatterInputVerifier(new NumberFormatter(255), new BackgroundModifier(Color.orange));
        inputVerifier359 = new FormatterInputVerifier(new NumberFormatter(359), new BackgroundModifier(Color.orange));
        setLayout(new GridBagLayout());
        add(createLeftPanel(), new GridBagConstraints(0, 0, 1, GridBagConstraints.REMAINDER, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(createRightPanel(),
                new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        listenersEnabled = true;
    }

    /**
     * Legt die Farbe für das Vergleichsfeld fest.
     * 
     * @param preselection
     *            Farbe für die Darstellung der Farbvergleichsfläche.
     */
    public void setReferenceColor(final Color preselection) {
        referenceColorPanel.setBackground(preselection);
    }

    /**
     * Legt die aktuell ausgewählte Farbe fest.
     * 
     * @param color
     *            Farbe, die als aktuell ausgewählte Farbe dargestellt werden soll.
     */
    public void setSelectedColor(final Color color) {
        selectedColorPanel.setBackground(color);
        redField.setText(Integer.toString(color.getRed()));
        blueField.setText(Integer.toString(color.getBlue()));
        greenField.setText(Integer.toString(color.getGreen()));
        transparencyField.setText(Integer.toString(color.getAlpha()));
        transparencySlider.setValue(color.getAlpha());
    }

    /**
     * Aktualsiert das Eingabefeld zur Eingabe der Farbe über hexadezimale RGB-Werte.
     */
    private void updateNameField() {
        final Color color = selectedColorPanel.getBackground();
        final int red = color.getRed();
        final int green = color.getGreen();
        final int blue = color.getBlue();
        nameField.setText(("#" + (red < 10 ? "0" : "") + Integer.toHexString(red) + (green < 10 ? "0" : "")
                + Integer.toHexString(green) + (blue < 10 ? "0" : "") + Integer.toHexString(blue))
                        .toUpperCase(Locale.US));
    }

    /**
     * Grafische Komponente zur Anzeige einer Farbe in einem Rechteck. Um die Transparenz einer Farbe zu visualisieren
     * wird die Komponente mit einem grauen Karomuster hinterlegt.
     * 
     * @author Christoph Lembeck
     *
     */
    class ShowColorComponent extends JComponent {

        /**
         * Version number of the current class.
         * 
         * @see java.io.Serializable
         */
        private static final long serialVersionUID = -1525650966496685186L;

        /**
         * 
         */
        @Override
        protected void paintComponent(final Graphics graphics) {
            final Graphics2D g2d = (Graphics2D) graphics;
            final Insets insets = getInsets();
            final int squareSize = 10;
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(insets.left, insets.top, getWidth() - insets.left - insets.right, getHeight() - insets.top
                    - insets.bottom);
            g2d.setColor(Color.DARK_GRAY);
            final int centerX = (getWidth() - insets.left - insets.right + squareSize - 1) / squareSize;
            final int centerY = (getHeight() - insets.top - insets.bottom + squareSize - 1) / squareSize;
            for (int x = 0, px = insets.left; x < centerX; x++, px += squareSize) {
                for (int y = 0, py = insets.top; y < centerY; y++, py += squareSize) {
                    if ((x + y) % 2 == 0) {
                        g2d.fillRect(px, py, squareSize, squareSize);
                    }
                }
            }

            g2d.setColor(getBackground());
            g2d.fillRect(insets.left, insets.top, getWidth() - insets.left - insets.right, getHeight() - insets.top
                    - insets.bottom);
        }
    }
}