package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SVTrongLopActivity extends AppCompatActivity {
    TextView TenLop;
    ListView lvSVTrongLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_v_trong_lop);
        TenLop=findViewById(R.id.TenLop);
        lvSVTrongLop=findViewById(R.id.lvSVTrongLop);
        Intent intent=getIntent();
        String tenlop=intent.getStringExtra("tenlop");
        ArrayList<String> dstenlop=intent.getStringArrayListExtra("dstensv");
        TenLop.setText(tenlop);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(SVTrongLopActivity.this, android.R.layout.simple_expandable_list_item_1,dstenlop);
        lvSVTrongLop.setAdapter(adapter);
    }
}