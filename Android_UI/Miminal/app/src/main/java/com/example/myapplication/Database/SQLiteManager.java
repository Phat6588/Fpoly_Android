package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.myapplication.Utilities.Constants.*;

public class SQLiteManager extends SQLiteOpenHelper {


    public SQLiteManager(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE IF NOT EXISTS "+TABLE_USER+" ( "+COLUMN_USERNAME+" TEXT PRIMARY KEY, "+COLUMN_PASSWORD+" TEXT )  ";
        sqLiteDatabase.execSQL(query);

        query = "Create Table IF NOT EXISTS " + TABLE_TODO_TASKS +
                " ("
                + COLUMN_TASK_ID + " Text PRIMARY KEY, "
                + COLUMN_TASK_NAME + " Text "
                + ");";
        sqLiteDatabase.execSQL(query);

        query = "Create Table IF NOT EXISTS " + TABLE_TODO_ITEMS +
                " ("
                + COLUMN_ITEM_ID + " Text PRIMARY KEY, "
                + COLUMN_ITEM_STATUS + " Boolean, "
                + COLUMN_ITEM_NAME + " Text, "
                + COLUMN_ITEM_TASK_ID + " Text, "
                + "FOREIGN KEY ("+COLUMN_ITEM_TASK_ID+") REFERENCES "+TABLE_TODO_TASKS+"("+COLUMN_TASK_ID+")"
                + ");";
        sqLiteDatabase.execSQL(query);

        query = "INSERT INTO "+TABLE_USER+" VALUES('admin', '123') ";
        sqLiteDatabase.execSQL(query);

        sqLiteDatabase.execSQL("INSERT INTO "+TABLE_TODO_TASKS+" VALUES('123e4567-e89b-12d3-a456-556642440000', 'Homework')");
        sqLiteDatabase.execSQL("INSERT INTO "+TABLE_TODO_TASKS+" VALUES('123e4568-e89b-12d3-a456-556642440000', 'Learning')");
        sqLiteDatabase.execSQL("INSERT INTO "+TABLE_TODO_TASKS+" VALUES('123e4569-e89b-12d3-a456-556642440000', 'Training')");

        sqLiteDatabase.execSQL("INSERT INTO "+TABLE_TODO_ITEMS+" VALUES('123e4567-e89b-12d3-a456-556642440000', 1, 'Item 1', '123e4567-e89b-12d3-a456-556642440000')");
        sqLiteDatabase.execSQL("INSERT INTO "+TABLE_TODO_ITEMS+" VALUES('123e4568-e89b-12d3-a456-556642440000', 0, 'Item 2', '123e4567-e89b-12d3-a456-556642440000')");

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, final int oldVersion, final int newVersion) {
        int upgradeTo = oldVersion + 1;
        while (upgradeTo <= newVersion)
        {
            switch (upgradeTo)
            {
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
            upgradeTo++;
        }
    }
}
