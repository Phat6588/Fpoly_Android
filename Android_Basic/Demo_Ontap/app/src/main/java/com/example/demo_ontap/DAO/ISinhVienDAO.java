package com.example.demo_ontap.DAO;

import com.example.demo_ontap.Model.SinhVien;

import java.util.List;

public interface ISinhVienDAO {
    List<SinhVien> get();
    SinhVien get(String maSV);
    boolean insert(SinhVien sv);
    boolean update(SinhVien sv);
    boolean delete(String maSV);
}
