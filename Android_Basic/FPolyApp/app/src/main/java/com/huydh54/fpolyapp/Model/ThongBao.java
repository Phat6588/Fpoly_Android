package com.huydh54.fpolyapp.Model;

public class ThongBao {
    private int anh;
    private String ten;

    public ThongBao(int anh, String ten) {
        this.anh = anh;
        this.ten = ten;
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
}
