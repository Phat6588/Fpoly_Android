package com.example.demoslide56768.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demoslide56768.Database.MyDatabase;
import com.example.demoslide56768.Model.Laptop;

import java.util.ArrayList;
import java.util.List;

public class LaptopDAO implements ILaptopDAO {

    MyDatabase myDatabase;

    public LaptopDAO(Context context){
        myDatabase = new MyDatabase(context);
    }

    @Override
    public List<Laptop> get() {
        List<Laptop> list = new ArrayList<>();
        SQLiteDatabase database = myDatabase.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM LAPTOP", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String laptopId = cursor.getString(0);
            String laptopName = cursor.getString(1);
            Double laptopPrice = cursor.getDouble(2);
            String brandId = cursor.getString(3);
            Laptop laptop = new Laptop(laptopId, laptopName, laptopPrice, brandId);
            list.add(laptop);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public Laptop get(String laptopId) {
        Laptop laptop = null;
        SQLiteDatabase database = myDatabase.getReadableDatabase();
        Cursor cursor = database
                .rawQuery("SELECT * FROM LAPTOP WHERE LAPTOPID = ?",
                        new String[]{laptopId});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            Double price = cursor.getDouble(2);
            String brandId = cursor.getString(3);
            laptop = new Laptop(id, name, price, brandId);
            cursor.moveToNext();
        }
        cursor.close();
        return laptop;
    }

    @Override
    public void insert(Laptop laptop) {

    }

    @Override
    public void update(Laptop laptop) {

    }

    @Override
    public void delete(String laptopId) {

    }
}
