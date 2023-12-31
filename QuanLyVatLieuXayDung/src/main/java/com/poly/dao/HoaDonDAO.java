/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.HoaDon;
import com.poly.utils.JdbcUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thien ban
 */
public class HoaDonDAO extends QuanLyVatLieuXayDungDAO<HoaDon, String> {

    private static final String INSERT_SQL = "INSERT INTO HoaDon (MaHoaDon, MaKhachHang, MaNhanVien, NgayXuat, HinhThucThanhToan, MaVoucher) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE HoaDon SET MaKhachHang = ?, MaNhanVien = ?, NgayXuat = ? , HinhThucThanhToan = ?, MaVoucher = ? WHERE MaHoaDon = ?";
    private static final String DELETE_SQL = "DELETE FROM HoaDon WHERE MaHoaDon = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
    private static final String SELECT_BY_KEYWORD_SQL = "SELECT * FROM HoaDon WHERE (MaHoaDon LIKE ?)";

    private static final String SELECT_TOP_BY_ORDER_BY_MAHOADON_DESC_SQL
            = "SELECT TOP 1 * FROM HoaDon ORDER BY MaHoaDon DESC";

    @Override
    public void insert(HoaDon entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaHoaDon(),
                entity.getMaKhachHang(),
                entity.getMaNhanVien(),
                entity.getNgayXuat(),
                entity.getHinhThucThanhToan(),
                entity.getMaVoucher());
    }

    @Override
    public void update(HoaDon entity) {
        JdbcUtil.executeUpdate(UPDATE_SQL,
                entity.getMaKhachHang(),
                entity.getMaNhanVien(),
                entity.getNgayXuat(),
                entity.getHinhThucThanhToan(),
                entity.getMaVoucher(),
                entity.getMaHoaDon());
    }

    @Override
    public void delete(String maHoaDon) {
        JdbcUtil.executeUpdate(DELETE_SQL, maHoaDon);
    }

    @Override
    public HoaDon selectById(String maHoaDon) {
        List<HoaDon> list = selectBySql(SELECT_BY_ID_SQL, maHoaDon);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public List<HoaDon> selectByKeyword(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%");
    }

    @Override
    protected List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHoaDon(rs.getString("MaHoaDon"));
                entity.setMaKhachHang(rs.getString("MaKhachHang"));
                entity.setMaNhanVien(rs.getString("MaNhanVien"));
                entity.setNgayXuat(rs.getDate("NgayXuat"));

                entity.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                entity.setMaVoucher(rs.getString("MaVoucher"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String findTopMaHoaDonByOrderByMaHoaDonDesc() {
        List<String> list = selectMaHoaDonBySql(SELECT_TOP_BY_ORDER_BY_MAHOADON_DESC_SQL);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private List<String> selectMaHoaDonBySql(String sql, Object... args) {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                String maHoaDon = rs.getString("MaHoaDon");
                list.add(maHoaDon);
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
