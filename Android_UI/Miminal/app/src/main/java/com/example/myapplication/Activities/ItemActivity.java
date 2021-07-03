package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.Adapters.ItemAdapter;
import com.example.myapplication.Adapters.TasksAdapter;
import com.example.myapplication.DAO.ItemDAO;
import com.example.myapplication.DAO.TaskDAO;
import com.example.myapplication.Models.TodoItem;
import com.example.myapplication.Models.TodoTask;
import com.example.myapplication.R;

import java.util.List;

public class ItemActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private List<TodoItem> data;
    private ListView listItems;
    private Button buttonAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        listItems = (ListView) findViewById(R.id.listItems);
        buttonAddItem = (Button) findViewById(R.id.buttonAddItem);

        data = (new ItemDAO(this)).getByTaskId(id);
        adapter = new ItemAdapter(this, data);
        listItems.setAdapter(adapter);
    }
}