package com.huydh54.fpolyapp.Model;

public class CaiDat {
    private int hinh;
    private String tieuDe;

    public CaiDat(int hinh, String tieuDe) {
        this.hinh = hinh;
        this.tieuDe = tieuDe;
    }

    public CaiDat() {
        this.hinh = 0;
        this.tieuDe = "";
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }
}
