package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.myapplication.Utilities.Constants.*;

public class MyDatabase extends SQLiteOpenHelper {

    private static MyDatabase dbInstance;
    public static synchronized MyDatabase getInstance(Context ctx){
        if (dbInstance == null){
            dbInstance = new MyDatabase(ctx);
        }
        return dbInstance;
    }

    private MyDatabase(Context context) {
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
                ""+COLUMN_INCOME_EXPENSE_CREATED_DATE+" INTEGER, " +
                ""+COLUMN_INCOME_EXPENSE_AMOUNT+" REAL, " +
                ""+COLUMN_INCOME_EXPENSE_CATEGORY_ID+" INTEGER, " +
                "FOREIGN KEY ("+COLUMN_INCOME_EXPENSE_CATEGORY_ID+") " +
                "REFERENCES "+TABLE_CATEGORY+" ("+COLUMN_CATEGORY_ID+")  )  ";
        db.execSQL(q);

        q = "INSERT INTO "+TABLE_CATEGORY+" ("+COLUMN_CATEGORY_NAME+")" +
                " VALUES ('Shopping'), ('Baverage'), ('Traveling'), ('Home'), ('Children') ";
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
                    String q = "ALTER TABLE "+TABLE_INCOME_EXPENSE+" ADD COLUMN "+COLUMN_FLAG+" INTEGER   ";
                    db.execSQL(q);

                    q = "INSERT INTO "+TABLE_CATEGORY+" ("+COLUMN_CATEGORY_NAME+")" +
                            " VALUES ('Category 1'), ('Category 2'), ('Category 3'), ('Category 4'), ('Category 5') ," +
                            " ('Category 6'), ('Category 7'), ('Category 8'), ('Category 9'), ('Category 10') ";
                    db.execSQL(q);
                    break;
                case 3:

                    String q3 = "INSERT INTO "+TABLE_CATEGORY+" ("+COLUMN_CATEGORY_NAME+")" +
                            " VALUES ('Category 11'), ('Category 12'), ('Category 13'), ('Category 14'), ('Category 15') ," +
                            " ('Category 16'), ('Category 17'), ('Category 18'), ('Category 19'), ('Category 20')," +
                            " ('Category 21'), ('Category 22'), ('Category 23'), ('Category 24'), ('Category 25')  ";
                    db.execSQL(q3);
                    break;
                default:
                    break;

            }
            upgrade++;
        }
    }


}
