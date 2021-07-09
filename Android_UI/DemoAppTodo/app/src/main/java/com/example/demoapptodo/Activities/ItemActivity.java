package com.example.demoapptodo.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demoapptodo.Adapters.ItemAdapter;
import com.example.demoapptodo.Adapters.TaskAdapter;
import com.example.demoapptodo.DAO.ItemsDAO;
import com.example.demoapptodo.DAO.TasksDAO;
import com.example.demoapptodo.Models.Items;
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

public class ItemActivity extends AppCompatActivity {

    private TextView textViewTaskname;
    private Button buttonAddItem;
    private ListView listItems;

    private List<Items> data;
    private ItemAdapter adapter;

    private View view;
    private EditText editTextName;
    private Button buttonCancel, buttonSave;

    private String taskId, taskName;

    final CollectionReference reference = FirebaseFirestore.getInstance().collection(TABLE_TODO_ITEMS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        buttonAddItem = (Button) findViewById(R.id.buttonAddItem);
        listItems = (ListView) findViewById(R.id.listItems);
        textViewTaskname = (TextView) findViewById(R.id.textViewTaskname);

        Intent intent = getIntent();
        taskId = intent.getStringExtra(COLUMN_TASK_ID);
        taskName = intent.getStringExtra(COLUMN_TASK_NAME);

        textViewTaskname.setText(taskName);

        // dùng SQLite
        // data = (new ItemsDAO(this)).getByTaskId(taskId);


        // dùng firebase
        data = new ArrayList<>();
        addListenerFirebase();
        getItemsFromFirebase();



        adapter = new ItemAdapter(this, data);
        listItems.setAdapter(adapter);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

    }

    private void showAddDialog(){
        LayoutInflater inflater = LayoutInflater.from(ItemActivity.this);
        view = inflater.inflate(R.layout.layout_add_new, null, false);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
        buttonSave = (Button) view.findViewById(R.id.buttonSave);

        AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
        builder.setTitle(ADD_NEW_TASK);
        builder.setCancelable(false);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = editTextName.getText().toString();
                Items items = new Items(UUID.randomUUID().toString(), _name, taskId, false);

                // dùng SQLite
                // ItemsDAO dao = new ItemsDAO(ItemActivity.this);
                // dao.insert(items);
                // data = dao.getByTaskId(taskId);
                // adapter.updateData(data);


                // dùng firebase
                addNewItemToFirebase(items);

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


    private void addNewItemToFirebase(Items items){
        Map map = new HashMap<String, Object>();
        map.put(COLUMN_ITEM_ID, items.getId());
        map.put(COLUMN_ITEM_NAME, items.getName());
        map.put(COLUMN_ITEM_STATUS, items.getStatus() == true ? STATUS_TRUE : STATUS_FALSE);
        map.put(COLUMN_ITEM_TASK_ID, items.getTaskId());
        reference.document(items.getId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    private void addListenerFirebase(){
        reference.whereEqualTo(COLUMN_ITEM_TASK_ID, taskId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException e) {
                if (e != null){
                    Log.i("Tag>>>>", e.getMessage());
                    return;
                }
                if(value != null && value.size() > 0){
                    List<Items> tasks = new ArrayList<>();
                    for(DocumentSnapshot ds: value.getDocuments()){
                        Map<String,Object> map = ds.getData();
                        String _id = map.get(COLUMN_ITEM_ID).toString();
                        String _taskId = map.get(COLUMN_ITEM_TASK_ID).toString();
                        String _name = map.get(COLUMN_ITEM_NAME).toString();
                        String _status = map.get(COLUMN_ITEM_STATUS).toString();
                        Items t = new Items(_id, _name, _taskId, _status.equals(STATUS_TRUE));
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

    private void getItemsFromFirebase(){
        reference.whereEqualTo(COLUMN_ITEM_TASK_ID, taskId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful() == true){
                    List<Items> tasks = new ArrayList<>();
                    for(QueryDocumentSnapshot ds: task.getResult()){
                        Map<String,Object> map = ds.getData();
                        String _id = map.get(COLUMN_ITEM_ID).toString();
                        String _taskId = map.get(COLUMN_ITEM_TASK_ID).toString();
                        String _name = map.get(COLUMN_ITEM_NAME).toString();
                        String _status = map.get(COLUMN_ITEM_STATUS).toString();
                        Items t = new Items(_id, _name, _taskId, _status.equals(STATUS_TRUE));
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
}