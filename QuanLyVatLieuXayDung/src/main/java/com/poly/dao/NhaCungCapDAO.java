package com.poly.dao;

import com.poly.entity.NhaCungCap;
import com.poly.utils.JdbcUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO extends QuanLyVatLieuXayDungDAO<NhaCungCap, String> {

    String INSERT_SQL = "INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, DiaChi, SoDienThoai, Email) VALUES (?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE NhaCungCap SET TenNhaCungCap=?, DiaChi=?, SoDienThoai=?, Email=? WHERE MaNhaCungCap=?";
    String DELETE_SQL = "DELETE FROM NhaCungCap WHERE MaNhaCungCap=?";
    String SELECT_ALL_SQL = "SELECT * FROM NhaCungCap";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhaCungCap WHERE MaNhaCungCap=?";
    String SORT_DECS = "SELECT * FROM NhaCungCap WHERE isDelete = 0 ORDER BY MaNhaCungCap DESC";
    String SORT_ASC = "SELECT * FROM NhaCungCap WHERE isDelete = 0 ORDER BY MaNhaCungCap ASC";
    String FIND_ID_BY_NAME = "SELECT ID FROM NhaCungCap WHERE Email = ?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM NhaCungCap WHERE (Email LIKE ? )";

    @Override
    public void insert(NhaCungCap entity) {
        JdbcUtil.executeUpdate(INSERT_SQL,
                entity.getMaNhaCungCap(),
                entity.getTenNhaCungCap(),
                entity.getDiaChi(),
                entity.getSoDienThoai(),
                entity.getEmail());
    }

    @Override
    public void update(NhaCungCap entity) {
        JdbcUtil.executeUpdate(UPDATE_SQL,
                entity.getTenNhaCungCap(),
                entity.getDiaChi(),
                entity.getSoDienThoai(),
                entity.getEmail(),
                entity.getMaNhaCungCap());
    }

    @Override
    public void delete(String id) {
        JdbcUtil.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public NhaCungCap selectById(String id) {
        List<NhaCungCap> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhaCungCap> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhaCungCap> selectBySql(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtil.executeQuery(sql, args);
            while (rs.next()) {
                NhaCungCap entity = new NhaCungCap();
                entity.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
                entity.setTenNhaCungCap(rs.getString("TenNhaCungCap"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setSoDienThoai(rs.getString("SoDienThoai"));
                entity.setEmail(rs.getString("Email"));
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

    public List<NhaCungCap> selectByKeyword(String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }
}