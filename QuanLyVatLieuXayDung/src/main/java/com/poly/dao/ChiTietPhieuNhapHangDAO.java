/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.ChiTietPhieuNhap;
import com.poly.utils.JdbcUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS X515EA
 */
public class ChiTietPhieuNhapHangDAO {

    private static final String INSERT_SQL = "INSERT INTO ChiTietPhieuNhapHang (MaChiTietPhieuNhapHang, MaPhieuNhapHang, MaSanPham, DonViTinh, SoLuong, DonGia, TongTien) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE ChiTietPhieuNhapHang SET MaPhieuNhapHang = ?, MaSanPham = ?, DonViTinh = ?, SoLuong = ?, DonGia = ?, TongTien = ? WHERE MaChiTietHoaDon = ?";
    private static final String DELETE_SQL = "DELETE FROM ChiTietPhieuNhapHang WHERE MaChiTietPhieuNhapHang = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM ChiTietPhieuNhapHang";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietPhieuNhapHang WHERE MaPhieuNhapHang = ?";
    private static final String SELECT_BY_PHIEUNHAPHANG_SQL = "SELECT * FROM ChiTietPhieuNhapHang WHERE MaPhieuNhapHang = ?";

    public void insert(ChiTietPhieuNhap entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaPhieuNhapHang(),
                entity.getMaSanPham(),
                entity.getDonViTinh(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getTongTien());
    }

    public void update(ChiTietPhieuNhap entity) {
        JdbcUtil.executeUpdate(UPDATE_SQL,
                entity.getMaPhieuNhapHang(),
                entity.getMaSanPham(),
                entity.getDonViTinh(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getTongTien());

    }

    public void delete(String maChiTietPhieuNhap) {
        JdbcUtil.executeUpdate(DELETE_SQL, maChiTietPhieuNhap);
    }

    public List<ChiTietPhieuNhap> selectAllById(String maPhieuNhapHang) {
        return selectBySql(SELECT_BY_ID_SQL);
    }

    public List<ChiTietPhieuNhap> selectByMaPhieuNhapHang(String maPhieuNhapHang) {
        return selectBySql(SELECT_BY_PHIEUNHAPHANG_SQL);
    }

    public List<ChiTietPhieuNhap> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    private List<ChiTietPhieuNhap> selectBySql(String sql, Object... args) {
        List<ChiTietPhieuNhap> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                ChiTietPhieuNhap entity = new ChiTietPhieuNhap();
                entity.setMaChiTietPhieuNhapHang(rs.getInt("MaChiTietPhieuNhapHang"));
                entity.setMaPhieuNhapHang(rs.getString("MaPhieuNhapHang"));
                entity.setMaSanPham(rs.getString("MaSanPham"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setSoLuong(rs.getInt("SoLuong")); // Assuming SoLuong is an int
                entity.setDonGia(rs.getBigDecimal("DonGia"));
                entity.setTongTien(rs.getBigDecimal("TongTien"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ChiTietPhieuNhap> findMaPhieuNhapByPhieuNH(String maNhaPH) {
        String FIND_BY_PHIEUNHAPHANG_SQL = "SELECT * FROM ChiTietPhieuNhapHang WHERE MaPhieuNhapHang = ?";
        List<ChiTietPhieuNhap> chiTietList = new ArrayList<>();

        try {
            ResultSet rs = JdbcUtil.executeQuery(FIND_BY_PHIEUNHAPHANG_SQL, maNhaPH);
            while (rs.next()) {
                ChiTietPhieuNhap entity = new ChiTietPhieuNhap();
                entity.setMaChiTietPhieuNhapHang(rs.getInt("MaChiTietPhieuNhapHang"));
                entity.setMaPhieuNhapHang(rs.getString("MaPhieuNhapHang"));
                entity.setMaSanPham(rs.getString("MaSanPham"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setSoLuong(rs.getInt("SoLuong")); // Assuming SoLuong is an int
                entity.setDonGia(rs.getBigDecimal("DonGia"));
                entity.setTongTien(rs.getBigDecimal("TongTien"));
                chiTietList.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return chiTietList;
    }
}
