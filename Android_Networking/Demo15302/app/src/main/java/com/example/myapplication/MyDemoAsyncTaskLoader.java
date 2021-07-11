package com.example.myapplication;

import android.content.Context;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class MyDemoAsyncTaskLoader
        extends AsyncTaskLoader<String> {

    public MyDemoAsyncTaskLoader(Context context){
        super(context);
    }

    @Override
    public String loadInBackground() {
        SystemClock.sleep(8000);
        return "Xin chao Loader";
    }
}
