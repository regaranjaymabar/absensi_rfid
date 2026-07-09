package com.mycompany.absenrfid.view;

import com.mycompany.absenrfid.util.MongoManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mycompany.absenrfid.serial.SerialDataHandler;
import com.mycompany.absenrfid.services.DigitalClockService;
import com.mycompany.absenrfid.services.SerialService;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mycompany.absenrfid.services.I18nService;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author user
 */
public class Monitoring extends javax.swing.JFrame 
    implements I18nService.I18nChangeListener{

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(Monitoring.class.getName());
    private Thread clockThread;
    private SerialDataHandler<String> rfidHandler;

    /**
     * Creates new form Monitoring
     */
    public Monitoring() {
        initComponents();
        setLocationRelativeTo(null);
        loadLogo();
        startClock();
        startSerialListener();
                rfidHandler = rawUid -> {
            SwingUtilities.invokeLater(() -> muatDataKartu("", ""));
            };
            SerialService.getInstance().addHandler(rfidHandler);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        muatDataKartu("", "");
        
        I18nService.registerListener(this);
        onLanguageChanged();
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

    private void startClock() {
        DigitalClockService clockService = new DigitalClockService(lblClock, "EEEE, d MMMM yyyy, HH:mm:ss");
    clockThread = clockService.getThread();
    clockThread.setName("Clock-Thread");
    clockThread.setDaemon(true); // mati otomatis saat aplikasi ditutup
    clockThread.start();
}
    
    private void startSerialListener() {
    rfidHandler = (String rawUid) -> {
        // Data RFID diterima dari hardware
        // SwingUtilities.invokeLater wajib untuk update UI dari thread latar
        SwingUtilities.invokeLater(() -> muatDataKartu("", ""));
    };
    SerialService.getInstance().addHandler(rfidHandler);
}



    /**
     * Ambil data dari MongoDB dengan filter kelas dan/atau prodi.
     */
    private List<Document> ambilData(String kelasFilter, String prodiFilter) {
        MongoCollection<Document> col =
            MongoManager.getDatabase().getCollection("LogAbsensi");
        List<Document> hasil = new ArrayList<>();
        try {
            List<Bson> filters = new ArrayList<>();

            if (kelasFilter != null && !kelasFilter.trim().isEmpty()) {
                filters.add(Filters.regex("kelas", kelasFilter.trim(), "i"));
            }
            if (prodiFilter != null && !prodiFilter.trim().isEmpty()) {
                filters.add(Filters.regex("prodi", prodiFilter.trim(), "i"));
            }

            if (!filters.isEmpty()) {
                col.find(Filters.and(filters)).into(hasil);
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

    /**
     * Muat data ke panel card dengan filter kelas dan prodi.
     */
    private void muatDataKartu(String kelasFilter, String prodiFilter) {
        pnlCardContainer.removeAll();
        pnlCardContainer.setBackground(new Color(230, 230, 230));

        List<Document> listData = ambilData(kelasFilter, prodiFilter);
        lblVisitorCount.setText(String.valueOf(listData.size()));

        if (listData.isEmpty()) {
            pnlCardContainer.setLayout(new BorderLayout());
            JLabel lbl = new JLabel("Tidak ada data.", SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            lbl.setForeground(Color.GRAY);
            pnlCardContainer.add(lbl, BorderLayout.CENTER);
        } else {
            pnlCardContainer.setLayout(new java.awt.GridLayout(0, 3, 8, 8));
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
        card.add(buatLabel("Waktu: "  + nvl(doc.getString("waktu")),  Font.PLAIN, 11, new Color(190, 190, 190)));
        card.add(Box.createVerticalStrut(2));
        card.add(buatLabel("NIM: "    + nvl(doc.getString("nim")),    Font.PLAIN, 11, new Color(210, 210, 210)));
        card.add(Box.createVerticalStrut(2));
        card.add(buatLabel("Nama: "   + nvl(doc.getString("nama")),   Font.BOLD,  12, Color.WHITE));
        card.add(Box.createVerticalStrut(2));
        card.add(buatLabel("Kelas: "  + nvl(doc.getString("kelas")),  Font.PLAIN, 11, new Color(210, 210, 210)));
        return card;
    }

    private JLabel buatLabel(String t, int style, int size, Color c) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", style, size));
        l.setForeground(c);
        return l;
    }

    private String nvl(String s) { return (s != null && !s.isEmpty()) ? s : "-"; }

    /** Hentikan timer sebelum pindah form agar tidak memory leak / konflik */
    private void navigateTo(javax.swing.JFrame target) {
    // Hentikan clock thread
    if (clockThread != null) clockThread.interrupt();
    // Bersihkan handler RFID agar tidak memory leak
    if (rfidHandler != null) {
        SerialService.getInstance().removeHandler(rfidHandler);
    }
    target.setVisible(true);
    I18nService.unregisterListener(this);
    this.dispose();
}
    @Override
    public void onLanguageChanged() {
        lblTitle.setText(I18nService.get("ui.title.monitoring"));
        lblSubtitle.setText(I18nService.get("ui.subtitle.monitoring"));
        lblVisitorLabel.setText(I18nService.get("ui.label.visitor"));
        btnSimpanFilter.setText(I18nService.get("ui.btn.save"));
        btnNavMonitoring.setText(I18nService.get("ui.nav.monitoring"));
        btnNavVisitor.setText(I18nService.get("ui.nav.datavisitor"));
        btnNavAbsensi.setText(I18nService.get("ui.nav.absensi"));
        btnNavPengaturan.setText(I18nService.get("ui.nav.pengaturan"));
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnNavMonitoring = new javax.swing.JButton();
        btnNavVisitor = new javax.swing.JButton();
        btnNavAbsensi = new javax.swing.JButton();
        btnNavPengaturan = new javax.swing.JButton();
        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        lblAdminName = new javax.swing.JLabel();
        lblAdminProfile = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        lblClock = new javax.swing.JLabel();
        pnlVisitorCard = new javax.swing.JPanel();
        lblVisitorCount = new javax.swing.JLabel();
        cbFilterKelas = new javax.swing.JComboBox<>();
        cbFilterProdi = new javax.swing.JComboBox<>();
        btnSimpanFilter = new javax.swing.JButton();
        lblVisitorLabel = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        pnlCardContainer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1280, 720));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlSidebar.setBackground(new java.awt.Color(102, 0, 0));
        pnlSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo Profil.png"))); // NOI18N
        lblLogo.setPreferredSize(new java.awt.Dimension(180, 90));
        pnlSidebar.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 130, 100));

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

        btnNavPengaturan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNavPengaturan.setText("Pengaturan");
        btnNavPengaturan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavPengaturanActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnNavPengaturan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 160, 30));

        getContentPane().add(pnlSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 720));

        pnlHeader.setBackground(new java.awt.Color(153, 0, 0));
        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("MONITORING");
        pnlHeader.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblSubtitle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(255, 255, 255));
        lblSubtitle.setText("Pantau kehadiran siswa secara realtime");
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

        lblClock.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        lblClock.setText("23 Jan 2026 10:08");
        pnlContent.add(lblClock, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 600, 60));

        lblVisitorCount.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        lblVisitorCount.setText("4");
        pnlVisitorCard.add(lblVisitorCount);

        pnlContent.add(pnlVisitorCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 200, 60));

        cbFilterKelas.setBackground(new java.awt.Color(204, 204, 204));
        cbFilterKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4 C", "4 A", "4 B", "4 D" }));
        cbFilterKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterKelasActionPerformed(evt);
            }
        });
        pnlContent.add(cbFilterKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 80, -1));

        cbFilterProdi.setBackground(new java.awt.Color(204, 204, 204));
        cbFilterProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Teknik Informatika", "Teknik Elektro", "Teknik Komputer", "Teknik Mesin" }));
        cbFilterProdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterProdiActionPerformed(evt);
            }
        });
        pnlContent.add(cbFilterProdi, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 140, -1));

        btnSimpanFilter.setBackground(new java.awt.Color(51, 204, 0));
        btnSimpanFilter.setText("Simpan");
        btnSimpanFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanFilterActionPerformed(evt);
            }
        });
        pnlContent.add(btnSimpanFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, -1));

        lblVisitorLabel.setText("Visitor Hari Ini");
        pnlContent.add(lblVisitorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jScrollPane.setViewportView(pnlCardContainer);

        pnlContent.add(jScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 1020, 420));

        getContentPane().add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 1060, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNavVisitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavVisitorActionPerformed
        navigateTo(new DataVisitor());
    }//GEN-LAST:event_btnNavVisitorActionPerformed

    private void cbFilterKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterKelasActionPerformed
        // Tidak langsung filter — tunggu tombol Simpan ditekan
    }//GEN-LAST:event_cbFilterKelasActionPerformed

    private void btnNavMonitoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavMonitoringActionPerformed
        // Sudah di halaman Monitoring, tidak perlu aksi
    }//GEN-LAST:event_btnNavMonitoringActionPerformed

    private void cbFilterProdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterProdiActionPerformed
        // Tidak langsung filter — tunggu tombol Simpan ditekan
    }//GEN-LAST:event_cbFilterProdiActionPerformed

    private void btnSimpanFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanFilterActionPerformed
        String kelas = cbFilterKelas.getSelectedItem().toString();
        String prodi = cbFilterProdi.getSelectedItem().toString();

        // "Semua" berarti tidak ada filter
        String kelasFilter = kelas.equalsIgnoreCase("Semua Kelas") ? "" : kelas;
        String prodiFilter = prodi.equalsIgnoreCase("Semua Jurusan") ? "" : prodi;

        muatDataKartu(kelasFilter, prodiFilter);
    }//GEN-LAST:event_btnSimpanFilterActionPerformed

    private void btnNavAbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavAbsensiActionPerformed
        navigateTo(new Absensi());
    }//GEN-LAST:event_btnNavAbsensiActionPerformed

    private void btnNavPengaturanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavPengaturanActionPerformed
        navigateTo(new Pengaturan());
    }//GEN-LAST:event_btnNavPengaturanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNavAbsensi;
    private javax.swing.JButton btnNavMonitoring;
    private javax.swing.JButton btnNavPengaturan;
    private javax.swing.JButton btnNavVisitor;
    private javax.swing.JButton btnSimpanFilter;
    private javax.swing.JComboBox<String> cbFilterKelas;
    private javax.swing.JComboBox<String> cbFilterProdi;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel lblAdminName;
    private javax.swing.JLabel lblAdminProfile;
    private javax.swing.JLabel lblClock;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSubtitle;
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
