/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.poly.model;

import com.poly.dao.KhachHangDAO;
import com.poly.entity.KhachHang;
import com.poly.entity.SanPham;
import static com.poly.model.MainJFrame.main;
import com.poly.utils.XDialog;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Nhu Y
 */
public class KhachHangJPanel extends javax.swing.JPanel {

    KhachHangDAO khdao = new KhachHangDAO();
    ArrayList<KhachHang> list = new ArrayList<>();
    int row = -1;
    private Component main;

    /**
     * Creates new form KhachHangJPanel
     */
    public KhachHangJPanel() {
        initComponents();
        init();
    }

    public void init() {
        this.fillTable();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachKH.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
            List<KhachHang> list = khdao.selectAll();
            for (KhachHang sp : list) {
                Object[] row = {sp.getMaKhachHang(), sp.getTenKhachHang(), sp.getDiaChi(), sp.getSoDienThoai(), sp.getEmail(), sp.isGioiTinh() ? "Nam" : "Nữ"};
                model.addRow(row);
            }
        } catch (Exception e) {
//            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            throw new RuntimeException(e);
//            System.out.println("Lỗi truy vấn dữ liệu");
        }
    }

    private void insert() {
        KhachHang khachHang = getForm();
        try {
            String errorMessage = validateDataAndGetErrorMessage(khachHang);
            if (errorMessage.isEmpty()) {
                insertAndNotify(khachHang);
            } else {
                XDialog.alert(this, errorMessage);
            }
        } catch (Exception e) {
            handleInsertionFailure(e);
        }
    }

    private String validateDataAndGetErrorMessage(KhachHang khachHang) {
        String maKhachHang = khachHang.getMaKhachHang();
        String tenKhachHang = khachHang.getTenKhachHang();
        String diaChi = khachHang.getDiaChi();
        String soDienThoai = khachHang.getSoDienThoai();
        String email = khachHang.getEmail();

        // Kiểm tra các điều kiện nhập liệu cụ thể
        boolean isValidMaKhachHang = maKhachHang.matches("^[a-zA-Z0-9]+$");
        boolean isValidTenKhachHang = tenKhachHang.matches("^[a-zA-Z\\s]+$");
        boolean isValidDiaChi = !diaChi.isEmpty();
        boolean isValidSoDienThoai = soDienThoai.matches("^\\d{10,}$"); // Kiểm tra số điện thoại có ít nhất 10 chữ số
        boolean isValidEmail = email.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$"); // Kiểm tra định dạng email

        // Xác định thông báo lỗi
        StringBuilder errorMessage = new StringBuilder();
        if (!isValidMaKhachHang) {
            errorMessage.append("Mã khách hàng không hợp lệ!\n");
        }
        if (!isValidTenKhachHang) {
            errorMessage.append("Tên khách hàng không hợp lệ!\n");
        }
        if (!isValidDiaChi) {
            errorMessage.append("Địa chỉ không được để trống!\n");
        }
        if (!isValidSoDienThoai) {
            errorMessage.append("Số điện thoại không hợp lệ!\n");
        }
        if (!isValidEmail) {
            errorMessage.append("Email không hợp lệ!\n");
        }

        // Trả về thông báo lỗi
        return errorMessage.toString();
    }

    private void update() {
        KhachHang khachHang = this.getForm();
        try {
            String errorMessage = validateUpdateDataAndGetErrorMessage(khachHang);
            if (errorMessage.isEmpty()) {
                updateAndNotify(khachHang);
            } else {
                XDialog.alert(this, errorMessage);
            }
        } catch (Exception e) {
            handleUpdateFailure(e);
        }
    }

    private String validateUpdateDataAndGetErrorMessage(KhachHang khachHang) {
        String maKhachHang = khachHang.getMaKhachHang();
        String tenKhachHang = khachHang.getTenKhachHang();
        String diaChi = khachHang.getDiaChi();
        String soDienThoai = khachHang.getSoDienThoai();
        String email = khachHang.getEmail();
        boolean gioiTinh = khachHang.isGioiTinh();

        // Kiểm tra các điều kiện nhập liệu cụ thể
        boolean isValidMaKhachHang = maKhachHang.matches("^[a-zA-Z0-9]+$");
        boolean isValidTenKhachHang = tenKhachHang.matches("^[a-zA-Z\\s]+$");
        boolean isValidDiaChi = !diaChi.isEmpty();
        boolean isValidSoDienThoai = soDienThoai.matches("^\\d{10,}$"); // Kiểm tra số điện thoại có ít nhất 10 chữ số
        boolean isValidEmail = email.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$"); // Kiểm tra định dạng email

        // Xác định thông báo lỗi
        StringBuilder errorMessage = new StringBuilder();
        if (!isValidMaKhachHang) {
            errorMessage.append("Mã khách hàng không hợp lệ!\n");
        }
        if (!isValidTenKhachHang) {
            errorMessage.append("Tên khách hàng không hợp lệ!\n");
        }
        if (!isValidDiaChi) {
            errorMessage.append("Địa chỉ không được để trống!\n");
        }
        if (!isValidSoDienThoai) {
            errorMessage.append("Số điện thoại không hợp lệ!\n");
        }
        if (!isValidEmail) {
            errorMessage.append("Email không hợp lệ!\n");
        }

        // Trả về thông báo lỗi
        return errorMessage.toString();
    }

    private void updateAndNotify(KhachHang khachHang) {
        try {
            khdao.update(khachHang); // Cập nhật
            fillTable(); // Đổ lại bảng
            XDialog.alert(this, "Cập nhật khách hàng thành công!");
        } catch (Exception e) {
            handleUpdateFailure(e);
        }
    }

    private void handleUpdateFailure(Exception e) {
        XDialog.alert(this, "Cập nhật khách hàng thất bại!");
        e.printStackTrace();
    }

    private void delete() {
        if (XDialog.confirm(this, "Bạn có thực sự muốn xóa khách hàng này không?")) {
            String sp = txtMaKH.getText();
            try {
                khdao.delete(sp);
                this.fillTable();
                this.clearForm();
                XDialog.alert(this, "Xóa khách hàng thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Xóa khách hàng thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void edit() {
        this.row = tblDanhSachKH.getSelectedRow();
        if (this.row >= 0) {
            String id = (String) tblDanhSachKH.getValueAt(this.row, 0);
            KhachHang sp = khdao.selectById(id);
            this.setForm(sp);
        }
    }

    private void clearForm() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        ButtonGroup group = new ButtonGroup();
        group.add(rdoNam);
        group.add(rdoNu);
        group.clearSelection();
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
        if (this.row < tblDanhSachKH.getRowCount() - 1) {
            this.row++;
            selectAndScrollToVisible();
        }
    }

    private void last() {
        this.row = tblDanhSachKH.getRowCount() - 1;
        selectAndScrollToVisible();
    }

    void ASC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachKH.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        // Chỉ định cột "mã" và thứ tự sắp xếp là tăng dần
        RowSorter.SortKey sortKey = new RowSorter.SortKey(0, SortOrder.ASCENDING);

        // Đặt danh sách SortKey chỉ chứa một phần tử vào TableRowSorter
        sorter.setSortKeys(List.of(sortKey));

        // Đặt TableRowSorter cho JTable
        tblDanhSachKH.setRowSorter(sorter);

        // Thực hiện sắp xếp
        sorter.sort();
    }

    void DESC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachKH.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblDanhSachKH.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachKH.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblDanhSachKH.setRowSorter(trs);

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
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jblGioiTinh = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        txtDiaChi = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        btnASC = new javax.swing.JButton();
        btnDESC = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachKH = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("KHÁCH HÀNG");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel4.setText("Mã khách hàng:");

        jLabel5.setText("Tên khách hàng:");

        jLabel6.setText("Địa chỉ:");

        jLabel7.setText("Số điện thoại: ");

        jLabel8.setText("Email:");

        jblGioiTinh.setText("Giới tính:");

        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(204, 204, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/insert.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapNhat.setBackground(new java.awt.Color(204, 204, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        btnCapNhat.setText("Cập Nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(204, 204, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(204, 204, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clear.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");
        rdoNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");
        rdoNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(294, 294, 294)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnCapNhat))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(183, 183, 183)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jblGioiTinh)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)))))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoNam)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(rdoNu))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                        .addComponent(txtEmail))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnLamMoi)))
                .addContainerGap(296, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jblGioiTinh)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        btnASC.setBackground(new java.awt.Color(204, 204, 255));
        btnASC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnASC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/performance.png"))); // NOI18N
        btnASC.setText("Sắp xếp tăng dần ");
        btnASC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnASCActionPerformed(evt);
            }
        });

        btnDESC.setBackground(new java.awt.Color(204, 204, 255));
        btnDESC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDESC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/statistics.png"))); // NOI18N
        btnDESC.setText("Sắp xếp giảm dần");
        btnDESC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDESCActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setText("Tìm kiếm");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        tblDanhSachKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại", "Email", "Giới tính"
            }
        ));
        tblDanhSachKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachKHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachKH);

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
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnASC, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDESC)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(337, 337, 337)
                        .addComponent(btnFirst)
                        .addGap(63, 63, 63)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(btnLast)))
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnASC)
                    .addComponent(btnDESC)
                    .addComponent(jLabel2)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(btnPrev)
                    .addComponent(btnFirst))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGap(510, 510, 510)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNamActionPerformed

    private void rdoNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNuActionPerformed

    private void tblDanhSachKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachKHMouseClicked
        int selectedRow = tblDanhSachKH.getSelectedRow();

        if (selectedRow != -1) { // Check if a row is selected
            DefaultTableModel model = (DefaultTableModel) tblDanhSachKH.getModel();
            String MaKhachHang = model.getValueAt(selectedRow, 0).toString();
            String TenKhachHang = model.getValueAt(selectedRow, 1).toString();
            String DiaChi = model.getValueAt(selectedRow, 2).toString(); // Assuming the first column contains the MaKhachHang
            String SoDienThoai = model.getValueAt(selectedRow, 3).toString(); // Assuming the second column contains the TenKhachHang
            String Email = model.getValueAt(selectedRow, 4).toString();
            if (model.getValueAt(selectedRow, 5) instanceof Boolean) {
                Boolean GioiTinh = (Boolean) model.getValueAt(selectedRow, 5);
                if (GioiTinh) {
                    rdoNam.setSelected(true);
                } else {
                    rdoNu.setSelected(true);
                }
            }

            // Populate text fields with the retrieved data
            txtMaKH.setText(MaKhachHang);
            txtTenKH.setText(TenKhachHang);
            txtDiaChi.setText(DiaChi);
            txtSDT.setText(SoDienThoai);
            txtEmail.setText(Email);
            if (model.getValueAt(selectedRow, 5) instanceof Boolean) {
                rdoNam.setSelected(true);
            } else {
                rdoNu.setSelected(true);
            }

        }
    }//GEN-LAST:event_tblDanhSachKHMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:   
    }//GEN-LAST:event_txtMaKHActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        this.clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (txtMaKH.getText().equals("")) {
            XDialog.alert(main, "Vui lòng chọn khách hàng cần sửa");
        } else {
            this.update();
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (txtMaKH.getText().equals("")) {
            XDialog.alert(main, "Vui lòng chọn khách hàng cần xóa");
        } else {
            this.delete();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnASCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnASCActionPerformed
        this.ASC();
    }//GEN-LAST:event_btnASCActionPerformed

    private void btnDESCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDESCActionPerformed
        this.DESC();
    }//GEN-LAST:event_btnDESCActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnASC;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnDESC;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jblGioiTinh;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblDanhSachKH;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenKH;
    // End of variables declaration//GEN-END:variables

    private KhachHang getForm() {
        KhachHang sp = new KhachHang();
        sp.setMaKhachHang(txtMaKH.getText());
        sp.setTenKhachHang(txtTenKH.getText());
        sp.setDiaChi(txtDiaChi.getText());
        sp.setSoDienThoai(txtSDT.getText());
        sp.setEmail(txtEmail.getText());
        boolean gt = false;
        if (rdoNam.isSelected()) {
            gt = true;
        }
        sp.setGioiTinh(gt);
        return sp;
    }

    private void setForm(KhachHang hv) {
        KhachHang sp = new KhachHang();
        txtMaKH.setText(hv.getMaKhachHang());
        txtTenKH.setText(hv.getTenKhachHang());
        txtDiaChi.setText(hv.getDiaChi());
        txtSDT.setText(hv.getSoDienThoai());
        txtEmail.setText(hv.getEmail());
        boolean gt = sp.isGioiTinh();
        if (gt == false) {
            rdoNu.isSelected();
        }
        sp.setGioiTinh(gt);
//        rdoNam.setSelected(hv.isGioiTinh());
//        rdoNu.setSelected(!hv.isGioiTinh());

    }

    private void selectAndScrollToVisible() {
        tblDanhSachKH.setRowSelectionInterval(this.row, this.row);
        tblDanhSachKH.scrollRectToVisible(tblDanhSachKH.getCellRect(this.row, 0, true));
        this.edit();
    }

    private void insertAndNotify(KhachHang khachHang) {
        try {
            khdao.insert(khachHang); // Thêm mới
            fillTable(); // Đổ lại bảng
            clearForm(); // Xóa trắng form
            XDialog.alert(this, "Thêm khách hàng mới thành công!");
        } catch (Exception e) {
            handleInsertionFailure(e);
        }
    }

    private void handleInsertionFailure(Exception e) {
        XDialog.alert(this, "Thêm khách hàng mới thất bại!");
        e.printStackTrace();
    }

}
