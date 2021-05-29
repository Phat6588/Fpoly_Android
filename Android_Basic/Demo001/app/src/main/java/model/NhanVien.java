package model;

public class NhanVien {

    private String maNhanVien;
    private String hoTen;
    private String hinhAnh;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String hoTen, String hinhAnh) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.hinhAnh = hinhAnh;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
