/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.poly.model;


import com.poly.dao.LoaiSanPhamDAO;
import com.poly.dao.NhaCungCapDAO;
import com.poly.dao.NhanVienDAO;
import com.poly.dao.PhieuNhapHangDAO;

import com.poly.entity.ChiTietHoaDon;
import com.poly.entity.ChiTietPhieuNhap;
import com.poly.entity.HoaDon;
import com.poly.entity.KhachHang;
import com.poly.entity.NhanVien;
import com.poly.entity.PhieuNhapHang;

import com.poly.entity.PhieuNhapHangg;
import com.poly.entity.SanPham;
import com.poly.entity.Voucher;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Nhu Y
 */
public class NhapHangJPanel extends javax.swing.JPanel {

    PhieuNhapHangDAO nhapHangDao = new PhieuNhapHangDAO();
    NhaCungCapDAO cungCapDao = new NhaCungCapDAO();
    
    NhanVienDAO nhanVienDAO = new NhanVienDAO();
    LoaiSanPhamDAO LsanPhamDao = new LoaiSanPhamDAO();
    

    Map<String, String> nhapHangMap = new HashMap<>();
    Map<String, String> LsanPhamMap = new HashMap<>();
    Map<String, BigDecimal> cungCapMap = new HashMap<>();

    Map<String, String> nhanVienMap = new HashMap<>();

   
    private boolean isHTTTClicked = false;

    // Get the current date and time
    Date currentDate = new Date();

// Format the date as "dd/MM/yyyy HH:mm:ss"
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String ngayNhapString = dateFormat.format(currentDate);

    int row = -1;

    public NhapHangJPanel() {
        initComponents();
        init();
    }

    public void init() {
        this.fillTable();
    }

    public BigDecimal tinhTongTien(String maPhieuNhapHang) {
        BigDecimal tongTien = BigDecimal.ZERO;

       PhieuNhapHang nhaphang = nhapHangDao.selectById(maPhieuNhapHang);

        // Get the associated ChiTietHoaDon for the found HoaDon
        List<ChiTietPhieuNhap> chiTietList = getChiTietPhieuNhapHangByMaPhieuNhapHang(maPhieuNhapHang);

//        System.out.println("List" + chiTietList);;
        for (ChiTietPhieuNhap chiTiet : chiTietList) {
            int soLuong = chiTiet.getSoLuong();
            BigDecimal donGia = chiTiet.getDonGia();

            BigDecimal tienMucChiTiet = BigDecimal.valueOf(soLuong).multiply(donGia);

            tongTien = tongTien.add(tienMucChiTiet);
        }

        // If a valid voucher is found, apply the discount
        
        BigDecimal discountAmount = tongTien.multiply(discountPercentage.divide(BigDecimal.valueOf(100)));

        System.out.println("Discount: " + discountPercentage);
        tongTien = tongTien.subtract(discountAmount);
        return tongTien;
    }

    private void setForm(PhieuNhapHangg obj) {
        txtMaCungCap.setText(obj.getMaNhaCungCap());

        // Format the date (NgayXuat) as "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngayNhapString = dateFormat.format(obj.getNgayNhap());
        txtNgayNhap.setText(ngayNhapString);

        // Calculate the total amount based on the selected row
        BigDecimal tongTien = tinhTongTien(obj.getMaPhieuNhapHang());

        // Set the total amount in the text field as a string
        txtTongTien.setText(tongTien.toString());

        // Assuming khachHangMap is your Map<String, String>
        BigDecimal tenNhaCungCap = cungCapMap.get(obj.getMaNhaCungCap());

        // Set the customer name to txtTenKhachHang
        txtTenNhaCungCap.setText(tenNhaCungCap);

        // Set the selected payment method in the combo box
        String hinhThucThanhToan = obj.getHinhThucThanhToan();
        cbHDHinhThucThanhToan.setSelectedItem(hinhThucThanhToan);

        // Set the selected voucher in the combo box
        String tenVoucher = LsanPhamMap.get(obj.getMaVoucher());
        cbVatLieu.setSelectedItem(tenVoucher);

        // Set the selected customer ID in the combo box
        String maKhachHang = obj.getMaKhachHang();
        cbNhaCungCap.setSelectedItem(maKhachHang);

    }

