package com.example.demoslide56768;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.demoslide56768.Adapter.LaptopAdapter;
import com.example.demoslide56768.DAO.LaptopDAO;
import com.example.demoslide56768.Model.Laptop;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    LaptopAdapter adapter;
    List<Laptop> list;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        listView = (ListView) findViewById(R.id.listViewLaptop);
        list = (new LaptopDAO(this)).get();
        adapter = new LaptopAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Laptop laptop = (Laptop) adapter.getItem(i);
                Intent intent = new Intent(WelcomeActivity.this, LaptopDetailActivity.class);
                intent.putExtra("id", laptop.getLaptopId());
                startActivity(intent);
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, AddNewLaptopActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            finish();
            startActivity(getIntent());
        }
    }
}