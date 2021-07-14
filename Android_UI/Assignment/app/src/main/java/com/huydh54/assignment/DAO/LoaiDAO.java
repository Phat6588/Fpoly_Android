package com.huydh54.assignment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huydh54.assignment.Database.MyDatabase;
import com.huydh54.assignment.Model.Loai;

import java.util.ArrayList;
import java.util.List;

public class LoaiDAO {
    MyDatabase myDatabase;

    public LoaiDAO(Context context) {
        myDatabase = new MyDatabase(context);
    }

    public ArrayList<Loai> doc(){
        ArrayList<Loai> loaiList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from loai", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String anh = c.getString(1);
            String ten = c.getString(2);
            String phanLoai = c.getString(3);
            loaiList.add(new Loai(anh, ten, phanLoai));
            c.moveToNext();
        }
        c.close();
        return loaiList;
    }

    public ArrayList<Loai> docThuChi(boolean thuChi){
        ArrayList<Loai> loaiThuList = new ArrayList<>();
        ArrayList<Loai> loaiChiList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from loai", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String anh = c.getString(1);
            String ten = c.getString(2);
            String phanLoai = c.getString(3);
            if(phanLoai.equals("Loại thu")) {
                loaiThuList.add(new Loai(anh, ten, phanLoai));
            }else if(phanLoai.equals("Loại chi")){
                loaiChiList.add(new Loai(anh, ten, phanLoai));
            }
            c.moveToNext();
        }
        c.close();
        if(thuChi == true) {
            return loaiThuList;
        }else{
            return loaiChiList;
        }
    }

    public ArrayList<Integer> docViTri(boolean thuChi){
        ArrayList<Loai> loaiList = new ArrayList<>();
        ArrayList<Integer> dsViTriThu = new ArrayList<>();
        ArrayList<Integer> dsViTriChi = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from loai", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String anh = c.getString(1);
            String ten = c.getString(2);
            String phanLoai = c.getString(3);
            loaiList.add(new Loai(anh, ten, phanLoai));
            c.moveToNext();
        }
        c.close();
        for(int i=0; i<loaiList.size(); i++){
            if(loaiList.get(i).getPhanLoai().equals("Loại thu")){
                dsViTriThu.add(i);
            }else if(loaiList.get(i).getPhanLoai().equals("Loại chi")){
                dsViTriChi.add(i);
            }
        }
        if(thuChi == true) {
            return dsViTriThu;
        }else{
            return dsViTriChi;
        }
    }

    public void them(String anh, String ten, String phanLoai) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Anh", anh);
        values.put("Ten", ten);
        values.put("PhanLoai", phanLoai);
        db.insertOrThrow("loai", null, values);
    }

    public void xoa(int ma){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("loai", "_id=?", new String[]{ma+""});
    }

    public void xoaToanBo(){
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete("loai", null, null);
    }

    public void xoaTheoViTri(int viTri){
        ArrayList<String> loaiList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from loai", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maTam = c.getInt(0);
            loaiList.add(maTam+"");
            c.moveToNext();
        }
        c.close();
        String maXoa = loaiList.get(viTri);
        db.delete("loai", "_id=?", new String[]{maXoa+""});
    }

    public Loai timTheoMa(int ma){
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from loai", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maTam = c.getInt(0);
            if(maTam == ma){
                String anh = c.getString(1);
                String ten = c.getString(2);
                String phanLoai = c.getString(3);
                Loai ketQua = new Loai(anh, ten, phanLoai);
                return ketQua;
            }
            c.moveToNext();
        }
        c.close();
        Loai ketQua2 = new Loai("Không tìm thấy!", "Không tìm thấy!", "Không tìm thấy!");
        return ketQua2;
    }

    public int getMa(int viTri){
        ArrayList<Integer> loaiList = new ArrayList<>();
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor c = db.rawQuery("select * from loai", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maTam = c.getInt(0);
            loaiList.add(maTam);
            c.moveToNext();
        }
        c.close();
        return loaiList.get(viTri);
    }

    public void capNhat(int viTri, String anh, String ten, String phanLoai){
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Anh", anh);
        contentValues.put("Ten", ten);
//        contentValues.put("PhanLoai", phanLoai);
        contentValues.put("PhanLoai", phanLoai);

        ArrayList<String> loaiList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from loai", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maTam2 = c.getString(0);
            loaiList.add(maTam2);
            c.moveToNext();
        }
        c.close();
        String maXoa2 = loaiList.get(viTri);
        sqLiteDatabase.update("loai", contentValues, "_id=?", new String[]{maXoa2+""});
    }
}
