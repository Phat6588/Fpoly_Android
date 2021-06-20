package com.example.demo_ontap.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demo_ontap.Constants.AppConstant;
import com.example.demo_ontap.Database.SinhVienDB;
import com.example.demo_ontap.Model.SinhVien;

import java.util.ArrayList;
import java.util.List;
// Design pattern
public class SinhVienDAO implements ISinhVienDAO{

    SinhVienDB db;

    public SinhVienDAO(Context context){
        db = new SinhVienDB(context);
    }

    @Override
    public List<SinhVien> get() {
        List<SinhVien> list = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ AppConstant.TABLE_SINHVIEN +"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String maSV = cursor.getString(0);
            String hoTen = cursor.getString(1);
            Double diemTB = cursor.getDouble(2);
            SinhVien sv = new SinhVien(maSV, hoTen, diemTB);
            list.add(sv);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public SinhVien get(String maSV) {
        SinhVien sinhVien = null;
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database
                .rawQuery("SELECT * FROM "+AppConstant.TABLE_SINHVIEN+" WHERE "+AppConstant.COLUMN_MA_SINHVIEN+" = ?",new String[]{maSV});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String ma = cursor.getString(0);
            String hoTen = cursor.getString(1);
            Double diemTB = cursor.getDouble(2);
            sinhVien = new SinhVien(ma, hoTen, diemTB);
            cursor.moveToNext();
        }
        cursor.close();
        return sinhVien;
    }

    @Override
    public boolean insert(SinhVien sv) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppConstant.COLUMN_MA_SINHVIEN, sv.getMaSV());
        values.put(AppConstant.COLUMN_HO_TEN, sv.getHoTen());
        values.put(AppConstant.COLUMN_DIEM_TRUNG_BINH, sv.getDiemTrungBinh());
        long count = database.insert(AppConstant.TABLE_SINHVIEN, null, values);
        return count == AppConstant.EXPECTED_QUERY_RESULT;
    }

    @Override
    public boolean update(SinhVien sv) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] params = new String[]{sv.getMaSV()};
        values.put(AppConstant.COLUMN_HO_TEN, sv.getHoTen());
        values.put(AppConstant.COLUMN_DIEM_TRUNG_BINH, sv.getDiemTrungBinh());
        long count = database.update(AppConstant.TABLE_SINHVIEN, values, " "+AppConstant.COLUMN_MA_SINHVIEN+" = ?", params);
        return count == AppConstant.EXPECTED_QUERY_RESULT;
    }

    @Override
    public boolean delete(String maSV) {
        SQLiteDatabase database = db.getWritableDatabase();
        String[] params = new String[]{maSV};
        long count = database.delete(AppConstant.TABLE_SINHVIEN, " "+AppConstant.COLUMN_MA_SINHVIEN+" = ?", params);
        return count == AppConstant.EXPECTED_QUERY_RESULT;
    }
}
