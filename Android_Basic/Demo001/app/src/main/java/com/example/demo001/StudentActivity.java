package com.example.demo001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class StudentActivity extends AppCompatActivity {

    private TextView txtDuLieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        txtDuLieu = (TextView) findViewById(R.id.duLieu);
        Intent intent = getIntent();
        String duLieu = intent.getStringExtra("data");
        float result =
                intent.getFloatExtra("result", 0.0f);
//        Log.i("my tag", result);
        txtDuLieu.setText(String.valueOf(result));
    }
}