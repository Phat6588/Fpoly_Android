package com.example.demoapptodo.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.demoapptodo.Utilities.Constants.*;

public class SQLiteManager extends SQLiteOpenHelper {

    public SQLiteManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS "+TABLE_USER+" ( "+COLUMN_USERNAME+" TEXT PRIMARY KEY, "+COLUMN_PASSWORD+" TEXT )  ";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS "+TABLE_TODO_TASKS+" ( "+COLUMN_TASK_ID+" TEXT PRIMARY KEY, "+COLUMN_TASK_NAME+" TEXT )  ";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS "+TABLE_TODO_ITEMS+" ( "+COLUMN_ITEM_ID+" TEXT PRIMARY KEY, "+COLUMN_ITEM_NAME+" TEXT, "+COLUMN_ITEM_STATUS+" REAL, "+COLUMN_ITEM_TASK_ID+" TEXT, FOREIGN KEY ( "+COLUMN_ITEM_TASK_ID+" ) REFERENCES "+TABLE_TODO_TASKS+" ( "+COLUMN_TASK_ID+" )   )  ";
        sqLiteDatabase.execSQL(query);

        query = "INSERT INTO "+TABLE_USER+" VALUES('admin', '123') ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // migration database
    @Override
    public void onUpgrade(SQLiteDatabase db, final int oldVersion, final int newVersion) {
        int upgrade = oldVersion + 1;
        while(upgrade <= newVersion){
            switch (upgrade){
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
            upgrade++;
        }
    }
}
