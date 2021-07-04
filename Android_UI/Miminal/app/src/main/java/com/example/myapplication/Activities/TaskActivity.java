package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.Adapters.TasksAdapter;
import com.example.myapplication.DAO.TaskDAO;
import com.example.myapplication.Models.TodoTask;
import com.example.myapplication.R;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private TasksAdapter adapter;
    private List<TodoTask> data;
    private ListView listTasks;
    private Button buttonAddTask;
    private View view;
    private EditText editText;
    private Button buttonCancel, buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listTasks = (ListView) findViewById(R.id.listTasks);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);

        data = (new TaskDAO(this)).get();
        adapter = new TasksAdapter(this, data);
        listTasks.setAdapter(adapter);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        // for dialog
        LayoutInflater layoutInflater = LayoutInflater.from(TaskActivity.this);
        view = layoutInflater.inflate(R.layout.dialog_add_new, null, false);
        editText = (EditText) view.findViewById(R.id.textViewName);
        buttonSave = (Button) view.findViewById(R.id.buttonSave);
        buttonCancel = (Button) view.findViewById(R.id.buttonCancel);

        // Create a AlertDialog Builder.
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
        // Set title, icon, can not cancel properties.
        builder.setTitle("Add new task.");
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = editText.getText().toString();
                TodoTask todoTask = new TodoTask();
                todoTask.setName(taskName);
                TaskDAO dao = new TaskDAO(TaskActivity.this);
                dao.insert(todoTask);
                data = dao.get();
                adapter.updateData(data);

                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}