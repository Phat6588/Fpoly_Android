package com.huydh54.assignment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.huydh54.assignment.R;

public class SignInActivity extends AppCompatActivity {

    Toolbar tbLogin;
    TextView tieuDeDK, quenMK, dangKyDN;
    Button dangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tbLogin = findViewById(R.id.tbLogin);
        tieuDeDK = findViewById(R.id.txtTitleLogin);
        dangKy = findViewById(R.id.btnLogin);
        quenMK = findViewById(R.id.txtQuenMK);
        dangKyDN = findViewById(R.id.txtDangKyDN);

        tieuDeDK.setText("Đăng ký");
        dangKy.setText("Đăng ký");
        ((ViewGroup) quenMK.getParent()).removeView(quenMK);
        dangKyDN.setText("Đăng nhập");
        dangKyDN.setGravity(Gravity.CENTER | Gravity.CENTER);

        setSupportActionBar(tbLogin);
        tbLogin.setNavigationIcon(R.drawable.ic_back_black);
        tbLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        dangKyDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}