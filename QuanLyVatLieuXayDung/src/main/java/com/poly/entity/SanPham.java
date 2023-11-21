/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

/**
 *
 * @author Nhu Y
 */
public class SanPham {
    private String MaSanPham;
    private String TenSanPham;
    private String Hinh = "NoImage.png";
    private String MaLoaiSanPham;
    private String GiaNhap;
    private String GiaXuat;
    private int SoLuong;
    public SanPham() {
        
}
    public SanPham(String MaSanPham, String TenSanPham, String Hinh, String MaLoaiSanPham, String GiaNhap, String GiaXuat, int SoLuong) {
        this.MaSanPham = MaSanPham;
        this.TenSanPham = TenSanPham;
        this.Hinh = Hinh;
        this.MaLoaiSanPham = MaLoaiSanPham;
        this.GiaNhap = GiaNhap;
        this.GiaXuat = GiaXuat;
        this.SoLuong = SoLuong;
       
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getMaLoaiSanPham() {
        return MaLoaiSanPham;
    }

    public void setMaLoaiSanPham(String Loai) {
        this.MaLoaiSanPham = Loai;
    }

    public String getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(String GiaNhap) {
        this.GiaNhap = GiaNhap;
    }

    public String getGiaXuat() {
        return GiaXuat;
    }

    public void setGiaXuat(String GiaXuat) {
        this.GiaXuat = GiaXuat;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

}
