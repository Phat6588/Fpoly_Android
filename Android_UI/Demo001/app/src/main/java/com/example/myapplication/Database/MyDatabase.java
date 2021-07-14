package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.myapplication.Utilities.Constants.*;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE IF NOT EXISTS "+TABLE_CATEGORY+" " +
                "( "+COLUMN_CATEGORY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+COLUMN_CATEGORY_NAME+" TEXT )  ";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS "+TABLE_INCOME_EXPENSE+" " +
                "( "+COLUMN_INCOME_EXPENSE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+COLUMN_INCOME_EXPENSE_NAME+" TEXT, " +
                ""+COLUMN_INCOME_EXPENSE_DESCRIPTION+" TEXT, " +
                ""+COLUMN_INCOME_EXPENSE_CREATED_DATE+" TEXT, " +
                ""+COLUMN_INCOME_EXPENSE_AMOUNT+" REAL, " +
                ""+COLUMN_INCOME_EXPENSE_CATEGORY_ID+" INTEGER, " +
                "FOREIGN KEY ("+COLUMN_INCOME_EXPENSE_CATEGORY_ID+") " +
                "REFERENCES "+TABLE_CATEGORY+" ("+COLUMN_CATEGORY_ID+")  )  ";
        db.execSQL(q);

        q = "INSERT INTO "+TABLE_CATEGORY+" ("+COLUMN_CATEGORY_NAME+") VALUES ('Shopping'), ('Baverage'), ('Traveling'), ('Home'), ('Children') ";
        db.execSQL(q);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
