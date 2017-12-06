package de.chrlembeck.util;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import de.chrlembeck.util.console.ConsoleTable;
import de.chrlembeck.util.console.ConsoleTable.Alignment;

/**
 * Tests für die ConsoleTable
 * 
 * @author LeC
 *
 */
@RunWith(JUnitPlatform.class)
public class ConsoleTableTest {

    /**
     * Testet die Tabelle
     */
    @Test
    public void testTable() {
        final ConsoleTable table = getTable();
        final String string = table.toString();
        System.out.println(string);
    }

    /**
     * Main-Methode für individuelle Tests.
     * 
     * @param args
     *            wird nicht verwendet.
     */
    public static void main(final String[] args) {
        final ConsoleTable table = getTable();
        table.setBorderConfiguration(table.createDefaultBorderCofiguration());
        final String text = table.toString();
        System.out.println(text);
        final JFrame frame = new JFrame();
        final JTextArea textArea = new JTextArea(text);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 24));
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Erzeugt die Testtabelle.
     * 
     * @return Tabelle für die Tests.
     */
    private static ConsoleTable getTable() {
        final ConsoleTable table = new ConsoleTable(3);
        table.setColumnNames("Col 1", "Col 2", "Col 3");
        table.addRow("a0", "right", "c0");
        table.addRow("a1", "longer text", "center1");
        table.setAlignment(1, Alignment.RIGHT);
        table.setAlignment(2, Alignment.CENTER);
        return table;
    }
}