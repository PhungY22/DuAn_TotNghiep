/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.poly.model;

import com.poly.dao.NhanVienDAO;
import com.poly.entity.NhanVien;
import static com.poly.model.DangNhapJFrame.main;
import com.poly.utils.DateHelper;
import com.poly.utils.ShareHelper;
import com.poly.utils.XDate;
//import com.poly.utils.XDate;
import com.poly.utils.XDialog;
import com.poly.utils.XImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
public class NhanVienJPanel extends javax.swing.JPanel {

    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    NhanVienDAO nvdao = new NhanVienDAO();
    int row = 0;
    MainJFrame main;

    /**
     * Creates new form NhanVienJPanel
     */
    public NhanVienJPanel() {
        initComponents();
        init();
    }

    public void init() {
        this.fillTable();
//      this.fillComboBox();
    }

    public void fillTable() { //gọi dữ liệu lên bảng
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            List<NhanVien> list = nvdao.selectAll();
            for (NhanVien nv : list) {
                Object[] row = {nv.getMaNhanVien(), nv.getHinh(), nv.getTenNhanVien(), nv.getSoDienThoai(), nv.getEmail(), nv.getDiaChi(),nv.getNgayNhanViec(), nv.getNgayThangNamSinh(), nv.isGioiTinh() ? "Nam" : "Nữ", nv.getChucVu()};
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    void selectComboBox() {
//        NhanVien nv = (NhanVien) cboChucVu.getSelectedItem();
//    }
//
//    void fillComboBox() {
//        List<NhanVien> listNV = nvdao.selectAll();
//        for (int i = 0; listNV.size() > i; i++) {
//            cboChucVu.addItem(listNV.get(i).getChucVu());
//        }
//    }
    private void insert() { //Thêm
        NhanVien nv = getForm();
        try {
            nvdao.insert(nv);
            this.fillTable();
            XDialog.alert(this, "Thêm mới nhân viên thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm mới nhân viên thất bại");
        }
    }

    private void update() { //Sửa
        NhanVien nv = getForm();
        try {
            nvdao.update(nv);
            this.fillTable();

            XDialog.alert(this, "Cập nhật nhân viên thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật nhân viên thất bại");
        }
    }

    private void delete() { //xóa
        if (XDialog.confirm(this, "Bạn có thực sự muốn xóa sản phẩm này không?")) {
            String nv = txtMaNhanVien.getText();
            try {
                nvdao.delete(nv);
                this.fillTable();
//                this.clearForm();
                XDialog.alert(this, "Xóa nhân viên thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Xóa nhân viên thất bại!");

            }
        }
    }

//    private void reset() { // Làm mới
//        txtMaNhanVien.setText("");
//        txtMaNhanVien.setEditable(true);
//        lblHinh.setText("");
//        txtTenNhanVien.setText("");
//        txtMatKhau.setText("");
//        txtSoDienThoai.setText("");
//        txtEmail.setText("");
//        txtDiaChi.setText("");
//        txtNamSinh.setText("");
//        rdoGioiTinhNam.setText("");
//        rdoGioiTinhNu.setText("");
//        cboChucVu.setSelectedIndex(0);
//        //this.row = -1;
//        lblMaNV.setText("");
//        lblTenNV.setText("");
//        lblMatKhau.setText("");
//        lblSDT.setText("");
//        lblEmail.setText("");
//        lblDiaChi.setText("");
//        lblNamSinh.setText("");
//        lblGioiTinh.setText("");
//        lblChucVu.setText("");
//    }
    private void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
        lblHinh.setIcon(null); // Đặt hình ảnh về null để xóa hình
        lblHinh.setToolTipText(null); // Đặt tooltip về null
        // Đặt ngày tháng năm sinh về giá trị mặc định hoặc trống
        txtNamSinh.setText(""); // Trường hợp làm trống
        // Hoặc
        // txtNamSinh.setText("01/01/2000"); // Giá trị mặc định
        this.row = -1;
        //this.updateStatus();
    }

    private void edit() {
        this.row = tblNhanVien.getSelectedRow();
        if (this.row >= 0) {
            String id = (String) tblNhanVien.getValueAt(this.row, 0);
            NhanVien nv = nvdao.selectById(id);
            this.setForm(nv);
        }
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
        if (this.row < tblNhanVien.getRowCount() - 1) {
            this.row++;
            selectAndScrollToVisible();
        }
    }

    private void last() {
        this.row = tblNhanVien.getRowCount() - 1;
        selectAndScrollToVisible();
    }

    private void selectAndScrollToVisible() {
        tblNhanVien.setRowSelectionInterval(this.row, this.row);
        tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(this.row, 0, true));
        this.edit();
    }

    void ASC() { // sắp xếp tăng
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblNhanVien.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    void DESC() { //sắp xếp giảm
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblNhanVien.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblNhanVien.setRowSorter(trs);

        // Chỉ định cột "ID" và cột "Tên" để áp dụng bộ lọc
        int columnIndexID = 0; // Index của cột "ID" trong model
        int columnIndexTen = 1; // Index của cột "Tên" trong model

        // Sử dụng RowFilter.orFilter để kết hợp hai điều kiện tìm kiếm
        RowFilter<DefaultTableModel, Object> idFilter = RowFilter.regexFilter(str, columnIndexID);
        RowFilter<DefaultTableModel, Object> tenFilter = RowFilter.regexFilter(str, columnIndexTen);

        trs.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, tenFilter)));

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
        jPanel1 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        txtTenNhanVien = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JPasswordField();
        txtSoDienThoai = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        cboChucVu = new javax.swing.JComboBox<>();
        txtNamSinh = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        rdoGioiTinhNam = new javax.swing.JRadioButton();
        rdoGioiTinhNu = new javax.swing.JRadioButton();
        txtNgayNhanViec = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnTang = new javax.swing.JButton();
        btnGiam = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        jLabel3.setText("Mã nhân viên:");

