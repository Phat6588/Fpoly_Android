package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DemoAsyncTaskActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<String>,
                Loader.OnLoadCanceledListener<String>{

    private Button button8, button9;
    private TextView textView7, textView6;

    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_async_task);

        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);

        loaderManager = LoaderManager.getInstance(this);

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MyDemoAsyncTask myDemoAsyncTask = new MyDemoAsyncTask(DemoAsyncTaskActivity.this);
//                myDemoAsyncTask.execute();
                onButtonClick();
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClick();
            }
        });
    }

    private void onCancelClick(){
        Loader<Object> loader = this.loaderManager.getLoader(1);
        if (loader!=null) loader.cancelLoad();
    }

    private void onButtonClick(){
        textView6.setText("");
        LoaderManager.LoaderCallbacks<String> loaderCallbacks = this;
        Loader<String> loader = loaderManager.initLoader(1, null, loaderCallbacks);
        loader.forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new MyDemoAsyncTaskLoader(DemoAsyncTaskActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        this.loaderManager.destroyLoader(loader.getId());
        textView6.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        textView6.setText("");
    }

    @Override
    public void onLoadCanceled(Loader<String> loader) {
        this.loaderManager.destroyLoader(loader.getId());
    }
}