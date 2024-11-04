package com.tolgagureli.turkishunicodeconverter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class UnicodeConverterToolWindowFactory extends AnAction {
    // Tablo verileri için sabitler
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

    // Sütun başlıkları için sabit
    private static final String[] COLUMN_NAMES = {"Turkish Letter", "Unicode Value"};

    // Pencere boyutları için sabitler
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 350;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // Pencereyi oluştur ve göster
        createAndShowFrame();
    }

    private void createAndShowFrame() {
        // Yeni bir pencere oluştur
        JFrame frame = new JFrame("Turkish Unicode Converter");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Tabloyu oluştur
        JTable table = createTable();
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Pencere boyutunu ve konumunu ayarla
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null); // Pencereyi ekranın ortasında aç
        frame.setVisible(true); // Pencereyi göster
    }

    private JTable createTable() {
        // Tablo modeli oluştur ve hücrelerin düzenlenemez olmasını sağla
        DefaultTableModel tableModel = new DefaultTableModel(DATA, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hücrelerin düzenlenmesine izin verme
            }
        };
        return new JTable(tableModel); // Tabloyu döndür
    }
}