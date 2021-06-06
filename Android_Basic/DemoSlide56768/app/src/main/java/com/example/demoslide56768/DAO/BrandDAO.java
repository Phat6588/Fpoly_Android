package com.example.demoslide56768.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demoslide56768.Database.MyDatabase;
import com.example.demoslide56768.Model.Brand;
import com.example.demoslide56768.Model.Laptop;

import java.util.ArrayList;
import java.util.List;

public class BrandDAO implements IBrandDAO{

    MyDatabase myDatabase;

    public BrandDAO(Context context){
        myDatabase = new MyDatabase(context);
    }

    @Override
    public List<Brand> get() {
        List<Brand> list = new ArrayList<>();
        SQLiteDatabase database = myDatabase.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM BRAND", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String brandId = cursor.getString(0);
            String brandName = cursor.getString(1);
            Brand brand = new Brand(brandId, brandName);
            list.add(brand);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
