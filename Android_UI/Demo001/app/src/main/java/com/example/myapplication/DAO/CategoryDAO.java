package com.example.myapplication.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.MyDatabase;
import com.example.myapplication.Models.Category;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Utilities.Constants.*;

public class CategoryDAO implements ICategoryDAO{

    MyDatabase db;
    public CategoryDAO(Context ctx){
        db = new MyDatabase(ctx);
    }

    @Override
    public List<Category> get() {
        List<Category> list = new ArrayList<>();
        String q = "SELECT "+COLUMN_CATEGORY_ID+", " +
                ""+COLUMN_CATEGORY_NAME+" " +
                "FROM  "+TABLE_CATEGORY+" ";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Integer id = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
            Category category = new Category(id, name);
            list.add(category);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
