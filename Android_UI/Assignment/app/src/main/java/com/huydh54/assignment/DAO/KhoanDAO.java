package com.huydh54.assignment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huydh54.assignment.Database.MyDatabase;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class KhoanDAO {
    MyDatabase myDatabase;

    public KhoanDAO(Context context) {
        myDatabase = new MyDatabase(context);
    }

    public ArrayList<Khoan> doc(){
        ArrayList<Khoan> khoanList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from khoan", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            Double soTien = c.getDouble(1);
            String ngay = c.getString(2);
            String vi = c.getString(3);
            int maLoai = c.getInt(4);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = new Date();
//            try {
//                date =  dateFormat.parse(ngay);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            khoanList.add(new Khoan(soTien, ngay, vi, maLoai));
            c.moveToNext();
        }
        c.close();
        return khoanList;
    }

    public void them(double soTien, String ngay, String vi, int maLoai) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SoTien", soTien);
        values.put("Ngay", ngay);
        values.put("Vi", vi);
        values.put("MaLoai", maLoai);
        db.insertOrThrow("khoan", null, values);
    }

    public int getMa(int viTri){
        ArrayList<Integer> khoanList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from khoan", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maTam = c.getInt(0);
            khoanList.add(maTam);
            c.moveToNext();
        }
        c.close();
        return khoanList.get(viTri);
    }

    public void xoa(int ma){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("khoan", "_id=?", new String[]{ma+""});
    }

    public void xoaToanBo(){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("khoan", null, null);
    }

    public void xoaTheoViTri(int viTri){
        ArrayList<String> khoanList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from khoan", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maTam = c.getInt(0);
            khoanList.add(maTam+"");
            c.moveToNext();
        }
        c.close();
        String maXoa = khoanList.get(viTri);
        db.delete("khoan", "_id=?", new String[]{maXoa+""});
    }

    public Khoan timTheoMa(int ma){
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from khoan", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maTam = c.getInt(0);
            if(maTam == ma){
                double soTien = c.getDouble(1);
                String ngay = c.getString(2);
                String vi = c.getString(3);
                int maLoai = c.getInt(4);
                Khoan ketQua = new Khoan(soTien, ngay, vi, maLoai);
                return ketQua;
            }
            c.moveToNext();
        }
        c.close();
        Khoan ketQua2 = new Khoan(
                0,
                "Không tìm thấy!",
                "Không tìm thấy!",
                -1
                );
        return ketQua2;
    }

    public void capNhat(int viTri, double soTien, String ngay, String vi, int maLoai){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SoTien", soTien);
        contentValues.put("Ngay", ngay);
        contentValues.put("Vi", vi);
        contentValues.put("MaLoai", maLoai);

        ArrayList<String> khoanList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from khoan", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maTam2 = c.getString(0);
            khoanList.add(maTam2);
            c.moveToNext();
        }
        c.close();
        String maXoa2 = khoanList.get(viTri);
        sqLiteDatabase.update("khoan", contentValues, "_id=?", new String[]{maXoa2+""});
    }
}
