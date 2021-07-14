package com.huydh54.assignment.Model;

import java.util.Date;

public class Khoan {
    private double soTien;
    private String ngay;
    private String vi;
    private int maLoai;

    public Khoan(double soTien, String ngay, String vi, int maLoai) {
        this.soTien = soTien;
        this.ngay = ngay;
        this.vi = vi;
        this.maLoai = maLoai;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getVi() {
        return vi;
    }

    public void setVi(String vi) {
        this.vi = vi;
    }

    public int getMaLop() {
        return maLoai;
    }

    public void setMaLop(int maLoai) {
        this.maLoai = maLoai;
    }
}
