package com.example.demo_ontap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.demo_ontap.Adapter.SinhVienAdapter;
import com.example.demo_ontap.DAO.SinhVienDAO;

public class SinhVienListActivity extends AppCompatActivity {

    private ListView listViewSinhVien;
    private SinhVienAdapter adapter;
    private SinhVienDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinh_vien_list);

        listViewSinhVien = (ListView) findViewById(R.id.listViewSinhVien);
        dao = new SinhVienDAO(this);
        adapter = new SinhVienAdapter(dao.get(),this);
        listViewSinhVien.setAdapter(adapter);

        listViewSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return false;
            }
        });
    }
}