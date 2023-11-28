/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.poly.model;

import com.poly.entity.NhaCungCap;
import com.poly.entity.NhanVien;
import com.poly.entity.PhieuNhapHangg;
import com.poly.utils.XDialog;
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
      PhieuNhapHangg pnhdao = new PhieuNhapHangg();
      NhaCungCap nccdao = new NhaCungCap();
      NhanVien nv = new NhanVien();
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
    }
    public void fillTable(){
         DefaultTableModel model = (DefaultTableModel) tblDanhSachPhieuNhap.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
             List<PhieuNhapHangg> list = pnhdao.selectAll();
            for (PhieuNhapHangg pnh : list) {
                Object[] row = {pnh.getMaNhanVien(),pnh.getMaNhaCungCap(),pnh.getNgayNhap(),pnh.getHinhThucThanhToan()};
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
      private void insert() {
         PhieuNhapHangg pnh = getForm();
        try {
            pnhdao.insert(nv);
            this.fillTable();
            XDialog.alert(this, "Thêm mới thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm mới thất bại");
            throw new RuntimeException(e);
        }
    }
       private void update() {
       PhieuNhapHangg pnh = getForm();
        try {
            pnhdao.update(pnh);
            this.fillTable();
            this.reset();
            XDialog.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật thất bại");
            throw new RuntimeException(e);
        }
    }
       private void delete() {
       PhieuNhapHangg pnh = getForm();
        try {
            pnhdao.delete(pnh.getMaNhaCungCap());
            this.fillTable();
            this.reset();
            XDialog.alert(main, "Xóa thành công");
        } catch (Exception e) {
            XDialog.alert(main, "Xóa thất bại");
            throw new RuntimeException(e);
        }
    }
       private void reset() {
         txtMaNhacc.setText("");
        txtMaNhacc.setEditable(true);
        txtNguoigiao.setText("");
        txtNgayNhap.setText("");
        txtDiengiai.setText("");
        txtNhanVienmuahang.setText("");
        txtSoLuong.setText("");
        txtSoPhieuNhap.setText("");
        txtThanhTien.setText("");
        txtVatLieu.setText("");
        rboGhiNo.setText("");
        rboThanhToanNgay.setText("");
        cboNhacc.setSelectedIndex(0);
    }
        private void edit() {
          this.row = tblDanhSachPhieuNhap.getSelectedRow();
        if (this.row >= 0) {
            String id = (String) tblDanhSachPhieuNhap.getValueAt(this.row, 0);
            PhieuNhapHangg pnh = pnhdao.selectById(id);
            this.setForm(pnh);
        }
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
    void ASC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachPhieuNhap.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblDanhSachPhieuNhap.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    void DESC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachPhieuNhap.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblDanhSachPhieuNhap.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
     void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachPhieuNhap.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblDanhSachPhieuNhap.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }
     boolean validateForm() {
        List<PhieuNhapHangg> list = pnhdao.selectAll();
        if (txtMaNhacc.getText().equalsIgnoreCase("")
                && txtNguoigiao.getText().equalsIgnoreCase("")
                && txtDiengiai.getText().equalsIgnoreCase("")
                && txtNhanVienmuahang.getText().equalsIgnoreCase("")
                && txtVatLieu.getText().equalsIgnoreCase("")
                && txtSoPhieuNhap.getText().equalsIgnoreCase("")
                && txtNgayNhap.getText().equalsIgnoreCase("")
                && txtSoLuong.getText().equalsIgnoreCase("")
                && txtDonViTinh.getText().equalsIgnoreCase("")
                && txtDiaDiem.getText().equalsIgnoreCase("")
                && txtDonGia.getText().equalsIgnoreCase("")
                && txtThanhTien.getText().equalsIgnoreCase("")
                && cboNhacc.getSelectedIndex() == 0) {
            
            return false;
        }
        if (txtMaNhacc.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaNhanVien().equalsIgnoreCase(txtMaNhacc.getText().trim())) {
                
                return false;
            }
        }
        if (txtNguoigiao.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        if (txtDiengiai.getText().equalsIgnoreCase("")) {
           
            return false;
        }
        if (txtNhanVienmuahang.getText().equalsIgnoreCase("")) {
           
            return false;
        }
        if (txtVatLieu.getText().equalsIgnoreCase("")) {
           
            return false;
        }
        if (txtSoPhieuNhap.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        if (txtNgayNhap.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        if (txtSoPhieuNhap.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        if (txtSoLuong.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        if (txtDonViTinh.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        if (txtDiaDiem.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        if (txtDonGia.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        if (txtThanhTien.getText().equalsIgnoreCase("")) {
            
            return false;
        }
        if (cboNhacc.getSelectedIndex() == 0) {
            
            return false;
        }
                
        return true;
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNhanVienmuahang, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNhacc)
                    .addComponent(txtNguoigiao)
                    .addComponent(txtDiengiai, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboNhacc, 0, 301, Short.MAX_VALUE))
                .addGap(85, 85, 85)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(txtNgayNhap)
                    .addComponent(txtSoPhieuNhap)
                    .addComponent(txtDonViTinh)
                    .addComponent(txtVatLieu))
                .addGap(81, 81, 81)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel10)
                    .addComponent(jLabel15))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtDiaDiem, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                    .addComponent(txtDonGia)
                    .addComponent(txtThanhTien))
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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                " Mã Nhà cung cấp", "Mã nhân viên", "Ngày nhập", "Hình thức thanh toán", "Tổng tiền"
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
                        .addGap(394, 394, 394)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(69, 69, 69)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1330, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(564, 564, 564)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
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
        if (btnInsert.getText().equalsIgnoreCase("Thêm")) {
            if (txtMaNhacc.getText().equals("")) {
                XDialog.alert(main, "Vui lòng chọn sản phẩm cần xóa");
            } else {
                boolean check = XDialog.confirm(main, "Bạn có chắc muốn sản phẩm học này");
                if (check) {
                    delete();
                } else {

                }
            }
        } else if (btnInsert.getText().equalsIgnoreCase("Xác Nhận")) {
            this.reset();
            btnInsert.setText("Thêm");
            btnDelete.setText("Xóa");
            btnReset.setEnabled(true);
            txtMaNhacc.setEditable(false);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
          if (txtMaNhacc.getText().equals("")) {
            XDialog.alert(main, "Vui lòng chọn nhân viên cần sửa");
        } else {
            this.update();
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
       if (btnInsert.getText().equalsIgnoreCase("Thêm")) {
            this.reset();
            btnInsert.setText("Xác nhận");
            btnDelete.setText("Hủy");
            btnReset.setEnabled(false);
            txtMaNhacc.setEditable(true);

        } else if (btnInsert.getText().equalsIgnoreCase("Xác Nhận")) {
            if (validateForm()) {
                this.insert();
                this.reset();
                btnInsert.setText("Thêm");
                btnDelete.setText("Xóa");
                btnReset.setEnabled(true);
                txtMaNhacc.setEditable(false);
            }
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblDanhSachPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachPhieuNhapMouseClicked
        // TODO add your handling code here:
         if (evt.getClickCount() == 2) {
            this.edit();
    }     
    }//GEN-LAST:event_tblDanhSachPhieuNhapMouseClicked


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
       
//        nv.setNgayThangNamSinh(txtNamSinh.getText());
        pnh.setMaNhaCungCap(pnhdao.findIdByName((String) cboNhacc.getSelectedItem()));
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
        rboGhiNo.setSelected(pnh.isGhiChu());
        rboThanhToanNgay.setSelected(!pnh.isGhiChu());
        }
        
    }
    

    

 

