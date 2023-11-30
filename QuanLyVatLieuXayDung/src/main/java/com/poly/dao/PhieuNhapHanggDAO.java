/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.PhieuNhapHangg;
import com.poly.utils.JdbcUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS X515EA
 */
public class PhieuNhapHanggDAO extends QuanLyVatLieuXayDungDAO<PhieuNhapHangg, String> {
       String INSERT_SQL = "INSERT INTO PhieuNhapHang (MaPhieuNhapHang,MaNhaCungCap,MaNhanVien,NgayNhap,TongTien,HinhThucThanhToan,GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE PhieuNhapHang SET MaNhaCungCap =?, MaNhanVien =?, NgayNhap =?, TongTien =?, HinhThucThanhToan =?, GhiChu =? WHERE MaPhieuNhapHang=?";
    String DELETE_SQL = "DELETE FROM PhieuNhapHang WHERE MaPhieuNhapHang=?";
    String SELECT_ALL_SQL = "SELECT * FROM PhieuNhapHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM PhieuNhapHang WHERE MaPhieuNhapHang= ?";
    String SORT_DECS = "SELECT * FROM PhieuNhapHang WHERE isDelete = 0 ORDER BY MaPhieuNhapHang DESC";
    String SORT_ASC = "SELECT * FROM PhieuNhapHang WHERE isDelete = 0 ORDER BY MaPhieuNhapHang ASC";
    String FIND_ID_BY_NAME = "SELECT ID FROM PhieuNhapHang WHERE MaNhaCungCap = ?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM PhieuNhapHang WHERE (MaNhaCungCap LIKE ? )";
    
    @Override
    public void insert(PhieuNhapHangg entity) {
         JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaNhaCungCap(),
                entity.getMaPhieuNhapHang(),
                entity.getMaNhanVien(),
                entity.getHinhThucThanhToan(),
                entity.getNgayNhap(),
                entity.getTongTien(),
                entity.getVatlieu(),
                entity.getDienDai(),
                entity.getSoPhieuNhap(),
                entity.getDonGia(),
                entity.getSoLuong(),
                entity.getNguoiGiao(),
                entity.getDonViTinh(),
                entity.isIsGhiChu());        
    }

    @Override
    public void update(PhieuNhapHangg entity) {
         JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaNhaCungCap(),
                entity.getMaPhieuNhapHang(),
                entity.getMaNhanVien(),
                entity.getHinhThucThanhToan(),
                entity.getNgayNhap(),
                entity.getTongTien(),
                entity.getVatlieu(),
                entity.getDienDai(),
                entity.getSoPhieuNhap(),
                entity.getDonGia(),
                entity.getSoLuong(),
                entity.getNguoiGiao(),
                entity.getDonViTinh(),
                entity.isIsGhiChu());   
    }

    @Override
    public void delete(String id) {
        JdbcUtil.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public PhieuNhapHangg selectById(String id) {
        List<PhieuNhapHangg> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuNhapHangg> selectAll() {
         return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<PhieuNhapHangg> selectBySql(String sql, Object... args) {
             List<PhieuNhapHangg> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                PhieuNhapHangg entity = new PhieuNhapHangg();
                entity.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
                entity.setMaNhanVien(rs.getString("MaNhanVien"));
                entity.setMaPhieuNhapHang(rs.getString("MaPhieuNhapHang"));
                entity.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                entity.setNgayNhap(rs.getString("NgayNhap"));
                entity.setTongTien(rs.getString("TongTien"));
                entity.setVatlieu(rs.getString("VatLieu"));
                entity.setSoPhieuNhap(rs.getString("SoPhieuNhap"));
                entity.setDiaDiem(rs.getString("DiaDiem"));
                entity.setDienDai(rs.getString("DienDai"));
                entity.setSoLuong(rs.getString("SoLuong"));
                entity.setDonGia(rs.getString("DonGia"));
                entity.setNguoiGiao(rs.getString("NguoiGiao"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setIsGhiChu(rs.getBoolean("isGhiChu"));
                
               
             
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
     public List<PhieuNhapHangg> selectByKeyword( String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }
    }
    

