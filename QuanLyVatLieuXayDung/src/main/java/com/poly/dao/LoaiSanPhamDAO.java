/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.ChiTietPhieuNhap;
import com.poly.entity.LoaiSanPham;
import com.poly.utils.JdbcUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Nhu Y
 */
public class LoaiSanPhamDAO extends QuanLyVatLieuXayDungDAO<LoaiSanPham, String>{

     String INSERT_SQL = "INSERT INTO LoaiSanPham (MaLoaiSanPham,TenLoaiSanPham) VALUES (?,?)";
    String UPDATE_SQL = "UPDATE LoaiSanPham SET TenLoaiSanPham =? WHERE MaLoaiSanPham=?";
    String DELETE_SQL = "DELETE FROM LoaiSanPham WHERE MaLoaiSanPham=?";
    String SELECT_ALL_SQL = "SELECT * FROM LoaiSanPham";
    String SELECT_BY_ID_SQL = "SELECT * FROM LoaiSanPham WHERE MaLoaiSanPham= ?";
    String SORT_DECS = "SELECT * FROM LoaiSanPham WHERE isDelete = 0 ORDER BY MaLoaiSanPham DESC";
    String SORT_ASC = "SELECT * FROM LoaiSanPham WHERE isDelete = 0 ORDER BY MaLoaiSanPham ASC";
    String FIND_ID_BY_NAME = "SELECT MaLoaiSanPham FROM LoaiSanPham WHERE TenLoaiSanPham = ?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM LoaiSanPham WHERE (TenLoaiSanPham LIKE ? )";
    public String findIdByName;

    @Override
    public void insert(LoaiSanPham entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaLoaiSanPham(),
                entity.getTenLoaiSanPham());
             
    }

    @Override
    public void update(LoaiSanPham entity) {
        JdbcUtil.executeUpdate(UPDATE_SQL,
               entity.getMaLoaiSanPham(),
                entity.getTenLoaiSanPham());
             
    }

    @Override
    public void delete(String id) {
        JdbcUtil.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public LoaiSanPham selectById(String id) {
        List<LoaiSanPham> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LoaiSanPham> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<LoaiSanPham> selectBySql(String sql, Object... args) {
        List<LoaiSanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
               LoaiSanPham entity = new LoaiSanPham();
                entity.setMaLoaiSanPham(rs.getString("MaLoaiSanPham"));
                entity.setTenLoaiSanPham(rs.getString("TenLoaiSanPham"));
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
     public List<LoaiSanPham> selectByKeyword( String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }
     
}
