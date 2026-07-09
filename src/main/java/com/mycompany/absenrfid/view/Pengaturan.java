package com.mycompany.absenrfid.view;
import com.mycompany.absenrfid.services.I18nService;

/**
 * 
 * @author User
 */
public class Pengaturan extends javax.swing.JFrame 
    implements I18nService.I18nChangeListener{

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(Pengaturan.class.getName());

    public Pengaturan() {
        initComponents();
        loadLogo();
        
        I18nService.registerListener(this);
        onLanguageChanged();
    }

    private void loadLogo() {
        try {
            java.awt.Image img = new javax.swing.ImageIcon(
                getClass().getResource("/Logo Profil.png")).getImage()
                    .getScaledInstance(130, 100, java.awt.Image.SCALE_SMOOTH);
            lblLogo.setIcon(new javax.swing.ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Gagal memuat logo: " + e.getMessage());
        }
    }
    
    @Override
    public void onLanguageChanged() {
        lblTitle.setText(I18nService.get("ui.title.pengaturan"));
        lblSubtitle.setText(I18nService.get("ui.subtitle.pengaturan"));
        lblbahasa.setText(I18nService.get("ui.label.bahasa"));
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
        btnNavPengaturan = new javax.swing.JButton();
        btnNavMonitoring = new javax.swing.JButton();
        btnNavVisitor = new javax.swing.JButton();
        btnNavAbsensi = new javax.swing.JButton();
        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        lblAdminName = new javax.swing.JLabel();
        lblAdminProfile = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        pnlwadah2 = new javax.swing.JPanel();
        ComboBoxBahasa = new javax.swing.JComboBox<>();
        lblbahasa = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlSidebar.setBackground(new java.awt.Color(102, 0, 0));
        pnlSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo Profil.png"))); // NOI18N
        lblLogo.setPreferredSize(new java.awt.Dimension(180, 90));
        pnlSidebar.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 130, 100));

        btnNavPengaturan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNavPengaturan.setText("Pengaturan");
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
        lblTitle.setText("Pengaturan");
        pnlHeader.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblSubtitle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(255, 255, 255));
        lblSubtitle.setText("Pengaturan Bahasa");
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

        pnlwadah2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ComboBoxBahasa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ComboBoxBahasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bahasa Indonesia", "English", "Spanyol" }));
        ComboBoxBahasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxBahasaActionPerformed(evt);
            }
        });
        pnlwadah2.add(ComboBoxBahasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 980, 30));

        lblbahasa.setText("Ubah Bahasa Sistem");
        pnlwadah2.add(lblbahasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        pnlContent.add(pnlwadah2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1020, 110));

        getContentPane().add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 1060, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNavMonitoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavMonitoringActionPerformed
        I18nService.unregisterListener(this);
        new Monitoring().setVisible(true); this.dispose();
    }//GEN-LAST:event_btnNavMonitoringActionPerformed

    private void btnNavVisitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavVisitorActionPerformed
        I18nService.unregisterListener(this);
        new DataVisitor().setVisible(true); this.dispose();
    }//GEN-LAST:event_btnNavVisitorActionPerformed

    private void ComboBoxBahasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxBahasaActionPerformed
        String pilihan = (String) ComboBoxBahasa.getSelectedItem();
    switch (pilihan) {
        case "Bahasa Indonesia":
            I18nService.setLocale(new java.util.Locale("id")); break;
        case "English":
            I18nService.setLocale(new java.util.Locale("en")); break;
        case "Spanyol":
            I18nService.setLocale(new java.util.Locale("es")); break;
    }
    javax.swing.JOptionPane.showMessageDialog(this,
        "Bahasa diubah ke: " + pilihan, "Pengaturan",
        javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_ComboBoxBahasaActionPerformed

    private void btnNavAbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavAbsensiActionPerformed
        I18nService.unregisterListener(this);
        new Absensi().setVisible(true); this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_btnNavAbsensiActionPerformed


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception ex) { logger.log(java.util.logging.Level.SEVERE, null, ex); }
        java.awt.EventQueue.invokeLater(() -> new Pengaturan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxBahasa;
    private javax.swing.JButton btnNavAbsensi;
    private javax.swing.JButton btnNavMonitoring;
    private javax.swing.JButton btnNavPengaturan;
    private javax.swing.JButton btnNavVisitor;
    private javax.swing.JLabel lblAdminName;
    private javax.swing.JLabel lblAdminProfile;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblbahasa;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlwadah2;
    // End of variables declaration//GEN-END:variables
}
