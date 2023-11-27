/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class Voucher {
    private String MaVoucher;
    private String TenVoucher;
    private BigDecimal GiaTriVoucher;
    private Date NgayHetHan;
    private int SoLuong;
    
    public Voucher() {
        
    }

    public Voucher(String MaVoucher, String TenVoucher, BigDecimal GiaTriVoucher, Date NgayHetHan, int SoLuong) {
        this.MaVoucher = MaVoucher;
        this.TenVoucher = TenVoucher;
        this.GiaTriVoucher = GiaTriVoucher;
        this.NgayHetHan = NgayHetHan;
        this.SoLuong = SoLuong;
    }

    public String getMaVoucher() {
        return MaVoucher;
    }

    public void setMaVoucher(String MaVoucher) {
        this.MaVoucher = MaVoucher;
    }

    public String getTenVoucher() {
        return TenVoucher;
    }

    public void setTenVoucher(String TenVoucher) {
        this.TenVoucher = TenVoucher;
    }

    public BigDecimal getGiaTriVoucher() {
        return GiaTriVoucher;
    }

    public void setGiaTriVoucher(BigDecimal GiaTriVoucher) {
        this.GiaTriVoucher = GiaTriVoucher;
    }

    public Date getNgayHetHan() {
        return NgayHetHan;
    }

    public void setNgayHetHan(Date NgayHetHan) {
        this.NgayHetHan = NgayHetHan;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }


}
