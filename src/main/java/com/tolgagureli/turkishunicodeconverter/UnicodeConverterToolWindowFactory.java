package com.tolgagureli.turkishunicodeconverter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;

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
    private static final int FRAME_WIDTH = 450;
    private static final int FRAME_HEIGHT = 600;

    @Override
    public void actionPerformed(AnActionEvent e) {
        createAndShowFrame();
    }

    private void createAndShowFrame() {
        JFrame frame = createFrame();
        JTable table = createTable();
        JPanel panel = createInputPanel(frame);

        JBScrollPane scrollPane = new JBScrollPane(table);
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
        frame.getContentPane().setBackground(JBColor.PanelBackground);
        return frame;
    }

    private JTable createTable() {
        DefaultTableModel tableModel = new DefaultTableModel(DATA, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JBTable(tableModel);
        table.setFont(JBUI.Fonts.label(14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(JBUI.Fonts.label().asBold());
        table.getTableHeader().setBackground(JBColor.background());
        table.getTableHeader().setForeground(JBColor.foreground());

        return table;
    }

    private JPanel createInputPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 20)); // Boşlukları ayarladık
        panel.setBackground(JBColor.PanelBackground);
        panel.setBorder(JBUI.Borders.empty(10, 10));

        // Türkçe karakterleri Unicode'a dönüştüren alan
        JPanel convertToUnicodePanel = new JPanel(new BorderLayout(5, 5));
        convertToUnicodePanel.setBackground(JBColor.PanelBackground);

        JBTextField unicodeInputField = new JBTextField();
        unicodeInputField.setFont(JBUI.Fonts.label(14));
        // Hem input hem de buton için aynı genişlik ve boyut
        Dimension inputButtonSize = new Dimension(300, 30);
        unicodeInputField.setPreferredSize(inputButtonSize);

        // Butonu input alanının altına ekliyoruz
        JButton copyButton = new JButton("Copy to Clipboard (Unicode)");
        copyButton.putClientProperty("JButton.buttonType", "primary");
        copyButton.setFont(JBUI.Fonts.label(12).asBold());
        copyButton.setPreferredSize(inputButtonSize);  // Aynı boyut

        copyButton.addActionListener(createCopyButtonListener(frame, unicodeInputField, true));

        // Input'u üstte, butonu altta yerleştiriyoruz
        convertToUnicodePanel.add(new JLabel("Enter text to convert to Unicode:"), BorderLayout.NORTH);
        convertToUnicodePanel.add(unicodeInputField, BorderLayout.CENTER);
        convertToUnicodePanel.add(copyButton, BorderLayout.SOUTH); // Buton alt tarafa

        // Unicode'dan Türkçe karakterlere dönüştüren alan
        JPanel convertToTextPanel = new JPanel(new BorderLayout(5, 5));
        convertToTextPanel.setBackground(JBColor.PanelBackground);

        JBTextField textInputField = new JBTextField();
        textInputField.setFont(JBUI.Fonts.label(14));
        textInputField.setPreferredSize(inputButtonSize);  // Aynı boyut

        // Butonu input alanının altına ekliyoruz
        JButton reverseCopyButton = new JButton("Copy to Clipboard (Text)");
        reverseCopyButton.putClientProperty("JButton.buttonType", "primary");
        reverseCopyButton.setFont(JBUI.Fonts.label(12).asBold());
        reverseCopyButton.setPreferredSize(inputButtonSize);  // Aynı boyut

        reverseCopyButton.addActionListener(createCopyButtonListener(frame, textInputField, false));

        // Input'u üstte, butonu altta yerleştiriyoruz
        convertToTextPanel.add(new JLabel("Enter Unicode text to convert to normal:"), BorderLayout.NORTH);
        convertToTextPanel.add(textInputField, BorderLayout.CENTER);
        convertToTextPanel.add(reverseCopyButton, BorderLayout.SOUTH); // Buton alt tarafa

        // Panellere ekleme
        panel.add(convertToUnicodePanel);
        panel.add(convertToTextPanel);

        return panel;
    }


    private ActionListener createCopyButtonListener(JFrame frame, JBTextField textField, boolean toUnicode) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                String convertedText = toUnicode ? convertToUnicode(inputText) : convertToText(inputText);
                copyToClipboard(convertedText);
                showConvertedText(frame, convertedText, toUnicode);
            }
        };
    }

    public String convertToUnicode(String inputText) {
        for (Object[] mapping : DATA) {
            inputText = inputText.replace(mapping[0].toString(), mapping[1].toString());
        }
        return inputText;
    }

    public String convertToText(String unicodeText) {
        for (Object[] mapping : DATA) {
            unicodeText = unicodeText.replace(mapping[1].toString(), mapping[0].toString());
        }
        return unicodeText;
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    private void showConvertedText(JFrame frame, String convertedText, boolean toUnicode) {
        String message = toUnicode ?
                "Unicode Text copied to clipboard:\n" + convertedText :
                "Normal Text copied to clipboard:\n" + convertedText;
        JOptionPane.showMessageDialog(frame, message);
    }
}