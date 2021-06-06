package com.huydh54.fpolyapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huydh54.fpolyapp.Database.MyDatabase;
import com.huydh54.fpolyapp.Model.KhoaHoc;

import java.util.ArrayList;

public class KhoaHocDAO {
    MyDatabase myDatabase;

    public KhoaHocDAO(Context context) {
        myDatabase = new MyDatabase(context);
    }

    public ArrayList<KhoaHoc> doc(){
        ArrayList<KhoaHoc> khoaHocList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from khoahoc", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String ma = c.getString(0);
            String anh = c.getString(1);
            String thoiGian = c.getString(2);
            String ten = c.getString(3);
            khoaHocList.add(new KhoaHoc(ma, anh, thoiGian, ten));
            c.moveToNext();
        }
        c.close();
        return khoaHocList;
    }

    public void them(String ma, String anh, String thoiGian, String ten) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Ma", ma);
        values.put("Anh", anh);
        values.put("ThoiGian", thoiGian);
        values.put("Ten", ten);
        db.insert("khoahoc", null, values);
    }

    public void xoa(String ma){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("khoahoc", "Ma=?", new String[]{ma+""});
    }

    public void xoaToanBo(){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("khoahoc", null, null);
    }

    public void xoaTheoViTri(int viTri){
        ArrayList<String> khoaHocList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from khoahoc", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maTam = c.getString(0);
            khoaHocList.add(maTam);
            c.moveToNext();
        }
        c.close();
        String maXoa = khoaHocList.get(viTri);
        db.delete("khoahoc", "Ma=?", new String[]{maXoa+""});
    }

    public void capNhat(int viTri, String ma, String anh, String thoiGian, String ten){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Ma", ma);
        contentValues.put("Anh", anh);
        contentValues.put("ThoiGian", thoiGian);
        contentValues.put("Ten", ten);

        ArrayList<String> khoaHocList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from khoahoc", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maTam2 = c.getString(0);
            khoaHocList.add(maTam2);
            c.moveToNext();
        }
        c.close();
        String maXoa2 = khoaHocList.get(viTri);
        sqLiteDatabase.update("khoahoc", contentValues, "Ma=?", new String[]{maXoa2+""});
    }
}


// String query = "SELECT * FROM "+TABLE_LOGIN+" WHERE "+COLUMN_NAME+" = ?";
// Cursor c = db.rawQuery(query, new String[]{ name });