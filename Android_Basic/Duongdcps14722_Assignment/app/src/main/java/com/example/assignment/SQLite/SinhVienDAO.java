package com.example.assignment.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignment.Model.SinhVien;

import java.util.ArrayList;

public class SinhVienDAO {
    private SQLiteDatabase db;
    MyDatabase myDatabase;
    public SinhVienDAO(Context c){
        myDatabase=new MyDatabase(c);
    }
    public void ThemSV(String tensv,String ngaysinh,int malop){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("tensv",tensv);
        values.put("ngaysinh",ngaysinh);
        values.put("malop",malop);
        db.insert("SV",null,values);
    }

    public void XoaSV(int masv){
        db=myDatabase.getWritableDatabase();
        String x[]=new String[]{masv+""};
        db.delete("SV","_id=?",x);
    }

    public void SuaSV(int masv,String tensv,String ngaysinh,int malop){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        String x[]=new String[]{masv+""};
        values.put("tensv",tensv);
        values.put("ngaysinh",ngaysinh);
        values.put("malop",malop);
        db.update("SV",values,"_id=?",x);
    }
    public ArrayList<SinhVien> getAllSV(){
        db=myDatabase.getReadableDatabase();
        ArrayList<SinhVien> dssv = new ArrayList<>();
        Cursor c = db.rawQuery("select * from SV",null);
        if(c.moveToFirst())
        {
            do{
                SinhVien x=new SinhVien();
                x.setId_sv(c.getInt(0));
                x.setTen_sv(c.getString(1));
                x.setNgay_sinh_sv(c.getString(2));
                x.setId_lopsv(c.getInt(3));
                dssv.add(x);
            }while (c.moveToNext());
        }
        return dssv;
    }
}
