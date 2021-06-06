package com.huydh54.fpolyapp.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XDate {
    public static SimpleDateFormat sdf = new SimpleDateFormat();

    public static Date dinhDangNgay(String chuoiNgay, String chuoiDinhDang) throws ParseException {
        sdf.applyPattern(chuoiDinhDang);
        return sdf.parse(chuoiNgay);
    }

    public static Date dinhDangNgay(String chuoiNgay) throws ParseException {
        sdf.applyPattern("dd/MM/yyyy");
        return sdf.parse(chuoiNgay);
    }
}
