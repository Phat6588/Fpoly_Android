package com.example.myapplication;

import android.content.Context;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class MyAsyncTaskLoader extends AsyncTaskLoader<List<String>> {

    public MyAsyncTaskLoader(Context context){
        super(context);
    }

    @Override
    public List<String> loadInBackground() {
        List<String> list = new ArrayList<>();
        list.add("Xin chao");
        list.add("Buoi sang");
        list.add("Buoi toi");
        SystemClock.sleep(2000);
        return list;
    }
}
