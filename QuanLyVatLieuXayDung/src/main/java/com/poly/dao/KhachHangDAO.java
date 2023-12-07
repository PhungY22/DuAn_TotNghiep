/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.dao;

import com.poly.entity.KhachHang;        
import com.poly.utils.JdbcUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
//    String UPDATE_SQL = "UPDATE KhachHang SET TenKhachHang =?,DiaChi =?,SoDienThoai=?,Email=?,GioiTinh=? WHERE MaKhachHang=?";
    String DELETE_SQL = "DELETE FROM KhachHang WHERE MaKhachHang=?";
    String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE MaKhachHang= ?";
    String SORT_DECS = "SELECT * FROM KhachHang WHERE isDelete = 0 ORDER BY MaKhachHang DESC";
    String SORT_ASC = "SELECT * FROM KhachHang WHERE isDelete = 0 ORDER BY MaKhachHang ASC";
    String FIND_ID_BY_NAME = "SELECT ID FROM KhachHang WHERE TenKhachHang = ?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM KhachHang WHERE (TenKhachHang LIKE ? )";
    private Object MaKhachHang;


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
    
   public void update(KhachHang entity) {
    String updateQuery = "UPDATE KhachHang SET TenKhachHang = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, GioiTinh = ? WHERE MaKhachHang = ?";
    
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=quanlydonoithat;encrypt=true;trustServerCertificate=true", "sa", "123");
         PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
        
        preparedStatement.setString(1, entity.getTenKhachHang());
        preparedStatement.setString(2, entity.getDiaChi());
        preparedStatement.setString(3, entity.getSoDienThoai());
        preparedStatement.setString(4, entity.getEmail());
        preparedStatement.setBoolean(5, entity.isGioiTinh());
        preparedStatement.setString(6, entity.getMaKhachHang());

        int rowsUpdated = preparedStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Dữ liệu đã được cập nhật thành công!");
        } else {
            System.out.println("Không có bản ghi nào được cập nhật!");
        }
    } catch (SQLException e) {
        // Xử lý lỗi một cách cụ thể hoặc ghi log
        e.printStackTrace();
    }
}

  
//    public void update(KhachHang entity) {
//        JdbcUtil.executeUpdate(UPDATE_SQL,
//                entity.getMaKhachHang(),
//                entity.getTenKhachHang(),
//                entity.getDiaChi(),
//                entity.getSoDienThoai(),
//                entity.getEmail(),
//                entity.isGioiTinh());
////            entity.setTenKhachHang(entity.getTenKhachHang());
////            entity.setDiaChi(entity.getDiaChi());
////            entity.setSoDienThoai(entity.getSoDienThoai());
////            entity.setEmail(entity.getEmail());
////            entity.setGioiTinh(entity.isGioiTinh());
////
////            JdbcUtil.executeUpdate(UPDATE_SQL,
////            entity.getMaKhachHang());
//    }
    
//     public void update(String MaKhachHang, String TenKhachHang, String DiaChi, String SoDienThoai, String Email, Boolean GioiTinh) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            // Kết nối đến cơ sở dữ liệu
////            connection = DriverManager.getConnection("jdbc:mysql://your_database_url", "username", "password");
//
//            // Chuẩn bị câu lệnh SQL để cập nhật dữ liệu
//            String updateQuery = "UPDATE your_table SET TenKhachHang = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, GioiTinh = ? WHERE MaKhachHang = ?";
//            preparedStatement = connection.prepareStatement(updateQuery);
//
//            // Thiết lập các tham số trong câu lệnh SQL
//            preparedStatement.setString(1, TenKhachHang);
//            preparedStatement.setString(2, DiaChi);
//            preparedStatement.setString(3, SoDienThoai);
//            preparedStatement.setString(4, Email);
//            preparedStatement.setBoolean(5, GioiTinh);
//            preparedStatement.setString(6, MaKhachHang);
//
//            // Thực hiện cập nhật dữ liệu
//            int rowsUpdated = preparedStatement.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("Dữ liệu đã được cập nhật thành công!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // Đóng các tài nguyên
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    
    @Override                                                                                                       
    public void delete(String MaKhachHang) {
    // Kiểm tra xem mã khách hàng có null hay không
        if (MaKhachHang == null) {
            return;
        }
//        KhachHang khachHang = findByMaKhachHang(maKhachHang);
//        if (khachHang == null) {
//         return;
//        }

    // Tạo câu lệnh SQL để xóa khách hàng
        String sql = "DELETE FROM KhachHang WHERE MaKhachHang = ?";

    // Thực thi câu lệnh SQL
        JdbcUtil.executeUpdate(sql, MaKhachHang);
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

//    private KhachHang findByMaKhachHang(String maKhachHang) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

//    @Override
//    public void update(KhachHang entity) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}