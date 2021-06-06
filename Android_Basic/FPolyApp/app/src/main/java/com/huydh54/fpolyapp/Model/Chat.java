package com.huydh54.fpolyapp.Model;

public class Chat {
    private int anh;
    private String ten;

    public Chat(int anh, String ten) {
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
