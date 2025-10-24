package com.tolgagureli.turkishunicodeconverter;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.*;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class UnicodeConverterToolWindowFactory implements ToolWindowFactory {

    private static final Object[][] DATA = {
            {"ç", "\\u00E7"}, {"Ç", "\\u00C7"},
            {"ğ", "\\u011F"}, {"Ğ", "\\u011E"},
            {"ö", "\\u00F6"}, {"Ö", "\\u00D6"},
            {"ş", "\\u015F"}, {"Ş", "\\u015E"},
            {"ü", "\\u00FC"}, {"Ü", "\\u00DC"},
            {"ı", "\\u0131"}, {"İ", "\\u0130"}
    };
    private static final String[] COLUMN_NAMES = {"Turkish Letter", "Unicode Value"};

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JPanel mainPanel = createMainPanel();
        ContentFactory contentFactory = ContentFactory.getInstance();
        com.intellij.ui.content.Content content = contentFactory.createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(JBUI.Borders.empty(10));
        panel.setBackground(JBColor.PanelBackground);

        JLabel title = new JLabel("🇹🇷 Turkish Unicode Converter", SwingConstants.CENTER);
        title.setFont(JBUI.Fonts.label().asBold().deriveFont(16f));
        panel.add(title, BorderLayout.NORTH);

        JTable table = new JBTable(new DefaultTableModel(DATA, COLUMN_NAMES));
        table.setRowHeight(25);
        table.setFont(JBUI.Fonts.label(13));
        JBScrollPane tablePane = new JBScrollPane(table);
        panel.add(tablePane, BorderLayout.CENTER);

        JPanel converterPanel = createConverterPanel();
        panel.add(converterPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createConverterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(JBColor.PanelBackground);
        panel.setBorder(JBUI.Borders.empty(10, 5));

        JLabel toUnicodeLabel = new JLabel("Convert Turkish → Unicode:");
        JBTextField inputField = new JBTextField();
        JButton convertButton = new JButton("Copy Unicode to Clipboard");
        convertButton.addActionListener(e -> {
            String result = convertToUnicode(inputField.getText());
            copyToClipboard(result);
            JOptionPane.showMessageDialog(null, result, "Converted Unicode", JOptionPane.INFORMATION_MESSAGE);
        });

        JLabel toTextLabel = new JLabel("Convert Unicode → Turkish:");
        JBTextField unicodeField = new JBTextField();
        JButton reverseButton = new JButton("Copy Text to Clipboard");
        reverseButton.addActionListener(e -> {
            String result = convertToText(unicodeField.getText());
            copyToClipboard(result);
            JOptionPane.showMessageDialog(null, result, "Converted Text", JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(toUnicodeLabel);
        panel.add(inputField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(convertButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(toTextLabel);
        panel.add(unicodeField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(reverseButton);

        return panel;
    }

    private String convertToUnicode(String text) {
        for (Object[] map : DATA)
            text = text.replace(map[0].toString(), map[1].toString());
        return text;
    }

    private String convertToText(String text) {
        for (Object[] map : DATA)
            text = text.replace(map[1].toString(), map[0].toString());
        return text;
    }

    private void copyToClipboard(String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
    }
}
