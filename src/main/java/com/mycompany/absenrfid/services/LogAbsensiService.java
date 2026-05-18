package com.mycompany.absenrfid.services;

import absenRFID.DAO.GenericDAO;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.List;

public class LogAbsensiService {
    private final GenericDAO<LogAbsensi> logDAO;

    public LogAbsensiService() {
        // Inisialisasi GenericDAO mengarah ke koleksi "LogAbsensi"
        this.logDAO = new GenericDAO<>("LogAbsensi", LogAbsensi.class) {
            @Override
            public long countAll() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
    }

    /**
     * 
     */
    public long hitungTotalVisitor() {
        return logDAO.countAll();
    }

    /**
     * 
     */
    public void tampilLogAbsensi(JPanel targetPanel, String keyword) {
        // 1. Bersihkan panel terlebih dahulu dari data lama
        targetPanel.removeAll();
        
        List<LogAbsensi> listData;
        
        // 2. Jika ada keyword pencarian, lakukan filter. Jika tidak, ambil semua.
        if (keyword != null && !keyword.trim().isEmpty()) {
            Bson filter = Filters.or(
                Filters.regex("namaLengkap", keyword, "i"),
                Filters.regex("nim", keyword, "i"),
                Filters.regex("uidRfid", keyword, "i")
            );
            listData = logDAO.findMany(filter);
        } else {
            listData = logDAO.findAll();
        }

        // 3. Set layout panel target agar komponen tersusun rapi ke bawah
        targetPanel.setLayout(new GridLayout(0, 1, 5, 5));

        // 4. Looping data menggunakan fungsi getter yang ada di model kamu
        for (LogAbsensi log : listData) {
            // DISESUAIKAN: Menggunakan getLocalDateTimewaktuTap() dan getNamaLengkap()
            String dataTeks = "  [ " + log.getLocalDateTimewaktuTap() + " ]  " 
                            + log.getNim() + " - " 
                            + log.getNamaLengkap() + " (" 
                            + log.getKelas() + ") -> Status: " 
                            + log.getStatus();
            
            JLabel lblData = new JLabel(dataTeks);
            lblData.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            
            // Tambahkan baris data ke panel
            targetPanel.add(lblData);
        }

        // 5. Refresh visual panel
        targetPanel.revalidate();
        targetPanel.repaint();
    }

    private static class LogAbsensi {

        public LogAbsensi() {
        }

        private String getLocalDateTimewaktuTap() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getNim() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getNamaLengkap() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getKelas() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getStatus() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}