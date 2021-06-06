package com.huydh54.fpolyapp.Model;

public class TaiKhoan {
    private String tenTK;
    private String matKhau;
    private String maSV;

    public TaiKhoan(String tenTK, String matKhau, String maSV) {
        this.tenTK = tenTK;
        this.matKhau = matKhau;
        this.maSV = maSV;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }
}
