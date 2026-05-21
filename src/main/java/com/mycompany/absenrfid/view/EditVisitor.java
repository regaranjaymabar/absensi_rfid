package com.mycompany.absenrfid.view;

import com.mycompany.absenrfid.objects.Visitor;
import com.mycompany.absenrfid.services.VisitorService;

public class EditVisitor extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EditVisitor.class.getName());
    private String nimAsal; // simpan NIM asli untuk keperluan update

    public EditVisitor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    // Dipanggil dari DataVisitor sebelum setVisible(true)
    // Mengisi semua field dengan data visitor yang akan diedit
    void setNim(String nim) {
        this.nimAsal = nim;
        VisitorService service = new VisitorService();
        Visitor v = service.cariByNim(nim);
        if (v != null) {
            TextFieldNim.setText(v.getNim());
            TextFieldNama.setText(v.getNama());
            TextFieldJurusan.setText(v.getJurusan());
            TextFieldKelas.setText(v.getKelas());
            TextFieldUID.setText(v.getUid_rfid());
        }
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
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);

        pnlWadah.setBackground(new java.awt.Color(102, 0, 0));
        pnlWadah.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJudul.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(255, 255, 255));
        lblJudul.setText("Edit Visitor");
        pnlWadah.add(lblJudul, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        lblUID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblUID.setForeground(new java.awt.Color(255, 255, 255));
        lblUID.setText("UID RFID");
        pnlWadah.add(lblUID, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, 20));

        lblNim.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNim.setForeground(new java.awt.Color(255, 255, 255));
        lblNim.setText("NIM");
        pnlWadah.add(lblNim, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        lblNama.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNama.setForeground(new java.awt.Color(255, 255, 255));
        lblNama.setText("Nama");
        pnlWadah.add(lblNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        lblJurusan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblJurusan.setForeground(new java.awt.Color(255, 255, 255));
        lblJurusan.setText("Jurusan");
        pnlWadah.add(lblJurusan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 157, -1, 30));

        lblKelas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblKelas.setForeground(new java.awt.Color(255, 255, 255));
        lblKelas.setText("Kelas");
        pnlWadah.add(lblKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 50, 20));

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
        btnBatal.addActionListener(evt -> dispose());
        pnlWadah.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, -1, -1));

        btnEdit.setBackground(new java.awt.Color(255, 153, 0));
        btnEdit.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(evt -> btnEditActionPerformed(evt));
        pnlWadah.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWadah, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWadah, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // Ambil data dari field dan simpan ke database via VisitorService
        Visitor v = new Visitor();
        v.setNim(TextFieldNim.getText().trim());
        v.setNama(TextFieldNama.getText().trim());
        v.setJurusan(TextFieldJurusan.getText().trim());
        v.setKelas(TextFieldKelas.getText().trim());
        v.setUid_rfid(TextFieldUID.getText().trim());

        if (v.getNim().isEmpty() || v.getNama().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "NIM dan Nama tidak boleh kosong!", "Validasi",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        VisitorService service = new VisitorService();
        service.update(nimAsal, v); // update berdasarkan NIM asli
        javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
        dispose();
    }//GEN-LAST:event_btnEditActionPerformed

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
            EditVisitor dialog = new EditVisitor(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblJurusan;
    private javax.swing.JLabel lblKelas;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNim;
    private javax.swing.JLabel lblUID;
    private javax.swing.JPanel pnlWadah;
    // End of variables declaration//GEN-END:variables
}
