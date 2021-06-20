package com.example.demo_ontap.Model;

public class SinhVien {
    private String maSV, hoTen;
    private double diemTrungBinh;

    public SinhVien() {
    }

    public SinhVien(String maSV, String hoTen, double diemTrungBinh) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.diemTrungBinh = diemTrungBinh;
    }

    public String getXepLoai(){
        if (this.diemTrungBinh >= 9.0){
            return "Xuất sắc";
        } else if(this.diemTrungBinh >= 8.0){
            return "Giỏi";
        } else if(this.diemTrungBinh >= 7.0){
            return "Khá";
        } else {
            return "Trung bình";
        }
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }
}
