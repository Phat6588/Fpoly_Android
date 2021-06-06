package com.huydh54.fpolyapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huydh54.fpolyapp.Database.MyDatabase;
import com.huydh54.fpolyapp.Model.LopHoc;

import java.util.ArrayList;

public class LopHocDAO {
    MyDatabase myDatabase;

    public LopHocDAO(Context context) {
        myDatabase = new MyDatabase(context);
    }

    public ArrayList<LopHoc> doc(){
        ArrayList<LopHoc> lopHocList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from lophoc", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String ma = c.getString(0);
            String khoa = c.getString(1);
            String nganh = c.getString(2);
            lopHocList.add(new LopHoc(ma, khoa, nganh));
            c.moveToNext();
        }
        c.close();
        return lopHocList;
    }

    public ArrayList<String> docMaLop(){
        ArrayList<String> maLopList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from lophoc", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String ma = c.getString(0);
            maLopList.add(ma);
            c.moveToNext();
        }
        c.close();
        return maLopList;
    }

    public void them(String ma, String khoa, String nganh) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Ma", ma);
        values.put("Khoa", khoa);
        values.put("Nganh", nganh);
        db.insertOrThrow("lophoc", null, values);
    }

    public void xoa(String ma){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("lophoc", "Ma=?", new String[]{ma+""});
    }

    public void xoaToanBo(){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("lophoc", null, null);
    }

    public void xoaTheoViTri(int viTri){
        ArrayList<String> lopHocList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from lophoc", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maTam = c.getString(0);
            lopHocList.add(maTam);
            c.moveToNext();
        }
        c.close();
        String maXoa = lopHocList.get(viTri);
        db.delete("lophoc", "Ma=?", new String[]{maXoa+""});
    }

    public void capNhat(int viTri, String ma, String khoa, String nganh){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Ma", ma);
        contentValues.put("Khoa", khoa);
        contentValues.put("Nganh", nganh);

        ArrayList<String> lopHocList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from lophoc", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maTam2 = c.getString(0);
            lopHocList.add(maTam2);
            c.moveToNext();
        }
        c.close();
        String maXoa2 = lopHocList.get(viTri);
        sqLiteDatabase.update("lophoc", contentValues, "Ma=?", new String[]{maXoa2+""});
    }
}
