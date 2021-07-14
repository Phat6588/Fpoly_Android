package com.huydh54.assignment.Model;

public class TuyChonKeHoach {
    int anh;
    String tieuDe;
    String noiDung;

    public TuyChonKeHoach(int anh, String tieuDe, String noiDung) {
        this.anh = anh;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
    }

    public int getAnh() {
        return anh;
    }

    public void setAnh(int anh) {
        this.anh = anh;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
