package com.mycompany.absenrfid.view;

import com.mycompany.absenrfid.services.VisitorService;
import com.mycompany.absenrfid.objects.Visitor;
import com.mycompany.absenrfid.util.MongoManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * DataVisitor - CRUD data visitor
 * @author NEXA
 */
public class DataVisitor extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(DataVisitor.class.getName());
    private final VisitorService visitorService = new VisitorService();
    private javax.swing.JTextField txtCari;

    public DataVisitor() {
        initComponents();
        setLocationRelativeTo(null);
        loadLogo();

        // Tambah field pencarian realtime ke pnlPencarian
        txtCari = new javax.swing.JTextField();
        txtCari.setBackground(new java.awt.Color(204, 204, 204));
        txtCari.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtCari.setToolTipText("Cari nama / NIM...");
        pnlPencarian.add(txtCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 28));
        txtCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { muatDataKartu(txtCari.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { muatDataKartu(txtCari.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });

        JScrollPane.setBorder(BorderFactory.createEmptyBorder());
        muatDataKartu("");
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

    // ── Ambil data dari MongoDB ───────────────────────────────────
    private List<Document> ambilData(String keyword) {
        MongoCollection<Document> col =
            MongoManager.getDatabase().getCollection("Visitors");
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

    // ── Tampilkan kartu merah ─────────────────────────────────────
    private void muatDataKartu(String keyword) {
        pnlCardContainer.removeAll();
        pnlCardContainer.setBackground(new Color(230, 230, 230));

        List<Document> listData = ambilData(keyword);
        lblVisitorCount.setText(String.valueOf(listData.size()));

        if (listData.isEmpty()) {
            pnlCardContainer.setLayout(new BorderLayout());
            JLabel lbl = new JLabel("Tidak ada data.", SwingConstants.CENTER);
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

    // ── Buat satu kartu merah dengan tombol Edit & Hapus ─────────
    private JPanel buatKartu(Document doc) {
        String nim   = nvl(doc.getString("nim"));
        String nama  = nvl(doc.getString("nama"));
        String kelas = nvl(doc.getString("kelas"));
        String uid   = nvl(doc.getString("uid_rfid"));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(153, 0, 0));
        card.setPreferredSize(new Dimension(326, 120));
        card.setMaximumSize(new Dimension(326, 120));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 6, 10)
        ));

        card.add(buatLabel("Nama: " + nama,         Font.BOLD,  12, Color.WHITE));
        card.add(Box.createVerticalStrut(2));
        card.add(buatLabel("NIM: " + nim,      Font.PLAIN, 11, new Color(210, 210, 210)));
        card.add(Box.createVerticalStrut(2));
        card.add(buatLabel("Kelas: " + kelas,  Font.PLAIN, 11, new Color(210, 210, 210)));
        card.add(Box.createVerticalStrut(2));
        card.add(buatLabel("UID: " + uid,       Font.PLAIN, 10, new Color(190, 190, 190)));
        card.add(Box.createVerticalStrut(6));

        // Panel tombol Edit & Hapus
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        pnlBtn.setBackground(new Color(153, 0, 0));
        pnlBtn.setOpaque(true);

        JButton btnEdit = new JButton("Edit");
        btnEdit.setBackground(new Color(255, 153, 0));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btnEdit.setFocusPainted(false);
        btnEdit.setPreferredSize(new Dimension(60, 22));
        btnEdit.addActionListener(e -> {
            EditVisitor dialog = new EditVisitor((Frame) SwingUtilities.getWindowAncestor(this), true);
            dialog.setNim(nim);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            muatDataKartu(""); // refresh
        });

        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBackground(new Color(204, 0, 0));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btnHapus.setFocusPainted(false);
        btnHapus.setPreferredSize(new Dimension(60, 22));
        btnHapus.addActionListener(e -> {
            int konfirm = JOptionPane.showConfirmDialog(this,
                "Yakin hapus data: " + nama + "?", "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);
            if (konfirm == JOptionPane.YES_OPTION) {
                visitorService.hapus(nim);
                muatDataKartu(""); // refresh
            }
        });

        pnlBtn.add(btnEdit);
        pnlBtn.add(btnHapus);
        card.add(pnlBtn);
        return card;
    }

    private JLabel buatLabel(String t, int style, int size, Color c) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", style, size));
        l.setForeground(c);
        return l;
    }

    private String nvl(String s) { return (s != null && !s.isEmpty()) ? s : "-"; }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnNavPengaturan = new javax.swing.JButton();
        btnNavMonitoring = new javax.swing.JButton();
        btnNavVisitor = new javax.swing.JButton();
        btnNavAbsensi = new javax.swing.JButton();
        pnlContent = new javax.swing.JPanel();
        pnlVisitorCard = new javax.swing.JPanel();
        lblVisitorCount = new javax.swing.JLabel();
        lblVisitorLabel = new javax.swing.JLabel();
        JScrollPane = new javax.swing.JScrollPane();
        pnlCardContainer = new javax.swing.JPanel();
        cbFilterProdi = new javax.swing.JComboBox<>();
        cbFilterKelas = new javax.swing.JComboBox<>();
        pnlPencarian = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        lblAdminName = new javax.swing.JLabel();
        lblAdminProfile = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));
        pnlContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVisitorCount.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        lblVisitorCount.setText("4");
        pnlVisitorCard.add(lblVisitorCount);

        pnlContent.add(pnlVisitorCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 200, 60));

        lblVisitorLabel.setText("Jumlah Visitor");
        pnlContent.add(lblVisitorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 10));

        JScrollPane.setBackground(new java.awt.Color(153, 153, 153));
        JScrollPane.setViewportView(pnlCardContainer);

        pnlContent.add(JScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 1020, 420));

        cbFilterProdi.setBackground(new java.awt.Color(204, 204, 204));
        cbFilterProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Teknik Informatika", "Teknik Elektro", "Teknik Komputer", "Teknik Mesin" }));
        cbFilterProdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterProdiActionPerformed(evt);
            }
        });
        pnlContent.add(cbFilterProdi, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 140, -1));

        cbFilterKelas.setBackground(new java.awt.Color(204, 204, 204));
        cbFilterKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4 C", "4 A", "4 B", "4 D" }));
        cbFilterKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterKelasActionPerformed(evt);
            }
        });
        pnlContent.add(cbFilterKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 80, -1));

        pnlPencarian.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        pnlPencarian.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 0, 20, 30));

        pnlContent.add(pnlPencarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 200, 30));

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnlContent.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 90, 30));

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        pnlContent.add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, 100, 30));

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        pnlContent.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 90, 30));

        btnSimpan.setBackground(new java.awt.Color(51, 204, 0));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        pnlContent.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, -1, -1));

        getContentPane().add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 1060, 620));

        pnlHeader.setBackground(new java.awt.Color(153, 0, 0));
        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Data Visitor");
        pnlHeader.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblSubtitle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(255, 255, 255));
        lblSubtitle.setText("Menejemen Data Visitor CRUD");
        pnlHeader.add(lblSubtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        lblAdminName.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblAdminName.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminName.setText("Admin");
        pnlHeader.add(lblAdminName, new org.netbeans.lib.awtextra.AbsoluteConstraints(952, 40, -1, 20));

        lblAdminProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/profil.png"))); // NOI18N
        pnlHeader.add(lblAdminProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 30, 40, 40));

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 1060, 100));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNavMonitoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavMonitoringActionPerformed
        new Monitoring().setVisible(true); this.dispose();
    }//GEN-LAST:event_btnNavMonitoringActionPerformed

    private void btnNavVisitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavVisitorActionPerformed
        // sudah di halaman ini
    }//GEN-LAST:event_btnNavVisitorActionPerformed

    private void btnNavAbsensiActionPerformed(java.awt.event.ActionEvent evt) {
        new Absensi().setVisible(true); this.dispose();
    }

    private void btnNavPengaturanActionPerformed(java.awt.event.ActionEvent evt) {
        new Pengaturan().setVisible(true); this.dispose();
    }

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        TambahVisitor dialog = new TambahVisitor((Frame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        muatDataKartu(""); // refresh setelah tambah
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        EditVisitor dialog = new EditVisitor((Frame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        muatDataKartu(""); // refresh setelah update
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        muatDataKartu(""); // reload semua data
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSimpanFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanFilterActionPerformed
        
    }//GEN-LAST:event_btnSimpanFilterActionPerformed

    private void cbFilterKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterKelasActionPerformed
    }//GEN-LAST:event_cbFilterKelasActionPerformed

    private void cbFilterProdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterProdiActionPerformed
    }//GEN-LAST:event_cbFilterProdiActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
       String k = (String) cbFilterKelas.getSelectedItem();
        muatDataKartu((k == null || k.startsWith("Semua")) ? "" : k.trim()); // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception ex) { logger.log(java.util.logging.Level.SEVERE, null, ex); }
        java.awt.EventQueue.invokeLater(() -> new DataVisitor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JScrollPane;
    private javax.swing.JButton btnNavAbsensi;
    private javax.swing.JButton btnNavMonitoring;
    private javax.swing.JButton btnNavPengaturan;
    private javax.swing.JButton btnNavVisitor;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbFilterKelas;
    private javax.swing.JComboBox<String> cbFilterProdi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAdminName;
    private javax.swing.JLabel lblAdminProfile;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblVisitorCount;
    private javax.swing.JLabel lblVisitorLabel;
    private javax.swing.JPanel pnlCardContainer;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlPencarian;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlVisitorCard;
    // End of variables declaration//GEN-END:variables
}
