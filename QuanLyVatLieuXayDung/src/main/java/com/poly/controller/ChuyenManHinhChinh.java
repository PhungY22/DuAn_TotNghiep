/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.controller;

import com.poly.bean.MenuBean;
//import com.poly.model.HoaDonChiTietJPanel;
import com.poly.model.HoaDonJPanel;
import com.poly.model.KhachHangJPanel;
import com.poly.model.LoaiSanPhamJPanel;
import com.poly.model.NhanVienJPanel;
//import com.poly.model.PhieuNhapHangJPanel;
import com.poly.model.SanPhamJPanel;
import com.poly.model.ThongKeJPanel;
import com.poly.model.TrangChuJPanel;
import com.poly.model.VoucherJPanel;
import com.sun.tools.javac.Main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nhu Y
 */
public class ChuyenManHinhChinh {
    private JPanel pnlView;
    private String kindSelected = "";
    
    public ChuyenManHinhChinh (JPanel pnlView) {
        this.pnlView = pnlView;
    }

    public void setDashboard(JPanel pnlItem, JLabel lblItem) {
        kindSelected = "TrangChu";
        JPanel node = new TrangChuJPanel();
        pnlView.removeAll();
        pnlView.validate();
        pnlView.repaint();
        pnlView.setLayout(new BorderLayout());
        pnlView.add(node);
        pnlView.validate();
        pnlView.repaint();
    }
    


  public void setEventMenu(List<MenuBean> listMenu) {
        for (MenuBean item : listMenu) {
            item.getbtn().addActionListener(new MenuEvent(item.getKind(), item.getbtn()));
        }
    }

    public void setDashBoaed(JPanel pnlItem) {
        kindSelected = "TrangChu";
        JPanel node = new TrangChuJPanel();
        pnlView.removeAll();
        pnlView.validate();
        pnlView.repaint();
        pnlView.setLayout(new BorderLayout());
        pnlView.add(node);
        pnlView.validate();
        pnlView.repaint();
    }

    public void setViews(JPanel node) {
        pnlView.removeAll();
        pnlView.validate();
        pnlView.repaint();
        pnlView.setLayout(new BorderLayout());
        pnlView.add(node);
        pnlView.validate();
        pnlView.repaint();
    }


    public class MenuEvent implements ActionListener {

        private JPanel node;
        private String kind;
        private JButton btn;
        private Main none;

        public MenuEvent(String kind, JButton btn) {
            this.kind = kind;
            this.btn = btn;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (kind) {
                case "ManHinhChinh":
                   none = new Main();
                    break;
                case "NhanVien":
                  node = new NhanVienJPanel();
                setViews(node);
                break;
                case "KhachHang":
                  node = new KhachHangJPanel();
            setViews(node);
                  break;
                case "SanPham":
                  node = new SanPhamJPanel();
                    setViews(node);
                    break;
                 case "LoaiSanPham":
                  node = new LoaiSanPhamJPanel();
                    setViews(node);
                    break;
                  //case "PhieuNhapHang": 
                   // node = new PhieuNhapHangJPanel();
                    //setViews(node);
                    //break;
                case "HoaDon":
                    node = new HoaDonJPanel();
                    setViews(node);
                  break;
              //case "HoaDonChiTiet":
                  //node = new HoaDonChiTietJPanel();
                   // setViews(node);
                   // break;
                case "Voucher":
                    node = new VoucherJPanel();
                    setViews(node);
                    break;
                case "ThongKe":
                    node = new ThongKeJPanel();
                    setViews(node);
                    break;
               
                default:
                    node = new TrangChuJPanel();
                    setViews(node);
                    break;
            }
        }

    }
}
