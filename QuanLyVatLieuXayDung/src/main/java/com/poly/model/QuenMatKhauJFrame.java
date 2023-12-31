/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.poly.model;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;

/**
 *
 * @author Nhu Y
 */
public class QuenMatKhauJFrame extends javax.swing.JFrame {
         Connection ketnoi;
    /**
     * Creates new form QuenMatKhau
     */
    public QuenMatKhauJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTenTaiKhoan = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lblQuayLai = new javax.swing.JLabel();
        btnDoiMatKhau = new javax.swing.JButton();
        txtPass = new javax.swing.JPasswordField();
        txtNhapLaiPass = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setText("QUÊN MẬT KHẨU");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(220, 20, 210, 32);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tên tài khoản:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(160, 90, 120, 20);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Email:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(420, 90, 50, 20);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Mật khẩu mới:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(160, 180, 110, 20);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Xác nhận lại mật khẩu:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(420, 180, 170, 20);
        jPanel1.add(txtTenTaiKhoan);
        txtTenTaiKhoan.setBounds(160, 120, 220, 40);
        jPanel1.add(txtEmail);
        txtEmail.setBounds(420, 120, 220, 40);

        lblQuayLai.setBackground(new java.awt.Color(0, 0, 0));
        lblQuayLai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblQuayLai.setText("Quay lại");
        lblQuayLai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuayLaiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQuayLaiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQuayLaiMouseExited(evt);
            }
        });
        jPanel1.add(lblQuayLai);
        lblQuayLai.setBounds(570, 340, 60, 20);

        btnDoiMatKhau.setBackground(new java.awt.Color(204, 204, 255));
        btnDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDoiMatKhau.setText("ĐĂNG NHẬP");
        btnDoiMatKhau.setBorder(null);
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });
        jPanel1.add(btnDoiMatKhau);
        btnDoiMatKhau.setBounds(320, 290, 160, 40);
        jPanel1.add(txtPass);
        txtPass.setBounds(160, 210, 220, 40);
        jPanel1.add(txtNhapLaiPass);
        txtNhapLaiPass.setBounds(420, 210, 220, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/model/phishing.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 90, 130, 180);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblQuayLaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuayLaiMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.setVisible(false);
            DangNhapJFrame dn = new DangNhapJFrame();
            dn.setVisible(true);
        }
    }//GEN-LAST:event_lblQuayLaiMouseClicked

    private void lblQuayLaiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuayLaiMouseEntered
        // TODO add your handling code here:
         lblQuayLai.setForeground(Color.red);
    }//GEN-LAST:event_lblQuayLaiMouseEntered

    private void lblQuayLaiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuayLaiMouseExited
        // TODO add your handling code here:
         lblQuayLai.setForeground(Color.white);
    }//GEN-LAST:event_lblQuayLaiMouseExited

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        // TODO add your handling code here:
         if(checknull()==true){
            try {
                doimatkhau();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed
  public void KetNoiCSDL() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=quanlydonoithat;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String pass = "123";
            Connection ketnoi = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex);

        }

    }
    public boolean checknull() {
        String username = txtTenTaiKhoan.getText();
        String email = txtEmail.getText();
        String mk = txtPass.getText();
        String mk1 = txtNhapLaiPass.getText();
        if (username.isEmpty() && email.isEmpty() && mk.isEmpty() && mk1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    public boolean checktk() {
        String matkhau1 = txtPass.getText();
        String matkhau2 = txtNhapLaiPass.getText();
        if (matkhau1.equalsIgnoreCase(matkhau2)) {
            return true;
        } else { 
            return false;
        }
    }
    public void doimatkhau() throws SQLException {
        KetNoiCSDL();
        String sql = "SELECT * FROM NhanVien where MaNhanVien =? and Email = ?";
        PreparedStatement cauLenh = ketnoi.prepareStatement(sql);
        cauLenh.setString(1, txtTenTaiKhoan.getText());
        cauLenh.setString(2, txtEmail.getText());
        ResultSet ketQua = cauLenh.executeQuery();
        if (ketQua.next()) {
            if (checktk()==true) {
                String update = "UPDATE NhanVien SET MatKhau = ? WHERE MaNhanVien=? and Email= ?";
                PreparedStatement updateStmt = ketnoi.prepareStatement(update);
                updateStmt.setString(1, txtPass.getText());
                updateStmt.setString(2, txtTenTaiKhoan.getText());
                updateStmt.setString(3, txtEmail.getText());
                updateStmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Mật khẩu cho tài khoản này đã được đổi", "Đổi mật khẩu", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không trùng nhau", "Đổi mật khẩu thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc Email không tồn tại", "Đổi mật khẩu", JOptionPane.ERROR_MESSAGE);
        }

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuenMatKhauJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblQuayLai;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtNhapLaiPass;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtTenTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
