package com.example.demo001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView txtViewMaNV = (TextView) findViewById(R.id.txtViewSecondMaNV) ;
        TextView txtViewTenNV = (TextView) findViewById(R.id.txtViewSecondTenNV) ;
        ImageView imageView = (ImageView) findViewById(R.id.imageViewSecondHinhAnh) ;

        Intent intent = getIntent();
        String maNV = intent.getStringExtra("maNV");
        String hoTen = intent.getStringExtra("hoTen");
        String hinhAnh = intent.getStringExtra("hinhAnh");

        txtViewMaNV.setText(maNV);
        txtViewTenNV.setText(hoTen);

        int imgId = getResources().getIdentifier(hinhAnh,"drawable",getPackageName());
        imageView.setImageResource(imgId);


    }
}