
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

import com.poly.dao.QuanLyVatLieuXayDungDAO;

/**
 *
 * @author ASUS X515EA
 */
public class PhieuNhapHangDAO extends QuanLyVatLieuXayDungDAO<PhieuNhapHang, String>{
      
    String INSERT_SQL = "INSERT INTO PhieuNhapHang (MaPhieuNhapHang,MaNhaCungCap,MaNhanVien,NgayNhap,TongTien,HinhThucThanhToan) VALUES (?,?)";
    String UPDATE_SQL = "UPDATE PhieuNhapHang SET MaNhaCungCap=?, MaNhanVien =?, NgayNhap =?, TongTien =?,HinhThucThanhToan =? WHERE MaPhieuNhapHang=?";
    String DELETE_SQL = "DELETE FROM PhieuNhapHang WHERE MaPhieuNhapHang=?";
    String SELECT_ALL_SQL = "SELECT * FROM PhieuNhapHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM PhieuNhapHang WHERE MaPhieuNhapHang= ?";
    String SORT_DECS = "SELECT * FROM PhieuNhapHang WHERE isDelete = 0 ORDER BY MaPhieuNhapHang DESC";
    String SORT_ASC = "SELECT * FROM PhieuNhapHang WHERE isDelete = 0 ORDER BY MaPhieuNhapHang ASC";
    String FIND_ID_BY_NAME = "SELECT ID FROM PhieuNhapHang WHERE HinhThucThanhToan = ?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM PhieuNhapHang WHERE (HinhThucThanhToan LIKE ? )";
    
    @Override
    public void insert(PhieuNhapHang entity) {
         JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaNhaCungCap(),
                entity.getMaNhanVien());
                entity.getNgayNhap();
                entity.getHinhThucThanhToan();
                entity.getTongTien();
    }

    @Override
    public void update(PhieuNhapHang entity) {
         JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaNhaCungCap(),
                entity.getMaNhanVien());
                entity.getNgayNhap();
                entity.getHinhThucThanhToan();
                entity.getTongTien();
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

    @Override
    protected List<PhieuNhapHang> selectBySql(String sql, Object... args) {
        List<PhieuNhapHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                PhieuNhapHang entity = new PhieuNhapHang();
                entity.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
                entity.setMaNhanVien(rs.getString("MaNhanVien"));
                entity.setNgayNhap(rs.getString("NgayNhap"));
                entity.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                entity.setTongTien(rs.getString("TongTien"));
               
             
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
     public List<PhieuNhapHang> selectByKeyword( String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }
    
}
