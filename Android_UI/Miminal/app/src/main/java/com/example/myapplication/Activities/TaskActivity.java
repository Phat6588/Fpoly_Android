package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.Adapters.TasksAdapter;
import com.example.myapplication.DAO.TasksDAO;
import com.example.myapplication.Models.Tasks;
import com.example.myapplication.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TaskActivity extends AppCompatActivity {

    private TasksAdapter adapter;
    private List<Tasks> data;
    private ListView listTasks;
    private Button buttonAddTask;
    private View view;
    private EditText editText;
    private Button buttonCancel, buttonSave;

    final CollectionReference reference = FirebaseFirestore.getInstance().collection("tasks");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listTasks = (ListView) findViewById(R.id.listTasks);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);

        // dùng SQLite
         data = (new TasksDAO(this)).get();

        // FB
//        data = new ArrayList<>();
//        addListenerFB();
//        getTasksFB();

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
                Tasks todoTask = new Tasks();
                todoTask.setName(taskName);
                todoTask.setId(UUID.randomUUID().toString());
                TasksDAO dao = new TasksDAO(TaskActivity.this);
                // dùng SQLite
                 dao.insert(todoTask);
                 data = dao.get();
                 adapter.updateData(data);

                // dùng fb
//                addTaskToFB(todoTask);

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

    private void addTaskToFB(Tasks task) {
        Map map = new HashMap<String, String>();
        map.put("id", task.getId());
        map.put("name", task.getName());
        reference.document(task.getId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    private void addListenerFB(){
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot snapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(">>>>>>>>TAG", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.size() > 0) {
                    List<DocumentSnapshot> list = snapshot.getDocuments();
                    List<Tasks> tasks = new ArrayList<>();
                    for (DocumentSnapshot dc :list) {
                        Map<String, Object> map =  dc.getData();
                        String id = map.get("id").toString();
                        String name = map.get("name").toString();
                        Tasks task = new Tasks(id, name);
                        tasks.add(task);
                    }
                    data = tasks;
                    adapter.updateData(data);
                } else {
                    Log.d(">>>>>>>>>>>>>TAG", "Current data: null");
                }
            }
        });
    }

    private void getTasksFB() {
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Tasks> tasks = new ArrayList<>();
                            for (QueryDocumentSnapshot dc :task.getResult()) {
                                Map<String, Object> map =  dc.getData();
                                String id = map.get("id").toString();
                                String name = map.get("name").toString();
                                Tasks t = new Tasks(id, name);
                                tasks.add(t);
                            }
                            data = tasks;
                            adapter.updateData(data);
                        } else {
                            Log.d(">>>>>>>>>>>>>TAG", "Current data: null");
                        }
                    }
                });
    }

}