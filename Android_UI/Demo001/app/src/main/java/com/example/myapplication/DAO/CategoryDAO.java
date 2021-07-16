package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.Database.MyDatabase;
import com.example.myapplication.Models.Category;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Utilities.Constants.*;

public class CategoryDAO implements ICategoryDAO{

    MyDatabase db;
    public CategoryDAO(Context ctx){
        db = MyDatabase.getInstance(ctx);
    }

    @Override
    public List<Category> get() {
        List<Category> list = new ArrayList<>();
        String q = "SELECT "+COLUMN_CATEGORY_ID+", " +
                ""+COLUMN_CATEGORY_NAME+" " +
                "FROM  "+TABLE_CATEGORY+" ";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, null);
        try {
            if (cursor.moveToFirst()){
                while (cursor.isAfterLast() == false){
                    Integer id = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
                    Category category = new Category(id, name);
                    list.add(category);
                    cursor.moveToNext();
                }
            }
        }catch(Exception e) {
            Log.e("Get Category error: ", e.getMessage());
        } finally {
            if (cursor!= null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }

    public void insert(Category category){
        SQLiteDatabase database =  db.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_NAME, category.getName());
            database.insertOrThrow(TABLE_CATEGORY,null, values);
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Insert Category error: ", e.getMessage());
        } finally {
            database.endTransaction();
        }
    }

    public void update(Category category){
        SQLiteDatabase database =  db.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_NAME, category.getName());
            database.update(TABLE_CATEGORY, values,
                    COLUMN_CATEGORY_ID + " = ?",
                    new String[]{String.valueOf(category.getId())});
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Update Category error: ", e.getMessage());
        } finally {
            database.endTransaction();
        }
    }

    public void delete(int categoryId){
        SQLiteDatabase database =  db.getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(TABLE_CATEGORY, COLUMN_CATEGORY_ID + " = ?",
                    new String[]{String.valueOf(categoryId)});
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Delete Category error: ", e.getMessage());
        } finally {
            database.endTransaction();
        }
    }

















}
