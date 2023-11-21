/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.SanPham;
import com.poly.utils.JdbcUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nhu Y
 */
public class SanPhamDAO extends QuanLyVatLieuXayDungDAO<SanPham, String>{
    String INSERT_SQL = "INSERT INTO SanPham (MaSanPham,TenSanPham,Hinh,MaLoaiSanPham,GiaNhap,GiaXuat,SoLuong) VALUES (?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE SanPham SET TenSanPham =?,Hinh =?,MaLoaiSanPham=?,GiaNhap=?,GiaXuat=?,SoLuong=? WHERE MaSanPham=?";
    String DELETE_SQL = "UPDATE SanPham SET isDelete = 1 WHERE MaSanPham = ?";
    String SELECT_ALL_SQL = "SELECT * FROM SanPham";
    String SELECT_BY_ID_SQL = "SELECT * FROM SanPham WHERE MaSanPham= ?";
    String SORT_DECS = "SELECT * FROM SanPham WHERE isDelete = 0 ORDER BY MaSanPham DESC";
    String SORT_ASC = "SELECT * FROM SanPham WHERE isDelete = 0 ORDER BY MaSanPham ASC";
    String FIND_ID_BY_NAME = "SELECT MaSanPham FROM SanPham WHERE TenSanPham = ?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM SanPham WHERE (TenSanPham LIKE ? )";

    @Override
    public void insert(SanPham entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaSanPham(),
                entity.getTenSanPham(),
                entity.getHinh(),
                entity.getMaLoaiSanPham(),
                entity.getGiaNhap(),
                entity.getGiaXuat(),
                entity.getSoLuong());
             
    }

    @Override
    public void update(SanPham entity) {
        JdbcUtil.executeUpdate(UPDATE_SQL,
               entity.getMaSanPham(),
                entity.getTenSanPham(),
                entity.getHinh(),
                entity.getMaLoaiSanPham(),
                entity.getGiaNhap(),
                entity.getGiaXuat(),
                entity.getSoLuong());
             
    }

    @Override
    public void delete(String id) {
        JdbcUtil.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SanPham> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSanPham(rs.getString("MaSanPham"));
                entity.setTenSanPham(rs.getString("TenSanPham"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setMaLoaiSanPham(rs.getString("MaLoaiSanPham"));
                entity.setGiaNhap(rs.getString("GiaNhap"));
                entity.setGiaXuat(rs.getString("GiaXuat"));
                entity.setSoLuong(rs.getInt("SoLuong"));
             
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String findIdByName(String name) {
        String id = "";
        try {
            ResultSet rs = JdbcUtil.executeQuery(FIND_ID_BY_NAME, name);
            while (rs.next()) {
                id = rs.getString(1);
            }
            rs.getStatement().getConnection().close();
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
     public List<SanPham> selectByKeyword( String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }
}
