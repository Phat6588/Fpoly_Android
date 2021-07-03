package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "TODO.DB";

    public static String DB_TABLE_TASKS = "TASKS";
    public static String DB_TABLE_ITEMS = "ITEMS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_COLOR = "COLOR";
    public static final String COLUMN_TASK_ID = "TASKID";

    public SQLiteManager(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create Table IF NOT EXISTS " + DB_TABLE_TASKS +
                " ("
                + COLUMN_ID + " Integer PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " Text, "
                + COLUMN_COLOR + " Text "
                + ");";
        sqLiteDatabase.execSQL(query);

        query = "Create Table IF NOT EXISTS " + DB_TABLE_ITEMS +
                " ("
                + COLUMN_ID + " Integer PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STATUS + " Boolean, "
                + COLUMN_NAME + " Text, "
                + COLUMN_COLOR + " Text, "
                + COLUMN_TASK_ID + " Text, "
                + "FOREIGN KEY ("+COLUMN_TASK_ID+") REFERENCES "+DB_TABLE_TASKS+"("+COLUMN_ID+")"
                + ");";
        sqLiteDatabase.execSQL(query);


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
                    sqLiteDatabase.execSQL("INSERT INTO "+DB_TABLE_TASKS+"("+COLUMN_NAME+", "+COLUMN_COLOR+") VALUES('Homework', 'blue')");
                    sqLiteDatabase.execSQL("INSERT INTO "+DB_TABLE_TASKS+"("+COLUMN_NAME+", "+COLUMN_COLOR+") VALUES('School', 'red')");
                    sqLiteDatabase.execSQL("INSERT INTO "+DB_TABLE_TASKS+"("+COLUMN_NAME+", "+COLUMN_COLOR+") VALUES('Shopping', 'yellow')");

                    sqLiteDatabase.execSQL("INSERT INTO "+DB_TABLE_ITEMS+" ("+COLUMN_STATUS+", "+COLUMN_NAME+", "+COLUMN_COLOR+", "+COLUMN_TASK_ID+") VALUES(1, 'Item 1', 'blue', 1)");
                    sqLiteDatabase.execSQL("INSERT INTO "+DB_TABLE_ITEMS+"("+COLUMN_STATUS+", "+COLUMN_NAME+", "+COLUMN_COLOR+", "+COLUMN_TASK_ID+") VALUES(0, 'Item 2', 'yellow', 2)");
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