        jLabel4.setText("Tên nhân viên:");

        jLabel5.setText("Password:");

        jLabel6.setText("Số điện thoại:");

        jLabel7.setText("Email:");

        jLabel8.setText("Địa chỉ:");

        jLabel9.setText("Năm sinh:");

        jLabel10.setText("Giới tính:");

        jLabel11.setText("Chức vụ:");

        txtMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhanVienActionPerformed(evt);
            }
        });

        txtMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatKhauActionPerformed(evt);
            }
        });

        cboChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quản lí ", "Nhân viên kĩ thuật", "Nhân viên kinh doanh", "Nhân viên hỗ trợ", "Nhân viên sale " }));
        cboChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChucVuActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(204, 204, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(204, 204, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setText("Cập Nhật");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(204, 204, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(204, 204, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoGioiTinhNam);
        rdoGioiTinhNam.setText("Nam");
        rdoGioiTinhNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGioiTinhNamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoGioiTinhNu);
        rdoGioiTinhNu.setText("Nữ");

        jLabel13.setText("Ngày nhận việc");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(txtTenNhanVien)
                            .addComponent(txtMatKhau))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(txtDiaChi)
                            .addComponent(txtSoDienThoai))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtNgayNhanViec, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(rdoGioiTinhNam)
                                            .addGap(18, 18, 18)
                                            .addComponent(rdoGioiTinhNu))
                                        .addComponent(txtNamSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13)
                                .addComponent(txtNgayNhanViec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel6)
                                .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 62, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel8)
                                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10)
                                            .addComponent(rdoGioiTinhNam)
                                            .addComponent(rdoGioiTinhNu)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel7)
                                .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(txtNamSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblHinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        btnTang.setBackground(new java.awt.Color(204, 204, 255));
        btnTang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTang.setText("Sắp xếp tăng dần ");
        btnTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTangActionPerformed(evt);
            }
        });

        btnGiam.setBackground(new java.awt.Color(204, 204, 255));
        btnGiam.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGiam.setText("Sắp xếp giảm dần");
        btnGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiamActionPerformed(evt);
            }
        });

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Hình", "Tên nhân viên", "Số điện thoại", "Email", "Địa chỉ", "Ngày nhận việc", "Năm sinh", "Giới tính ", "Chức vụ"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

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

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel12.setText("Tìm kiếm");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTang, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(btnGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnFirst)
                            .addGap(84, 84, 84)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(105, 105, 105)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(95, 95, 95)
                            .addComponent(btnLast))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTang)
                    .addComponent(btnGiam)
                    .addComponent(jLabel12)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("NHÂN VIÊN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(497, 497, 497)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChucVuActionPerformed

    }//GEN-LAST:event_cboChucVuActionPerformed

    private void rdoGioiTinhNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGioiTinhNamActionPerformed

    }//GEN-LAST:event_rdoGioiTinhNamActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        int selectedRow = tblNhanVien.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            String MaNhanVien = model.getValueAt(selectedRow, 0).toString();
            String Hinh = model.getValueAt(selectedRow, 1).toString();
            String TenNhanVien = model.getValueAt(selectedRow, 2).toString();
            String SoDienThoai = model.getValueAt(selectedRow, 3).toString();
            String Email = model.getValueAt(selectedRow, 4).toString();
            String DiaChi = model.getValueAt(selectedRow, 5).toString();
            String NgayNhanViec = model.getValueAt(selectedRow, 6).toString();
            try {
                Date dateOfBirth = inputDateFormat.parse(NgayNhanViec); // Parse ngày tháng từ định dạng gốc
                NgayNhanViec = outputDateFormat.format(dateOfBirth); // Chuyển đổi sang định dạng mới
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String NamSinh = model.getValueAt(selectedRow, 7).toString();
            try {
                Date dateOfBirth = inputDateFormat.parse(NamSinh); // Parse ngày tháng từ định dạng gốc
                NamSinh = outputDateFormat.format(dateOfBirth); // Chuyển đổi sang định dạng mới
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Handling Gender
            Object GioiTinh = model.getValueAt(selectedRow, 8);
            if (GioiTinh instanceof Boolean) {
                boolean gioiTinh = (Boolean) GioiTinh;
                if (gioiTinh) {
                    rdoGioiTinhNam.setSelected(true);
                } else {
                    rdoGioiTinhNu.setSelected(true);
                }
            }

            // Handling ChucVu (Position/Role)
            Object chucVuValue = model.getValueAt(selectedRow, 9);
            String ChucVu = (chucVuValue != null) ? chucVuValue.toString() : "";

            // Populating text fields with retrieved data
            txtMaNhanVien.setText(MaNhanVien);
            lblHinh.setText(Hinh);
            txtTenNhanVien.setText(TenNhanVien);
            txtSoDienThoai.setText(SoDienThoai);
            txtEmail.setText(Email);
            txtDiaChi.setText(DiaChi);
            txtNgayNhanViec.setText(NgayNhanViec);
            txtNamSinh.setText(NamSinh);
            // Set Gender based on retrieved data
            // (Double-check this logic to ensure it sets the correct gender based on your data structure)
            if (GioiTinh instanceof Boolean) {
                boolean gioiTinh = (Boolean) GioiTinh;
                if (gioiTinh) {
                    rdoGioiTinhNam.setSelected(true);
                } else {
                    rdoGioiTinhNu.setSelected(true);
                }
            }

            // Set ChucVu in the combo box
            cboChucVu.setSelectedItem(ChucVu);
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        NhanVien nv = getForm();
        try {
            nvdao.insert(nv);
            this.fillTable();

            XDialog.alert(this, "Thêm mới nhân viên thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm mới viên thất bại");
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (txtMaNhanVien.getText().equals("")) {
            XDialog.alert(main, "Vui lòng chọn nhân viên cần sửa");
        } else {
            this.update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (btnThem.getText().equalsIgnoreCase("Thêm")) {
            if (txtMaNhanVien.getText().equals("")) {
                XDialog.alert(main, "Vui lòng chọn nhân viên cần xóa");
            } else {
                boolean check = XDialog.confirm(main, "Bạn có chắc muốn xóa nhân viên này này");
                if (check) {
                    delete();
                } else {

                }
            }
        } else if (btnThem.getText().equalsIgnoreCase("Xác Nhận")) {
            this.clearForm();
            btnThem.setText("Thêm");
            btnXoa.setText("Xóa");
            btnSua.setEnabled(true);
            txtMaNhanVien.setEditable(false);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        this.clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTangActionPerformed
        this.ASC();
    }//GEN-LAST:event_btnTangActionPerformed

    private void btnGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiamActionPerformed
        this.DESC();
    }//GEN-LAST:event_btnGiamActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        String str = txtTimKiem.getText();
        search(str);
    }//GEN-LAST:event_txtTimKiemActionPerformed

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

    private void txtMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNhanVienActionPerformed

    private void txtMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatKhauActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        this.selectIcon();
    }//GEN-LAST:event_lblHinhMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnGiam;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTang;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboChucVu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JRadioButton rdoGioiTinhNam;
    private javax.swing.JRadioButton rdoGioiTinhNu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtNamSinh;
    private javax.swing.JTextField txtNgayNhanViec;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

    private NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(txtMaNhanVien.getText());
        nv.setHinh(lblHinh.getToolTipText());
        nv.setTenNhanVien(txtTenNhanVien.getText());
        nv.setMatKhau(txtMatKhau.getText());
        nv.setSoDienThoai(txtSoDienThoai.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setNgayNhanViec(XDate.toDate(txtNgayNhanViec.getText()));
        nv.setNgayThangNamSinh(XDate.toDate(txtNamSinh.getText()));
        boolean gt = false;
        if (rdoGioiTinhNam.isSelected()) {
            gt = true;
        }
        nv.setGioiTinh(gt);
//        nv.setMaNhanVien(nvdao.findIdByName((String) cboChucVu.getSelectedItem()));
        boolean gioiTinhNam = rdoGioiTinhNam.isSelected();
//        nv.setGioiTinh(gioiTinhNam);
        String selectedChucVu = (String) cboChucVu.getSelectedItem();
        nv.setChucVu(selectedChucVu);
        return nv;
    }

    private void updateStatus() {

    }

    private void setForm(NhanVien nv) {
        
        txtMaNhanVien.setText(nv.getMaNhanVien());
        lblHinh.setToolTipText(nv.getHinh());
        if (nv.getHinh() != null) {
            lblHinh.setIcon(ShareHelper.readLogo(nv.getHinh()));
        }
        txtTenNhanVien.setText(nv.getTenNhanVien());
        txtMatKhau.setText(nv.getMatKhau());
        txtSoDienThoai.setText(nv.getSoDienThoai());
        txtDiaChi.setText(nv.getDiaChi());
        txtEmail.setText(nv.getEmail());
        txtNgayNhanViec.setText(XDate.toString(nv.getNgayNhanViec()));
        txtNamSinh.setText(XDate.toString(nv.getNgayThangNamSinh()));
        boolean gt = nv.isGioiTinh();
        if (gt == false) {
            rdoGioiTinhNu.isSelected();
        }
        nv.setGioiTinh(gt);
        String tenLNV = nvdao.selectById(nv.getMaNhanVien()).getTenNhanVien();
        cboChucVu.setSelectedItem(tenLNV);
        
    }
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
}
