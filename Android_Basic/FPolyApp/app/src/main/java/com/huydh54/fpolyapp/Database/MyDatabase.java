package com.huydh54.fpolyapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(@Nullable Context context) {
        super(context, "FpolyDatabase", null, 1);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table khoahoc" +
                "(Ma text primary key not null," +
                " Anh text," +
                " ThoiGian text," +
                " Ten text)";
        sqLiteDatabase.execSQL(sql);
        sql = "create table lophoc" +
                "(Ma text primary key not null," +
                " Khoa text," +
                " Nganh text)";
        sqLiteDatabase.execSQL(sql);
        sql = "create table sinhvien" +
                "(Ma text primary key not null," +
                " Anh text," +
                " Ten text," +
                " MaLop text not null, " +
                " foreign key (MaLop) references lophoc(Ma))";
        sqLiteDatabase.execSQL(sql);
        sql = "create table taikhoan" +
                "(TaiKhoan text primary key not null," +
                " MatKhau text," +
                " MaSV text not null unique, " +
                " foreign key (MaSV) references sinhvien(Ma))";
        sqLiteDatabase.execSQL(sql);
        sql = "create table thongbao" +
                "(_id integer primary key autoincrement," +
                " Anh integer, " +
                " ThongBao text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists sinhvien");
        sqLiteDatabase.execSQL("drop table if exists lophoc");
        sqLiteDatabase.execSQL("drop table if exists khoahoc");
        sqLiteDatabase.execSQL("drop table if exists taikhoan");
        sqLiteDatabase.execSQL("drop table if exists thongbao");
        onCreate(sqLiteDatabase);
    }
}
