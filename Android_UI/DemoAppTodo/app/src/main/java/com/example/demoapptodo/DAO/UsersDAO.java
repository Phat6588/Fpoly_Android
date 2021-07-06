package com.example.demoapptodo.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demoapptodo.Database.SQLiteManager;
import static com.example.demoapptodo.Utilities.Constants.*;

public class UsersDAO implements IUsers{

    SQLiteManager db;
    public UsersDAO(Context context){
        db = new SQLiteManager(context);
    }

    @Override
    public boolean login(String username, String password) {
        String query = " SELECT * FROM "+TABLE_USER+" " +
                "WHERE "+COLUMN_USERNAME+" = ?  ";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{username});
        cursor.moveToFirst();
        String pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
        // mã hóa
        cursor.close();
        return pass.equals(password);
    }
}
