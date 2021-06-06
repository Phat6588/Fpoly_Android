package com.example.assignment.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.assignment.Model.Lop;
import com.example.assignment.Model.SinhVien;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(@Nullable Context context) {
        super(context, "quanlysinhvien", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_lop="create table Lop" +
                "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenlop TEXT" +
                " )";
        String table_sv="create table SV" +
                "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tensv TEXT," +
                "ngaysinh TEXT," +
                "malop INTEGER" +
                " )";
        String table_user="create table User" +
                "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "taikhoan TEXT," +
                "matkhau TEXT" +
                " )";

        db.execSQL(table_lop);
        db.execSQL(table_sv);
        db.execSQL(table_user);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable1="DROP TABLE IF EXISTS Lop";
        db.execSQL(dropTable1);
        String dropTable2="DROP TABLE IF EXISTS SV";
        db.execSQL(dropTable2);
        String dropTable3="DROP TABLE IF EXISTS User";
        db.execSQL(dropTable3);
        onCreate(db);
    }


}
