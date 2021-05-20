package com.example.demo001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BeLamToanActivity extends AppCompatActivity {

    private TextView txtDiem;
    private TextView txtPhepToan;
    private Button btnDung;
    private Button btnSai;

    private int a, b, c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_lam_toan);

        txtDiem = (TextView) findViewById(R.id.txtDiem);
        txtPhepToan = (TextView) findViewById(R.id.txtPhepToan);
        btnDung = (Button) findViewById(R.id.btnDung);
        btnSai = (Button) findViewById(R.id.btnSai);
        startPhepToan();

        btnDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        btnSai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });
    }

    private void checkAnswer(){

    }

    private void startPhepToan(){
        a = (int) Math.floor(Math.random() * 9);
        b = (int) Math.floor(Math.random() * 9);

        int ketQua = ((int) Math.floor(Math.random() * 9) );
        // ketQua == 0 -> true, false
        if (ketQua == 0){
            c = a + b;
        }
        else {
            c = a + b + (int) Math.floor(Math.random() * 9);
        }
        txtPhepToan.setText(a + " + " + b + " = " + c);
    }
}