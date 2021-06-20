package com.example.demo_ontap.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.demo_ontap.Constants.AppConstant;

import androidx.annotation.Nullable;

public class SinhVienDB extends SQLiteOpenHelper {
    public SinhVienDB(Context context) {
        super(context, AppConstant.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+ AppConstant.TABLE_SINHVIEN +"("+ AppConstant.COLUMN_MA_SINHVIEN +" text primary key not null, "+ AppConstant.COLUMN_HO_TEN +" text, "+ AppConstant.COLUMN_DIEM_TRUNG_BINH +" real)";
        db.execSQL(sql);

        db.execSQL("insert into sinhVien("+ AppConstant.COLUMN_MA_SINHVIEN +", "+ AppConstant.COLUMN_HO_TEN +", "+ AppConstant.COLUMN_DIEM_TRUNG_BINH +") values('PS12345', 'Khuất Đại An', 8.6)");
        db.execSQL("insert into sinhVien("+ AppConstant.COLUMN_MA_SINHVIEN +", "+ AppConstant.COLUMN_HO_TEN +", "+ AppConstant.COLUMN_DIEM_TRUNG_BINH +") values('PS12346', 'Khuất Nguyên', 8.9)");
        db.execSQL("insert into sinhVien("+ AppConstant.COLUMN_MA_SINHVIEN +","+ AppConstant.COLUMN_HO_TEN +", "+ AppConstant.COLUMN_DIEM_TRUNG_BINH +") values('PS12347', 'Lý Tiểu Long', 8.8)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion) {
            case 2:
                db.execSQL("create table lopHoc(maLop text primary key not null, tenLop text)");
                break;
            case 3:
                db.execSQL("alter table sinhVien add column diaChi text");
                break;
            default:
                break;
        }

    }
}
