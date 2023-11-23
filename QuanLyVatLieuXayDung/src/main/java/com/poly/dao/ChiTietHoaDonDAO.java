/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.ChiTietHoaDon;
import com.poly.utils.JdbcUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thien ban
 */
public class ChiTietHoaDonDAO extends QuanLyVatLieuXayDungDAO<ChiTietHoaDon, String> {

    private static final String INSERT_SQL = "INSERT INTO ChiTietHoaDon ( MaHoaDon, MaSanPham, SoLuong, DonGia, TongTien) VALUES (?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE ChiTietHoaDon SET MaHoaDon = ?, MaSanPham = ?, SoLuong = ?, DonGia = ?, TongTien = ? WHERE MaChiTietHoaDon = ?";
    private static final String DELETE_SQL = "DELETE FROM ChiTietHoaDon WHERE MaChiTietHoaDon = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM ChiTietHoaDon";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietHoaDon WHERE MaHoaDon = ?";
    private static final String SELECT_BY_MAHOADON_SQL = "SELECT * FROM ChiTietHoaDon WHERE MaHoaDon = ?";

    @Override
    public void insert(ChiTietHoaDon entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaHoaDon(),
                entity.getMaSanPham(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getTongTien());
    }

    @Override
    public void update(ChiTietHoaDon entity) {
        JdbcUtil.executeUpdate(UPDATE_SQL,
                entity.getMaHoaDon(),
                entity.getMaSanPham(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getTongTien());

    }

    @Override
    public void delete(String maChiTietHoaDon) {
        JdbcUtil.executeUpdate(DELETE_SQL, maChiTietHoaDon);
    }

    public List<ChiTietHoaDon> selectAllById(String maHoaDon) {
        return selectBySql(SELECT_BY_ID_SQL, maHoaDon);
    }

    public List<ChiTietHoaDon> selectByMaHoaDon(String maHoaDon) {
        return selectBySql(SELECT_BY_MAHOADON_SQL, maHoaDon);
    }

    @Override
    public List<ChiTietHoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<ChiTietHoaDon> selectBySql(String sql, Object... args) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                ChiTietHoaDon entity = new ChiTietHoaDon();
                entity.setMaChiTietHoaDon(rs.getInt("MaChiTietHoaDon"));
                entity.setMaHoaDon(rs.getString("MaHoaDon"));
                entity.setMaSanPham(rs.getString("MaSanPham"));
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

    @Override
    public ChiTietHoaDon selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
