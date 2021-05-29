package com.example.demo001;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import adapter.CustomListSinhVienAdapter;
import model.SinhVien;

public class DemoListViewActivity extends AppCompatActivity {

    List<SinhVien> danhSach;
    CustomListSinhVienAdapter customListSinhVienAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list_view);

        danhSach = new ArrayList<>();
        danhSach.add(new SinhVien("001", "Nguyễn Văn A"));
        danhSach.add(new SinhVien("002", "Nguyễn Văn B"));
        danhSach.add(new SinhVien("003", "Nguyễn Văn C"));
        danhSach.add(new SinhVien("004", "Nguyễn Văn D"));
        danhSach.add(new SinhVien("005", "Nguyễn Văn E"));
        danhSach.add(new SinhVien("006", "Nguyễn Văn F"));

        customListSinhVienAdapter = new CustomListSinhVienAdapter(danhSach);

        ListView listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setAdapter(customListSinhVienAdapter);

        listView1.setOnItemClickListener((adapterView, view, i, l) -> {
            SinhVien sinhVien = (SinhVien) customListSinhVienAdapter.getItem(i);
            Toast.makeText(DemoListViewActivity.this, sinhVien.getHoTen(),
                    Toast.LENGTH_LONG).show();
        });

//        listView1.setAdapter(adapter);

    }
}