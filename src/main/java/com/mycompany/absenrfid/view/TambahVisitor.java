package com.mycompany.absenrfid.view;

import com.mycompany.absenrfid.services.VisitorService;
import com.mycompany.absenrfid.objects.Visitor;
import com.mycompany.absenrfid.services.I18nService;

/**
 *
 * @author Regar
 */
public class TambahVisitor extends javax.swing.JDialog
    implements I18nService.I18nChangeListener{
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TambahVisitor.class.getName());
    private final VisitorService visitorService = new VisitorService();

    public TambahVisitor(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    I18nService.registerListener(this);
    onLanguageChanged();
}

    private void simpanData() {
      String nim = TextFieldNim.getText().trim();
      String nama = TextFieldNama.getText().trim();
      String jurusan = TextFieldJurusan.getText().trim();
      String kelas = TextFieldKelas.getText().trim();
      String uid = TextFieldUID.getText().trim();

      if (nim.isEmpty() || nama.isEmpty()) {
          javax.swing.JOptionPane.showMessageDialog(this,
              I18nService.get("ui.msg.nimnamakosongg"),
              I18nService.get("ui.msg.validasi"),
              javax.swing.JOptionPane.WARNING_MESSAGE);
          return;
      }

      try {
          Visitor v = new Visitor();
          v.setNim(nim);
          v.setNama(nama);
          v.setJurusan(jurusan);
          v.setKelas(kelas);
          v.setUid_rfid(uid);
          visitorService.tambah(v);
          javax.swing.JOptionPane.showMessageDialog(this,
              I18nService.get("ui.msg.simpansukses"),
              I18nService.get("ui.msg.validasi"),
              javax.swing.JOptionPane.INFORMATION_MESSAGE);
          I18nService.unregisterListener(this);
          dispose();
      } catch (Exception e) {
          javax.swing.JOptionPane.showMessageDialog(this,
              I18nService.get("ui.msg.galalsimpan") + "\n" + e.getMessage(),
              I18nService.get("ui.msg.error"),
              javax.swing.JOptionPane.ERROR_MESSAGE);
      }
  }
        @Override
      public void onLanguageChanged() {
          lblJudul.setText(I18nService.get("ui.title.tambahvisitor"));
          lblNim.setText(I18nService.get("ui.label.nim"));
          lblNama.setText(I18nService.get("ui.label.nama"));
          lblJurusan.setText(I18nService.get("ui.label.jurusan"));
          lblKelas.setText(I18nService.get("ui.label.kelas"));
          lblUID.setText(I18nService.get("ui.label.uid"));
          btnSimpan.setText(I18nService.get("ui.btn.simpan"));
          btnBatal.setText(I18nService.get("ui.btn.batal"));
      }
  
    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {
        I18nService.unregisterListener(this);
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlWadah = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        lblUID = new javax.swing.JLabel();
        lblNim = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        lblJurusan = new javax.swing.JLabel();
        lblKelas = new javax.swing.JLabel();
        TextFieldUID = new javax.swing.JTextField();
        TextFieldNim = new javax.swing.JTextField();
        TextFieldNama = new javax.swing.JTextField();
        TextFieldJurusan = new javax.swing.JTextField();
        TextFieldKelas = new javax.swing.JTextField();
        btnBatal = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);

        pnlWadah.setBackground(new java.awt.Color(102, 0, 0));
        pnlWadah.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJudul.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(255, 255, 255));
        lblJudul.setText("Tambah Visitor");
        pnlWadah.add(lblJudul, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        lblUID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblUID.setForeground(new java.awt.Color(255, 255, 255));
        lblUID.setText("UID RFID");
        pnlWadah.add(lblUID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        lblNim.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNim.setForeground(new java.awt.Color(255, 255, 255));
        lblNim.setText("NIM");
        pnlWadah.add(lblNim, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        lblNama.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNama.setForeground(new java.awt.Color(255, 255, 255));
        lblNama.setText("Nama");
        pnlWadah.add(lblNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        lblJurusan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblJurusan.setForeground(new java.awt.Color(255, 255, 255));
        lblJurusan.setText("Jurusan");
        pnlWadah.add(lblJurusan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        lblKelas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblKelas.setForeground(new java.awt.Color(255, 255, 255));
        lblKelas.setText("Kelas");
        pnlWadah.add(lblKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        TextFieldUID.setBackground(new java.awt.Color(204, 204, 204));
        pnlWadah.add(TextFieldUID, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 210, -1));

        TextFieldNim.setBackground(new java.awt.Color(204, 204, 204));
        pnlWadah.add(TextFieldNim, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 210, -1));

        TextFieldNama.setBackground(new java.awt.Color(204, 204, 204));
        pnlWadah.add(TextFieldNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 210, -1));

        TextFieldJurusan.setBackground(new java.awt.Color(204, 204, 204));
        pnlWadah.add(TextFieldJurusan, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 210, -1));

        TextFieldKelas.setBackground(new java.awt.Color(204, 204, 204));
        pnlWadah.add(TextFieldKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 210, -1));

        btnBatal.setBackground(new java.awt.Color(204, 0, 0));
        btnBatal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        pnlWadah.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, -1, -1));

        btnSimpan.setBackground(new java.awt.Color(0, 255, 0));
        btnSimpan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        pnlWadah.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWadah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWadah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
       simpanData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            TambahVisitor dialog = new TambahVisitor(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) { System.exit(0); }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TextFieldJurusan;
    private javax.swing.JTextField TextFieldKelas;
    private javax.swing.JTextField TextFieldNama;
    private javax.swing.JTextField TextFieldNim;
    private javax.swing.JTextField TextFieldUID;
    public javax.swing.JButton btnBatal;
    public javax.swing.JButton btnSimpan;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblJurusan;
    private javax.swing.JLabel lblKelas;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNim;
    private javax.swing.JLabel lblUID;
    private javax.swing.JPanel pnlWadah;
    // End of variables declaration//GEN-END:variables
}
