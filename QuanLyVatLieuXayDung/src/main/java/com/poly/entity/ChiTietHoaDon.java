/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

import java.math.BigDecimal;

/**
 *
 * @author thien ban
 */
public class ChiTietHoaDon {

    private int maChiTietHoaDon;
    private String maHoaDon;
    private String maSanPham;
    private int soLuong;
    private BigDecimal donGia;
    private BigDecimal tongTien;

    public ChiTietHoaDon() {
    }

    ;

    public ChiTietHoaDon(int maChiTietHoaDon, String maHoaDon, String maSanPham, String donViTinh, int soLuong, BigDecimal donGia, BigDecimal tongTien) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tongTien = tongTien;
    }

    public int getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(int maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }



    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
}
