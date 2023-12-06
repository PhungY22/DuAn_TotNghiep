/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.KhachHang;
import com.poly.entity.Voucher;
import com.poly.utils.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyên An
 */
    public class VoucherDAO extends QuanLyVatLieuXayDungDAO<Voucher, String>{
     String INSERT_SQL = "INSERT INTO Voucher (MaVoucher,TenVoucher,GiaTriVoucher,NgayHetHan,SoLuong) VALUES (?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Voucher SET TenVoucher =?,GiaTriVoucher =?,NgayHetHan=?,SoLuong=? WHERE MaVoucher=?";
    String DELETE_SQL = "DELETE FROM Voucher WHERE MaVoucher=?";
    String SELECT_ALL_SQL = "SELECT * FROM Voucher";
    String SELECT_BY_ID_SQL = "SELECT * FROM Voucher WHERE MaVoucher= ?";
    String SORT_DECS = "SELECT * FROM Voucher WHERE isDelete = 0 ORDER BY MaVoucher DESC";
    String SORT_ASC = "SELECT * FROM Voucher WHERE isDelete = 0 ORDER BY MaVoucher ASC";
    String FIND_ID_BY_NAME = "SELECT MaVoucher FROM Voucher WHERE TenVoucher = ?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM Voucher WHERE (TenVoucher LIKE ? )";

    @Override
    public void insert(Voucher entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaVoucher(),
                entity.getTenVoucher(),
                entity.getGiaTriVoucher(),
                entity.getNgayHetHan(),
                entity.getSoLuong());
    }

    @Override
    public void update(Voucher entity) {
        JdbcUtil.executeUpdate(UPDATE_SQL,
               entity.getMaVoucher(),
                entity.getTenVoucher(),
                entity.getGiaTriVoucher(),
                entity.getNgayHetHan(),
                entity.getSoLuong());
    }

    @Override
    public void delete(String MaVoucher) {
        // Kiểm tra xem mã khách hàng có null hay không
        if (MaVoucher == null) {
            return;
        }
//        KhachHang khachHang = findByMaKhachHang(maKhachHang);
//        if (khachHang == null) {
//         return;
//        }

    // Tạo câu lệnh SQL để xóa khách hàng
        String sql = "DELETE FROM Voucher WHERE MaVoucher = ?";

    // Thực thi câu lệnh SQL
        JdbcUtil.executeUpdate(sql, MaVoucher);
    }

    @Override
    public Voucher selectById(String id) {
        List<Voucher> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Voucher> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<Voucher> selectBySql(String sql, Object... args) {
        List<Voucher> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                Voucher entity = new Voucher();
                entity.setMaVoucher(rs.getString("MaVoucher"));
                entity.setTenVoucher(rs.getString("TenVoucher"));
                entity.setGiaTriVoucher(rs.getBigDecimal("GiaTriVoucher"));
                entity.setNgayHetHan(rs.getDate("NgayHetHan"));
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
     public List<Voucher> selectByKeyword( String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }

    private Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}