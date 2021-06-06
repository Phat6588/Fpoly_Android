package com.example.assignment.Model;

public class SinhVien {
    private int id_sv;
    private String ten_sv;
    private String ngay_sinh_sv;
    private int id_lopsv;

    public SinhVien() {
        super();
    }

    public SinhVien(int id_sv, String ten_sv, String ngay_sinh_sv ,int id_lopsv) {
        this.id_sv = id_sv;
        this.ten_sv = ten_sv;
        this.ngay_sinh_sv=ngay_sinh_sv;
        this.id_lopsv = id_lopsv;
    }

    public int getId_sv() {
        return id_sv;
    }

    public void setId_sv(int id_sv) {
        this.id_sv = id_sv;
    }

    public String getTen_sv() {
        return ten_sv;
    }

    public void setTen_sv(String ten_sv) {
        this.ten_sv = ten_sv;
    }

    public String getNgay_sinh_sv() {
        return ngay_sinh_sv;
    }

    public void setNgay_sinh_sv(String ngay_sinh_sv) {
        this.ngay_sinh_sv = ngay_sinh_sv;
    }

    public int getId_lopsv() {
        return id_lopsv;
    }

    public void setId_lopsv(int id_lop) {
        this.id_lopsv = id_lop;
    }
}
