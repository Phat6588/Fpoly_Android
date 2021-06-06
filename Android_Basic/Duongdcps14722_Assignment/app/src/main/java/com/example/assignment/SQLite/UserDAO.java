package com.example.assignment.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignment.Model.User;

import java.util.ArrayList;

public class UserDAO {
    private SQLiteDatabase db;
    MyDatabase myDatabase;
    public UserDAO(Context c){
        myDatabase=new MyDatabase(c);
    }

    public void ThemUser(String taikhoan,String matkhau){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("taikhoan",taikhoan);
        values.put("matkhau",matkhau);
        db.insert("User",null,values);
    }
    public void XoaUser(int mauser){
        db=myDatabase.getWritableDatabase();
        String x[]=new String[]{mauser+""};
        db.delete("User","_id=?",x);
    }
    public void SuaUser(String matkhau,int maUser){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        String x[]=new String[]{maUser+""};
        values.put("matkhau",matkhau);
        db.update("User",values,"_id=?",x);
    }
    public ArrayList<User> getAllUser(){
        db=myDatabase.getReadableDatabase();
        ArrayList<User> dsuser = new ArrayList<>();
        Cursor c = db.rawQuery("select * from User",null);
        if(c.moveToFirst())
        {
            do{
                User x=new User();
                x.setId_user(c.getInt(0));
                x.setTaikhoan(c.getString(1));
                x.setMatkhau(c.getString(2));
                dsuser.add(x);
            }while (c.moveToNext());
        }
        return dsuser;
    }
}
