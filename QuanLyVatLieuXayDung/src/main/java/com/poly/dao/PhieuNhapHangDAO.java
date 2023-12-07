/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.PhieuNhapHang;
import com.poly.utils.JdbcUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS X515EA
 */
public class PhieuNhapHangDAO extends QuanLyVatLieuXayDungDAO<PhieuNhapHang, String> {

    private static final String INSERT_SQL = "INSERT INTO PhieuNhapHang (MaPhieuNhapHang, MaNhaCungCap, MaNhanVien, NgayNhap, HinhThucThanhToan, VatLieu, DiaDiem, NguoiGiao) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE PhieuNhapHang SET MaNhaCungCap = ?, MaNhanVien = ?, NgayNhap = ? ,  HinhThucThanhToan = ?, VatLieu = ?, DiaDiem = ?, NguoiGiao = ? WHERE MaPhieuNhapHang = ?";
    private static final String DELETE_SQL = "DELETE FROM PhieuNhapHang WHERE MaPhieuNhapHang = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM PhieuNhapHang";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM PhieuNhapHang WHERE MaPhieuNhapHang = ?";
    private static final String SELECT_BY_KEYWORD_SQL = "SELECT * FROM PhieuNhapHang WHERE (MaPhieuNhapHang LIKE ?)";

    private static final String SELECT_TOP_BY_ORDER_BY_MAPHIEUNHAPHANG_DESC_SQL
            = "SELECT TOP 1 * FROM PhieuNhapHang ORDER BY MaPhieuNhapHang DESC";

    @Override
    public void insert(PhieuNhapHang entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaNhaCungCap(),
                entity.getMaPhieuNhapHang(),
                entity.getMaNhanVien(),
                entity.getHinhThucThanhToan(),
                entity.getNgayNhap(),
                entity.getVatlieu(),
                entity.getSoPhieuNhap(),
                entity.getNguoiGiao());
    }

    @Override
    public void update(PhieuNhapHang entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaNhaCungCap(),
                entity.getMaPhieuNhapHang(),
                entity.getMaNhanVien(),
                entity.getHinhThucThanhToan(),
                entity.getNgayNhap(),
                entity.getVatlieu(),
                entity.getSoPhieuNhap(),
                entity.getNguoiGiao());
    }

    @Override
    public void delete(String id) {
        JdbcUtil.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public PhieuNhapHang selectById(String id) {
        List<PhieuNhapHang> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuNhapHang> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<PhieuNhapHang> selectByKeyword(String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }

    @Override
    protected List<PhieuNhapHang> selectBySql(String sql, Object... args) {
        List<PhieuNhapHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                PhieuNhapHang entity = new PhieuNhapHang();
                entity.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
                entity.setMaNhanVien(rs.getString("MaNhanVien"));
                entity.setMaPhieuNhapHang(rs.getString("MaPhieuNhapHang"));
                entity.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                entity.setNgayNhap(rs.getDate("NgayNhap"));
                entity.setVatlieu(rs.getString("VatLieu"));
                entity.setSoPhieuNhap(rs.getString("SoPhieuNhap"));
                entity.setDiaDiem(rs.getString("DiaDiem"));

                entity.setNguoiGiao(rs.getString("NguoiGiao"));

                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String findTopMaPhieuNhapHangOrderByMaPhieuNhapDesc() {
        List<String> list = selectMaPhieuNhapHangSql(SELECT_TOP_BY_ORDER_BY_MAPHIEUNHAPHANG_DESC_SQL);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private List<String> selectMaPhieuNhapHangSql(String sql, Object... args) {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                String maPhieuNhapHang = rs.getString("MaPhieuNhapHang");
                list.add(maPhieuNhapHang);
            }
            // It's good practice to close ResultSet, Statement, and Connection in a finally block
            // to ensure resources are properly released even if an exception occurs
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
