package com.huydh54.fpolyapp.Model;

import java.io.Serializable;

public class GhiChu implements Serializable {
    private String noiDung;
    private String tieuDe;

    public GhiChu(String noiDung, String tieuDe) {
        this.noiDung = noiDung;
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }
}
