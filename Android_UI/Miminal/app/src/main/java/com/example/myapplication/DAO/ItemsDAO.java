package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.SQLiteManager;
import com.example.myapplication.Models.Items;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Utilities.Constants.*;

public class ItemsDAO implements IItems {
    SQLiteManager db;
    public ItemsDAO(Context context){
        db = new SQLiteManager(context);
    }
    @Override
    public List<Items> getByTaskId(String id) {
        List<Items> list = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database
                .rawQuery("SELECT * FROM "+TABLE_TODO_ITEMS+" WHERE "+COLUMN_ITEM_TASK_ID+" = ?",
                        new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String _id = cursor.getString(0);
            Boolean status = cursor.getInt(1) == 1;
            String name = cursor.getString(2);
            String taskId = cursor.getString(3);
            Items item = new Items(_id, name, status, taskId);
            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public Items get(String id) {
        Items item = null;
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database
                .rawQuery("SELECT * FROM "+TABLE_TODO_ITEMS+" WHERE "+COLUMN_ITEM_ID+" = ?",
                        new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String _id = cursor.getString(0);
            Boolean status = cursor.getInt(1) == 1;
            String name = cursor.getString(2);
            String taskId = cursor.getString(3);
            item = new Items(_id, name, status, taskId);
            cursor.moveToNext();
        }
        cursor.close();
        return item;
    }

    @Override
    public boolean insert(Items item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getName());
        values.put(COLUMN_ITEM_ID, item.getId());
        values.put(COLUMN_ITEM_STATUS, item.isStatus());
        values.put(COLUMN_ITEM_TASK_ID, item.getTaskId());
        long row = database.insert(TABLE_TODO_ITEMS, null, values);
        return row == 1;
    }

    @Override
    public boolean update(Items item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] params = new String[]{String.valueOf(item.getId())};
        values.put(COLUMN_ITEM_STATUS, item.isStatus() == true ? 1 : 0);
        int row = database.update(TABLE_TODO_ITEMS, values, ""+COLUMN_ITEM_ID+" = ?", params);
        return row == 1;
    }

    @Override
    public boolean delete(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        String[] params = new String[]{String.valueOf(id)};
        int row = database.delete(TABLE_TODO_ITEMS, ""+COLUMN_ITEM_ID+" = ?", params);
        return row == 1;
    }
}
