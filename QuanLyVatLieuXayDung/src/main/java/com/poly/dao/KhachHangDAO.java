/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.KhachHang;
import com.poly.utils.JdbcUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nguyên An
 */
    public class KhachHangDAO extends QuanLyVatLieuXayDungDAO<KhachHang, String>{
     String INSERT_SQL = "INSERT INTO KhachHang (MaKhachHang,TenKhachHang,DiaChi,SoDienThoai,Email,GioiTinh) VALUES (?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE KhachHang SET TenKhachHang =?,DiaChi =?,SoDienThoai=?,Email=?,GioiTinh=? WHERE MaKhachHang=?";
    String DELETE_SQL = "DELETE FROM KhachHang WHERE MaKhachHang=?";
    String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE MaKhachHang= ?";
    String SORT_DECS = "SELECT * FROM KhachHang WHERE isDelete = 0 ORDER BY MaKhachHang DESC";
    String SORT_ASC = "SELECT * FROM KhachHang WHERE isDelete = 0 ORDER BY MaKhachHang ASC";
    String FIND_ID_BY_NAME = "SELECT ID FROM KhachHang WHERE TenKhachHang = ?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM KhachHang WHERE (TenKhachHang LIKE ? )";


    @Override
    public void insert(KhachHang entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaKhachHang(),
                entity.getTenKhachHang(),
                entity.getDiaChi(),
                entity.getSoDienThoai(),
                entity.getEmail(),
                entity.isGioiTinh());
    }

    @Override
    public void update(KhachHang entity) {
//        JdbcUtil.executeUpdate(UPDATE_SQL,
//                entity.getMaKhachHang(),
//                entity.getTenKhachHang(),
//                entity.getDiaChi(),
//                entity.getSoDienThoai(),
//                entity.getEmail(),
//                entity.isGioiTinh());
            entity.setTenKhachHang(entity.getTenKhachHang());
            entity.setDiaChi(entity.getDiaChi());
            entity.setSoDienThoai(entity.getSoDienThoai());
            entity.setEmail(entity.getEmail());
            entity.setGioiTinh(entity.isGioiTinh());

            JdbcUtil.executeUpdate(UPDATE_SQL,
            entity.getMaKhachHang());
    }

    @Override
    public void delete(String maKhachHang) {
    // Kiểm tra xem mã khách hàng có null hay không
        if (maKhachHang == null) {
            return;
        }
//        KhachHang khachHang = findByMaKhachHang(maKhachHang);
//        if (khachHang == null) {
//         return;
//        }

    // Tạo câu lệnh SQL để xóa khách hàng
        String sql = "DELETE FROM KhachHang WHERE MaKhachHang = ?";

    // Thực thi câu lệnh SQL
        JdbcUtil.executeUpdate(sql, maKhachHang);
    }

    @Override
    public KhachHang selectById(String id) {
        List<KhachHang> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKhachHang(rs.getString("MaKhachHang"));
                entity.setTenKhachHang(rs.getString("TenKhachHang"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setSoDienThoai(rs.getString("SoDienThoai"));
                entity.setEmail(rs.getString("Email"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
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
     public List<KhachHang> selectByKeyword( String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }

    private KhachHang findByMaKhachHang(String maKhachHang) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}