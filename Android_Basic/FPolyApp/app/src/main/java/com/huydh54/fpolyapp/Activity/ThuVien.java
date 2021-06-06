package com.huydh54.fpolyapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.huydh54.fpolyapp.Adapter.AnhThuVienAdapter;
import com.huydh54.fpolyapp.Model.AnhThuVien;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class ThuVien extends AppCompatActivity {

    Toolbar tbThuVien;
    GridView gvThuVien;
    ArrayList<AnhThuVien> dsTenAnh;
    AnhThuVienAdapter anhAdapter;
    int viTriSV = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_vien);

        tbThuVien = findViewById(R.id.tbThuVien);
        gvThuVien = findViewById(R.id.gvThuVien);
        dsTenAnh = new ArrayList<>();

        try {
            Bundle bundleNhanSV = getIntent().getBundleExtra("boxsv");
            viTriSV = bundleNhanSV.getInt("vitrisv");
        } catch (Exception e) {

        }

        dsTenAnh.add(new AnhThuVien("load_sv1"));
        dsTenAnh.add(new AnhThuVien("load_sv2"));
        dsTenAnh.add(new AnhThuVien("load_sv3"));
        dsTenAnh.add(new AnhThuVien("load_sv4"));
        dsTenAnh.add(new AnhThuVien("load_sv5"));
        dsTenAnh.add(new AnhThuVien("load_sv6"));

        anhAdapter = new AnhThuVienAdapter(ThuVien.this, dsTenAnh);
        gvThuVien.setAdapter(anhAdapter);

        setSupportActionBar(tbThuVien);
        tbThuVien.setNavigationIcon(R.drawable.ic_webview_back);
        tbThuVien.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundleAnh = new Bundle();
//                bundleAnh.putString("tenanh", "khongchon");
                bundleAnh.putInt("vitrisv2", viTriSV);
                intent.putExtra("boxanh", bundleAnh);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        gvThuVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tenAnh = dsTenAnh.get(i).getTenAnh();
                Intent intent = new Intent();
                Bundle bundleAnh = new Bundle();
                bundleAnh.putString("tenanh", tenAnh);
                bundleAnh.putInt("vitrisv2", viTriSV);
                intent.putExtra("boxanh", bundleAnh);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}