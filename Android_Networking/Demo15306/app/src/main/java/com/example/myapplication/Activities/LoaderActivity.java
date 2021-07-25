package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class LoaderActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<List<String>>,
        Loader.OnLoadCanceledListener<List<String>>, Loader.OnLoadCompleteListener<List<String>> {

    private TextView textView;
    private Button button;
    private LoaderManager loaderManager;

    private static final String KEY_PARAM1 = "SomeKey1";
    private static final String KEY_PARAM2 = "SomeKey2";
    private static final int LOADER_ID_USERACCOUNT = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.buttonLogin);
//        textView = (TextView) findViewById(R.id.textView);
        this.loaderManager = LoaderManager.getInstance(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButtonLoad();
            }
        });
    }


    @Override
    public void onLoadCanceled(Loader<List<String>> loader) {
        if(loader.getId() == LOADER_ID_USERACCOUNT) {
            // Destroy a Loader by ID.
            this.loaderManager.destroyLoader(loader.getId());
        }
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id,  Bundle args) {
        if(id == LOADER_ID_USERACCOUNT) {
            // Parameters:
            String param1 = (String) args.get(KEY_PARAM1);
            String param2 = (String) args.get(KEY_PARAM2);
            // Return a Loader.
            return new MyAsyncTaskLoader(LoaderActivity.this, param1, param2);
        }
        throw new RuntimeException("TODO..");
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        this.loaderManager.destroyLoader(loader.getId());

        StringBuilder sb = new StringBuilder();

        for(String s: data)  {
            sb.append("string:").append(s).append("\t");
        }
        this.textView.setText(sb.toString());
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {
        this.textView.setText("");
    }


    // User click on "Load Data" button.
    private void clickButtonLoad() {
        this.textView.setText("");
        LoaderManager.LoaderCallbacks<List<String>> loaderCallbacks = this;

        // Arguments:
        Bundle args = new Bundle();
        args.putString(KEY_PARAM1, "Some value1");
        args.putString(KEY_PARAM2, "Some value2");

        // You can pass a null args to a Loader
        Loader<List<String>> loader =  this.loaderManager.initLoader(LOADER_ID_USERACCOUNT, args, loaderCallbacks);
        try {
            loader.registerOnLoadCanceledListener(this); // Loader.OnLoadCanceledListener
        } catch(IllegalStateException e) {
            // There is already a listener registered
        }
        loader.forceLoad(); // Start Loading..
    }

    // User click on "Cancel" button.
    private void clickButtonCancel() {
        Loader<Object> loader = this.loaderManager.getLoader(LOADER_ID_USERACCOUNT);
        if(loader != null)  {
            boolean cancelled = loader.cancelLoad();
        }
    }

    @Override
    public void onLoadComplete(Loader<List<String>> loader, List<String> data) {

    }
}