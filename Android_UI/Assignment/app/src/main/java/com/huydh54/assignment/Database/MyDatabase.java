package com.huydh54.assignment.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(@Nullable Context context) {
        super(context, "MoneyLoverDatabase", null, 1);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table loai" +
                "(_id integer primary key autoincrement," +
                " Anh text," +
                " Ten text," +
                " PhanLoai text)";
        sqLiteDatabase.execSQL(sql);
        sql = "create table khoan" +
                "(_id integer primary key autoincrement," +
                " SoTien double, " +
                " Ngay text, " +
                " Vi text, " +
                " MaLoai integer not null, " +
                " foreign key (MaLoai) references loai(_id))";
        sqLiteDatabase.execSQL(sql);
        this.duLieuMacDinh(sqLiteDatabase,"load_chonloai_divay", "Đi vay", "Vay nợ");
        this.duLieuMacDinh(sqLiteDatabase,"load_chonloai_thuno", "Thu nợ", "Vay nợ");
        this.duLieuMacDinh(sqLiteDatabase,"load_chonloai_chovay", "Cho vay", "Vay nợ");
        this.duLieuMacDinh(sqLiteDatabase,"load_chonloai_trano", "Trả nợ", "Vay nợ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists loai");
        sqLiteDatabase.execSQL("drop table if exists khoan");
        onCreate(sqLiteDatabase);
    }

    public void duLieuMacDinh(SQLiteDatabase sqLiteDatabase, String anh, String ten, String phanLoai) {
        ContentValues values = new ContentValues();
        values.put("Anh", anh);
        values.put("Ten", ten);
        values.put("PhanLoai", phanLoai);
        sqLiteDatabase.insertOrThrow("loai", null, values);
    }
}
