package com.example.demoslide56768.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demoslide56768.Database.MyDatabase;
import com.example.demoslide56768.Model.Laptop;
import com.example.demoslide56768.Model.User;

public class UserDAO implements IUserDAO{

    MyDatabase myDatabase;

    public UserDAO(Context context){
        myDatabase = new MyDatabase(context);
    }

    @Override
    public Boolean login(String _userName, String _password) {
        SQLiteDatabase database = myDatabase.getReadableDatabase();
        String sql = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{_userName, _password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}
