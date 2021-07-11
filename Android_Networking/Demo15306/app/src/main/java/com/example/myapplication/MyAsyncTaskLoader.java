package com.example.myapplication;


import android.content.Context;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import androidx.loader.content.AsyncTaskLoader;

public class MyAsyncTaskLoader extends AsyncTaskLoader<List<String>> {

    private String param1;
    private String param2;

    public MyAsyncTaskLoader(Context context, String param1, String param2) {
        super(context);
        this.param1 = param1;
        this.param2 = param2;
    }

    @Override
    public List<String> loadInBackground() {
        // Do something, for example:
        // - Download data from URL and parse it info Java object.
        // - Query data from Database into Java object.

        List<String> list = new ArrayList<String>();
        list.add("xin chao");
        list.add("buoi toi");
        list.add("xin chao buoi sang");

        SystemClock.sleep(2000); // 2 Seconds.

        return list;
    }
}