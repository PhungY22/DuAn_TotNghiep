/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.poly.model;

import com.poly.dao.NhaCungCapDAO;
import com.poly.dao.NhanVienDAO;
import com.poly.dao.PhieuNhapHanggDAO;
import com.poly.entity.NhaCungCap;
import com.poly.entity.NhanVien;
import com.poly.entity.PhieuNhapHangg;
import com.poly.utils.XDialog;
import java.util.ArrayList;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static org.apache.logging.log4j.ThreadContext.init;

/**
 *
 * @author ASUS X515EA
 */
public class PhieuNhapHanggJPanel extends javax.swing.JPanel {
      PhieuNhapHanggDAO pnhdao = new PhieuNhapHanggDAO();
      NhaCungCapDAO nccdao = new NhaCungCapDAO();
      NhanVienDAO nv = new NhanVienDAO();
      
      int row = -1;
      MainJFrame main;
    /**
     * Creates new form PhieuNhapHanggJPanel
     */
    public PhieuNhapHanggJPanel() {
        initComponents();
         init();
    }
    public void init(){
        this.fillTable();
        this.fillComboBox();
    }
    public void fillTable(){
         DefaultTableModel model = (DefaultTableModel) tblDanhSachPhieuNhap.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
             List<PhieuNhapHangg> list = pnhdao.selectAll();
            for (PhieuNhapHangg pnh : list) {
                Object[] row = {pnh.getMaNhanVien(),pnh.getMaNhaCungCap(),pnh.getNgayNhap(),pnh.getHinhThucThanhToan()
                        ,pnh.getDiaDiem(),pnh.getDienDai(),pnh.getDonGia(),pnh.getNguoiGiao(),pnh.getSoLuong(),pnh.getSoPhieuNhap()
                        ,pnh.getTongTien(),pnh.getVatlieu()};
                model.addRow(row);
            }
        } catch (Exception e) {
//            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            throw new RuntimeException(e);
//            System.out.println("Lỗi truy vấn dữ liệu");
        }
    }
      void fillComboBox() {

        //Môn học
        List<NhaCungCap> listNCC = nccdao.selectAll();
        for (int i = 0; listNCC.size() > i; i++) {
            cboNhacc.addItem(listNCC.get(i).getTenNhaCungCap());
        }
    }
      public void checkForm(){
          StringBuffer sb = new StringBuffer();
          if (chbNhanKemHoaDon.isSelected()){
              sb.append("NhanKemHoaDon");
          }
      }
      private void insert() {
         PhieuNhapHangg pnh = getForm();
         try {
            pnhdao.insert(pnh); // thêm mới
            this.fillTable(); // đỗ lại bảng
            this.clearForm(); // xóa trắng form
            XDialog.alert(this, "Thêm sản phẩm mới thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm sản phẩm mới thất bại!");
            e.printStackTrace();
        }
    }
       private void update() {
       PhieuNhapHangg pnh = getForm();
       try {
            pnhdao.update(pnh); // cập nhật
            this.fillTable(); // đổ lại bảng
            XDialog.alert(this, "Cập nhật sản phẩm thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật sản phẩm thất bại!");
            e.printStackTrace();
        }
    }
       private void delete() {
        if (XDialog.confirm(this, "Bạn có thực sự muốn xóa sản phẩm này không?")) {
           String sp = txtMaNhacc.getText();
            try {
                pnhdao.delete(sp);
               this.fillTable();
                this.clearForm();
               XDialog.alert(this, "Xóa sản phẩm thành công!");
            } catch (Exception e) {
               XDialog.alert(this, "Xóa sản phẩm thất bại!");
               e.printStackTrace();
                 }
    }
    }
       
        private void edit() {
         this.row = tblDanhSachPhieuNhap.getSelectedRow();
        if (this.row >= 0) {
            String id = (String) tblDanhSachPhieuNhap.getValueAt(this.row, 0);
            PhieuNhapHangg pnh = pnhdao.selectById(id);
            this.setForm(pnh);
        }
    }
        private void clearForm() {
         PhieuNhapHangg pnh = new  PhieuNhapHangg();
        this.setForm(pnh);
        this.row = -1;
        //this.updateStatus();
    }
        void first() {
        this.row = 0;
        this.edit();
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void next() {
        if (this.row < tblDanhSachPhieuNhap.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblDanhSachPhieuNhap.getRowCount() - 1;
        this.edit();
    }
    

   
     void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachPhieuNhap.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblDanhSachPhieuNhap.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }
     
    
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        rboGhiNo = new javax.swing.JRadioButton();
        rboThanhToanNgay = new javax.swing.JRadioButton();
        chbNhanKemHoaDon = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDiengiai = new javax.swing.JTextField();
        txtNguoigiao = new javax.swing.JTextField();
        txtNhanVienmuahang = new javax.swing.JTextField();
        cboNhacc = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSoPhieuNhap = new javax.swing.JTextField();
        txtNgayNhap = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDiaDiem = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMaNhacc = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        txtDonViTinh = new javax.swing.JTextField();
        txtVatLieu = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachPhieuNhap = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));
        setPreferredSize(new java.awt.Dimension(1501, 871));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel1.text")); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup1.add(rboGhiNo);
        org.openide.awt.Mnemonics.setLocalizedText(rboGhiNo, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.rboGhiNo.text")); // NOI18N

        buttonGroup1.add(rboThanhToanNgay);
        org.openide.awt.Mnemonics.setLocalizedText(rboThanhToanNgay, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.rboThanhToanNgay.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(chbNhanKemHoaDon, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.chbNhanKemHoaDon.text")); // NOI18N
        chbNhanKemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbNhanKemHoaDonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel16, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel16.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(478, 478, 478)
                .addComponent(jLabel16)
                .addGap(58, 58, 58)
                .addComponent(rboGhiNo)
                .addGap(51, 51, 51)
                .addComponent(rboThanhToanNgay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chbNhanKemHoaDon)
                .addGap(168, 168, 168))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rboGhiNo)
                    .addComponent(rboThanhToanNgay)
                    .addComponent(chbNhanKemHoaDon)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel7.text")); // NOI18N

        txtNguoigiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNguoigiaoActionPerformed(evt);
            }
        });

        cboNhacc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Công ty A", "Công ty B", "Công ty C", "Công ty D" }));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel9.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel10.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel11.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel13.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel14.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel15.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtDiengiai, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNguoigiao, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNhacc, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboNhacc, javax.swing.GroupLayout.Alignment.LEADING, 0, 261, Short.MAX_VALUE)
                    .addComponent(txtNhanVienmuahang))
                .addGap(133, 133, 133)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtVatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel10)
                            .addComponent(jLabel15))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDiaDiem, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                            .addComponent(txtDonGia)
                            .addComponent(txtThanhTien)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNhacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(txtDiaDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtMaNhacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtSoPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNguoigiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDiengiai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNhanVienmuahang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.jLabel2.text")); // NOI18N

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        tblDanhSachPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                " Nhà cung cấp", "Mã nhà cung cấp", "Người giao", "Diễn giải", "Nhân viên mua hàng", "Vật liệu", "Số phiếu nhập", "Ngày nhập", "Số lượng", "Đơn vị tính", "Địa điểm", "Đơn giá", "Thành tiền"
            }
        ));
        tblDanhSachPhieuNhap.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblDanhSachPhieuNhapAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tblDanhSachPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachPhieuNhap);
        if (tblDanhSachPhieuNhap.getColumnModel().getColumnCount() > 0) {
            tblDanhSachPhieuNhap.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title0")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title1")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title2")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title3")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title4")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title5")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title6")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(7).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title7")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(8).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title8")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(9).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title9")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(10).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title10")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(11).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title11")); // NOI18N
            tblDanhSachPhieuNhap.getColumnModel().getColumn(12).setHeaderValue(org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.tblDanhSachPhieuNhap.columnModel.title12")); // NOI18N
        }

        btnDelete.setBackground(new java.awt.Color(204, 204, 255));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnDelete, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.btnDelete.text")); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(204, 204, 255));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnReset, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.btnReset.text")); // NOI18N
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnInsert.setBackground(new java.awt.Color(204, 204, 255));
        btnInsert.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnInsert, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.btnInsert.text")); // NOI18N
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(204, 204, 255));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnUpdate, org.openide.util.NbBundle.getMessage(PhieuNhapHanggJPanel.class, "PhieuNhapHanggJPanel.btnUpdate.text")); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(468, 468, 468)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(716, 716, 716)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(325, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(636, 636, 636)
                        .addComponent(jLabel1)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNguoigiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNguoigiaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNguoigiaoActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
        String str = txtSearch.getText();
        search(str);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tblDanhSachPhieuNhapAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblDanhSachPhieuNhapAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDanhSachPhieuNhapAncestorAdded

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
       this.insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblDanhSachPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachPhieuNhapMouseClicked
        // TODO add your handling code here:
         int selectedRow = tblDanhSachPhieuNhap.getSelectedRow();

        if (selectedRow != -1) { // Check if a row is selected
            DefaultTableModel model = (DefaultTableModel) tblDanhSachPhieuNhap.getModel();
            String NhaCungCap = model.getValueAt(selectedRow, 0).toString();
            String MaNhaCungCap = model.getValueAt(selectedRow, 1).toString();
            String NguoiGiao = model.getValueAt(selectedRow, 2).toString(); // Assuming the first column contains the MaKhachHang
            String DienDai = model.getValueAt(selectedRow, 3).toString(); // Assuming the second column contains the TenKhachHang
            String NhanVienMuaHang = model.getValueAt(selectedRow, 4).toString();
            String VatLieu = model.getValueAt(selectedRow, 5).toString();
            String SoPhieuNhap = model.getValueAt(selectedRow, 6).toString();
            String NgayNhap = model.getValueAt(selectedRow, 7).toString();
            String SoLuong = model.getValueAt(selectedRow, 8).toString();
            String DonViTinh = model.getValueAt(selectedRow, 9).toString();
            String DiaDiem = model.getValueAt(selectedRow, 10).toString();
            String DonGia = model.getValueAt(selectedRow, 11).toString();
            String ThanhTien = model.getValueAt(selectedRow, 12).toString();
            if (model.getValueAt(selectedRow, 5) instanceof Boolean) {
            Boolean isGhiChu = (Boolean) model.getValueAt(selectedRow, 5);
               
            if (isGhiChu) {
                rboGhiNo.setSelected(true);
            } else {
                rboThanhToanNgay.setSelected(true);
            }
            }
   
            // Populate text fields with the retrieved data
            cboNhacc.setSelectedItem(NhaCungCap);
            txtMaNhacc.setText(MaNhaCungCap);
            txtNguoigiao.setText(NguoiGiao);
            txtDiengiai.setText(DienDai);
            txtNhanVienmuahang.setText(NhanVienMuaHang);
            txtVatLieu.setText(VatLieu);
            txtSoPhieuNhap.setText(SoPhieuNhap);
            txtNgayNhap.setText(NgayNhap);
            txtSoLuong.setText(SoLuong);
            txtDonViTinh.setText(DonViTinh);
            txtDiaDiem.setText(DiaDiem);
            txtDonGia.setText(DonGia);
            txtThanhTien.setText(ThanhTien);
            if (model.getValueAt(selectedRow, 5) instanceof Boolean) {
                rboGhiNo.setSelected(true);
            }else {
                rboThanhToanNgay.setSelected(true);
            }
        }
    }//GEN-LAST:event_tblDanhSachPhieuNhapMouseClicked

    private void chbNhanKemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbNhanKemHoaDonActionPerformed
        // TODO add your handling code here:
        checkForm();
    }//GEN-LAST:event_chbNhanKemHoaDonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboNhacc;
    private javax.swing.JCheckBox chbNhanKemHoaDon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rboGhiNo;
    private javax.swing.JRadioButton rboThanhToanNgay;
    private javax.swing.JTable tblDanhSachPhieuNhap;
    private javax.swing.JTextField txtDiaDiem;
    private javax.swing.JTextField txtDiengiai;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtMaNhacc;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtNguoigiao;
    private javax.swing.JTextField txtNhanVienmuahang;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoPhieuNhap;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtVatLieu;
    // End of variables declaration//GEN-END:variables

    

    private PhieuNhapHangg getForm() {
       PhieuNhapHangg pnh = new PhieuNhapHangg();
        pnh.setMaNhanVien(txtNhanVienmuahang.getText());
        pnh.setMaNhaCungCap(txtMaNhacc.getToolTipText());
        pnh.setNgayNhap(txtNgayNhap.getText());
        pnh.setMaPhieuNhapHang(txtSoPhieuNhap.getText());      
        pnh.setHinhThucThanhToan(txtDonGia.getText());
        pnh.setTongTien(txtThanhTien.getText());
        pnh.setSoPhieuNhap(txtSoPhieuNhap.getText());
        pnh.setDiaDiem(txtDiaDiem.getText());
        pnh.setVatlieu(txtVatLieu.getText());
        pnh.setSoLuong(txtSoLuong.getText());
        pnh.setDonViTinh(txtDonViTinh.getText());
        pnh.setDonGia(txtDonGia.getText());
       
       
//        nv.setNgayThangNamSinh(txtNamSinh.getText());
        pnh.setMaNhaCungCap(pnhdao.findIdByName((String) cboNhacc.getSelectedItem()));
        boolean gc = false;
        if(rboGhiNo.isSelected()){
            gc = true;
        }
        pnh.setIsGhiChu(gc);
        return pnh;
    }

    private void setForm(PhieuNhapHangg pnh) {
         txtMaNhacc.setText(pnh.getMaNhaCungCap());
        txtNguoigiao.setText(pnh.getNgayNhap());
        
        txtNhanVienmuahang.setText(pnh.getMaNhanVien());
        txtVatLieu.setText(pnh.getVatlieu());
        txtDiaDiem.setText(pnh.getDiaDiem());
        txtSoPhieuNhap.setText(XDate.toString(pnh.getSoPhieuNhap()));
        txtThanhTien.setText(pnh.getTongTien());
        txtSoLuong.setText(pnh.getSoLuong());
        txtDiengiai.setText(pnh.getDienDai());
        txtNgayNhap.setText(pnh.getNgayNhap());
        txtDonViTinh.setText(pnh.getDonViTinh());
        txtDonGia.setText(pnh.getDonGia());
        rboGhiNo.setSelected(pnh.isIsGhiChu());
        rboThanhToanNgay.setSelected(!pnh.isIsGhiChu());
        }

    
        
    }
    

    

 

