package com.huydh54.fpolyapp.Model;

public class SinhVien {
    private String ma;
    private String anh;
    private String ten;
    private String maLop;

    public SinhVien(String ma, String anh, String ten, String maLop) {
        this.ma = ma;
        this.anh = anh;
        this.ten = ten;
        this.maLop = maLop;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLop() {
        return maLop;
    }

    public void setLop(String lop) {
        this.maLop = maLop;
    }
}
