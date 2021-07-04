package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
    private TextView taskNameLabel;

    private View view;
    private EditText editText;
    private Button buttonCancel, buttonSave;

    private int taskId;
    private String taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Todo list");
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        taskId = intent.getIntExtra("id", 0);
        taskName = intent.getStringExtra("name");

        listItems = (ListView) findViewById(R.id.listItems);
        buttonAddItem = (Button) findViewById(R.id.buttonAddItem);
        taskNameLabel = (TextView) findViewById(R.id.taskNameLabel);

        taskNameLabel.setText(taskName);

        data = (new ItemDAO(this)).getByTaskId(taskId);
        adapter = new ItemAdapter(this, data);
        listItems.setAdapter(adapter);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        // for dialog
        LayoutInflater layoutInflater = LayoutInflater.from(ItemActivity.this);
        view = layoutInflater.inflate(R.layout.dialog_add_new, null, false);
        editText = (EditText) view.findViewById(R.id.textViewName);
        buttonSave = (Button) view.findViewById(R.id.buttonSave);
        buttonCancel = (Button) view.findViewById(R.id.buttonCancel);

        // Create a AlertDialog Builder.
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
        // Set title, icon, can not cancel properties.
        builder.setTitle("Add new item.");
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();

                TodoItem item = new TodoItem();
                item.setName(name);
                item.setStatus(false);
                item.setTaskId(taskId);

                ItemDAO dao = new ItemDAO(ItemActivity.this);
                dao.insert(item);

                data = dao.getByTaskId(taskId);
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