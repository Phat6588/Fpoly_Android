package com.example.demoslide56768.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// CRUD: Create, Update, Read, Delete
public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(@Nullable Context context) {
        super(context, "LaptopDB", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // tao bang Brand
        String sql = "CREATE TABLE BRAND (BrandId Text primary key not null, " +
                "BrandName Text)";
        sqLiteDatabase.execSQL(sql);

        // tao bang Laptop
        sql = "CREATE TABLE LAPTOP (LaptopId Text primary key not null, " +
                "LaptopName Text, " +
                "LaptopPrice Real, " +
                "BrandId Text not null, " +
                "LaptopImage Blob, " +
                "foreign key (BrandId) references BRAND(BrandId))";
        sqLiteDatabase.execSQL(sql);

        // tao bang User
        sql = "CREATE TABLE USER (UserId Text primary key not null, " +
                "UserName Text, Password Text)";
        sqLiteDatabase.execSQL(sql);

        // insert du lieu mau
        String sqlInsert = "INSERT INTO USER VALUES('PS00001', 'admin', '123')";
        sqLiteDatabase.execSQL(sqlInsert);

        sqLiteDatabase.execSQL("INSERT INTO BRAND VALUES('0001', 'Dell')");
        sqLiteDatabase.execSQL("INSERT INTO BRAND VALUES('0002', 'HP')");
        sqLiteDatabase.execSQL("INSERT INTO BRAND VALUES('0003', 'Lenovo')");
        sqLiteDatabase.execSQL("INSERT INTO BRAND VALUES('0004', 'ASUS')");

        sqLiteDatabase.execSQL("INSERT INTO LAPTOP(LaptopId, LaptopName, LaptopPrice, BrandId) VALUES('0001', 'Dell001', 2000, '0001')");
        sqLiteDatabase.execSQL("INSERT INTO LAPTOP(LaptopId, LaptopName, LaptopPrice, BrandId) VALUES('0002', 'Dell001', 3000, '0001')");
        sqLiteDatabase.execSQL("INSERT INTO LAPTOP(LaptopId, LaptopName, LaptopPrice, BrandId) VALUES('0003', 'Dell001', 2500, '0001')");
        sqLiteDatabase.execSQL("INSERT INTO LAPTOP(LaptopId, LaptopName, LaptopPrice, BrandId) VALUES('0004', 'Lenovo001', 1500, '0003')");
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LAPTOP");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BRAND");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(sqLiteDatabase);
    }
}
