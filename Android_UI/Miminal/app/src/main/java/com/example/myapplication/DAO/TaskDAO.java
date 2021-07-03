package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.SQLiteManager;
import com.example.myapplication.Models.TodoTask;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements InterfaceTask {

    SQLiteManager db;
    public TaskDAO(Context context){
        db = new SQLiteManager(context);
    }

    @Override
    public List<TodoTask> get() {
        List<TodoTask> list = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+SQLiteManager.DB_TABLE_TASKS+"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            String color = cursor.getString(2);
            TodoTask task = new TodoTask(id, name, color);
            list.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public TodoTask get(int id) {
        TodoTask task = null;
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database
                .rawQuery("SELECT * FROM "+SQLiteManager.DB_TABLE_TASKS+" WHERE "+SQLiteManager.COLUMN_ID+" = ?",
                        new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer _id = cursor.getInt(0);
            String name = cursor.getString(1);
            String color = cursor.getString(2);
            task = new TodoTask(_id, name, color);
            cursor.moveToNext();
        }
        cursor.close();
        return task;
    }

    @Override
    public boolean insert(TodoTask item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteManager.COLUMN_NAME, item.getName());
        values.put(SQLiteManager.COLUMN_COLOR, item.getColor());
        long row = database.insert(SQLiteManager.DB_TABLE_TASKS, null, values);
        return row == 1;
    }

    @Override
    public boolean update(TodoTask item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] params = new String[]{String.valueOf(item.getId())};
        values.put(SQLiteManager.COLUMN_NAME, item.getName());
        values.put(SQLiteManager.COLUMN_COLOR, item.getColor());
        int row = database.update(SQLiteManager.DB_TABLE_TASKS, values, ""+SQLiteManager.COLUMN_ID+" = ?", params);
        return row == 1;
    }

    @Override
    public boolean delete(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        String[] params = new String[]{String.valueOf(id)};
        int row = database.delete(SQLiteManager.DB_TABLE_TASKS, ""+SQLiteManager.COLUMN_ID+" = ?", params);
        return row == 1;
    }
}
