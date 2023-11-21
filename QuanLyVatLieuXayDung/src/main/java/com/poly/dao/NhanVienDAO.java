/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.NhanVien;
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
public class NhanVienDAO extends QuanLyVatLieuXayDungDAO<NhanVien, String>{
    String INSERT_SQL = "INSERT INTO NhanVien (MaNhanVien,Hinh,TenNhanVien,MatKhau,SoDienThoai,Email,DiaChi,NgayThangNamSinh,GioiTinh,ChucVu) VALUES (?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NhanVien SET Hinh=?, TenNhanVien=?, MatKhau=?, SoDienThoai=?, Email=?, DiaChi=?, NgayThangNamSinh=?, GioiTinh=?, ChucVu=? WHERE MaNhanVien=?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNhanVien=?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNhanVien= ?";
    String SORT_DECS = "SELECT * FROM NhanVien WHERE isDelete = 0 ORDER BY MaNhanVien DESC";
    String SORT_ASC = "SELECT * FROM NhanVien WHERE isDelete = 0 ORDER BY MaNhanVien ASC";
    String FIND_ID_BY_NAME = "SELECT MaNhanVien FROM NhanVien WHERE TenNhanVien = ?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM NhanVien WHERE (TenNhanVien LIKE ? )";
    @Override
    public void insert(NhanVien entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaNhanVien(),
                entity.getHinh(),
                entity.getTenNhanVien(),
                entity.getMatKhau(),
                entity.getSoDienThoai(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.getNgayThangNamSinh(),
                entity.isGioiTinh(),
                entity.getChucVu());           
    }

    public void update(NhanVien entity) {
        JdbcUtil.executeUpdate(UPDATE_SQL,
                entity.getMaNhanVien(),
                entity.getHinh(),
                entity.getTenNhanVien(),
                entity.getMatKhau(),
                entity.getSoDienThoai(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.getNgayThangNamSinh(),
                entity.isGioiTinh(),
                entity.getChucVu());            
    }

    @Override
    public void delete(String id) {
        JdbcUtil.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNhanVien(rs.getString("MaNhanVien"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setTenNhanVien(rs.getString("TenNhanVien"));              
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setSoDienThoai(rs.getString("SoDienThoai"));
                entity.setEmail(rs.getString("Email"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setNgayThangNamSinh(rs.getDate("NgayThangNamSinh"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setChucVu(rs.getString("ChucVu"));
                           
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
     public List<NhanVien> selectByKeyword( String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }
}
