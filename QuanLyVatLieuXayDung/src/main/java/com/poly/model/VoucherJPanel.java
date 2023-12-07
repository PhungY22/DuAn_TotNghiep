/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.poly.model;

import com.poly.dao.VoucherDAO;
import com.poly.entity.KhachHang;
import com.poly.entity.Voucher;
import com.poly.utils.XDialog;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static com.poly.model.DangNhapJFrame.main;
import java.awt.Component;

/**
 *
 * @author Nhu Y
 */
public class VoucherJPanel extends javax.swing.JPanel {

    VoucherDAO Voudao = new VoucherDAO();
    int row = -1;
    private Component main;

    /**
     * Creates new form VoucherJPanel
     */
    public VoucherJPanel() {
        initComponents();
        init();
    }

    public void init() {
        this.fillTable();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachVou.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
            List<Voucher> list = Voudao.selectAll();
            for (Voucher sp : list) {
                Object[] row = {sp.getMaVoucher(), sp.getTenVoucher(), sp.getGiaTriVoucher(), sp.getNgayHetHan(), sp.getSoLuong()};
                model.addRow(row);
            }
        } catch (Exception e) {
//            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            throw new RuntimeException(e);
//            System.out.println("Lỗi truy vấn dữ liệu");
        }
    }
    
    

    private void insert() {
        Voucher sp = this.getForm();
        try {
            if (validateData(sp)) {
                Voudao.insert(sp); // thêm mới
                this.fillTable(); // đổ lại bảng
                this.clearForm(); // xóa trắng form
                XDialog.alert(this, "Thêm Voucher mới thành công!");
            } else {
                XDialog.alert(this, "Vui lòng nhập đầy đủ thông tin và đúng định dạng cho các trường sau:\n" + getErrorMessage(sp));
            }
        } catch (Exception e) {
            XDialog.alert(this, "Thêm Voucher mới thất bại!");
            e.printStackTrace();
        }
    }

    private boolean validateData(Voucher voucher) {
        return isStringValid(voucher.getMaVoucher()) &&
               isStringValid(voucher.getTenVoucher()) &&
               isBigDecimalValid(voucher.getGiaTriVoucher()) &&
               isDateValid(voucher.getNgayHetHan()) &&
               isIntValid(voucher.getSoLuong());
    }
    
    private boolean isBigDecimalValid(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0; // Sửa thành kiểm tra lớn hơn hoặc bằng 0
    }
    
    private boolean isStringValid(String str) {
        return str != null && !str.isEmpty();
    }

    private boolean isDateValid(Date date) {
        return date != null; // Có thể thêm kiểm tra định dạng ngày tháng nếu cần thiết
    }

    private boolean isIntValid(int value) {
        return value > 0; // Số lượng phải là số nguyên dương
    }

    private String getErrorMessage(Voucher voucher) {
        StringBuilder errorMessage = new StringBuilder();

        if (!isStringValid(voucher.getMaVoucher())) {
            errorMessage.append("- Mã Voucher\n");
        }
        if (!isStringValid(voucher.getTenVoucher())) {
            errorMessage.append("- Tên Voucher\n");
        }
        if (!isBigDecimalValid(voucher.getGiaTriVoucher())) {
            errorMessage.append("- Giá Trị Voucher\n");
        }
        if (!isDateValid(voucher.getNgayHetHan())) {
            errorMessage.append("- Ngày Hết Hạn\n");
        }
        if (!isIntValid(voucher.getSoLuong())) {
            errorMessage.append("- Số Lượng Voucher\n");
        } else if (voucher.getSoLuong() == 0) {
            errorMessage.append("- Số Lượng Voucher phải lớn hơn 0\n");
        }

        return errorMessage.toString();
    }

    private void update() {
        Voucher sp = this.getForm();
        try {
            Voudao.update(sp); // cập nhật
            this.fillTable(); // đổ lại bảng
            XDialog.alert(this, "Cập nhật Voucher thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật Voucher thất bại!");
            e.printStackTrace();
        }
    }

    private void delete() {
        if (XDialog.confirm(this, "Bạn có thực sự muốn xóa Voucher này không?")) {
            String sp = txtMaVou.getText();
            try {
                Voudao.delete(sp);
                this.fillTable();
                this.clearForm();
                XDialog.alert(this, "Xóa Voucher thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Xóa Voucher thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void clearForm() {
        txtMaVou.setText("");
        txtTenVou.setText("");
        txtGiaTriVou.setText("");
        txtNgayHetHan.setText(null); // Đặt phần tử được chọn là null, nếu có
        txtSoLuong.setText("");

        txtMaVou.requestFocus();
    }

    private void edit() {
        this.row = tblDanhSachVou.getSelectedRow();
        if (this.row >= 0) {
            String id = (String) tblDanhSachVou.getValueAt(this.row, 0);
            Voucher sp = Voudao.selectById(id);
//            this.setForm(sp);
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
        if (this.row < tblDanhSachVou.getRowCount() - 1) {
            this.row++;
            selectAndScrollToVisible();
        }
    }

    private void last() {
        this.row = tblDanhSachVou.getRowCount() - 1;
        selectAndScrollToVisible();
    }
    
        void ASC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachVou.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        // Chỉ định cột "mã" và thứ tự sắp xếp là tăng dần
        RowSorter.SortKey sortKey = new RowSorter.SortKey(0, SortOrder.ASCENDING);

        // Đặt danh sách SortKey chỉ chứa một phần tử vào TableRowSorter
        sorter.setSortKeys(List.of(sortKey));

        // Đặt TableRowSorter cho JTable
        tblDanhSachVou.setRowSorter(sorter);

        // Thực hiện sắp xếp
        sorter.sort();
    }
    
    void DESC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachVou.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblDanhSachVou.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
    void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachVou.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblDanhSachVou.setRowSorter(trs);

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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaVou = new javax.swing.JTextField();
        txtTenVou = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtNgayHetHan = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        txtGiaTriVou = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnASC = new javax.swing.JButton();
        btnDESC = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachVou = new javax.swing.JTable();
        btnFisrt = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("VOUCHER");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin khuyến mãi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel4.setText("Mã voucher:");

        jLabel5.setText("Tên voucher:");

        jLabel6.setText("Giá trị voucher:");

        jLabel7.setText("Ngày hết hạn:");

        jLabel8.setText("Số lượng:");

        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        txtNgayHetHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayHetHanActionPerformed(evt);
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

        txtGiaTriVou.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriVouActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(btnCapNhat)
                        .addGap(218, 218, 218))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtGiaTriVou))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(33, 33, 33)
                                    .addComponent(txtMaVou, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(33, 33, 33)
                                    .addComponent(txtTenVou, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(154, 154, 154)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(91, 91, 91)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgayHetHan, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                            .addComponent(txtSoLuong)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(btnLamMoi)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(184, 184, 184))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaVou, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtNgayHetHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenVou, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtGiaTriVou, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách khuyến mãi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

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

        tblDanhSachVou.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã voucher", "Tên voucher", "Giá trị voucher", "Ngày hết hạn", "Số lượng voucher"
            }
        ));
        tblDanhSachVou.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachVouMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachVou);

        btnFisrt.setBackground(new java.awt.Color(204, 204, 255));
        btnFisrt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnFisrt.setText("|<");
        btnFisrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFisrtActionPerformed(evt);
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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(btnASC, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(btnDESC, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1096, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnFisrt)
                        .addGap(63, 63, 63)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(btnLast)
                        .addGap(290, 290, 290)))
                .addGap(39, 39, 39))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnASC)
                    .addComponent(btnDESC)
                    .addComponent(jLabel2)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(btnPrev)
                    .addComponent(btnFisrt))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(517, 517, 517)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void txtGiaTriVouActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriVouActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriVouActionPerformed

    private void txtNgayHetHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayHetHanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayHetHanActionPerformed

    private void tblDanhSachVouMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachVouMouseClicked
        int selectedRow = tblDanhSachVou.getSelectedRow();

    if (selectedRow != -1) { // Check if a row is selected
        DefaultTableModel model = (DefaultTableModel) tblDanhSachVou.getModel();

        // Lấy dữ liệu từ bảng và gán vào các biến
//        String MaVoucher = model.getValueAt(selectedRow, 0).toString();
//        String TenVoucher = model.getValueAt(selectedRow, 1).toString();
//        BigDecimal GiaTriVoucher = (BigDecimal) model.getValueAt(selectedRow, 2);
//        Date NgayHetHan = (Date) model.getValueAt(selectedRow, 3);
//        int SoLuong = (int) model.getValueAt(selectedRow, 4);
//
//        // Gán dữ liệu vào các textfield
//        txtMaVou.setText(MaVoucher);
//        txtTenVou.setText(TenVoucher);
//        txtGiaTriVou.setText(GiaTriVoucher.toString());
//        txtNgayHetHan.setText(NgayHetHan.toString());
//        txtSoLuong.setText(String.valueOf(SoLuong));
        String MaVoucher = model.getValueAt(selectedRow, 0).toString();
        String TenVoucher = model.getValueAt(selectedRow, 1).toString();
        BigDecimal GiaTriVoucher = (BigDecimal) model.getValueAt(selectedRow, 2);
        Date NgayHetHan = (Date) model.getValueAt(selectedRow, 3); // Kiểm tra lại định dạng ngày tháng trả về từ bảng
        int SoLuong = Integer.parseInt(model.getValueAt(selectedRow, 4).toString()); // Chuyển đổi sang kiểu Integer

        // Gán dữ liệu vào các textfield
        txtMaVou.setText(MaVoucher);
        txtTenVou.setText(TenVoucher);
        txtGiaTriVou.setText(GiaTriVoucher.toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strNgayHetHan = dateFormat.format(NgayHetHan);
        txtNgayHetHan.setText(strNgayHetHan);

        txtSoLuong.setText(String.valueOf(SoLuong));
    }
    }//GEN-LAST:event_tblDanhSachVouMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        this.clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        this.delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (txtMaVou.getText().equals("")) {
            XDialog.alert(main, "Vui lòng chọn Voucher cần sửa");
        } else {
            this.update();
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

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

    private void btnFisrtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFisrtActionPerformed
        this.first();
    }//GEN-LAST:event_btnFisrtActionPerformed

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
    private javax.swing.JButton btnFisrt;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
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
    private javax.swing.JTable tblDanhSachVou;
    private javax.swing.JTextField txtGiaTriVou;
    private javax.swing.JTextField txtMaVou;
    private javax.swing.JTextField txtNgayHetHan;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenVou;
    // End of variables declaration//GEN-END:variables

    private Voucher getForm() {
        String maVoucher = txtMaVou.getText();
        String tenVoucher = txtTenVou.getText();
        BigDecimal giaTriVoucher = new BigDecimal(txtGiaTriVou.getText());
        Date ngayHetHan = null;
        int soLuong = Integer.parseInt(txtSoLuong.getText());

//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            ngayHetHan = (Date) dateFormat.parse(txtNgayHetHan.getText());
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//        }

        return new Voucher(maVoucher, tenVoucher, giaTriVoucher, ngayHetHan, soLuong);
    }

    private void setForm(Voucher voucher) {
        txtMaVou.setText(voucher.getMaVoucher());
        txtTenVou.setText(voucher.getTenVoucher());
        txtGiaTriVou.setText(voucher.getGiaTriVoucher().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtNgayHetHan.setText(dateFormat.format(voucher.getNgayHetHan()));
        txtSoLuong.setText(String.valueOf(voucher.getSoLuong()));
    }

    private void selectAndScrollToVisible() {
        tblDanhSachVou.setRowSelectionInterval(this.row, this.row);
        tblDanhSachVou.scrollRectToVisible(tblDanhSachVou.getCellRect(this.row, 0, true));
        this.edit();
    }
    
}
