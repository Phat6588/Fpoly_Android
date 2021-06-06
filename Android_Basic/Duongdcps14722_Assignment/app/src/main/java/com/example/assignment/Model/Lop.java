package com.example.assignment.Model;

public class Lop {
    private int id_lop;
    private String ten_lop;
    public Lop(){
    }
    public Lop(int id_lop,String ten_lop){
        this.id_lop=id_lop;
        this.ten_lop=ten_lop;
    }


    public int getId_lop() {
        return id_lop;
    }

    public String getTen_lop() {
        return ten_lop;
    }

    public void setId_lop(int id_lop) {
        this.id_lop = id_lop;
    }

    public void setTen_lop(String ten_lop) {
        this.ten_lop = ten_lop;
    }

}
