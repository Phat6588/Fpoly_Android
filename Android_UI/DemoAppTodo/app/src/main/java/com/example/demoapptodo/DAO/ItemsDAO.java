package com.example.demoapptodo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demoapptodo.Database.SQLiteManager;
import com.example.demoapptodo.Models.Items;
import com.example.demoapptodo.Models.Tasks;

import java.util.ArrayList;
import java.util.List;

import static com.example.demoapptodo.Utilities.Constants.*;

public class ItemsDAO implements IItems{
    SQLiteManager db;
    public ItemsDAO(Context context){
        db = new SQLiteManager(context);
    }

    @Override
    public List<Items> getByTaskId(String taskId) {
        List<Items> list = new ArrayList<>();
        String query = "SELECT "+COLUMN_ITEM_ID+", "+COLUMN_ITEM_NAME+", "+COLUMN_ITEM_STATUS+", "+COLUMN_ITEM_TASK_ID+" FROM "+TABLE_TODO_ITEMS+" WHERE "+COLUMN_ITEM_TASK_ID+" = ? ";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{taskId});
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            String _id = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID));
            String _name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
            String _status = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_STATUS));
            String _taskId = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_TASK_ID));
            Items items = new Items(_id, _name, _taskId, _status.equals(STATUS_TRUE));
            list.add(items);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public Items get(String id) {
        String query = "SELECT "+COLUMN_ITEM_ID+", "+COLUMN_ITEM_NAME+", "+COLUMN_ITEM_STATUS+", "+COLUMN_ITEM_TASK_ID+" FROM "+TABLE_TODO_ITEMS+" WHERE "+COLUMN_ITEM_ID+" = ? ";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{id});
        try {
            cursor.moveToFirst();
            String _id = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID));
            String _name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
            String _status = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_STATUS));
            String _taskId = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_TASK_ID));
            Items items = new Items(_id, _name, _taskId, _status.equals(STATUS_TRUE));
            cursor.close();
            return items;
        } catch (Exception e){}
        return null;
    }

    @Override
    public boolean insert(Items items) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_ID, items.getId());
        values.put(COLUMN_ITEM_NAME, items.getName());
        values.put(COLUMN_ITEM_STATUS, items.getStatus() == true ? STATUS_TRUE : STATUS_FALSE);
        values.put(COLUMN_ITEM_TASK_ID, items.getTaskId());
        long row = database.insert(TABLE_TODO_ITEMS, null, values);
        return row == ROW_EFFECTED;
    }

    @Override
    public boolean update(Items items) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_STATUS, items.getStatus() == true ? STATUS_TRUE : STATUS_FALSE);
        long row = database.update(TABLE_TODO_ITEMS, values,
                " "+COLUMN_ITEM_ID+" = ? ",
                new String[]{items.getId()});
        return row == ROW_EFFECTED;
    }
}
