/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.poly.model;

import com.poly.dao.LoaiSanPhamDAO;
import com.poly.entity.LoaiSanPham;
import com.poly.utils.XDialog;
import java.util.ArrayList;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Nhu Y
 */
public class LoaiSanPhamJPanel extends javax.swing.JPanel {
        LoaiSanPhamDAO lspdao = new LoaiSanPhamDAO();
         ArrayList<LoaiSanPham> list = new ArrayList<>();
         int row = -1;
    /**
     * Creates new form LoaiSanPhamJPanel
     */
    public LoaiSanPhamJPanel() {
        initComponents();
        init();
    }
    public void init(){
        fillTable();
    }
    public void fillTable(){
        DefaultTableModel model = (DefaultTableModel) tblDanhSachlsp.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
             List<LoaiSanPham> list = lspdao.selectAll();
            for (LoaiSanPham lsp : list) {
                Object[] row = {lsp.getMaLoaiSanPham(), lsp.getTenLoaiSanPham()};
                model.addRow(row);
            }
        } catch (Exception e) {
//            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            throw new RuntimeException(e);
//            System.out.println("Lỗi truy vấn dữ liệu");
        }
    }
    private void insert() {
        LoaiSanPham lsp = this.getForm();
         // Validate the LoaiSanPham object before attempting to insert
    if (lsp.getMaLoaiSanPham()== null || lsp.getMaLoaiSanPham().isEmpty() || lsp.getTenLoaiSanPham()== null || lsp.getTenLoaiSanPham().isEmpty()) {
        XDialog.alert(this, "Vui lòng nhập đầy đủ thông tin loại sản phẩm!");
        return; // Exit the method if validation fails
    }

    try {
        lspdao.insert(lsp); // Thêm mới
        this.fillTable(); // Đổ lại bảng
        this.clearForm(); // Xóa trắng form
        XDialog.alert(this, "Thêm loại sản phẩm mới thành công!");
    } catch (Exception e) {
        XDialog.alert(this, "Thêm loại sản phẩm mới thất bại!");
        e.printStackTrace();
    }
    }
      private void update() {
        LoaiSanPham lsp = this.getForm();
        try {
//             String sql = "YOUR_UPDATE_SQL_QUERY";
//             System.out.println("Executing SQL: " + sql);
             lspdao.update(lsp);
             this.fillTable();
             XDialog.alert(this, "Cập nhật loại sản phẩm thành công!");
         } catch (Exception e) {
             XDialog.alert(this, "Cập nhật loại sản phẩm thất bại!");
             e.printStackTrace();
}
    }
       private void delete() {
          if (XDialog.confirm(this, "Bạn có thực sự muốn xóa sản phẩm này không?")) {
        String maLoaiSP = txtMaLoaiSP.getText();

        // Validate maLoaiSP before attempting to delete
        if (maLoaiSP == null || maLoaiSP.isEmpty()) {
            XDialog.alert(this, "Vui lòng chọn một sản phẩm để xóa!");
            return; // Exit the method if validation fails
        }

        try {
            lspdao.delete(maLoaiSP);
            this.fillTable();
            this.clearForm();
            XDialog.alert(this, "Xóa loại sản phẩm thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Xóa loại sản phẩm thất bại!");
            e.printStackTrace();
        }
    }
    }
       
          private void edit() {
           this.row = tblDanhSachlsp.getSelectedRow();
        if (this.row >= 0) {
            String id = (String) tblDanhSachlsp.getValueAt(this.row, 0);
            LoaiSanPham lsp = lspdao.selectById(id);
            this.setForm(lsp);
        }
    }
            private void clearForm() {
          LoaiSanPham lsp = new LoaiSanPham();
          this.setForm(lsp);
          this.row = -1;
        //this.updateStatus();
    }
            
       private void first() {
        this.row = 0;
        selectAndScrollToVisible();
        this.edit();
    }
       
        private void prev() {
        if (this.row > 0) {
            this.row--;
            selectAndScrollToVisible();
        }
    }
        

      private void next() {
        int rowCount = tblDanhSachlsp.getRowCount();
        if (rowCount > 0 && this.row < rowCount - 1) {
            this.row++;
            selectAndScrollToVisible();
        }
    }

       private void last() {
        int rowCount = tblDanhSachlsp.getRowCount();
        if (rowCount > 0) {
            this.row = rowCount - 1;
            selectAndScrollToVisible();
        }
    }
            private void selectAndScrollToVisible() {
        tblDanhSachlsp.setRowSelectionInterval(this.row, this.row);
        tblDanhSachlsp.scrollRectToVisible(tblDanhSachlsp.getCellRect(this.row, 0, true));
        this.edit();
    }
            
    void ASC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachlsp.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblDanhSachlsp.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    void DESC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachlsp.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblDanhSachlsp.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
     void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachlsp.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblDanhSachlsp.setRowSorter(trs);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaLoaiSP = new javax.swing.JTextField();
        txtTenLoaiSP = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnLammoi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnTang = new javax.swing.JButton();
        btnGiam = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachlsp = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin loại sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel4.setText("Mã loại sản phẩm:");

        jLabel5.setText("Tên loại sản phẩm:");

        btnInsert.setBackground(new java.awt.Color(204, 204, 255));
        btnInsert.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/insert.png"))); // NOI18N
        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(204, 204, 255));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        btnUpdate.setText("Cập Nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 204, 255));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnLammoi.setBackground(new java.awt.Color(204, 204, 255));
        btnLammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clear.png"))); // NOI18N
        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(57, 57, 57)
                        .addComponent(txtMaLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel5)
                        .addGap(30, 30, 30)
                        .addComponent(txtTenLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(btnLammoi)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách loại sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        btnTang.setBackground(new java.awt.Color(204, 204, 255));
        btnTang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/performance.png"))); // NOI18N
        btnTang.setText("Sắp xếp tăng dần ");
        btnTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTangActionPerformed(evt);
            }
        });

        btnGiam.setBackground(new java.awt.Color(204, 204, 255));
        btnGiam.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGiam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/statistics.png"))); // NOI18N
        btnGiam.setText("Sắp xếp giảm dần");
        btnGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiamActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-search-32.png"))); // NOI18N
        jLabel2.setText("Tìm kiếm");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        tblDanhSachlsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã loại sản phẩm", "Tên loại sản phẩm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblDanhSachlsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachlspMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachlsp);

        btnFirst.setBackground(new java.awt.Color(204, 204, 255));
        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(204, 204, 255));
        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPrev.setText("<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(204, 204, 255));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(204, 204, 255));
        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addGap(50, 50, 50)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnLast))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnTang, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(btnGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(33, 33, 33)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(23, 23, 23))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("LOẠI SẢN PHẨM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(513, 513, 513)
                .addComponent(jLabel1)
                .addContainerGap(457, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblDanhSachlspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachlspMouseClicked
            int selectedRow = tblDanhSachlsp.getSelectedRow();
           
        // TOint selectedRow = tblDanhSachKH.getSelectedRow();

        if (selectedRow != -1) { // Check if a row is selected
            DefaultTableModel model = (DefaultTableModel) tblDanhSachlsp.getModel();
            String MaLoaiSanPham = model.getValueAt(selectedRow, 0).toString();
            String TenLoaiSanPham = model.getValueAt(selectedRow, 1).toString();
            
           
            
            if (model.getValueAt(selectedRow, 1) instanceof Boolean) {
            
            }
   
            // Populate text fields with the retrieved data
            txtMaLoaiSP.setText(MaLoaiSanPham);
            txtTenLoaiSP.setText(TenLoaiSanPham);
            
        }
    }//GEN-LAST:event_tblDanhSachlspMouseClicked

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
          this.insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        // TODO add your handling code here:
          this.clearForm();
    }//GEN-LAST:event_btnLammoiActionPerformed

    private void btnTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTangActionPerformed
        // TODO add your handling code here:
        this.ASC();
    }//GEN-LAST:event_btnTangActionPerformed

    private void btnGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiamActionPerformed
        // TODO add your handling code here:
        this.DESC();
    }//GEN-LAST:event_btnGiamActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here
        String str = txtSearch.getText();
        search(str);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        this.first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        this.prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        this.next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        this.last();
    }//GEN-LAST:event_btnLastActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnGiam;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnTang;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDanhSachlsp;
    private javax.swing.JTextField txtMaLoaiSP;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenLoaiSP;
    // End of variables declaration//GEN-END:variables

    private LoaiSanPham getForm() {
          
         LoaiSanPham lsp = new LoaiSanPham();
        lsp.setMaLoaiSanPham(txtMaLoaiSP.getText());
        lsp.setTenLoaiSanPham(txtTenLoaiSP.getText());
        return lsp;
    }

    private void setForm(LoaiSanPham lsp) {
        
        txtMaLoaiSP.setText(lsp.getMaLoaiSanPham());
        txtTenLoaiSP.setText(lsp.getTenLoaiSanPham());
        
    }

   

  
    }

   

