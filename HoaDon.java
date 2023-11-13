/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author thien ban
 */
public class HoaDon {
       private String maHoaDon;
    private String maKhachHang;
    private String maNhanVien;
    private Date ngayXuat;
    private String hinhThucThanhToan;
    private String maVoucher;

   // Constructor
    public HoaDon(String maHoaDon, String maKhachHang, String maNhanVien, Date ngayXuat, String hinhThucThanhToan, String maVoucher) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.ngayXuat = ngayXuat;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.maVoucher = maVoucher;
    }

    public HoaDon() {
    }
    // Các phương thức getter và setter cho trường tongTien
 

    

    // Các phương thức getter và setter cho các trường dữ liệu khác
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Date getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Date ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

 
}
