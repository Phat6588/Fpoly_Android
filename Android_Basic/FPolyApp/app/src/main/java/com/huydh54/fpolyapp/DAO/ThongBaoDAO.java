package com.huydh54.fpolyapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huydh54.fpolyapp.Database.MyDatabase;
import com.huydh54.fpolyapp.Model.SinhVien;
import com.huydh54.fpolyapp.Model.ThongBao;

import java.util.ArrayList;

public class ThongBaoDAO {
    MyDatabase myDatabase;

    public ThongBaoDAO(Context context) {
        myDatabase = new MyDatabase(context);
    }

    public ArrayList<ThongBao> doc(){
        ArrayList<ThongBao> thongBaoList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from thongbao", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int anh = c.getInt(1);
            String ten = c.getString(2);
            thongBaoList.add(new ThongBao(anh, ten));
            c.moveToNext();
        }
        c.close();
        return thongBaoList;
    }

    public void them(int anh, String ten) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Anh", anh);
        values.put("ThongBao", ten);
        db.insertOrThrow("thongbao", null, values);
    }

    public void xoa(int ma){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("thongbao", "_id=?", new String[]{ma+""});
    }

    public void xoaToanBo(){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("thongbao", null, null);
    }

    public void xoaTheoViTri(int viTri){
        ArrayList<String> thongBaoList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from thongbao", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maTam = c.getInt(0);
            thongBaoList.add(maTam+"");
            c.moveToNext();
        }
        c.close();
        String maXoa = thongBaoList.get(viTri);
        db.delete("thongbao", "_id=?", new String[]{maXoa+""});
    }
}