    public void fillTable() {

        DefaultTableModel model = (DefaultTableModel) tblDanhSachHD.getModel();

        Date ngayXuat = Calendar.getInstance().getTime();

        // Format the ngayXuat date as "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedNgayXuat = dateFormat.format(ngayXuat);

        txtNgayNhap.setText(formattedNgayXuat);

        getVoucher();
        getKhachHang();
        getSanPham();
        getNhanVien();

        model.setRowCount(0); // Clears all rows in the table

        try {
            List<HoaDon> list = hoaDonDao.selectAll();

            for (HoaDon hoaDon : list) {

                String maKhachHang = hoaDon.getMaKhachHang();
                String maNhanVien = hoaDon.getMaNhanVien();

                String tenKhachHang = khachHangMap.get(maKhachHang);
                String tenNhanVien = nhanVienMap.get(maNhanVien);

                Object[] row = {
                    hoaDon.getMaHoaDon(),
                    tenKhachHang, // Sửa thành tên khách hàng
                    tenNhanVien,
                    hoaDon.getNgayXuat(),
                    tinhTongTien(hoaDon.getMaHoaDon()),
                    hoaDon.getHinhThucThanhToan(),
                    hoaDon.getMaVoucher()
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DefaultTableModel chiTietModel = (DefaultTableModel) tblChiTietHoaDon.getModel();
        chiTietModel.setRowCount(0); // Clears all rows in tblChiTietHoaDon
    }

    public void getVoucher() {
        try {

            List<Voucher> vouchers = voucherDao.selectAll();

            // Clear existing items in the JComboBox
            cbVatLieu.removeAllItems();

            // Add vouchers to the JComboBox
            for (Voucher voucher : vouchers) {

                cbVatLieu.addItem(voucher.getTenVoucher());

                cbGiamGia.addItem(voucher.getTenVoucher());

                voucherMap.put(voucher.getMaVoucher(), voucher.getTenVoucher());
                voucherTenVoucherMap.put(voucher.getTenVoucher(), voucher.getMaVoucher());
                voucherGiaTriMap.put(voucher.getTenVoucher(), voucher.getGiaTriVoucher());

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getKhachHang() {
        try {
            // Assuming there's a KhachHangDao class with a selectAll() method
            List<KhachHang> khachHangs = khachHangDAO.selectAll();

            // Clear existing items in the JComboBox
            cbNhaCungCap.removeAllItems();

            // Add KhachHang items to the JComboBox
            for (KhachHang khachHang : khachHangs) {
                cbNhaCungCap.addItem(khachHang.getMaKhachHang());

                // Assuming you want to store some information in a map
                khachHangMap.put(khachHang.getMaKhachHang(), khachHang.getTenKhachHang());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getSanPham() {
        try {
            // Assuming there's a SanPhamDao class with a selectAll() method
            List<SanPham> sanPhams = sanPhamDAO.selectAll();

            // Clear existing items in the JComboBox
            cbMaSP.removeAllItems();

            // Add SanPham items to the JComboBox
            for (SanPham sanPham : sanPhams) {
                cbMaSP.addItem(sanPham.getMaSanPham());

                // Assuming you want to store some information in a map
                sanPhamMap.put(sanPham.getMaSanPham(), sanPham.getTenSanPham());
                sanPhamDonGiaMap.put(sanPham.getMaSanPham(), new BigDecimal(sanPham.getGiaXuat()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getNhanVien() {
        try {
            List<NhanVien> nhanViens = nhanVienDAO.selectAll();

            // Add employees to the JComboBox
            for (NhanVien nhanVien : nhanViens) {

                nhanVienMap.put(nhanVien.getMaNhanVien(), nhanVien.getTenNhanVien());
                // Add other mappings if needed
                // Example:
                // someOtherItemMap.put(nhanVien.getMaNhanVien(), nhanVien.getSomeOtherItem());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void edit() {
        if (this.row >= 0) {
            String id = (String) tblDanhSachHD.getValueAt(this.row, 0);
            HoaDon hd = hoaDonDao.selectById(id);
            this.setForm(hd);
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
        int rowCount = tblDanhSachHD.getRowCount();
        if (rowCount > 0 && this.row < rowCount - 1) {
            this.row++;
            selectAndScrollToVisible();
        }
    }

    private void last() {
        int rowCount = tblDanhSachHD.getRowCount();
        if (rowCount > 0) {
            this.row = rowCount - 1;
            selectAndScrollToVisible();
        }
    }

    private void selectAndScrollToVisible() {
        tblDanhSachHD.setRowSelectionInterval(this.row, this.row);
        tblDanhSachHD.scrollRectToVisible(tblDanhSachHD.getCellRect(this.row, 0, true));
        this.edit();
    }

    void ASC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachHD.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblDanhSachHD.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    void DESC() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachHD.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblDanhSachHD.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachHD.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblDanhSachHD.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }

    // Function to update txtThanhTien based on txtDonGia and txtSoLuong
    private void updateTxtThanhTien() {
        try {
            // Check if txtDonGia and txtSoLuong are not empty
            String donGiaText = txtDonGia.getText().trim();
            String soLuongText = txtSoLuong.getText().trim();

            if (donGiaText.isEmpty() || soLuongText.isEmpty()) {
                // Handle the case where either txtDonGia or txtSoLuong is empty
                txtThanhTien.setText("Please enter values");
                return;
            }

            BigDecimal donGia = new BigDecimal(donGiaText);

            // Try to parse txtSoLuong
            int soLuong = Integer.parseInt(soLuongText);

            // Calculate the total value
            BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));

            // Update txtThanhTien with the calculated value
            txtThanhTien.setText(thanhTien.toString());
        } catch (NumberFormatException ex) {
            // Handle the case where the input in txtDonGia or txtSoLuong is not a valid number
            txtThanhTien.setText("Invalid Input");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        txtTenKH2 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtDiaChi2 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtMaKH2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtEmail3 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtEmail4 = new javax.swing.JTextField();
        txtTenKH3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtMaCungCap = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtNgayNhap = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cbVatLieu = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cbNhaCungCap = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtTenNhaCungCap = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        cbHDHinhThucThanhToan = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtMaPhieuNhap = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSoPhieuNhap = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachHD = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnTangDan = new javax.swing.JButton();
        btnGiamDan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtThanhToan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtConLai = new javax.swing.JTextField();
        btnLuuLai = new javax.swing.JButton();
        btnInPhieuNhap = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cbHinhThucThanhToan = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        txtDiaDiem = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtThem = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNguoiGiao = new javax.swing.JTextField();

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chi tiết hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel24.setText("Mã Sản Phẩm:");

        jLabel25.setText("Mã Chi Tiết Hoá Đơn:");

        txtDiaChi2.setText("SP001");

        jLabel26.setText("Mã Hóa Đơn:");

        txtMaKH2.setText("CTHD001");

        jLabel27.setText("Đơn Vị Tính:");

        jLabel28.setText("Số Lượng:");

        jLabel29.setText("Đơn Giá:");

        jLabel30.setText("Tổng Tiền:");

        txtTenKH3.setText("HD001");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kg", "Cái" }));

        btnThem.setBackground(new java.awt.Color(204, 204, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setText("Thêm");

        btnCapNhat.setBackground(new java.awt.Color(204, 204, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setText("Cập Nhật");

        btnXoa.setBackground(new java.awt.Color(204, 204, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setText("Xóa");

        btnLamMoi.setBackground(new java.awt.Color(204, 204, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setText("Làm mới");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel24)
                            .addComponent(jLabel30))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDiaChi2)
                            .addComponent(txtTenKH2)
                            .addComponent(txtMaKH2, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(txtTenKH3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel27)
                            .addComponent(jLabel29))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail3)
                            .addComponent(txtEmail4)
                            .addComponent(jComboBox1, 0, 295, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(txtEmail3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(txtTenKH3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiaChi2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel29)
                            .addComponent(txtEmail4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtTenKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38))))
        );

        setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("NHẬP HÀNG");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin phiếu nhập"));

        jLabel16.setText("Mã Nhân Viên:");

        jLabel17.setText("Mã Nhà Cung Cấp:");

        txtMaNhanVien.setEditable(false);
        txtMaNhanVien.setText("NV003");

        jLabel18.setText("Nhà Cung Cấp:");

        txtMaCungCap.setEditable(false);
        txtMaCungCap.setText("HD001");

        jLabel20.setText("Ngày Nhập:");

        txtNgayNhap.setEditable(false);
        txtNgayNhap.setText("3/11/2023");

        jLabel22.setText("Hình thức thanh toán:");

        jLabel23.setText("Vật liệu:");

        cbNhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbNhaCungCapMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbNhaCungCapMouseEntered(evt);
            }
        });

        jLabel4.setText("Tên Nhà Cung Cấp:");

        txtTenNhaCungCap.setEditable(false);

        jLabel3.setText("Tổng Tiền:");

        txtTongTien.setEditable(false);

        cbHDHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thanh toán tiền mặt", "Thanh toán thẻ", " " }));
        cbHDHinhThucThanhToan.setEnabled(false);
        cbHDHinhThucThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbHDHinhThucThanhToanActionPerformed(evt);
            }
        });

        jLabel14.setText("Mã phiếu nhập hàng:");

        jLabel15.setText("Số phiếu nhập:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel17))
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel23))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(cbVatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel15))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtTenNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbHDHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSoPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtMaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cbNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtMaPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(cbHDHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtSoPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbVatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel3)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách phiếu nhập"));

        tblDanhSachHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Nhà Cung Cấp", "Nhà Cung Cấp", "Mã Phiếu Nhập Hàng", "Mã Nhân Viên", "Vật liệu", "Hình Thức Thanh Toán", "Số Phiếu Nhập", "Tổng Tiền"
            }
        ));
        tblDanhSachHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachHD);

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

        btnTangDan.setBackground(new java.awt.Color(204, 204, 255));
        btnTangDan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTangDan.setText("Sắp xếp tăng dần ");
        btnTangDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTangDanActionPerformed(evt);
            }
        });

        btnGiamDan.setBackground(new java.awt.Color(204, 204, 255));
        btnGiamDan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGiamDan.setText("Sắp xếp giảm dần");
        btnGiamDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiamDanActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setText("Tìm kiếm");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnFirst)
                .addGap(69, 69, 69)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLast)
                .addGap(22, 22, 22))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnTangDan, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGiamDan, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGiamDan)
                    .addComponent(btnTangDan)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách chi tiết phiếu nhập"));

        tblChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Người giao", "Địa điểm", "Đơn Giá", "Số Lượng ", "Thành Tiền"
            }
        ));
        tblChiTietHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblChiTietHoaDon);

        jLabel10.setText("Tổng Tiền:");

        txtThanhToan.setEditable(false);

        jLabel12.setText("Còn Lại");

        txtConLai.setEditable(false);

        btnLuuLai.setBackground(new java.awt.Color(204, 204, 255));
        btnLuuLai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLuuLai.setText("Lưu lại");
        btnLuuLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuLaiActionPerformed(evt);
            }
        });

        btnInPhieuNhap.setBackground(new java.awt.Color(204, 204, 255));
        btnInPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInPhieuNhap.setText("In phiếu nhập");
        btnInPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInPhieuNhapActionPerformed(evt);
            }
        });

        jLabel13.setText("Hình Thức Thanh Toán:");

        cbHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thanh toán tiền mặt", "Thanh toán thẻ", " " }));
        cbHinhThucThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbHinhThucThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel10)
                .addGap(38, 38, 38)
                .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtConLai, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnLuuLai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10)
                    .addComponent(btnLuuLai, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtConLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbHinhThucThanhToan, txtConLai, txtThanhToan});

        jLabel5.setText("Người giao");

        txtThanhTien.setEditable(false);

        txtDiaDiem.setEditable(false);

        txtDonGia.setEditable(false);

        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });
        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyTyped(evt);
            }
        });

        txtThem.setText("Thêm");
        txtThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThemActionPerformed(evt);
            }
        });

        jLabel6.setText("Địa điểm");

        jLabel7.setText("Đơn Giá");

        jLabel8.setText("Số Lượng");

        jLabel9.setText("Thành Tiền");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtNguoiGiao, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDiaDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(226, 226, 226))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addComponent(txtThem, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNguoiGiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDiaDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtThem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtDiaDiem, txtDonGia, txtSoLuong, txtThanhTien});

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(472, 472, 472))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

