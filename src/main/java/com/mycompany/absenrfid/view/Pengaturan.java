package com.mycompany.absenrfid.view;

/**
 * Pengaturan - halaman pengaturan sistem
 * @author NEXA
 */
public class Pengaturan extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(Pengaturan.class.getName());

    public Pengaturan() {
        initComponents();
        loadLogo();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlSidebar.setBackground(new java.awt.Color(102, 0, 0));
        pnlSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnlSidebar.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 130, 100));

        btnNavPengaturan.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnNavPengaturan.setText("Pengaturan");
        btnNavPengaturan.setFocusPainted(false);
        btnNavPengaturan.setBorderPainted(false);
        pnlSidebar.add(btnNavPengaturan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 160, 30));

        btnNavMonitoring.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnNavMonitoring.setText("Monitoring");
        btnNavMonitoring.addActionListener(evt -> btnNavMonitoringActionPerformed(evt));
        pnlSidebar.add(btnNavMonitoring, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 160, 30));

        btnNavVisitor.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnNavVisitor.setText("Data Visitor");
        btnNavVisitor.addActionListener(evt -> btnNavVisitorActionPerformed(evt));
        pnlSidebar.add(btnNavVisitor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 160, 30));

        btnNavAbsensi.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnNavAbsensi.setText("Absensi");
        btnNavAbsensi.addActionListener(evt -> btnNavAbsensiActionPerformed(evt));
        pnlSidebar.add(btnNavAbsensi, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 160, 30));

        getContentPane().add(pnlSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 720));

        pnlHeader.setBackground(new java.awt.Color(153, 0, 0));
        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setForeground(java.awt.Color.WHITE);
        lblTitle.setText("Pengaturan");
        pnlHeader.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        lblSubtitle.setFont(new java.awt.Font("Arial", 0, 12));
        lblSubtitle.setForeground(java.awt.Color.WHITE);
        lblSubtitle.setText("Pengaturan Bahasa");
        pnlHeader.add(lblSubtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));
        lblAdminName.setFont(new java.awt.Font("Arial", 1, 12));
        lblAdminName.setForeground(java.awt.Color.WHITE);
        lblAdminName.setText("Admin");
        pnlHeader.add(lblAdminName, new org.netbeans.lib.awtextra.AbsoluteConstraints(952, 40, -1, 20));
        lblAdminProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/profil.png")));
        pnlHeader.add(lblAdminProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 30, 40, 40));
        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 1060, 100));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNavMonitoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavMonitoringActionPerformed
        new Monitoring().setVisible(true); this.dispose();
    }//GEN-LAST:event_btnNavMonitoringActionPerformed

    private void btnNavVisitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavVisitorActionPerformed
        new DataVisitor().setVisible(true); this.dispose();
    }//GEN-LAST:event_btnNavVisitorActionPerformed

    private void btnNavAbsensiActionPerformed(java.awt.event.ActionEvent evt) {
        new Absensi().setVisible(true); this.dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception ex) { logger.log(java.util.logging.Level.SEVERE, null, ex); }
        java.awt.EventQueue.invokeLater(() -> new Pengaturan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNavAbsensi;
    private javax.swing.JButton btnNavMonitoring;
    private javax.swing.JButton btnNavPengaturan;
    private javax.swing.JButton btnNavVisitor;
    private javax.swing.JLabel lblAdminName;
    private javax.swing.JLabel lblAdminProfile;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlSidebar;
    // End of variables declaration//GEN-END:variables
}
