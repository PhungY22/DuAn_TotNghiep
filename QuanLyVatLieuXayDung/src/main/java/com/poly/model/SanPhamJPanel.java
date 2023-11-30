/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.poly.model;

import com.poly.dao.LoaiSanPhamDAO;
import com.poly.dao.SanPhamDAO;
import com.poly.entity.LoaiSanPham;
import com.poly.entity.SanPham;
import com.poly.utils.XDialog;
import com.poly.utils.XImage;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static java.util.Locale.filter;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Nhu Y
 */
public class SanPhamJPanel extends javax.swing.JPanel {

    SanPhamDAO spdao = new SanPhamDAO();
    LoaiSanPhamDAO lspdao = new LoaiSanPhamDAO();

    int row = -1;
    MainJFrame main;

    /**
     * Creates new form SanPhamJPanel
     */
    public SanPhamJPanel() {
        initComponents();
        init();
    }

    public void init() {
        this.fillTable();
        this.fillComboBox();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachSP.getModel();
        model.setRowCount(0);
        try {
            List<SanPham> list = spdao.selectAll();
            for (SanPham sp : list) {
                String tenLSP = lspdao.selectById(sp.getMaLoaiSanPham()).getTenLoaiSanPham();
                Object[] row = {sp.getMaSanPham(), sp.getTenSanPham(), tenLSP,
                    sp.getGiaNhap(), sp.getGiaXuat(), sp.getSoLuong(), sp.getHinh()};
                model.addRow(row);
            }
        } catch (Exception e) {
//            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            throw new RuntimeException(e);
//            System.out.println("Lỗi truy vấn dữ liệu");
        }
    }

    void fillComboBox() {
        List<LoaiSanPham> listSP = lspdao.selectAll();
        for (int i = 0; listSP.size() > i; i++) {
            cboLoaiSP.addItem(listSP.get(i).getTenLoaiSanPham());
        }
    }

