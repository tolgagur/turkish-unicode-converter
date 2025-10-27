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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

import static javax.swing.SwingConstants.CENTER;

public class UnicodeConverterToolWindowFactory implements ToolWindowFactory {

    // ToolWindow ID: plugin.xml'deki id ile eşleşmelidir
    public static final String TOOL_WINDOW_ID = "TurkishUnicodeConverter";

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
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(JBColor.PanelBackground);

        // Header section
        JPanel headerPanel = createHeaderPanel();
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content with split layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(280);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);

        // Table section
        JPanel tableSection = createTableSection();
        splitPane.setTopComponent(tableSection);

        // Converter section
        JPanel converterSection = createConverterSection();
        splitPane.setBottomComponent(converterSection);

        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new JBColor(new Color(60, 63, 65), new Color(60, 63, 65)));
        header.setBorder(JBUI.Borders.empty(15, 20));

        JLabel title = new JLabel("🇹🇷 Turkish Unicode Converter");
        title.setFont(JBUI.Fonts.label().asBold().deriveFont(18f));
        title.setForeground(new JBColor(new Color(187, 187, 187), new Color(187, 187, 187)));

        JLabel subtitle = new JLabel("Convert between Turkish characters and Unicode escape sequences");
        subtitle.setFont(JBUI.Fonts.label(12));
        subtitle.setForeground(new JBColor(new Color(140, 140, 140), new Color(140, 140, 140)));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new JBColor(new Color(60, 63, 65), new Color(60, 63, 65)));
        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitle);

        header.add(textPanel, BorderLayout.WEST);

        return header;
    }

    private JPanel createTableSection() {
        JPanel section = new JPanel(new BorderLayout(0, 10));
        section.setBackground(JBColor.PanelBackground);
        section.setBorder(JBUI.Borders.empty(15, 20, 10, 20));

        JLabel sectionTitle = new JLabel("Reference Table");
        sectionTitle.setFont(JBUI.Fonts.label().asBold().deriveFont(14f));
        section.add(sectionTitle, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(DATA, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JBTable table = new JBTable(model);
        table.setRowHeight(30);
        table.setFont(JBUI.Fonts.label(13));
        table.setShowGrid(true);
        table.setGridColor(new JBColor(new Color(80, 80, 80), new Color(80, 80, 80)));

        // Center align cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);

        JBScrollPane scrollPane = new JBScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new JBColor(new Color(80, 80, 80), new Color(80, 80, 80))));
        section.add(scrollPane, BorderLayout.CENTER);

        return section;
    }

    private JPanel createConverterSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(JBColor.PanelBackground);
        section.setBorder(JBUI.Borders.empty(10, 20, 20, 20));

        // Turkish to Unicode converter
        JPanel toUnicodePanel = createConverterCard(
                "Turkish → Unicode",
                "Enter Turkish text with special characters",
                "Convert to Unicode",
                this::convertToUnicode
        );

        // Unicode to Turkish converter
        JPanel toTextPanel = createConverterCard(
                "Unicode → Turkish",
                "Enter Unicode escape sequences (e.g., \\u00E7)",
                "Convert to Turkish",
                this::convertToText
        );

        section.add(toUnicodePanel);
        section.add(Box.createVerticalStrut(15));
        section.add(toTextPanel);

        return section;
    }

    private JPanel createConverterCard(String title, String placeholder, String buttonText,
                                       java.util.function.Function<String, String> converter) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new JBColor(new Color(70, 73, 75), new Color(70, 73, 75)));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new JBColor(new Color(90, 90, 90), new Color(90, 90, 90))),
                JBUI.Borders.empty(15)
        ));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(JBUI.Fonts.label().asBold().deriveFont(13f));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Input field
        JBTextField inputField = new JBTextField();
        inputField.getEmptyText().setText(placeholder);
        inputField.setFont(JBUI.Fonts.label(13));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Button
        JButton button = new JButton(buttonText);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFont(JBUI.Fonts.label().asBold());
        button.addActionListener(e -> {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter some text first.",
                        "Empty Input",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String result = converter.apply(input);
            copyToClipboard(result);
            showResultDialog(result, "✓ Copied to Clipboard");
        });

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(inputField);
        card.add(Box.createVerticalStrut(10));
        card.add(button);

        return card;
    }

    private void showResultDialog(String result, String title) {
        JTextArea textArea = new JTextArea(result);
        textArea.setEditable(false);
        textArea.setFont(JBUI.Fonts.label(13));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(JBUI.Borders.empty(10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private String convertToUnicode(String text) {
        for (Object[] map : DATA) {
            text = text.replace(map[0].toString(), map[1].toString());
        }
        return text;
    }

    private String convertToText(String text) {
        for (Object[] map : DATA) {
            text = text.replace(map[1].toString(), map[0].toString());
        }
        return text;
    }

    private void copyToClipboard(String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new StringSelection(text), null);
    }
}