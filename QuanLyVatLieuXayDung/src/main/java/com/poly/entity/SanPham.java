/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

import static javax.management.Query.value;

/**
 *
 * @author Nhu Y
 */
public class SanPham {
    private String MaSanPham;
    private String TenSanPham;
    private String Hinh = "NoImage.png";
    private String MaLoaiSanPham;
    private double GiaNhap;
    private double GiaXuat;
    private int SoLuong;
    public SanPham() {
        
}
    public SanPham(String MaSanPham, String TenSanPham, String Hinh, String MaLoaiSanPham, double GiaNhap, double GiaXuat, int SoLuong) {
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

    public double getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(double GiaNhap) {
        this.GiaNhap = GiaNhap;
    }

    public double getGiaXuat() {
        return GiaXuat;
    }

    public void setGiaXuat(double GiaXuat) {
        this.GiaXuat = GiaXuat;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public boolean isValid() {
       return MaSanPham != null && !MaSanPham.isEmpty() &&
           TenSanPham != null && !TenSanPham.isEmpty() &&
           SoLuong > 0 && Hinh != null && !Hinh.isEmpty()
           && MaLoaiSanPham != null && !MaLoaiSanPham.isEmpty();
      
    
    }

}
