package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import controller.ClazzController;
import model.Clazz;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ClazzListActivity extends AppCompatActivity {

    private ListView clazzList;
    private ClazzController controller;
    private List<Clazz> arrayList = new ArrayList<Clazz>();
    private ArrayAdapter<Clazz> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clazz_list);

        this.clazzList = (ListView) findViewById(R.id.clazzList);
        this.controller = new ClazzController(this);

        List<Clazz> list = this.controller.get();
//        Log.i("SQLite", ">>>>>>>>>>"+String.valueOf(list.size()));
        arrayList.addAll(list);
        // Define a new Adapter
        // 1 - Context
        // 2 - Layout for the row
        // 3 - ID of the TextView to which the data is written
        // 4 - the List of data
        this.listViewAdapter = new ArrayAdapter<Clazz>(this,
                android.R.layout.simple_list_item_1, this.arrayList);

        // Assign adapter to ListView
        this.clazzList.setAdapter(this.listViewAdapter);
    }
}