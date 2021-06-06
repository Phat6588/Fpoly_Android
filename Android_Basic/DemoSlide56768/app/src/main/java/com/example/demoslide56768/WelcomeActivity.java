package com.example.demoslide56768;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.demoslide56768.Adapter.LaptopAdapter;
import com.example.demoslide56768.DAO.LaptopDAO;
import com.example.demoslide56768.Model.Laptop;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    LaptopAdapter adapter;
    List<Laptop> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ListView listView = (ListView) findViewById(R.id.listViewLaptop);
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
    }
}