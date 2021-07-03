package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<List<String>>
        {

    private Button button, button2, button3;
    private TextView textView, textView2, textView3;

    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        loaderManager = LoaderManager.getInstance(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                LoaderManager.LoaderCallbacks<List<String>> loaderCallbacks = MainActivity.this;
                Loader<List<String>> loader =
                        loaderManager.initLoader(1, null, loaderCallbacks);
                loader.forceLoad();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id,  Bundle args) {
        if (id == 1){
            return new MyAsyncTaskLoader(MainActivity.this);
        }
        throw new RuntimeException("");
    }

    @Override
    public void onLoadFinished( Loader<List<String>> loader, List<String> data) {
        this.loaderManager.destroyLoader(loader.getId());
        // hien thi du lieu tra ve
        StringBuilder sb = new StringBuilder();
        for (String s: data) {
            sb.append(s +" ");
        }
        textView.setText(sb.toString());
    }

    @Override
    public void onLoaderReset( Loader<List<String>> loader) {
        textView.setText("");
    }
}