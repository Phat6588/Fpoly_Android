package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Adapters.ItemAdapter;
import com.example.myapplication.DAO.ItemsDAO;
import com.example.myapplication.Models.Items;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private List<Items> data;
    private ListView listItems;
    private Button buttonAddItem;
    private TextView taskNameLabel;

    private View view;
    private EditText editText;
    private Button buttonCancel, buttonSave;

    private String taskId;
    private String taskName;

    final CollectionReference reference = FirebaseFirestore.getInstance().collection("items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Todo list");
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        taskId = intent.getStringExtra("id");
        taskName = intent.getStringExtra("name");

        listItems = (ListView) findViewById(R.id.listItems);
        buttonAddItem = (Button) findViewById(R.id.buttonAddItem);
        taskNameLabel = (TextView) findViewById(R.id.taskNameLabel);

        taskNameLabel.setText(taskName);

        // dùng SQLite
//        data = (new ItemsDAO(this)).getByTaskId(taskId);

        // dung FB
        data = new ArrayList<>();
        addListenerFB();
        getItemsFB();


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

                Items item = new Items();
                item.setId(UUID.randomUUID().toString());
                item.setName(name);
                item.setStatus(false);
                item.setTaskId(taskId);

//                ItemsDAO dao = new ItemsDAO(ItemActivity.this);
//                dao.insert(item);
//                data = dao.getByTaskId(taskId);
//                adapter.updateData(data);

                // dung fb
                addItemToFB(item);

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


    private void addItemToFB(Items item) {
        Map map = new HashMap<String, Object>();
        map.put("id", item.getId());
        map.put("name", item.getName());
        map.put("status", item.isStatus() == true ? "1" : "0");
        map.put("taskId", item.getTaskId());
        reference.document(item.getId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    Log.w("TAG", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.size() > 0) {
                    List<DocumentSnapshot> list = snapshot.getDocuments();
                    List<Items> items = new ArrayList<>();
                    for (DocumentSnapshot dc :list) {
                        Map<String, Object> map =  dc.getData();
                        String tId = map.get("taskId").toString();
                        if(tId.equals(taskId) == false) continue;
                        String id = map.get("id").toString();
                        String name = map.get("name").toString();
                        Boolean status = Integer.parseInt(map.get("status").toString()) == 1;
                        Items item = new Items(id, name, status, tId);
                        items.add(item);
                    }
                    data = items;
                    adapter.updateData(data);
                } else {
                    Log.d("TAG", "Current data: null");
                }
            }
        });
    }

    private void getItemsFB() {
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Items> items = new ArrayList<>();
                            for (DocumentSnapshot dc :task.getResult()) {
                                Map<String, Object> map =  dc.getData();
                                String tId = map.get("taskId").toString();
                                if(tId.equals(taskId) == false) continue;
                                String id = map.get("id").toString();
                                String name = map.get("name").toString();
                                Boolean status = Integer.parseInt(map.get("status").toString()) == 1;
                                Items item = new Items(id, name, status, tId);
                                items.add(item);
                            }
                            data = items;
                            adapter.updateData(data);
                        } else {
                            Log.d("TAG", "Current data: null");
                        }
                    }
                });
    }
}