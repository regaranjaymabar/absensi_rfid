package com.mycompany.absenrfid.view;

import com.mycompany.absenrfid.util.MongoManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


/**
 *
 * @author user
 */
public class Absensi extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(Absensi.class.getName());

    public Absensi() {
        initComponents();
        setLocationRelativeTo(null);
        loadLogo();
        JScrollPane.setBorder(BorderFactory.createEmptyBorder());
        muatDataAbsensi("");
    }
    
    private void loadLogo() {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Logo Profil.png"));
            lblLogo.setIcon(new ImageIcon(
                icon.getImage().getScaledInstance(130, 100, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            System.out.println("Gagal memuat logo: " + e.getMessage());
        }
    }
    
    private List<Document> ambilData(String keyword) {
        MongoCollection<Document> col =
            MongoManager.getDatabase().getCollection("LogAbsensi");
        List<Document> hasil = new ArrayList<>();
        try {
            if (keyword != null && !keyword.trim().isEmpty()) {
                Bson f = Filters.or(
                    Filters.regex("nama",  keyword, "i"),
                    Filters.regex("nim",   keyword, "i"),
                    Filters.regex("kelas", keyword, "i")
                );
                col.find(f).into(hasil);
            } else {
                col.find().into(hasil);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Gagal koneksi MongoDB:\n" + e.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        return hasil;
    }
    
    private void muatDataAbsensi(String keyword) {
        pnlCardContainer.removeAll();
        pnlCardContainer.setBackground(new Color(230, 230, 230));

        List<Document> listData = ambilData(keyword);
        lblVisitorCount.setText(String.valueOf(listData.size()));

        if (listData.isEmpty()) {
            pnlCardContainer.setLayout(new BorderLayout());
            JLabel lbl = new JLabel("Tidak ada data absensi.", SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            lbl.setForeground(Color.GRAY);
            pnlCardContainer.add(lbl, BorderLayout.CENTER);
        } else {
            pnlCardContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
            for (Document doc : listData) {
                pnlCardContainer.add(buatKartu(doc));
            }
        }
        pnlCardContainer.revalidate();
        pnlCardContainer.repaint();
    }
    
    private JPanel buatKartu(Document doc) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(153, 0, 0));
        card.setPreferredSize(new Dimension(326, 90));
        card.setMaximumSize(new Dimension(326, 90));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        card.add(buatLabel("Waktu: " + nvl(doc.getString("waktu")),       Font.PLAIN, 11, new Color(190, 190, 190)));
        card.add(Box.createVerticalStrut(2));
        card.add(buatLabel("NIM: " + nvl(doc.getString("nim")),    Font.PLAIN, 11, new Color(210, 210, 210)));
        card.add(Box.createVerticalStrut(2));
        card.add(buatLabel("Nama: " + nvl(doc.getString("nama")),         Font.BOLD,  12, Color.WHITE));
        card.add(Box.createVerticalStrut(2));
        card.add(buatLabel("Kelas: " + nvl(doc.getString("kelas")), Font.PLAIN, 11, new Color(210, 210, 210)));
        return card;
    }

    private void exportCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new java.io.File("absensi_export.csv"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
            fw.write("Waktu,NIM,Nama,Kelas,Status\n");
            for (Document d : ambilData("")) {
                fw.write(q(d.getString("waktu")) + "," + q(d.getString("nim")) + ","
                    + q(d.getString("nama")) + "," + q(d.getString("kelas")) + ","
                    + q(d.getString("status")) + "\n");
            }
            JOptionPane.showMessageDialog(this, "Export berhasil!\n" + fc.getSelectedFile().getPath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String q(String s) { return "\"" + (s != null ? s.replace("\"","\"\"") : "") + "\""; }
    private JLabel buatLabel(String t, int style, int size, Color c) {
        JLabel l = new JLabel(t); l.setFont(new Font("Segoe UI", style, size)); l.setForeground(c); return l;
    }
    private String nvl(String s) { return (s != null && !s.isEmpty()) ? s : "-"; }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSidebar = new javax.swing.JPanel();
        pnlSidebar.setBackground(new java.awt.Color(102, 0, 0));
        pnlSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo Profil.png"))); // NOI18N
        lblLogo.setPreferredSize(new java.awt.Dimension(180, 90));
        pnlSidebar.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 130, 100));

        btnNavPengaturan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNavPengaturan.setText("Pengaturan");
        btnNavPengaturan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavPengaturanActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnNavPengaturan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 160, 30));

        btnNavMonitoring.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNavMonitoring.setText("Monitoring");
        btnNavMonitoring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavMonitoringActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnNavMonitoring, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 160, 30));

        btnNavVisitor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNavVisitor.setText("Data Visitor");
        btnNavVisitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavVisitorActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnNavVisitor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 160, 30));

        btnNavAbsensi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNavAbsensi.setText("Absensi");
        btnNavAbsensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavAbsensiActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnNavAbsensi, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 160, 30));

        getContentPane().add(pnlSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 720));

        pnlHeader.setBackground(new java.awt.Color(153, 0, 0));
        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Absensi");
        pnlHeader.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblSubtitle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(255, 255, 255));
        lblSubtitle.setText("Absensi Dan Laporan hasil absen");
        pnlHeader.add(lblSubtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        lblAdminName.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblAdminName.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminName.setText("Admin");
        pnlHeader.add(lblAdminName, new org.netbeans.lib.awtextra.AbsoluteConstraints(952, 40, -1, 20));

        lblAdminProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/profil.png"))); // NOI18N
        pnlHeader.add(lblAdminProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 30, 40, 40));

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 1060, 100));

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));
        pnlContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVisitorCount.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        lblVisitorCount.setText("4");
        pnlVisitorCard.add(lblVisitorCount);

        pnlContent.add(pnlVisitorCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 200, 60));

        btnSimpanFilter.setBackground(new java.awt.Color(51, 204, 0));
        btnSimpanFilter.setText("Simpan");
        btnSimpanFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanFilterActionPerformed(evt);
            }
        });
        pnlContent.add(btnSimpanFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, -1, -1));

        lblVisitorLabel.setText("Jumlah Visitor");
        pnlContent.add(lblVisitorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));

        JScrollPane.setBackground(new java.awt.Color(153, 153, 153));

        pnlCardContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        JScrollPane.setViewportView(pnlCardContainer);

        pnlContent.add(JScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 1020, 420));

        lblTgl.setText("Tanggal");
        pnlContent.add(lblTgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, -1));

        btnExportcsv.setBackground(new java.awt.Color(102, 102, 255));
        btnExportcsv.setText("Export CSV");
        btnExportcsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportcsvActionPerformed(evt);
            }
        });
        pnlContent.add(btnExportcsv, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, -1, 30));

        btnTampilkanAbsen.setBackground(new java.awt.Color(255, 255, 0));
        btnTampilkanAbsen.setText("Tampilkan Menu Absen");
        btnTampilkanAbsen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanAbsenActionPerformed(evt);
            }
        });
        pnlContent.add(btnTampilkanAbsen, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 120, 160, 40));

        jDate.setAutoscrolls(true);
        pnlContent.add(jDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 140, -1));

        cbFilterProdi.setBackground(new java.awt.Color(204, 204, 204));
        cbFilterProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jurusan", "Teknik Informatika", "Teknik Komputer", "Teknik Mesin", "Teknik Elektro" }));
        cbFilterProdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterProdiActionPerformed(evt);
            }
        });
        pnlContent.add(cbFilterProdi, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 130, -1));

        cbFilterKelas.setBackground(new java.awt.Color(204, 204, 204));
        cbFilterKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kelas", "4 A", "4 B", "4 C", "4 D" }));
        cbFilterKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterKelasActionPerformed(evt);
            }
        });
        pnlContent.add(cbFilterKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        getContentPane().add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 1060, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNavMonitoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavMonitoringActionPerformed
        new Monitoring().setVisible(true); this.dispose();       // TODO add your handling code here:
    }//GEN-LAST:event_btnNavMonitoringActionPerformed

    private void btnNavVisitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavVisitorActionPerformed
        new DataVisitor().setVisible(true); this.dispose(); // TODO add your handling code here:
    }//GEN-LAST:event_btnNavVisitorActionPerformed

    private void btnExportcsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportcsvActionPerformed
        exportCSV();        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportcsvActionPerformed

    private void btnSimpanFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanFilterActionPerformed
        String k = (String) cbFilterKelas.getSelectedItem();
        muatDataAbsensi((k == null || k.equals("Kelas")) ? "" : k.trim());        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanFilterActionPerformed

    private void btnTampilkanAbsenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanAbsenActionPerformed
        muatDataAbsensi("");        // TODO add your handling code here:
    }//GEN-LAST:event_btnTampilkanAbsenActionPerformed

    private void btnNavAbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavAbsensiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNavAbsensiActionPerformed

    private void btnNavPengaturanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavPengaturanActionPerformed
        new Pengaturan().setVisible(true); this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnNavPengaturanActionPerformed

    private void cbFilterProdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterProdiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterProdiActionPerformed

    private void cbFilterKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterKelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterKelasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception ex) { logger.log(java.util.logging.Level.SEVERE, null, ex); }
        java.awt.EventQueue.invokeLater(() -> new com.mycompany.absenrfid.view.Absensi().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JScrollPane;
    private javax.swing.JButton btnExportcsv;
    private javax.swing.JButton btnNavAbsensi;
    private javax.swing.JButton btnNavMonitoring;
    private javax.swing.JButton btnNavPengaturan;
    private javax.swing.JButton btnNavVisitor;
    private javax.swing.JButton btnSimpanFilter;
    private javax.swing.JButton btnTampilkanAbsen;
    private javax.swing.JComboBox<String> cbFilterKelas;
    private javax.swing.JComboBox<String> cbFilterProdi;
    private com.toedter.calendar.JDateChooser jDate;
    private javax.swing.JLabel lblAdminName;
    private javax.swing.JLabel lblAdminProfile;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTgl;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblVisitorCount;
    private javax.swing.JLabel lblVisitorLabel;
    private javax.swing.JPanel pnlCardContainer;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlVisitorCard;
    // End of variables declaration//GEN-END:variables
}
