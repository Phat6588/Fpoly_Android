package com.huydh54.fpolyapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huydh54.fpolyapp.Database.MyDatabase;
import com.huydh54.fpolyapp.Model.TaiKhoan;

import java.util.ArrayList;

public class TaiKhoanDAO {
    MyDatabase myDatabase;

    public TaiKhoanDAO(Context context) {
        myDatabase = new MyDatabase(context);
    }

    public ArrayList<TaiKhoan> doc(){
        ArrayList<TaiKhoan> taiKhoanList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from taikhoan", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String taiKhoan = c.getString(0);
            String matKhau = c.getString(1);
            String maSV = c.getString(2);
            taiKhoanList.add(new TaiKhoan(taiKhoan, matKhau, maSV));
            c.moveToNext();
        }
        c.close();
        return taiKhoanList;
    }

    public void them(String taiKhoan, String matKhau, String maSV) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TaiKhoan", taiKhoan);
        values.put("MatKhau", matKhau);
        values.put("MaSV", maSV);
        db.insertOrThrow("taikhoan", null, values);
    }

    public void xoa(String taiKhoan){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("taikhoan", "TaiKhoan=?", new String[]{taiKhoan+""});
    }

    public void xoaToanBo(){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("taikhoan", null, null);
    }

    public void xoaTheoViTri(int viTri){
        ArrayList<String> taiKhoanList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from taikhoan", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String taiKhoanTam = c.getString(0);
            taiKhoanList.add(taiKhoanTam);
            c.moveToNext();
        }
        c.close();
        String taiKhoanXoa = taiKhoanList.get(viTri);
        db.delete("taikhoan", "TaiKhoan=?", new String[]{taiKhoanXoa+""});
    }

    public void capNhat(int viTri, String taiKhoan, String matKhau, String maSV){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TaiKhoan", taiKhoan);
        contentValues.put("MatKhau", matKhau);
        contentValues.put("MaSV", maSV);

        ArrayList<String> taiKhoanList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from taikhoan", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String taiKhoanTam2 = c.getString(0);
            taiKhoanList.add(taiKhoanTam2);
            c.moveToNext();
        }
        c.close();
        String taiKhoanXoa2 = taiKhoanList.get(viTri);
        sqLiteDatabase.update("taikhoan", contentValues, "TaiKhoan=?", new String[]{taiKhoanXoa2+""});
    }
}