// Get the ChiTietHoaDon associated with a specific MaHoaDon
    private List<ChiTietHoaDon> getChiTietHoaDonByMaHoaDon(String maHoaDon) {
        ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO(); // Create an instance of ChiTietHoaDonDAO
        List<ChiTietHoaDon> chiTietList = chiTietHoaDonDAO.selectAllById(maHoaDon);

        List<ChiTietHoaDon> filteredChiTietList = new ArrayList<>();

        for (ChiTietHoaDon chiTiet : chiTietList) {
            if (chiTiet.getMaHoaDon().equals(maHoaDon)) {
                filteredChiTietList.add(chiTiet);
            }
        }

        return filteredChiTietList;
    }

    private void btnInPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInPhieuNhapActionPerformed
        // Create an instance of the InHoaDon frame
        InHoaDon inHoaDonFrame = new InHoaDon();

        // Set the invoice data
        String invoiceData = txtMaCungCap.getText();  // Replace this with your actual invoice data
//        inHoaDonFrame.setInvoiceData(invoiceData);
        inHoaDonFrame.init(invoiceData);
        
        // Show the InHoaDon frame
        inHoaDonFrame.setVisible(true);

       }//GEN-LAST:event_btnInPhieuNhapActionPerformed

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

    private void btnTangDanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTangDanActionPerformed
        ASC();
    }//GEN-LAST:event_btnTangDanActionPerformed

    private void btnGiamDanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiamDanActionPerformed
        DESC();
    }//GEN-LAST:event_btnGiamDanActionPerformed

    private void tblDanhSachHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachHDMouseClicked
        int selectedRow = tblDanhSachHD.getSelectedRow();

        if (selectedRow != -1) { // Check if a row is selected
            DefaultTableModel model = (DefaultTableModel) tblDanhSachHD.getModel();
            String maHoaDon = model.getValueAt(selectedRow, 0).toString();
            String maKhachHang = model.getValueAt(selectedRow, 1).toString(); // Assuming the first column contains the MaKhachHang
            String maNhanVien = model.getValueAt(selectedRow, 2).toString(); // Assuming the second column contains the TenKhachHang
            String hinhThucThanhToan = model.getValueAt(selectedRow, 5).toString(); // Assuming the second column contains the TenKhachHang
            String MaVoucher = model.getValueAt(selectedRow, 6).toString();

            // Populate text fields with the retrieved data
            txtMaCungCap.setText(maHoaDon);
//            txtMaKhachHang.setText(maKhachHang);
            txtMaNhanVien.setText(maNhanVien);

            // Calculate the total amount based on the selected row
            BigDecimal tongtien = tinhTongTien(maHoaDon);

            // Set the total amount in the text field as a string
//            txtTongTien.setText(tongtien.toString());
            // Retrieve the corresponding maVoucher from the map
            String tenVoucher = voucherMap.get(MaVoucher);

            cbVatLieu.setSelectedItem(tenVoucher);
            cbHinhThucThanhToan.setSelectedItem(hinhThucThanhToan);

            BangChiTietHoaDon bang = new BangChiTietHoaDon(maHoaDon);

            bang.setVisible(true);

        }
    }//GEN-LAST:event_tblDanhSachHDMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        String str = txtSearch.getText();
        search(str);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void comboBoxSuggestion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSuggestion1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxSuggestion1ActionPerformed

    private void tblChiTietHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietHoaDonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblChiTietHoaDonMouseClicked

    private void cbNhaCungCapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbNhaCungCapMouseClicked

    }//GEN-LAST:event_cbNhaCungCapMouseClicked

    private void cbNhaCungCapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbNhaCungCapMouseEntered
        Object selectedObject = cbNhaCungCap.getSelectedItem();

        if (selectedObject != null) {
            String selectedMaKhachHang = selectedObject.toString();
            String selectedKhachHangName = khachHangMap.get(selectedMaKhachHang);
            updateTxtThanhTien();
            // Assuming textField is a JTextField
            txtTenNhaCungCap.setText(selectedKhachHangName);
        }
    }//GEN-LAST:event_cbNhaCungCapMouseEntered

    private void txtThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThemActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();

