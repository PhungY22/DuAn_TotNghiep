/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.model;

import com.poly.dao.ChiTietHoaDonDAO;
import com.poly.dao.HoaDonDAO;
import com.poly.dao.KhachHangDAO;
import com.poly.dao.NhanVienDAO;
import com.poly.dao.SanPhamDAO;
import com.poly.dao.VoucherDAO;
import com.poly.entity.ChiTietHoaDon;
import com.poly.entity.HoaDon;
import com.poly.entity.KhachHang;
import com.poly.entity.NhanVien;
import com.poly.entity.SanPham;
import com.poly.entity.Voucher;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import java.lang.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public class InHoaDon extends javax.swing.JFrame {

    HoaDonDAO hoaDonDao = new HoaDonDAO();
    ChiTietHoaDon chiTietHoaDonDao = new ChiTietHoaDon();

    VoucherDAO voucherDao = new VoucherDAO();
    KhachHangDAO khachHangDAO = new KhachHangDAO();
    SanPhamDAO sanPhamDAO = new SanPhamDAO();
    NhanVienDAO nhanVienDAO = new NhanVienDAO();
    ChiTietHoaDonDAO chiTietHDDao = new ChiTietHoaDonDAO();

    public InHoaDon() {
        this.setUndecorated(true);

        initComponents();
//        init();
    }

//    public InHoaDon(HoaDon hoadon) {
//      
//        
//        initComponents();
//     
//    }
    void init(String maHoaDon) {

        HoaDon hoadon = hoaDonDao.selectById(maHoaDon);

        NhanVien nhanvien = nhanVienDAO.selectById(hoadon.getMaNhanVien());

        KhachHang khachhang = khachHangDAO.selectById(hoadon.getMaKhachHang());

        List<ChiTietHoaDon> chiTietHD = chiTietHDDao.selectByMaHoaDon(maHoaDon);

        List<SanPham> chiTietSP = sanPhamDAO.selectAllByChiTietHoaDon(chiTietHD);

// Format date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedNgayXuat = dateFormat.format(hoadon.getNgayXuat());

        jLabelsophieunhap.setText("Mã hóa dơn: " + hoadon.getMaHoaDon());
        jLabelnhanvien.setText("Nhân Viên: " + nhanvien.getTenNhanVien());
        jLabelkhachang.setText("Khách Hàng: " + khachhang.getTenKhachHang());
        jLabelthoigian.setText("Thời Gian: " + formattedNgayXuat);
//        jLabelTenSP7.setText(jLabelTenSP7.getText() + String.valueOf(hoadon.getTongTien()));

        // Assuming you want panels to be added horizontally
        jPanel5.setLayout(new GridLayout(0, 1)); // 1 column, variable rows

// Call this method after creating the panels
        List<JPanel> panels = createPanels_SP(chiTietHD, chiTietSP, hoadon);

        for (JPanel panel : panels) {
            jPanel5.add(panel);
        }

        jPanel5.revalidate();
        jPanel5.repaint();

    }

//    
    private List<JPanel> createPanels_SP(List<ChiTietHoaDon> chiTietHDList, List<SanPham> listSP, HoaDon hoaDon) {
        List<JPanel> panels = new ArrayList<>();

        // Retrieve the voucher for the hoaDon
        Voucher voucher = voucherDao.selectById(hoaDon.getMaVoucher());

        for (int i = 0; i < Math.min(chiTietHDList.size(), listSP.size()); i++) {
            ChiTietHoaDon chiTietHD = chiTietHDList.get(i);
            SanPham sp = listSP.get(i);

            JPanel panel = new JPanel();

            JLabel[] list_lbl = new JLabel[5]; // Increase array size to accommodate the new label

            panel.setLayout(new GridLayout(0, 5)); // You can change the number of columns here

            list_lbl[0] = new JLabel(String.valueOf(sp.getTenSanPham()));
            list_lbl[1] = new JLabel(String.valueOf(chiTietHD.getSoLuong()));
            list_lbl[2] = new JLabel(String.valueOf(sp.getGiaXuat()) + " đồng");

            BigDecimal soLuongDecimal = new BigDecimal(chiTietHD.getSoLuong());

            // Calculate ThanhTien (SoLuong * GiaXuat)
// Convert soLuong to BigDecimal
// Calculate ThanhTien (SoLuong * GiaXuat)
            BigDecimal thanhTien = soLuongDecimal.multiply(new BigDecimal(sp.getGiaXuat()));

            list_lbl[3] = new JLabel(String.valueOf(voucher.getTenVoucher()));

            list_lbl[4] = new JLabel(String.valueOf(thanhTien) + " đồng");

            // Set the value of lblTongTien based on your calculation
// For example, if you want to set it to the total of all thanhTien values with voucher discount
            BigDecimal totalTongTien = calculateTotalTongTien(chiTietHDList, listSP, voucher);
            lblTongTien.setText("" + totalTongTien);

            for (JLabel label : list_lbl) {
                label.setFont(new Font("Arial", Font.PLAIN, 14));
                label.setBackground(new Color(255, 255, 255));

                // Add margin to labels
                label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                panel.add(label);
            }

            panels.add(panel);
        }
        return panels;
    }

    private BigDecimal calculateTotalTongTien(List<ChiTietHoaDon> chiTietHDList, List<SanPham> listSP, Voucher voucher) {
        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < chiTietHDList.size(); i++) {
            ChiTietHoaDon chiTietHD = chiTietHDList.get(i);
            SanPham sp = listSP.get(i);

            BigDecimal soLuongDecimal = new BigDecimal(chiTietHD.getSoLuong());
            BigDecimal thanhTien = soLuongDecimal.multiply(new BigDecimal(sp.getGiaXuat()));

            // Apply voucher discount percentage
            BigDecimal discountPercentage = voucher.getGiaTriVoucher(); // Assuming GiaTriVoucher is in percentage
            BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100)));
            thanhTien = thanhTien.multiply(discountMultiplier);

            total = total.add(thanhTien);
        }

        return total;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelsophieunhap = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabelnhanvien = new javax.swing.JLabel();
        jLabelthoigian = new javax.swing.JLabel();
        jLabelkhachang = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabelTenSP1 = new javax.swing.JLabel();
        jLabelTenSP2 = new javax.swing.JLabel();
        jLabelTenSP4 = new javax.swing.JLabel();
        jLabelTenSP3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabelTenSP7 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jPanelControl = new javax.swing.JPanel();
        jButtonPrint = new javax.swing.JButton();
        jButtonThoat = new javax.swing.JButton();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("In Thông Tin Phiếu Nhập");
        setResizable(false);

        jPanelHeader.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("HÓA ĐƠN");

        jLabelsophieunhap.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelsophieunhap.setText("Mã hóa đon:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel9.setText("Cửa Hàng VLXD Diamond");

        jLabelnhanvien.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelnhanvien.setText("Nhân viên:");

        jLabelthoigian.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelthoigian.setText("Thời gian :");

        jLabelkhachang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelkhachang.setText("Khách Hàng:");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel5.setLayout(new java.awt.CardLayout());

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Sản Phẩm");
        jPanel6.add(jLabel7);

        jLabelTenSP1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTenSP1.setText("Số Lượng:");
        jPanel6.add(jLabelTenSP1);

        jLabelTenSP2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTenSP2.setText("Giá:");
        jPanel6.add(jLabelTenSP2);

        jLabelTenSP4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTenSP4.setText("Tỉ lệ khuyến mãi:");
        jPanel6.add(jLabelTenSP4);

        jLabelTenSP3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTenSP3.setText("Thành tiền:");
        jPanel6.add(jLabelTenSP3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1101, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelTenSP7.setFont(new java.awt.Font("Arial", 0, 30)); // NOI18N
        jLabelTenSP7.setText("Tổng tiền:");

        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTenSP7, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelTenSP7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))))
        );

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHeaderLayout.createSequentialGroup()
                        .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(200, 200, 200))
                            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelthoigian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelkhachang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(jLabelnhanvien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelsophieunhap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(77, 77, 77))
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addComponent(jLabelsophieunhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelnhanvien)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelkhachang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelthoigian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonPrint.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonPrint.setText("In");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });

        jButtonThoat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonThoat.setText("Thoát");
        jButtonThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelControlLayout = new javax.swing.GroupLayout(jPanelControl);
        jPanelControl.setLayout(jPanelControlLayout);
        jPanelControlLayout.setHorizontalGroup(
            jPanelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonThoat, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelControlLayout.setVerticalGroup(
            jPanelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelControlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPrint)
                    .addComponent(jButtonThoat))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanelControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThoatActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonThoatActionPerformed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        try {
            // Print the content of jPanelHeader
            printPanel(jPanelHeader);

            // Show success notification
            JOptionPane.showMessageDialog(null, "Xuất Hóa Đơn Thành Công", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Dispose of the JFrame
            dispose();
        } catch (PrinterException e) {
            e.printStackTrace();
            // Show error notification
            JOptionPane.showMessageDialog(null, "lỗi khi xuất hóa đơn", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonPrintActionPerformed
    private void printPanel(JPanel panel) throws PrinterException {
        // Get an array of available print services
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        if (printServices.length > 0) {
            // Let the user choose a print service
            PrintService selectedService = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            if (service != null) {
                selectedService = service;
            } else {
                selectedService = PrintServiceLookup.lookupPrintServices(null, null)[0];
            }

            // Create a PrinterJob
            PrinterJob job = PrinterJob.getPrinterJob();

            // Set the selected print service
            job.setPrintService(selectedService);

            // Set the printable content using the provided panel
            job.setPrintable(new PanelPrintable(panel));

            // Display the print dialog to the user
            if (job.printDialog()) {
                // Perform the print operation
                try {
                    job.print();
                } catch (PrinterException e) {
                    throw new PrinterException("Error printing panel: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No printer available.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class PanelPrintable implements Printable {

        private final JPanel panel;

        public PanelPrintable(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) g;

            // Rotate the page for landscape mode
            g2d.rotate(Math.toRadians(90), pageFormat.getImageableX(), pageFormat.getImageableY());
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY() - pageFormat.getWidth());

            // Debug information
            System.out.println("Panel width: " + panel.getWidth());
            System.out.println("Panel height: " + panel.getHeight());
            System.out.println("Printable width: " + pageFormat.getImageableWidth());
            System.out.println("Printable height: " + pageFormat.getImageableHeight());

            // Apply scaling factor and additional offset
            double scaleX = (pageFormat.getImageableHeight()) / (panel.getPreferredSize().getWidth());
            double scaleY = pageFormat.getImageableWidth() / panel.getPreferredSize().getHeight();

           

            // Draw the panel content on the rotated page
            g2d.scale(scaleX, scaleY);

            panel.print(g2d);

            return PAGE_EXISTS;
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
            java.util.logging.Logger.getLogger(InHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JButton jButtonThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTenSP1;
    private javax.swing.JLabel jLabelTenSP2;
    private javax.swing.JLabel jLabelTenSP3;
    private javax.swing.JLabel jLabelTenSP4;
    private javax.swing.JLabel jLabelTenSP7;
    private javax.swing.JLabel jLabelkhachang;
    private javax.swing.JLabel jLabelnhanvien;
    private javax.swing.JLabel jLabelsophieunhap;
    private javax.swing.JLabel jLabelthoigian;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelControl;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel lblTongTien;
    // End of variables declaration//GEN-END:variables

}
