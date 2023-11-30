/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

import java.util.List;

/**
 *
 * @author ASUS X515EA
 */
public class PhieuNhapHangg {
    private String MaPhieuNhapHang;
    private String MaNhaCungCap;
    private String MaNhanVien;
    private String NgayNhap;
    private String TongTien;
    private String HinhThucThanhToan;
    private String Vatlieu;
    private String SoPhieuNhap;
    private String DiaDiem;
    private String DienDai;
    private String SoLuong;
    private String DonGia;
    private String NguoiGiao;
    private String DonViTinh;
    
    private boolean isGhiChu;
    
    public PhieuNhapHangg(){
    
    }

    public PhieuNhapHangg(String MaPhieuNhapHang, String MaNhaCungCap, String MaNhanVien, String NgayNhap, String TongTien, String HinhThucThanhToan, String Vatlieu, String SoPhieuNhap, String DiaDiem, String DienDai, String SoLuong, String DonGia, String NguoiGiao, String DonViTinh, boolean isGhiChu) {
        this.MaPhieuNhapHang = MaPhieuNhapHang;
        this.MaNhaCungCap = MaNhaCungCap;
        this.MaNhanVien = MaNhanVien;
        this.NgayNhap = NgayNhap;
        this.TongTien = TongTien;
        this.HinhThucThanhToan = HinhThucThanhToan;
        this.Vatlieu = Vatlieu;
        this.SoPhieuNhap = SoPhieuNhap;
        this.DiaDiem = DiaDiem;
        this.DienDai = DienDai;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
        this.NguoiGiao = NguoiGiao;
        this.DonViTinh = DonViTinh;
        this.isGhiChu = isGhiChu;
    }

    

    public String getMaPhieuNhapHang() {
        return MaPhieuNhapHang;
    }

    public void setMaPhieuNhapHang(String MaPhieuNhapHang) {
        this.MaPhieuNhapHang = MaPhieuNhapHang;
    }

    public String getMaNhaCungCap() {
        return MaNhaCungCap;
    }

    public void setMaNhaCungCap(String MaNhaCungCap) {
        this.MaNhaCungCap = MaNhaCungCap;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String TongTien) {
        this.TongTien = TongTien;
    }

    public String getHinhThucThanhToan() {
        return HinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String HinhThucThanhToan) {
        this.HinhThucThanhToan = HinhThucThanhToan;
    }

    public String getVatlieu() {
        return Vatlieu;
    }

    public void setVatlieu(String Vatlieu) {
        this.Vatlieu = Vatlieu;
    }

    public String getSoPhieuNhap() {
        return SoPhieuNhap;
    }

    public void setSoPhieuNhap(String SoPhieuNhap) {
        this.SoPhieuNhap = SoPhieuNhap;
    }

    public String getDiaDiem() {
        return DiaDiem;
    }

    public void setDiaDiem(String DiaDiem) {
        this.DiaDiem = DiaDiem;
    }

    public String getDienDai() {
        return DienDai;
    }

    public void setDienDai(String DienDai) {
        this.DienDai = DienDai;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getDonGia() {
        return DonGia;
    }

    public void setDonGia(String DonGia) {
        this.DonGia = DonGia;
    }

    public String getNguoiGiao() {
        return NguoiGiao;
    }

    public void setNguoiGiao(String NguoiGiao) {
        this.NguoiGiao = NguoiGiao;
    }

    public boolean isIsGhiChu() {
        return isGhiChu;
    }

    public void setIsGhiChu(boolean isGhiChu) {
        this.isGhiChu = isGhiChu;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String DonViTinh) {
        this.DonViTinh = DonViTinh;
    }

    

    
}