// Lấy dữ liệu từ các ô nhập liệu
        String maSP = cbMaSP.getSelectedItem().toString();
        String tenSP = txtDiaDiem.getText();
        BigDecimal donGia = new BigDecimal(txtDonGia.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        BigDecimal thanhTien = new BigDecimal(txtThanhTien.getText());

        boolean isProductExist = false;
// Iterate through the rows of the table model
        for (int i = 0; i < model.getRowCount(); i++) {
            String currentMaSP = model.getValueAt(i, 0).toString(); // Assuming maSP is in the first column

            // Check if the product already exists in the table
            if (maSP.equals(currentMaSP)) {
                // If the product exists, update soLuong and thanhTien
                int currentSoLuong = (int) model.getValueAt(i, 3); // Assuming soLuong is in the fourth column
                BigDecimal currentThanhTien = (BigDecimal) model.getValueAt(i, 4); // Assuming thanhTien is in the fifth column

                int newSoLuong = currentSoLuong + soLuong;
                BigDecimal newThanhTien = currentThanhTien.add(thanhTien);

                // Update the values in the model
                model.setValueAt(newSoLuong, i, 3); // Update soLuong in the fourth column
                model.setValueAt(newThanhTien, i, 4); // Update thanhTien in the fifth column

                isProductExist = true;
                break; // Exit the loop since the product has been found and updated
            }
        }

// If the product does not exist in the table, add a new row
        if (!isProductExist) {
            Object[] rowData = {maSP, tenSP, donGia, soLuong, thanhTien};
            model.addRow(rowData);
        }

// Calculate the total of the "thanhTien" column
        BigDecimal totalThanhTien = BigDecimal.ZERO;
        for (int i = 0; i < model.getRowCount(); i++) {
            BigDecimal thanhTienForRow = (BigDecimal) model.getValueAt(i, 4); // Assuming thanhTien is in the fifth column
            totalThanhTien = totalThanhTien.add(thanhTienForRow);
        }

// Set the total thanhTien to txtThanhToan
        txtThanhToan.setText(totalThanhTien.toString());


    }//GEN-LAST:event_txtThemActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void txtSoLuongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyPressed
        try {
            // Check if txtDonGia and txtSoLuong are not empty
            String donGiaText = txtDonGia.getText().trim();
            String soLuongText = txtSoLuong.getText().trim();

            if (donGiaText.isEmpty() || soLuongText.isEmpty()) {
                // Handle the case where either txtDonGia or txtSoLuong is empty
                txtThanhTien.setText("Please enter values");
                return;
            }

            BigDecimal donGia = new BigDecimal(donGiaText);

            // Try to parse txtSoLuong
            int soLuong = Integer.parseInt(soLuongText);

            // Calculate the total value
            BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));

            // Update txtThanhTien with the calculated value
            txtThanhTien.setText(thanhTien.toString());
        } catch (NumberFormatException ex) {
            // Handle the case where the input in txtDonGia or txtSoLuong is not a valid number
            txtThanhTien.setText("Invalid Input");
        }    }//GEN-LAST:event_txtSoLuongKeyPressed

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
        try {
            // Check if txtDonGia and txtSoLuong are not empty
            String donGiaText = txtDonGia.getText().trim();
            String soLuongText = txtSoLuong.getText().trim();

            if (donGiaText.isEmpty() || soLuongText.isEmpty()) {
                // Handle the case where either txtDonGia or txtSoLuong is empty
                txtThanhTien.setText("Please enter values");
                return;
            }

            BigDecimal donGia = new BigDecimal(donGiaText);

            // Try to parse txtSoLuong
            int soLuong = Integer.parseInt(soLuongText);

            // Calculate the total value
            BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));

            // Update txtThanhTien with the calculated value
            txtThanhTien.setText(thanhTien.toString());
        } catch (NumberFormatException ex) {
            // Handle the case where the input in txtDonGia or txtSoLuong is not a valid number
            txtThanhTien.setText("Invalid Input");
        }
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void txtSoLuongKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyTyped
        try {
            // Check if txtDonGia and txtSoLuong are not empty
            String donGiaText = txtDonGia.getText().trim();
            String soLuongText = txtSoLuong.getText().trim();

            if (donGiaText.isEmpty() || soLuongText.isEmpty()) {
                // Handle the case where either txtDonGia or txtSoLuong is empty
                txtThanhTien.setText("Please enter values");
                return;
            }

            BigDecimal donGia = new BigDecimal(donGiaText);

            // Try to parse txtSoLuong
            int soLuong = Integer.parseInt(soLuongText);

            // Calculate the total value
            BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));

            // Update txtThanhTien with the calculated value
            txtThanhTien.setText(thanhTien.toString());
        } catch (NumberFormatException ex) {
            // Handle the case where the input in txtDonGia or txtSoLuong is not a valid number
            txtThanhTien.setText("Invalid Input");
        }
    }//GEN-LAST:event_txtSoLuongKeyTyped

    private void btnLuuLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuLaiActionPerformed
        try {

            // Check if tblChiTietHoaDon has data
            if (tblChiTietHoaDon.getRowCount() == 0) {
                // Show a JOptionPane to notify the user
                JOptionPane.showMessageDialog(this, "không có sản phẩm", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return; // Exit the method
            }

            // Check if the payment confirmation is valid
//            if (!isPaymentConfirmationValid()) {
//                return; // Exit the method if the payment confirmation is not valid
//            }
            // Generate a new invoice ID
            String newMaHoaDon = generateNewMaHoaDon();
            System.out.println("New MaHoaDon: " + newMaHoaDon);

            // Retrieve information from components
            String maKhachHang = cbNhaCungCap.getSelectedItem().toString();
            String maNhanVien = txtMaNhanVien.getText();

            System.out.println("MaKH: " + maKhachHang);
            System.out.println("MANV: " + maNhanVien);

            String hinhThucThanhToan = cbHinhThucThanhToan.getSelectedItem().toString();

            // Assuming cbGiamGia is a JComboBox
            String selectedVoucher = cbGiamGia.getSelectedItem().toString();

            // Assuming cbMaSP is a JComboBox<String>
            String selectedMaSanPham = (String) cbMaSP.getSelectedItem();
            System.out.println("Selected MaSanPham: " + selectedMaSanPham);
            
            
            // Check if the voucherTenVoucherMap contains the selectedVoucher
            if (voucherTenVoucherMap.containsKey(selectedVoucher)) {
                // Retrieve the String value associated with selectedVoucher
                String voucherDiscountString = voucherTenVoucherMap.get(selectedVoucher);

                // Parse the String value to a BigDecimal
                BigDecimal voucherDiscount = new BigDecimal(voucherDiscountString);
                System.out.println("Voucher Discount: " + voucherDiscount);

                // Retrieve ngayXuatString from your component (replace with the actual component)
                String ngayXuatString = txtNgayNhap.getText(); // Replace with the actual component

                // Parse ngayXuatString into a Date object using the parseNgayXuat method
                Date ngayXuat = parseNgayXuat(ngayXuatString);
                System.out.println("NgayXuat: " + ngayXuat);
                
                // Now, create a new HoaDon object with the extracted information
                HoaDon hoaDon = new HoaDon(newMaHoaDon, maKhachHang, maNhanVien, ngayXuat, hinhThucThanhToan, voucherDiscountString);

//                Console here for me
// Print the contents of the HoaDon object
//                System.out.println("HoaDon: " + hoaDon);
                // Assuming hoaDonDAO is an instance of your HoaDonDAO class
                hoaDonDao.insert(hoaDon);
                // Further processing or actions...

                // Assuming chiTietHoaDonDAO is an instance of your ChiTietHoaDonDAO class
                List<ChiTietHoaDon> chiTietHoaDons = createChiTietHoaDons(newMaHoaDon);

// Iterate through the list and insert each ChiTietHoaDon separately
                for (ChiTietHoaDon chiTietHoaDon : chiTietHoaDons) {
                    chiTietHoaDonDao.insert(chiTietHoaDon);
                }

                this.fillTable();

                // After payment confirmation, reset the flags to false
                isXacNhanGiamGiaClicked = false;
                isHTTTClicked = false;
            } else {
                System.out.println("Voucher không được tìm thấy: " + selectedVoucher);
            }

        } catch (Exception e) {
            // Handle exceptions, show an error message, etc.
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnLuuLaiActionPerformed

    private void cbHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbHinhThucThanhToanActionPerformed
        // Set the flag to true when btnHTTT is clicked
        isHTTTClicked = true;
    }//GEN-LAST:event_cbHinhThucThanhToanActionPerformed

    private void cbHDHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbHDHinhThucThanhToanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbHDHinhThucThanhToanActionPerformed

    private boolean isPaymentConfirmationValid() {
        // Check if btnXacNhanGiamGia is clicked
        if (!isXacNhanGiamGiaClicked) {
            // Show a notification that XacNhanGiamGia is not confirmed
            JOptionPane.showMessageDialog(this, "hãy xác nhận mã voucher", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        // Check if btnHTTT is clicked
        if (!isHTTTClicked) {
            // Show a notification that HTTT is not selected
            JOptionPane.showMessageDialog(this, "hãy xác nhận phương thức thanh toán", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        return true; // Both actions are valid
    }

    private List<ChiTietHoaDon> createChiTietHoaDons(String maHoaDon) {
        List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();

        // Assuming tblChiTietHoaDon is a JTable with a DefaultTableModel
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();

        // Replace with the actual column indices for each property
        int columnIndexMaSP = 0;
        int columnIndexDonGia = 2;
        int columnIndexSoLuong = 3;
        int columnIndexTongTien = 4;

        // Loop through the rows
        for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
            chiTietHoaDon.setMaHoaDon(maHoaDon);
            chiTietHoaDon.setMaSanPham((String) model.getValueAt(rowIndex, columnIndexMaSP));

            System.out.println("////////////////////");

//            System.out.println("MaHD: " + chiTietHoaDon.getMaHoaDon());
            // Replace "YourSoLuongValue" with the actual value from the table
            Object soLuongValue = model.getValueAt(rowIndex, columnIndexSoLuong);

//            System.out.println("SoLuong: " + soLuongValue);
            if (soLuongValue != null && soLuongValue instanceof Number) {
                chiTietHoaDon.setSoLuong(((Number) soLuongValue).intValue());
            } else {
                System.out.println("Invalid or null value for SoLuong in row " + rowIndex);
            }

            // Replace "YourDonGiaValue" with the actual value from the table
            Object donGiaValue = model.getValueAt(rowIndex, columnIndexDonGia);
            if (donGiaValue != null && donGiaValue instanceof Number) {
                chiTietHoaDon.setDonGia(new BigDecimal(donGiaValue.toString()));
            } else {
                System.out.println("Invalid or null value for DonGia in row " + rowIndex);
            }

            // Replace "YourTongTienValue" with the actual value from the table
            Object tongTienValue = model.getValueAt(rowIndex, columnIndexTongTien);
            if (tongTienValue != null && tongTienValue instanceof Number) {
                chiTietHoaDon.setTongTien(new BigDecimal(tongTienValue.toString()));
            } else {
                System.out.println("Invalid or null value for TongTien in row " + rowIndex);
            }

//            System.out.println("SoLuong" + donGiaValue);
//            System.out.println("tongTienValue" + tongTienValue);
            // Add the chiTietHoaDon to the list
            chiTietHoaDons.add(chiTietHoaDon);
        }
        System.out.println("Chi Tiết Hóa Đơn" + chiTietHoaDons);

        return chiTietHoaDons;
    }

    private String generateNewMaHoaDon() {
        String largestMaHoaDon = getLargestMaHoaDon();

        if (largestMaHoaDon != null) {
            // Extract the numeric part of the largestMaHoaDon
            String numericPart = largestMaHoaDon.substring(2); // Assuming "HDxxx" format

            // Increment the numeric part
            int nextNumber = Integer.parseInt(numericPart) + 1;

            // Format the new invoice ID
            String newMaHoaDon = String.format("HD%03d", nextNumber); // Assuming you want "HDxxx" format

            return newMaHoaDon;
        } else {
            // Handle the case where there are no existing invoice IDs
            return "HD001"; // or another default value
        }
    }

    private String getLargestMaHoaDon() {
        // Assuming you have a method to retrieve the largest invoice ID from your data source (e.g., database)
        // Replace this with your actual implementation using your database access mechanism

        // Sample implementation using a hypothetical database query
        // This is just for illustration; replace it with your actual database access code
        // For example, if you're using Spring Data JPA, you might use a repository method
        // Replace "YourEntity" and "yourRepository" with your actual entity class and repository interface
        // The method name (findTopByOrderByMaHoaDonDesc) should match your requirements
        String largestMaHoaDonEntity = hoaDonDao.findTopMaHoaDonByOrderByMaHoaDonDesc();

        if (largestMaHoaDonEntity != null) {
            return largestMaHoaDonEntity;
        } else {
            // Handle the case where there are no existing invoice IDs
            return null; // or another default value
        }
    }

    private Date parseNgayXuat(String ngayXuatString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.parse(ngayXuatString);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnGiamDan;
    private javax.swing.JButton btnInPhieuNhap;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLuuLai;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnTangDan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbHDHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbNhaCungCap;
    private javax.swing.JComboBox<String> cbVatLieu;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblChiTietHoaDon;
    private javax.swing.JTable tblDanhSachHD;
    private javax.swing.JTextField txtConLai;
    private javax.swing.JTextField txtDiaChi2;
    private javax.swing.JTextField txtDiaDiem;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtEmail3;
    private javax.swing.JTextField txtEmail4;
    private javax.swing.JTextField txtMaCungCap;
    private javax.swing.JTextField txtMaKH2;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMaPhieuNhap;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtNguoiGiao;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoPhieuNhap;
    private javax.swing.JTextField txtTenKH2;
    private javax.swing.JTextField txtTenKH3;
    private javax.swing.JTextField txtTenNhaCungCap;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtThanhToan;
    private javax.swing.JButton txtThem;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables

    private List<ChiTietPhieuNhap> getChiTietPhieuNhapHangByMaPhieuNhapHang(String maPhieuNhapHang) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class discountPercentage {

        private static BigDecimal divide(BigDecimal valueOf) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public discountPercentage() {
        }
    }

}
