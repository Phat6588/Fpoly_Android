package com.example.demoapptodo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demoapptodo.Database.SQLiteManager;
import com.example.demoapptodo.Models.Tasks;

import java.util.ArrayList;
import java.util.List;

import static com.example.demoapptodo.Utilities.Constants.*;

public class TasksDAO implements ITasks{

    SQLiteManager db;
    private Context context;
    public TasksDAO(Context _context){
        db = new SQLiteManager(_context);
        this.context = _context;
    }

    @Override
    public List<Tasks> get() {
        List<Tasks> list = new ArrayList<>();
        String query = "SELECT "+COLUMN_TASK_ID+", "+COLUMN_TASK_NAME+" FROM "+TABLE_TODO_TASKS+" ";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            String _id = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_ID));
            String _name = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
            Tasks tasks = new Tasks(_id, _name);
            list.add(tasks);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public Tasks get(String id) {
        String query = "SELECT "+COLUMN_TASK_ID+", "+COLUMN_TASK_NAME+" FROM "+TABLE_TODO_TASKS+" WHERE "+COLUMN_TASK_ID+" = ? ";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{id});
        try {
            cursor.moveToFirst();
            String _id = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_ID));
            String _name = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
            Tasks tasks = new Tasks(_id, _name);
            cursor.close();
            return tasks;
        } catch (Exception e){}
        return null;
    }

    @Override
    public boolean insert(Tasks tasks) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_ID, tasks.getId());
        values.put(COLUMN_TASK_NAME, tasks.getName());
        long row = database.insert(TABLE_TODO_TASKS, null, values);
        return row == ROW_EFFECTED;
    }

    @Override
    public boolean delete(String id) {
        // xoa ben bang items
        ItemsDAO dao = new ItemsDAO(context);
        dao.delete(id);
        // xoa bang tasks
        SQLiteDatabase database = db.getWritableDatabase();
        long row = database.delete(TABLE_TODO_TASKS," "+COLUMN_TASK_ID+" = ? ",
                                                new String[]{id});
        return row == ROW_EFFECTED;
    }
}
