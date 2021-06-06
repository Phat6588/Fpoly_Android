package com.huydh54.fpolyapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huydh54.fpolyapp.Database.MyDatabase;
import com.huydh54.fpolyapp.Model.SinhVien;

import java.util.ArrayList;

public class SinhVienDAO {
    MyDatabase myDatabase;

    public SinhVienDAO(Context context) {
        myDatabase = new MyDatabase(context);
    }

    public ArrayList<SinhVien> doc(){
        ArrayList<SinhVien> sinhVienList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from sinhvien", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String ma = c.getString(0);
            String anh = c.getString(1);
            String ten = c.getString(2);
            String maLop = c.getString(3);
            sinhVienList.add(new SinhVien(ma, anh, ten, maLop));
            c.moveToNext();
        }
        c.close();
        return sinhVienList;
    }

    public ArrayList<String> docMaSinhVien(){
        ArrayList<String> maSVList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from sinhvien", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String ma = c.getString(0);
            maSVList.add(ma);
            c.moveToNext();
        }
        c.close();
        return maSVList;
    }

    public void them(String ma, String anh, String ten, String maLop) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Ma", ma);
        values.put("Anh", anh);
        values.put("Ten", ten);
        values.put("MaLop", maLop);
        db.insertOrThrow("sinhvien", null, values);
    }

    public void xoa(int ma){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("sinhvien", "Ma=?", new String[]{ma+""});
    }

    public void xoaToanBo(){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("sinhvien", null, null);
    }

    public void xoaTheoViTri(int viTri){
        ArrayList<String> sinhVienList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from sinhvien", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maTam = c.getString(0);
            sinhVienList.add(maTam);
            c.moveToNext();
        }
        c.close();
        String maXoa = sinhVienList.get(viTri);
        db.delete("sinhvien", "Ma=?", new String[]{maXoa+""});
    }

    public void capNhat(int viTri, String ma, String anh, String ten, String maLop){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Ma", ma);
        contentValues.put("Anh", anh);
        contentValues.put("Ten", ten);
        contentValues.put("MaLop", maLop);

        ArrayList<String> sinhVienList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from sinhvien", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maTam2 = c.getString(0);
            sinhVienList.add(maTam2);
            c.moveToNext();
        }
        c.close();
        String maXoa2 = sinhVienList.get(viTri);
        sqLiteDatabase.update("sinhvien", contentValues, "Ma=?", new String[]{maXoa2+""});
    }
}
