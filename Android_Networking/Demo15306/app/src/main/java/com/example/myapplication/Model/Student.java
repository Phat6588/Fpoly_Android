package com.example.myapplication.Model;

public class Student {
    private String ten;
    private Double luong, thuNhap;

    public Student() {
    }

    public Student(String ten, Double luong, Double thuNhap) {
        this.ten = ten;
        this.luong = luong;
        this.thuNhap = thuNhap;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Double getLuong() {
        return luong;
    }

    public void setLuong(Double luong) {
        this.luong = luong;
    }

    public Double getThuNhap() {
        return thuNhap;
    }

    public void setThuNhap(Double thuNhap) {
        this.thuNhap = thuNhap;
    }
}
