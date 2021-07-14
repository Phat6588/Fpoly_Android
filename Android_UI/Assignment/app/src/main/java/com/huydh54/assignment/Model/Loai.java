package com.huydh54.assignment.Model;

public class Loai {
    private String anh;
    private String ten;
    private String phanLoai;

    public Loai(String anh, String ten, String phanLoai) {
        this.anh = anh;
        this.ten = ten;
        this.phanLoai = phanLoai;
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

    public String getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }
}
