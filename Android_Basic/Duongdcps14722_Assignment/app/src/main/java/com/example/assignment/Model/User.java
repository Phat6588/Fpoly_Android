package com.example.assignment.Model;

public class User {
    private int id_user;
    private String taikhoan;
    private String matkhau;

    public User() {
    }

    public User(int id_user,String taikhoan, String matkhau) {
        this.id_user=id_user;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
