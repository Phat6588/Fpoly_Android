package com.example.demo001;

import adapter.CustomListNhanVienAdapter;
import androidx.appcompat.app.AppCompatActivity;
import model.NhanVien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DemoNhanVienActivity extends AppCompatActivity {

    List<NhanVien> data;
    CustomListNhanVienAdapter adapter;

    ListView listViewNhanVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_nhan_vien);

        data = new ArrayList<>();
        data.add(new NhanVien("001", "Nguyen Van A", "hinh001"));
        data.add(new NhanVien("002", "Tran Van C", "hinh002"));

        adapter = new CustomListNhanVienAdapter(this, data);

        listViewNhanVien = (ListView) findViewById(R.id.listViewNhanVien);
        listViewNhanVien.setAdapter(adapter);

    }
}