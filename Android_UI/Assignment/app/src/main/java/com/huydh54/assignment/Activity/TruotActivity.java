package com.huydh54.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.huydh54.assignment.Adapter.CaiDatAdapter;
import com.huydh54.assignment.Model.CaiDat;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class TruotActivity extends AppCompatActivity {

    ListView lvCaiDat;
    ArrayList<CaiDat> caiDatList;
    CaiDatAdapter caiDatAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truot);
        lvCaiDat = findViewById(R.id.lvCaiDat);
        toolbar = findViewById(R.id.toolBarLab);

        caiDatList = new ArrayList<>();


        caiDatList = new ArrayList<>();
        caiDatList.add(new CaiDat(R.drawable.load_nav_loai, "Phân loại"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_khoan, "Lịch sử chi tiêu"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_thongke, "Thống kê"));

        caiDatList.add(new CaiDat(R.drawable.load_nav_vicuatoi, "Ví của tôi"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_nhom, "Chuyên viên"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_sono, "Sổ nợ"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_maytinh, "Tính lãi vay"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_thoigian, "Thời gian"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_caidat, "Cài đặt"));

        caiDatList.add(new CaiDat(R.drawable.load_nav_dangxuat, "Đăng xuất"));

        caiDatAdapter = new CaiDatAdapter(TruotActivity.this, caiDatList);
        lvCaiDat.setAdapter(caiDatAdapter);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

    }
}