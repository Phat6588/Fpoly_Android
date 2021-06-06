package com.example.assignment.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignment.Model.Lop;

import java.util.ArrayList;

public class LopDAO {
    private SQLiteDatabase db;
    public MyDatabase myDatabase;
    public LopDAO(Context c){
        myDatabase=new MyDatabase(c);
    }
    public void ThemLop(String tenlop){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("tenlop",tenlop);
        db.insert("Lop",null,values);
    }
    public void XoaLop(int malop){
        db=myDatabase.getWritableDatabase();
        String x[]=new String[]{malop+""};
        db.delete("Lop","_id=?",x);
    }
    public void SuaLop(int malop,String tenlop){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        String x[]=new String[]{malop+""};
        values.put("tenlop",tenlop);
        db.update("Lop",values,"_id=?",x);
    }
    public ArrayList<Lop> getAllLop(){
        db=myDatabase.getReadableDatabase();
        ArrayList<Lop> dslop = new ArrayList<Lop>();
        Cursor c = db.rawQuery("select * from Lop",null);
        if(c.moveToFirst())
        {
            do{
                Lop x=new Lop();
                x.setId_lop(c.getInt(0));
                x.setTen_lop(c.getString(1));
                dslop.add(x);
            }while (c.moveToNext());
        }
        return dslop;
    }
}
