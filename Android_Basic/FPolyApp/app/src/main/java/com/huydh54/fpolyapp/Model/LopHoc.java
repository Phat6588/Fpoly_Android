package com.huydh54.fpolyapp.Model;

public class LopHoc {
    private String ma;
    private String khoa;
    private String nganh;

    public LopHoc(String ma, String khoa, String nganh) {
        this.ma = ma;
        this.khoa = khoa;
        this.nganh = nganh;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }
}
