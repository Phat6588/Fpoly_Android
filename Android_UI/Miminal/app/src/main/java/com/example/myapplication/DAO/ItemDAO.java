package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.SQLiteManager;
import com.example.myapplication.Models.TodoItem;
import com.example.myapplication.Models.TodoTask;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements InterfaceItem{

    SQLiteManager db;

    public ItemDAO(Context context){
        db = new SQLiteManager(context);
    }

    @Override
    public List<TodoItem> getByTaskId(int id) {
        List<TodoItem> list = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database
                .rawQuery("SELECT * FROM "+SQLiteManager.DB_TABLE_ITEMS+" WHERE "+SQLiteManager.COLUMN_TASK_ID+" = ?",
                        new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer _id = cursor.getInt(0);
            Boolean status = cursor.getInt(1) == 1;
            String name = cursor.getString(2);
            String color = cursor.getString(3);
            Integer taskId = cursor.getInt(4);
            TodoItem item = new TodoItem(_id, name, status, color, taskId);
            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public TodoItem get(int id) {
        TodoItem item = null;
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database
                .rawQuery("SELECT * FROM "+SQLiteManager.DB_TABLE_ITEMS+" WHERE "+SQLiteManager.COLUMN_ID+" = ?",
                        new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer _id = cursor.getInt(0);
            Boolean status = cursor.getInt(1) == 1;
            String name = cursor.getString(2);
            String color = cursor.getString(3);
            Integer taskId = cursor.getInt(4);
            item = new TodoItem(_id, name, status, color, taskId);
            cursor.moveToNext();
        }
        cursor.close();
        return item;
    }

    @Override
    public boolean insert(TodoItem item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteManager.COLUMN_NAME, item.getName());
        values.put(SQLiteManager.COLUMN_COLOR, item.getColor());
        values.put(SQLiteManager.COLUMN_STATUS, item.isStatus());
        values.put(SQLiteManager.COLUMN_TASK_ID, item.getTaskId());
        long row = database.insert(SQLiteManager.DB_TABLE_ITEMS, null, values);
        return row == 1;
    }

    @Override
    public boolean update(TodoItem item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] params = new String[]{String.valueOf(item.getId())};
        values.put(SQLiteManager.COLUMN_STATUS, item.isStatus() == true ? 1 : 0);
        int row = database.update(SQLiteManager.DB_TABLE_ITEMS, values, ""+SQLiteManager.COLUMN_ID+" = ?", params);
        return row == 1;
    }

    @Override
    public boolean delete(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        String[] params = new String[]{String.valueOf(id)};
        int row = database.delete(SQLiteManager.DB_TABLE_ITEMS, ""+SQLiteManager.COLUMN_ID+" = ?", params);
        return row == 1;
    }
}
