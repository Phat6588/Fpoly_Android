package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.SQLiteManager;
import com.example.myapplication.Models.Tasks;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Utilities.Constants.*;

public class TasksDAO implements ITasks {
    SQLiteManager db;
    public TasksDAO(Context context){
        db = new SQLiteManager(context);
    }
    @Override
    public List<Tasks> get() {
        List<Tasks> list = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_TODO_TASKS+"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
            Tasks task = new Tasks(id, name);
            list.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public Tasks get(int id) {
        Tasks task = null;
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database
                .rawQuery("SELECT * FROM "+TABLE_TODO_TASKS+" WHERE "+COLUMN_TASK_ID+" = ?",
                        new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String _id = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
            task = new Tasks(_id, name);
            cursor.moveToNext();
        }
        cursor.close();
        return task;
    }

    @Override
    public boolean insert(Tasks item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, item.getName());
        values.put(COLUMN_TASK_ID, item.getId());
        long row = database.insert(TABLE_TODO_TASKS, null, values);
        return row == 1;
    }

    @Override
    public boolean update(Tasks item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] params = new String[]{String.valueOf(item.getId())};
        values.put(COLUMN_TASK_NAME, item.getName());
        int row = database.update(TABLE_TODO_TASKS, values, ""+COLUMN_TASK_ID+" = ?", params);
        return row == 1;
    }

    @Override
    public boolean delete(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        String[] params = new String[]{String.valueOf(id)};
        int row = database.delete(TABLE_TODO_TASKS, ""+COLUMN_TASK_ID+" = ?", params);
        return row == 1;
    }

}
