package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.Adapters.TasksAdapter;
import com.example.myapplication.DAO.TaskDAO;
import com.example.myapplication.Models.TodoTask;
import com.example.myapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private TasksAdapter adapter;
    private List<TodoTask> data;
    private ListView listTasks;
    private Button buttonAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listTasks = (ListView) findViewById(R.id.listTasks);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);

        data = (new TaskDAO(this)).get();
        adapter = new TasksAdapter(this, data);
        listTasks.setAdapter(adapter);


    }
}