 private void insert() {
    // Tạo đối tượng DAO
    SanPhamDAO dao = new SanPhamDAO();
    
    // Lấy thông tin từ form
    
   
        String MaSanPham = txtMaSP.getText();
        String TenSanPham = txtTenSP.getText();
        String Hinh = lblHinh.getToolTipText();
        String MaLoaiSanPham = String.valueOf(cboLoaiSP.getSelectedItem());
        String GiaNhap = txtGiaNhap.getText();
        String GiaXuat = txtGiaXuat.getText();
        int SoLuong;

    try {
        // Parse the quantity
        SoLuong = Integer.parseInt(txtSoLuong.getText());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Xin vui lòng nhập số ", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Tạo đối tượng sản phẩm
    SanPham sp = new SanPham(MaSanPham, TenSanPham, Hinh, MaLoaiSanPham, GiaNhap, GiaXuat, SoLuong);

    // Kiểm tra tính hợp lệ của sản phẩm
    if (!sp.isValid()) {
        JOptionPane.showMessageDialog(this, "Thông tin sản phẩm không hợp lệ. Vui lòng điền vào tất cả các lĩnh vực.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    
    
    // Thêm sản phẩm vào cơ sở dữ liệu bằng lớp DAO
    dao.insert(sp);
    // Đóng form (hoặc thực hiện các hành động khác sau khi thêm sản phẩm)
    dispose();
}

    private void update() {
        SanPham sp = getForm();
        try {
            spdao.update(sp);
            this.fillTable();
            this.reset();
            XDialog.alert(this, "Cập nhật sản phẩm thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật sản phẩm thất bại");
            throw new RuntimeException(e);
        }
    }

    private void delete() {
        SanPham sp = getForm();
        try {
            spdao.delete(sp.getMaSanPham());
            this.fillTable();
            this.reset();
            XDialog.alert(main, "Xóa sản phẩm thành công");
        } catch (Exception e) {
            XDialog.alert(main, "Xóa sản phẩm thất bại");
            throw new RuntimeException(e);
        }
    }

    private void reset() {
        txtMaSP.setText("");
        txtMaSP.setEditable(true);
        txtTenSP.setText("");
        txtGiaNhap.setText("");
        txtGiaXuat.setText(String.valueOf(""));
        txtSoLuong.setText(String.valueOf(""));
        cboLoaiSP.setSelectedIndex(0);
        //this.row = -1;
        lblMaSP.setText("");
        lblTenSP.setText("");
        lblLSP.setText("");
        lblGiaNhap.setText("");
        lblGiaXuat.setText("");
        lblSoLuong.setText("");
    }

    private void edit() {
        this.row = tblDanhSachSP.getSelectedRow();
        if (this.row >= 0) {
            String id = (String) tblDanhSachSP.getValueAt(this.row, 0);
            SanPham sp = spdao.selectById(id);
            this.setForm(sp);
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
        if (this.row < tblDanhSachSP.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblDanhSachSP.getRowCount() - 1;
        this.edit();
    }

    void ASC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachSP.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        // Chỉ định cột "mã" và thứ tự sắp xếp là tăng dần
        RowSorter.SortKey sortKey = new RowSorter.SortKey(0, SortOrder.ASCENDING);

        // Đặt danh sách SortKey chỉ chứa một phần tử vào TableRowSorter
        sorter.setSortKeys(List.of(sortKey));

        // Đặt TableRowSorter cho JTable
        tblDanhSachSP.setRowSorter(sorter);

        // Thực hiện sắp xếp
        sorter.sort();
    }

    void DESC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachSP.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblDanhSachSP.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachSP.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblDanhSachSP.setRowSorter(trs);

        // Chỉ định cột "ID" và cột "Tên" để áp dụng bộ lọc
        int columnIndexID = 0; // Index của cột "ID" trong model
        int columnIndexTen = 1; // Index của cột "Tên" trong model

        // Sử dụng RowFilter.orFilter để kết hợp hai điều kiện tìm kiếm
        RowFilter<DefaultTableModel, Object> idFilter = RowFilter.regexFilter(str, columnIndexID);
        RowFilter<DefaultTableModel, Object> tenFilter = RowFilter.regexFilter(str, columnIndexTen);

        trs.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, tenFilter)));
    }
//      boolean validateForm() {
//        List<SanPham> list = spdao.selectAll();
//        if (txtMaSP.getText().equalsIgnoreCase("")
//                && txtTenSP.getText().equalsIgnoreCase("")
//                && txtGiaNhap.getText().equalsIgnoreCase("")
//                && txtGiaXuat.getText().equalsIgnoreCase("")
//                && txtSoLuong.getText().equalsIgnoreCase("")
//                && cboLoaiSP.getSelectedIndex() == 0) {
//            lblMaSP.setText("*Không được để trống");
//            lblTenSP.setText("*Không được để trống");
//            lblGiaNhap.setText("*Không được để trống");
//            lblGiaXuat.setText("*Không được để trống");
//            lblSoLuong.setText("*Không được để trống");
//            lblLSP.setText("Vui lòng chọn loại sản phẩm");
//            return false;
//        }
//        if (txtMaSP.getText().equalsIgnoreCase("")) {
//            lblTenSP.setText("*Không được để trống");
//            return false;
//        }
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getMaSanPham().equalsIgnoreCase(txtMaSP.getText().trim())) {
//                lblMaSP.setText("Mã sản phẩm đã tồn tại!");
//                return false;
//            }
//        }
//        if (txtTenSP.getText().equalsIgnoreCase("")) {
//            lblTenSP.setText("*Không được để trống tên sản phẩm");
//            return false;
//        }
//        if (txtGiaNhap.getText().equalsIgnoreCase("")) {
//            lblGiaNhap.setText("*Không được để trống giá xuất");
//            return false;
//        }
//        if (txtGiaXuat.getText().equalsIgnoreCase("")) {
//            lblGiaXuat.setText("*Không được để trống giá nhập");
//            return false;
//        }
//        if (txtSoLuong.getText().equalsIgnoreCase("")) {
//            lblSoLuong.setText("*Không được để trống số lượng");
//            return false;
//        }
//        if (cboLoaiSP.getSelectedIndex() == 0) {
//            lblLSP.setText("Vui lòng chọn tên loại sản phẩm");
//            return false;
//        }
//        try {
//            Integer.parseInt(txtGiaNhap.getText());
//        } catch (NumberFormatException e) {
//            lblGiaNhap.setText("Vui lòng nhập số!");
//            return false;
//        }
//        try {
//            Integer.parseInt(txtGiaXuat.getText());
//        } catch (NumberFormatException e) {
//            lblGiaXuat.setText("Vui lòng nhập số!");
//            return false;
//        }
//        try {
//            Integer.parseInt(txtSoLuong.getText());
//        } catch (NumberFormatException e) {
//            lblSoLuong.setText("Vui lòng nhập số!");
//            return false;
//        }
//        return true;
//    }

    private void selectIcon() {

        JFileChooser fc = new JFileChooser("logos\\avatars");
        FileFilter filter = new FileNameExtensionFilter("Image Files", "gif", "jpeg", "jpg", "png");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        int kq = fc.showOpenDialog(fc);
        if (kq == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fc.getSelectedFile();
            XImage.saveIconNH(file);
            ImageIcon icon = XImage.readIconNH(file.getName());
            lblHinh.setIcon(icon);
            lblHinh.setToolTipText(file.getName());
        }
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
        lblHinh = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        txtGiaXuat = new javax.swing.JTextField();
        txtGiaNhap = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        cboLoaiSP = new javax.swing.JComboBox<>();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        lblMaSP = new javax.swing.JLabel();
        lblGiaNhap = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        lblGiaNhap2 = new javax.swing.JLabel();
        lblGiaXuat = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        lblLSP = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnTang = new javax.swing.JButton();
        btnGiam = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachSP = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        jLabel4.setText("Mã sản phẩm:");

        jLabel5.setText("Tên sản phẩm:");

        jLabel6.setText("Loại sản phẩm:");

        jLabel7.setText("Giá nhập:");

        jLabel8.setText("Giá xuất:");

        jLabel9.setText("Số lượng:");

        cboLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Chọn loại sản phẩm--" }));

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
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        btnUpdate.setText("Cập Nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 204, 255));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(204, 204, 255));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clear.png"))); // NOI18N
        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        lblMaSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblMaSPKeyReleased(evt);
            }
        });

        lblGiaNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblGiaNhapKeyReleased(evt);
            }
        });

        lblTenSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblTenSPKeyReleased(evt);
            }
        });

        lblGiaXuat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblGiaXuatKeyReleased(evt);
            }
        });

        lblSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblSoLuongKeyReleased(evt);
            }
        });

        lblLSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblLSPKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(lblMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(185, 185, 185))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(216, 216, 216)
                                .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblGiaNhap2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblGiaXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(177, 177, 177))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel6))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(5, 5, 5)))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel9)
                                        .addGap(53, 53, 53)
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8))
                                        .addGap(60, 60, 60)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGiaXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(112, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtGiaXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblGiaNhap2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblGiaXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 4, Short.MAX_VALUE))
                            .addComponent(lblTenSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("SẢN PHẨM");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

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

        tblDanhSachSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Giá nhập", "Giá xuất", "Số lượng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblDanhSachSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachSP);

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
                .addGap(0, 351, Short.MAX_VALUE)
                .addComponent(btnFirst)
                .addGap(54, 54, 54)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnLast)
                .addGap(401, 401, 401))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTang, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(btnGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185)
                .addComponent(jLabel2)
                .addGap(29, 29, 29)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(btnPrev)
                    .addComponent(btnFirst))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(513, 513, 513)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblDanhSachSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachSPMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.edit();
    }//GEN-LAST:event_tblDanhSachSPMouseClicked
    }
    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        this.insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (txtTenSP.getText().equals("")) {
            XDialog.alert(main, "Vui lòng chọn sản phẩm cần sửa");
        } else {
            this.update();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (btnInsert.getText().equalsIgnoreCase("Thêm")) {
            if (txtMaSP.getText().equals("")) {
                XDialog.alert(main, "Vui lòng chọn sản phẩm cần xóa");
            } else {
                boolean check = XDialog.confirm(main, "Bạn có chắc muốn xóa sản phẩm này ");
                if (check) {
                    delete();
                } else {

                }
            }
        } else if (btnInsert.getText().equalsIgnoreCase("Xác Nhận")) {
            this.reset();
            btnInsert.setText("Thêm");
            btnDelete.setText("Xóa");
            btnUpdate.setEnabled(true);
            txtMaSP.setEditable(false);
        }
        this.delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        this.reset();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTangActionPerformed
        this.ASC();
    }//GEN-LAST:event_btnTangActionPerformed

    private void btnGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiamActionPerformed
        this.DESC();
    }//GEN-LAST:event_btnGiamActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        String str = txtSearch.getText();
        search(str);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void lblMaSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblMaSPKeyReleased
        // TODO add your handling code here:
        lblMaSP.setText("");
    }//GEN-LAST:event_lblMaSPKeyReleased

    private void lblGiaNhapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblGiaNhapKeyReleased
        // TODO add your handling code here:
        lblGiaNhap.setText("");
    }//GEN-LAST:event_lblGiaNhapKeyReleased

    private void lblTenSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblTenSPKeyReleased
        // TODO add your handling code here:
        lblTenSP.setText("");
    }//GEN-LAST:event_lblTenSPKeyReleased

    private void lblGiaXuatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblGiaXuatKeyReleased
        // TODO add your handling code here:
        lblGiaXuat.setText("");
    }//GEN-LAST:event_lblGiaXuatKeyReleased

    private void lblLSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblLSPKeyReleased
        // TODO add your handling code here:
        lblLSP.setText("");
    }//GEN-LAST:event_lblLSPKeyReleased

    private void lblSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblSoLuongKeyReleased
        // TODO add your handling code here:
        lblSoLuong.setText("");
    }//GEN-LAST:event_lblSoLuongKeyReleased

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        this.selectIcon();
    }//GEN-LAST:event_lblHinhMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnGiam;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnTang;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboLoaiSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGiaNhap;
    private javax.swing.JLabel lblGiaNhap2;
    private javax.swing.JLabel lblGiaXuat;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblLSP;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JTable tblDanhSachSP;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtGiaXuat;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables

    private void setForm(SanPham sp) {
        txtMaSP.setText(sp.getMaSanPham());
        txtTenSP.setText(sp.getTenSanPham());
        lblHinh.setIcon(XImage.readIconCD("NoImage.png"));
        if (sp.getHinh() != null) {
            lblHinh.setToolTipText(sp.getHinh());
            lblHinh.setIcon(XImage.readIconCD(sp.getHinh()));
        }
        txtGiaNhap.setText(sp.getGiaNhap());
        txtGiaXuat.setText(sp.getGiaXuat());
        int SoLuong = sp.getSoLuong();
        txtSoLuong.setText(String.valueOf(SoLuong));
        String tenLSP = lspdao.selectById(sp.getMaLoaiSanPham()).getTenLoaiSanPham();
        cboLoaiSP.setSelectedItem(tenLSP);
    }

    private void updateStatus() {

    }

    private SanPham getForm() {
        SanPham sp = new SanPham();
        sp.setMaSanPham(txtMaSP.getText());
        sp.setTenSanPham(txtTenSP.getText());
        sp.setHinh(lblHinh.getToolTipText());
        sp.setMaLoaiSanPham(lspdao.findIdByName((String) cboLoaiSP.getSelectedItem()));
        sp.setGiaNhap(txtTenSP.getText());
        sp.setGiaXuat(txtTenSP.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        return sp;
    }

    private void dispose() {
        dispose();
    }
}
