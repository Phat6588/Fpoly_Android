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
        data.add(new NhanVien("003", "Tran Van D", "hinh004"));
        data.add(new NhanVien("004", "Tran Van E", "hinh005"));
        data.add(new NhanVien("005", "Tran Van F", "hinh006"));
        data.add(new NhanVien("006", "Tran Van G", "hinh001"));
        data.add(new NhanVien("007", "Tran Van H", "hinh002"));
        data.add(new NhanVien("008", "Tran Van I", "hinh004"));

        adapter = new CustomListNhanVienAdapter(this, data);

        listViewNhanVien = (ListView) findViewById(R.id.listViewNhanVien);
        listViewNhanVien.setAdapter(adapter);

        listViewNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NhanVien nv = (NhanVien) adapter.getItem(i);
                Intent intent = new Intent(DemoNhanVienActivity.this, SecondActivity.class);
                intent.putExtra("maNV", nv.getMaNhanVien());
                intent.putExtra("hoTen", nv.getHoTen());
                intent.putExtra("hinhAnh", nv.getHinhAnh());
                startActivity(intent);
            }
        });
    }
}