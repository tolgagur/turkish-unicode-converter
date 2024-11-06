package com.tolgagureli.turkishunicodeconverter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnicodeConverterToolWindowFactory extends AnAction {

    private static final Object[][] DATA = {
            {"ç", "\\u00E7"},
            {"Ç", "\\u00C7"},
            {"ğ", "\\u011F"},
            {"Ğ", "\\u011E"},
            {"ö", "\\u00F6"},
            {"Ö", "\\u00D6"},
            {"ş", "\\u015F"},
            {"Ş", "\\u015E"},
            {"ü", "\\u00FC"},
            {"Ü", "\\u00DC"},
            {"ı", "\\u0131"},
            {"İ", "\\u0130"}
    };

    private static final String[] COLUMN_NAMES = {"Turkish Letter", "Unicode Value"};
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;

    @Override
    public void actionPerformed(AnActionEvent e) {
        createAndShowFrame();
    }

    private void createAndShowFrame() {
        JFrame frame = createFrame();
        JTable table = createTable();
        JPanel panel = createInputPanel(frame);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.NORTH);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Turkish Unicode Converter");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private JTable createTable() {
        DefaultTableModel tableModel = new DefaultTableModel(DATA, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Cells should not be editable
            }
        };
        return new JTable(tableModel);
    }

    private JPanel createInputPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField textField = new JTextField();
        panel.add(textField, BorderLayout.NORTH);

        JButton copyButton = new JButton("Copy to Clipboard");
        copyButton.addActionListener(createCopyButtonListener(frame, textField));
        panel.add(copyButton, BorderLayout.EAST);

        return panel;
    }

    private ActionListener createCopyButtonListener(JFrame frame, JTextField textField) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                String convertedText = convertToUnicode(inputText);
                copyToClipboard(convertedText);
                showConvertedText(frame, convertedText);
            }
        };
    }

    private String convertToUnicode(String inputText) {
        for (Object[] mapping : DATA) {
            inputText = inputText.replace(mapping[0].toString(), mapping[1].toString());
        }
        return inputText;
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    private void showConvertedText(JFrame frame, String convertedText) {
        JOptionPane.showMessageDialog(frame, "Converted Text copied to clipboard: " + convertedText);
    }
}