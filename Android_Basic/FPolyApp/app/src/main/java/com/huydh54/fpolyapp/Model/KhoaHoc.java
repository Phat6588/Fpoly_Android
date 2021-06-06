package com.huydh54.fpolyapp.Model;

public class KhoaHoc {
    private String ma;
    private String anh;
    private String thoiGian;
    private String ten;

    public KhoaHoc(String ma, String anh, String thoiGian, String ten) {
        this.ma = ma;
        this.anh = anh;
        this.thoiGian = thoiGian;
        this.ten = ten;
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

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
