package com.example.myapplication.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.myapplication.Database.SQLiteManager;
import static com.example.myapplication.Utilities.Constants.*;

public class UsersDAO implements IUsers {
    SQLiteManager db;
    public UsersDAO(Context context) {db = new SQLiteManager(context);}
    @Override
    public boolean login(String username, String password) {
        try {
            String query = " SELECT * FROM " + TABLE_USER + " " +
                    "WHERE " + COLUMN_USERNAME + " = ?  ";
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.rawQuery(query, new String[]{username});
            cursor.moveToFirst();
            String pass = cursor.getString(1);
            cursor.close();
            return pass.equals(password);
        } catch (Exception e) {
            Log.i("Error: ", e.getMessage());
        }
        return false;
    }
}
