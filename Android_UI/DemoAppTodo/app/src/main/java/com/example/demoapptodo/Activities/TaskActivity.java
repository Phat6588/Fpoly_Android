package com.example.demoapptodo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.demoapptodo.Adapters.TaskAdapter;
import com.example.demoapptodo.DAO.TasksDAO;
import com.example.demoapptodo.Models.Tasks;
import com.example.demoapptodo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.demoapptodo.Utilities.Constants.*;

public class TaskActivity extends AppCompatActivity {

    private Button buttonAddTask;
    private ListView listTasks;

    private List<Tasks> data;
    private TaskAdapter adapter;

    private View view;
    private EditText editTextName;
    private Button buttonCancel, buttonSave;

    final CollectionReference reference = FirebaseFirestore.getInstance().collection(TABLE_TODO_TASKS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);
        listTasks = (ListView) findViewById(R.id.listTasks);

        // dùng SQLite
         // data = (new TasksDAO(this)).get();

        // dùng firebase
        addListenerFirebase();
        data = new ArrayList<>();
        getTasksFromFirebase();

        adapter = new TaskAdapter(this, data);
        listTasks.setAdapter(adapter);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog(){
        LayoutInflater inflater = LayoutInflater.from(TaskActivity.this);
        view = inflater.inflate(R.layout.layout_add_new, null, false);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
        buttonSave = (Button) view.findViewById(R.id.buttonSave);

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
        builder.setTitle(ADD_NEW_TASK);
        builder.setCancelable(false);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = editTextName.getText().toString();
                Tasks tasks = new Tasks(UUID.randomUUID().toString(), _name);

                // dùng SQLite
                // TasksDAO dao = new TasksDAO(TaskActivity.this);
                // dao.insert(tasks);
                // data = dao.get();
                // adapter.updateData(data);

                // dùng firebase
                addNewTaskToFirebase(tasks);
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

    private void addNewTaskToFirebase(Tasks tasks){
        Map map = new HashMap<String, Object>();
        map.put(COLUMN_TASK_ID, tasks.getId());
        map.put(COLUMN_TASK_NAME, tasks.getName());
        reference.document(tasks.getId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    private void addListenerFirebase(){
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException e) {
                if (e != null){
                    Log.i("Tag>>>>", e.getMessage());
                    return;
                }
                if(value != null && value.size() > 0){
                    List<Tasks> tasks = new ArrayList<>();
                    for(DocumentSnapshot ds: value.getDocuments()){
                        Map<String,Object> map = ds.getData();
                        String _id = map.get(COLUMN_TASK_ID).toString();
                        String _name = map.get(COLUMN_TASK_NAME).toString();
                        Tasks t = new Tasks(_id, _name);
                        tasks.add(t);
                    }
                    data = tasks;
                    adapter.updateData(data);
                }
                else {
                    Log.i("Tag>>>>", "Data null");
                }
            }
        });
    }

    private void getTasksFromFirebase(){
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful() == true){
                    List<Tasks> tasks = new ArrayList<>();
                    for(QueryDocumentSnapshot qdc: task.getResult()){
                        Map<String,Object> map = qdc.getData();
                        String _id = map.get(COLUMN_TASK_ID).toString();
                        String _name = map.get(COLUMN_TASK_NAME).toString();
                        Tasks t = new Tasks(_id, _name);
                        tasks.add(t);
                    }
                    data = tasks;
                    adapter.updateData(data);
                }
                else {

                }
            }
        });
    }
}

















