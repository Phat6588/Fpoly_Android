package com.huydh54.assignment.Model;

public class TienTe {
    private int anh;
    private String ten;
    private String kyHieu;

    public TienTe(int anh, String ten, String kyHieu) {
        this.anh = anh;
        this.ten = ten;
        this.kyHieu = kyHieu;
    }

    public int getAnh() {
        return anh;
    }

    public void setAnh(int anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getKyHieu() {
        return kyHieu;
    }

    public void setKyHieu(String kyHieu) {
        this.kyHieu = kyHieu;
    }
}
