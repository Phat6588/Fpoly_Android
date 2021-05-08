package controller;

import android.content.Context;

import androidx.annotation.Nullable;
import helper.MyDbHelper;
import model.Clazz;

import java.util.List;

public class ClazzController {
    MyDbHelper myDbHelper;
    public ClazzController(@Nullable Context context){
        myDbHelper = new MyDbHelper(context, 6);
    }

    public void add(Clazz clazz){
        myDbHelper.addClazz(clazz);
    }

    public List<Clazz> get(){
        return myDbHelper.getClazz();
    }

    public Clazz get(String code){
        return myDbHelper.getClazz(code);
    }

    public int update(Clazz clazz){
        return myDbHelper.updateClazz(clazz);
    }

    public void delete(Clazz clazz){
        myDbHelper.deleteClazz(clazz);
    }
}
