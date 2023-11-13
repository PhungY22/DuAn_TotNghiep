/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

import java.util.Date;

/**
 *
 * @author Nhu Y
 */
public class NhanVien {
    private String MaNhanVien;
    private String TenNhanVien;
    private String Hinh;
    private String MatKhau;
    private String SoDienThoai;
    private String Email;
    private String DiaChi;
    private Date NgayThangNamSinh;
    private boolean GioiTinh;
    private String ChucVu;

    public NhanVien() {
    }

    public NhanVien(String MaNhanVien, String TenNhanVien, String Hinh, String MatKhau, String SoDienThoai, String Email, String DiaChi, Date NgayThangNamSinh, boolean GioiTinh, String ChucVu) {
        this.MaNhanVien = MaNhanVien;
        this.TenNhanVien = TenNhanVien;
        this.Hinh = Hinh;
        this.MatKhau = MatKhau;
        this.SoDienThoai = SoDienThoai;
        this.Email = Email;
        this.DiaChi = DiaChi;
        this.NgayThangNamSinh = NgayThangNamSinh;
        this.GioiTinh = GioiTinh;
        this.ChucVu = ChucVu;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getTenNhanVien() {
        return TenNhanVien;
    }

    public void setTenNhanVien(String TenNhanVien) {
        this.TenNhanVien = TenNhanVien;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public Date getNgayThangNamSinh() {
        return NgayThangNamSinh;
    }

    public void setNgayThangNamSinh(Date NgayThangNamSinh) {
        this.NgayThangNamSinh = NgayThangNamSinh;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

    

    
    
}
